package br.edu.cs.poo.ac.ordem.tela;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.beans.Beans;
import java.lang.reflect.Method;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class TelaEquipamento extends JFrame {

    private static final long serialVersionUID = 1L;

    private enum Modo { INICIAL, NOVO, EDICAO }

    private JComboBox<String> cmbTipo;
    private JTextField txtSerial;
    private JTextArea txtDescricao;
    private JRadioButton rbNovoSim, rbNovoNao;
    private JFormattedTextField txtValor;

    private JCheckBox chkDadosSensiveis;
    private JCheckBox chkEhServidor;

    private JButton btnNovo, btnBuscar, btnAdicionar, btnAlterar, btnExcluir, btnCancelar, btnLimpar;

    private final EquipamentoMediator mediator = EquipamentoMediator.getInstancia();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                if (!Beans.isDesignTime()) {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                TelaEquipamento t = new TelaEquipamento();
                t.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaEquipamento() {
        if (!Beans.isDesignTime()) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ignore) {}
        }

        setTitle("Registrar Equipamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 420);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblGeral = new JLabel("Geral:");
        lblGeral.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        lblGeral.setBounds(20, 10, 70, 23);
        getContentPane().add(lblGeral);

        JLabel lblEspecifico = new JLabel("Específico:");
        lblEspecifico.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        lblEspecifico.setBounds(20, 210, 80, 23);
        getContentPane().add(lblEspecifico);

        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setBounds(20, 41, 80, 17);
        getContentPane().add(lblTipo);

        cmbTipo = new JComboBox<>(new String[] { "Notebook", "Desktop" });
        cmbTipo.setBounds(20, 64, 150, 22);
        getContentPane().add(cmbTipo);

        JLabel lblSerial = new JLabel("Serial");
        lblSerial.setBounds(190, 41, 80, 17);
        getContentPane().add(lblSerial);

        txtSerial = new JTextField();
        txtSerial.setToolTipText("Digite o serial (ID do equipamento)");
        txtSerial.setBounds(190, 64, 200, 22);
        getContentPane().add(txtSerial);

        Font fBtn = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        btnNovo = new JButton("NOVO");
        btnNovo.setFont(fBtn);
        btnNovo.setBounds(410, 60, 95, 30);
        getContentPane().add(btnNovo);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(fBtn);
        btnBuscar.setBounds(515, 60, 105, 30);
        getContentPane().add(btnBuscar);

        JSeparator sep1 = new JSeparator();
        sep1.setBounds(10, 100, 660, 2);
        getContentPane().add(sep1);

        JLabel lblDesc = new JLabel("Descrição");
        lblDesc.setBounds(20, 110, 100, 17);
        getContentPane().add(lblDesc);

        txtDescricao = new JTextArea();
        txtDescricao.setToolTipText("Descreva o equipamento");
        JScrollPane spDesc = new JScrollPane(txtDescricao);
        spDesc.setBounds(20, 132, 420, 60);
        getContentPane().add(spDesc);

        JLabel lblNovo = new JLabel("É novo?");
        lblNovo.setBounds(460, 110, 60, 17);
        getContentPane().add(lblNovo);

        rbNovoNao = new JRadioButton("Não");
        rbNovoSim = new JRadioButton("Sim");
        rbNovoNao.setSelected(true);
        ButtonGroup bgNovo = new ButtonGroup();
        bgNovo.add(rbNovoNao);
        bgNovo.add(rbNovoSim);
        rbNovoNao.setBackground(Color.WHITE);
        rbNovoSim.setBackground(Color.WHITE);
        rbNovoNao.setBounds(460, 132, 60, 21);
        rbNovoSim.setBounds(520, 132, 60, 21);
        getContentPane().add(rbNovoNao);
        getContentPane().add(rbNovoSim);

        JLabel lblValor = new JLabel("Valor estimado");
        lblValor.setBounds(460, 160, 120, 17);
        getContentPane().add(lblValor);

        NumberFormat nf = new DecimalFormat("#,##0.00");
        NumberFormatter nff = new NumberFormatter(nf);
        nff.setValueClass(Double.class);
        nff.setAllowsInvalid(false);
        txtValor = new JFormattedTextField(nff);
        txtValor.setValue(0.0);
        txtValor.setBounds(460, 182, 160, 22);
        getContentPane().add(txtValor);

        chkDadosSensiveis = new JCheckBox("Carrega dados sensíveis? (Notebook)");
        chkDadosSensiveis.setBackground(Color.WHITE);
        chkDadosSensiveis.setBounds(20, 240, 300, 22);
        getContentPane().add(chkDadosSensiveis);

        chkEhServidor = new JCheckBox("É servidor? (Desktop)");
        chkEhServidor.setBackground(Color.WHITE);
        chkEhServidor.setBounds(340, 240, 200, 22);
        getContentPane().add(chkEhServidor);

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setFont(fBtn);
        btnAdicionar.setBounds(20, 300, 120, 30);
        getContentPane().add(btnAdicionar);

        btnAlterar = new JButton("ALTERAR");
        btnAlterar.setFont(fBtn);
        btnAlterar.setBounds(145, 300, 100, 30);
        getContentPane().add(btnAlterar);

        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setFont(fBtn);
        btnExcluir.setBounds(250, 300, 95, 30);
        getContentPane().add(btnExcluir);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setFont(fBtn);
        btnCancelar.setBounds(350, 300, 110, 30);
        getContentPane().add(btnCancelar);

        btnLimpar = new JButton("LIMPAR");
        btnLimpar.setFont(fBtn);
        btnLimpar.setBounds(465, 300, 100, 30);
        getContentPane().add(btnLimpar);

        setModo(Modo.INICIAL);

        cmbTipo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) ajustarCamposEspecificos();
        });

        btnNovo.addActionListener(e -> {
            String serial = txtSerial.getText().trim();
            if (serial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Serial deve ser preenchido!");
                return;
            }
            String id = getIdEquipamento();
            Equipamento existente = isNotebook() ? mediator.buscarNotebook(id) : mediator.buscarDesktop(id);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, "Equipamento já existente!");
                return;
            }
            limparDados(false);
            setModo(Modo.NOVO);
        });

        btnBuscar.addActionListener(e -> {
            if (Beans.isDesignTime()) return;
            String serial = txtSerial.getText().trim();
            if (serial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Serial deve ser preenchido!");
                return;
            }
            String id = getIdEquipamento();
            Equipamento eq = isNotebook() ? mediator.buscarNotebook(id) : mediator.buscarDesktop(id);
            if (eq == null) {
                JOptionPane.showMessageDialog(this, "Nenhum equipamento encontrado.", "Resultado da Busca", JOptionPane.WARNING_MESSAGE);
                return;
            }
            preencherTela(eq);
            setModo(Modo.EDICAO);
        });

        btnAdicionar.addActionListener(e -> {
            try {
                ResultadoMediator r = isNotebook()
                        ? mediator.incluirNotebook(construirNotebook())
                        : mediator.incluirDesktop(construirDesktop());
                if (!r.isOperacaoRealizada()) {
                    String s = "Operação não realizada pois:";
                    for (String m : r.getMensagensErro().listar()) s += "\n" + m;
                    JOptionPane.showMessageDialog(this, s, "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Equipamento cadastrado com sucesso!", "Resultado da Inclusão", JOptionPane.INFORMATION_MESSAGE);
                    setModo(Modo.INICIAL);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos. Verifique os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnAlterar.addActionListener(e -> {
            try {
                ResultadoMediator r = isNotebook()
                        ? mediator.alterarNotebook(construirNotebook())
                        : mediator.alterarDesktop(construirDesktop());
                if (!r.isOperacaoRealizada()) {
                    String s = "Operação não realizada pois:";
                    for (String m : r.getMensagensErro().listar()) s += "\n" + m;
                    JOptionPane.showMessageDialog(this, s, "Resultado da Alteração", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Cadastro alterado com sucesso!", "Resultado da Alteração", JOptionPane.INFORMATION_MESSAGE);
                    setModo(Modo.INICIAL);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos. Verifique os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            String serial = txtSerial.getText().trim();
            if (serial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o Serial para excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = getIdEquipamento();
            ResultadoMediator r = isNotebook() ? mediator.excluirNotebook(id) : mediator.excluirDesktop(id);
            if (!r.isOperacaoRealizada()) {
                String s = "Operação não realizada pois:";
                for (String m : r.getMensagensErro().listar()) s += "\n" + m;
                JOptionPane.showMessageDialog(this, s, "Resultado da Exclusão", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Resultado da Exclusão", JOptionPane.INFORMATION_MESSAGE);
                setModo(Modo.INICIAL);
            }
        });

        btnCancelar.addActionListener(e -> setModo(Modo.INICIAL));

        btnLimpar.addActionListener(e -> {
            if (txtSerial.isEnabled()) txtSerial.setText("");
            limparDados(true);
        });

        ajustarCamposEspecificos();
    }

    private void setModo(Modo m) {
        boolean inicial = m == Modo.INICIAL;
        boolean novo = m == Modo.NOVO;
        boolean edicao = m == Modo.EDICAO;

        cmbTipo.setEnabled(inicial);
        txtSerial.setEnabled(inicial);

        txtDescricao.setEnabled(novo || edicao);
        rbNovoSim.setEnabled(novo || edicao);
        rbNovoNao.setEnabled(novo || edicao);
        txtValor.setEnabled(novo || edicao);
        chkDadosSensiveis.setEnabled(novo || edicao);
        chkEhServidor.setEnabled(novo || edicao);

        btnNovo.setEnabled(inicial);
        btnBuscar.setEnabled(inicial);

        btnAdicionar.setEnabled(novo);
        btnAlterar.setEnabled(edicao);
        btnExcluir.setEnabled(edicao);
        btnCancelar.setEnabled(!inicial);
        btnLimpar.setEnabled(true);

        if (inicial) {
            txtSerial.setText("");
            limparDados(true);
        }
    }

    private void limparDados(boolean manterTipo) {
        if (!manterTipo) cmbTipo.setSelectedIndex(0);
        txtDescricao.setText("");
        rbNovoNao.setSelected(true);
        txtValor.setValue(0.0);
        chkDadosSensiveis.setSelected(false);
        chkEhServidor.setSelected(false);
        ajustarCamposEspecificos();
    }

    private void ajustarCamposEspecificos() {
        boolean note = isNotebook();
        chkDadosSensiveis.setVisible(note);
        chkEhServidor.setVisible(!note);
    }

    private Notebook construirNotebook() {
        double valor = (txtValor.getValue() instanceof Number)
                ? ((Number) txtValor.getValue()).doubleValue() : 0.0;
        return new Notebook(
                getIdEquipamento(),
                txtDescricao.getText(),
                rbNovoSim.isSelected(),
                valor,
                chkDadosSensiveis.isSelected()
        );
    }

    private Desktop construirDesktop() {
        double valor = (txtValor.getValue() instanceof Number)
                ? ((Number) txtValor.getValue()).doubleValue() : 0.0;
        return new Desktop(
                getIdEquipamento(),
                txtDescricao.getText(),
                rbNovoSim.isSelected(),
                valor,
                chkEhServidor.isSelected()
        );
    }

    private String getIdEquipamento() {
        return (isNotebook() ? "NO" : "DE") + txtSerial.getText().trim();
    }

    private boolean isNotebook() {
        return "Notebook".equals(cmbTipo.getSelectedItem());
    }

    private void preencherTela(Equipamento eq) {
        if (eq instanceof Notebook) cmbTipo.setSelectedItem("Notebook");
        else cmbTipo.setSelectedItem("Desktop");
        ajustarCamposEspecificos();

        String desc = tryGetString(eq, "getDescricao");
        if (desc != null) txtDescricao.setText(desc);

        Boolean ehNovo = tryGetBoolean(eq, "isEhNovo");
        if (ehNovo != null) {
            if (ehNovo) rbNovoSim.setSelected(true);
            else rbNovoNao.setSelected(true);
        }

        Double valor = tryGetDouble(eq, "getValorEstimado");
        if (valor != null) txtValor.setValue(valor);

        if (eq instanceof Notebook) {
            Boolean sens = tryGetBoolean(eq, "isCarregaDadosSensiveis");
            chkDadosSensiveis.setSelected(Boolean.TRUE.equals(sens));
        } else if (eq instanceof Desktop) {
            Boolean serv = tryGetBoolean(eq, "isEhServidor");
            chkEhServidor.setSelected(Boolean.TRUE.equals(serv));
        }
    }

    private static String tryGetString(Object o, String method) {
        try { Method m = o.getClass().getMethod(method); Object r = m.invoke(o); return r != null ? r.toString() : null; }
        catch (Exception ignore) { return null; }
    }

    private static Boolean tryGetBoolean(Object o, String method) {
        try { Method m = o.getClass().getMethod(method); Object r = m.invoke(o); return (r instanceof Boolean) ? (Boolean) r : null; }
        catch (Exception ignore) { return null; }
    }

    private static Double tryGetDouble(Object o, String method) {
        try { Method m = o.getClass().getMethod(method); Object r = m.invoke(o); return (r instanceof Number) ? ((Number) r).doubleValue() : null; }
        catch (Exception ignore) { return null; }
    }
}
