/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.api;

import com.github.idelstak.youtubeinfofx.spi.VideoCategory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.Test;

import static com.github.idelstak.youtubeinfofx.api.service.GoogleService.Client.YOUTUBE;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class YouTubeVideoCategoriesTest {

  public YouTubeVideoCategoriesTest() {
  }

  @Test
  public void shouldListCategories() throws GeneralSecurityException, IOException {
    YouTubeVideoCategories categories = new YouTubeVideoCategories(YOUTUBE);
    Iterable<VideoCategory> videoCategories = categories.getVideoCategories();

    for (VideoCategory videoCategory : videoCategories) {
      System.out.println(videoCategory);
    }
  }

}
