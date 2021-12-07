/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.ui;

import com.github.idelstak.youtubeinfofx.ui.controllers.APIKeyDialogController;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Builder;
import com.google.api.services.youtube.model.VideoCategory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport;
import static com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.logging.Logger.getLogger;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class YouTubeInfoFXApp extends Application {

  private static final Logger LOG = getLogger(YouTubeInfoFXApp.class.getName());
  private static final String API_KEY_DIALOG_FXML = "/fxml/api-key-dialog.fxml";
  private static final String APP_TITLE = "YouTubeInfoFX";
  private static final String API_KEY = "api.key";
  private static final String APP_NAME = "application.name";
  private static final Properties PROPERTIES = new Properties();
  private static final String PROPS_DIRECTORY_NAME = ".youtube-info-fx-credentials";
  private static final File PROPS_DIR_FILE = new File(System.getProperty("user.home") + "/" + PROPS_DIRECTORY_NAME);
  private static final String PROPS_FILE_NAME = "properties";
  private static final File PROPS_FILE = new File(System.getProperty("user.home") + "/" + PROPS_DIRECTORY_NAME + "/" + PROPS_FILE_NAME);

  @Override
  public void start(Stage stage) {
    if (!PROPS_DIR_FILE.exists() && !PROPS_DIR_FILE.mkdirs()) {
      LOG.log(Level.WARNING, "Failed to create directory: {0}", PROPS_DIR_FILE);
    }

    if (!PROPS_FILE.exists()) {
      try {
        PROPS_FILE.createNewFile();
      } catch (IOException ex) {
        LOG.log(Level.SEVERE, null, ex);
      }
    }

    try ( InputStream is = new FileInputStream(PROPS_FILE)) {
      PROPERTIES.load(is);
    } catch (IOException ex) {
      throw new RuntimeException(format("Failed to load the application properties. Reason: %s", ex.getMessage()));
    }

    String appName = PROPERTIES.getProperty(APP_NAME);
    String apiKey = PROPERTIES.getProperty(API_KEY);

    Parent root;

    List<VideoCategory> categories = getService(appName)
            .map(yt -> getCategories(yt, apiKey))
            .orElse(emptyList());

    if (categories.isEmpty()) {
      URL url = getClass().getResource(API_KEY_DIALOG_FXML);
      FXMLLoader loader = new FXMLLoader(url);

      try {
        root = loader.load();
      } catch (IOException ex) {
        throw new RuntimeException();
      }

      APIKeyDialogController controller = loader.getController();

      controller.setAppName(appName);
      controller.setApiKey(apiKey);
    } else {
      root = new BorderPane(new Label("Success!"));
      ((BorderPane) root).setPrefSize(300, 300);
    }

    stage.setScene(new Scene(root));
    stage.setTitle(APP_TITLE);

    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  private Optional<YouTube> getService(String appName) {
    String applicationName = appName;

    if (applicationName == null || applicationName.isEmpty()) {
      TextInputDialog inputDlg = new TextInputDialog();

      inputDlg.setTitle(APP_TITLE);
      inputDlg.setHeaderText("Application name wasn't found. Are you sure it's set");
      inputDlg.setContentText("Application Name");

      Optional<String> name = inputDlg.showAndWait();

      name.filter(s -> s != null && !s.isEmpty())
              .ifPresent(s -> {
                PROPERTIES.setProperty(APP_NAME, s);

                try ( OutputStream os = new FileOutputStream(PROPS_FILE)) {
                  LOG.log(Level.INFO, "output stream: {0}", os);

                  PROPERTIES.store(os, null);
                } catch (IOException ex) {
                  LOG.log(Level.SEVERE, null, ex);
                }
              });
    }

    Optional<HttpTransport> transport = empty();

    try {
      transport = Optional.ofNullable(newTrustedTransport());
    } catch (GeneralSecurityException | IOException ex) {
      LOG.log(Level.WARNING, "Failed to initialize HttpTransport. Reason: {0}", ex.getMessage());
    }

    return transport
            .map(trans -> new Builder(trans, getDefaultInstance(), null))
            .map(builder -> builder.setApplicationName(PROPERTIES.getProperty(APP_NAME)).build());
  }

  private List<VideoCategory> getCategories(YouTube yt, String apiKey) {
    List<VideoCategory> categories = emptyList();

    try {
      categories = yt.videoCategories()
              .list("snippet")
              .setRegionCode("US")
              .setKey(apiKey)
              .execute()
              .getItems()
              .stream()
              .collect(toList());
    } catch (IOException ex) {
      LOG.log(Level.WARNING, "Failed to retrieve video categories. Reason: {0}", ex.getMessage());

      if (ex.getMessage().contains("403 Forbidden")) {
        TextInputDialog inputDlg = new TextInputDialog();

        inputDlg.setTitle(APP_TITLE);
        inputDlg.setHeaderText("The request was forbidden. Do you have an API key");
        inputDlg.setContentText("API Key");

        Optional<String> key = inputDlg.showAndWait();

        key.filter(s -> s != null && !s.isEmpty())
                .ifPresent(s -> {
                  PROPERTIES.setProperty(API_KEY, s);

                  try ( OutputStream os = new FileOutputStream(PROPS_FILE)) {
                    LOG.log(Level.INFO, "output stream: {0}", os);

                    PROPERTIES.store(os, null);
                  } catch (IOException ioe) {
                    LOG.log(Level.SEVERE, null, ioe);
                  }
                });

        categories = getCategories(yt, PROPERTIES.getProperty(API_KEY));

      } else if (ex.getMessage().contains("400 Bad Request")) {
        TextInputDialog inputDlg = new TextInputDialog();

        inputDlg.setTitle(APP_TITLE);
        inputDlg.setHeaderText("The request failed. Do you have a valid API key");
        inputDlg.setContentText("API Key");

        Optional<String> key = inputDlg.showAndWait();

        key.filter(s -> s != null && !s.isEmpty())
                .ifPresent(s -> {
                  PROPERTIES.setProperty(API_KEY, s);

                  try ( OutputStream os = new FileOutputStream(PROPS_FILE)) {
                    LOG.log(Level.INFO, "output stream: {0}", os);

                    PROPERTIES.store(os, null);
                  } catch (IOException ioe) {
                    LOG.log(Level.SEVERE, null, ioe);
                  }
                });

        categories = getCategories(yt, PROPERTIES.getProperty(API_KEY));
      }
    }

    return categories;
  }
}
