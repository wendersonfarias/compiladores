package compiladorWenderson.compilador;

public class Token {
	
	String token;
	String lexema;
	String LinhaColuna;
	
	public Token(String token, String lexema, String LinhaColuna) {
		this.token = token;
		this.lexema =lexema;
		this.LinhaColuna = LinhaColuna;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public String getLinhaColuna() {
		return LinhaColuna;
	}

	public void setLinhaColuna(String linhaColuna) {
		LinhaColuna = linhaColuna;
	}
	
	
	
	
	
}
