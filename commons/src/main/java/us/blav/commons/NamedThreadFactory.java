package us.blav.commons;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

  private final String prefix;

  private final AtomicInteger counter;

  public NamedThreadFactory (String prefix) {
    this.prefix = prefix;
    this.counter = new AtomicInteger ();
  }

  @Override
  public Thread newThread (Runnable r) {
    return new Thread (r, prefix + "-" + counter.incrementAndGet ());
  }
}
