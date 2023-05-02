package compiladorWenderson.compilador.sintatico;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import compiladorWenderson.compilador.TokensLinhaColunaLog;

public class AnalisadorSintatico {
	
	Stack<String> pilha = new Stack<String>();
	Stack<String> tokens= new Stack<String>();
	Stack<String> linhasColunas= new Stack<String>();
	String nomeArquivo;
	ArrayList<String> LOGS = new ArrayList<>();
	
	HashMap<String, Integer> mapaLinha = new HashMap<String, Integer>();
	HashMap<String, Integer> mapaColuna = new HashMap<String, Integer>();
	
	Integer[][] tabelaSintatica = new Integer[18][26];
	
	Boolean ErroSintatico = false;
	
	TokensLinhaColunaLog tokensLinhaColunaLog  = new  TokensLinhaColunaLog();
	
	
	
	public AnalisadorSintatico(TokensLinhaColunaLog tokensLinhaColunaLog) {
		this.tokensLinhaColunaLog = tokensLinhaColunaLog;
		this.pilha.push("$");
		this.pilha.push("<programa>");
		this.nomeArquivo = "tabela_tokens.txt";
		mapaLinha = criaLinha();
		mapaColuna = criaColuna();
		tabelaSintatica = inicializaTabelaSintatica();
	}


	


	public Boolean analisaSintatico() throws IOException {
		this.tokensLinhaColunaLog.setErroSintatico(false);
		
		//imprimePontilhado();
		LOGS.add("Inicio da Analise Sintatica\n");
		LOGS.add("\nPilha atual");
		LOGS.add("$, <programa>" );
		//imprimePontilhado();
		
		obtemTokens();
		Integer producao;
		
		do {
			if(pilha.peek().contains("<") && pilha.peek().contains(">")) {
				
				
				producao =  producaoTabelaSintatica(posicaoLinha(pilha.peek()),posicaoColuna(tokens.peek()));
						
				
				if(producao == null) {
					imprimePontilhado();
					System.out.println("Erro sintatico: ["+ tokens.peek()+ "] inesperado");
					this.tokensLinhaColunaLog.setErroSintatico(true);
					System.out.println(linhasColunas.peek());
					imprimePontilhado();
					break;
				}else {
					if(  producao == 0) 
						producao0();
						 
					else  if( producao == 1) 
						producao1();
						 
					else  if( producao == 2) 
						producao2();
						 
					else if( producao == 3) 
						producao3();
						 
					else if( producao == 4) 
						producao4();
						 
					else if( producao == 5) 
						producao5();
						 
					else if( producao == 6) 
						producao6();
						 
					else if( producao == 7) 
						producao7();
						 
					else if( producao == 8) 
						producao8();
						 
					else if( producao == 9) 
						producao9();
						 
					else if( producao == 10) 
						producao10();
						 
					else if( producao == 11) 
						producao11();
						 
					else if( producao == 12) 
						producao12();
						 
					else if( producao == 13) 
						producao13();
						 
					else if( producao == 14) 
						producao14();
						 
					else if( producao == 15) 
						producao15();
						 
					else if( producao == 16) 
						producao16();
						 
					else if( producao == 17) 
						producao17();
						 
					else if( producao == 18) 
						producao18();
						 
					else if( producao == 19) 
						producao19();
						 
					else if( producao == 20) 
						producao20();
						 
					else if( producao == 21) 
						producao21();
						 
					else if( producao == 22) 
						producao22();
						 
					else if( producao == 23) 
						producao23();
						 
					else if( producao == 24) 
						producao24();
						 
					else if( producao == 25) 
						producao25();
						 
					else if( producao == 26) 
						producao26();
						 
					else if( producao == 27) 
						producao27();
						 
					else if( producao == 28) 
						producao28();
						 
					else if( producao == 29) 
						producao29();
						 
					else if( producao == 30) 
						producao30();
						 
					else if( producao == 31) 
						producao31();
						 
					else if( producao == 32) 
						producao32();
						 
						 
	
					else {
						System.out.println("Erro sintatico token " + tokens.peek());
						this.tokensLinhaColunaLog.setErroSintatico(true);
						System.out.println(linhasColunas.peek());
						break;
					}
					
				}
				
			}else {
				if(tokens.isEmpty() && pilha.peek().equals("$")) {
					LOGS.add("***************************************************");
					LOGS.add("*     Analise Sintatica feita com sucesso!        *");
					LOGS.add("*     "+LocalDate.now()+"                                  *");
					LOGS.add("***************************************************");
					break;
					
				}else if(pilha.peek().equals("$") && !tokens.isEmpty()) {
					imprimePontilhado();
					this.tokensLinhaColunaLog.setErroSintatico(true);
					System.out.println("Erro sintatico "+ tokens.peek()+ " inesperado");
					System.out.println(linhasColunas.peek());
					System.out.println("\nDesempilhado: "+ tokens.pop());
					linhasColunas.pop();
					imprimePontilhado();
					
				
				}else if(pilha.peek().equals(tokens.peek())) {
					LOGS.add("\n**************Dois Terminais*****************");
					LOGS.add("\nDesempilhado: "+ pilha.pop());
					LOGS.add("\nDesempilhado: "+ tokens.pop());
					LOGS.add("\n*********************************************");
					
					linhasColunas.pop();
					
					

				}else if(pilha.peek().equals("î")) {
					LOGS.add("\n********  Vazio  *****************************");
					LOGS.add("\nDesempilhado: "+ pilha.pop());
					LOGS.add("\n**********************************************");
				
				}
				
				else {
					System.out.println("Erro sintatico " + tokens.peek() + " inesperado");
					System.out.println(linhasColunas.peek());
					this.tokensLinhaColunaLog.setErroSintatico(true);
				}
				
			}
		
		
		}while(!pilha.empty());
		
		escreveLogs();
		
		return this.tokensLinhaColunaLog.getErroSintatico();
	
	}
	



