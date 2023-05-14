package compiladorWenderson.compilador.semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import compiladorWenderson.compilador.Token;

public class AnalisadorSemantico {

	private Map<String, Integer> tabelaSimbolos; // Tabela de símbolos para armazenar as variáveis
	
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
	            // Declaração duplicada de variável
	            System.out.println("Erro: Variável " + nomeVariavel + " já foi declarada anteriormente.");
	        } else {
	            tabelaSimbolos.put(nomeVariavel, 0); // Adiciona a variável na tabela de símbolos com um valor inicial de 0
	        }
	}
	
	public void analisarReferenciaVariavel(String nomeVariavel) {
        if (!tabelaSimbolos.containsKey(nomeVariavel)) {
            // Variável não declarada
            System.out.println("Erro: Variável " + nomeVariavel + " não foi declarada anteriormente.");
        }
    }
	
	public void analisarDivisaoPorZero(int divisor) {
        if (divisor == 0) {
            System.out.println("Erro: Divisão por zero encontrada.");
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
