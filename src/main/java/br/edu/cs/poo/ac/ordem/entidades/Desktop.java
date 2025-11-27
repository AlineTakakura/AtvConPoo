package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Desktop extends Equipamento{

    private boolean ehServido;

    public Desktop(String Serial, String descricao, boolean ehNovo, double valorEstimado, boolean ehServido) {
        super(Serial, descricao, ehNovo, valorEstimado);
        this.ehServido = ehServido;
    }

    public String getIdTipo() {
        return "DE";
    }

}