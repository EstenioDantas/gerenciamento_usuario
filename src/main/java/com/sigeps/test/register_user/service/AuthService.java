package com.sigeps.test.register_user.service;

import com.sigeps.test.register_user.model.UsuarioModel;
import com.sigeps.test.register_user.repository.IUsuarioRepository;
import com.sigeps.test.register_user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticateUser(String username, String password) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByUsername(username);

        if (usuarioOptional.isPresent() && usuarioOptional.get().getPassword().equals(password)) {
            UsuarioModel usuario = usuarioOptional.get();
            return jwtUtil.generateToken(usuario.getUsername(), usuario.getRoles());
        }
        return null;
    }

    public boolean authorizeUser(String token, String requiredRole) {
        if (jwtUtil.validateToken(token)) {
            Set<String> userRoles = jwtUtil.getRolesFromToken(token);
            return userRoles.contains(requiredRole);
        }
        return false;
    }
}