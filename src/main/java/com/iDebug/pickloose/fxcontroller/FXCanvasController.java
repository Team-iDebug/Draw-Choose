package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.canvas.DrawManager;
import com.iDebug.pickloose.canvas.ResizableCanvas;
import com.iDebug.pickloose.network.SERVICE;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class FXCanvasController {
    @FXML
    Pane canvasContainer;

    @FXML
    HBox undo;
    @FXML
    HBox redo;

    private void networkRequest() {
        try {
            NetworkManager.sendReqAsAuthUser(SERVICE.GET_CANVAS_SOCKET);
        }
        catch (Exception e) {
            System.out.println("Could Not Establish Canvas Socket Connection");
        }
    }

    @FXML
    public void initialize() {
        networkRequest();
        ResizableCanvas canvas = new ResizableCanvas();
        DrawManager.getInstance().setCanvas(canvas);
        DrawManager.getInstance().setCanvas(canvas);
        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.heightProperty().bind(canvasContainer.heightProperty());
        canvasContainer.getChildren().add(canvas);

        canvas.addEventFilter(MouseEvent.ANY, e -> {
            DrawManager.getInstance().listenMouseEvent(e);
        });

        undo.setOnMouseClicked(e -> {
            canvas.undo();
        });
        redo.setOnMouseClicked(e -> {
            canvas.redo();
        });
    }
}
