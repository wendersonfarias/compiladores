package compiladorWenderson.compilador.semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import compiladorWenderson.compilador.Token;

public class AnalisadorSemantico {

	private Map<String, Boolean> tabelaSimbolos; // Tabela de símbolos para armazenar as variáveis
	
	private ArrayList<Token> listaToken;
	
	private Boolean erroSemantico = false;
	private Integer indice ;
	
	

	public AnalisadorSemantico(ArrayList<Token> listaToken) {
		tabelaSimbolos = new HashMap<>();
		this.listaToken = listaToken;
	}

	
	public Map<String, Boolean> analiseSemantica() {
		indice = 0;
		Token token;
		while(indice < getListaToken().size() && !erroSemantico) {
			token = getListaToken().get(indice);
			
			if (token.getToken().equals("var")) {
				verificaDeclaracaoDaVariavel(token);
            }
            else if (token.getToken().equals("se") || token.getToken().equals("laco")) {
            	verificaArgumentoCondicional();
            }
            else if (token.getToken().equals("id")) {
            	verificaVariavel(token);
            }
            else if (token.getToken().equals("leia")) {
               marcarArgumentoLeituraComoAtribuido();
            }
			indice++;
        }
			
			
		
		
		return tabelaSimbolos;
		
	}
	
	/**
	 * Verifica a declaração de uma variável e a registra na tabela de símbolos.
	 * Recebe um token como argumento, representando a declaração da variável.
	 * Verifica se a variável já foi declarada anteriormente, lança um erro caso positivo.
	 * Adiciona a variável à tabela de símbolos com o valor de atribuição inicial como falso.
	 * Imprime uma mensagem informando que a variável foi declarada.
	 */
	public void verificaDeclaracaoDaVariavel(Token token) {
		var proximoToken = listaToken.get(indice + 1 );
		verificaVariavelJaDeclarada(proximoToken);
		tabelaSimbolos.put(proximoToken.getLexema(), false);
		System.out.println("A variavel '" + proximoToken.getLexema() + "' foi declarada");
		
		
	}
	
	
	
	/**
	 * Verifica a presença e o tratamento de uma variável em uma expressão.
	 *
	 * @param tokenAtual O token atual que representa a variável.
	 */
	private void verificaVariavel(Token tokenAtual) {
		verificaVariavelNaoDeclarada(tokenAtual);
	        var buffer = new StringBuilder();
	        if (getListaToken().get(++indice).getToken().equals("=") ) {
	            indice++;
	            verificaExpressao(buffer);
	            verificaValorVariavel(buffer, tokenAtual);
	        }
	}
	
	/**
	 * Marca um argumento de leitura como atribuído.
	 * O índice do argumento lido é obtido somando-se 2 ao valor do índice atual.
	 * O próximo token é obtido a partir do índice calculado.
	 * Verifica se a variável representada pelo próximo token não foi declarada anteriormente.
	 * Adiciona a variável à tabela de símbolos com o valor de atribuição como verdadeiro.
	 * Imprime uma mensagem informando que a variável é um valor de entrada.
	 */
	private void marcarArgumentoLeituraComoAtribuido() {
        final var argumento_lido = this.indice + 2;
        var proximoToken = getListaToken().get(argumento_lido);
        verificaVariavelNaoDeclarada(proximoToken);
        tabelaSimbolos.put(proximoToken.getLexema(), true);
        System.out.println("A variavel '" + proximoToken.getLexema() + "' e um valor a ser lido");
    }
	
	
	/**
	 * Verifica se a variável já foi declarada anteriormente.
	 * Se a variável estiver declarada, uma mensagem de aviso é impressa no console,
	 * informando o nome da variável e a linha/coluna onde ocorre a declaração.
	 *
	 * @param tokenAtual O token que representa a variável atual.
	 */
	private void verificaVariavelJaDeclarada(Token tokenAtual) {
        if (variavelDeclarada(tokenAtual)) {
        	System.out.println("A variavel '" + tokenAtual.getLexema() + "' já está declarado" +  tokenAtual.getLinhaColuna());
        	erroSemantico = true;
        }
    }
	
