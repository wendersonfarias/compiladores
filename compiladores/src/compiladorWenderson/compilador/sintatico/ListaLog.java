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

public class ListaLog {
	
	public static void listaLog() throws IOException {
			InputStream fis =  new FileInputStream("log_sintatico.txt");
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			OutputStream fos = System.out;  
			Writer osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			String linha =  br.readLine();
			
			while( !(linha == null ) ) {
				bw.write(linha);
				bw.newLine();
				bw.flush();
				linha = br.readLine();
			}
			
			br.close();
			bw.close();

	}
	
}
