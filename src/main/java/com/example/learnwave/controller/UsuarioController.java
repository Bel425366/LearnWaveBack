package com.example.learnwave.controller;

import com.example.learnwave.enums.TipoUsuario;
import com.example.learnwave.model.entity.Usuario;
import com.example.learnwave.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // CADASTRAR usuário
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        System.out.println("Received usuario: " + usuario.getNome() + ", tipo: " + usuario.getTipo());
        validarDadosObrigatorios(usuario);
        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(usuarioCriado);
    }

    // PESQUISAR usuários
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Usuario>> buscarPorTipo(@PathVariable TipoUsuario tipo) {
        return ResponseEntity.ok(usuarioService.buscarPorTipo(tipo));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Usuario>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(usuarioService.buscarPorStatus(status));
    }

    // ALTERAR usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        validarDadosObrigatorios(usuario);
        Usuario usuarioAtualizado = usuarioService.atualizar(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable Integer id, @RequestParam String status) {
        if (!"ativo".equals(status) && !"inativo".equals(status)) {
            throw new RuntimeException("Status deve ser 'ativo' ou 'inativo'");
        }
        usuarioService.alterarStatus(id, status);
        return ResponseEntity.ok().build();
    }

    // LOGAR usuário
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String email, @RequestParam String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email e obrigatorio");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new RuntimeException("Senha e obrigatoria");
        }

        Usuario usuario = usuarioService.autenticar(email, senha);
        if (usuario == null) {
            return ResponseEntity.badRequest().build();
        }

        if ("inativo".equals(usuario.getStatus())) {
            throw new RuntimeException("Usuario inativo");
        }

        return ResponseEntity.ok(usuario);
    }

    // APAGAR usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id) {
        if (!usuarioService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    // Operações específicas para professores
    @GetMapping("/professores/pendentes")
    public ResponseEntity<List<Usuario>> listarProfessoresPendentes() {
        return ResponseEntity.ok(usuarioService.listarProfessoresPendentes());
    }

    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<Void> aprovarProfessor(@PathVariable Integer id) {
        if (!usuarioService.aprovarProfessor(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/rejeitar")
    public ResponseEntity<Void> rejeitarProfessor(@PathVariable Integer id) {
        if (!usuarioService.rejeitarProfessor(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    // Rotas específicas baseadas no script SQL
    @GetMapping("/area/{area}")
    public ResponseEntity<List<Usuario>> buscarPorAreaEnsino(@PathVariable String area) {
        return ResponseEntity.ok(usuarioService.buscarPorAreaEnsino(area));
    }

    @GetMapping("/disciplina/{disciplina}")
    public ResponseEntity<List<Usuario>> buscarPorDisciplina(@PathVariable String disciplina) {
        return ResponseEntity.ok(usuarioService.buscarPorDisciplina(disciplina));
    }

    @GetMapping("/escola/{escola}")
    public ResponseEntity<List<Usuario>> buscarPorEscola(@PathVariable String escola) {
        return ResponseEntity.ok(usuarioService.buscarPorEscola(escola));
    }

    @GetMapping("/verificacao/{status}")
    public ResponseEntity<List<Usuario>> buscarPorStatusVerificacao(@PathVariable String status) {
        return ResponseEntity.ok(usuarioService.buscarPorStatusVerificacao(status));
    }

    // Validações
    private void validarDadosObrigatorios(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome e obrigatorio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email e obrigatorio");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new RuntimeException("Senha e obrigatoria");
        }
        if (usuario.getTipo() == null) {
            throw new RuntimeException("Tipo de usuario e obrigatorio");
        }
    }
}