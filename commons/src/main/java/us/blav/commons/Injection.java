package us.blav.commons;

import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.ServiceLoader;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.util.Modules.combine;

public class Injection {

  private static volatile Injector injector;

  public static Injector injector () {
    if (injector != null)
      return injector;

    synchronized (Injection.class) {
      return (injector != null) ? injector :
        (injector = createInjector (combine (ServiceLoader.load (Module.class))));
    }
  }
}
