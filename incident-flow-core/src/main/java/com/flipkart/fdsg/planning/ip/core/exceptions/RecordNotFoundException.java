package com.flipkart.fdsg.planning.ip.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecordNotFoundException extends RuntimeException {
    private final Class clazz;
    private final String id;
    private String message;

    public RecordNotFoundException(Class clazz, String id) {
        super(String.format("Record of class %s and id %s not found", clazz.getName(), id));
        this.clazz = clazz;
        this.id = id;
        this.message = super.getMessage();
    }
}
