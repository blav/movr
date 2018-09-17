package us.blav.movr.cloudrails;

import com.cloudrail.si.interfaces.CloudStorage;
import us.blav.movr.CloudDrive;
import us.blav.movr.Entry;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

class CloudRailsDrive implements CloudDrive {

  private final CloudStorage storage;

  CloudRailsDrive (CloudStorage storage) {
    this.storage = storage;
  }

  @Override
  public Entry getRoot () {
    return Entry.ROOT;
  }

  @Override
  public Collection<Entry> getChildren (Entry entry) {
    return Optional.of (entry)
      .filter (Entry::isFolder)
      .map (e -> storage.getChildren (e.getPath ()).stream ()
        .map (m -> new Entry (m.getName (), m.getPath (), m.getFolder (), m.getSize ()))
        .collect (toList ()))
      .orElse (emptyList ());
  }
}
