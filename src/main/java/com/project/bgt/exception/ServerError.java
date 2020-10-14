package com.project.bgt.exception;

import java.util.List;
import lombok.Getter;

@Getter
public class ServerError {

  private String header;

  private List<String> items;

  public ServerError() {
  }

  public ServerError(String header, List<String> items) {
    this.header = header;
    this.items = items;
  }


}
