package com.github.nduyhai.kcresourceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FailureEvents {

  @EventListener
  public void onFailure(AbstractAuthenticationFailureEvent event) {
    log.warn("onFailure {} - {}", event.getException().getMessage(), event);
  }
}
