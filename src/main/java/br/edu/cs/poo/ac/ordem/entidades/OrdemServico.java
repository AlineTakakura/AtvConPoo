package br.edu.cs.poo.ac.ordem.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class OrdemServico {

    private Cliente cliente;
    private PrecoBase precoBase;
    private Notebook notebook;
    private Desktop desktop;
    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;

    public LocalDate getDataEstimadaEntrega() {
        return dataHoraAbertura.toLocalDate().plusDays(prazoEmDias);
    }

    public String getNumero() {
        String tipoEquipamento = null;

        if (notebook != null) {
            tipoEquipamento = notebook.getIdTipo();
        } else if (desktop != null) {
            tipoEquipamento = desktop.getIdTipo();
        }

        int ano = dataHoraAbertura.getYear();
        int mes = dataHoraAbertura.getMonthValue();
        int dia = dataHoraAbertura.getDayOfMonth();
        int hora = dataHoraAbertura.getHour();
        int minuto = dataHoraAbertura.getMinute();

        String cpfCnpj = cliente.getCpfCnpj();

        if (cpfCnpj.length() == 14) {
            return tipoEquipamento + ano + mes + dia + hora + minuto + cpfCnpj;
        } else {
            return mes + "" + ano + dia + hora + minuto + "000" + cpfCnpj;
        }
    }
}
