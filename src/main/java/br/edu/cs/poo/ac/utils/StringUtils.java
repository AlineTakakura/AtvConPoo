package br.edu.cs.poo.ac.utils;

public class StringUtils{

    public static boolean estaVazia(String str){
        if(str==null){
        	return true;
        }
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)!=' '){
                return false;
            }
        }
        return true;
    }

    public static boolean tamanhoExcedido(String str, int tamanho){
        if(tamanho<0){
        	return false;  
        }
        if(str==null){
        	return tamanho> 0;
        }
        return str.length()>tamanho;
    }

    public static boolean emailValido(String email) {
        if(estaVazia(email)) {
        	return false;
        }
        String regex="^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    public static boolean telefoneValido(String tel){
        if(estaVazia(tel)){
        	return false;
        }
        String regex="^\\(\\d{2}\\)\\d{8,9}$";
        return tel.matches(regex);
    }
}
