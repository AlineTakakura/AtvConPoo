package br.edu.cs.poo.ac.utils;

public class ValidadorCPFCNPJ{
    
    private static final int[] PESO_CPF={11,10,9,8,7,6,5,4,3,2};
    private static final int[] PESO_CNPJ1={5,4,3,2,9,8,7,6,5,4,3,2};
    private static final int[] PESO_CNPJ2={6,5,4,3,2,9,8,7,6,5,4,3,2};

    public static ResultadoValidacaoCPFCNPJ validarCPFCNPJ(String cpfCnpj){
        if(StringUtils.estaVazia(cpfCnpj)){
            return new ResultadoValidacaoCPFCNPJ(false,false,ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }

        String apenasDigitos=cpfCnpj.replaceAll("[^0-9]","");

        if(apenasDigitos.length()!=11&&apenasDigitos.length()!=14){
            return new ResultadoValidacaoCPFCNPJ(false,false,ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
        
        if(isCPF(apenasDigitos)){
            ErroValidacaoCPFCNPJ erro=validarCPF(apenasDigitos);
            return new ResultadoValidacaoCPFCNPJ(true,false,erro);
        } 
        else if(isCNPJ(apenasDigitos)){
            ErroValidacaoCPFCNPJ erro=validarCNPJ(apenasDigitos);
            return new ResultadoValidacaoCPFCNPJ(false,true,erro);
        } 
        else{
             return new ResultadoValidacaoCPFCNPJ(false,false,ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
    }

    public static boolean isCPF(String valor){
        return valor.length()==11;
    }

    public static boolean isCNPJ(String valor){
        return valor.length()==14;
    }

    public static ErroValidacaoCPFCNPJ validarCPF(String cpf){
        if(!isCPF(cpf)){
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ; 
        }
        
        if(cpf.matches("(\\d)\\1{10}")){
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }

        if(!isDigitoVerificadorValidoCPF(cpf)){
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }

        return null;
    }

    public static ErroValidacaoCPFCNPJ validarCNPJ(String cnpj){
        if(!isCNPJ(cnpj)){
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        if(cnpj.matches("(\\d)\\1{13}")){
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }
        
        if(!isDigitoVerificadorValidoCNPJ(cnpj)){
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }

        return null;
    }

    private static boolean isDigitoVerificadorValidoCPF(String cpf){
        
        int soma1=0;
        for(int i=0;i<9;i++){
            soma1+=(cpf.charAt(i)-'0')*(10-i);
        }
        int dv1Calculado=(soma1%11<2)?0:11-(soma1%11);

        if(dv1Calculado!=(cpf.charAt(9)-'0'))return false;

        int soma2=0;
        for(int i=0;i<10;i++){
            soma2+=(cpf.charAt(i)-'0')*(11-i);
        }
        int dv2Calculado=(soma2%11<2)?0:11-(soma2%11);

        return dv2Calculado==(cpf.charAt(10)-'0');
    }
    
    private static boolean isDigitoVerificadorValidoCNPJ(String cnpj){
        
        int soma1=0;
        for(int i=0;i<12;i++){
            soma1+=(cnpj.charAt(i)-'0')*PESO_CNPJ1[i];
        }
        int dv1Calculado=(soma1%11<2)?0:11-(soma1%11);
        
        if(dv1Calculado!=(cnpj.charAt(12)-'0'))return false;

        int soma2=0;
        for(int i=0;i<13;i++){
            soma2+=(cnpj.charAt(i)-'0')*PESO_CNPJ2[i];
        }
        int dv2Calculado=(soma2%11<2)?0:11-(soma2%11);
        
        return dv2Calculado==(cnpj.charAt(13)-'0');
    }
}
