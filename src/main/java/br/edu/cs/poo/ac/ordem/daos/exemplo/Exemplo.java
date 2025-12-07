package br.edu.cs.poo.ac.ordem.daos.exemplo;

import br.edu.cs.poo.ac.utils.Registro;

public class Exemplo implements Registro {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nome;

    public Exemplo(String codigo, String nome) {
        super();
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getId() {
        return codigo;
    }
}