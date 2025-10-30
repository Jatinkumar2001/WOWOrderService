package com.enterprisex.wallsofwonder.oms.Entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.sql.Timestamp;

@MappedSuperclass
@Data
public class AbstractEntity {
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
