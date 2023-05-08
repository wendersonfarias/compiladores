package compiladorWenderson.compilador.semantico;

import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

import compiladorWenderson.compilador.TokensLinhaColunaLog;

public class AnalisadorSemantico {

	List<String> tokens= new Stack<String>();
	List<String> linhaColuna= new Stack<String>();
	
	TokensLinhaColunaLog tokensLinhaColunaLog = new TokensLinhaColunaLog();
	
	
	
	public AnalisadorSemantico(TokensLinhaColunaLog tokensLinhaColunaLog) {
		this.tokensLinhaColunaLog = tokensLinhaColunaLog;
	}


	void analisaSemantica() {
		
		TabelaDeSimbolos tabela = new TabelaDeSimbolos();

		do {
			if(tokens.peek().equals("var") || tokens.peek().equals("numero") ) {
				copia = tokens.pop();
				
				if(tokens.peek().equals("var")) {
					
					if(tokens.peek().equals("identificador")) {
						tabela.adicionarSimbolo(tokens.pop(), 0);
					}else {
						System.out.println("Erro semantico "+ tokens.pop() + "inesperado");
					}
					
				}else {
					if(tokens.peek().equals("identificador")) {
						if(tabela.verificaDeclarado(tokens.pop())) {
							
						}	
					}
				}
			}
		}while(!tokens.empty());
	}
	
	
	public void obtemTokens() throws IOException {
		Stack<String> linhasTokens = new Stack<String>(); 
		Stack<String> LinhasColunas = new Stack<String>(); 
		
	
		linhasTokens.addAll(tokensLinhaColunaLog.getToken());
		
		LinhasColunas.addAll(tokensLinhaColunaLog.getLinhaColuna());
		
		
		Collections.reverse(linhasTokens);
		Collections.reverse(LinhasColunas);
		
		this.tokens = linhasTokens;
		this.linhaColuna = LinhasColunas;
		
	}
}
