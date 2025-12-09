package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import br.edu.cs.poo.ac.utils.Registro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdemServico implements Registro {

    private Cliente cliente;
    private PrecoBase precoBase;

    private Equipamento equipamento;
    private Notebook notebook;
    private Desktop desktop;

    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;

    private StatusOrdem status;
    private String vendedor;
    private FechamentoOrdemServico dadosFechamento;
    private LocalDateTime dataHoraCancelamento;
    private String motivoCancelamento;

    public OrdemServico(Cliente cliente, PrecoBase precoBase, Equipamento equipamento,
                        LocalDateTime dataHoraAbertura, int prazoEmDias, double valor) {
        this.cliente = cliente;
        this.precoBase = precoBase;
        this.equipamento = equipamento;

        if (equipamento instanceof Notebook) {
            this.notebook = (Notebook) equipamento;
        } else if (equipamento instanceof Desktop) {
            this.desktop = (Desktop) equipamento;
        }

        this.dataHoraAbertura = dataHoraAbertura;
        this.prazoEmDias = prazoEmDias;
        this.valor = valor;
        this.status = StatusOrdem.ABERTA;
    }

    @Override
    public String getId() {
        return getNumero();
    }

    public LocalDate getDataEstimadaEntrega() {
        if (dataHoraAbertura == null) return null;
        return dataHoraAbertura.toLocalDate().plusDays(prazoEmDias);
    }

    public String getNumero() {
        if (equipamento == null) {
            if (notebook != null) equipamento = notebook;
            else if (desktop != null) equipamento = desktop;
        }

        String prefixo = (equipamento != null) ? equipamento.getIdTipo() : "XX";

        if (cliente == null || dataHoraAbertura == null) return null;

        int ano = dataHoraAbertura.getYear();
        int mes = dataHoraAbertura.getMonthValue();
        int dia = dataHoraAbertura.getDayOfMonth();
        int hora = dataHoraAbertura.getHour();
        int min = dataHoraAbertura.getMinute();

        String dataJunta = String.format("%04d%02d%02d%02d%02d", ano, mes, dia, hora, min);
        String identificacao = cliente.getCpfCnpj();

        if (identificacao.length() == 11) {
            return prefixo + dataJunta + "000" + identificacao;
        } else {
            return prefixo + dataJunta + identificacao;
        }
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
        if (equipamento instanceof Notebook) {
            this.notebook = (Notebook) equipamento;
            this.desktop = null;
        } else if (equipamento instanceof Desktop) {
            this.desktop = (Desktop) equipamento;
            this.notebook = null;
        }
    }

    public Equipamento getEquipamento() {
        if (equipamento != null) return equipamento;
        if (notebook != null) return notebook;
        if (desktop != null) return desktop;
        return null;
    }
}