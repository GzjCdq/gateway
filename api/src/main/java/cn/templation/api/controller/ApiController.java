package cn.templation.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiController {

    @GetMapping("/deliver")
    public String getText() {
        return "Hello, Deliver Team.";
    }
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World.";
    }

}
