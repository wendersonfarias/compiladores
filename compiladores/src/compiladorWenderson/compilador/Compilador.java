package compiladorWenderson.compilador;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import compiladorWenderson.compilador.codigoIntermediario.CodigoIntermediario;
import compiladorWenderson.compilador.lexico.AnalisadorLexico;
import compiladorWenderson.compilador.lexico.ListaTokens;
import compiladorWenderson.compilador.semantico.AnalisadorSemantico;
import compiladorWenderson.compilador.sintatico.AnalisadorSintatico;
import compiladorWenderson.compilador.sintatico.ListaLog;

public class Compilador {

	public static void main(String[] args) throws IOException {
		String arquivo = ""  ;
		Boolean listarLogTokens = false;
		Boolean listarLogSintatico = false;
		Boolean listarLogSemantico = false;
		for (String arg : args) {
			if(arg.equals("-tudo")) {
				listarLogSintatico= true;
				listarLogTokens = true;
			}
			else if (arg.equals("-lt")) {
            	listarLogTokens = true;
            }else if(arg.equals("-ls")) {
            	listarLogSintatico= true;
            }
            else if(arg.equals("-lse")) {
            	listarLogSemantico= true;
            }
            
            if(arg.contains("txt")) {
            	 arquivo = arg;
            	
            }else {
            	arquivo = "fonte.txt";
            }      
        }

		try {
			FileInputStream fis =new FileInputStream(arquivo);
			fis.close();
			
		}catch (Exception e) {
			System.out.println("arquivo fonte nao encontrado");
		}
		
		AnalisadorLexico analisadorLexico = new AnalisadorLexico();
		ArrayList<Token> listaToken = analisadorLexico.analisaTokens(arquivo);
		Boolean erroLexico = analisadorLexico.getErro();

		ArrayList<String> logSintatico = new ArrayList<String>();
		ArrayList<String> logSemantico= new ArrayList<String>();
		ArrayList<String> logCodigoIntermediario= new ArrayList<String>();
		ArrayList<String> codigoIntermediario = new ArrayList<String>();
		
		Boolean erroSintatico;
		Boolean erroSemantico;
		
		ArrayList<String> logEscreverConsole = new ArrayList<String>();
	
		if(listarLogTokens) {
			logEscreverConsole.addAll(new ListaTokens(listaToken).listaToken());
		}
		if(!erroLexico) {
			AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(listaToken);
			logSintatico = analisadorSintatico.analisaSintatico(); 
			erroSintatico = analisadorSintatico.getErro();
			
			if(listarLogSintatico) {
				logEscreverConsole.addAll(logSintatico);
			}
			
			if(!erroSintatico) {
				AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico(listaToken);
				Map<String, Boolean> tabelaSimbolos = analisadorSemantico.analiseSemantica();
				erroSemantico =  analisadorSemantico.getErro();
				logSemantico = analisadorSemantico.getLogSemantico();
				
				if(listarLogSintatico) {
					logEscreverConsole.addAll(logSintatico);
				}
				
				if(!erroSemantico) {
					CodigoIntermediario gerarCodigoIntermediario = new CodigoIntermediario( listaToken, tabelaSimbolos);
					codigoIntermediario = gerarCodigoIntermediario.gerarCodigoIntermediario();
					logCodigoIntermediario = gerarCodigoIntermediario.retornaLogIntermediario();
				}
			}
			
			
		}else {
			System.out.println("Não possivel usar o analisador sintatico");
		}
		
		
	
			
		
		
	}

	

}
