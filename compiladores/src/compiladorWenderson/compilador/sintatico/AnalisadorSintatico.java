package compiladorWenderson.compilador.sintatico;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import compiladorWenderson.compilador.Token;

public class AnalisadorSintatico {
	
	Stack<String> pilha = new Stack<String>();
	Stack<String> tokens= new Stack<String>();
	Stack<String> linhasColunas= new Stack<String>();
	String nomeArquivo;
	ArrayList<String> LOGS = new ArrayList<>();
	
	
	Boolean ErroSintatico = false;
	
	
	ArrayList<Token> listaToken = new ArrayList<Token>();
	
	public AnalisadorSintatico(ArrayList<Token> listaToken) {
		this.listaToken = listaToken;
		this.pilha.push("$");
		this.pilha.push("<programa>");
		this.nomeArquivo = "tabela_tokens.txt";
	}


	


	public ArrayList<String> analisaSintatico() throws IOException {
		
		LOGS.add("Inicio da Analise Sintatica\n");
		LOGS.add("\nPilha atual");
		LOGS.add("$, <programa>" );
		//imprimePontilhado();
		
		obtemTokens();

		
		do {
			if(pilha.peek().contains("<") && pilha.peek().contains(">")) {
				
				
				tabelaSintatica(pilha.peek(),tokens.peek());
						
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
					System.out.println("Erro sintatico token [ " + tokens.peek() + " ] inesperado " + linhasColunas.peek());
					System.out.println("Não foi possivel continuar a Analise Sintatica ");
					System.out.println("Corrija os erros! e tente novamente.");
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
				else if(tokens.peek().equals("id") && pilha.peek().equals("=")) {
					imprimePontilhado();
					System.out.println("ERRO SINTÁTICO: Era esperado [ = ] " + linhasColunas.peek());
					System.out.println("Não foi possivel continuar a Analise Sintatica ");
					System.out.println("Corrija os erros! e tente novamente.");
					ErroSintatico = (true);
					imprimePontilhado();
					
				}
				else if(tokens.peek().equals("var") && pilha.peek().equals(";")) {
					imprimePontilhado();
					System.out.println("ERRO SINTÁTICO: Era esperado [ ; ] " + linhasColunas.peek());
					System.out.println("Não foi possivel continuar a Analise Sintatica ");
					System.out.println("Corrija os erros! e tente novamente.");
					ErroSintatico = (true);
					imprimePontilhado();
					
				}
				else {
					imprimePontilhado();
					System.out.println("Erro sintatico token [ " + tokens.peek() + " ] inesperado " + linhasColunas.peek());
					System.out.println("Não foi possivel continuar a Analise Sintatica ");
					System.out.println("Corrija os erros! e tente novamente.");
					ErroSintatico = (true);
					imprimePontilhado();
				}
				
			}
		
		
		}while(!pilha.empty() && !ErroSintatico);
		
		return LOGS;
	
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
	
	
	private void tabelaSintatica(String topoPilha, String tokenEntrada) {
		if(topoPilha.equals("<programa>")) {
			switch (tokenEntrada) {
			case "$": {
				producao1();
				break;
			}
			case "inicioprograma": {
				producao0();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<declaracao_recursiva>")) {
			switch (tokenEntrada) {
			case "id": {
				producao2();
				break;
			}
			case "fimprograma": {
				producao3();
				break;
			}
			case "fimse": {
				producao3();
				break;
			}
			case "fimlaco": {
				producao3();
				break;
			}
			case "se": {
				producao2();
				break;
			}
			case "senao": {
				producao3();
				break;
			}case "laco": {
				producao2();
				break;
			}
			case "leia": {
				producao2();
				break;
			}
			case "escreva": {
				producao2();
				break;
			}
			case "var": {
				producao2();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<declaracao>")) {
			switch (tokenEntrada) {
			case "id": {
				producao4();
				break;
			}
			case "se": {
				producao9();
				break;
			}
			case "laco": {
				producao8();
				break;
			}
			case "leia": {
				producao5();
				break;
			}
			case "escreva": {
				producao6();
				break;
			}
			case "var": {
				producao7();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<expressao>")) {
			switch (tokenEntrada) {
			case "id": {
				producao12();
				break;
			}
			case "numero": {
				producao12();
				break;
			}
			case "(": {
				producao12();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<senao>")) {
			switch (tokenEntrada) {
			case "fimse": {
				producao11();
				break;
			}
			case "senao": {
				producao10();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<termo>")) {
			switch (tokenEntrada) {
			case "id": {
				producao15();
				break;
			}
			case "numero": {
				producao15();
				break;
			}
			case "(": {
				producao15();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<proximo_termo>")) {
			switch (tokenEntrada) {
			case ")": {
				producao14();
				break;
			}
			case ";": {
				producao14();
				break;
			}
			case "==": {
				producao14();
				break;
			}
			case "+": {
				producao13();
				break;
			}
			case "-": {
				producao13();
				break;
			}
			case "<": {
				producao14();
				break;
			}
			case "<=": {
				producao14();
				break;
			}
			case ">=": {
				producao14();
				break;
			}case ">": {
				producao14();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<mais_menos>")) {
			switch (tokenEntrada) {
			case "+": {
				producao21();
				break;
			}
			case "-": {
				producao22();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<fator>")) {
			switch (tokenEntrada) {
			case "id": {
				producao19();
				break;
			}
			case "numero": {
				producao20();
				break;
			}
			case "(": {
				producao18();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<proximo_fator>")) {
			switch (tokenEntrada) {
			case ")": {
				producao17();
				break;
			}
			case ";": {
				producao17();
				break;
			}
			case "==": {
				producao17();
				break;
			}
			case "+": {
				producao17();
				break;
			}
			case "-": {
				producao17();
				break;
			}
			case "*": {
				producao16();
				break;
			}
			case "/": {
				producao16();
				break;
			}
			case "<": {
				producao17();
				break;
			}
			case "<=": {
				producao17();
				break;
			}
			case ">=": {
				producao17();
				break;
			}
			case ">": {
				producao17();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<multi_div>")) {
			switch (tokenEntrada) {
			case "*": {
				producao23();
				break;
			}
			case "/": {
				producao24();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else if(topoPilha.equals("<expressao_logica>")) {
			switch (tokenEntrada) {
			case "(": {
				producao25();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}
		else if(topoPilha.equals("<operador_logico>")) {
			switch (tokenEntrada) {
			case "==": {
				producao30();
				break;
			}
			case "<": {
				producao26();
				break;
			}
			case "<=": {
				producao27();
				break;
			}
			case ">=": {
				producao28();
				break;
			}
			case ">": {
				producao29();
				break;
			}
			default:
				imprimePontilhado();
				System.out.println("Erro sintatico token [ " + tokenEntrada + " ] inesperado " + linhasColunas.peek());
				ErroSintatico = (true);
				System.out.println("Não foi possivel continuar a Analise Sintatica ");
				System.out.println("Corrija os erros! e tente novamente.");
				imprimePontilhado();
				break;
			}
		}else {
			imprimePontilhado();
			System.out.println("Erro sintatico: ["+  tokenEntrada+ "] inesperado");
			ErroSintatico = (true);
			System.out.println(linhasColunas.peek());
			imprimePontilhado();
		}
		
	}
	
	
	
	void producao0() {
		LOGS.add("\nEntrou na producao 0");
		
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		getPilha().push("fimprograma");
		getPilha().push("<declaracao_recursiva>");
		getPilha().push("inicioprograma");
		
		empilhado.add("fimprograma");
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("inicioprograma" );
		
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 0");
	}
	
	
	
	void producao1() {
		LOGS.add("\nEntrou na producao 1");
		
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 1");
	}
	
	void producao2() {
		LOGS.add("\nEntrou na producao 2");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		
		getPilha().push("<declaracao_recursiva>");
		getPilha().push(";");
		getPilha().push("<declaracao>");
		
		empilhado.add("<declaracao_recursiva>");
		empilhado.add(";");
		empilhado.add("<declaracao>");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 2");
	}
	
	void producao3() {
		LOGS.add("\nEntrou na producao 3");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 3");
	}
	
	void producao4() {
		LOGS.add("\nEntrou na producao 4");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		
		getPilha().push("<expressao>");
		getPilha().push("=");
		getPilha().push("id");
		
		empilhado.add("<expressao>");
		empilhado.add("=");
		empilhado.add("id");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 4");
		
	}
	
	void producao5() {
		LOGS.add("\nEntrou na producao 5");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		
		getPilha().push(")");
		getPilha().push("id");
		getPilha().push("(");
		getPilha().push("leia");
		
		empilhado.add(")");
		empilhado.add("id");
		empilhado.add("(");
		empilhado.add("leia");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 5");
	}
	
	void producao6() {
		LOGS.add("\nEntrou na producao 6");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		
		getPilha().push("<expressao>");
		getPilha().push("escreva");
		
		empilhado.add("<expressao>");
		empilhado.add("escreva");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 6");
	}
	
	void producao7() {
		LOGS.add("\nEntrou na producao 7");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		
		getPilha().push("id");
		getPilha().push("var");
		
		empilhado.add("id");
		empilhado.add("var");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 7");
	}
	
	void producao8() {
		LOGS.add("\nEntrou na producao 8");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		getPilha().push("fimlaco");
		getPilha().push("<declaracao_recursiva>");
		getPilha().push("faca");
		getPilha().push("<expressao_logica>");
		getPilha().push("laco");
		
		empilhado.add("fimlaco");
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("faca");
		empilhado.add("<expressao_logica>");
		empilhado.add("laco");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 8");
	}
	
	void producao9() {
		LOGS.add("\nEntrou na producao 9");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		
		getPilha().push("fimse");
		getPilha().push("<senao>");
		getPilha().push("<declaracao_recursiva>");
		getPilha().push("entao");
		getPilha().push("<expressao_logica>");
		getPilha().push("se");
		
		empilhado.add("fimse");
		empilhado.add("<senao>");
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("entao");
		empilhado.add("<expressao_logica>");
		empilhado.add("se");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 9");
	}
	
	void producao10() {
		LOGS.add("\nEntrou na producao 10");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();

		getPilha().push("<declaracao_recursiva>");
		getPilha().push("senao");
		
		
		empilhado.add("<declaracao_recursiva>");
		empilhado.add("senao");

		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 10");
		
	}
	
	void producao11() {
		LOGS.add("\nEntrou na producao 11");
		String desempilhado;
		ArrayList<String> empilhado = new ArrayList<String>();
		desempilhado = getPilha().pop();
		getPilha().push("î");
	
		empilhado.add("î");

		
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 11");
	}
	
	void producao12() {
		LOGS.add("\nEntrou na producao 12");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("<proximo_termo>");
		getPilha().push("<termo>");
		
		empilhado.add("<proximo_termo>");
		empilhado.add("<termo>");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 12");
	}
	
	void producao13() {
		LOGS.add("\nEntrou na producao 13");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("<proximo_termo>");
		getPilha().push("<termo>");
		getPilha().push("<mais_menos>");
		
		empilhado.add("<proximo_termo>");
		empilhado.add("<termo>");
		empilhado.add("<mais_menos>");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 13");
	}
	
	void producao14() {
		LOGS.add("\nEntrou na producao 14");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 14");
	}
	
	void producao15() {
		LOGS.add("\nEntrou na producao 15");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("<proximo_fator>");
		getPilha().push("<fator>");
		

		empilhado.add("<proximo_fator>");
		empilhado.add("<fator>");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 15");
		
	}
	
	void producao16() {
		LOGS.add("\nEntrou na producao 16");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("<proximo_fator>");
		getPilha().push("<fator>");
		getPilha().push("<multi_div>");
		
		empilhado.add("<proximo_fator>");
		empilhado.add("<fator>");
		empilhado.add("<multi_div>");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 16");
	}
	
	void producao17() {
		LOGS.add("\nEntrou na producao 17");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("î");
		
		empilhado.add("î");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 17");
	}
	
	void producao18() {
		LOGS.add("\nEntrou na producao 18");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push(")");
		getPilha().push("<expressao>");
		getPilha().push("(");
		
		empilhado.add(")");
		empilhado.add("<expressao>");
		empilhado.add("(");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 18");
	}
	
	void producao19() {
		LOGS.add("\nEntrou na producao 19");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("id");

		empilhado.add("id");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 19");
	}
	
	void producao20() {
		LOGS.add("\nEntrou na producao 20");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("numero");

		empilhado.add("numero");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 20");
	}
	
	void producao21() {
		LOGS.add("\nEntrou na producao 21");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("+");
		
		empilhado.add("+");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 21");
	}
	
	void producao22() {
		LOGS.add("\nEntrou na producao 22");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("-");
		
		empilhado.add("-");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 22");
	}
	
	void producao23() {
		LOGS.add("\nEntrou na producao 23");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("*");
		
		empilhado.add("*");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 23");
	}
	
	void producao24() {
		LOGS.add("\nEntrou na producao 24");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("/");
		
	
		empilhado.add("/");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 24");
	}
	
	void producao25() {
		LOGS.add("\nEntrou na producao 25");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
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
		
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 25");
	}
	
	void producao26() {
		LOGS.add("\nEntrou na producao 26");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("<");

		empilhado.add("<");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 26");
	}
	
	void producao27() {
		LOGS.add("\nEntrou na producao 27");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("<=");
	
		empilhado.add("<=");
		
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 27");
	}
	
	void producao28() {
		LOGS.add("\nEntrou na producao 28");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push(">=");

		empilhado.add(">=");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 28");
	}
	
	void producao29() {
		LOGS.add("\nEntrou na producao 29");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push(">");
		
		empilhado.add(">");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 29");
	}
	
	void producao30() {
		LOGS.add("\nEntrou na producao 30");
		ArrayList<String> empilhado = new ArrayList<String>();
		String desempilhado = getPilha().pop();
		getPilha().push("==");

		empilhado.add("==");
		logPilha(desempilhado,empilhado);
		LOGS.add("\nSaiu da producao 30");
	}
	
	
	void logPilha(String desempilhado, ArrayList<String> empilhado){
		
		LOGS.add("\nDesempilhado: "+ desempilhado);

		empilhado.forEach(valor -> 
			LOGS.add("\nEmpilhado: " + valor)
		);
		
		
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

	public Boolean getErro() {
		return ErroSintatico;
	}
	
	
}

