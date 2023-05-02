package compiladorWenderson.compilador.lexico;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import compiladorWenderson.compilador.TokensLinhaColunaLog;

public class ListaTokens {
	
	TokensLinhaColunaLog tokensLinhaColunaLog ;
	
	
	
	public ListaTokens(TokensLinhaColunaLog tokensLinhaColunaLog) {
		this.tokensLinhaColunaLog = tokensLinhaColunaLog;
	}



	public  void listaToken() throws IOException {
			
			OutputStream fos = System.out;  
			Writer osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
	
			for (String valor : tokensLinhaColunaLog.getToken()) {
				bw.write(valor);
				bw.newLine();
				bw.flush();
			}
			
			bw.close();

	}
	
}
