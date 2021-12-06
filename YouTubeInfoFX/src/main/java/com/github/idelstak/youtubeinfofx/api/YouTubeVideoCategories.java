/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.api;

import com.github.idelstak.youtubeinfofx.spi.VideoCategory;
import com.google.api.services.youtube.YouTube;
import java.io.IOException;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class YouTubeVideoCategories {

  private final YouTube youTube;

  public YouTubeVideoCategories(YouTube youTube) {
    this.youTube = youTube;
  }

  public Iterable<VideoCategory> getVideoCategories() throws IOException {
    return youTube.videoCategories()
            .list("snippet")
            .setRegionCode("US")
            .execute()
            .getItems()
            .stream()
            .map(this::toVideoCategory)
            .collect(toList());
  }

  private VideoCategory toVideoCategory(com.google.api.services.youtube.model.VideoCategory category) {
    return new YouTubeVideoCategory(category.getId(), category.getSnippet().getTitle());
  }
}
