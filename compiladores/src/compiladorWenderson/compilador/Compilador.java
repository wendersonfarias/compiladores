package compiladorWenderson.compilador;

import java.io.FileInputStream;
import java.io.IOException;

import compiladorWenderson.compilador.lexico.AnalisadorLexico;

public class Compilador {

	public static void main(String[] args) throws IOException {
		String arquivo = ""  ;
		Boolean EscrevelistaTokens = false;
		for (String arg : args) {
            if (arg.equals("-lt")) {
            	EscrevelistaTokens = true;
            }
            if(arg.contains("txt")) {
            	 arquivo = arg;
            	
            }       
        }

		try {
			FileInputStream fis =new FileInputStream(arquivo);
			fis.close();
			
		}catch (Exception e) {
			System.out.println("arquivo n�o encontrado");
		}
		
		AnalisadorLexico an = new AnalisadorLexico();
		an.analisaTokens(arquivo);
		
		
		
		
		if(EscrevelistaTokens) {
			ListaTokens.listaToken();
		}
			
		
		
	}

	

}
