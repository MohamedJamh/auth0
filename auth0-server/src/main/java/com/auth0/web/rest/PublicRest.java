package com.auth0.web.rest;

import com.auth0.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class PublicRest {

    @GetMapping("/hello")
    public ResponseEntity<Response<String>> hello() {
        Response<String> response = new Response<>();
        response.setMessage("Hello World");
        return ResponseEntity.ok().body(response);
    }
}
