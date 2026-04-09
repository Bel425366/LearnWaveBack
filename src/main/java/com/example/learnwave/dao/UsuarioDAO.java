package com.example.learnwave.dao;


import com.example.learnwave.enums.StatusVerificacao;
import com.example.learnwave.enums.TipoUsuario;
import com.example.learnwave.model.entity.Usuario;

import java.util.List;

public interface UsuarioDAO {

    // Operações CRUD básicas
    Usuario salvar(Usuario usuario);
    Usuario buscarPorId(Integer id);
    Usuario buscarPorEmail(String email);
    List<Usuario> listarTodos();
    Usuario atualizar(Usuario usuario);
    boolean deletar(Integer id);

    // Consultas específicas
    List<Usuario> buscarPorTipo(TipoUsuario tipo);
    List<Usuario> buscarPorStatus(String status);
    List<Usuario> buscarPorStatusVerificacao(StatusVerificacao status);
    List<Usuario> buscarProfessoresPorArea(String area);
    boolean existeEmail(String email);
    boolean existeCpf(String cpf);

    // Operações de verificação
    boolean aprovarProfessor(Integer id);
    boolean rejeitarProfessor(Integer id);
    boolean ativarUsuario(Integer id);
    boolean desativarUsuario(Integer id);
    void atualizarNome(Integer id, String nome);

    // Estatísticas
    long contarUsuariosPorTipo(TipoUsuario tipo);
    long contarProfessoresPendentes();
    
    // Buscar professores pendentes
    List<Usuario> buscarProfessoresPendentes();

    // Consultas específicas por campos
    List<Usuario> buscarPorAreaEnsino(String area);
    List<Usuario> buscarPorEscola(String escola);
    List<Usuario> buscarPorStatusVerificacao(String status);
}
