package com.iDebug.pickloose.fxcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FXGameWordController {
    @FXML
    HBox FXWord;

    static HBox GUIWord;

    public static void setWord(String word) {
        GUIWord.getChildren().clear();
        for(int i = 0 ; i < word.length() ; i++) {
            HBox letterBox = new HBox();
            letterBox.getStyleClass().add("word-box");
            Label letter = new Label();
            letter.setText(""+word.charAt(i));
            letter.getStyleClass().add("word-text");
            letterBox.getChildren().add(letter);
            GUIWord.getChildren().add(letterBox);
        }
    }

    public static void setAnonymousWord(int length) {
        GUIWord.getChildren().clear();
        for(int i = 0 ; i < length ; i++) {
            HBox letterBox = new HBox();
            letterBox.getStyleClass().add("word-box-empty");
            GUIWord.getChildren().add(letterBox);
        }
    }

    @FXML
    public void initialize() {
        GUIWord = FXWord;
    }
}
