package com.hcmute.puremusic.data.bean;

public class LibraryInfo {

  private final String title;
  private final String summary;
  private final String url;

  public LibraryInfo(String title, String summary, String url) {
    this.title = title;
    this.summary = summary;
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public String getSummary() {
    return summary;
  }

  public String getUrl() {
    return url;
  }
}
