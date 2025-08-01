package com.sigeps.test.register_user.controller;

import com.sigeps.test.register_user.dto.UsuarioDTO;
import com.sigeps.test.register_user.model.UsuarioModel;
import com.sigeps.test.register_user.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuario")
public class UsuarioController {

    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }


    @Operation(summary = "Cria um novo usuário", description = "Adiciona um novo usuário ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário CRIADO com SUCESSO!",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida!")
    })
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){

            UsuarioDTO novoUsuario = usuarioService.cadastrarUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);

    }

    @Operation(summary = "Lista todos os Usuários", description = "Retorna uma lista de TODOS os usuários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornados com SUCESSO!",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado")
    })
    @GetMapping({"","/"})
    // @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')") // Admin ou SuperAdmin podem listar
    public ResponseEntity<List<UsuarioDTO>> listarAllUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Busca usuário pelo ID", description = "Retorna um usuários específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário NÃO encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioByID(@Parameter(description = "ID do usuário a ser BUSCADO") @PathVariable Long id){
       UsuarioDTO usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioByCpf(@PathVariable String cpf){
        UsuarioDTO usuario = usuarioService.buscarUsuarioPorCPF(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @Operation(summary = "Atualizar usuário pelo ID", description = "Atualiza informações do usuário pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com SUCESSO!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário NÃO encontrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida!")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuarioById(@Parameter(description = "ID do usuário a ser ATUALIZADO") @PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO){
        UsuarioDTO usuario = usuarioService.atualizarPessoa(id, usuarioDTO);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @Operation(summary = "Busca usuário pelo ID", description = "Retorna um usuários específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com SUCESSO!"),
            @ApiResponse(responseCode = "404", description = "Usuário NÃO encontrado!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioById(@Parameter(description = "ID do usuário a ser DELETADO") @PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }



}
