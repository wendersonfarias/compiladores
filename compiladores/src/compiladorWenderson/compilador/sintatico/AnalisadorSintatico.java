package compiladorWenderson.compilador.sintatico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

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
	
	
	
	public AnalisadorSintatico() {
		this.pilha.push("$");
		this.pilha.push("<programa>");
		this.nomeArquivo = "tabela_tokens.txt";
		mapaLinha = criaLinha();
		mapaColuna = criaColuna();
		tabelaSintatica = inicializaTabelaSintatica();
	}


	


	public Boolean analisaSintatico() throws IOException {
		
		//imprimePontilhado();
		LOGS.add("Inicio da Analise Sintatica\n");
		LOGS.add("\nPilha atual");
		LOGS.add("$, <programa>" );
		//imprimePontilhado();
		
		obtemTokens();
		Integer producao;
		
		do {
			if(pilha.peek().contains("<") && pilha.peek().contains(">")) {
				producao =  
				tabelaSintatica
				[posicaoLinha(pilha.peek())]
				[posicaoColuna(tokens.peek())] ;
				
				if(producao == null) {
					imprimePontilhado();
					System.out.println("Erro sintatico: ["+ tokens.peek()+ "] inesperado");
					ErroSintatico = true;
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
						 
					else if( producao == 33) 
						producao33();
						 
					else if( producao == 34) 
						producao34();
						 
					else if( producao == 35) 
						producao35();
						 
					else if( producao == 36) 
						producao36();
						 
					else if( producao == 37) 
						producao37();
					else if( producao == 38) 
						producao38();
						 
	
					else {
						System.out.println("Erro sintatico token " + tokens.peek());
						ErroSintatico = true;
						System.out.println(linhasColunas.peek());
						break;
					}
					
				}
				
			}else {
				if(tokens.isEmpty() && pilha.peek().equals("$")) {
					System.out.println("***************************************************");
					System.out.println("*     Analise Sintatica feita com sucesso!        *");
					System.out.println("*     "+LocalDate.now()+"                                  *");
					System.out.println("***************************************************");
					break;
					
				}else if(pilha.peek().equals("$") && !tokens.isEmpty()) {
					imprimePontilhado();
					ErroSintatico = true;
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
					ErroSintatico = true;
				}
				
			}
		
		
		}while(!pilha.empty());
		
		logs();
		
		return ErroSintatico;
	
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
		
		
		tabela[1][1]=   2 ;tabela[1][4] =  3 ;
		tabela[1][5] =  3 ;tabela[1][6] =  3 ;
		tabela[1][7] =  2 ;tabela[1][8] =  3 ;
		tabela[1][9] =  2 ;tabela[1][10]=  2 ;
		tabela[1][11]=  2 ;tabela[1][12]=  2 ;
		
		tabela[2][1]=   9 ;tabela[2][7]=   6 ;
		tabela[2][9]=   7 ;tabela[2][10]=  5 ;
		tabela[2][11]=  4 ;tabela[2][12]=  8 ;
		
		tabela[3][11]=  10 ;
		
		tabela[4][10]=  11 ;
		
		tabela[5][7]=   12 ;
		
		tabela[6][7]=   13 ;
		
		tabela[7][5]=   15 ;tabela[7][8]=  14 ; 
		
		tabela[8][9]=   16 ;
		
		tabela[9][9]=   17 ;
		
		tabela[10][12]=  18 ;
		
		tabela[11][1]=  19 ;
		
		tabela[12][1]=  20 ;tabela[12][2]=  21 ;
		tabela[12][13]= 24 ;tabela[12][18]= 22 ;
		tabela[12][19]= 23 ;
		
		tabela[13][14]= 29 ;tabela[13][15]= 29 ;
		tabela[13][18]= 25 ;tabela[13][19]= 26 ;
		tabela[13][20]= 27 ;tabela[13][21]= 28 ;
		
		tabela[14][1]=  32 ;tabela[14][2]=  32 ;
		tabela[14][13]=  32 ;tabela[14][18]=  32 ;
		tabela[14][19]=  32 ;
		
		tabela[15][1]=  38 ;tabela[15][2]=  38 ;
		
		tabela[16][1]=  30 ;tabela[16][2]= 31  ;
		
		tabela[17][22]= 33 ;tabela[17][23]= 36 ;
		tabela[17][24]= 35 ;tabela[17][25]= 34 ;
		tabela[17][17]= 37 ;

		
		return tabela;
	}
	
	public HashMap<String, Integer> criaLinha(){
		HashMap<String, Integer> mapa = new HashMap<String, Integer>();
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("<programa>");
		lista.add("<declaracao_recursiva>");
		lista.add("<declaracao>");
		lista.add("<escreva>");
		lista.add("<leia>");
		lista.add("<condicional>");
		lista.add("<se>");
		lista.add("<senao>");
		lista.add("<loop>");
		lista.add("<while>");
		lista.add("<declaracao_var>");
		lista.add("<atribuicao>");
		lista.add("<expressao_basica>");
		lista.add("<operador_basico>");
		lista.add("<qualquer_expressao>");
		lista.add("<expressao_logica>");
		lista.add("<id_or_number>");
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
		getPilha().push("<declaracao>");
		
		empilhado.add("<declaracao_recursiva>");
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
		
		getPilha().push("<escreva>");
		
		empilhado.add("<escreva>");
		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao5() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("<leia>");
		
		empilhado.add("<leia>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao6() {
		
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("<condicional>");
		
		empilhado.add("<condicional");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao7() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("<loop>");
		
		empilhado.add("<loop>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao8() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		getPilha().push("<declaracao_var>");
		
		empilhado.add("<declaracao_var>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao9() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		
		getPilha().push("<atribuicao>");
		
		empilhado.add("<atribuicao>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao10() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		getPilha().push(";");
		getPilha().push(")");
		getPilha().push("<qualquer_expressao>");
		getPilha().push("(");
		getPilha().push("escreva");
		
		empilhado.add(";");
		empilhado.add(")");
		empilhado.add("<qualquer_expressao>");
		empilhado.add("(");
		empilhado.add("escreva");
		
		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao11() {
		String desenpilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desenpilhado = getPilha().pop();
		getPilha().push(";");
		getPilha().push(")");
		getPilha().push("id");
		getPilha().push("(");
		getPilha().push("leia");
		
		empilhado.add(";");
		empilhado.add(")");
		empilhado.add("id");
		empilhado.add("(");
		empilhado.add("leia");
		
		logPilha(desenpilhado,empilhado);
	}
	
	void producao12() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("fimse");
		getPilha().push("<senao>");
		getPilha().push("<se>");
		
		empilhado.add("fimse");
		empilhado.add("<senao>");
		empilhado.add("<se>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao13() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<declaracao_recursiva>");
		getPilha().push(")");
		getPilha().push("<expressao_logica>");
		getPilha().push("(");
		getPilha().push("se");
		
		empilhado.add("<declaracao_recursiva>");
		empilhado.add(")");
		empilhado.add("<expressao_logica>");
		empilhado.add("(");
		empilhado.add("se");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao14() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<declaracao_recursiva>");
		getPilha().push("senao");
		
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("senao");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao15() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
		
	}
	
	void producao16() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("fimlaco");
		getPilha().push("<while>");
		
		empilhado.add("fimlaco");
		empilhado.add("<while>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao17() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<declaracao_recursiva>");
		getPilha().push(")");
		getPilha().push("<expressao_logica>");
		getPilha().push("(");
		getPilha().push("laco");
		
		empilhado.add("<declaracao_recursiva>");
		empilhado.add(")");
		empilhado.add("<expressao_logica>");
		empilhado.add("(");
		empilhado.add("laco");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao18() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(";");
		getPilha().push("id");
		getPilha().push("var");
		
		empilhado.add(";");
		empilhado.add("id");
		empilhado.add("var");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao19() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(";");
		getPilha().push("<expressao_basica>");
		getPilha().push("=");
		getPilha().push("id");
		
		empilhado.add(";");
		empilhado.add("<expressao_basica>");
		empilhado.add("=");
		empilhado.add("id");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao20() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<operador_basico>");
		getPilha().push("id");
		
		empilhado.add("<operador_basico>");
		empilhado.add("id");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao21() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<operador_basico>");
		getPilha().push("numero");
		
		empilhado.add("<operador_basico>");
		empilhado.add("numero");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao22() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<expressao_basica>");
		getPilha().push("+");
		
		empilhado.add("<expressao_basica>");
		empilhado.add("+");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao23() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<expressao_basica>");
		getPilha().push("-");
		
		empilhado.add("<expressao_basica>");
		empilhado.add("-");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao24() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<operador_basico>");
		getPilha().push(")");
		getPilha().push("<expressao_basica>");
		getPilha().push("(");
		
		empilhado.add("<operador_basico>");
		empilhado.add(")");
		empilhado.add("<expressao_basica>");
		empilhado.add("(");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao25() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<expressao_basica>");
		getPilha().push("+");
		
		empilhado.add("<expressao_basica>");
		empilhado.add("+");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao26() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<expressao_basica>");
		getPilha().push("-");
		
		empilhado.add("<expressao_basica>");
		empilhado.add("-");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao27() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<expressao_basica>");
		getPilha().push("*");
		
		empilhado.add("<expressao_basica>");
		empilhado.add("*");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao28() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<expressao_basica>");
		getPilha().push("/");
		
		empilhado.add("<expressao_basica>");
		empilhado.add("/");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao29() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao30() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("id");

		empilhado.add("id");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao31() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("numero");
		
		empilhado.add("numero");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao32() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<expressao_basica>");
		
		empilhado.add("<expressao_basica>");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao33() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<");

		empilhado.add("<");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao34() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(">");
		
		empilhado.add(">");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao35() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push(">=");
		
		empilhado.add(">=");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao36() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<=");
		
		empilhado.add("<=");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao37() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("==");
		
		empilhado.add("==");
		logPilha(desenpilhado,empilhado);
	}
	
	void producao38() {
		ArrayList<String> empilhado = new ArrayList<String>();
		String desenpilhado = getPilha().pop();
		getPilha().push("<id_or_number>");
		getPilha().push("<operador_logico>");
		getPilha().push("<id_or_number>");
		
		empilhado.add("<id_or_number>");
		empilhado.add("<operador_logico>");
		empilhado.add("<id_or_number>");
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
		InputStream fis =new FileInputStream(getNomeArquivo());  
		Reader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		Stack<String> linhasTokens = new Stack<String>(); 
		
		InputStream fis1 =new FileInputStream("tabela_linha_coluna.txt");  
		Reader isr1 = new InputStreamReader(fis1);
		BufferedReader br1 = new BufferedReader(isr1);
		Stack<String> LinhasColunas = new Stack<String>(); 
		
		String linha ;
		do {
			linha = br.readLine();
			if(linha != null) {
				
				linhasTokens.push(linha);
			} 
			
		}while( linha != null );
		
		linha = null;
		do {
			linha = br1.readLine();
			if(linha != null) {
				
				LinhasColunas.push(linha);
			} 
			
		}while( linha != null );
		
		br1.close();
		
		Collections.reverse(linhasTokens);
		Collections.reverse(LinhasColunas);
		
		this.tokens = linhasTokens;
		this.linhasColunas = LinhasColunas;
		
	}
	
	void logs() throws IOException {
		OutputStream fos = new FileOutputStream("log_sintatico.txt");   
		Writer osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		
		for (int i = 0; i < LOGS.size(); i++) {
			bw.write(LOGS.get(i));
			bw.newLine();
			bw.flush();
		}
		
		bw.close();
		
		
	}
	
}



