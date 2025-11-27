package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.utils.Registro;
import java.io.Serializable;

public abstract class DAOGenerico {

    protected CadastroObjetos cadastroObjetos;

    public DAOGenerico() {
        cadastroObjetos = new CadastroObjetos(getClasseEntidade());
    }

    public abstract Class<?> getClasseEntidade();

    public Registro buscar(String id) {
        return (Registro) cadastroObjetos.buscar(id);
    }

    public boolean incluir(Registro registro) {
        if (buscar(registro.getId()) != null)
            return false;
        cadastroObjetos.incluir((Serializable) registro, registro.getId());
        return true;
    }

    public boolean alterar(Registro registro) {
        if (buscar(registro.getId()) == null)
            return false;
        cadastroObjetos.alterar((Serializable) registro, registro.getId());
        return true;
    }

    public boolean excluir(String id) {
        if (buscar(id) == null)
            return false;
        cadastroObjetos.excluir(id);
        return true;
    }

    public Registro[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
        if (ret == null || ret.length == 0) return new Registro[0];

        Registro[] retorno = new Registro[ret.length];
        for (int i = 0; i < ret.length; i++) {
            retorno[i] = (Registro) ret[i];
        }

        return retorno;
    }
}
