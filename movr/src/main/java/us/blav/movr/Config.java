package us.blav.movr;

import javax.inject.Singleton;

@Singleton
public class Config {

  public String getBasePath () {
    return "/Users/blav/dev/perso/cloudmovr/work";
  }

  public int getParallelBuilderQueueSize () {
    return 1_000;
  }

  public int getParallelFeederPoolSize () {
    return 10;
  }
}