	private Integer producaoTabelaSintatica(Integer posicaoLinha, Integer posicaoColuna) {
		Integer producao  = tabelaSintatica[posicaoLinha][posicaoColuna] ;
		return producao;
	}





	public Stack<String> getPilha() {
		return pilha;
	}


	public void setPilha(Stack<String> pilha) {
		this.pilha = pilha;
	}


	public String getNomeArquivo() {
		return nomeArquivo;
	}


	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	
	public HashMap<String, Integer> getMapaLinha() {
		return mapaLinha;
	}


	public void setMapaLinha(HashMap<String, Integer> mapaLinha) {
		this.mapaLinha = mapaLinha;
	}


	public HashMap<String, Integer> getMapaColuna() {
		return mapaColuna;
	}


	public void setMapaColuna(HashMap<String, Integer> mapaColuna) {
		this.mapaColuna = mapaColuna;
	}


	public Integer[][] getTabelaSintatica() {
		return tabelaSintatica;
	}





	public void setTabelaSintatica(Integer[][] tabelaSintatica) {
		this.tabelaSintatica = tabelaSintatica;
	}



	public Integer posicaoLinha(String naoTerminal) {
		return getMapaLinha().get(naoTerminal);
		
	}
	public Integer posicaoColuna(String token) {
		return getMapaColuna().get(token);
		
	}
	
	
	
	private Integer[][] inicializaTabelaSintatica() {
		Integer[][] tabela =  new Integer[24][27]; ;
		
		int linhas = tabela.length;
		int colunas = tabela[0].length;

		for (int i = 0; i < linhas; i++) {
		    for (int j = 0; j < colunas; j++) {
		        tabela[i][j] = null;
		    }
		}
		
		tabela[0][0] =  1 ;tabela[0][3] =  0 ;
		
		
		tabela[1][4]=   3 ;tabela[1][6] =  3 ;
		tabela[1][7] =  2 ;tabela[1][8] =  3 ;
		tabela[1][9] =  2 ;tabela[1][10] = 2 ;
		tabela[1][11]=  2 ;tabela[1][12]=  2 ;
		tabela[1][15]=  3 ;
		
		tabela[2][7]=  11 ;tabela[2][9]=   10;
		tabela[2][10]=  5 ;tabela[2][11]=  6 ;
		tabela[2][12]=  7 ;
		
		tabela[3][1]=  4 ;
		
		tabela[4][1]=  14 ;tabela[4][2]=  14 ;
		tabela[4][13]= 14 ;
		
		tabela[5][8]=   12;tabela[5][15]= 13 ;
		
		tabela[6][1]=   17 ;tabela[6][2]= 17;
		tabela[6][13]=  17 ;
		
		tabela[7][14]=  16 ;tabela[7][15]=  16; 
		tabela[7][17]=  16 ;tabela[7][18]=  15;
		tabela[7][19]=  15 ;tabela[7][22]=  16;
		tabela[7][23]=  16;  tabela[7][24]=  16;
		tabela[7][25]=  16;
		
		tabela[8][18]=  23 ;tabela[8][19]=  24 ;
		
		tabela[9][1]=   21 ;tabela[9][2]=   22 ;
		tabela[9][13]=  20 ;
		
		tabela[10][14]=  19;tabela[10][15]=  19 ;
		tabela[10][17]=  19;tabela[10][18]=  19;
		tabela[10][19]=  19;tabela[10][20]=  18;
		tabela[10][21]=  18;tabela[10][22]=  19;
		tabela[10][23]=  19;tabela[10][24]=  19;
		tabela[10][25]=  19;
		
		tabela[11][20]=  25;tabela[11][21]=  26;
		
		tabela[12][13]=  27;
		
		tabela[13][17]= 32 ;tabela[13][22]= 28 ;
		tabela[13][23]= 29 ;tabela[13][24]= 30 ;
		tabela[13][25]= 31 ;
		
		tabela[14][15]=  9 ;tabela[14][26]=  8 ;

		
		return tabela;
	}
	
