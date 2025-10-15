package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;

import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;

public class ClienteMediator {

	private static ClienteMediator instancia;
	private ClienteDAO clienteDAO;

    private static final String CPF_CNPJ_NAO_INFORMADO = "CPF/CNPJ não informado";
    private static final String NOME_NAO_INFORMADO = "Nome não informado";
    private static final String CONTATO_NAO_INFORMADO = "Contato não informado";
    private static final String DATA_DO_CADASTRO_NAO_INFORMADA = "Data do cadastro não informada";
    private static final String CLIENTE_NAO_INFORMADO = "Cliente não informado";
    private static final int TAMANHO_MAX_NOME = 50;

	private ClienteMediator() {
		this.clienteDAO = new ClienteDAO();
	}

	public static ClienteMediator getInstancia() {
		if (instancia == null) {
			instancia = new ClienteMediator();
		}
		return instancia;
	}

	public ResultadoMediator validar(Cliente cliente) {
		ListaString erros = new ListaString();

        if (cliente == null) {
            erros.adicionar(CLIENTE_NAO_INFORMADO);
            return new ResultadoMediator(false, false, erros);
        }
        String cpfCnpj = cliente.getCpfCnpj();
        if (StringUtils.estaVazia(cpfCnpj)) {
            erros.adicionar(CPF_CNPJ_NAO_INFORMADO);
        } else {
            ResultadoValidacaoCPFCNPJ resCpfCnpj = ValidadorCPFCNPJ.validarCPFCNPJ(cpfCnpj);
            if (resCpfCnpj.getErroValidacao() != null) {
                switch (resCpfCnpj.getErroValidacao()) {
                    case CPF_CNPJ_COM_DV_INVALIDO:
                        erros.adicionar("CPF ou CNPJ com dígito verificador inválido");
                        break;
                    case CPF_CNPJ_NAO_E_CPF_NEM_CNPJ:
                        erros.adicionar("Não é CPF nem CNJP");
                        break;
                    default:
                        erros.adicionar("Erro de validação de CPF/CNPJ desconhecido");
                        break;
                }
            }
        }
        String nome = cliente.getNome();
        if (StringUtils.estaVazia(nome)) {
            erros.adicionar(NOME_NAO_INFORMADO);
        } else if (StringUtils.tamanhoExcedido(nome, TAMANHO_MAX_NOME)) {
            erros.adicionar("Nome tem mais de " + TAMANHO_MAX_NOME + " caracteres");
        }
        Contato contato = cliente.getContato();
        if (contato == null) {
            erros.adicionar(CONTATO_NAO_INFORMADO);
        }
        LocalDate dataCadastro = cliente.getDataCadastro();
        if (dataCadastro == null) {
            erros.adicionar(DATA_DO_CADASTRO_NAO_INFORMADA);
        } else if (dataCadastro.isAfter(LocalDate.now())) {
            erros.adicionar("Data do cadastro não pode ser posterior à data atual");
        }
        if (contato != null) {
            String email = contato.getEmail();
            String celular = contato.getCelular();
            boolean emailVazio = StringUtils.estaVazia(email);
            boolean celularVazio = StringUtils.estaVazia(celular);
            if (emailVazio && celularVazio) {
                erros.adicionar("Celular e e-mail não foram informados");
            } else {
                if (!emailVazio && !StringUtils.emailValido(email)) {
                    erros.adicionar("E-mail está em um formato inválido");
                }

                if (!celularVazio && !StringUtils.telefoneValido(celular)) {
                    erros.adicionar("Celular está em um formato inválido");
                }
                
                if (celularVazio && contato.isEhZap()) {
                    erros.adicionar("Celular não informado e indicador de zap ativo");
                }
            }
        }
        
        return new ResultadoMediator(erros.tamanho() == 0, false, erros); 
	}

	public ResultadoMediator incluir(Cliente cliente) {
		ResultadoMediator resValidacao = validar(cliente);
        
		if (!resValidacao.isValidado()) {
			return resValidacao;
		}
        if (clienteDAO.buscar(cliente.getCpfCnpj()) != null) {
            ListaString erros = new ListaString();
			erros.adicionar("CPF/CNPJ já existente");
			return new ResultadoMediator(true, false, erros);
        }

		clienteDAO.incluir(cliente);
        
		return new ResultadoMediator(true, true, new ListaString());
	}

	public ResultadoMediator alterar(Cliente cliente) {
		ResultadoMediator resValidacao = validar(cliente);

		if (!resValidacao.isValidado()) {
			return resValidacao;
		}
		if (clienteDAO.buscar(cliente.getCpfCnpj()) == null) {
			ListaString erros = new ListaString();
			erros.adicionar("CPF/CNPJ inexistente");
			return new ResultadoMediator(true, false, erros);
		}

		clienteDAO.alterar(cliente);

		return new ResultadoMediator(true, true, new ListaString());
	}

	public ResultadoMediator excluir(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
			ListaString erros = new ListaString();
			erros.adicionar(CPF_CNPJ_NAO_INFORMADO); 
			return new ResultadoMediator(false, false, erros);
        }
        if (clienteDAO.buscar(cpfCnpj) == null) {
			ListaString erros = new ListaString();
			erros.adicionar("CPF/CNPJ inexistente");
			return new ResultadoMediator(true, false, erros);
        }
        
		clienteDAO.excluir(cpfCnpj);

		return new ResultadoMediator(true, true, new ListaString());
	}

	public Cliente buscar(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            return null;
        }
		return clienteDAO.buscar(cpfCnpj);
	}
}