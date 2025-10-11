package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class FechamentoOrdemServico implements Serializable{

    private String numeroOrdemServico;
    private LocalDate dataFechamento;
    private boolean pago;
    private String relatorioFinal;
}