	public HashMap<String, Integer> criaLinha(){
		HashMap<String, Integer> mapa = new HashMap<String, Integer>();
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("<programa>");
		lista.add("<declaracao_recursiva>");
		lista.add("<declaracao>");
		lista.add("<atribuicao>");
		lista.add("<expressao>");
		lista.add("<senao>");
		lista.add("<proximo_termo>");
		lista.add("<mais_menos>");
		lista.add("<fator>");
		lista.add("<proximo_fator>");
		lista.add("<multi_div>");
		lista.add("<expressao_logica>");
		lista.add("<operador_logico>");
		lista.add("<declarao_variaveis>");
		
		for (int i = 0; i < lista.size(); i++) {
		    mapa.put(lista.get(i), i);
		}
		
		return mapa;
	}
	
	public HashMap<String, Integer> criaColuna(){
		HashMap<String, Integer> mapa = new HashMap<String, Integer>();
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("$");
		lista.add("id");
		lista.add("numero");
		lista.add("inicioprograma");
		lista.add("fimprograma");
		lista.add("fimse");
		lista.add("fimlaco");
		lista.add("se");
		lista.add("senao");
		lista.add("laco");
		lista.add("leia");
		lista.add("escreva");
		lista.add("var");
		lista.add("(");
		lista.add(")");
		lista.add(";");
		lista.add("=");
		lista.add("==");
		lista.add("+");
		lista.add("-");
		lista.add("*");
		lista.add("/");
		lista.add("<");
		lista.add("<=");
		lista.add(">=");
		lista.add(">");
		lista.add(",");
		
		
		
		for (int i = 0; i < lista.size(); i++) {
		    mapa.put(lista.get(i), i);
		}
		
		return mapa;
	}
	
	void producao0() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		getPilha().push("fimprograma");
		getPilha().push("<declaracao_recursiva>");
		getPilha().push("inicioprograma");
		
