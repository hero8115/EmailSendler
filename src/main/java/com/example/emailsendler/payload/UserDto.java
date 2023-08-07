package com.example.emailsendler.payload;

import com.example.emailsendler.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @NotNull
    private String username;
    @NotNull
    @Size(min = 4,max = 8)
    private String password;
    @NotNull
    private String email;

    private String emailCode;

    private boolean isActive;

    private String rolename;




}
