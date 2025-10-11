package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;

public class DesktopDAO extends DAOGenerico {
	
	public DesktopDAO() {
		super(Desktop.class);
	}
    
	public Desktop buscar(String serial) {
		return (Desktop)cadastroObjetos.buscar(serial);		
	}
    
	public boolean incluir(Desktop desktop) {
		if (buscar(desktop.getSerial()) == null) {
			cadastroObjetos.incluir(desktop, desktop.getSerial());
			return true;
		} else {
			return false;
		}
	}
    
	public boolean alterar(Desktop desktop) {
		if (buscar(desktop.getSerial()) != null) {
			cadastroObjetos.alterar(desktop, desktop.getSerial());
			return true;
		} else {
			return false;
		}
	}
    
	public boolean excluir(String serial) {
		if (buscar(serial) != null) {
			cadastroObjetos.excluir(serial);
			return true;
		} else {
			return false;
		}
	}	
    
	public Desktop[] buscarTodos() {
		Serializable[] ret = cadastroObjetos.buscarTodos();
		Desktop[] retorno;
		if (ret != null && ret.length > 0) {
			retorno = new Desktop[ret.length];
			for (int i=0; i<ret.length; i++) {
				retorno[i] = (Desktop)ret[i];
			}
		} else {
			retorno = new Desktop[0]; 
		}
		return retorno;
	}
}