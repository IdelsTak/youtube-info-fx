/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.api.service;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Builder;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport;
import static com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public interface GoogleService {

  String getKey();

  <T extends AbstractGoogleClient> T getService() throws GeneralSecurityException, IOException;

  public enum Client implements GoogleService {
    YOUTUBE {
      private static final String APP_NAME = "youtube-info-fx";
      private final String API_KEY = "AIzaSyAQswnEqxrgXVvd4H3to_h31HWZ7wkriX8";

      @Override
      public String getKey() {
        return API_KEY;
      }

      @Override
      public YouTube getService() throws GeneralSecurityException, IOException {
        HttpTransport transport = newTrustedTransport();
        Builder builder = new Builder(transport, getDefaultInstance(), null);

        return builder.setApplicationName(APP_NAME).build();
      }
    };
  }
}
