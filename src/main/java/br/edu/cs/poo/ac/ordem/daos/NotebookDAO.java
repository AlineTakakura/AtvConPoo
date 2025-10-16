package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;


public class NotebookDAO extends DAOGenerico {

    public NotebookDAO() {
        super(Notebook.class);
    }

    private String getCodigo(Notebook notebook) {
        return notebook.getIdTipo() + notebook.getSerial();
    }

    public Notebook buscar(String codigo) {
        return (Notebook) cadastroObjetos.buscar(codigo);
    }

    public boolean incluir(Notebook notebook) {
        if (buscar(getCodigo(notebook)) == null) {
            cadastroObjetos.incluir((Serializable) notebook, getCodigo(notebook));
            return true;
        } else {
            return false;
        }
    }

    public boolean alterar(Notebook notebook) {
        if (buscar(getCodigo(notebook)) != null) {
            cadastroObjetos.alterar((Serializable) notebook, getCodigo(notebook));
            return true;
        } else {
            return false;
        }
    }

    public boolean excluir(String codigo) {
        if (buscar(codigo) != null) {
            cadastroObjetos.excluir(codigo);
            return true;
        } else {
            return false;
        }
    }

    public Notebook[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
        Notebook[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new Notebook[ret.length];
            for (int i = 0; i < ret.length; i++) {
                retorno[i] = (Notebook) ret[i];
            }
        } else {
            retorno = new Notebook[0];
        }
        return retorno;
    }
}
