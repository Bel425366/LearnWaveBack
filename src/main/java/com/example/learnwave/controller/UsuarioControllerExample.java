package com.example.learnwave.controller;

import com.example.learnwave.enums.TipoUsuario;
import com.example.learnwave.model.entity.Usuario;
import org.springframework.http.ResponseEntity;

/**
 * Exemplo de uso do UsuarioController
 * Demonstra todas as operações CRUD disponíveis
 */
public class UsuarioControllerExample {

    private UsuarioController usuarioController;

    public UsuarioControllerExample(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void exemploOperacoes() {
        try {
            // 1. CADASTRAR usuário
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome("João Silva");
            novoUsuario.setEmail("joao@email.com");
            novoUsuario.setSenha("123456");
            novoUsuario.setTipo(TipoUsuario.ALUNO);
            novoUsuario.setCpf("123.456.789-00");
            novoUsuario.setTelefone("(11) 99999-9999");

            var response = usuarioController.cadastrarUsuario(novoUsuario);
            Usuario usuarioCadastrado = response.getBody();
            System.out.println("Usuário cadastrado: " + usuarioCadastrado.getNome());

            // 2. PESQUISAR usuários
            var responseId = usuarioController.buscarPorId(usuarioCadastrado.getId());
            Usuario usuarioEncontrado = responseId.getBody();
            System.out.println("Usuário encontrado por ID: " + usuarioEncontrado.getNome());

            var responseEmail = usuarioController.buscarPorEmail("joao@email.com");
            Usuario usuarioPorEmail = responseEmail.getBody();
            System.out.println("Usuário encontrado por email: " + usuarioPorEmail.getNome());

            // Listar todos os usuários
            var todosUsuarios = usuarioController.listarTodos().getBody();
            System.out.println("Total de usuários: " + todosUsuarios.size());

            // Buscar por tipo
            var alunos = usuarioController.buscarPorTipo(TipoUsuario.ALUNO).getBody();
            System.out.println("Total de alunos: " + alunos.size());

            // 3. ALTERAR usuário
            usuarioCadastrado.setTelefone("(11) 88888-8888");
            usuarioCadastrado.setEscola("Escola ABC");
            var responseUpdate = usuarioController.atualizarUsuario(usuarioCadastrado.getId(), usuarioCadastrado);
            Usuario usuarioAtualizado = responseUpdate.getBody();
            System.out.println("Usuário atualizado: " + usuarioAtualizado.getTelefone());

            // Alterar status
            usuarioController.alterarStatus(usuarioCadastrado.getId(), "inativo");
            System.out.println("Status alterado para inativo");

            // 4. LOGAR usuário
            try {
                var responseLogin = usuarioController.login("joao@email.com", "123456");
                if (responseLogin.getStatusCode().is2xxSuccessful()) {
                    Usuario usuarioLogado = responseLogin.getBody();
                    System.out.println("Login realizado: " + usuarioLogado.getNome());
                }
            } catch (RuntimeException e) {
                System.out.println("Erro no login: " + e.getMessage());
            }

            // Reativar para poder fazer login
            usuarioController.alterarStatus(usuarioCadastrado.getId(), "ativo");
            var responseLoginFinal = usuarioController.login("joao@email.com", "123456");
            Usuario usuarioLogado = responseLoginFinal.getBody();
            System.out.println("Login realizado após reativação: " + usuarioLogado.getNome());

            // 5. APAGAR usuário
            usuarioController.excluirUsuario(usuarioCadastrado.getId());
            System.out.println("Usuário excluído com sucesso");

        } catch (RuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void exemploOperacoesProfessor() {
        try {
            // Cadastrar professor
            Usuario professor = new Usuario();
            professor.setNome("Maria Santos");
            professor.setEmail("maria@email.com");
            professor.setSenha("senha123");
            professor.setTipo(TipoUsuario.PROFESSOR);
            professor.setAreaEnsino("Matemática");
            professor.setFormacao("Licenciatura em Matemática");
            professor.setExperiencia("5 anos");
            professor.setDisciplina("Álgebra");

            var responseProfessor = usuarioController.cadastrarUsuario(professor);
            Usuario professorCadastrado = responseProfessor.getBody();
            System.out.println("Professor cadastrado: " + professorCadastrado.getNome());

            // Listar professores pendentes
            var professoresPendentes = usuarioController.listarProfessoresPendentes().getBody();
            System.out.println("Professores pendentes: " + professoresPendentes.size());

            // Aprovar professor
            usuarioController.aprovarProfessor(professorCadastrado.getId());
            System.out.println("Professor aprovado");

        } catch (RuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}