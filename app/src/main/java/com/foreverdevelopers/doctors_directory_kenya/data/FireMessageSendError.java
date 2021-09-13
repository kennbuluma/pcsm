package com.foreverdevelopers.doctors_directory_kenya.data;

public class FireMessageSendError {
    String errorMessage;
    Exception exception;
    public FireMessageSendError(){}
    public FireMessageSendError(String errorMessage, Exception exception){
        this.errorMessage = errorMessage;
        this.exception = exception;
    }
}
