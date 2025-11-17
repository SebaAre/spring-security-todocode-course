package com.sebastianarellano.blogsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @GetMapping("/success")
    public Map<String, String> oauth2Success(@RequestParam String token) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "OAuth2 login successful");
        response.put("jwt", token);
        response.put("status", "success");
        return response;
    }
}