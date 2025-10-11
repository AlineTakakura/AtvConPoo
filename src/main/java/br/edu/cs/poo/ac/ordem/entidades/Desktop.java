package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;
import lombok.Setter;

public class Desktop extends Equipamento {
    @Getter @Setter
    private boolean ehServidor;

    public Desktop(String serial, String descricao, boolean ehNovo, double valorEstimado, boolean ehServidor) {
        super(serial,descricao,ehNovo,valorEstimado);
        this.ehServidor = ehServidor;
    }

    public String getIdTipo() {
        return "DE";
    }
}
