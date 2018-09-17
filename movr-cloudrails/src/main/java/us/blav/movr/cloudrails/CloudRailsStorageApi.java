package us.blav.movr.cloudrails;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.servicecode.commands.awaitCodeRedirect.LocalReceiver;
import com.cloudrail.si.services.Dropbox;
import com.cloudrail.si.services.OneDrive;
import us.blav.movr.CloudDrive;
import us.blav.movr.CloudStorageApi;

import javax.inject.Named;
import java.util.NoSuchElementException;

import static us.blav.movr.cloudrails.CloudRails.CLOUDRAILS;

@Named (CLOUDRAILS)
class CloudRailsStorageApi implements CloudStorageApi {

  static {
    CloudRail.setAppKey ("5b9ce66c9dfc1256ed1bd256");
  }

  @Override
  public CloudDrive getDrive (String storage) {
    int port = 8080;
    String redirectUri = "http://localhost:" + port + "/";
    LocalReceiver receiver = new LocalReceiver (port);

    switch (storage) {
      case "onedrive":
        return new CloudRailsDrive (new OneDrive (
          receiver,
          "9d3c7cb9-dd0b-4ec3-8ae4-383d8b0b34c9",
          "txpyGYKX7_)~dmgGLH4159!",
          redirectUri,
          "someState"
        ));

      case "dropbox":
        return new CloudRailsDrive (new Dropbox (
          receiver,
          "4beb3llqz3kpomt",
          "vpt6dmkmt73rg2g",
          "http://localhost:" + port + "/",
          "someState"
        ));

      case "googledrive":
      case "box":
      default:
        throw new NoSuchElementException (storage);
    }
  }
}
