package org.learning.todo.controller;

import org.learning.todo.controller.request.UserRegistrationRequest;
import org.learning.todo.service.AppUserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AppUserDetailService appUserDetailService;

  public AuthController(AppUserDetailService appUserDetailService) {
    this.appUserDetailService = appUserDetailService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request){
    this.appUserDetailService.registerUser(request);
    return ResponseEntity.ok().build();
  }
}
