/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.api.credentials;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import static com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.load;
import static com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Builder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import static java.util.Arrays.asList;

import java.util.Collection;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class Auth {

  private static final String CLIENT_SECRETS = "client_secrets.json";
  private static final Collection<String> SCOPES = asList("https://www.googleapis.com/auth/youtube.readonly");
  private static final String APPLICATION_NAME = "Quickstart";
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

  public static HttpRequestInitializer authorize(HttpTransport httpTransport) throws IOException {
    // Load client secrets.
    InputStream in = Auth.class.getResourceAsStream(CLIENT_SECRETS);
    GenericJson json = load(JSON_FACTORY, new InputStreamReader(in));
    // Build flow and trigger user authorization request.
    AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, (GoogleClientSecrets) json, SCOPES).build();
    LocalServerReceiver receiver = new LocalServerReceiver();
    AuthorizationCodeInstalledApp installedApp = new AuthorizationCodeInstalledApp(flow, receiver);
    Credential credential = installedApp.authorize("user");
    return credential;
  }

  public static YouTube getService() throws GeneralSecurityException, IOException {
    HttpTransport httpTransport = newTrustedTransport();
    HttpRequestInitializer initializer = authorize(httpTransport);
    Builder builder = new Builder(httpTransport, JSON_FACTORY, initializer);

    return builder.setApplicationName(APPLICATION_NAME).build();
  }
}
