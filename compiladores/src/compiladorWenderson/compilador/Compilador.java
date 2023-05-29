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
			System.out.println("arquivo fonte nï¿½o encontrado");
		}
		
		AnalisadorLexico analisadorLexico = new AnalisadorLexico();
		ArrayList<Token> listaToken = analisadorLexico.analisaTokens(arquivo);
		Boolean erroLexico = analisadorLexico.getErro();
		//ArrayList<String> listaLogSintatico;
	
		
		if(!erroLexico) {
			AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(listaToken);
			ArrayList<String> listaLogSintatico= analisadorSintatico.analisaSintatico(); 
			Boolean erroSintatico = analisadorSintatico.getErro();
			
			AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico(listaToken);
			Map<String, Boolean> tabelaSimbolos = analisadorSemantico.analiseSemantica();
			//CodigoIntermediario codigoIntermediario = new CodigoIntermediario();
		}else {
			System.out.println("Não possivel usar o analisador sintatico");
		}
		
		
		
		
		
		
		
		if(listarLogTokens) {
			System.out.println("*****************************************Inicio Listagem Token******************************************************************");
			new ListaTokens(listaToken).listaToken();
			System.out.println("*****************************************Fim Listagem Token******************************************************************");
		}
		/*if(listarLogSintatico) {
			new ListaLog(listaLogs).listaLog();
		}*/
			
		
		
	}

	

}
