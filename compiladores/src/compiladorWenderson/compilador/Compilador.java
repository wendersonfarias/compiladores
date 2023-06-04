package compiladorWenderson.compilador;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;

import compiladorWenderson.compilador.codigoFinal.CodigoFinal;
import compiladorWenderson.compilador.codigoIntermediario.CodigoIntermediario;
import compiladorWenderson.compilador.lexico.AnalisadorLexico;
import compiladorWenderson.compilador.lexico.ListaTokens;
import compiladorWenderson.compilador.semantico.AnalisadorSemantico;
import compiladorWenderson.compilador.sintatico.AnalisadorSintatico;

public class Compilador {

	public static void main(String[] args) throws IOException {
		String arquivo = ""  ;
		String arquivoSaida = ""  ;
		Boolean listarLogTokens = false;
		Boolean listarLogSintatico = false;
		Boolean listarLogSemantico = false;
		Boolean listarLogCodigoIntermediario = false;
		Boolean listarLogCodigoFinal = false;
		
		for (String arg : args) {
			if(arg.equals("-tudo")) {
				listarLogSintatico= true;
				listarLogTokens = true;
				listarLogSemantico= true;
				listarLogCodigoIntermediario = true;
				listarLogCodigoFinal = true;
			}
			else if (arg.equals("-lt")) {
            	listarLogTokens = true;
            }else if(arg.equals("-ls")) {
            	listarLogSintatico= true;
            }
            else if(arg.equals("-lse")) {
            	listarLogSemantico= true;
            }
            else if(arg.equals("-ci")) {
            	listarLogCodigoIntermediario = true;
            }
            else if(arg.equals("-cf")) {
            	listarLogCodigoFinal = true;
            }
            
            if(arg.contains("txt")) {
            	 arquivo = arg;
            	
            }else {
            	arquivo = "fonte.txt";
            }  
            
            if(arg.contains("mepa")) {
            arquivoSaida = "mepa/" + arg;
           }else {
        	   arquivoSaida = "mepa/saida_codigo_final.mepa";
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
		
		ArrayList<String> logCodigoFinal= new ArrayList<String>();
		ArrayList<String> codigoFinal = new ArrayList<String>();
		
		Boolean erroSintatico = false;
		Boolean erroSemantico = false;
		
		ArrayList<String> logEscreverConsole = new ArrayList<String>();
	
		if(listarLogTokens) {
			logEscreverConsole.add("*********************************************************************");
			logEscreverConsole.add("Inicio da listagem dos token");
			logEscreverConsole.add("\n");
			logEscreverConsole.addAll(new ListaTokens(listaToken).listaToken());
			logEscreverConsole.add("\n");
			logEscreverConsole.add("Fim da listagem dos token");
		}
		if(!erroLexico) {
			AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(listaToken);
			logSintatico = analisadorSintatico.analisaSintatico(); 
			erroSintatico = analisadorSintatico.getErro();
			
			if(listarLogSintatico) {
				logEscreverConsole.add("\n");
				logEscreverConsole.add("*********************************************************************");
				logEscreverConsole.add("*********************************************************************");
				logEscreverConsole.addAll(logSintatico);
				logEscreverConsole.add("\n");
				logEscreverConsole.add("Fim Log da analise sintatica");
			}
			
			if(!erroSintatico) {
				AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico(listaToken);
				Map<String, Boolean> tabelaSimbolos = analisadorSemantico.analiseSemantica();
				erroSemantico =  analisadorSemantico.getErro();
				logSemantico = analisadorSemantico.getLogSemantico();
				
				if(listarLogSemantico) {
					logEscreverConsole.add("\n");
					logEscreverConsole.add("*********************************************************************");
					logEscreverConsole.add("*********************************************************************");
					logEscreverConsole.add("Inicio Log da analise semantica");
					logEscreverConsole.addAll(logSemantico);
					logEscreverConsole.add("\n");
					logEscreverConsole.add("Fim Log da analise semantica");
				}
				
				if(!erroSemantico) {
					CodigoIntermediario gerarCodigoIntermediario = new CodigoIntermediario( listaToken, tabelaSimbolos);
					codigoIntermediario = gerarCodigoIntermediario.gerarCodigoIntermediario();
					logCodigoIntermediario = gerarCodigoIntermediario.retornaLogIntermediario();
					
					CodigoFinal gerarCodigoFinal = new CodigoFinal(codigoIntermediario);
					codigoFinal = gerarCodigoFinal.gerarCodigoFinal();
					logCodigoFinal = gerarCodigoFinal.retornaLogFinal();
					
					
					if(listarLogCodigoIntermediario) {
						logEscreverConsole.add("*********************************************************************");
						logEscreverConsole.add("*********************************************************************");
						logEscreverConsole.add("Inicio Codigo Intermediario");
						logEscreverConsole.add("\n");
						logEscreverConsole.addAll(codigoIntermediario);
						logEscreverConsole.add("\n");
						logEscreverConsole.add("Fim Codigo Intermediario");
						logEscreverConsole.add("*********************************************************************");
						logEscreverConsole.add("*********************************************************************");
						logEscreverConsole.add("\n");
						logEscreverConsole.add("Inicio Log do Codigo Intermediario");
						logEscreverConsole.add("\n");
						logEscreverConsole.addAll(logCodigoIntermediario);
						logEscreverConsole.add("\n");
						logEscreverConsole.add("Fim Log do Codigo Intermediario");
						
					}
					
					if(listarLogCodigoFinal) {
						
						
						logEscreverConsole.add("*********************************************************************");
						logEscreverConsole.add("*********************************************************************");
						logEscreverConsole.add("Inicio Codigo Final MEPA");
						logEscreverConsole.add("\n");
						logEscreverConsole.addAll(codigoFinal);
						logEscreverConsole.add("\n");
						logEscreverConsole.add("Fim Codigo Final MEPA");
						logEscreverConsole.add("*********************************************************************");
						logEscreverConsole.add("*********************************************************************");
						logEscreverConsole.add("\n");
						logEscreverConsole.add("Inicio Log Codigo Final MEPA");
						logEscreverConsole.addAll(logCodigoFinal);
						logEscreverConsole.add("\n");
						logEscreverConsole.add("Fim Log Codigo Final MEPA");
						
					}
				}
				else {
					System.out.println("Nao possivel usar o gerar codigo intermediario");
				}
			}else {
				System.out.println("Nao possivel usar o gerar codigo semantico");
			}
			
			
		}else {
			System.out.println("Nao possivel usar o analisador sintatico");
		}
		
		OutputStream fos = new FileOutputStream("saida_log_compilador.txt");   
		Writer osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		
		for (int i = 0; i < logEscreverConsole.size(); i++) {
			bw.write(logEscreverConsole.get(i));
			bw.newLine();
			bw.flush();
		}
		
		bw.close();
		
		OutputStream fos1 = new FileOutputStream(arquivoSaida);   
		Writer osw1 = new OutputStreamWriter(fos1);
		BufferedWriter bw1 = new BufferedWriter(osw1);
		
		for (int i = 0; i < codigoFinal.size(); i++) {
			bw1.write(codigoFinal.get(i));
			bw1.newLine();
			bw1.flush();
		}
		
		bw1.close();
		
	}

	

}
