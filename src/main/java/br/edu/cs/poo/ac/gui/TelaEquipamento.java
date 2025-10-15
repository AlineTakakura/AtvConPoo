package br.edu.cs.poo.ac.gui;

import java.math.BigDecimal;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;

public class TelaEquipamento {

    private static final int ID_TIPO_NOTEBOOK = 1;
    private static final int ID_TIPO_DESKTOP = 2;

    protected Shell shell;
    private final EquipamentoMediator mediator = EquipamentoMediator.getInstancia();

    private Combo comboTipo;
    private Text txtSerial;
    private Text txtDescricao;
    private Button rbNovoNao, rbNovoSim;
    private Text txtValorEstimado;
    private Group grpNotebook;
    private Button rbCarregaNao, rbCarregaSim;
    private Group grpDesktop;
    private Button rbServidorNao, rbServidorSim;
    private Button btnNovo, btnBuscar, btnIncluirAlterar, btnExcluir, btnCancelar, btnLimpar;

    public static void main(String[] args) {
        try {
            TelaEquipamento w = new TelaEquipamento();
            w.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }

    private void createContents() {
        shell = new Shell();
        shell.setSize(720, 520);
        shell.setText("Cadastro de Equipamento (Notebook / Desktop)");

        Label lblTipo = new Label(shell, SWT.NONE);
        lblTipo.setBounds(30, 30, 70, 22);
        lblTipo.setText("Tipo");

        comboTipo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        comboTipo.setBounds(110, 28, 150, 28);
        comboTipo.setItems(new String[] { "Notebook", "Desktop" });
        comboTipo.select(0);

        Label lblSerial = new Label(shell, SWT.NONE);
        lblSerial.setBounds(300, 30, 60, 22);
        lblSerial.setText("Serial");

        txtSerial = new Text(shell, SWT.BORDER);
        txtSerial.setBounds(360, 28, 180, 28);

        btnNovo = new Button(shell, SWT.PUSH);
        btnNovo.setBounds(560, 28, 60, 28);
        btnNovo.setText("Novo");

        btnBuscar = new Button(shell, SWT.PUSH);
        btnBuscar.setBounds(630, 28, 60, 28);
        btnBuscar.setText("Buscar");

        Label lblDescricao = new Label(shell, SWT.NONE);
        lblDescricao.setBounds(30, 90, 100, 22);
        lblDescricao.setText("Descrição");

        txtDescricao = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        txtDescricao.setBounds(110, 90, 580, 80);
        txtDescricao.setEnabled(false);

        Label lblENovo = new Label(shell, SWT.NONE);
        lblENovo.setBounds(30, 190, 60, 22);
        lblENovo.setText("É novo");

        rbNovoNao = new Button(shell, SWT.RADIO);
        rbNovoNao.setBounds(110, 190, 60, 22);
        rbNovoNao.setText("NÃO");
        rbNovoNao.setSelection(true);
        rbNovoNao.setEnabled(false);

        rbNovoSim = new Button(shell, SWT.RADIO);
        rbNovoSim.setBounds(180, 190, 60, 22);
        rbNovoSim.setText("SIM");
        rbNovoSim.setEnabled(false);

        Label lblValor = new Label(shell, SWT.NONE);
        lblValor.setBounds(260, 190, 100, 22);
        lblValor.setText("Valor estimado");

        txtValorEstimado = new Text(shell, SWT.BORDER);
        txtValorEstimado.setBounds(360, 188, 140, 28);
        txtValorEstimado.setToolTipText("Use vírgula para decimal (ex.: 1234,56)");
        txtValorEstimado.setEnabled(false);
        txtValorEstimado.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String s = somenteNumeroVirgula(txtValorEstimado.getText());
                txtValorEstimado.setText(s);
            }
        });

        grpNotebook = new Group(shell, SWT.NONE);
        grpNotebook.setText("Notebook");
        grpNotebook.setBounds(30, 240, 300, 70);
        grpNotebook.setEnabled(false);

        Label lblCarrega = new Label(grpNotebook, SWT.NONE);
        lblCarrega.setBounds(10, 30, 160, 22);
        lblCarrega.setText("Carrega dados sensíveis");

        rbCarregaNao = new Button(grpNotebook, SWT.RADIO);
        rbCarregaNao.setBounds(180, 30, 50, 22);
        rbCarregaNao.setText("NÃO");
        rbCarregaNao.setSelection(true);

        rbCarregaSim = new Button(grpNotebook, SWT.RADIO);
        rbCarregaSim.setBounds(240, 30, 50, 22);
        rbCarregaSim.setText("SIM");

        grpDesktop = new Group(shell, SWT.NONE);
        grpDesktop.setText("Desktop");
        grpDesktop.setBounds(360, 240, 330, 70);
        grpDesktop.setEnabled(false);

        Label lblServidor = new Label(grpDesktop, SWT.NONE);
        lblServidor.setBounds(10, 30, 90, 22);
        lblServidor.setText("É Servidor");

        rbServidorNao = new Button(grpDesktop, SWT.RADIO);
        rbServidorNao.setBounds(110, 30, 50, 22);
        rbServidorNao.setText("NÃO");
        rbServidorNao.setSelection(true);

        rbServidorSim = new Button(grpDesktop, SWT.RADIO);
        rbServidorSim.setBounds(170, 30, 50, 22);
        rbServidorSim.setText("SIM");

        btnIncluirAlterar = new Button(shell, SWT.PUSH);
        btnIncluirAlterar.setBounds(50, 360, 100, 30);
        btnIncluirAlterar.setText("Incluir");
        btnIncluirAlterar.setEnabled(false);

        btnExcluir = new Button(shell, SWT.PUSH);
        btnExcluir.setBounds(170, 360, 100, 30);
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);

        btnCancelar = new Button(shell, SWT.PUSH);
        btnCancelar.setBounds(290, 360, 100, 30);
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);

        btnLimpar = new Button(shell, SWT.PUSH);
        btnLimpar.setBounds(410, 360, 100, 30);
        btnLimpar.setText("Limpar");

        configurarListeners();
        aplicarVisibilidadeExtras();
    }

    private void configurarListeners() {

        comboTipo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                aplicarVisibilidadeExtras();
            }
        });

        btnLimpar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (txtSerial.getEnabled()) txtSerial.setText("");
                limparDados();
            }
        });

        btnCancelar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resetarTela();
            }
        });

        btnNovo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int idTipo = getIdTipoSelecionado();
                String serial = txtSerial.getText().trim();

                if (StringUtils.estaVazia(serial)) {
                    msg("Serial deve ser preenchido!", SWT.ICON_WARNING);
                    txtSerial.setFocus();
                    return;
                }

                Equipamento achado = mediator.buscar(idTipo, serial);
                if (achado != null) {
                    msg("Equipamento já existente!", SWT.ICON_WARNING);
                    return;
                }

                limparDados();
                btnIncluirAlterar.setText("Incluir");
                setModoEdicao(true);
            }
        });

        btnBuscar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int idTipo = getIdTipoSelecionado();
                String serial = txtSerial.getText().trim();

                if (StringUtils.estaVazia(serial)) {
                    msg("Serial deve ser preenchido!", SWT.ICON_WARNING);
                    txtSerial.setFocus();
                    return;
                }

                Equipamento eq = mediator.buscar(idTipo, serial);
                if (eq == null) {
                    msg("Equipamento não existente!", SWT.ICON_WARNING);
                    return;
                }

                preencherCampos(eq);
                btnIncluirAlterar.setText("Alterar");
                setModoEdicao(true);
                btnExcluir.setEnabled(true);
            }
        });

        btnIncluirAlterar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String modo = btnIncluirAlterar.getText();
                Equipamento eq = montarEquipamentoDaTela();
                if (eq == null) {
                    msg("Preencha Tipo e Serial corretamente.", SWT.ICON_WARNING);
                    return;
                }

                ResultadoMediator resultado;
                String sucesso;

                if ("Incluir".equals(modo)) {
                    resultado = mediator.incluir(eq);
                    sucesso = "Inclusão realizada com sucesso";
                } else {
                    resultado = mediator.alterar(eq);
                    sucesso = "Alteração realizada com sucesso";
                }

                if (resultado.isOperacaoRealizada()) {
                    msg(sucesso, SWT.ICON_INFORMATION);
                    resetarTela();
                } else if (!resultado.isValidado()) {
                    exibirErros(resultado.getMensagensErro());
                } else {
                    msg(juntarMensagens(resultado.getMensagensErro()), SWT.ICON_ERROR);
                }
            }
        });

        btnExcluir.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int idTipo = getIdTipoSelecionado();
                String serial = txtSerial.getText().trim();

                MessageBox box = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
                box.setText("Confirmação");
                box.setMessage("Tem certeza que deseja excluir o equipamento " + serial + "?");
                if (box.open() != SWT.YES) return;

                ResultadoMediator r = mediator.excluir(idTipo, serial);
                if (r.isOperacaoRealizada()) {
                    msg("Exclusão realizada com sucesso", SWT.ICON_INFORMATION);
                    resetarTela();
                } else {
                    msg("Exclusão não pôde ser realizada.\n" + juntarMensagens(r.getMensagensErro()), SWT.ICON_ERROR);
                }
            }
        });
    }

    private void aplicarVisibilidadeExtras() {
        boolean isNotebook = (comboTipo.getSelectionIndex() == 0);
        grpNotebook.setVisible(isNotebook);
        grpDesktop.setVisible(!isNotebook);
        boolean habilitar = txtDescricao.getEnabled();
        grpNotebook.setEnabled(isNotebook && habilitar);
        grpDesktop.setEnabled(!isNotebook && habilitar);
    }

    private void setModoEdicao(boolean edicao) {
        comboTipo.setEnabled(!edicao);
        txtSerial.setEnabled(!edicao);
        btnNovo.setEnabled(!edicao);
        btnBuscar.setEnabled(!edicao);
        txtDescricao.setEnabled(edicao);
        rbNovoNao.setEnabled(edicao);
        rbNovoSim.setEnabled(edicao);
        txtValorEstimado.setEnabled(edicao);
        boolean isNotebook = (comboTipo.getSelectionIndex() == 0);
        grpNotebook.setEnabled(edicao && isNotebook);
        grpDesktop.setEnabled(edicao && !isNotebook);
        btnIncluirAlterar.setEnabled(edicao);
        btnCancelar.setEnabled(edicao);
        if (!edicao) btnExcluir.setEnabled(false);
        aplicarVisibilidadeExtras();
    }

    private void resetarTela() {
        limparTudo();
        btnIncluirAlterar.setText("Incluir");
        setModoEdicao(false);
    }

    private void limparDados() {
        txtDescricao.setText("");
        rbNovoNao.setSelection(true);
        rbNovoSim.setSelection(false);
        txtValorEstimado.setText("");
        rbCarregaNao.setSelection(true);
        rbCarregaSim.setSelection(false);
        rbServidorNao.setSelection(true);
        rbServidorSim.setSelection(false);
    }

    private void limparTudo() {
        comboTipo.select(0);
        txtSerial.setText("");
        limparDados();
        aplicarVisibilidadeExtras();
    }

    private void preencherCampos(Equipamento eq) {
        if (eq instanceof Notebook) {
            comboTipo.select(0);
            Notebook n = (Notebook) eq;
            txtSerial.setText(n.getSerial());
            txtDescricao.setText(n.getDescricao() == null ? "" : n.getDescricao());
            if (n.isNovo()) rbNovoSim.setSelection(true); else rbNovoNao.setSelection(true);
            txtValorEstimado.setText(valorToTexto(n.getValorEstimado()));
            if (n.isCarregaDadosSensiveis()) rbCarregaSim.setSelection(true); else rbCarregaNao.setSelection(true);
        } else if (eq instanceof Desktop) {
            comboTipo.select(1);
            Desktop d = (Desktop) eq;
            txtSerial.setText(d.getSerial());
            txtDescricao.setText(d.getDescricao() == null ? "" : d.getDescricao());
            if (d.isNovo()) rbNovoSim.setSelection(true); else rbNovoNao.setSelection(true);
            txtValorEstimado.setText(valorToTexto(d.getValorEstimado()));
            if (d.isServidor()) rbServidorSim.setSelection(true); else rbServidorNao.setSelection(true);
        }
        aplicarVisibilidadeExtras();
    }

    private Equipamento montarEquipamentoDaTela() {
        int idTipo = getIdTipoSelecionado();
        String serial = txtSerial.getText().trim();
        String descricao = txtDescricao.getText().trim();
        boolean ehNovo = rbNovoSim.getSelection();
        BigDecimal valor = parseValor(txtValorEstimado.getText().trim());

        if (StringUtils.estaVazia(serial)) return null;

        if (idTipo == ID_TIPO_NOTEBOOK) {
            Notebook n = new Notebook();
            n.setIdTipo(ID_TIPO_NOTEBOOK);
            n.setSerial(serial);
            n.setDescricao(descricao);
            n.setNovo(ehNovo);
            n.setValorEstimado(valor);
            n.setCarregaDadosSensiveis(rbCarregaSim.getSelection());
            return n;
        } else {
            Desktop d = new Desktop();
            d.setIdTipo(ID_TIPO_DESKTOP);
            d.setSerial(serial);
            d.setDescricao(descricao);
            d.setNovo(ehNovo);
            d.setValorEstimado(valor);
            d.setServidor(rbServidorSim.getSelection());
            return d;
        }
    }

    private int getIdTipoSelecionado() {
        return comboTipo.getSelectionIndex() == 0 ? ID_TIPO_NOTEBOOK : ID_TIPO_DESKTOP;
    }

    private void msg(String mensagem, int tipo) {
        MessageBox box = new MessageBox(shell, tipo | SWT.OK);
        box.setText("Aviso");
        box.setMessage(mensagem);
        box.open();
    }

    private void exibirErros(ListaString erros) {
        msg("Erros de Validação:\n\n" + juntarMensagens(erros), SWT.ICON_ERROR);
    }

    private String juntarMensagens(ListaString erros) {
        String[] arr = erros.listar();
        if (arr == null || arr.length == 0) return "";
        return String.join("\n", arr);
    }

    private String somenteNumeroVirgula(String s) {
        if (s == null) return "";
        return s.replaceAll("[^0-9,]", "");
    }

    private BigDecimal parseValor(String texto) {
        if (StringUtils.estaVazia(texto)) return new BigDecimal("0.00");
        String norm = texto.replace(".", "").replace(",", ".");
        try {
            return new BigDecimal(norm);
        } catch (Exception e) {
            return new BigDecimal("0.00");
        }
    }

    private String valorToTexto(BigDecimal v) {
        if (v == null) return "";
        String s = v.toPlainString();
        return s.replace(".", ",");
    }
}
