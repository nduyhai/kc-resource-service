package com.github.nduyhai.kcresourceservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {
  private static final String CONTENT_SECURITY_POLICY =
      """
      default-src 'self'; frame-src 'self' data:; \
      script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; \
      style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; \
      img-src 'self' data:; \
      font-src 'self' data: https://fonts.gstatic.com;\
      """;

  @Bean
  public SecurityFilterChain jwtFilterChain(
      HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .headers(
            headers ->
                headers
                    .contentSecurityPolicy(csp -> csp.policyDirectives(CONTENT_SECURITY_POLICY))
                    .frameOptions(FrameOptionsConfig::deny)
                    .referrerPolicy(
                        referrer ->
                            referrer.policy(
                                ReferrerPolicyHeaderWriter.ReferrerPolicy
                                    .STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                    .permissionsPolicy(
                        permissions ->
                            permissions.policy(
                                "camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authz ->
                authz
                    .requestMatchers(new MvcRequestMatcher(introspector, "/actuator/**"))
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

    return http.build();
  }
}
