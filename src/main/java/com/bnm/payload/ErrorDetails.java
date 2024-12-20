package com.bnm.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class ErrorDetails {

    private Date date;
    private String message;
    private String request;

    public ErrorDetails(Date date, String message, String request) {
        this.date = date;
        this.message = message;
        this.request = request;
    }
}
