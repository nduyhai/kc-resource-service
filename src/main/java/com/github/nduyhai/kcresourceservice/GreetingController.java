package com.github.nduyhai.kcresourceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/greeting")
public class GreetingController {
  @GetMapping
  public String greeting(Authentication authentication) {
    log.info("Found login user {}", authentication);
    return "Greetings from Spring Boot!";
  }
}