	/**
	 * Verifica o valor atribuído a uma variável e realiza ações com base nesse valor.
	 * Se o valor for "0", a variável é adicionada à tabela de símbolos com valor de atribuição como falso.
	 * Se o valor contiver a sequência "/0", imprime uma mensagem de erro de "Divisão por 0" no console.
	 * Caso contrário, a variável é adicionada à tabela de símbolos com valor de atribuição como verdadeiro.
	 * Imprime uma mensagem informando que a variável recebeu um determinado valor.
	 *
	 * @param buffer      O StringBuilder contendo o valor atribuído à variável.
	 * @param tokenAtual  O token que representa a variável atual.
	 */
	private void verificaValorVariavel(StringBuilder buffer, Token tokenAtual) {
        if ("0".equals(buffer.toString())) {
            tabelaSimbolos.put(tokenAtual.getLexema(), false);
            System.out.println("A variavel '%s' recebeu o valor: '%s'".formatted(tokenAtual.getLexema(), buffer));
        }
        else if (buffer.toString().contains("/0")) {
        	System.out.println();
        	System.out.println("** \tErro Semantico!                           **");
        	System.out.println("**    Divisao por 0, " + tokenAtual.getLinhaColuna());
        	System.out.println();
        	System.out.println("** Nao eh possivel continuar a analise semantica! **");
        	System.out.println("** Corrija os erros! e tente novamente.           **");
        	System.out.println();
        	erroSemantico = true;
        	return;
        }
        else {
        	tabelaSimbolos.put(tokenAtual.getLexema(), true);
        	System.out.println("A variavel '%s' recebeu o valor: '%s'".formatted(tokenAtual.getLexema(), buffer));
        }
        
    }
	
	/**
	 * Verifica uma expressão e constrói um valor acumulado no StringBuilder 'buffer'.
	 * O método percorre a lista de tokens até encontrar um token de ponto e vírgula (';').
	 * Para cada token encontrado, verifica se o lexema é vazio.
	 * Se o lexema for vazio, verifica se a variável associada ao lexema está declarada e obtém seu valor.
	 * Concatena o lexema à expressão acumulada no StringBuilder 'buffer', utilizando o valor da variável se estiver declarada ou '0' se não estiver.
	 * Caso contrário, simplesmente concatena o lexema à expressão acumulada no StringBuilder 'buffer'.
	 *
	 * @param buffer O StringBuilder utilizado para construir a expressão acumulada.
	 */
	private void verificaExpressao(StringBuilder buffer) {
        while (!getListaToken().get(indice).getToken().equals(";")) {
            var lexeme = getListaToken().get(indice).getLexema();
            if (isVazio(lexeme)) {
            	verificaVariavelNaoDeclarada(getListaToken().get(indice));
                final boolean hasValue = tabelaSimbolos.get(lexeme);
                buffer.append(hasValue ? lexeme : "0"); //Se tiver valor concatena o lexema, senão concatena 0.
            }
            else {
                buffer.append(lexeme);
            }
            indice++;
        }
}


	/**
	 * Verifica se uma sequência de caracteres está vazia, permitindo apenas letras e dígitos.
	 * Retorna true se a sequência estiver vazia e contiver apenas letras e dígitos,
	 * caso contrário, retorna false.
	 */
	private boolean isVazio(String lexeme) {
        if (!Character.isLetter(lexeme.charAt(0))) {
            return false;
        }
        return lexeme.chars().allMatch(Character::isLetterOrDigit);
    }
	
	
	
	
	/**
	 * Verifica os argumentos condicionais dentro de um parêntese.
	 * O método percorre a lista de tokens enquanto o token atual não for um parêntese de fechamento (')').
	 * Para cada token encontrado, verifica se é um token identificador ("id").
	 * Se for um token identificador, chama o método 'verificaVariavelNaoDeclarada' para verificar se a variável não foi declarada anteriormente.
	 *
	 * A variável 'indice' é incrementada dentro do loop, indicando o avanço para o próximo token.
	 */
	private void verificaArgumentoCondicional() {
        while (!getListaToken().get(indice++).getToken().equals(")") ) {
            Token tokenAtual = getListaToken().get(indice);
            if (tokenAtual.getToken().equals("id")) {
            	verificaVariavelNaoDeclarada(tokenAtual);
            }
        }
        indice--;
    }
	
	

	private void verificaVariavelNaoDeclarada(Token tokenAtual) {
	    if (!variavelDeclarada(tokenAtual)) {
	    	System.out.println("A variavel '" + tokenAtual.getLexema() + "' não foi declarada "
	    			+ "anteriormente, " + tokenAtual.getLinhaColuna());
	    	erroSemantico = true;
	    	System.out.println("** Nao eh possivel continuar a analise semantica! **");
        	System.out.println("** Corrija os erros! e tente novamente.           **");
	    }
	}
	
	private boolean variavelDeclarada(Token tokenAtual) {
        return tabelaSimbolos.containsKey(tokenAtual.getLexema());
    }
	
	public Map<String, Boolean> getTabelaSimbolos() {
		return tabelaSimbolos;
	}

	public void setTabelaSimbolos(Map<String, Boolean> tabelaSimbolos) {
		this.tabelaSimbolos = tabelaSimbolos;
	}

	public ArrayList<Token> getListaToken() {
		return listaToken;
	}

	public void setListaToken(ArrayList<Token> listaToken) {
		this.listaToken = listaToken;
	}
	
	

	
	

}
