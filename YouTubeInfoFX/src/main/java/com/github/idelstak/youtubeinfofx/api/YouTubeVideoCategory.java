/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.api;

import com.github.idelstak.youtubeinfofx.spi.VideoCategory;

public class YouTubeVideoCategory implements VideoCategory {

  private final String id;
  private final String title;

  public YouTubeVideoCategory(String id, String title) {
    this.id = id;
    this.title = title;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public String toString() {
    return String.format("Video Category(id: %s; title: %s)", id, title);
  }

}
