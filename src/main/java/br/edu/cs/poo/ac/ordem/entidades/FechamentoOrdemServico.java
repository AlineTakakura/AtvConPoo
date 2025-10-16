package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter

public class FechamentoOrdemServico implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long numeroOrdemServico;
    private LocalDate dataFechamento;
    private Double valorFinal;

    public FechamentoOrdemServico(Long numeroOrdemServico, LocalDate dataFechamento, Double valorFinal) {
        this.numeroOrdemServico = numeroOrdemServico;
        this.dataFechamento = dataFechamento;
        this.valorFinal = valorFinal;
    }
}
