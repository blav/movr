package us.blav.movr;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entry {

  public static final Entry ROOT = new Entry ();

  @JsonProperty ("path")
  private String path;

  @JsonProperty ("name")
  private String name;

  @JsonProperty ("size")
  private int size;

  @JsonProperty ("folder")
  private boolean folder;

  private Entry () {
    this.path = "/";
    this.folder = true;
    this.name = "root";
    this.size = 0;
  }

  public Entry (String name, String path, boolean folder, int size) {
    this.folder = folder;
    this.name = name;
    this.path = path;
    this.size = size;
  }

  public String getPath () {
    return path;
  }

  public String getName () {
    return name;
  }

  public int getSize () {
    return size;
  }

  public boolean isFolder () {
    return folder;
  }
}
