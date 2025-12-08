package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;

public enum StatusOrdem implements Serializable {
    ABERTA(1, "Aberta"),
    CANCELADA(2, "Cancelada"), // Código 2 e nome ajustados conforme teste
    FECHADA(3, "Fechada");

    private int codigo;
    private String descricao;

    private StatusOrdem(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() { // Adicionado para resolver erro
        return descricao;
    }

    public static StatusOrdem getStatusOrdem(int codigo) {
        for (StatusOrdem status : values()) {
            if (status.codigo == codigo) {
                return status;
            }
        }
        return null;
    }
}