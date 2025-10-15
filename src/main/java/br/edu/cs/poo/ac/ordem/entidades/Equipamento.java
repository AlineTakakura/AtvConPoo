package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter

public class Equipamento {
    private String serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;

    public Equipamento(String serial, String descricao, boolean ehNovo, double valorEstimado) {
        this.serial = serial;
        this.descricao = descricao;
        this.ehNovo = ehNovo;
        this.valorEstimado = valorEstimado;
    }
}

