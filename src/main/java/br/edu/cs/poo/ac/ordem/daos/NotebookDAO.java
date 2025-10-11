package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;

public class NotebookDAO extends DAOGenerico {
	
	public NotebookDAO() {
		super(Notebook.class);
	}
    
	public Notebook buscar(String serial) {
		return (Notebook)cadastroObjetos.buscar(serial);		
	}
    
	public boolean incluir(Notebook notebook) {
		if (buscar(notebook.getSerial()) == null) {
			cadastroObjetos.incluir(notebook, notebook.getSerial());
			return true;
		} else {
			return false;
		}
	}
    
	public boolean alterar(Notebook notebook) {
		if (buscar(notebook.getSerial()) != null) {
			cadastroObjetos.alterar(notebook, notebook.getSerial());
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
    
	public Notebook[] buscarTodos() {
		Serializable[] ret = cadastroObjetos.buscarTodos();
		Notebook[] retorno;
		if (ret != null && ret.length > 0) {
			retorno = new Notebook[ret.length];
			for (int i=0; i<ret.length; i++) {
				retorno[i] = (Notebook)ret[i];
			}
		} else {
			retorno = new Notebook[0]; 
		}
		return retorno;
	}
}