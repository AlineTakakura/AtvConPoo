package br.edu.cs.poo.ac.excecoes;

import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class ExcecaoNegocio extends Exception {
    private ResultadoMediator res;

    public ExcecaoNegocio(String message) {
        super(message);
    }

    public ExcecaoNegocio(ResultadoMediator res) {
        this.res = res;
    }

    public ResultadoMediator getRes() {
        return res;
    }
}