package us.blav.movr;

import java.util.Collection;

public interface CloudDrive {

  Entry getRoot ();

  Collection<Entry> getChildren (Entry entry);

}
