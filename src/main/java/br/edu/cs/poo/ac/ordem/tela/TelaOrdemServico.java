package br.edu.cs.poo.ac.ordem.tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;
import br.edu.cs.poo.ac.ordem.mediators.DadosOrdemServico;
import br.edu.cs.poo.ac.ordem.mediators.OrdemServicoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class TelaOrdemServico extends JFrame {

    private static final long serialVersionUID = 1L;

    private final OrdemServicoMediator mediator = OrdemServicoMediator.getInstancia();

    // INCLUIR
    private JTextField txtCpfCliente;
    private JTextField txtIdEquipamento;
    private JTextField txtVendedor;
    private JComboBox<PrecoBase> cbPrecoBase;

    // CANCELAR
    private JTextField txtNumOrdemCancel;
    private JTextArea txtMotivoCancel;

    // FECHAR
    private JTextField txtNumOrdemFechar;
    private JTextArea txtRelatorio;
    private JCheckBox chkPago;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new TelaOrdemServico().setVisible(true);
        });
    }

    public TelaOrdemServico() {
        setTitle("Gestão de Ordens de Serviço");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 520);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Cabeçalho
        JLabel titulo = new JLabel("Ordens de Serviço", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(new Color(9, 74, 151));
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        contentPane.add(titulo, BorderLayout.NORTH);

        // Abas
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Incluir OS", criarPainelIncluir());
        tabbedPane.addTab("Cancelar OS", criarPainelCancelar());
        tabbedPane.addTab("Fechar OS", criarPainelFechar());

        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel criarPainelIncluir() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Nova Ordem de Serviço"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        txtCpfCliente = new JTextField();
        txtIdEquipamento = new JTextField();
        txtVendedor = new JTextField();
        cbPrecoBase = new JComboBox<>(PrecoBase.values());

        int linha = 0;

        // CPF/CNPJ
        c.gridx = 0; c.gridy = linha; c.weightx = 0.2;
        panel.add(new JLabel("Cliente (CPF/CNPJ):"), c);
        c.gridx = 1; c.gridy = linha; c.weightx = 0.8;
        panel.add(txtCpfCliente, c);
        linha++;

        // Equipamento
        c.gridx = 0; c.gridy = linha; c.weightx = 0.2;
        JLabel lblEquip = new JLabel("Equipamento (ID):");
        lblEquip.setToolTipText("Ex: NO1234 (notebook) ou DE5678 (desktop)");
        panel.add(lblEquip, c);
        c.gridx = 1; c.gridy = linha; c.weightx = 0.8;
        panel.add(txtIdEquipamento, c);
        linha++;

        // Preço base
        c.gridx = 0; c.gridy = linha; c.weightx = 0.2;
        panel.add(new JLabel("Serviço (Preço base):"), c);
        c.gridx = 1; c.gridy = linha; c.weightx = 0.8;
        panel.add(cbPrecoBase, c);
        linha++;

        // Vendedor
        c.gridx = 0; c.gridy = linha; c.weightx = 0.2;
        panel.add(new JLabel("Vendedor responsável:"), c);
        c.gridx = 1; c.gridy = linha; c.weightx = 0.8;
        panel.add(txtVendedor, c);
        linha++;

        // Dica
        c.gridx = 0; c.gridy = linha; c.gridwidth = 2;
        JLabel dica = new JLabel("Dica: verifique se o cliente e o equipamento já foram cadastrados.");
        dica.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        dica.setForeground(new Color(158, 7, 145));
        panel.add(dica, c);
        linha++;

        // Botao
        JButton btnIncluir = new JButton("Incluir OS");
        btnIncluir.addActionListener(e -> acaoIncluir());
        c.gridx = 0; c.gridy = linha; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(btnIncluir, c);

        return panel;
    }

    private JPanel criarPainelCancelar() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cancelamento de Ordem"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        txtNumOrdemCancel = new JTextField();
        txtMotivoCancel = new JTextArea(5, 20);
        txtMotivoCancel.setLineWrap(true);
        txtMotivoCancel.setWrapStyleWord(true);
        JScrollPane scrollMotivo = new JScrollPane(txtMotivoCancel);

        int linha = 0;

        // Número da ordem
        c.gridx = 0; c.gridy = linha; c.weightx = 0.2;
        panel.add(new JLabel("Número da OS:"), c);
        c.gridx = 1; c.gridy = linha; c.weightx = 0.8;
        panel.add(txtNumOrdemCancel, c);
        linha++;

        // Motivo
        c.gridx = 0; c.gridy = linha; c.weightx = 0.2;
        panel.add(new JLabel("Motivo do cancelamento:"), c);
        c.gridx = 1; c.gridy = linha; c.weightx = 0.8;
        c.fill = GridBagConstraints.BOTH;
        panel.add(scrollMotivo, c);
        linha++;

        // Botão
        JButton btnCancelar = new JButton("Cancelar OS");
        btnCancelar.setForeground(new Color(188, 5, 5));
        btnCancelar.addActionListener(e -> acaoCancelar());
        c.gridx = 0; c.gridy = linha; c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(btnCancelar, c);

        return panel;
    }

    private JPanel criarPainelFechar() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Fechamento de Ordem"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        txtNumOrdemFechar = new JTextField();
        txtRelatorio = new JTextArea(5, 20);
        txtRelatorio.setLineWrap(true);
        txtRelatorio.setWrapStyleWord(true);
        JScrollPane scrollRelatorio = new JScrollPane(txtRelatorio);

        chkPago = new JCheckBox("Pagamento confirmado");

        int linha = 0;

        // Número
        c.gridx = 0; c.gridy = linha; c.weightx = 0.2;
        panel.add(new JLabel("Número da OS:"), c);
        c.gridx = 1; c.gridy = linha; c.weightx = 0.8;
        panel.add(txtNumOrdemFechar, c);
        linha++;

        // Relatório
        c.gridx = 0; c.gridy = linha; c.weightx = 0.2;
        panel.add(new JLabel("Relatório técnico:"), c);
        c.gridx = 1; c.gridy = linha; c.weightx = 0.8;
        c.fill = GridBagConstraints.BOTH;
        panel.add(scrollRelatorio, c);
        linha++;

        // Checkbox
        c.gridx = 0; c.gridy = linha; c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        panel.add(chkPago, c);
        linha++;

        // Botão
        JButton btnFechar = new JButton("Finalizar OS");
        btnFechar.setForeground(new Color(7, 149, 73));
        btnFechar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFechar.addActionListener(e -> acaoFechar());
        c.gridx = 0; c.gridy = linha; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(btnFechar, c);

        return panel;
    }



    private void acaoIncluir() {
        try {
            String cpf = txtCpfCliente.getText().trim();
            String idEquip = txtIdEquipamento.getText().trim();
            String vendedor = txtVendedor.getText().trim();
            PrecoBase precoSel = (PrecoBase) cbPrecoBase.getSelectedItem();

            if (cpf.isEmpty() || idEquip.isEmpty() || vendedor.isEmpty() || precoSel == null) {
                JOptionPane.showMessageDialog(this,
                        "Preencha todos os campos para incluir a ordem.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            DadosOrdemServico dados =
                    new DadosOrdemServico(cpf, precoSel.getCodigo(), idEquip, vendedor);

            ResultadoMediator res = mediator.incluir(dados);

            if (res.isOperacaoRealizada()) {
                JOptionPane.showMessageDialog(this, "Ordem de Serviço incluída com sucesso!");
                txtCpfCliente.setText("");
                txtIdEquipamento.setText("");
                txtVendedor.setText("");
            } else {
                mostrarErros(res);
            }
        } catch (ExcecaoNegocio e) {
            tratarErroNegocio(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoCancelar() {
        try {
            String numero = txtNumOrdemCancel.getText().trim();
            String motivo = txtMotivoCancel.getText().trim();

            if (numero.isEmpty() || motivo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Informe o número da OS e o motivo do cancelamento.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            ResultadoMediator res = mediator.cancelar(numero, motivo, LocalDateTime.now());

            if (res != null && res.isOperacaoRealizada()) {
                JOptionPane.showMessageDialog(this, "Cancelamento efetuado com sucesso!");
                txtNumOrdemCancel.setText("");
                txtMotivoCancel.setText("");
            } else if (res != null) {
                mostrarErros(res);
            }
        } catch (ExcecaoNegocio e) {
            tratarErroNegocio(e);
        }
    }

    private void acaoFechar() {
        try {
            String numero = txtNumOrdemFechar.getText().trim();
            String relatorio = txtRelatorio.getText().trim();
            boolean pago = chkPago.isSelected();

            if (numero.isEmpty() || relatorio.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Informe o número da OS e o relatório técnico.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            FechamentoOrdemServico dados =
                    new FechamentoOrdemServico(numero, LocalDate.now(), pago, relatorio);

            ResultadoMediator res = mediator.fechar(dados);

            if (res != null && res.isOperacaoRealizada()) {
                JOptionPane.showMessageDialog(this, "Ordem finalizada com sucesso!");
                txtNumOrdemFechar.setText("");
                txtRelatorio.setText("");
                chkPago.setSelected(false);
            } else if (res != null) {
                mostrarErros(res);
            }
        } catch (ExcecaoNegocio e) {
            tratarErroNegocio(e);
        }
    }


    private void tratarErroNegocio(ExcecaoNegocio e) {
        if (e.getRes() != null) {
            mostrarErros(e.getRes());
        } else {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erro de validação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarErros(ResultadoMediator res) {
        if (res.getMensagensErro() != null && res.getMensagensErro().tamanho() > 0) {
            StringBuilder sb = new StringBuilder("Verifique os seguintes pontos:\n");
            for (String s : res.getMensagensErro().listar()) {
                sb.append("- ").append(s).append("\n");
            }
            JOptionPane.showMessageDialog(this,
                    sb.toString(),
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
