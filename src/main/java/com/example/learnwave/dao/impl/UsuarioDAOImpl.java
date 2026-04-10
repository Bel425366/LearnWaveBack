package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.UsuarioDAO;
import com.example.learnwave.enums.StatusVerificacao;
import com.example.learnwave.enums.TipoUsuario;
import com.example.learnwave.model.entity.Usuario;
import com.example.learnwave.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario salvar(Usuario usuario) {
        // Forçar status baseado no tipo
        if (TipoUsuario.ALUNO.equals(usuario.getTipo()) || 
            TipoUsuario.ESTUDANTE.equals(usuario.getTipo()) || 
            TipoUsuario.ADMIN.equals(usuario.getTipo()) || 
            TipoUsuario.ADMINISTRADOR.equals(usuario.getTipo())) {
            usuario.setStatusVerificacao(StatusVerificacao.APROVADO);
        } else if (TipoUsuario.PROFESSOR.equals(usuario.getTipo())) {
            usuario.setStatusVerificacao(StatusVerificacao.PENDENTE);
        }
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public boolean deletar(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Usuario> buscarPorTipo(TipoUsuario tipo) {
        return usuarioRepository.findByTipo(tipo);
    }

    @Override
    public List<Usuario> buscarPorStatus(String status) {
        return usuarioRepository.findByStatus(status);
    }

    @Override
    public List<Usuario> buscarPorStatusVerificacao(StatusVerificacao status) {
        return usuarioRepository.findByStatusVerificacao(status);
    }

    @Override
    public List<Usuario> buscarProfessoresPorArea(String area) {
        return new ArrayList<>();
    }

    public List<Usuario> buscarProfessoresPendentes() {
        return usuarioRepository.findByTipoAndStatusVerificacao(TipoUsuario.PROFESSOR, StatusVerificacao.PENDENTE);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean existeCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    @Override
    public boolean aprovarProfessor(Integer id) {
        System.out.println("Aprovando professor com ID: " + id);
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getEmail() + ", Status atual: " + usuario.getStatusVerificacao());
            usuario.setStatusVerificacao(StatusVerificacao.APROVADO);
            usuario.setStatus("ativo");
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            System.out.println("Usuario salvo com status: " + usuarioSalvo.getStatusVerificacao() + ", ativo: " + usuarioSalvo.getStatus());
            return true;
        }
        System.out.println("Usuario nao encontrado com ID: " + id);
        return false;
    }

    @Override
    public boolean rejeitarProfessor(Integer id) {
        System.out.println("DAO: Rejeitando professor ID: " + id);
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setStatusVerificacao(StatusVerificacao.REJEITADO);
            usuarioRepository.save(usuario);
            System.out.println("DAO: Professor rejeitado ID: " + id);
            return true;
        }
        System.out.println("DAO: Usuario não encontrado com ID: " + id);
        return false;
    }

    @Override
    public void atualizarNome(Integer id, String nome) {
        usuarioRepository.updateNome(id, nome);
    }

    @Override
    public void atualizarSenha(Integer id, String novaSenha) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setSenha(novaSenha);
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public boolean ativarUsuario(Integer id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setStatus("ativo");
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    @Override
    public boolean desativarUsuario(Integer id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setStatus("inativo");
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    @Override
    public long contarUsuariosPorTipo(TipoUsuario tipo) {
        return 0;
    }

    @Override
    public long contarProfessoresPendentes() {
        return 0;
    }

    @Override
    public List<Usuario> buscarPorAreaEnsino(String area) {
        return usuarioRepository.findByAreaEnsino(area);
    }

    @Override
    public List<Usuario> buscarPorEscola(String escola) {
        return usuarioRepository.findByEscola(escola);
    }

    @Override
    public List<Usuario> buscarPorStatusVerificacao(String status) {
        try {
            StatusVerificacao statusEnum = StatusVerificacao.valueOf(status.toUpperCase());
            return usuarioRepository.findByStatusVerificacao(statusEnum);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }
}