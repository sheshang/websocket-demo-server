package com.codeforgeyt.wschatapplication.controller;

import com.codeforgeyt.wschatapplication.handler.RestSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import java.io.IOException;
import java.util.stream.Stream;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RequestMapping("/")
@RestController("streamRestController")
public class StreamRestController {

    @Autowired
    RestSocketHandler getRestSocketHandler;

    @GetMapping("ping")
    String ping() {
        return "pong";
    }

    @GetMapping("stream/int")
    boolean streamIntegers() {

        Stream.of(1, 2, 3).forEach(i -> {
            try {
                String payload = "{\"user\":\"Server\",\"message\":" + i + "}";
                getRestSocketHandler.sendMessage(new TextMessage(payload));
                Thread.sleep(2000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        return true;
    }
}
