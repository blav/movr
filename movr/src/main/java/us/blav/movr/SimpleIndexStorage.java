package us.blav.movr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimpleIndexStorage implements IndexStorage {

  private static final Logger logger = LoggerFactory.getLogger (SimpleIndexStorage.class);

  @Inject
  private Config config;

  public class SimpleIndexWriter implements IndexWriter {

    private final List<Entry> entries;

    public SimpleIndexWriter () {
      entries = new ArrayList<> ();
    }

    @Override
    public void append (Entry entry) {
      entries.add (entry);
    }

    @Override
    public void close () {
      try (OutputStream out = Files.newOutputStream (Paths.get (config.getBasePath (), "index.json"))) {
        new ObjectMapper ().writer ().writeValue (out, entries);
      } catch (IOException e) {
        logger.error ("", e);
      }
    }
  }

  @Override
  public IndexWriter update () {
    return new SimpleIndexWriter ();
  }

}
