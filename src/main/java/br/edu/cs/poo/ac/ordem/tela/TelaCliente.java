package br.edu.cs.poo.ac.ordem.tela;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.Beans;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.text.*;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class TelaCliente extends JFrame {

    private static final long serialVersionUID = 1L;

    private enum Modo { INICIAL, NOVO, EDICAO }

    private JTextField txtCpfCnpj;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JFormattedTextField txtData;
    private JCheckBox chkZap;

    private JButton btnBuscar, btnAdicionar, btnExcluir, btnAlterar;
    private JButton btnNovo, btnCancelar, btnLimpar;

    private final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaCliente t = new TelaCliente();
                t.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaCliente() {
        if (!Beans.isDesignTime()) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ignore) {}
        }

        setTitle("Registrar Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 340);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblGeral = new JLabel("Geral:");
        lblGeral.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        lblGeral.setBounds(20, 10, 70, 23);
        getContentPane().add(lblGeral);

        JLabel lblContato = new JLabel("Contato:");
        lblContato.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        lblContato.setBounds(286, 16, 80, 17);
        getContentPane().add(lblContato);

        JLabel lblNome = new JLabel("Nome Completo");
        lblNome.setBounds(20, 96, 104, 17);
        getContentPane().add(lblNome);

        JLabel lblDoc = new JLabel("CPF/CNPJ");
        lblDoc.setBounds(20, 41, 80, 17);
        getContentPane().add(lblDoc);

        JLabel lblMail = new JLabel("E-mail");
        lblMail.setBounds(276, 41, 54, 17);
        getContentPane().add(lblMail);

        JLabel lblFone = new JLabel("Celular");
        lblFone.setBounds(276, 96, 54, 17);
        getContentPane().add(lblFone);

        JLabel lblDataCad = new JLabel("Data do cadastro:");
        lblDataCad.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        lblDataCad.setBounds(20, 168, 122, 29);
        getContentPane().add(lblDataCad);

        txtNome = new JTextField();
        txtNome.setToolTipText("Digite o nome completo do cliente");
        txtNome.setBounds(20, 124, 221, 21);
        getContentPane().add(txtNome);

        txtCpfCnpj = new JTextField();
        txtCpfCnpj.setBounds(20, 64, 221, 21);
        getContentPane().add(txtCpfCnpj);
        txtCpfCnpj.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String dig = txtCpfCnpj.getText().replaceAll("\\D", "");
                if (dig.isEmpty()) { txtCpfCnpj.setText(""); return; }
                if (dig.length() == 11) {
                    txtCpfCnpj.setText(dig.substring(0,3)+"."+dig.substring(3,6)+"."+dig.substring(6,9)+"-"+dig.substring(9));
                } else if (dig.length() == 14) {
                    txtCpfCnpj.setText(dig.substring(0,2)+"."+dig.substring(2,5)+"."+dig.substring(5,8)+"/"+dig.substring(8,12)+"-"+dig.substring(12));
                } else {
                    txtCpfCnpj.setText(dig);
                }
            }
        });
        txtCpfCnpj.setToolTipText("Digite o cpf/cnpj do cliente");

        txtEmail = new JTextField();
        txtEmail.setToolTipText("Digite email do cliente");
        txtEmail.setBounds(276, 64, 207, 21);
        getContentPane().add(txtEmail);

        txtCelular = new JTextField();
        txtCelular.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String raw = txtCelular.getText();
                if (raw.matches("^\\(\\d{2}\\)\\d{8,9}$")) return;
                String d = raw.replaceAll("\\D", "");
                if (d.length() == 11 || d.length() == 10) {
                    txtCelular.setText("(" + d.substring(0,2) + ")" + d.substring(2));
                }
            }
        });
        txtCelular.setToolTipText("Digite o celular do cliente");
        txtCelular.setBounds(276, 119, 207, 21);
        getContentPane().add(txtCelular);

        chkZap = new JCheckBox("é WhatsApp?");
        chkZap.setBackground(Color.WHITE);
        chkZap.setBounds(276, 146, 110, 17);
        getContentPane().add(chkZap);

        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            txtData = new JFormattedTextField(mf);
        } catch (Exception ex) {
            txtData = new JFormattedTextField();
        }
        txtData.setToolTipText("Data Atual");
        txtData.setBounds(20, 203, 112, 21);
        getContentPane().add(txtData);

        if (!Beans.isDesignTime()) {
            LocalDate hoje = LocalDate.now();
            txtData.setText(hoje.format(FMT));
            txtData.setFocusLostBehavior(JFormattedTextField.COMMIT);
            txtData.setCaretPosition(txtData.getText().length());
        }

        Font fBtn = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        btnNovo = new JButton("NOVO");
        btnNovo.setFont(fBtn);
        btnNovo.setForeground(Color.BLACK);
        btnNovo.setBounds(20, 230, 95, 30);
        getContentPane().add(btnNovo);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(fBtn);
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setBounds(120, 230, 105, 30);
        getContentPane().add(btnBuscar);

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setFont(fBtn);
        btnAdicionar.setForeground(Color.BLACK);
        btnAdicionar.setBounds(230, 230, 120, 30);
        getContentPane().add(btnAdicionar);

        btnAlterar = new JButton("ALTERAR");
        btnAlterar.setFont(fBtn);
        btnAlterar.setForeground(Color.BLACK);
        btnAlterar.setBounds(355, 230, 95, 30);
        getContentPane().add(btnAlterar);

        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setFont(fBtn);
        btnExcluir.setForeground(Color.BLACK);
        btnExcluir.setBounds(20, 265, 95, 30);
        getContentPane().add(btnExcluir);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setFont(fBtn);
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setBounds(120, 265, 105, 30);
        getContentPane().add(btnCancelar);

        btnLimpar = new JButton("LIMPAR");
        btnLimpar.setFont(fBtn);
        btnLimpar.setForeground(Color.BLACK);
        btnLimpar.setBounds(230, 265, 120, 30);
        getContentPane().add(btnLimpar);

        setModo(Modo.INICIAL);

        btnNovo.addActionListener(e -> {
            String id = cpfCnpjLimpo();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF/CNPJ deve ser preenchido!");
                return;
            }
            ClienteMediator med = ClienteMediator.getInstancia();
            if (med.buscar(id) != null) {
                JOptionPane.showMessageDialog(this, "Cliente já existente!");
                return;
            }
            limparDados();
            setModo(Modo.NOVO);
        });

        btnBuscar.addActionListener(e -> {
            if (Beans.isDesignTime()) return;
            String id = cpfCnpjLimpo();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF/CNPJ deve ser preenchido!");
                return;
            }
            ClienteMediator med = ClienteMediator.getInstancia();
            Cliente c = med.buscar(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado.", "Resultado da Busca", JOptionPane.WARNING_MESSAGE);
                return;
            }
            preencher(c);
            setModo(Modo.EDICAO);
        });

        btnAdicionar.addActionListener(e -> {
            ClienteMediator med = ClienteMediator.getInstancia();
            try {
                LocalDate dt = LocalDate.parse(txtData.getText(), FMT);
                Contato ct = new Contato(txtEmail.getText(), txtCelular.getText(), chkZap.isSelected());
                Cliente cl = new Cliente(cpfCnpjLimpo(), txtNome.getText(), ct, dt);
                ResultadoMediator r = med.incluir(cl);
                if (!r.isOperacaoRealizada()) {
                    String s = "Operação não realizada pois:";
                    for (String m : r.getMensagensErro().listar()) s += "\n" + m;
                    JOptionPane.showMessageDialog(this, s, "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Resultado da Inclusão", JOptionPane.INFORMATION_MESSAGE);
                    setModo(Modo.INICIAL);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data inválida (use dd/MM/yyyy).", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnAlterar.addActionListener(e -> {
            ClienteMediator med = ClienteMediator.getInstancia();
            try {
                LocalDate dt = LocalDate.parse(txtData.getText(), FMT);
                Contato ct = new Contato(txtEmail.getText(), txtCelular.getText(), chkZap.isSelected());
                Cliente cl = new Cliente(cpfCnpjLimpo(), txtNome.getText(), ct, dt);
                ResultadoMediator r = med.alterar(cl);
                if (!r.isOperacaoRealizada()) {
                    String s = "Operação não realizada pois:";
                    for (String m : r.getMensagensErro().listar()) s += "\n" + m;
                    JOptionPane.showMessageDialog(this, s, "Resultado da Alteração", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Cadastro alterado com sucesso!", "Resultado da Alteração", JOptionPane.INFORMATION_MESSAGE);
                    setModo(Modo.INICIAL);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data inválida (use dd/MM/yyyy).", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            ClienteMediator med = ClienteMediator.getInstancia();
            String id = cpfCnpjLimpo();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o CPF/CNPJ para excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ResultadoMediator r = med.excluir(id);
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
            if (txtCpfCnpj.isEnabled()) txtCpfCnpj.setText("");
            limparDados();
        });
    }

    private String cpfCnpjLimpo() {
        return txtCpfCnpj.getText().replaceAll("\\D", "");
    }

    private void setModo(Modo m) {
        boolean inicial = m == Modo.INICIAL;
        boolean novo = m == Modo.NOVO;
        boolean edicao = m == Modo.EDICAO;

        txtCpfCnpj.setEnabled(inicial);

        txtNome.setEnabled(novo || edicao);
        txtEmail.setEnabled(novo || edicao);
        txtCelular.setEnabled(novo || edicao);
        chkZap.setEnabled(novo || edicao);
        txtData.setEnabled(novo || edicao);

        btnNovo.setEnabled(inicial);
        btnBuscar.setEnabled(inicial);

        btnAdicionar.setEnabled(novo);
        btnAlterar.setEnabled(edicao);
        btnExcluir.setEnabled(edicao);
        btnCancelar.setEnabled(!inicial);
        btnLimpar.setEnabled(true);

        if (inicial) {
            txtCpfCnpj.setText("");
            limparDados();
            txtData.setText(LocalDate.now().format(FMT));
        }
    }

    private void limparDados() {
        txtNome.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        chkZap.setSelected(false);
    }

    private void preencher(Cliente c) {
        txtCpfCnpj.setText(s(c.getCpfCnpj()));
        txtNome.setText(s(c.getNome()));
        if (c.getContato() != null) {
            txtEmail.setText(s(c.getContato().getEmail()));
            txtCelular.setText(s(c.getContato().getCelular()));
            chkZap.setSelected(c.getContato().isEhZap());
        } else {
            txtEmail.setText("");
            txtCelular.setText("");
            chkZap.setSelected(false);
        }
        if (txtData.getText() == null || txtData.getText().trim().isEmpty()) {
            txtData.setText(LocalDate.now().format(FMT));
        }
    }

    private static String s(String v) {
        return v == null ? "" : v;
    }
}
