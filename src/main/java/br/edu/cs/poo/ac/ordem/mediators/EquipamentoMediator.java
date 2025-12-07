package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.utils.ListaString;
import static br.edu.cs.poo.ac.utils.StringUtils.*;

import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;

public class EquipamentoMediator {
    private static final String SERIAL_DO_NOTEBOOK_NAO_EXISTENTE = "Serial do notebook n�o existente";
    private static final String SERIAL_DO_DESKTOP_NAO_EXISTENTE = "Serial do desktop n�o existente";
    private NotebookDAO notebookDao = new NotebookDAO();
    private DesktopDAO desktopDao = new DesktopDAO();
    private static EquipamentoMediator instancia;

    public static EquipamentoMediator getInstancia() {
        if (instancia == null) {
            instancia = new EquipamentoMediator();
        }
        return instancia;
    }

    private EquipamentoMediator() {}

    public ResultadoMediator validarNotebook(Notebook note) {
        ListaString mensagensAx = new ListaString();
        if (note ==  null) {
            mensagensAx.adicionar("Notebook n�o informado");
            return new ResultadoMediator(false, false, mensagensAx);
        }
        return validar(new DadosEquipamento(
                note.getSerial(), note.getDescricao(),
                note.isEhNovo(), note.getValorEstimado()));
    }
    public ResultadoMediator validarDesktop(Desktop desk) {
        ListaString mensagensAx = new ListaString();
        if (desk ==  null) {
            mensagensAx.adicionar("Desktop n�o informado");
            return new ResultadoMediator(false, false, mensagensAx);
        }
        return validar(new DadosEquipamento(
                desk.getSerial(), desk.getDescricao(),
                desk.isEhNovo(), desk.getValorEstimado()));
    }

    public ResultadoMediator validar(DadosEquipamento equipamento) {
        ListaString mensagens = new ListaString();
        if (equipamento == null) {
            mensagens.adicionar("Dados b�sicos do equipamento n�o informados");
            return new ResultadoMediator(mensagens.tamanho() == 0, false, mensagens);
        }
        String descricao = equipamento.getDescricao();
        if (estaVazia(descricao)) {
            mensagens.adicionar("Descri��o n�o informada");
        } else {
            if (tamanhoExcedido(descricao, 150)) {
                mensagens.adicionar("Descri��o tem mais de 150 caracteres");
            } else if (tamanhoMenor(descricao, 10)) {
                mensagens.adicionar("Descri��o tem menos de 10 caracteres");
            }
        }
        if (estaVazia(equipamento.getSerial())) {
            mensagens.adicionar("Serial n�o informado");
        }
        if (equipamento.getValorEstimado() <= 0) {
            mensagens.adicionar("Valor estimado menor ou igual a zero");
        }
        return new ResultadoMediator(mensagens.tamanho() == 0, false, mensagens);
    }
    public ResultadoMediator incluirNotebook(Notebook note) {
        ListaString mensagensAx = new ListaString();
        if (note ==  null) {
            mensagensAx.adicionar("Notebook n�o informado");
            return new ResultadoMediator(false, false, mensagensAx);
        }
        ResultadoMediator res = validarNotebook(note);
        if (res.isValidado()) {
            ListaString mensagens = new ListaString();
            boolean incluido = notebookDao.incluir(note);
            if (!incluido) {
                mensagens.adicionar("Serial do notebook j� existente");
            }
            return new ResultadoMediator(true, incluido, mensagens);
        } else {
            return res;
        }
    }
    public ResultadoMediator incluirDesktop(Desktop desktop) {
        ListaString mensagensAx = new ListaString();
        if (desktop ==  null) {
            mensagensAx.adicionar("Desktop n�o informado");
            return new ResultadoMediator(false, false, mensagensAx);
        }
        ResultadoMediator res = validarDesktop(desktop);
        if (res.isValidado()) {
            ListaString mensagens = new ListaString();
            boolean incluido = desktopDao.incluir(desktop);
            if (!incluido) {
                mensagens.adicionar("Serial do desktop j� existente");
            }
            return new ResultadoMediator(true, incluido, mensagens);
        } else {
            return res;
        }
    }
    public ResultadoMediator alterarNotebook(Notebook note) {
        ListaString mensagensAx = new ListaString();
        if (note ==  null) {
            mensagensAx.adicionar("Notebook n�o informado");
            return new ResultadoMediator(false, false, mensagensAx);
        }
        ResultadoMediator res = validarNotebook(note);
        if (res.isValidado()) {
            ListaString mensagens = new ListaString();
            boolean incluido = notebookDao.alterar(note);
            if (!incluido) {
                mensagens.adicionar(SERIAL_DO_NOTEBOOK_NAO_EXISTENTE);
            }
            return new ResultadoMediator(true, incluido, mensagens);
        } else {
            return res;
        }
    }
    public ResultadoMediator alterarDesktop(Desktop desktop) {
        ResultadoMediator res = validarDesktop(desktop);
        if (res.isValidado()) {
            ListaString mensagens = new ListaString();
            boolean incluido = desktopDao.alterar(desktop);
            if (!incluido) {
                mensagens.adicionar(SERIAL_DO_DESKTOP_NAO_EXISTENTE);
            }
            return new ResultadoMediator(true, incluido, mensagens);
        } else {
            return res;
        }
    }
    public ResultadoMediator excluirDesktop(String idTipoSerial) {
        ListaString mensagens = new ListaString();
        boolean validado = false;
        boolean excluido = false;
        if (estaVazia(idTipoSerial)) {
            mensagens.adicionar("Id do tipo + serial do desktop n�o informado");
        } else {
            validado = true;
            excluido = desktopDao.excluir(idTipoSerial);
            if (!excluido) {
                mensagens.adicionar(SERIAL_DO_DESKTOP_NAO_EXISTENTE);
            }
        }
        return new ResultadoMediator(validado, excluido, mensagens);
    }
    public ResultadoMediator excluirNotebook(String idTipoSerial) {
        ListaString mensagens = new ListaString();
        boolean validado = false;
        boolean excluido = false;
        if (estaVazia(idTipoSerial)) {
            mensagens.adicionar("Id do tipo + serial do notebook n�o informado");
        } else {
            validado = true;
            excluido = notebookDao.excluir(idTipoSerial);
            if (!excluido) {
                mensagens.adicionar(SERIAL_DO_NOTEBOOK_NAO_EXISTENTE);
            }
        }
        return new ResultadoMediator(validado, excluido, mensagens);
    }
    public Notebook buscarNotebook(String idTipoSerial) {
        if (estaVazia(idTipoSerial)) {
            return null;
        } else {
            return notebookDao.buscar(idTipoSerial);
        }
    }
    public Desktop buscarDesktop(String idTipoSerial) {
        if (estaVazia(idTipoSerial)) {
            return null;
        } else {
            return desktopDao.buscar(idTipoSerial);
        }
    }
}