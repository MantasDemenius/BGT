package com.project.bgt.common;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.exception.CustomExceptionHandler;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class LocationHeader {

//  @Autowired
//  private Environment env;
//
//  private String getUrl(){
//    return env.getProperty("url.addr");
//  }
//
//@Profile("dev")
//  public HttpHeaders getLocationHeaders(String path, long id) throws URISyntaxException {
//    HttpHeaders responseHeaders = new HttpHeaders();
////    URI location = new URI(ur+ "/api/cards/" + newCard.getId());
////    URI location = new URI("http://localhost:5000" + path + "/" + id);
//  URI location = new URI( getUrl() + path + "/" + id);
//    responseHeaders.setLocation(location);
//
//    return responseHeaders;
//  }

  public static HttpHeaders getLocationHeaders(String path, long id) {
    try {
      HttpHeaders responseHeaders = new HttpHeaders();
      URI location = new URI("http://localhost:5000" + path + "/" + id);
      responseHeaders.setLocation(location);

      return responseHeaders;
    }catch (URISyntaxException e) {
      System.out.println("URI BAD");
    }
    return null;
  }
//  URI location = ServletUriComponentsBuilder
//    .fromCurrentContextPath().path("/api/users/{username}")
//    .buildAndExpand(result.getUsername()).toUri();
}
