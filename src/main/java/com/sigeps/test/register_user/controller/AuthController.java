package com.sigeps.test.register_user.controller;

import com.sigeps.test.register_user.dto.AuthResponseDTO;
import com.sigeps.test.register_user.dto.LoginRequestDTO;
import com.sigeps.test.register_user.dto.UsuarioDTO;
import com.sigeps.test.register_user.model.UsuarioModel;
import com.sigeps.test.register_user.service.AuthService;
import com.sigeps.test.register_user.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.authenticateUser(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        if (token != null) {
            return ResponseEntity.ok(new AuthResponseDTO(token, loginRequestDTO.getUsername(), "Login bem-sucedido"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new AuthResponseDTO(null, loginRequestDTO.getUsername(), "Credenciais inválidas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioModel> registerUser(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioModel novoUsuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
}