		empilhado.add("fimprograma");
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("inicioprograma" );
		logPilha(desenpilhado,empilhado);
	}
	
	
	
	void producao1() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao2() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("<declaracao_recursiva>");
		getPilha().push(";");
		getPilha().push("<declaracao>");
		
		empilhado.add("<declaracao_recursiva>");
		empilhado.add(";");
		empilhado.add("<declaracao>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao3() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao4() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("<expressao>");
		getPilha().push("=");
		getPilha().push("id");
		
		empilhado.add("<expressao>");
		empilhado.add("=");
		empilhado.add("id");
		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao5() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push(")");
		getPilha().push("id");
		getPilha().push("(");
		getPilha().push("leia");
		
		empilhado.add(")");
		empilhado.add("id");
		empilhado.add("(");
		empilhado.add("leia");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao6() {
		
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("<expressao>");
		getPilha().push("escreva");
		
		empilhado.add("<expressao>");
		empilhado.add("escreva");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao7() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("<declarao_variaveis>");
		getPilha().push("id");
		getPilha().push("var");
		
		empilhado.add("<declarao_variaveis>");
		empilhado.add("id");
		empilhado.add("var");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao8() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		getPilha().push("<declarao_variaveis>");
		getPilha().push("id");
		getPilha().push(",");
		
		empilhado.add("<declarao_variaveis>");
		empilhado.add("id");
		empilhado.add(",");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao9() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao10() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		getPilha().push("fimlaco");
		getPilha().push("<declaracao_recursiva>");
		getPilha().push("<expressao_logica>");
		getPilha().push("laco");
		
		
		empilhado.add("fimlaco");
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("<expressao_logica>");
		empilhado.add("laco");

		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao11() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		getPilha().push("<senao>");
		getPilha().push("<declaracao_recursiva>");
		getPilha().push("<expressao_logica>");
		getPilha().push("se");

		
		empilhado.add("<senao>");
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("<expressao_logica>");
		empilhado.add("se");

		
		logPilha(desenpilhado,empilhado);
	}
	
	void producao12() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("senao");
		getPilha().push("<declaracao_recursiva>");
		

		empilhado.add("senao");
		empilhado.add("<declaracao_recursiva>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao13() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("î");
	
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao14() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<proximo_termo>");
		getPilha().push(" <termo>");
		
		empilhado.add("<proximo_termo>");
		empilhado.add(" <termo>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao15() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<proximo_termo>");
		getPilha().push("<termo>");
		getPilha().push("<mais_menos>");
		
		empilhado.add("<proximo_termo>");
		empilhado.add("<termo>");
		empilhado.add("<mais_menos>");
		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao16() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao17() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<proximo_fator>");
		getPilha().push("<fator>");
		

		empilhado.add("<proximo_fator>");
		empilhado.add("<fator>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao18() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<proximo_fator>");
		getPilha().push("<fator>");
		getPilha().push("<multi_div>");
		
		empilhado.add("<proximo_fator>");
		empilhado.add("<fator>");
		empilhado.add("<multi_div> ");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao19() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("î");

		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao20() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(")");
		getPilha().push("<expressao>");
		getPilha().push("(");
		
		empilhado.add(")");
		empilhado.add("<expressao>");
		empilhado.add("(");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao21() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("id");
		
		empilhado.add("id");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao22() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("numero");
		
		empilhado.add("numero");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao23() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("+");
		
		empilhado.add("+");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao24() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("-");
	
		empilhado.add("-");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao25() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("*");

		empilhado.add("*");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao26() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("/");

		empilhado.add("/");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao27() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(")");
		getPilha().push("<expressao>");
		getPilha().push("<operador_logico>");
		getPilha().push("<expressao>");
		getPilha().push("(");
		
		empilhado.add(")");
		empilhado.add("<expressao>");
		empilhado.add("<operador_logico>");
		empilhado.add("<expressao>");
		empilhado.add("(");
		
		logPilha(desenpilhado,empilhado);
	}
	
	void producao28() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<");

		empilhado.add("<");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao29() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<=");
		
		empilhado.add("<=");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao30() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(">=");

		empilhado.add(">=");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao31() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(">");
		
		empilhado.add(">");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao32() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("==");
		
		empilhado.add("==");
		logPilha(desenpilhado,empilhado);
	}
	
	
	
	
	void logPilha(String desempilhado, ArrayList<String> empilhado){
		
		LOGS.add("\nDesempilhado: "+ desempilhado);

		empilhado.forEach(valor -> 
			LOGS.add("\nEmpilhado: " + valor)
		);
		
		
		//imprimePontilhado();
	}
	
	void imprimePontilhado() {
		System.out.println("-----------------------------------"
				+ "---------------------------------------"
				+ "----------------------------------------");
	}
	
	public void obtemTokens() throws IOException {
		Stack<String> linhasTokens = new Stack<String>(); 
		Stack<String> LinhasColunas = new Stack<String>(); 
		
	
		linhasTokens.addAll(tokensLinhaColunaLog.getTabelaSimbolos());
		
		LinhasColunas.addAll(tokensLinhaColunaLog.getLinhaColuna());
		
		
		Collections.reverse(linhasTokens);
		Collections.reverse(LinhasColunas);
		
		this.tokens = linhasTokens;
		this.linhasColunas = LinhasColunas;
		
	}
	
	void escreveLogs() throws IOException {
		
		for (int i = 0; i < LOGS.size(); i++) {
			tokensLinhaColunaLog.getLogSintatico().add(LOGS.get(i));
			
		}
		
		
	}
	
}



