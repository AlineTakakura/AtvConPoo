package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.utils.Registro;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;

public class FechamentoOrdemServicoDAO extends DAOGenerico {

    @Override
    public Class<?> getClasseEntidade() {
        return FechamentoOrdemServico.class;
    }

    public FechamentoOrdemServico buscar(String numeroOrdemServico) {
        return (FechamentoOrdemServico) super.buscar(numeroOrdemServico);
    }

    public boolean incluir(FechamentoOrdemServico fechamento) {
        return super.incluir(fechamento);
    }

    public boolean alterar(FechamentoOrdemServico fechamento) {
        return super.alterar(fechamento);
    }

    public boolean excluir(String numeroOrdemServico) {
        return super.excluir(numeroOrdemServico);
    }

    public FechamentoOrdemServico[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        FechamentoOrdemServico[] retorno;

        if (ret != null && ret.length > 0) {
            retorno = new FechamentoOrdemServico[ret.length];
            for (int i = 0; i < ret.length; i++) {
                retorno[i] = (FechamentoOrdemServico) ret[i];
            }
        } else {
            retorno = new FechamentoOrdemServico[0];
        }
        return retorno;
    }
}