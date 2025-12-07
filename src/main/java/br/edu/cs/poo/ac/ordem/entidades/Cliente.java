package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate;
import java.time.Period;
import br.edu.cs.poo.ac.utils.Registro;

public class Cliente implements Registro {
    private static final long serialVersionUID = 1L;
    private String cpfCnpj;
    private String nome;
    private Contato contato;
    private LocalDate dataCadastro;
    public int getIdadeCadastro() {
        Period periodo = Period.between(dataCadastro, LocalDate.now());
        return periodo.getYears();
    }
    public Cliente(String cpfCnpj, String nome, Contato contato, LocalDate dataCadastro) {
        super();
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.contato = contato;
        this.dataCadastro = dataCadastro;
    }
    public String getCpfCnpj() {
        return cpfCnpj;
    }
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Contato getContato() {
        return contato;
    }
    public void setContato(Contato contato) {
        this.contato = contato;
    }
    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    @Override
    public String getId() {
        return cpfCnpj;
    }
}