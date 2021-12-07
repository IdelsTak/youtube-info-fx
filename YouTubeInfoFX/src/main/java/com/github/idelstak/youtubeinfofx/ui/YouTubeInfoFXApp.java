/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.ui;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class YouTubeInfoFXApp extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    URL resource = YouTubeInfoFXApp.class.getResource("/fxml/api-key-dialog.fxml");
    FXMLLoader loader = new FXMLLoader(resource);

    Parent root = loader.load();

    stage.setScene(new Scene(root));
    stage.setTitle("YouTubeInfoFX");

    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
