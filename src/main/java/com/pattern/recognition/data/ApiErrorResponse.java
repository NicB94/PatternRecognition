package com.pattern.recognition.data;

public class ApiErrorResponse {

    private String errorDescription;

    public ApiErrorResponse(String errorDescription){
        this.errorDescription = errorDescription;
    }
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
