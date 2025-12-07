package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.utils.Registro;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public abstract class DAOGenerico {

    private CadastroObjetos cadastro;

    public abstract Class<?> getClasseEntidade();

    public DAOGenerico() {
        this.cadastro = new CadastroObjetos(getClasseEntidade());
    }

    public boolean incluir(Registro registro) {
        try {
            cadastro.incluir(registro, registro.getId());
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean alterar(Registro registro) {
        try {
            cadastro.alterar(registro, registro.getId());
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public Registro buscar(String id) {
        return (Registro)cadastro.buscar(id);
    }

    public boolean excluir(String id) {
        try {
            cadastro.excluir(id);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public Registro[] buscarTodos() {
        java.io.Serializable[] rets = cadastro.buscarTodos();
        if (rets != null) {
            Registro[] registros = new Registro[rets.length];
            for (int i = 0; i < rets.length; i++) {
                registros[i] = (Registro)rets[i];
            }
            return registros;
        }
        return new Registro[0];
    }
}