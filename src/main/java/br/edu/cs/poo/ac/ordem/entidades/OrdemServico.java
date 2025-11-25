package br.edu.cs.poo.ac.ordem.entidades;

import br.edu.cs.poo.ac.utils.Registro;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class OrdemServico implements Registro {

    private Cliente cliente;
    private PrecoBase precoBase;
    private Notebook notebook;
    private Desktop desktop;
    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;

    public LocalDate getDataEstimadaEntrega() {

        LocalDate dataAbertura = dataHoraAbertura.toLocalDate();

        return dataAbertura.plusDays(prazoEmDias);
    }

    public String getNumero() {

        String concatenacao = "";
        if (notebook != null) {
            concatenacao += notebook.getIdTipo();
        } else{
            concatenacao += desktop.getIdTipo();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        concatenacao += dataHoraAbertura.format(formatter);

        if (cliente.getCpfCnpj().length() > 11) {
            concatenacao += cliente.getCpfCnpj();
        } else {
            concatenacao += "000" + cliente.getCpfCnpj();
        }

        return concatenacao;
    }

    public String getId() {
        return getNumero();
    }
}