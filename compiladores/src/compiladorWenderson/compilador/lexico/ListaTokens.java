package compiladorWenderson.compilador.lexico;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import compiladorWenderson.compilador.Token;
import compiladorWenderson.compilador.TokensLinhaColunaLog;

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
