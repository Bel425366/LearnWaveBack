package com.example.learnwave.enums;

public enum TipoUsuario {
    ALUNO("aluno"),
    ESTUDANTE("estudante"),
    PROFESSOR("professor"),
    ADMINISTRADOR("administrador");

    private final String valor;

    TipoUsuario(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoUsuario fromString(String valor) {
        for (TipoUsuario tipo : TipoUsuario.values()) {
            if (tipo.valor.equalsIgnoreCase(valor)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de usuário inválido: " + valor);
    }
}
