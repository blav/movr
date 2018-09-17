package us.blav.movr;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.stream.Stream;

public class SimpleIndexBuilder implements IndexBuilder {

  private static final Logger logger = LoggerFactory.getLogger (SimpleIndexBuilder.class);

  @Inject
  private IndexStorage storage;

  @Override
  public void buildIndex (CloudDrive drive) {
    try (IndexStorage.IndexWriter writer = storage.update ()){
      drive.getChildren (drive.getRoot ()).stream ()
        .flatMap (e -> flatten (drive, e))
        .peek (ie -> logger.debug ("{} - {} {}", ie.isFolder (), ie.getPath (), ie.getSize ()))
        .forEach (writer::append);
    }
  }

  @VisibleForTesting
  Stream<Entry> flatten (CloudDrive drive, Entry entry) {
    return Stream.concat (
      Stream.of (entry),
      entry.isFolder () ?
        drive.getChildren (entry).stream ()
          .flatMap (e -> flatten (drive, e)) :
        Stream.empty ());
  }
}
