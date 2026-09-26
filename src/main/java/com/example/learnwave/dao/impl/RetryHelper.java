package com.example.learnwave.dao.impl;

import java.util.function.Supplier;

/**
 * Utilitário de retry para operações de banco.
 *
 * O Somee (plano gratuito) às vezes fecha a conexão no meio da operação
 * ("Connection is closed" / "Unable to rollback"). Quando isso acontece,
 * tentamos de novo: o Hikari descarta a conexão morta e entrega uma nova.
 *
 * Continua salvando/lendo do MESMO banco (Somee) — apenas repete a tentativa.
 */
public final class RetryHelper {

    private RetryHelper() {}

    public static <T> T comRetry(Supplier<T> operacao) {
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
                    throw e; // erro que não é de conexão — não repete
                }
            }
        }
        throw ultimoErro;
    }

    /**
     * Executa uma operação que não retorna valor, com retry.
     */
    public static void comRetryVoid(Runnable operacao) {
        comRetry(() -> {
            operacao.run();
            return null;
        });
    }

    private static boolean ehErroDeConexao(Throwable e) {
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
}
