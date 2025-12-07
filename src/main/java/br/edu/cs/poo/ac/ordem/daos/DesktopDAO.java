package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.utils.Registro;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;

public class DesktopDAO extends DAOGenerico {

    @Override
    public Class<?> getClasseEntidade() {
        return Desktop.class;
    }

    public Desktop buscar(String codigo) {
        return (Desktop)super.buscar(codigo);
    }

    public boolean incluir(Desktop desktop) {
        return super.incluir(desktop);
    }

    public boolean alterar(Desktop desktop) {
        return super.alterar(desktop);
    }

    public boolean excluir(String id) {
        return super.excluir(id);
    }

    public Desktop[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        Desktop[] retorno;

        if (ret != null && ret.length > 0) {
            retorno = new Desktop[ret.length];
            for (int i=0; i<ret.length; i++) {
                retorno[i] = (Desktop)ret[i];
            }
        } else {
            retorno = new Desktop[0];
        }
        return retorno;
    }
}