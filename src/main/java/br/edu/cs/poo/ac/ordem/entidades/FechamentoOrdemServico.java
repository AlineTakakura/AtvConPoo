package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import br.edu.cs.poo.ac.utils.Registro; // Importa Registro

// Agora implementa Registro. Isso resolve os problemas de tipo no DAO.
public class FechamentoOrdemServico implements Serializable, Registro {
    private static final long serialVersionUID = 1L;
    // Campos reescritos e ajustados para o TesteEntidadesExcecoes
    private String numeroOrdemServico;
    private LocalDate dataFechamento;
    private boolean pago;
    private String relatorioFinal;

    public FechamentoOrdemServico(String numeroOrdemServico, LocalDate dataFechamento, boolean pago, String relatorioFinal) {
        this.numeroOrdemServico = numeroOrdemServico;
        this.dataFechamento = dataFechamento;
        this.pago = pago;
        this.relatorioFinal = relatorioFinal;
    }

    public String getNumeroOrdemServico() { return numeroOrdemServico; }
    public LocalDate getDataFechamento() { return dataFechamento; }
    public boolean isPago() { return pago; }
    public String getRelatorioFinal() { return relatorioFinal; }

    public void setDataFechamento(LocalDate dataFechamento) { this.dataFechamento = dataFechamento; }
    public void setPago(boolean pago) { this.pago = pago; }
    public void setRelatorioFinal(String relatorioFinal) { this.relatorioFinal = relatorioFinal; }

    // Implementação obrigatória da interface Registro.
    // Usamos o numeroOrdemServico como ID único para a persistência.
    @Override
    public String getId() {
        return this.numeroOrdemServico;
    }
}