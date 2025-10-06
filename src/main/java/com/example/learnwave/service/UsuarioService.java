package com.example.learnwave.service;

import com.example.learnwave.dao.UsuarioDAO;
import com.example.learnwave.enums.StatusVerificacao;
import com.example.learnwave.enums.TipoUsuario;
import com.example.learnwave.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioDAO.existeEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        if (usuario.getCpf() != null && usuarioDAO.existeCpf(usuario.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }

        // Definir valores padrão
        if (usuario.getStatus() == null) {
            usuario.setStatus("ativo");
        }
        if (usuario.getStatusVerificacao() == null) {
            usuario.setStatusVerificacao(StatusVerificacao.PENDENTE);
        }
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setDataAtualizacao(LocalDateTime.now());

        return usuarioDAO.salvar(usuario);
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioDAO.buscarPorId(id);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public List<Usuario> buscarPorTipo(TipoUsuario tipo) {
        return usuarioDAO.buscarPorTipo(tipo);
    }

    public List<Usuario> buscarPorStatus(String status) {
        return usuarioDAO.buscarPorStatus(status);
    }

    public Usuario atualizar(Usuario usuario) {
        Usuario usuarioExistente = usuarioDAO.buscarPorId(usuario.getId());
        if (usuarioExistente == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        // Verificar se email já existe para outro usuário
        Usuario usuarioComEmail = usuarioDAO.buscarPorEmail(usuario.getEmail());
        if (usuarioComEmail != null && !usuarioComEmail.getId().equals(usuario.getId())) {
            throw new RuntimeException("Email já cadastrado para outro usuário");
        }

        usuario.setDataAtualizacao(LocalDateTime.now());
        return usuarioDAO.atualizar(usuario);
    }

    public void alterarStatus(Integer id, String status) {
        Usuario usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        if ("ativo".equals(status)) {
            usuarioDAO.ativarUsuario(id);
        } else {
            usuarioDAO.desativarUsuario(id);
        }
    }

    public boolean deletar(Integer id) {
        return usuarioDAO.deletar(id);
    }

    public List<Usuario> listarProfessoresPendentes() {
        return usuarioDAO.buscarPorStatusVerificacao(StatusVerificacao.PENDENTE);
    }

    public boolean aprovarProfessor(Integer id) {
        return usuarioDAO.aprovarProfessor(id);
    }

    public boolean rejeitarProfessor(Integer id) {
        return usuarioDAO.rejeitarProfessor(id);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario != null && verificarSenha(senha, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    private boolean verificarSenha(String senhaPlana, String senhaHash) {
        // TODO: Implementar verificação de hash da senha (BCrypt, etc.)
        // Por enquanto, comparação simples para desenvolvimento
        return senhaPlana.equals(senhaHash);
    }

    public List<Usuario> buscarPorAreaEnsino(String area) {
        return usuarioDAO.buscarPorAreaEnsino(area);
    }

    public List<Usuario> buscarPorDisciplina(String disciplina) {
        return usuarioDAO.buscarPorDisciplina(disciplina);
    }

    public List<Usuario> buscarPorEscola(String escola) {
        return usuarioDAO.buscarPorEscola(escola);
    }

    public List<Usuario> buscarPorStatusVerificacao(String status) {
        return usuarioDAO.buscarPorStatusVerificacao(status);
    }
}
