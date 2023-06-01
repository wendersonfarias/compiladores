package compiladorWenderson.compilador.lexico;


import java.util.ArrayList;

import compiladorWenderson.compilador.Token;


public class ListaTokens {
	
	ArrayList<Token> listaToken = new ArrayList<Token>();
	
	
	
	public ListaTokens(ArrayList<Token> listaTokens) {
		this.listaToken = listaTokens;
	}



public ArrayList<String> listaToken() {
		ArrayList<String> listaTokens = new ArrayList<String>();
		String item;
		for (Token token : listaToken) {
				item ="| Token: " + 
								token.getToken()  
				+ " | Lexema: " + token.getLexema()   + " | "+ token.getLinhaColuna();
				listaTokens.add(item);	
		}
	return listaTokens;
	}
	
}
