package com.example.emailsendler.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginDto {

    private String username;
    private String password;

}
