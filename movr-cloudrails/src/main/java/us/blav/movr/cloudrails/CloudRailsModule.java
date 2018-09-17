package us.blav.movr.cloudrails;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import us.blav.movr.CloudStorageApi;

public class CloudRailsModule implements Module {

  @Override
  public void configure (Binder binder) {
    binder.bind (CloudStorageApi.class)
      .annotatedWith (Names.named (CloudRails.CLOUDRAILS))
      .to (CloudRailsStorageApi.class);
  }
}
