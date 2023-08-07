package com.example.emailsendler.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private boolean status;
    private String token;
    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }


}
