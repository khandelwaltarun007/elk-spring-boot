package com.javalabs.userserviceelkstack.exception;

public interface ErrorMessage {
    String getCode();

    String getStatus();

    String getReason();

    String getErrorMessage();
}
