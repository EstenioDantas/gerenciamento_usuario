package com.sigeps.test.register_user.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "O username é obrigatório")
    private String username;
    @NotBlank(message = "A senha é obrigatória")
    private String password;
}