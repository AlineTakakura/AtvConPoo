package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoNaoExistente;
import br.edu.cs.poo.ac.ordem.daos.DAORegistro;
import br.edu.cs.poo.ac.ordem.entidades.*;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;

public class OrdemServicoMediator {

    private static final OrdemServicoMediator INSTANCIA = new OrdemServicoMediator();
    private final DAORegistro<OrdemServico> daoOrdem = new DAORegistro<>(OrdemServico.class);
    private final ClienteMediator clienteMediator = ClienteMediator.getInstancia();
    private final EquipamentoMediator equipamentoMediator = EquipamentoMediator.getInstancia();

    private OrdemServicoMediator() {}

    public static OrdemServicoMediator getInstancia() {
        return INSTANCIA;
    }

    public OrdemServico buscar(String numero) {
        return daoOrdem.buscar(numero);
    }

    public ResultadoMediator incluir(DadosOrdemServico dados) throws ExcecaoNegocio {
        ListaString erros = new ListaString();

        if (dados == null) {
            erros.adicionar("Dados básicos da ordem de serviço não informados");
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        if (dados.getCodigoPrecoBase() < 1 || dados.getCodigoPrecoBase() > 4) {
            erros.adicionar("Código do preço base inválido");
        }
        if (StringUtils.estaVazia(dados.getVendedor())) erros.adicionar("Vendedor não informado");
        if (StringUtils.estaVazia(dados.getCpfCnpjCliente())) erros.adicionar("CPF/CNPJ do cliente não informado");
        if (StringUtils.estaVazia(dados.getIdEquipamento())) erros.adicionar("Id do equipamento não informado");

        if (erros.tamanho() > 0) throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));

        Cliente cliente = clienteMediator.buscar(dados.getCpfCnpjCliente());
        if (cliente == null) erros.adicionar("CPF/CNPJ do cliente não encontrado");

        Equipamento equipamento = null;
        if (dados.getIdEquipamento().startsWith("NO")) {
            equipamento = equipamentoMediator.buscarNotebook(dados.getIdEquipamento());
        } else {
            equipamento = equipamentoMediator.buscarDesktop(dados.getIdEquipamento());
        }

        if (equipamento == null) erros.adicionar("Id do equipamento não encontrado");

        String cpfLimpo = dados.getCpfCnpjCliente().replaceAll("\\D", "");
        if (cpfLimpo.length() == 11 && dados.getCodigoPrecoBase() == 3) {
            erros.adicionar("Prazo e valor não podem ser avaliados pois o preço base é incompatível com cliente pessoa física");
        }

