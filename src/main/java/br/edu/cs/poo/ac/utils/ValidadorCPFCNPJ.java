package br.edu.cs.poo.ac.utils;

public class ValidadorCPFCNPJ {

    public static ResultadoValidacaoCPFCNPJ validarCPFCNPJ(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }

        String apenasDigitos = cpfCnpj.replaceAll("[^0-9]", "");

        if (apenasDigitos.length() != 11 && apenasDigitos.length() != 14) {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
        
        if (isCPF(apenasDigitos)) {
            ErroValidacaoCPFCNPJ erro = validarCPF(apenasDigitos);
            return new ResultadoValidacaoCPFCNPJ(true, false, erro);
        } 
        else if (isCNPJ(apenasDigitos)) {
            ErroValidacaoCPFCNPJ erro = validarCNPJ(apenasDigitos);
            return new ResultadoValidacaoCPFCNPJ(false, true, erro);
        } 
        else {
             return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
    }

    public static boolean isCPF(String valor) {
        if (StringUtils.estaVazia(valor)) return false;
        return valor.length() == 11;
    }

    public static boolean isCNPJ(String valor) {
        if (StringUtils.estaVazia(valor)) return false;
        return valor.length() == 14;
    }

    public static ErroValidacaoCPFCNPJ validarCPF(String cpf) {
        if (!isCPF(cpf)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        if (!isDigitoVerificadorValidoCPF(cpf)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }

        return null;
    }

    public static ErroValidacaoCPFCNPJ validarCNPJ(String cnpj) {
        if (!isCNPJ(cnpj)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        if (!isDigitoVerificadorValidoCNPJ(cnpj)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }

        return null;
    }


    private static boolean isDigitoVerificadorValidoCPF(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException e) {
            return false;
        }

        int d1, d2;
        int digito1, digito2, resto;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digito1 = Integer.valueOf(new String(cpf.substring(nCount - 1, nCount))).intValue();
            d1 = d1 + (11 - nCount) * digito1;
            d2 = d2 + (12 - nCount) * digito1;
        }

        resto = (d1 % 11);
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        resto = (d2 % 11);
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        return nDigVerific.equals(nDigResult);
    }
    
    private static boolean isDigitoVerificadorValidoCNPJ(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;
        
        int soma = 0, peso = 2;

        for (int i = 11; i >= 0; i--) {
            soma += (cnpj.charAt(i) - '0') * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int primeiroDV = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        if (primeiroDV != (cnpj.charAt(12) - '0')) return false;

        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            soma += (cnpj.charAt(i) - '0') * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int segundoDV = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        return segundoDV == (cnpj.charAt(13) - '0');
    }
}
