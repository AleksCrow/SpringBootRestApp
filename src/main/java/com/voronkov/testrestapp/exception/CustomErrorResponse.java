package com.voronkov.testrestapp.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.voronkov.testrestapp.util.View;

import java.sql.Timestamp;

/**Exception entity
 * @author A.Voronkov
 * @since 31.08.2020
 * @version 1.0
 */
public class CustomErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp timestamp;

    @JsonView(View.DisplayExceptionMessage.class)
    private String message;

    public CustomErrorResponse(Timestamp timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
