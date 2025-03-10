package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class TimeStamp {
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdBy = getCurrentUser();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedBy = getCurrentUser();
    }

    private String getCurrentUser(){
        try{
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }catch (Exception e){
            return "SYSTEM";
        }
    }
}
