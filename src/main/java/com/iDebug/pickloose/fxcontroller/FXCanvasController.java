package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.canvas.DrawManager;
import com.iDebug.pickloose.canvas.ResizableCanvas;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class FXCanvasController {
    @FXML
    HBox canvasContainer;

    @FXML
    HBox undo;
    @FXML
    HBox redo;

    @FXML
    public void initialize() {
        ResizableCanvas canvas = new ResizableCanvas(canvasContainer.getWidth(), canvasContainer.getHeight());
        canvasContainer.getChildren().add(canvas.getCanvas());
//         canvasContainer.getParent()
        canvasContainer.widthProperty().addListener(e -> {
            System.out.println("width");
            canvas.draw(canvasContainer.getWidth(),canvasContainer.getHeight());
        });
        canvasContainer.heightProperty().addListener(e -> {
            System.out.println("height");
            canvas.draw(canvasContainer.getWidth(),canvasContainer.getHeight());
        });

        DrawManager.getInstance().setCanvas(canvas.getCanvas());
        canvas.getCanvas().addEventFilter(MouseEvent.ANY, e -> {
            DrawManager.getInstance().listenMouseEvent(e);
        });
        undo.setOnMouseClicked(e -> {
//            DrawManager.getInstance().undo();
            canvas.draw(800,500);
        });
        redo.setOnMouseClicked(e -> {
//            DrawManager.getInstance().redo();
            canvas.draw(500,500);
        });
    }
}
