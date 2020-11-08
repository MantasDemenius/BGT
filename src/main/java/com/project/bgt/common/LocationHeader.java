package com.project.bgt.common;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class LocationHeader {

  public static HttpHeaders getLocationHeaders(String path, long id) {
    HttpHeaders responseHeaders = new HttpHeaders();
    URI location = ServletUriComponentsBuilder
      .fromCurrentContextPath()
      .path(path + "/" + id)
      .build()
      .toUri();
    responseHeaders.setLocation(location);
    return responseHeaders;
  }
}
