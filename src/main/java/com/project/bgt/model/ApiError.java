package com.project.bgt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.bgt.common.constant.DateFormatConst;
import com.project.bgt.common.message.ErrorMessages;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiError{

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private HttpStatus status;
  private List<String> errors;
}
