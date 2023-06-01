package compiladorWenderson.compilador.codigoIntermediario;

import java.util.ArrayList;
import java.util.Map;

import compiladorWenderson.compilador.Token;
import compiladorWenderson.compilador.util.Infix_posfix2;

public class CodigoIntermediario {

	private ArrayList<Token> 		listaToken;
	//private ArrayList<String> 		logIntermediario = new ArrayList<String>();
	private ArrayList<String> 		registarLog = new ArrayList<String>();
	private String					comando;
	private ArrayList<String>		codigoIntermediario = new ArrayList<String>();
	private Map<String, Boolean> 	tabelaSimbolos;
	
	public CodigoIntermediario(ArrayList<Token> listaToken, Map<String, Boolean> tabela) {
		this.listaToken = listaToken;
		this.tabelaSimbolos = tabela;
	}
	
	public ArrayList<String>  gerarCodigoIntermediario(){
		codigoIntermediario.add("Inicio do codigo intermediario");
	  Infix_posfix2 infix_posfix = new Infix_posfix2();
		for(String variavel : tabelaSimbolos.keySet()) {
			codigoIntermediario.add("_Var "+ variavel);
			registarLog.add("Declaracao da variavel "+ variavel);
		}
		Integer i = 0;
		
		//converte as producoes  
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
					comando += "" + listaToken.get(i).getLexema();
					++i;
				}
				
				
				codigoIntermediario.add(variavel + " = " + infix_posfix.InFixToPosFix(comando) );
				registarLog.add("Expressao atribuida para variavel " + variavel);
				
			}else if(listaToken.get(i).getToken().equals("escreva")) {
				comando = "escreva ";
				String expressao = "";
				i += 1;
				
				while(!listaToken.get(i).getToken().equals(";")) {
					expressao += "" + listaToken.get(i).getLexema();
					++i;
				}
				
				codigoIntermediario.add(comando + infix_posfix.InFixToPosFix(expressao));
				registarLog.add("Comando para escrever em tela");
			}else if(listaToken.get(i).getToken().equals("laco")) {
				comando = "enquanto ";
				i = i + 2;
				String expressao = "";
				String expressao2 = "" ;
				
				while(!listaToken.get(i).getToken().equals("faca")) {
					if(listaToken.get(i).getToken().equals("<=") || listaToken.get(i).getToken().equals("<") ||
					 		listaToken.get(i).getToken().equals(">=") || listaToken.get(i).getToken().equals(">") || 
					 		listaToken.get(i).getToken().equals("==") ) {
					 		expressao = infix_posfix.InFixToPosFix(expressao2) + listaToken.get(i).getLexema()  ;
					 		expressao2 = expressao;
					 		
					 	}else {
					 		
					 		expressao2 += "" +listaToken.get(i).getLexema();
					 	}

						++i;
				}
				String[] expressao3 = expressao2.split(")");
				comando = comando  + expressao3[0] + "";
				codigoIntermediario.add(comando);
				registarLog.add("Reconhecido comando repecitao (enquanto)");
			}else if(listaToken.get(i).getToken().equals("fimlaco")) {
				comando = "fim_enquanto ";
				i = i+ 1 ;
				codigoIntermediario.add(comando);
				registarLog.add("Reconhecido comando fim_enquanto");
			}else if(listaToken.get(i).getToken().equals("se")) {
				 comando = "se ";
				 i = i + 2;
				 String expressao = "";
				 String expressao2 = "" ;
				 while(!listaToken.get(i).getToken().equals("entao")) {
					 	if(listaToken.get(i).getToken().equals("<=") || listaToken.get(i).getToken().equals("<") ||
					 		listaToken.get(i).getToken().equals(">=") || listaToken.get(i).getToken().equals(">") || 
					 		listaToken.get(i).getToken().equals("==") ) {
					 		expressao = infix_posfix.InFixToPosFix(expressao2) + listaToken.get(i).getLexema()  ;
					 		expressao2 = expressao;
					 		
					 	}else {
					 		
					 		expressao2 += "" +listaToken.get(i).getLexema();
					 	}

						++i;
				 }
				 String[] expressao3 = expressao2.split(")");
				 comando = comando  + expressao3[0] ;
				 codigoIntermediario.add(comando);
				 registarLog.add("Reconhecido comando condicional (se entao) ");
			}else if(listaToken.get(i).getToken().equals("senao")) {
				 comando = "senao ";
				 i = i + 1;
				 codigoIntermediario.add(comando);
				 registarLog.add("Reconhecido complemento do comando condiconal (senao) ");
			}
			else if(listaToken.get(i).getToken().equals("fimse")) {
				 comando = "fimse ";
				 i = i + 1;
				 codigoIntermediario.add(comando);
				 registarLog.add("Reconhecido termino do comando condiconal (fimse) ");
			}else {
				i = i + 1 ;
			}
		}
		
		return codigoIntermediario;
	}
	
	public ArrayList<String>  retornaLogIntermediario(){
		return registarLog;
	}
	
}
