package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class DAOGenerico {
    protected CadastroObjetos cadastroObjetos;
@SuppressWarnings("rawtypes")
    public DAOGenerico(Class classe) {
        cadastroObjetos = new CadastroObjetos(classe);
    }
}