        if (erros.tamanho() > 0) throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));

        int prazo = 0;
        double valor = 0.0;
        PrecoBase pb = PrecoBase.getPrecoBase(dados.getCodigoPrecoBase());
        boolean ehNotebook = dados.getIdEquipamento().startsWith("NO");
        boolean ehPessoaJuridica = cpfLimpo.length() > 11;

        if (ehNotebook && ehPessoaJuridica && pb == PrecoBase.MANUTENCAO_EMERGENCIAL) {
            valor = 340.00; prazo = 4;
        } else if (ehNotebook && !ehPessoaJuridica && pb == PrecoBase.MANUTENCAO_EMERGENCIAL) {
            valor = 280.00; prazo = 4;
        } else if (ehPessoaJuridica) {
            if (pb == PrecoBase.LIMPEZA) { valor = 250.00; prazo = 6; }
            else if (pb == PrecoBase.REVISAO) { valor = 270.00; prazo = 6; }
            else if (pb == PrecoBase.MANUTENCAO_NORMAL) { valor = 240.00; prazo = 6; }
            else { valor = 340.00; prazo = 3; }
        } else {
            if (pb == PrecoBase.MANUTENCAO_NORMAL) { valor = 180.00; prazo = 6; }
            else if (pb == PrecoBase.MANUTENCAO_EMERGENCIAL) { valor = 280.00; prazo = 3; }
            else { valor = 210.00; prazo = 6; }
        }

        OrdemServico os = new OrdemServico(cliente, pb, equipamento, LocalDateTime.now(), prazo, valor);
        os.setVendedor(dados.getVendedor());
        os.setStatus(StatusOrdem.ABERTA);

        try {
            daoOrdem.incluir(os);
            return new ResultadoMediator(true, true, new ListaString());
        } catch (ExcecaoObjetoJaExistente e) {
            erros.adicionar("Ordem de serviço já existente");
            throw new ExcecaoNegocio(new ResultadoMediator(true, false, erros));
        }
    }

    public ResultadoMediator cancelar(String numero, String motivo, LocalDateTime dataCancelamento) throws ExcecaoNegocio {
        ListaString erros = new ListaString();
        boolean valido = true;

        if (StringUtils.estaVazia(motivo)) {
            valido = false;
            erros.adicionar("Motivo deve ser informado");
        }
        if (dataCancelamento == null) {
            valido = false;
            erros.adicionar("Data/hora cancelamento deve ser informada");
        } else if (dataCancelamento.isAfter(LocalDateTime.now())) {
            valido = false;
            erros.adicionar("Data/hora cancelamento deve ser menor do que a data hora atual");
        }
        if (StringUtils.estaVazia(numero)) {
            valido = false;
            erros.adicionar("Número de ordem deve ser informado");
        }

        if (!valido) throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));

        OrdemServico os = daoOrdem.buscar(numero);
        if (os == null) {
            erros.adicionar("Número de ordem não encontrado");
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        if (os.getStatus() == StatusOrdem.CANCELADA) {
            erros.adicionar("Ordem já foi cancelada");
            throw new ExcecaoNegocio(new ResultadoMediator(true, false, erros));
        }
        if (os.getStatus() == StatusOrdem.FECHADA) {
            erros.adicionar("Ordem já foi fechada");
            throw new ExcecaoNegocio(new ResultadoMediator(true, false, erros));
        }

        if (os.getDataHoraAbertura().plusDays(2).isBefore(LocalDateTime.now())) {
            erros.adicionar("Ordem aberta há mais de dois dias não pode ser cancelada");
            throw new ExcecaoNegocio(new ResultadoMediator(true, false, erros));
        }

        os.setStatus(StatusOrdem.CANCELADA);
        os.setMotivoCancelamento(motivo);
        os.setDataHoraCancelamento(dataCancelamento);

        try {
            daoOrdem.alterar(os);
            return new ResultadoMediator(true, true, new ListaString());
        } catch (ExcecaoObjetoNaoExistente e) {
            return null;
        }
    }

    public ResultadoMediator fechar(FechamentoOrdemServico fechamento) throws ExcecaoNegocio {
        ListaString erros = new ListaString();
        boolean valido = true;

        if (fechamento == null) {
            erros.adicionar("Dados do fechamento de ordem não informados");
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        if (StringUtils.estaVazia(fechamento.getRelatorioFinal())) {
            valido = false;
            erros.adicionar("Relatório final não informado");
        }
        if (fechamento.getDataFechamento() == null) {
            valido = false;
            erros.adicionar("Data de fechamento não informada");
        } else if (fechamento.getDataFechamento().isAfter(LocalDate.now())) {
            valido = false;
            erros.adicionar("Data de fechamento maior que a data atual");
        }
        if (StringUtils.estaVazia(fechamento.getNumeroOrdemServico())) {
            valido = false;
            erros.adicionar("Número de ordem não informado");
        }

        if (!valido) throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));

        OrdemServico os = daoOrdem.buscar(fechamento.getNumeroOrdemServico());
        if (os == null) {
            erros.adicionar("Número de ordem não encontrado");
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        if (os.getStatus() == StatusOrdem.CANCELADA) {
            erros.adicionar("Ordem já foi cancelada");
            throw new ExcecaoNegocio(new ResultadoMediator(true, false, erros));
        }
        if (os.getStatus() == StatusOrdem.FECHADA) {
            erros.adicionar("Ordem já foi fechada");
            throw new ExcecaoNegocio(new ResultadoMediator(true, false, erros));
        }

        os.setStatus(StatusOrdem.FECHADA);
        os.setDadosFechamento(fechamento);

        try {
            daoOrdem.alterar(os);
            return new ResultadoMediator(true, true, new ListaString());
        } catch (ExcecaoObjetoNaoExistente e) {
            return null;
        }
    }
}