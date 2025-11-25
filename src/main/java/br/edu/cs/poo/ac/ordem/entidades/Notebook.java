package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class Notebook extends Equipamento {

    private boolean carregaDadosSensiveis;

    public Notebook(String Serial, String descricao, boolean ehNovo, double valorEstimado,
                    boolean carregaDadosSensiveis) {
        super(Serial, descricao, ehNovo, valorEstimado);
        this.carregaDadosSensiveis = carregaDadosSensiveis;
    }

    public String getIdTipo(){
        return "NO";
    }

    public String getId() {
        return getIdTipo() + super.getSerial();
    }
}