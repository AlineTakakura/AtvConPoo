package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor 
public class OrdemServico implements Serializable{
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
        String tipoEquipamento;
        if (notebook != null) {
            tipoEquipamento = notebook.getIdTipo();
        } else if (desktop != null) {
            tipoEquipamento = desktop.getIdTipo();
        } else {
            tipoEquipamento = "XX";
        }

        String cpfCnpj = cliente.getCpfCnpj();
        String dataHoraFormatada = dataHoraAbertura.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        if (cpfCnpj != null && cpfCnpj.replaceAll("[^0-9]", "").length() > 11) {
            return tipoEquipamento + dataHoraFormatada + cpfCnpj; 
        }

        return tipoEquipamento + dataHoraFormatada + "000" + cpfCnpj;
    }
}
