
package br.edu.cs.poo.ac.ordem.daos.exemplo;

import java.io.Serializable;

import br.edu.cs.poo.ac.ordem.daos.DAOGenerico;
/**
 *
 * O identificador �nico por objeto de Exemplo � o seu c�digo, que ser� usado nos m�todos
 * para verificar a exist�ncia no cadastro de um objeto com um dado identificador �nico.
 *
 */
public class ExemploDAO extends DAOGenerico {
    public ExemploDAO() {
        super(Exemplo.class);
    }
    public Exemplo buscar(String codigo) {
        return (Exemplo)cadastroObjetos.buscar(codigo);
    }
    public boolean incluir(Exemplo exemplo) {
        if (buscar(exemplo.getCodigo()) == null) {
            cadastroObjetos.incluir(exemplo, exemplo.getCodigo());
            return true;
        } else {
            return false;
        }
    }
    public boolean alterar(Exemplo exemplo) {
        if (buscar(exemplo.getCodigo()) != null) {
            cadastroObjetos.alterar(exemplo, exemplo.getCodigo());
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
    public Exemplo[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
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