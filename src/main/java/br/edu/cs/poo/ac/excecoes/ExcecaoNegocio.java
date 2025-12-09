package br.edu.cs.poo.ac.excecoes;

import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;

public class ExcecaoNegocio extends Exception {
    private static final long serialVersionUID = 1L;

    private ResultadoMediator resultadoMediator;

    public ExcecaoNegocio(ResultadoMediator resultadoMediator) {
        super(extrairMensagem(resultadoMediator));
        this.resultadoMediator = resultadoMediator;
    }

    private static String extrairMensagem(ResultadoMediator res) {
        if (res != null && res.getMensagensErro() != null && res.getMensagensErro().tamanho() > 0) {
            return res.getMensagensErro().buscar(0);
        }
        return null;
    }

    public ResultadoMediator getRes() {
        return this.resultadoMediator;
    }

    public ListaString getMensagensErro() {
        return this.resultadoMediator != null ? this.resultadoMediator.getMensagensErro() : new ListaString();
    }
}