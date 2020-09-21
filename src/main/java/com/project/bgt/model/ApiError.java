package com.project.bgt.model;

import com.project.bgt.common.constant.DateFormatConst;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class ApiError{

  String message;
  String timestamp = DateFormatConst.sdf.format(new Timestamp(System.currentTimeMillis()));

  public ApiError(String message){
    this.message = message;
  }

  public ApiError(){
  }

}