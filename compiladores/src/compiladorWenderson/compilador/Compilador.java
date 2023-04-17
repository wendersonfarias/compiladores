package compiladorWenderson.compilador;

import java.io.FileInputStream;
import java.io.IOException;

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
		
		AnalisadorLexico an = new AnalisadorLexico();
		AnalisadorSintatico as = new AnalisadorSintatico();
		Boolean erroLexico = an.analisaTokens(arquivo);
		
		if(!erroLexico) {
			Boolean erroSintatico = as.analisaSintatico();
		}else {
			System.out.println("Não é possivel usar o analisador sintatico");
		}
		
		
		
		
		
		
		
		if(EscrevelistaTokens) {
			System.out.println("TOKEN { lexema } (Linha - Coluna) \n");
			ListaTokens.listaToken();
		}
		if(ListarLog && !erroLexico ) {
			ListaLog.listaLog();
		}
			
		
		
	}

	

}
