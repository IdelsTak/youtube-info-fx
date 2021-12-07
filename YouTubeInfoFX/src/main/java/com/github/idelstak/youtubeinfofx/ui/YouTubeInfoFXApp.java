/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.ui;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class YouTubeInfoFXApp extends Application {

  private static final Logger LOG = Logger.getLogger(YouTubeInfoFXApp.class.getName());

  @Override
  public void start(Stage stage) throws Exception {

    InputStream propsStream = YouTubeInfoFXApp.class.getResourceAsStream("/project/properties/youtube.properties");

    LOG.log(Level.INFO, "props{0}", propsStream);

    Properties props = new Properties();

    props.load(propsStream);

    String appName = props.getProperty("application.name");
    String apiKey = props.getProperty("api.key");

    Scene scene;

    if (appName == null || apiKey == null) {
      URL resource = YouTubeInfoFXApp.class.getResource("/fxml/api-key-dialog.fxml");
      FXMLLoader loader = new FXMLLoader(resource);

      Parent root = loader.load();
      scene = new Scene(root);
    } else {
      BorderPane pane = new BorderPane(new Label("YouTubeInfoFX"));
      scene = new Scene(pane);
    }

    stage.setScene(scene);
    stage.setTitle("YouTubeInfoFX");

    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
