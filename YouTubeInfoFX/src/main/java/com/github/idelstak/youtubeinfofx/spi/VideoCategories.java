/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.spi;

import com.github.idelstak.youtubeinfofx.api.YouTubeVideoCategory;
import com.google.api.services.youtube.YouTube;
import java.io.IOException;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public abstract class VideoCategories {

  private final YouTube youTube;

  protected VideoCategories(YouTube youTube) {
    this.youTube = youTube;
  }

  public abstract Iterable<VideoCategory> getVideoCategories() throws IOException;

  protected YouTube getService() {
    return youTube;
  }

  public VideoCategory toVideoCategory(com.google.api.services.youtube.model.VideoCategory category) {
    return new YouTubeVideoCategory(category.getId(), category.getSnippet().getTitle());
  }

}
