package us.blav.movr;

import com.google.inject.Key;
import com.google.inject.name.Names;

import static us.blav.commons.Injection.injector;

public interface CloudStorageApi {

  static CloudStorageApi getInstance (String vendor) {
    return injector ().getInstance (Key.get (CloudStorageApi.class, Names.named (vendor)));
  }

  CloudDrive getDrive (String storage);

}
