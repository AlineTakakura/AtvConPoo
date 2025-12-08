package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate; // Importação adicionada
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import br.edu.cs.poo.ac.utils.Registro;
import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;

public class OrdemServico implements Registro {
    private static final long serialVersionUID = 1L;
    private String numero;
    private Cliente cliente;
    private PrecoBase precoBase;
    private Equipamento equipamento;
    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;
    private StatusOrdem status;
    private String vendedor;
    private FechamentoOrdemServico dadosFechamento;

    private String motivoCancelamento;
    private LocalDateTime dataHoraCancelamento;

    public OrdemServico(Cliente cliente, PrecoBase precoBase, Equipamento equipamento,
                        LocalDateTime dataHoraAbertura, int prazoEmDias, double valor) {
        this.cliente = cliente;
        this.precoBase = precoBase;
        this.equipamento = equipamento;
        this.dataHoraAbertura = dataHoraAbertura;
        this.prazoEmDias = prazoEmDias;
        this.valor = valor;
        this.status = StatusOrdem.ABERTA;
        this.numero = gerarNumero();
    }

    private String gerarNumero() {
        String prefixo = equipamento.getIdTipo();
        String dataHoraStr = dataHoraAbertura.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        String cpfCnpj = cliente.getCpfCnpj();

        String suffix;
        if (cpfCnpj != null && cpfCnpj.length() == 11) {
            suffix = String.format("%014d", Long.parseLong(cpfCnpj));
        } else {
            suffix = cpfCnpj;
        }

        return prefixo + dataHoraStr + suffix;
    }

    @Override public String getId() { return numero; }
    public String getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public PrecoBase getPrecoBase() { return precoBase; }
    public Equipamento getEquipamento() { return equipamento; }
    public LocalDateTime getDataHoraAbertura() { return dataHoraAbertura; }
    public int getPrazoEmDias() { return prazoEmDias; }
    public double getValor() { return valor; }
    public StatusOrdem getStatus() { return status; }
    public String getVendedor() { return vendedor; }
    public FechamentoOrdemServico getDadosFechamento() { return dadosFechamento; }
    public String getMotivoCancelamento() { return motivoCancelamento; }
    public LocalDateTime getDataHoraCancelamento() { return dataHoraCancelamento; }

    // MÉTODO ADICIONADO PARA CORRIGIR O ERRO
    public LocalDate getDataEstimadaEntrega() {
        return this.dataHoraAbertura.toLocalDate().plusDays(this.prazoEmDias);
    }

    // Setters exigidos pelo teste01
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setPrecoBase(PrecoBase precoBase) { this.precoBase = precoBase; }
    public void setEquipamento(Equipamento equipamento) { this.equipamento = equipamento; }
    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) { this.dataHoraAbertura = dataHoraAbertura; }
    public void setPrazoEmDias(int prazoEmDias) { this.prazoEmDias = prazoEmDias; }
    public void setValor(double valor) { this.valor = valor; }
    public void setDadosFechamento(FechamentoOrdemServico dadosFechamento) { this.dadosFechamento = dadosFechamento; }
    public void setStatus(StatusOrdem status) { this.status = status; }
    public void setVendedor(String vendedor) { this.vendedor = vendedor; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }
    public void setDataHoraCancelamento(LocalDateTime dataHoraCancelamento) { this.dataHoraCancelamento = dataHoraCancelamento; }

    public double getValorTotal() {
        return prazoEmDias * valor;
    }

    public void fecharOrdem(FechamentoOrdemServico dadosFechamento) throws ExcecaoNegocio {
        if (this.status == StatusOrdem.FECHADA || this.status == StatusOrdem.CANCELADA) {
            throw new ExcecaoNegocio("Ordem de serviço não pode ser fechada, status atual: " + this.status);
        }
        this.status = StatusOrdem.FECHADA;
        this.dadosFechamento = dadosFechamento;
    }
}