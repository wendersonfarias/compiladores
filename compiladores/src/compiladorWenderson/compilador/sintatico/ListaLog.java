package compiladorWenderson.compilador.sintatico;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class ListaLog {
	
	ArrayList<String> logs = new ArrayList<String>();
	
	
	
	public ListaLog(ArrayList<String> listaLogs) {
		this.logs = listaLogs;
	}



	public void listaLog() throws IOException {
		
			
			OutputStream fos = System.out;  
			Writer osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			for (String valor : logs) {
				bw.write(valor);
				bw.newLine();
				bw.flush();
			}
		
			bw.close();

	}
	
}
