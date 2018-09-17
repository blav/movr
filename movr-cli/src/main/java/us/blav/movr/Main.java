package us.blav.movr;


import us.blav.commons.Injection;
import us.blav.movr.cloudrails.CloudRails;

import static us.blav.commons.Injection.injector;

public class Main {

  static class Transfer {

    public void transfer () {
      CloudStorageApi api = CloudStorageApi.getInstance (CloudRails.CLOUDRAILS);
      CloudDrive drive = api.getDrive ("onedrive");

      injector ().getInstance (ParallelIndexBuilder.class).buildIndex (drive);
      System.out.println ("done with index building");
    }
  }

  public static void main (String... args) {
    new Transfer ().transfer ();
  }
}
