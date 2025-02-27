package com.ems.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO {

    private String message;
    private Object data;
    private LocalDateTime timeStamp;
    private boolean isSuccess;
}
