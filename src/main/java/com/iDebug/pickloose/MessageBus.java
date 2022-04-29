package com.iDebug.pickloose;

import com.iDebug.pickloose.network.Request;

import java.util.LinkedList;
import java.util.Queue;

public class MessageBus {
    private static MessageBus messageBus;
    Queue<Request> messages;
    private MessageBus() {
        messages = new LinkedList<>();
    }
    public static MessageBus getInstance() {
        if(messageBus == null)
            messageBus = new MessageBus();
        return messageBus;
    }
    public void add(Request request) {
        messages.add(request);
    }
    public Request peek() {
        return messages.peek();
    }
    public Request poll() {
        return messages.poll();
    }
    public void pop() {
        messages.remove();
    }
    public int size() {
        return messages.size();
    }
}
