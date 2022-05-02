package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.canvas.*;
import com.iDebug.pickloose.network.SERVICE;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class FXCanvasController {
    @FXML
    private Pane canvasContainer;

    @FXML
    private HBox undo;
    @FXML
    private HBox redo;
    @FXML
    private HBox FXPencil;
    @FXML
    private HBox FXBrush;
    @FXML
    private HBox FXEraser;
    @FXML
    private HBox FXSpray;
    @FXML
    private HBox FXFill;
    @FXML
    private HBox FXClearCanvas;

    private void networkRequest() {
        try {
            NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.GET_CANVAS_SOCKET);
        }
        catch (Exception e) {
            System.out.println("Could Not Establish Canvas Socket Connection");
        }
    }

    public void setupTools() {
        HBox[] tools = {FXPencil,FXBrush,FXEraser,FXSpray,FXFill};
        HashMap<HBox,Tool> toolMap = new HashMap<>();
        toolMap.put(FXPencil,new NaturalPencil());
        toolMap.put(FXBrush,new OilBrush());
        toolMap.put(FXEraser,new Eraser());
        toolMap.put(FXSpray, new Spray());
        toolMap.put(FXFill,new Fill());

        for(var tool : tools) {
            tool.setOnMouseClicked(e -> {
                DrawManager.getInstance().setSelectedTool(toolMap.get(tool));
            });
        }

        FXClearCanvas.setOnMouseClicked(e -> {
            DrawManager.getInstance().clearCanvas();
        });
    }

    @FXML
    public void initialize() {
        networkRequest();
        setupTools();

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
