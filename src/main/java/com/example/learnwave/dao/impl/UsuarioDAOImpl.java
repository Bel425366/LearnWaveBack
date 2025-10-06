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
        return true;
    }

    @Override
    public boolean rejeitarProfessor(Integer id) {
        return true;
    }

    @Override
    public boolean ativarUsuario(Integer id) {
        return true;
    }

    @Override
    public boolean desativarUsuario(Integer id) {
        return true;
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
    public List<Usuario> buscarPorDisciplina(String disciplina) {
        return usuarioRepository.findByDisciplina(disciplina);
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