package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.utils.Registro;

public class ClienteDAO extends DAOGenerico {

    @Override
    public Class<?> getClasseEntidade() {
        return Cliente.class;
    }

    public Cliente buscar(String cpfCnpj) {
        return (Cliente)super.buscar(cpfCnpj);
    }

    public boolean incluir(Cliente cliente) {
        return super.incluir(cliente);
    }

    public boolean alterar(Cliente cliente) {
        return super.alterar(cliente);
    }

    public boolean excluir(String cpfCnpj) {
        return super.excluir(cpfCnpj);
    }

    public Cliente[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        Cliente[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new Cliente[ret.length];
            for (int i=0; i<ret.length; i++) {
                retorno[i] = (Cliente)ret[i];
            }
        } else {
            retorno = new Cliente[0];
        }
        return retorno;
    }
}
