/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.api;

import com.github.idelstak.youtubeinfofx.spi.VideoCategories;
import com.github.idelstak.youtubeinfofx.spi.VideoCategory;
import com.google.api.services.youtube.YouTube;
import java.io.IOException;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class YouTubeVideoCategories extends VideoCategories {

  public YouTubeVideoCategories(YouTube youTube) {
    super(youTube);
  }

  public Iterable<VideoCategory> getVideoCategories() throws IOException {
    return super.getService()
            .videoCategories()
            .list("snippet")
            .setRegionCode("US")
            .execute()
            .getItems()
            .stream()
            .map(super::toVideoCategory)
            .collect(toList());
  }
}
