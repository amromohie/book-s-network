package com.codex.booksnetwork.auth.controller;

import com.codex.booksnetwork.auth.request.RegistrationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
@Tag(name = "AuthController")
public class AuthController {
  @PostMapping(path = "")
  public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {

  }
}
