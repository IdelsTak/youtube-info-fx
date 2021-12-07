/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.spi;

import com.github.idelstak.youtubeinfofx.api.YouTubeVideoCategory;
import com.google.api.services.youtube.YouTube;
import java.io.IOException;
import java.security.GeneralSecurityException;
import com.github.idelstak.youtubeinfofx.api.service.GoogleService;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public abstract class VideoCategories {

  private final GoogleService gs;

  protected VideoCategories(GoogleService gs) {
    this.gs = gs;
  }

  public abstract Iterable<VideoCategory> getVideoCategories() throws IOException;

  protected String getKey() {
    return gs.getKey();
  }

  protected YouTube getService() {
    try {
      return gs.getService();
    } catch (GeneralSecurityException | IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public VideoCategory toVideoCategory(com.google.api.services.youtube.model.VideoCategory vc) {
    return new YouTubeVideoCategory(vc.getId(), vc.getSnippet().getTitle());
  }

}
