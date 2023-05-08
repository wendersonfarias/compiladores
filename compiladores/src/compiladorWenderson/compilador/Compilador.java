package compiladorWenderson.compilador;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import compiladorWenderson.compilador.lexico.AnalisadorLexico;
import compiladorWenderson.compilador.lexico.ListaTokens;
import compiladorWenderson.compilador.sintatico.AnalisadorSintatico;
import compiladorWenderson.compilador.sintatico.ListaLog;

public class Compilador {

	public static void main(String[] args) throws IOException {
		String arquivo = ""  ;
		Boolean EscrevelistaTokens = false;
		Boolean ListarLog = false;
		for (String arg : args) {
			if(arg.equals("-tudo")) {
				ListarLog= true;
				EscrevelistaTokens = true;
			}
			else if (arg.equals("-lt")) {
            	EscrevelistaTokens = true;
            }else if(arg.equals("-ls")) {
            	ListarLog= true;
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
			System.out.println("arquivo não encontrado");
		}
		
		TokensLinhaColunaLog tokensLinhaColunaLog = new TokensLinhaColunaLog();
		AnalisadorLexico an = new AnalisadorLexico();
		
		
		ArrayList<Token> listaToken = an.analisaTokens(arquivo);
		AnalisadorSintatico as = new AnalisadorSintatico(listaToken);
		ArrayList<String> listaLogs = null;
		
		if(listaToken != null) {
			listaLogs = as.analisaSintatico();
		}else {
			System.out.println("Não é possivel usar o analisador sintatico");
		}
		
		
		
		
		
		
		
		if(EscrevelistaTokens) {
			System.out.println("*****************************************Inicio Listagem Token******************************************************************");
			new ListaTokens(listaToken).listaToken();
			System.out.println("*****************************************Fim Listagem Token******************************************************************");
		}
		if(ListarLog && listaToken != null ) {
			new ListaLog(listaLogs).listaLog();
		}
			
		
		
	}

	

}
