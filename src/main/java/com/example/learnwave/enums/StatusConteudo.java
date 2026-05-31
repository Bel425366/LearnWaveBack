package com.example.learnwave.enums;

public enum StatusConteudo {
    RASCUNHO("rascunho"),
    PUBLICADO("publicado"),
    ARQUIVADO("arquivado"),
    LIXEIRA("lixeira");

    private final String valor;

    StatusConteudo(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static StatusConteudo fromString(String valor) {
        for (StatusConteudo status : StatusConteudo.values()) {
            if (status.valor.equalsIgnoreCase(valor)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de conteúdo inválido: " + valor);
    }
}
