package com.github.nduyhai.kcresourceservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
  @GetMapping
  public String greeting() {
    return "Greetings from Spring Boot!";
  }
}
