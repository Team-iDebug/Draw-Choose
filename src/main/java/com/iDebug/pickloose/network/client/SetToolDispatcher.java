package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.canvas.*;
import com.iDebug.pickloose.network.Response;

public class SetToolDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        switch (TOOLS.valueOf(response.getBody())) {
            case BRUSH -> DrawManager.getInstance().setSelectedTool(new OilBrush());
            case PENCIL -> DrawManager.getInstance().setSelectedTool(new NaturalPencil());
            case SPRAY -> DrawManager.getInstance().setSelectedTool(new Spray());
            case FILL -> DrawManager.getInstance().setSelectedTool(new Fill());
            case ERASER -> DrawManager.getInstance().setSelectedTool(new Eraser());
        }
    }
}
