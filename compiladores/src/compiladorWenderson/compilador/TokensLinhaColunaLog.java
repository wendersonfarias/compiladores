package compiladorWenderson.compilador;

import java.util.ArrayList;

public class TokensLinhaColunaLog {

	private ArrayList<String> token = new ArrayList<String>();
	
	private ArrayList<String> linhaColuna = new ArrayList<String>();
	
	private ArrayList<String> lexemas = new ArrayList<String>();
	
	private ArrayList<String> tabelaSimbolos = new ArrayList<String>();
	
	private ArrayList<String> logSintatico = new ArrayList<String>();

	public ArrayList<String> getTabelaSimbolos() {
		return tabelaSimbolos;
	}

	public void setTabelaSimbolos(ArrayList<String> tabelaSimbolos) {
		this.tabelaSimbolos = tabelaSimbolos;
	}

	private Boolean ErroLexico;
	
	private Boolean ErroSintatico;
	
	private Boolean ErroSemantico;

	public ArrayList<String> getToken() {
		return token;
	}

	public void setToken(ArrayList<String> token) {
		this.token = token;
	}

	public ArrayList<String> getLogSintatico() {
		return logSintatico;
	}

	public void setLogSintatico(ArrayList<String> logSintatico) {
		this.logSintatico = logSintatico;
	}

	public ArrayList<String> getLinhaColuna() {
		return linhaColuna;
	}

	public void setLinhaColuna(ArrayList<String> linhaColuna) {
		this.linhaColuna = linhaColuna;
	}

	public ArrayList<String> getLexemas() {
		return lexemas;
	}

	public void setLexemas(ArrayList<String> lexemas) {
		this.lexemas = lexemas;
	}

	public Boolean getErroLexico() {
		return ErroLexico;
	}

	public void setErroLexico(Boolean erroLexico) {
		ErroLexico = erroLexico;
	}

	public Boolean getErroSintatico() {
		return ErroSintatico;
	}

	public void setErroSintatico(Boolean erroSintatico) {
		ErroSintatico = erroSintatico;
	}

	public Boolean getErroSemantico() {
		return ErroSemantico;
	}

	public void setErroSemantico(Boolean erroSemantico) {
		ErroSemantico = erroSemantico;
	}
	
	
	
	
}
