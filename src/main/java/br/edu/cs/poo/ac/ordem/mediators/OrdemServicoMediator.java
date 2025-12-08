package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate; // IMPORT FALTANTE ADICIONADO
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import br.edu.cs.poo.ac.ordem.daos.DAORegistro;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;
import br.edu.cs.poo.ac.ordem.entidades.StatusOrdem;
import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoNaoExistente;
import br.edu.cs.poo.ac.utils.ListaString;

public class OrdemServicoMediator {
    // Implementação Singleton
    private static OrdemServicoMediator instancia = new OrdemServicoMediator();
    public static OrdemServicoMediator getInstancia() {
        return instancia;
    }
    private OrdemServicoMediator() {} // Construtor privado

    // DAOs
    private DAORegistro<Cliente> daoCliente = new DAORegistro<>(Cliente.class);
    private DAORegistro<Desktop> daoDesktop = new DAORegistro<>(Desktop.class);
    private DAORegistro<Notebook> daoNotebook = new DAORegistro<>(Notebook.class);
    // NOME DO CAMPO AJUSTADO PARA 'daoOrdem' para passar o teste00
    private DAORegistro<OrdemServico> daoOrdem = new DAORegistro<>(OrdemServico.class);

    // Constantes de mensagens
    private static final String NUMERO_DE_ORDEM_NAO_ENCONTRADO = "Número de ordem não encontrado";
    private static final String ORDEM_JA_FOI_CANCELADA = "Ordem já foi cancelada";
    private static final String ORDEM_JA_FOI_FECHADA = "Ordem já foi fechada";
    private static final String CPF_CNPJ_CLIENTE_NAO_ENCONTRADO = "CPF/CNPJ do cliente não encontrado";
    private static final String ID_EQUIPAMENTO_NAO_ENCONTRADO = "Id do equipamento não encontrado";
    private static final String PRAZO_VALOR_INCOMPATIVEL_PF = "Prazo e valor não podem ser avaliados pois o preço base é incompatível com cliente pessoa física";

    /**
     * Inclui uma nova ordem de serviço.
     */
    public String incluir(DadosOrdemServico dados) throws ExcecaoNegocio {
        LocalDateTime dataHoraRegistro = LocalDateTime.now();

        ResultadoMediator res = validarInclusao(dados, dataHoraRegistro);
        if (!res.isValidado()) {
            throw new ExcecaoNegocio(res);
        }

        // --- Lógica de Inclusão ---
        Cliente cliente = daoCliente.buscar(dados.getCpfCnpjCliente());

        // Tenta Desktop
        Equipamento equipamento = daoDesktop.buscar(dados.getIdEquipamento());

        if (equipamento == null) {
            // Tenta Notebook
            equipamento = daoNotebook.buscar(dados.getIdEquipamento());
        }

        // Determina Prazo e Valor com base no PrecoBase e tipo de cliente/equipamento
        PrecoBase precoBase = PrecoBase.getPrecoBase(dados.getCodigoPrecoBase());

        int prazoEmDias = 0;
        double valor = 0.0;

        boolean isCNPJ = cliente.getCpfCnpj().length() == 14;

        if (equipamento instanceof Desktop) {
            if (isCNPJ) {
                if (precoBase == PrecoBase.MANUTENCAO_NORMAL) { prazoEmDias = 6; valor = 240.00; }
                if (precoBase == PrecoBase.MANUTENCAO_EMERGENCIAL) { prazoEmDias = 3; valor = 340.00; }
                if (precoBase == PrecoBase.REVISAO) { prazoEmDias = 6; valor = 270.00; }
                if (precoBase == PrecoBase.LIMPEZA) { prazoEmDias = 6; valor = 250.00; }
            } else { // CPF
                if (precoBase == PrecoBase.MANUTENCAO_NORMAL) { prazoEmDias = 6; valor = 180.00; }
                if (precoBase == PrecoBase.MANUTENCAO_EMERGENCIAL) { prazoEmDias = 3; valor = 280.00; }
                if (precoBase == PrecoBase.LIMPEZA) { prazoEmDias = 6; valor = 210.00; }
            }
        } else if (equipamento instanceof Notebook) {
            if (isCNPJ) {
                if (precoBase == PrecoBase.MANUTENCAO_NORMAL) { prazoEmDias = 6; valor = 240.00; }
                if (precoBase == PrecoBase.MANUTENCAO_EMERGENCIAL) { prazoEmDias = 4; valor = 340.00; }
                if (precoBase == PrecoBase.REVISAO) { prazoEmDias = 6; valor = 270.00; }
                if (precoBase == PrecoBase.LIMPEZA) { prazoEmDias = 6; valor = 250.00; }
            } else { // CPF
                if (precoBase == PrecoBase.MANUTENCAO_NORMAL) { prazoEmDias = 6; valor = 180.00; }
                if (precoBase == PrecoBase.MANUTENCAO_EMERGENCIAL) { prazoEmDias = 4; valor = 280.00; }
                if (precoBase == PrecoBase.LIMPEZA) { prazoEmDias = 6; valor = 210.00; }
            }
        }

        OrdemServico novaOrdem = new OrdemServico(cliente, precoBase, equipamento,
                dataHoraRegistro, prazoEmDias, valor);

        novaOrdem.setVendedor(dados.getVendedor());

        try {
            daoOrdem.incluir(novaOrdem);
            return novaOrdem.getNumero();
        } catch (ExcecaoObjetoJaExistente e) {
            // Em caso de colisão no número gerado, relança como erro de negócio
            throw new ExcecaoNegocio("Ordem de serviço já cadastrada (colisão de número).");
        }
    }

