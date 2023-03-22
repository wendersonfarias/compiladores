package compiladorWenderson.compilador;

import java.io.FileInputStream;
import java.io.IOException;

public class Compilador {

	public static void main(String[] args) throws IOException {
		String arquivo = ""  ;
		for (String arg : args) {
            if (arg.equals("-lt")) {
            	//listaTokens.listaToken();
            }
            if(arg.contains("txt")) {
            	 arquivo = arg;
            	
            }       
        }

		try {
			FileInputStream fis =new FileInputStream(arquivo);
			fis.close();
			
		}catch (Exception e) {
			System.out.println("arquivo não encontrado");
		}
		
		AnalisadorLexico an = new AnalisadorLexico();
		an.analisaTokens(arquivo);
		
		
		
		
	
			
		
		
	}

	

}
