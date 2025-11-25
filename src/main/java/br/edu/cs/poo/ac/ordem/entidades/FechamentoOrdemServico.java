package br.edu.cs.poo.ac.ordem.entidades;

import br.edu.cs.poo.ac.utils.Registro;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter


public class FechamentoOrdemServico implements Registro {
    private String numeroOrdemServico;
    private LocalDate dataFechamento;
    private boolean pago;
    private String relatorioFinal;

    public String getId() {
        return this.numeroOrdemServico;
    }
}