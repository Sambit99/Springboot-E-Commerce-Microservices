package com.app.microservices.product.dto;

import org.bson.types.ObjectId;

import java.math.BigDecimal;

public record ProductResponse(ObjectId id, String name, String description, BigDecimal price) {
}
