package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.utils.Registro;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;

public class NotebookDAO extends DAOGenerico {

    @Override
    public Class<?> getClasseEntidade() {
        return Notebook.class;
    }

    public Notebook buscar(String codigo) {
        return (Notebook)super.buscar(codigo);
    }

    public boolean incluir(Notebook notebook) {
        return super.incluir(notebook);
    }

    public boolean alterar(Notebook notebook) {
        return super.alterar(notebook);
    }

    public boolean excluir(String id) {
        return super.excluir(id);
    }

    public Notebook[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        Notebook[] retorno;

        if (ret != null && ret.length > 0) {
            retorno = new Notebook[ret.length];
            for (int i=0; i<ret.length; i++) {
                retorno[i] = (Notebook)ret[i];
            }
        } else {
            retorno = new Notebook[0];
        }
        return retorno;
    }
}