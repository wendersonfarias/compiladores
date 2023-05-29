package compiladorWenderson.compilador.semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import compiladorWenderson.compilador.Token;

public class AnalisadorSemantico {

	private Map<String, Boolean> tabelaSimbolos; // Tabela de s�mbolos para armazenar as vari�veis
	
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
	 * Verifica a declara��o de uma vari�vel e a registra na tabela de s�mbolos.
	 * Recebe um token como argumento, representando a declara��o da vari�vel.
	 * Verifica se a vari�vel j� foi declarada anteriormente, lan�a um erro caso positivo.
	 * Adiciona a vari�vel � tabela de s�mbolos com o valor de atribui��o inicial como falso.
	 * Imprime uma mensagem informando que a vari�vel foi declarada.
	 */
	public void verificaDeclaracaoDaVariavel(Token token) {
		var proximoToken = listaToken.get(indice + 1 );
		verificaVariavelJaDeclarada(proximoToken);
		tabelaSimbolos.put(proximoToken.getLexema(), false);
		System.out.println("A variavel '" + proximoToken.getLexema() + "' foi declarada");
		
		
	}
	
	
	
	/**
	 * Verifica a presen�a e o tratamento de uma vari�vel em uma express�o.
	 *
	 * @param tokenAtual O token atual que representa a vari�vel.
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
	 * Marca um argumento de leitura como atribu�do.
	 * O �ndice do argumento lido � obtido somando-se 2 ao valor do �ndice atual.
	 * O pr�ximo token � obtido a partir do �ndice calculado.
	 * Verifica se a vari�vel representada pelo pr�ximo token n�o foi declarada anteriormente.
	 * Adiciona a vari�vel � tabela de s�mbolos com o valor de atribui��o como verdadeiro.
	 * Imprime uma mensagem informando que a vari�vel � um valor de entrada.
	 */
	private void marcarArgumentoLeituraComoAtribuido() {
        final var argumento_lido = this.indice + 2;
        var proximoToken = getListaToken().get(argumento_lido);
        verificaVariavelNaoDeclarada(proximoToken);
        tabelaSimbolos.put(proximoToken.getLexema(), true);
        System.out.println("A variavel '" + proximoToken.getLexema() + "' e um valor a ser lido");
    }
	
	
	/**
	 * Verifica se a vari�vel j� foi declarada anteriormente.
	 * Se a vari�vel estiver declarada, uma mensagem de aviso � impressa no console,
	 * informando o nome da vari�vel e a linha/coluna onde ocorre a declara��o.
	 *
	 * @param tokenAtual O token que representa a vari�vel atual.
	 */
	private void verificaVariavelJaDeclarada(Token tokenAtual) {
        if (variavelDeclarada(tokenAtual)) {
        	System.out.println("A variavel '" + tokenAtual.getLexema() + "' j� est� declarado" +  tokenAtual.getLinhaColuna());
        	erroSemantico = true;
        }
    }
	
	/**
	 * Verifica o valor atribu�do a uma vari�vel e realiza a��es com base nesse valor.
	 * Se o valor for "0", a vari�vel � adicionada � tabela de s�mbolos com valor de atribui��o como falso.
	 * Se o valor contiver a sequ�ncia "/0", imprime uma mensagem de erro de "Divis�o por 0" no console.
	 * Caso contr�rio, a vari�vel � adicionada � tabela de s�mbolos com valor de atribui��o como verdadeiro.
	 * Imprime uma mensagem informando que a vari�vel recebeu um determinado valor.
	 *
	 * @param buffer      O StringBuilder contendo o valor atribu�do � vari�vel.
	 * @param tokenAtual  O token que representa a vari�vel atual.
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
	 * Verifica uma express�o e constr�i um valor acumulado no StringBuilder 'buffer'.
	 * O m�todo percorre a lista de tokens at� encontrar um token de ponto e v�rgula (';').
	 * Para cada token encontrado, verifica se o lexema � vazio.
	 * Se o lexema for vazio, verifica se a vari�vel associada ao lexema est� declarada e obt�m seu valor.
	 * Concatena o lexema � express�o acumulada no StringBuilder 'buffer', utilizando o valor da vari�vel se estiver declarada ou '0' se n�o estiver.
	 * Caso contr�rio, simplesmente concatena o lexema � express�o acumulada no StringBuilder 'buffer'.
	 *
	 * @param buffer O StringBuilder utilizado para construir a express�o acumulada.
	 */
	private void verificaExpressao(StringBuilder buffer) {
        while (!getListaToken().get(indice).getToken().equals(";")) {
            var lexeme = getListaToken().get(indice).getLexema();
            if (isVazio(lexeme)) {
            	verificaVariavelNaoDeclarada(getListaToken().get(indice));
                final boolean hasValue = tabelaSimbolos.get(lexeme);
                buffer.append(hasValue ? lexeme : "0"); //Se tiver valor concatena o lexema, sen�o concatena 0.
            }
            else {
                buffer.append(lexeme);
            }
            indice++;
        }
}


	/**
	 * Verifica se uma sequ�ncia de caracteres est� vazia, permitindo apenas letras e d�gitos.
	 * Retorna true se a sequ�ncia estiver vazia e contiver apenas letras e d�gitos,
	 * caso contr�rio, retorna false.
	 */
	private boolean isVazio(String lexeme) {
        if (!Character.isLetter(lexeme.charAt(0))) {
            return false;
        }
        return lexeme.chars().allMatch(Character::isLetterOrDigit);
    }
	
	
	
	
	/**
	 * Verifica os argumentos condicionais dentro de um par�ntese.
	 * O m�todo percorre a lista de tokens enquanto o token atual n�o for um par�ntese de fechamento (')').
	 * Para cada token encontrado, verifica se � um token identificador ("id").
	 * Se for um token identificador, chama o m�todo 'verificaVariavelNaoDeclarada' para verificar se a vari�vel n�o foi declarada anteriormente.
	 *
	 * A vari�vel 'indice' � incrementada dentro do loop, indicando o avan�o para o pr�ximo token.
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
	    	System.out.println("A variavel '" + tokenAtual.getLexema() + "' n�o foi declarada "
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
