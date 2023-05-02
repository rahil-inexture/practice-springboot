package com.spring.practice.entity;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class DateAudit {
    @CreatedDate
    @Column(name = "creation_date")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}