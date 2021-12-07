/*
 * Copyright (C) 2021
 */
package com.github.idelstak.youtubeinfofx.ui.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javafx.scene.control.ButtonType.FINISH;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class APIKeyDialogController {

  private static final Logger LOG = Logger.getLogger(APIKeyDialogController.class.getName());
  @FXML
  private DialogPane dlgPane;
  @FXML
  private TextField applicationNameTxtFld;
  @FXML
  private TextField apiKeyTxtFld;

  public void setAppName(String appName) {
    applicationNameTxtFld.setText(appName);
  }

  public void setApiKey(String apiKey) {
    apiKeyTxtFld.setText(apiKey);
  }

  @FXML
  void initialize() {
    applicationNameTxtFld
            .skinProperty()
            .addListener((obs) -> {
              applicationNameTxtFld.requestFocus();
              applicationNameTxtFld.selectAll();
            });

    Button save = (Button) dlgPane.lookupButton(FINISH);
    save.setText("Save Properties");

    save.disableProperty().bind(applicationNameTxtFld.textProperty().isEmpty().or(apiKeyTxtFld.textProperty().isEmpty()));

    save.setOnAction(ev -> {

      Node source = (Node) ev.getSource();
      Stage stage = (Stage) source.getScene().getWindow();

      stage.close();
    });
  }
}
