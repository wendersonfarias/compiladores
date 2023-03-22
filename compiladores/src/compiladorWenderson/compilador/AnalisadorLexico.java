package compiladorWenderson.compilador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class AnalisadorLexico {
	
	Integer linha = 0;
	Integer coluna = 0;
	
	
	public void analisaTokens(String nomeArquivo) throws IOException {
		InputStream fis =new FileInputStream(nomeArquivo);  
		Reader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);

		OutputStream fos = System.out;  
		Writer osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		
		String linha ;
		do {
			linha = br.readLine();
			if(linha != null) {
				automato(linha);
			} 
			
		}
		while( linha != null );
		
		/*while( !(linha == null || linha.isEmpty()) ) {
			bw.write(linha);
			bw.newLine();
			bw.flush();
			linha = br.readLine();
		}*/
	
		br.close();
		bw.close();
	}

	private void automato(String linha) {
			setLinha(getLinha()+1);
			setColuna(getColuna()+1);
			String copia = linha;
			Integer tamanhoLinha = linha.length();
			
			for(Integer contador = 0; contador <tamanhoLinha; contador++) {
				System.out.println(String.valueOf(contador));
			}
		
	}

	public Integer getLinha() {
		return linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
	}

	public Integer getColuna() {
		return coluna;
	}

	public void setColuna(Integer coluna) {
		this.coluna = coluna;
	}
	
	
}
