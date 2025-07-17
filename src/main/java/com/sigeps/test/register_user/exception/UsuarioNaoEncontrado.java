package com.sigeps.test.register_user.exception;

public class UsuarioNaoEncontrado extends RuntimeException {
    public UsuarioNaoEncontrado() {
        super("Usuário não encontrado!");
    }

    public UsuarioNaoEncontrado(String message){
        super(message);
    }
}
