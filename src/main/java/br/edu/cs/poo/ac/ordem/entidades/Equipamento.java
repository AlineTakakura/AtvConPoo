package br.edu.cs.poo.ac.ordem.entidades;

import br.edu.cs.poo.ac.utils.Registro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public abstract class Equipamento implements Registro {
    private String Serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;

    public abstract String getIdTipo();
}