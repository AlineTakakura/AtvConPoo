package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.utils.Registro;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;

public class OrdemServicoDAO extends DAOGenerico {

    @Override
    public Class<?> getClasseEntidade() {
        return OrdemServico.class;
    }

    public OrdemServico buscar(String numero) {
        return (OrdemServico)super.buscar(numero);
    }

    public boolean incluir(OrdemServico ordemServico) {
        return super.incluir(ordemServico);
    }

    public boolean alterar(OrdemServico ordemServico) {
        return super.alterar(ordemServico);
    }

    public boolean excluir(String numero) {
        return super.excluir(numero);
    }

    public OrdemServico[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        OrdemServico[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new OrdemServico[ret.length];
            for (int i=0; i<ret.length; i++) {
                retorno[i] = (OrdemServico)ret[i];
            }
        } else {
            retorno = new OrdemServico[0];
        }
        return retorno;
    }
}