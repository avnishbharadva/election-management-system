package com.ems.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Named("multipartFileToString")
    default String multipartFileToString(MultipartFile file){
        if(file == null || file.isEmpty()){
            return null;
        }
        try {
            return Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException ex){
            throw new RuntimeException("Error converting file to Base64 String", ex);
        }
    }
}
