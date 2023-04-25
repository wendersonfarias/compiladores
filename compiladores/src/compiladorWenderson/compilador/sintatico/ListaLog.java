package compiladorWenderson.compilador.sintatico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import compiladorWenderson.compilador.TokensLinhaColunaLog;

public class ListaLog {
	
	TokensLinhaColunaLog tokensLinhaColunaLog  = new  TokensLinhaColunaLog();
	
	
	
	public ListaLog(TokensLinhaColunaLog tokensLinhaColunaLog) {
		this.tokensLinhaColunaLog = tokensLinhaColunaLog;
	}



	public void listaLog() throws IOException {
		
			
			OutputStream fos = System.out;  
			Writer osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			for (String valor : tokensLinhaColunaLog.getLogSintatico()) {
				bw.write(valor);
				bw.newLine();
				bw.flush();
			}
		
			bw.close();

	}
	
}