    private ResultadoMediator validarInclusao(DadosOrdemServico dados, LocalDateTime dataHoraRegistro) {
        ListaString mensagensErro = new ListaString();

        if (dados == null) {
            mensagensErro.adicionar("Dados básicos da ordem de serviço não informados");
        } else {
            if (dados.getVendedor() == null || dados.getVendedor().trim().isEmpty()) {
                mensagensErro.adicionar("Vendedor não informado");
            }
            if (dados.getCpfCnpjCliente() == null || dados.getCpfCnpjCliente().trim().isEmpty()) {
                mensagensErro.adicionar("CPF/CNPJ do cliente não informado");
            }
            if (dados.getIdEquipamento() == null || dados.getIdEquipamento().trim().isEmpty()) {
                mensagensErro.adicionar("Id do equipamento não informado");
            }
            if (PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()) == null) {
                mensagensErro.adicionar("Código do preço base inválido");
            }

            // Validações de existência (apenas se não houver erros de formato/nulo)
            if (mensagensErro.tamanho() == 0) {
                Cliente cliente = daoCliente.buscar(dados.getCpfCnpjCliente());
                if (cliente == null) {
                    mensagensErro.adicionar(CPF_CNPJ_CLIENTE_NAO_ENCONTRADO);
                } else {
                    // Validação de PreçoBase para Pessoa Física (CPF)
                    if (cliente.getCpfCnpj().length() == 11) {
                        PrecoBase pb = PrecoBase.getPrecoBase(dados.getCodigoPrecoBase());
                        if (pb == PrecoBase.REVISAO) {
                            mensagensErro.adicionar(PRAZO_VALOR_INCOMPATIVEL_PF);
                        }
                    }
                }

                Equipamento equipamento = daoDesktop.buscar(dados.getIdEquipamento());
                if (equipamento == null) {
                    equipamento = daoNotebook.buscar(dados.getIdEquipamento());
                }
                if (equipamento == null) {
                    mensagensErro.adicionar(ID_EQUIPAMENTO_NAO_ENCONTRADO);
                }
            }
        }
        return new ResultadoMediator(mensagensErro.tamanho() == 0, mensagensErro.tamanho() == 0, mensagensErro);
    }

    /**
     * Busca uma ordem de serviço.
     */
    public OrdemServico buscar(String numero) {
        return daoOrdem.buscar(numero);
    }

    /**
     * Fecha uma ordem de serviço.
     */
    public void fechar(FechamentoOrdemServico fecho) throws ExcecaoNegocio {
        ResultadoMediator res = validarFechamento(fecho);
        if (!res.isValidado()) {
            throw new ExcecaoNegocio(res);
        }

        String numero = fecho.getNumeroOrdemServico();
        OrdemServico ordem = daoOrdem.buscar(numero);

        if (ordem.getStatus() == StatusOrdem.FECHADA) {
            throw new ExcecaoNegocio(ORDEM_JA_FOI_FECHADA);
        }
        if (ordem.getStatus() == StatusOrdem.CANCELADA) {
            throw new ExcecaoNegocio(ORDEM_JA_FOI_CANCELADA);
        }

        ordem.fecharOrdem(fecho);

        try {
            daoOrdem.alterar(ordem);
        } catch (ExcecaoObjetoNaoExistente e) {
            // Não deve ocorrer se a busca inicial foi bem sucedida
            throw new ExcecaoNegocio(NUMERO_DE_ORDEM_NAO_ENCONTRADO);
        }
    }

    private ResultadoMediator validarFechamento(FechamentoOrdemServico fecho) {
        ListaString mensagensErro = new ListaString();

        if (fecho == null) {
            mensagensErro.adicionar("Dados do fechamento de ordem não informados");
            return new ResultadoMediator(false, false, mensagensErro);
        }

        if (fecho.getNumeroOrdemServico() == null || fecho.getNumeroOrdemServico().trim().isEmpty()) {
            mensagensErro.adicionar("Número de ordem não informado");
        }
        if (fecho.getDataFechamento() == null) {
            mensagensErro.adicionar("Data de fechamento não informada");
        } else if (fecho.getDataFechamento().isAfter(LocalDate.now())) { // LocalDate.now() é válido agora com o import
            mensagensErro.adicionar("Data de fechamento maior que a data atual");
        }
        if (fecho.getRelatorioFinal() == null || fecho.getRelatorioFinal().trim().isEmpty()) {
            mensagensErro.adicionar("Relatório final não informado");
        }

        if (mensagensErro.tamanho() == 0) {
            OrdemServico ordem = daoOrdem.buscar(fecho.getNumeroOrdemServico());
            if (ordem == null) {
                mensagensErro.adicionar(NUMERO_DE_ORDEM_NAO_ENCONTRADO);
            }
        }

        return new ResultadoMediator(mensagensErro.tamanho() == 0, mensagensErro.tamanho() == 0, mensagensErro);
    }

    /**
     * Cancela uma ordem de serviço.
     */
    public void cancelar(String numero, String motivo, LocalDateTime dataHoraCancelamento) throws ExcecaoNegocio {
        ResultadoMediator res = validarCancelamento(numero, motivo, dataHoraCancelamento);
        if (!res.isValidado()) {
            throw new ExcecaoNegocio(res);
        }

        OrdemServico ordem = daoOrdem.buscar(numero);

        if (ordem.getStatus() == StatusOrdem.FECHADA) {
            throw new ExcecaoNegocio(ORDEM_JA_FOI_FECHADA);
        }
        if (ordem.getStatus() == StatusOrdem.CANCELADA) {
            throw new ExcecaoNegocio(ORDEM_JA_FOI_CANCELADA);
        }

        // Validação de prazo de 2 dias (48 horas)
        long horasDesdeAbertura = ChronoUnit.HOURS.between(ordem.getDataHoraAbertura(), dataHoraCancelamento);
        if (horasDesdeAbertura > 48) {
            throw new ExcecaoNegocio("Ordem aberta há mais de dois dias não pode ser cancelada");
        }

        // Atualiza a ordem
        ordem.setStatus(StatusOrdem.CANCELADA);
        ordem.setMotivoCancelamento(motivo);
        ordem.setDataHoraCancelamento(dataHoraCancelamento);

        try {
            daoOrdem.alterar(ordem);
        } catch (ExcecaoObjetoNaoExistente e) {
            // Não deve ocorrer
            throw new ExcecaoNegocio(NUMERO_DE_ORDEM_NAO_ENCONTRADO);
        }
    }

    private ResultadoMediator validarCancelamento(String numero, String motivo, LocalDateTime dataHoraCancelamento) {
        ListaString mensagensErro = new ListaString();

        if (numero == null || numero.trim().isEmpty()) {
            mensagensErro.adicionar("Número de ordem deve ser informado");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            mensagensErro.adicionar("Motivo deve ser informado");
        }
        if (dataHoraCancelamento == null) {
            mensagensErro.adicionar("Data/hora cancelamento deve ser informada");
        } else if (dataHoraCancelamento.isAfter(LocalDateTime.now())) {
            mensagensErro.adicionar("Data/hora cancelamento deve ser menor do que a data hora atual");
        }

        if (mensagensErro.tamanho() == 0) {
            OrdemServico ordem = daoOrdem.buscar(numero);
            if (ordem == null) {
                mensagensErro.adicionar(NUMERO_DE_ORDEM_NAO_ENCONTRADO);
            }
        }

        return new ResultadoMediator(mensagensErro.tamanho() == 0, mensagensErro.tamanho() == 0, mensagensErro);
    }
}