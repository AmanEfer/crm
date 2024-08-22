package com.amanefer.crm.exceptions;

public class TaskForbiddenOperationException extends RuntimeException {

    public TaskForbiddenOperationException(String message) {
        super(message);
    }
}
