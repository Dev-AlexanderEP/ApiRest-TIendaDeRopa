package com.ecommerce.server.model.entity;

public record PageResult<T>(
        Iterable<T> data,
        long totalElements,
        int totalPages,
        int pageNumber,
        Integer prev,
        Integer next,
        boolean empty
) {
}


