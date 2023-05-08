package compiladorWenderson.compilador.sintatico;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import compiladorWenderson.compilador.Token;

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
	
	
	ArrayList<Token> listaToken = new ArrayList<Token>();
	
	public AnalisadorSintatico(ArrayList<Token> listaToken) {
		this.listaToken = listaToken;
		this.pilha.push("$");
		this.pilha.push("<programa>");
		this.nomeArquivo = "tabela_tokens.txt";
		mapaLinha = criaLinha();
		mapaColuna = criaColuna();
		tabelaSintatica = inicializaTabelaSintatica();
	}


	


	public ArrayList<String> analisaSintatico() throws IOException {
		
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
					ErroSintatico = (true);
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
						 
					else {
						System.out.println("Erro sintatico token " + tokens.peek());
						ErroSintatico = (true);
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
					ErroSintatico = (true);
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
					ErroSintatico = (true);
				}
				
			}
		
		
		}while(!pilha.empty());
		
		//escreveLogs();
		
		if(ErroSintatico) {
			return null;
		}
		return LOGS;
	
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
		Integer[][] tabela =  new Integer[30][30]; ;
		
		int linhas = tabela.length;
		int colunas = tabela[0].length;

		for (int i = 0; i < linhas; i++) {
		    for (int j = 0; j < colunas; j++) {
		        tabela[i][j] = null;
		    }
		}
		
		tabela[0][0] =  1 ;tabela[0][3] =  0 ;
		
		
		tabela[1][1]=   2 ;tabela[1][4] =  3 ;
		tabela[1][5] =  3 ;tabela[1][6] =  3 ;
		tabela[1][7] =  2 ;tabela[1][8] =  3 ;
		tabela[1][9]=   2 ;tabela[1][10]=  2 ;
		tabela[1][11]=  2 ;tabela[1][12]=  2 ;
		
		tabela[2][1]=   4 ;tabela[2][7]=   9 ;
		tabela[2][9]=   8 ;tabela[2][10]=  5 ;
		tabela[2][11]=  6 ;tabela[2][12]=  7 ;
		
		tabela[3][1]=  12 ;tabela[3][2]=  12 ;
		tabela[3][13]= 12 ;
		
		tabela[4][5]=  11 ;tabela[4][8]=  10 ;
		
		tabela[5][1]=  15 ;tabela[5][2]= 15 ;
		tabela[5][13]= 15 ;
		
		tabela[6][14]=  14 ;tabela[6][15]= 14 ;
		tabela[6][17]=  14 ;tabela[6][18]= 13 ;
		tabela[6][19]=  13 ;tabela[6][22]=  14 ;
		tabela[6][23]=  14 ;tabela[6][24]=  14 ;
		tabela[6][25]=  14 ;
		
		tabela[7][18]=  21 ;tabela[7][19]=  22; 
		
		tabela[8][1]=   19 ;tabela[8][2]=   20 ;
		tabela[8][13]=  18 ;
		
		tabela[9][14]=  17 ;tabela[9][15]=  17 ;
		tabela[9][17]=  17 ;tabela[9][18]=  17 ;
		tabela[9][19]=  17 ;tabela[9][20]=  16 ;
		tabela[9][21]=  16 ;tabela[9][22]=  17 ;
		tabela[9][23]=  17 ;tabela[9][24]=  17 ;
		tabela[9][25]=  17 ;
		
	
		tabela[10][20]= 23 ;tabela[10][21]=  24;
		
		tabela[11][13]=  25;
		
		tabela[12][17]=  30;tabela[12][22]=  26;
		tabela[12][23]=  27;tabela[12][24]=  28;
		tabela[12][25]=  29;
		

		
		return tabela;
	}
	
	public HashMap<String, Integer> criaLinha(){
		HashMap<String, Integer> mapa = new HashMap<String, Integer>();
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("<programa>");
		lista.add("<declaracao_recursiva>");
		lista.add("<declaracao>");
		lista.add("<expressao>");
		lista.add("<senao>");
		lista.add("<termo>");
		lista.add("<proximo_termo>");
		lista.add("<mais_menos>");
		lista.add("<fator>");
		lista.add("<proximo_fator>");
		lista.add("<multi_div>");
		lista.add("<expressao_logica>");
		lista.add("<operador_logico>");
		
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
		
		getPilha().push("id");
		getPilha().push("var");
		
		empilhado.add("id");
		empilhado.add("var");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao8() {
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
	
	void producao9() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("fimse");
		getPilha().push("<senao>");
		getPilha().push("<declaracao_recursiva>");
		getPilha().push("<expressao_logica>");
		getPilha().push("se");
		
		empilhado.add("fimse");
		empilhado.add("<senao>");
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("<expressao_logica>");
		empilhado.add("se");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao10() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();

		getPilha().push("<declaracao_recursiva>");
		getPilha().push("senao");
		
		
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("senao");

		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao11() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		getPilha().push("î");
	
		empilhado.add("î");

		
		logPilha(desenpilhado,empilhado);
	}
	
	void producao12() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<proximo_termo>");
		getPilha().push("<termo>");
		
		empilhado.add("<proximo_termo>");
		empilhado.add("<termo>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao13() {
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
	
	void producao14() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao15() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<proximo_fator>");
		getPilha().push("<fator>");
		

		empilhado.add("<proximo_fator>");
		empilhado.add("<fator>");
		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao16() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<proximo_fator>");
		getPilha().push("<fator>");
		getPilha().push("<multi_div>");
		
		empilhado.add("<proximo_fator>");
		empilhado.add("<fator>");
		empilhado.add("<multi_div>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao17() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao18() {
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
	
	void producao19() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("id");

		empilhado.add("id");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao20() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("numero");

		empilhado.add("numero");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao21() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("+");
		
		empilhado.add("+");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao22() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("-");
		
		empilhado.add("-");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao23() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("*");
		
		empilhado.add("*");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao24() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("/");
	
		empilhado.add("/");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao25() {
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
	
	void producao26() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<");

		empilhado.add("<");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao27() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<=");
	
		empilhado.add("<=");
		
		logPilha(desenpilhado,empilhado);
	}
	
	void producao28() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(">=");

		empilhado.add(">=");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao29() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(">");
		
		empilhado.add(">");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao30() {
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
		
		for(Token valor: listaToken) {
			linhasTokens.add(valor.getToken());
			LinhasColunas.add(valor.getLinhaColuna());
		}
		
	
		
		
		Collections.reverse(linhasTokens);
		Collections.reverse(LinhasColunas);
		
		this.tokens = linhasTokens;
		this.linhasColunas = LinhasColunas;
		
	}
	
	/*void escreveLogs() throws IOException {
		
		for (int i = 0; i < LOGS.size(); i++) {
			tokensLinhaColunaLog.getLogSintatico().add(LOGS.get(i));
			
		}
		
		
	}*/
	
}



