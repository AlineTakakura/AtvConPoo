package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;

public class EquipamentoMediator {

	private static EquipamentoMediator instancia;
	private DesktopDAO desktopDAO;
	private NotebookDAO notebookDAO;

    private static final String SERIAL_NAO_INFORMADO = "Serial não informado";
    private static final String DESCRICAO_NAO_INFORMADA = "Descrição não informada";
    private static final String VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO = "Valor estimado menor ou igual a zero";
    private static final int TAMANHO_MIN_DESCRICAO = 10;
    private static final int TAMANHO_MAX_DESCRICAO = 150;
    
	private EquipamentoMediator() {
		this.desktopDAO = new DesktopDAO();
		this.notebookDAO = new NotebookDAO();
	}

	public static EquipamentoMediator getInstancia() {
		if (instancia == null) {
			instancia = new EquipamentoMediator();
		}
		return instancia;
	}

	public ResultadoMediator validar(DadosEquipamento equip) {
		ListaString erros = new ListaString();
        boolean validado = true;

        if (equip == null) {
            erros.adicionar("Dados básicos do equipamento não informados");
            return new ResultadoMediator(false, false, erros);
        }

        String descricao = equip.getDescricao();
        if (StringUtils.estaVazia(descricao)) {
            erros.adicionar(DESCRICAO_NAO_INFORMADA);
            validado = false;
        } else if (descricao.length() < TAMANHO_MIN_DESCRICAO) {
            erros.adicionar("Descrição tem menos de " + TAMANHO_MIN_DESCRICAO + " caracteres");
            validado = false;
        } else if (StringUtils.tamanhoExcedido(descricao, TAMANHO_MAX_DESCRICAO)) {
            erros.adicionar("Descrição tem mais de " + TAMANHO_MAX_DESCRICAO + " caracteres");
            validado = false;
        }

        if (StringUtils.estaVazia(equip.getSerial())) {
            erros.adicionar(SERIAL_NAO_INFORMADO);
            validado = false;
        }

        if (equip.getValorEstimado() <= 0.0) {
            erros.adicionar(VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO);
            validado = false;
        }

		return new ResultadoMediator(validado, false, erros);
	}
    
	public ResultadoMediator validarDesktop(Desktop desk) {
        if (desk == null) {
            ListaString erros = new ListaString();
            erros.adicionar("Desktop não informado");
            return new ResultadoMediator(false, false, erros);
        }

        DadosEquipamento dados = new DadosEquipamento(
            desk.getSerial(), 
            desk.getDescricao(), 
            desk.isEhNovo(), 
            desk.getValorEstimado()
        );
		return validar(dados);
	}

	public ResultadoMediator validarNotebook(Notebook note) {
        if (note == null) {
            ListaString erros = new ListaString();
            erros.adicionar("Notebook não informado");
            return new ResultadoMediator(false, false, erros);
        }

        DadosEquipamento dados = new DadosEquipamento(
            note.getSerial(), 
            note.getDescricao(), 
            note.isEhNovo(), 
            note.getValorEstimado()
        );
		return validar(dados);
	}

	public ResultadoMediator incluirDesktop(Desktop desk) {
		ResultadoMediator resValidacao = validarDesktop(desk);
        
		if (!resValidacao.isValidado()) {
			return resValidacao;
		}

		boolean incluido = desktopDAO.incluir(desk);
        
		if (!incluido) {
			ListaString erros = new ListaString();
			erros.adicionar("Serial do desktop já existente");
			return new ResultadoMediator(true, false, erros);
		}

		return new ResultadoMediator(true, true, new ListaString());
	}

	public ResultadoMediator incluirNotebook(Notebook note) {
		ResultadoMediator resValidacao = validarNotebook(note);
        
		if (!resValidacao.isValidado()) {
			return resValidacao;
		}

		boolean incluido = notebookDAO.incluir(note);
        
		if (!incluido) {
			ListaString erros = new ListaString();
			erros.adicionar("Serial do notebook já existente");
			return new ResultadoMediator(true, false, erros);
		}

		return new ResultadoMediator(true, true, new ListaString());
	}

	public ResultadoMediator alterarDesktop(Desktop desk) {
		ResultadoMediator resValidacao = validarDesktop(desk);

		if (!resValidacao.isValidado()) {
			return resValidacao;
		}

		boolean alterado = desktopDAO.alterar(desk);

		if (!alterado) {
			ListaString erros = new ListaString();
			erros.adicionar("Serial do desktop não existente");
			return new ResultadoMediator(true, false, erros);
		}

		return new ResultadoMediator(true, true, new ListaString());
	}

	public ResultadoMediator alterarNotebook(Notebook note) {
		ResultadoMediator resValidacao = validarNotebook(note);

		if (!resValidacao.isValidado()) {
			return resValidacao;
		}

		boolean alterado = notebookDAO.alterar(note);

		if (!alterado) {
			ListaString erros = new ListaString();
			erros.adicionar("Serial do notebook não existente");
			return new ResultadoMediator(true, false, erros);
		}

		return new ResultadoMediator(true, true, new ListaString());
	}

	public ResultadoMediator excluirDesktop(String idTipoSerial) {
        if (StringUtils.estaVazia(idTipoSerial)) {
			ListaString erros = new ListaString();
			erros.adicionar("Id do tipo + serial do desktop não informado");
			return new ResultadoMediator(false, false, erros);
        }

		boolean excluido = desktopDAO.excluir(idTipoSerial);
        
		if (!excluido) {
			ListaString erros = new ListaString();
			erros.adicionar("Serial do desktop não existente");
			return new ResultadoMediator(true, false, erros);
		}

		return new ResultadoMediator(true, true, new ListaString());
	}

	public ResultadoMediator excluirNotebook(String idTipoSerial) {
        if (StringUtils.estaVazia(idTipoSerial)) {
			ListaString erros = new ListaString();
			erros.adicionar("Id do tipo + serial do notebook não informado");
			return new ResultadoMediator(false, false, erros);
        }

		boolean excluido = notebookDAO.excluir(idTipoSerial);
        
		if (!excluido) {
			ListaString erros = new ListaString();
			erros.adicionar("Serial do notebook não existente");
			return new ResultadoMediator(true, false, erros);
		}

		return new ResultadoMediator(true, true, new ListaString());
	}

	public Desktop buscarDesktop(String idTipoSerial) {
        if (StringUtils.estaVazia(idTipoSerial)) {
            return null;
        }
		return desktopDAO.buscar(idTipoSerial);
	}
    
	public Notebook buscarNotebook(String idTipoSerial) {
        if (StringUtils.estaVazia(idTipoSerial)) {
            return null;
        }
		return notebookDAO.buscar(idTipoSerial);
	}
}