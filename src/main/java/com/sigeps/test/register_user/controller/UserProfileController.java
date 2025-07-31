package com.sigeps.test.register_user.controller;

import com.sigeps.test.register_user.service.AuthService;
import com.sigeps.test.register_user.service.UsuarioService;
import com.sigeps.test.register_user.exception.UsuarioNaoEncontrado;
import com.sigeps.test.register_user.model.UsuarioModel;
import com.sigeps.test.register_user.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);

        if (authService.authorizeUser(token, "ROLE_USER") || authService.authorizeUser(token, "ROLE_ADMIN")) {
            String username = jwtUtil.getUsernameFromToken(token);
            try {
                UsuarioModel user = usuarioService.usuarioRepository.findByUsername(username)
                                        .orElseThrow(() -> new UsuarioNaoEncontrado("Usuário do token não encontrado no DB."));
                return ResponseEntity.ok("Bem-vindo ao seu perfil de usuário, " + user.getNomeCompleto() + "! (Dados protegidos por papel de usuário/admin)");
            } catch (UsuarioNaoEncontrado e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado: Você não possui o papel necessário para acessar o perfil.");
        }
    }

    @GetMapping("/admin-dashboard")
    public ResponseEntity<String> getAdminDashboard(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);

        if (authService.authorizeUser(token, "ROLE_ADMIN")) {
            return ResponseEntity.ok("Bem-vindo ao Painel de Administração! (Somente para administradores)");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado: Papel de administrador necessário.");
        }
    }
}