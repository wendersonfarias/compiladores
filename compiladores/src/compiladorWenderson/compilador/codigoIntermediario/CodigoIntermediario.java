package compiladorWenderson.compilador.codigoIntermediario;

import java.util.ArrayList;
import java.util.Map;

import compiladorWenderson.compilador.Token;
import compiladorWenderson.compilador.util.Infixa_posfixa;

public class CodigoIntermediario {

	private ArrayList<Token> 		listaToken;
	private ArrayList<String> 		logIntermediario = new ArrayList<String>();
	private ArrayList<String> 		registarLog = new ArrayList<String>();
	private String					comando;
	private ArrayList<String>		codigoIntermediario = new ArrayList<String>();
	private Map<String, Boolean> 	tabelaSimbolos;
	
	public CodigoIntermediario(ArrayList<Token> listaToken, Map<String, Boolean> tabela) {
		this.listaToken = listaToken;
		this.tabelaSimbolos = tabela;
	}
	
	void gerarCodigoIntermediario(){
		for(String variavel : tabelaSimbolos.keySet()) {
			codigoIntermediario.add("_Var "+ variavel);
			registarLog.add("Declaracao da variavel "+ variavel);
		}
		Integer i = 0;
		
		while(i < listaToken.size()) {
			comando = "";
			if(listaToken.get(i).getToken().equals("leia")) {
				codigoIntermediario.add("leia " + listaToken.get(i+2).getLexema());
				registarLog.add("Comando de leitura da variavel  "+ listaToken.get(i+2).getLexema());
				i += 5 ; //pula parenteses e pontovirgula
			}else if(listaToken.get(i).getToken().equals("=")) {
				String variavel = listaToken.get(i-1).getLexema(); //pega o nome da variavel a ser atribuida
				i += 1;
				
				while(!listaToken.get(i).getToken().equals(";")) {
					comando += " " + listaToken.get(i).getLexema();
					++i;
				}
				
				codigoIntermediario.add(variavel + " = " + Infixa_posfixa.infixa_posfixa(comando) );
				registarLog.add("Expressao atribuida para variavel " + variavel);
				
			}else if(listaToken.get(i).getToken().equals("escreva")) {
				comando = "escreva ";
				i += 1;
				
				while(!listaToken.get(i).getToken().equals(";")) {
					comando += " " + listaToken.get(i).getLexema();
					++i;
				}
				
				codigoIntermediario.add(Infixa_posfixa.infixa_posfixa(comando));
				registarLog.add("Comando para escrever em tela");
			}else if(listaToken.get(i).getToken().equals("laco")) {
				i = i + 1 ;
				comando = "enquanto ";
				
				while(!listaToken.get(i).getToken().equals(";")) {
					comando += " " +listaToken.get(i).getLexema();
					++i;
				}
				codigoIntermediario.add(comando);
				registarLog.add("Reconhecido comando enquanto");
			}else if(listaToken.get(i).getToken().equals("fimlaco")) {
				comando = "fim_enquanto";
				i = i+ 1 ;
				codigoIntermediario.add(comando);
				registarLog.add("Reconhecido comando fim_enquanto");
			}else if(listaToken.get(i).getToken().equals("se")) {
				 comando = "se";
				 i = i + 1;
				 while(!listaToken.get(i).getToken().equals("senao")) {
						comando += " " +listaToken.get(i).getLexema();
						++i;
				}
			}
		}
		
	}
	
	
}
