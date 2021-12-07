/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.api;

import com.github.idelstak.youtubeinfofx.spi.VideoCategories;
import com.github.idelstak.youtubeinfofx.spi.VideoCategory;
import java.io.IOException;

import static java.util.stream.Collectors.toList;

import com.github.idelstak.youtubeinfofx.api.service.GoogleService;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class YouTubeVideoCategories extends VideoCategories {

  YouTubeVideoCategories(GoogleService gs) {
    super(gs);
  }

  public Iterable<VideoCategory> getVideoCategories() throws IOException {
    return super.getService()
            .videoCategories()
            .list("snippet")
            .setRegionCode("US")
            .setKey(super.getKey())
            .execute()
            .getItems()
            .stream()
            .map(super::toVideoCategory)
            .collect(toList());
  }
}
