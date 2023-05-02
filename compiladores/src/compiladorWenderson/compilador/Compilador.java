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
			System.out.println("arquivo n�o encontrado");
		}
		
		TokensLinhaColunaLog tokensLinhaColunaLog = new TokensLinhaColunaLog();
		AnalisadorLexico an = new AnalisadorLexico();
		AnalisadorSintatico as = new AnalisadorSintatico(tokensLinhaColunaLog);
		
		tokensLinhaColunaLog = an.analisaTokens(arquivo);
		
		/*if(!tokensLinhaColunaLog.getErroLexico()) {
			Boolean erroSintatico = as.analisaSintatico();
		}else {
			System.out.println("N�o � possivel usar o analisador sintatico");
		}*/
		
		
		
		
		
		
		
		if(EscrevelistaTokens) {
			System.out.println("*****************************************Inicio Listagem Token******************************************************************");
			System.out.println("TOKEN { lexema } (Linha - Coluna) \n");
			new ListaTokens(tokensLinhaColunaLog).listaToken();
			System.out.println("*****************************************Fim Listagem Token******************************************************************");
		}
		/*if(ListarLog && !tokensLinhaColunaLog.getErroLexico() ) {
			new ListaLog(tokensLinhaColunaLog).listaLog();
		}*/
			
		
		
	}

	

}
