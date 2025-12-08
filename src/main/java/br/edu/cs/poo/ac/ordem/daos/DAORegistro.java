package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoNaoExistente;
import br.edu.cs.poo.ac.utils.Registro;

public class DAORegistro<T extends Registro> {
    private final CadastroObjetos cadastro;
    private final String nomeClasse;

    public DAORegistro(Class<T> classe) {
        this.cadastro = new CadastroObjetos(classe);
        this.nomeClasse = classe.getSimpleName();
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public T buscar(String id) {
        return (T) cadastro.buscar(id);
    }

    public void incluir(T instancia) throws ExcecaoObjetoJaExistente {
        if (instancia == null)
            return;
        if (buscar(instancia.getId()) != null)
            throw new ExcecaoObjetoJaExistente(getNomeClasse() + " já existente");

        cadastro.incluir(instancia, instancia.getId());
    }

    public void alterar(T instancia) throws ExcecaoObjetoNaoExistente {
        if (instancia == null)
            return;
        if (buscar(instancia.getId()) == null)
            throw new ExcecaoObjetoNaoExistente(getNomeClasse() + " não existente");

        cadastro.alterar(instancia, instancia.getId());
    }

    public void excluir(String id) throws ExcecaoObjetoNaoExistente {
        if (id == null)
            return;
        if (buscar(id) == null)
            throw new ExcecaoObjetoNaoExistente(getNomeClasse() + " não existente");

        cadastro.excluir(id);
    }

    public List<T> buscarTodos() {
        Serializable[] ret = cadastro.buscarTodos();
        List<T> list = new ArrayList<T>();

        if (ret == null) return list;
        for(int i = 0; i < ret.length; i++) {
            list.add((T) ret[i]);
        }
        return list;
    }
}