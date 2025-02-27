package com.ems.dtos;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseDTO {

    private String message;
    private Object data;
    private LocalDateTime timeStamp;
}
