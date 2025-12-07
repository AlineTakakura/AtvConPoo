package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import br.edu.cs.poo.ac.utils.Registro;

@Getter
@Setter

public class FechamentoOrdemServico implements Registro {
    private static final long serialVersionUID = 1L;

    private Long numeroOrdemServico;
    private LocalDate dataFechamento;
    private Double valorFinal;

    public FechamentoOrdemServico(Long numeroOrdemServico, LocalDate dataFechamento, Double valorFinal) {
        this.numeroOrdemServico = numeroOrdemServico;
        this.dataFechamento = dataFechamento;
        this.valorFinal = valorFinal;
    }
    @Override
    public String getId(){
        return String.valueOf(numeroOrdemServico);
    }
}