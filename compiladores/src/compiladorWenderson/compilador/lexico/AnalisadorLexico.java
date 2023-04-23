package compiladorWenderson.compilador.lexico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class AnalisadorLexico {
	
	Integer cabeca ;
	Integer numeroLinha ;
	String lexema;
	String fita ;
	ArrayList<String> linhasTokens = new ArrayList<String>();
	ArrayList<String> tabelaTokens = new ArrayList<String>();
	ArrayList<String> tabelaLinhaColuna = new ArrayList<String>();
	
	Boolean temErro = false;
	Integer tabulacao;
	
	
	
	

	public AnalisadorLexico() {
		 cabeca      =0 ;
		 numeroLinha =0;
		 lexema      ="";
		 fita        ="";
	}

	

	public Boolean analisaTokens(String nomeArquivo) throws IOException {
		InputStream fis =new FileInputStream(nomeArquivo);  
		Reader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		

		OutputStream fos = new FileOutputStream("tabela_simbolo.txt");   
		Writer osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		
		
		OutputStream fos11 = new FileOutputStream("tabela_tokens.txt");   
		Writer osw11 = new OutputStreamWriter(fos11);
		BufferedWriter bw11 = new BufferedWriter(osw11);
		
		OutputStream fos13 = new FileOutputStream("tabela_linha_coluna.txt");   
		Writer osw13 = new OutputStreamWriter(fos13);
		BufferedWriter bw13 = new BufferedWriter(osw13);
		
		
		Character pulaLinha = Character.valueOf('\n');
		
		String linha ;
		do {
			linha = br.readLine();
			if(linha != null) {
				linha = linha +pulaLinha.toString();
				this.adiconaLinha();
				this.setFita(linha);
				this.setCabeca(0);
				tabulacao = 0;
				this.automato();
			} 
			
		}
		while( linha != null );
		
		
		
		for (int i = 0; i < linhasTokens.size(); i++) {
			bw.write(linhasTokens.get(i));
			bw.newLine();
			bw.flush();
		}
		
		
		
		
		
		for (int i = 0; i < tabelaTokens.size(); i++) {
			bw11.write(tabelaTokens.get(i));
			bw11.newLine();
			bw11.flush();
		}
		
		for (int i = 0; i < tabelaLinhaColuna.size(); i++) {
			bw13.write(tabelaLinhaColuna.get(i));
			bw13.newLine();
			bw13.flush();
		}
		
			
		
		/*while( !(linha == null || linha.isEmpty()) ) {
			bw.write(linha);
			bw.newLine();
			bw.flush();
			linha = br.readLine();
		}*/
	
		
		
		bw11.close();
		bw13.close();
		bw.close();
		br.close();
		
		return temErro;
		
	}

	private void automato() {
		estadoQ0();
		
	}

	private void estadoQ0() {
		Character pulaLinha = Character.valueOf('\n');
		Character charBarrat = Character.valueOf('\t');
		String barraT = charBarrat.toString();
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		
		if("i".equals(letra)) {
			this.estadoQ1();
		}
		else if("f".equals(letra)) {
			this.estadoQ8();
		}
		else if("s".equals(letra)) {
			this.estadoQ13();
		}
		else if("e".equals(letra)) {
			this.estadoQ15();
		}
		else if("l".equals(letra)) {
			this.estadoQ34();
		}
		else if("v".equals(letra)) {
			this.estadoQ52();
		}
		else if(letra.equals("=") ){ 
			this.estadoQ64();
		}
		else if(letra.equals(">")) {
			this.estadoQ56();
		}
		else if(letra.equals("<")) {
			this.estadoQ57();
		}
		else if(letra.equals("/")) {
			this.estadoQ60();
		}
		else if(letra.equals("*")) {
			this.estadoQ61();
		}
		else if(letra.equals("+")) {
			this.estadoQ62();
		}
		else if(letra.equals("-")) {
			this.estadoQ63();
		}
		else if(letra.equals(";")) {
			this.estadoQ65();
		}
		else if(letra.equals(")")) {
			this.estadoQ66();
		}
		else if(letra.equals("(")) {
			this.estadoQ67();
		
		}else if( Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.matches("\\d+")) {
			this.estadoQ71();
		}else if(letra.equals("\t")) {
			tabulacao += 3;
			this.setLexema("");
			estadoQ0();
		}else if(letra.equals(" ")) {
			this.setLexema("");
			estadoQ0();
		
		}else if(letra.equals(fimLinha) ){
			this.setLexema("");
			return;
		
		}
		else { 
			imprimeErro(this.getNumeroLinha(), cabeca, letra);
			this.setLexema("");
			this.estadoQ0();
		}
	}



	private void estadoQ71() {
		String letra = obterCharacter();
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String token = "numero";
		
		if(letra.matches("\\d+")) {  //verifica se é um numero
			this.estadoQ71();
		}else if(letra.equals(fimLinha)) {
			if(this.getLexema().length() > 0) {
				this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			}
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca());
			this.setLexema("");
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca());
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
			
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
		}
		
	}



	private void estadoQ12() {
		String letra = obterCharacter();
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String token = "id";
		
		if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}
		else if(letra.equals(fimLinha)) {
			if(this.getLexema().length() > 0) {
				this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			}
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
			
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
		}
		
		
	}



	private void estadoQ66() {
		//Reconhece comando  [ ) ]
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = ")";
		
		 if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		 	 
		
	}



	private void estadoQ67() {
		//Reconhece comando  [ ( ]
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "(";
		
		 if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		 	 
		
	}



	private void estadoQ65() {
		//Reconhece comando  [ ; ]
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = ";";
		
		 if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		 	 
		
	}



	private void estadoQ64() {
		//Reconhece comando  - = ou tenta reconhecer == -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "=";
		
		if(letra.equals("=")) {
			estadoQ70();
		
		}
		else if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		
		
		
		
	}



	private void estadoQ68() {
		//Reconhece comando  - >= -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = ">=";
		
		if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		
		
	}



	private void estadoQ70() {
		//Reconhece comando  - == -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "==";
		
		if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		
	}



	private void estadoQ63() {
		//Reconhece comando  [ - ]
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "-";
		
		 if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		  
		
	}



	private void estadoQ62() {
		//Reconhece comando  - + -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token  = "+";
		
		 if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		 		 
		
	}



	private void estadoQ61() {
		//Reconhece comando  - * -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "*";
		
		 if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		  
		
	}



	private void estadoQ57() {
		//Reconhece comando  - [ < ou tenta reconhecer <= }
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "<";
		
		if(letra.equals("=")) {
			estadoQ69();
		
		}
		else if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca());
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		
		 
		
	}



	private void estadoQ69() {
		//Reconhece comando  [ <=]
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "<=";
		
		if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		 
		
	}



	private void estadoQ60() {
		//Reconhece comando  - / -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "/";
		
		 if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();

			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		 
		
	}



	private void estadoQ56() {
		
		//Reconhece comando  - > ou tenta reconhecer >= -
				Character pulaLinha = Character.valueOf('\n');
				String fimLinha = pulaLinha.toString();
				String letra = obterCharacter();
				String token = ">";
				
				if(letra.equals("=")) {
					estadoQ68();
				
				} 
				else if(letra.equals(fimLinha)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();

					//termina a linha e reconhece o token
				}else if(letra.equals(" ")) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
				}else if(verificaCaractereEspecial(letra) || letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					setCabeca(getCabeca() - 1); 
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
					//volta a cabeca porque o proximo poderá ser um outro token
				}
				
				else { 
					imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
				}	
	}



	



	private void estadoQ52() {
		String letra = obterCharacter();
		if(letra.equals("a")) {
			estadoQ53();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ53() {
		String letra = obterCharacter();
		if(letra.equals("r")) {
			estadoQ54();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ54() {
		//Reconhece comando  - var -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "var";
		
		if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		
	}



	private void estadoQ34() {
		String letra = obterCharacter();
		if(letra.equals("e")) {
			estadoQ35();
		
		}else if(letra.equals("a")) {
				estadoQ45();
		
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ35() {
		String letra = obterCharacter();
		if(letra.equals("i")) {
			estadoQ36();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ36() {
		String letra = obterCharacter();
		if(letra.equals("a")) {
			estadoQ37();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ37() {
		//Reconhece comando  # Leia -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "leia";
		
		if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
		
	}



	private void estadoQ15() {
		String letra = obterCharacter();
		if(letra.equals("l")) {
			estadoQ16();
		}else if(letra.equals("s")){
			estadoQ24();
		}else if(letra.equals("n")){
			estadoQ30();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ30() {
		String letra = obterCharacter();
		if(letra.equals("t")) {
			estadoQ31();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ31() {
		String letra = obterCharacter();
		if(letra.equals("a")) {
			estadoQ32();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ32() {
		String letra = obterCharacter();
		if(letra.equals("o")) {
			estadoQ33();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
	}



	private void estadoQ33() {
		//Reconhece comando  - entao -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "entao";
		
		if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		 
		
	}



	private void estadoQ24() {
		String letra = obterCharacter();
		if(letra.equals("c")) {
			estadoQ25();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ25() {
		String letra = obterCharacter();
		if(letra.equals("r")) {
			estadoQ26();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ26() {
		String letra = obterCharacter();
		if(letra.equals("e")) {
			estadoQ27();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ27() {
		String letra = obterCharacter();
		if(letra.equals("v")) {
			estadoQ28();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ28() {
		String letra = obterCharacter();
		if(letra.equals("a")) {
			estadoQ29();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ29() {
		//Reconhece comando  - escreva -
				Character pulaLinha = Character.valueOf('\n');
				String fimLinha = pulaLinha.toString();
				String letra = obterCharacter();
				String token = "escreva";
				
				if(letra.equals(fimLinha)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					//termina a linha e reconhece o token
				}else if(letra.equals(" ")) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
				}else if(verificaCaractereEspecial(letra)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					setCabeca(getCabeca() - 1); 
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
					//volta a cabeca porque o proximo poderá ser um outro token
				}
				else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
					this.estadoQ12();
				}
				else { 
					imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
				}
		
	}



	private void estadoQ16() {
		String letra = obterCharacter();
		if(letra.equals("a")) {
			estadoQ17();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ17() {
		String letra = obterCharacter();
		if(letra.equals("o")) {
			estadoQ18();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ18() {
		//Reconhece comando  - senao -
				Character pulaLinha = Character.valueOf('\n');
				String fimLinha = pulaLinha.toString();
				String letra = obterCharacter();
				String token = "senao";
				
				if(letra.equals(fimLinha)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					//termina a linha e reconhece o token
				}else if(letra.equals(" ")) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
				}else if(verificaCaractereEspecial(letra)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					setCabeca(getCabeca() - 1); 
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
					//volta a cabeca porque o proximo poderá ser um outro token
				}
				else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
					this.estadoQ12();
				}
				else { 
					imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
				}
		
	}



	private void estadoQ13() {
		String letra = obterCharacter();
		if(letra.equals("e")) {
			estadoQ14();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ14() {
		//Reconhece comando  - se -
				Character pulaLinha = Character.valueOf('\n');
				String fimLinha = pulaLinha.toString();
				String letra = obterCharacter();
				String token = "se";
				
				if(letra.equals("n")) {
					estadoQ16();
				}
				else if(letra.equals(fimLinha)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1));
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					//termina a linha e reconhece o token
				}else if(letra.equals(" ")) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
				}else if(verificaCaractereEspecial(letra)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					setCabeca(getCabeca() - 1); 
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
					//volta a cabeca porque o proximo poderá ser um outro token
				}
				else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
					this.estadoQ12();
				}
				else { 
					imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
				}
		
	}



	private void estadoQ8() {
		String letra = obterCharacter();
		if(letra.equals("i")) {
			estadoQ9();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ9() {
		String letra = obterCharacter();
		if(letra.equals("m")) {
			estadoQ10();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ10() {
		String letra = obterCharacter();
		if(letra.equals("p")) {
			estadoQ11();
		}else if(letra.equals("s")){
			estadoQ22();
		}else if(letra.equals("l")){
			estadoQ48();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ48() {
		String letra = obterCharacter();
		if(letra.equals("a")) {
			estadoQ49();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ49() {
		String letra = obterCharacter();
		if(letra.equals("c")) {
			estadoQ50();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ50() {
		String letra = obterCharacter();
		if(letra.equals("o")) {
			estadoQ51();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ51() {
		//Reconhece comando  - fimlaco-
				Character pulaLinha = Character.valueOf('\n');
				String fimLinha = pulaLinha.toString();
				String letra = obterCharacter();
				String token = "fimlaco";
				
				if(letra.equals(fimLinha)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					//termina a linha e reconhece o token
				}else if(letra.equals(" ")) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
				}else if(verificaCaractereEspecial(letra)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					setCabeca(getCabeca() - 1); 
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
					//volta a cabeca porque o proximo poderá ser um outro token
				}
				else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
					this.estadoQ12();
				}
				else { 
					imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
				}
		
	}



	private void estadoQ22() {
		String letra = obterCharacter();
		if(letra.equals("e")) {
			estadoQ23();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ23() {
		//Reconhece comando  - fimse -
				Character pulaLinha = Character.valueOf('\n');
				String fimLinha = pulaLinha.toString();
				String letra = obterCharacter();
				String token = "fimse";
				
				if(letra.equals(fimLinha)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					//termina a linha e reconhece o token
				}else if(letra.equals(" ")) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
				}else if(verificaCaractereEspecial(letra)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					setCabeca(getCabeca() - 1); 
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
					//volta a cabeca porque o proximo poderá ser um outro token
				}
				else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
					this.estadoQ12();
				}
				else { 
					imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
				}
		
	}



	private void estadoQ11() {
		//Reconhece comando  - fimp -
				Character pulaLinha = Character.valueOf('\n');
				String fimLinha = pulaLinha.toString();
				String letra = obterCharacter();
				String token = "fimprograma";
				
				if(letra.equals(fimLinha)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					//termina a linha e reconhece o token
				}else if(letra.equals(" ")) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
				}else if(verificaCaractereEspecial(letra)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					setCabeca(getCabeca() - 1); 
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
					//volta a cabeca porque o proximo poderá ser um outro token
				}
				else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
					this.estadoQ12();
				}
				else { 
					imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
				}
		
	}



	private void estadoQ1() {
		String letra = obterCharacter();
		if(letra.equals("n")) {
			estadoQ2();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ2() {
		String letra = obterCharacter();
		if(letra.equals("i")) {
			estadoQ3();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ3() {
		String letra = obterCharacter();
		if(letra.equals("c")) {
			estadoQ4();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ4() {
		String letra = obterCharacter();
		if(letra.equals("i")) {
			estadoQ5();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ5() {
		String letra = obterCharacter();
		if(letra.equals("o")) {
			estadoQ6();
		}
		else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	



	private void estadoQ45() {
		String letra = obterCharacter();
		if(letra.equals("c")) {
			estadoQ46();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ46() {
		String letra = obterCharacter();
		if(letra.equals("o")) {
			estadoQ47();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ47() {
		//Reconhece comando  # Laco -
				Character pulaLinha = Character.valueOf('\n');
				String fimLinha = pulaLinha.toString();
				String letra = obterCharacter();
				String token = "laco";
				
				if(letra.equals(fimLinha)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					//termina a linha e reconhece o token
				}else if(letra.equals(" ")) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
				}else if(verificaCaractereEspecial(letra)) {
					this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
					salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
					this.setLexema("");
					setCabeca(getCabeca() - 1); 
					this.estadoQ0();
					//reconhece um novo token e continua a reconhcer outro token
					//volta a cabeca porque o proximo poderá ser um outro token
				}
				else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
					this.estadoQ12();
				}
				else { 
					imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
				}
		
	}



	private void estadoQ6() {
		
		String letra = obterCharacter();
		if(letra.equals("p")) {
			estadoQ7();
		}else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			setCabeca(getCabeca()-1); 
			estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		
	}



	private void estadoQ7() {
		//Reconhece comando  - iniciop -
		Character pulaLinha = Character.valueOf('\n');
		String fimLinha = pulaLinha.toString();
		String letra = obterCharacter();
		String token = "inicioprograma";
		
		if(letra.equals(fimLinha)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			//termina a linha e reconhece o token
		}else if(letra.equals(" ")) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
		}else if(verificaCaractereEspecial(letra)) {
			this.setLexema(this.getLexema().substring(0, this.getLexema().length() - 1)); 
			salvaToken(token, getLexema(), getNumeroLinha(), getCabeca()); 
			this.setLexema("");
			setCabeca(getCabeca() - 1); 
			this.estadoQ0();
			//reconhece um novo token e continua a reconhcer outro token
			//volta a cabeca porque o proximo poderá ser um outro token
		}
		else if(letra.matches("\\d+") || Character.isLetter(letra.charAt(0))) {
			this.estadoQ12();
		}
		else { 
			imprimeErro(this.getNumeroLinha(),getCabeca(),letra );
		}
		

		
	}
	




	private String obterCharacter() {
		String letra = "";
		Character caractere;
		Character pulaLinha = Character.valueOf('\n');
		if(getCabeca() < getFita().length() ) {
			caractere = Character.valueOf(getFita().charAt(getCabeca()));
			letra = caractere.toString();
			this.avancaCabeca();
			letra = letra.toLowerCase();
			if(!letra.contains("\n") || !letra.equals(" ")) {
				setLexema(getLexema() + letra);
				return letra;
			}
			
			
		}
		return pulaLinha.toString();
		
	}
	

	public Integer getCabeca() {
		return cabeca;
	}



	public void avancaCabeca() {
		this.cabeca++;
	}


	

	public Integer getNumeroLinha() {
		return numeroLinha;
	}



	public void adiconaLinha() {
		this.numeroLinha++;
	}



	public String getLexema() {
		return lexema;
	}



	public void setLexema(String lexema) {
		this.lexema = lexema;
	}



	public String getFita() {
		return fita;
	}



	public void setFita(String fita) {
		this.fita = fita;
	}



	public void setCabeca(Integer cabeca) {
		this.cabeca = cabeca;
	}



	public void setNumeroLinha(Integer numeroLinha) {
		this.numeroLinha = numeroLinha;
	}



	public Boolean verificaCaractereEspecial(String caractere) {
		Character c = Character.valueOf(caractere.charAt(0));
		if (c == '=' || c == '+' || c == '-' || c == '*' || c == '/' || c == '>' 
								|| c == '<' || c == '(' || c == ')' || c == ';') {
		
			return true;
		}	
		return false;
	}
	
	public void imprimeErro(Integer numeroLinha,Integer coluna,String caractere ) {
		System.out.println("Erro Léxico ( Linha: " +  numeroLinha + " - Coluna: " 
				+ ((coluna-1) - lexema.length()+tabulacao) +"): Caracter { "  + caractere +  " } Inesperado \n ");
		temErro = true;
	}

	
	public void salvaToken(String token, String lexema, Integer numeroLinha, Integer coluna) {
		String newToken = token + " { " + lexema + " } (L"+ numeroLinha + " - C" + ((coluna) - lexema.length()+tabulacao) + " )";
		linhasTokens.add(newToken);
		tabelaTokens.add(token);
		tabelaLinhaColuna.add("(Linha: "+ numeroLinha + " - Coluna: " + ((coluna) - lexema.length()+tabulacao) + " )");		
	}
}
