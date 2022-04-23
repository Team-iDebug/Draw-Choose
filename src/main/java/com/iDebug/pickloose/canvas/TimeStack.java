package com.iDebug.pickloose.canvas;

import java.util.Stack;

class TimeStack<T> {
    private Stack<T> undoStack;
    private Stack<T> redoStack;
    public TimeStack() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    public void forward(T obj) {
        redoStack.clear();
        undoStack.push(obj);
    }
    public T forward() {
        if(redoStack.empty())
            return null;
        undoStack.push(redoStack.pop());
        return undoStack.peek();
    }
    public T backward() {
        if(undoStack.empty())
            return null;
        else {
            redoStack.push(undoStack.pop());
            if(undoStack.empty())
                return null;
            return undoStack.peek();
        }
    }
}
