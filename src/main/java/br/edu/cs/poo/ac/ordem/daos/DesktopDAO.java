package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;

public class DesktopDAO extends DAOGenerico {
	
	public DesktopDAO() {
		super(Desktop.class);
	}
    
	public Desktop buscar(String idTipoSerial) {
		return (Desktop)cadastroObjetos.buscar(idTipoSerial);		
	}
    
	public boolean incluir(Desktop desktop) {
        String idCompleto = desktop.getIdTipo() + desktop.getSerial();
		if (buscar(idCompleto) == null) {
			cadastroObjetos.incluir(desktop, idCompleto);
			return true;
		} else {
			return false;
		}
	}
    
	public boolean alterar(Desktop desktop) {
        String idCompleto = desktop.getIdTipo() + desktop.getSerial();
		if (buscar(idCompleto) != null) {
			cadastroObjetos.alterar(desktop, idCompleto);
			return true;
		} else {
			return false;
		}
	}
    
	public boolean excluir(String idTipoSerial) {
		if (buscar(idTipoSerial) != null) {
			cadastroObjetos.excluir(idTipoSerial);
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