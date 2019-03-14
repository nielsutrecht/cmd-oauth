package com.nibado.cmdoauth;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.function.Consumer;

public class Server extends NanoHTTPD {
    private final Consumer<String> callback;

    public Server(Consumer<String> callback) {
        super(0);
        this.callback = callback;
    }

    public void start() throws IOException {
        super.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        callback.accept(session.getQueryParameterString());
        String msg = "<html><body><h1>Hello server</h1></body></html>\n";

        return newFixedLengthResponse(msg);
    }
}
