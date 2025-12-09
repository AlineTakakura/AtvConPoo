package br.edu.cs.poo.ac.excecoes;

import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;

public class ExcecaoObjetoNaoExistente extends ExcecaoNegocio {
    private static final long serialVersionUID = 1L;

    public ExcecaoObjetoNaoExistente(String mensagem) {
        super(gerarResultado(mensagem));
    }

    private static ResultadoMediator gerarResultado(String mensagem) {
        ListaString lista = new ListaString();
        lista.adicionar(mensagem);
        return new ResultadoMediator(true, false, lista);
    }
}