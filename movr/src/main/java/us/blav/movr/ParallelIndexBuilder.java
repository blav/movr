package us.blav.movr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.blav.commons.NamedThreadFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MINUTES;

public class ParallelIndexBuilder implements IndexBuilder {

  private static final Logger logger = LoggerFactory.getLogger (ParallelIndexBuilder.class);

  private Config config;

  private final ArrayBlockingQueue<Entry> queue;

  private final AtomicInteger processing;


  @Inject
  public ParallelIndexBuilder (Config config) {
    this.config = config;
    queue = new ArrayBlockingQueue<> (config.getParallelBuilderQueueSize ());
    processing = new AtomicInteger ();
  }

  @Override
  public void buildIndex (CloudDrive drive) {
    ThreadPoolExecutor pool = createPool (config.getParallelFeederPoolSize ());
    Entry poison = new Entry (null, null, false, 0);
    try {
      enqueue (drive.getRoot ());
      processing.incrementAndGet ();
      while (true) {
        Entry current = queue.take ();
        if (current == poison)
          break;

        logger.debug ("{} - {} {}", current.isFolder (), current.getPath (), current.getSize ());
        pool.submit (() -> {
          Collection<Entry> children = drive.getChildren (current);
          children.forEach (this::enqueue);
          processing.addAndGet (children.size ());
          if (processing.decrementAndGet () == 0)
            enqueue (poison);
        });
      }
    } catch (InterruptedException e) {
    }
  }

  private void enqueue (Entry entry) {
    try {
      queue.put (entry);
    } catch (InterruptedException e) {
      logger.error ("", e);
    }
  }

  private static ThreadPoolExecutor createPool (int size) {
    return new ThreadPoolExecutor (size, size, 5, MINUTES, new LinkedBlockingQueue<> (),
      new NamedThreadFactory ("index-builder"), new ThreadPoolExecutor.AbortPolicy ());
  }
}
