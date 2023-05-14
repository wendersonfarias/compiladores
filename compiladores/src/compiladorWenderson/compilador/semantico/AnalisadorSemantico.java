package compiladorWenderson.compilador.semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import compiladorWenderson.compilador.Token;

public class AnalisadorSemantico {

	private Map<String, Integer> tabelaSimbolos; // Tabela de s�mbolos para armazenar as vari�veis
	
	private ArrayList<Token> listaToken;
	
	

	public AnalisadorSemantico(ArrayList<Token> listaToken) {
		tabelaSimbolos = new HashMap<>();
		this.listaToken = listaToken;
	}

	
	public ArrayList<String> analiseSemantica() {
		ListIterator<Token> iterador = getListaToken().listIterator();
		
		while (iterador.hasNext()) {
		    Token token = iterador.next();
		    
		    if(token.getToken().equals("var")) {
		    	if(iterador.hasNext()) {
		    		token = iterador.next();
		    		if(token.getToken().equals("id") && iterador.hasNext() && iterador.next().getToken().equals(";")) {
		    			analisarDeclaracaoVariavel(token.getLexema());
		    		}else {
		    			token = iterador.previous();
		    			
		    		}
		    	}
		    }
		}
		
		return null;
		
	}
	
	
	
	
	public void analisarDeclaracaoVariavel(String nomeVariavel) {
	        if (tabelaSimbolos.containsKey(nomeVariavel)) {
	            // Declara��o duplicada de vari�vel
	            System.out.println("Erro: Vari�vel " + nomeVariavel + " j� foi declarada anteriormente.");
	        } else {
	            tabelaSimbolos.put(nomeVariavel, 0); // Adiciona a vari�vel na tabela de s�mbolos com um valor inicial de 0
	        }
	}
	
	public void analisarReferenciaVariavel(String nomeVariavel) {
        if (!tabelaSimbolos.containsKey(nomeVariavel)) {
            // Vari�vel n�o declarada
            System.out.println("Erro: Vari�vel " + nomeVariavel + " n�o foi declarada anteriormente.");
        }
    }
	
	public void analisarDivisaoPorZero(int divisor) {
        if (divisor == 0) {
            System.out.println("Erro: Divis�o por zero encontrada.");
        }
    }
	
	public Map<String, Integer> getTabelaSimbolos() {
		return tabelaSimbolos;
	}

	public void setTabelaSimbolos(Map<String, Integer> tabelaSimbolos) {
		this.tabelaSimbolos = tabelaSimbolos;
	}

	public ArrayList<Token> getListaToken() {
		return listaToken;
	}

	public void setListaToken(ArrayList<Token> listaToken) {
		this.listaToken = listaToken;
	}
	
	

}
