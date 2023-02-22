package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testJdk11APIs();

        try {
            runJetty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void testJdk11APIs() {
        String blank = "";
        if (blank.isBlank()) System.out.println("The string is blank");
    }

    private void runJetty() throws Exception {
        // Create and configure a ThreadPool.
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("server");

        // Create a Server instance.
        Server server = new Server(threadPool);

        // Create a ServerConnector to accept connections from clients.
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);

        // Add the Connector to the Server
        server.addConnector(connector);

        // Set a simple Handler to handle requests/responses.
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request jettyRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                // Mark the request as handled by this Handler.
                jettyRequest.setHandled(true);

                response.setStatus(200);
                response.setContentType("text/html; charset=UTF-8");

                // Write a Hello World response.
                response.getWriter().print("" + "<!DOCTYPE html>" + "<html>" + "<head>" + "  <title>Jetty Hello World Handler</title>" + "</head>" + "<body>" + "  <p>Hello World</p>" + "</body>" + "</html>" + "");
            }
        });

        // Start the Server so it starts accepting connections from clients.
        server.start();
    }
}