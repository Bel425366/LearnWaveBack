package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.UsuarioDAO;
import com.example.learnwave.enums.StatusVerificacao;
import com.example.learnwave.enums.TipoUsuario;
import com.example.learnwave.model.entity.Usuario;
import com.example.learnwave.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Executa uma operação de banco com retry automático.
     * O Somee (plano gratuito) às vezes fecha a conexão no meio da operação
     * ("Connection is closed"). Quando isso acontece, tentamos de novo:
     * o Hikari descarta a conexão morta e entrega uma nova.
     */
    private <T> T comRetry(java.util.function.Supplier<T> operacao) {
        int tentativas = 0;
        int maxTentativas = 4;
        RuntimeException ultimoErro = null;
        while (tentativas < maxTentativas) {
            try {
                return operacao.get();
            } catch (RuntimeException e) {
                ultimoErro = e;
                if (ehErroDeConexao(e)) {
                    tentativas++;
                    System.err.println("Conexao com o banco falhou (tentativa " + tentativas + "/" + maxTentativas + "). Tentando novamente...");
                    try {
                        Thread.sleep(800L * tentativas);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e; // erro que não é de conexão (ex: email duplicado) — não repete
                }
            }
        }
        throw ultimoErro;
    }

    /**
     * Verifica se a exceção (ou alguma de suas causas) é um problema de conexão fechada/derrubada.
     */
    private boolean ehErroDeConexao(Throwable e) {
        Throwable atual = e;
        while (atual != null) {
            String msg = atual.getMessage();
            if (msg != null) {
                String m = msg.toLowerCase();
                if (m.contains("connection is closed") ||
                    m.contains("unable to rollback") ||
                    m.contains("unable to acquire") ||
                    m.contains("connection is not available") ||
                    m.contains("the connection is closed") ||
                    m.contains("connection reset") ||
                    m.contains("broken pipe") ||
                    m.contains("socket") ||
                    m.contains("i/o error")) {
                    return true;
                }
            }
            atual = atual.getCause();
        }
        return false;
    }

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
        
        return comRetry(() -> usuarioRepository.save(usuario));
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return comRetry(() -> usuarioRepository.findById(id).orElse(null));
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return comRetry(() -> usuarioRepository.findByEmail(email));
    }

    @Override
    public List<Usuario> listarTodos() {
        return comRetry(() -> usuarioRepository.findAll());
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        return comRetry(() -> usuarioRepository.save(usuario));
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
        return comRetry(() -> usuarioRepository.existsByEmail(email));
    }

    @Override
    public boolean existeCpf(String cpf) {
        return comRetry(() -> usuarioRepository.existsByCpf(cpf));
    }

    @Override
    @Transactional
    public boolean aprovarProfessor(Integer id) {
        System.out.println("Aprovando professor com ID: " + id);
        if (!usuarioRepository.existsById(id)) {
            System.out.println("Usuario nao encontrado com ID: " + id);
            return false;
        }
        int rows = usuarioRepository.updateStatusVerificacaoEAtivo(id, StatusVerificacao.APROVADO);
        System.out.println("Rows atualizadas na aprovação: " + rows);
        return rows > 0;
    }

    @Override
    @Transactional
    public boolean rejeitarProfessor(Integer id) {
        System.out.println("DAO: Rejeitando professor ID: " + id);
        if (!usuarioRepository.existsById(id)) {
            System.out.println("DAO: Usuario não encontrado com ID: " + id);
            return false;
        }
        int rows = usuarioRepository.updateStatusVerificacao(id, StatusVerificacao.REJEITADO);
        System.out.println("DAO: Rows atualizadas na rejeição: " + rows);
        return rows > 0;
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

    @Override
    public Usuario buscarPorResetToken(String resetToken) {
        return usuarioRepository.findByResetToken(resetToken);
    }
}