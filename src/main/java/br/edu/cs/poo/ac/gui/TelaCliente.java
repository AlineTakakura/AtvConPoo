package br.edu.cs.poo.ac.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator; 
import br.edu.cs.poo.ac.utils.ListaString; 
import br.edu.cs.poo.ac.utils.StringUtils; 

public class TelaCliente {

    protected Shell shlCadastroDeCliente;
    private ClienteMediator mediator = ClienteMediator.getInstancia(); 

    private Text txtCpfCnpj;

    private Text txtNome;
    private Text txtEmail;
    private Text txtCelular;
    private Button chkIsZap;
    private Text txtDataCadastro;

    private Button btnNovo;
    private Button btnBuscar;
    private Button btnIncluirAlterar;
    private Button btnExcluir;
    private Button btnCancelar;
    private Button btnLimpar;

    public static void main(String[] args) {
        try {
            TelaCliente window = new TelaCliente();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        Display display = Display.getDefault();
        createContents();
        shlCadastroDeCliente.open();
        shlCadastroDeCliente.layout();
        while (!shlCadastroDeCliente.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    protected void createContents() {
        shlCadastroDeCliente = new Shell();
        shlCadastroDeCliente.setSize(650, 450);
        shlCadastroDeCliente.setText("Cadastro de Cliente");

        Label lblCpfCnpj = new Label(shlCadastroDeCliente, SWT.NONE);
        lblCpfCnpj.setBounds(30, 30, 70, 20);
        lblCpfCnpj.setText("CPF/CNPJ");

        txtCpfCnpj = new Text(shlCadastroDeCliente, SWT.BORDER);
        txtCpfCnpj.setToolTipText("Digite o CPF ou CNPJ");
        txtCpfCnpj.setBounds(110, 30, 160, 26);
        
        txtCpfCnpj.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String apenasDigitos = apenasDigitos(txtCpfCnpj.getText());
                
                if (apenasDigitos.length() == 11) {
                    String formatado = apenasDigitos.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
                    txtCpfCnpj.setText(formatado);
                } else if (apenasDigitos.length() == 14) {
                    String formatado = apenasDigitos.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
                    txtCpfCnpj.setText(formatado);
                }
            }
        });

        btnNovo = new Button(shlCadastroDeCliente, SWT.NONE);
        btnNovo.setBounds(300, 30, 90, 30);
        btnNovo.setText("Novo");

        btnBuscar = new Button(shlCadastroDeCliente, SWT.NONE);
        btnBuscar.setBounds(400, 30, 90, 30);
        btnBuscar.setText("Buscar");

        Label lblNome = new Label(shlCadastroDeCliente, SWT.NONE);
        lblNome.setBounds(30, 100, 70, 20);
        lblNome.setText("Nome");
        
        txtNome = new Text(shlCadastroDeCliente, SWT.BORDER);
        txtNome.setEnabled(false);
        txtNome.setBounds(110, 100, 480, 26);

        Label lblEmail = new Label(shlCadastroDeCliente, SWT.NONE);
        lblEmail.setBounds(30, 150, 70, 20);
        lblEmail.setText("E-mail");
        
        txtEmail = new Text(shlCadastroDeCliente, SWT.BORDER);
        txtEmail.setEnabled(false);
        txtEmail.setBounds(110, 150, 250, 26);

        Label lblCelular = new Label(shlCadastroDeCliente, SWT.NONE);
        lblCelular.setBounds(370, 150, 70, 20);
        lblCelular.setText("Celular");
        
        txtCelular = new Text(shlCadastroDeCliente, SWT.BORDER);
        txtCelular.setEnabled(false);
        txtCelular.setBounds(450, 150, 140, 26);

        chkIsZap = new Button(shlCadastroDeCliente, SWT.CHECK);
        chkIsZap.setBounds(110, 200, 100, 20);
        chkIsZap.setText("É ZAP");
        chkIsZap.setEnabled(false);

        Label lblDataCadastro = new Label(shlCadastroDeCliente, SWT.NONE);
        lblDataCadastro.setBounds(30, 250, 100, 20);
        lblDataCadastro.setText("Data Cadastro");
        
        txtDataCadastro = new Text(shlCadastroDeCliente, SWT.BORDER);
        txtDataCadastro.setToolTipText("dd/mm/yyyy");
        txtDataCadastro.setEnabled(false);
        txtDataCadastro.setBounds(130, 250, 100, 26);
        
        txtDataCadastro.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String data = apenasDigitos(txtDataCadastro.getText());
                if (data.length() == 8) {
                    String formatado = data.replaceAll("(\\d{2})(\\d{2})(\\d{4})", "$1/$2/$3");
                    txtDataCadastro.setText(formatado);
                }
            }
        });

        btnIncluirAlterar = new Button(shlCadastroDeCliente, SWT.NONE);
        btnIncluirAlterar.setEnabled(false);
        btnIncluirAlterar.setBounds(50, 350, 100, 30);
        btnIncluirAlterar.setText("Incluir");

        btnExcluir = new Button(shlCadastroDeCliente, SWT.NONE);
        btnExcluir.setEnabled(false);
        btnExcluir.setBounds(170, 350, 100, 30);
        btnExcluir.setText("Excluir");
        
        btnCancelar = new Button(shlCadastroDeCliente, SWT.NONE);
        btnCancelar.setEnabled(false);
        btnCancelar.setBounds(290, 350, 100, 30);
        btnCancelar.setText("Cancelar");

        btnLimpar = new Button(shlCadastroDeCliente, SWT.NONE);
        btnLimpar.setBounds(410, 350, 100, 30);
        btnLimpar.setText("Limpar");

        configurarListenersDeBotoes();
        atualizarEstadoBotoes(true);
    }

    private void configurarListenersDeBotoes() {
        
        btnNovo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String cpfCnpj = apenasDigitos(txtCpfCnpj.getText());

                if (StringUtils.estaVazia(cpfCnpj)) {
                    exibirMensagem("CPF/CNPJ deve ser preenchido!", SWT.ICON_WARNING);
                    txtCpfCnpj.setFocus();
                    return;
                }
                
                Cliente ent = mediator.buscar(cpfCnpj);
                if (ent != null) {
                    exibirMensagem("Cliente já existente!", SWT.ICON_WARNING);
                } else {
                    limparCamposDeDados();
                    txtDataCadastro.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    
                    btnIncluirAlterar.setText("Incluir");
                    atualizarEstadoBotoes(false); 
                }
            }
        });

        btnBuscar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String cpfCnpj = apenasDigitos(txtCpfCnpj.getText());
                
                if (StringUtils.estaVazia(cpfCnpj)) {
                    exibirMensagem("CPF/CNPJ deve ser preenchido!", SWT.ICON_WARNING);
                    txtCpfCnpj.setFocus();
                    return;
                }
                
                Cliente ent = mediator.buscar(cpfCnpj);
                if (ent == null) {
                    exibirMensagem("Cliente não existente!", SWT.ICON_WARNING);
                } else {
                    preencherCampos(ent);
                    btnIncluirAlterar.setText("Alterar");
                    atualizarEstadoBotoes(false); 
                    btnExcluir.setEnabled(true);
                }
            }
        });

        btnIncluirAlterar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String modo = btnIncluirAlterar.getText();
                
                String cpfCnpj = apenasDigitos(txtCpfCnpj.getText());
                String nome = txtNome.getText();
                String email = txtEmail.getText();
                String celular = txtCelular.getText();
                boolean isZap = chkIsZap.getSelection();
                
                LocalDate dataCadastro;
                try {
                    dataCadastro = LocalDate.parse(txtDataCadastro.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (Exception ex) {
                    exibirMensagem("Data do cadastro em formato inválido (dd/mm/yyyy)", SWT.ICON_ERROR);
                    return;
                }

                Contato contato = new Contato(email, celular, isZap);
                Cliente cliente = new Cliente(cpfCnpj, nome, contato, dataCadastro);

                ResultadoMediator resultado;
                String msgSucesso;

                if (modo.equals("Incluir")) {
                    resultado = mediator.incluir(cliente);
                    msgSucesso = "Inclusão realizada com sucesso";
                } else { 
                    resultado = mediator.alterar(cliente);
                    msgSucesso = "Alteração realizada com sucesso";
                }
                
                if (resultado.isOperacaoRealizada()) { 
                    exibirMensagem(msgSucesso, SWT.ICON_INFORMATION);
                    resetarTela();
                } else if (!resultado.isValidado()) { 
                    exibirErros(resultado.getMensagensErro()); 
                } else {
                    exibirMensagem(formatarMensagens(resultado.getMensagensErro()), SWT.ICON_ERROR);
                }
            }
        });

        btnExcluir.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                MessageBox messageBox = new MessageBox(shlCadastroDeCliente, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
                messageBox.setText("Confirmação");
                messageBox.setMessage("Tem certeza que deseja excluir o cliente " + txtNome.getText() + "?");
                if (messageBox.open() != SWT.YES) {
                    return;
                }

                String cpfCnpj = apenasDigitos(txtCpfCnpj.getText());
                ResultadoMediator resultado = mediator.excluir(cpfCnpj);
                
                if (resultado.isOperacaoRealizada()) { 
                    exibirMensagem("Exclusão realizada com sucesso", SWT.ICON_INFORMATION);
                    resetarTela();
                } else {
                    exibirMensagem("Exclusão não pôde ser realizada. Detalhe: " + formatarMensagens(resultado.getMensagensErro()), SWT.ICON_ERROR);
                }
            }
        });
        
        btnCancelar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resetarTela();
            }
        });
        
        btnLimpar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (txtCpfCnpj.getEnabled()) {
                    txtCpfCnpj.setText("");
                }
                limparCamposDeDados();
            }
        });
    }
    
    private void resetarTela() {
        limparCamposTodos();
        btnIncluirAlterar.setText("Incluir");
        atualizarEstadoBotoes(true);
    }
    
    private void atualizarEstadoBotoes(boolean modoInicial) {
        txtCpfCnpj.setEnabled(modoInicial);
        btnNovo.setEnabled(modoInicial);
        btnBuscar.setEnabled(modoInicial);

        txtNome.setEnabled(!modoInicial);
        txtEmail.setEnabled(!modoInicial);
        txtCelular.setEnabled(!modoInicial);
        chkIsZap.setEnabled(!modoInicial);
        txtDataCadastro.setEnabled(!modoInicial);

        btnIncluirAlterar.setEnabled(!modoInicial);
        btnCancelar.setEnabled(!modoInicial);
        
        if(modoInicial) {
            btnExcluir.setEnabled(false);
        }
    }
    
    private void limparCamposDeDados() {
        txtNome.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        chkIsZap.setSelection(false);
        txtDataCadastro.setText("");
    }
    
    private void limparCamposTodos() {
        txtCpfCnpj.setText("");
        limparCamposDeDados();
    }
    
    private void preencherCampos(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtEmail.setText(cliente.getContato().getEmail());
        txtCelular.setText(cliente.getContato().getCelular());
        chkIsZap.setSelection(cliente.getContato().isEhZap());
        
        txtDataCadastro.setText(cliente.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    
    private void exibirMensagem(String mensagem, int tipo) {
        MessageBox messageBox = new MessageBox(shlCadastroDeCliente, tipo | SWT.OK);
        messageBox.setText("Aviso");
        messageBox.setMessage(mensagem);
        messageBox.open();
    }
    
    private String formatarMensagens(ListaString erros) {
        String[] mensagens = erros.listar();
        if (mensagens.length == 0) {
            return "";
        }
        return String.join("\n", mensagens);
    }
    
    private void exibirErros(ListaString erros) {
        String mensagemCompleta = "Erros de Validação:\n\n" + formatarMensagens(erros);
        exibirMensagem(mensagemCompleta, SWT.ICON_ERROR);
    }
    
    private String apenasDigitos(String str) {
        if (str == null) return "";
        return str.replaceAll("[^0-9]", "");
    }
}