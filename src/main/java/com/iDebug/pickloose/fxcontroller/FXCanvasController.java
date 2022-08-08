package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.GameManager;
import com.iDebug.pickloose.GameSettings;
import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.WindowManager;
import com.iDebug.pickloose.canvas.*;
import com.iDebug.pickloose.network.SERVICE;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FXCanvasController {
    static Label TIMER;
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
    @FXML
    private ColorPicker FXColorPicker;
    @FXML
    private Label FXTimer;

    private void networkRequest() {
        try {
            NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.GET_CANVAS_SOCKET);
        } catch (Exception e) {
            System.out.println("Could Not Establish Canvas Socket Connection");
        }
    }

    public void setupTools() {
        HBox[] tools = {FXPencil, FXBrush, FXEraser, FXSpray, FXFill};
        HashMap<HBox, Tool> toolMap = new HashMap<>();
        HashMap<HBox, TOOLS> toolEnum = new HashMap<>();

        toolMap.put(FXPencil, new NaturalPencil());
        toolMap.put(FXBrush, new OilBrush());
        toolMap.put(FXEraser, new Eraser());
        toolMap.put(FXSpray, new Spray());
        toolMap.put(FXFill, new Fill());

        toolEnum.put(FXPencil, TOOLS.PENCIL);
        toolEnum.put(FXBrush, TOOLS.BRUSH);
        toolEnum.put(FXEraser, TOOLS.ERASER);
        toolEnum.put(FXSpray, TOOLS.SPRAY);
        toolEnum.put(FXFill, TOOLS.FILL);

        for (var tool : tools) {
            tool.setOnMousePressed(e -> {
                DrawManager.getInstance().setSelectedTool(toolMap.get(tool));
                NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.SET_TOOL, toolEnum.get(tool).toString());
            });
        }

        FXClearCanvas.setOnMousePressed(e -> {
            DrawManager.getInstance().clearCanvas();
            NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.CLEAR_CANVAS);
        });

        FXColorPicker.setOnAction(e -> {
            Color color = FXColorPicker.getValue();
            DrawManager.getInstance().setSelectedColor(color);
            NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.SET_COLOR, color.toString());
        });
    }

    @FXML
    public void initialize() {
        // static variables initialization
        TIMER = FXTimer;
        GameManager.getInstance().setGUITimer(TIMER);

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
