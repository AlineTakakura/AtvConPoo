package br.edu.cs.poo.ac.ordem.daos.exemplo;

import br.edu.cs.poo.ac.ordem.daos.DAOGenerico;
import br.edu.cs.poo.ac.utils.Registro;

/**
 * O DAOGenerico agora encapsula a lgica bsica do CRUD.
 * Este DAO apenas delega as chamadas e lida com os tipos especficos (Exemplo).
 */
public class ExemploDAO extends DAOGenerico {
    @Override
    public Class<?> getClasseEntidade() {
        return Exemplo.class;
    }

    public Exemplo buscar(String codigo) {
        return (Exemplo)super.buscar(codigo);
    }

    public boolean incluir(Exemplo exemplo) {
        return super.incluir(exemplo);
    }

    public boolean alterar(Exemplo exemplo) {
        return super.alterar(exemplo);
    }

    public boolean excluir(String codigo) {
        return super.excluir(codigo);
    }

    public Exemplo[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        Exemplo[] retorno;

        if (ret != null && ret.length > 0) {
            retorno = new Exemplo[ret.length];
            for (int i=0; i<ret.length; i++) {
                retorno[i] = (Exemplo)ret[i];
            }
        } else {
            retorno = new Exemplo[0];
        }
        return retorno;
    }
}