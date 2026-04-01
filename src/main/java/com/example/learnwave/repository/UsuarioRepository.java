package com.example.learnwave.repository;

import com.example.learnwave.enums.StatusVerificacao;
import com.example.learnwave.enums.TipoUsuario;
import com.example.learnwave.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);
    List<Usuario> findByTipo(TipoUsuario tipo);
    List<Usuario> findByStatus(String status);
    List<Usuario> findByStatusVerificacao(StatusVerificacao statusVerificacao);
    List<Usuario> findByTipoAndStatusVerificacao(TipoUsuario tipo, StatusVerificacao statusVerificacao);
    List<Usuario> findByAreaEnsino(String areaEnsino);
    List<Usuario> findByEscola(String escola);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}