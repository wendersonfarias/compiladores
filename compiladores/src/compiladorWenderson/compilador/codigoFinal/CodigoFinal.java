/**
 * 
 */
package compiladorWenderson.compilador.codigoFinal;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author wende
 *
 */
public class CodigoFinal {

	private ArrayList<String> codigoIntermediario = new ArrayList<String>();
	private ArrayList<String> codigoFinal = new ArrayList<String>();
	private ArrayList<String> logCodigoFinal = new ArrayList<String>();
	private Integer contador = 0;
	private Stack<String> Rotulo= new Stack<String>();
	private String rotulo = "";
	private ArrayList<String> tabelaVariaveis = new ArrayList<String>();
	String identacao = "      ";

	public CodigoFinal(ArrayList<String> codigoIntermediario) {
		this.codigoIntermediario = codigoIntermediario;
		
	}

	public ArrayList<String> gerarCodigoFinal() {
		
		for (String elemento : codigoIntermediario) {

            if (elemento.substring(0, 4).equals("_Var")) {
                tabelaVariaveis.add(elemento.substring(5, elemento.length()));
            }
        }

		gerarCodigoFinal("MAIN "+ identacao + "inicioprograma");
		alocarMemoriaVariavel();

		for (String comando : codigoIntermediario) {
			String[] linha = comando.split(" ");
            if (!comando.substring(0, 4).equals("_Var")) {
                if(linha[0].equals("leia")){
    				Integer posicaoDaTabela = tabelaVariaveis.indexOf(linha[1]);
					logCodigoFinal.add("Geracao comando READ: " + linha);
					gerarCodigoFinal("READ "+ identacao + "leia");
					gerarCodigoFinal("STVL 0," + posicaoDaTabela 
					+ identacao +"carregue valor no endereco de memoria 0," + posicaoDaTabela);

				}else if(linha[0].equals("escreva")){
					logCodigoFinal.add("Geracao comando WRITE: " + linha);
					
					ArrayList<String> buffer = new ArrayList<String>();
					for (int i = 1; i < linha.length; i++) {
						buffer.add(linha[i]);
					}	

					gerarCodigoExpressao(buffer);
					gerarCodigoFinal("PRNT escreva");
				}else if(linha[0].equals("se")){
					logCodigoFinal.add("Geracao do codigo de comando condicional SE: " + linha);
					rotulo = obterRotulo();
					Rotulo.push(rotulo);

					//Obtem a posicao do operador logico
					Integer operadorLogico = 0;
					for (int i = 0; i < linha.length; i++) {
						String elemento = linha[i];
						if (elemento.equals("==") || elemento.equals("<") || elemento.equals(">") || 
							elemento.equals("<=") || elemento.equals(">=") ) {
							operadorLogico = i;
							break;
						}
					}

					//pega a expressao antes do operador logico
					ArrayList<String> buffer = new ArrayList<String>();
					for (int i = 1; i < operadorLogico; i++) {
						buffer.add(linha[i]);
					}	 
					gerarCodigoExpressao(buffer);

					//pega a expressao depois do operador logico
					buffer = new ArrayList<String>();
					for (int i = operadorLogico + 1; i < linha.length; i++) {
						buffer.add(linha[i]);
					}	

					gerarCodigoExpressao(buffer);

					//compara as expressoes
					if(linha[operadorLogico].equals("==")){
						gerarCodigoFinal("EQUA " + identacao + "compara igual");
					}else if(linha[operadorLogico].equals("<")){
						gerarCodigoFinal("LESS"  + identacao + "compara menor");
					}else if(linha[operadorLogico].equals(">")){
						gerarCodigoFinal("GRTR"  + identacao + "compara maior");
					}else if(linha[operadorLogico].equals("<=")){
						gerarCodigoFinal("LEQU"  + identacao + " compara menor ou igual");
					}else if(linha[operadorLogico].equals(">=")){
						gerarCodigoFinal("GEQU"  + identacao + " compara maior ou igual");
					}

					//pula se falso
					codigoFinal.add("JMPF " + rotulo + identacao +"pula se falso");

				}else if(linha[0].equals("senao")){
					logCodigoFinal.add("Geracao do codigo de comando SENAO: " + linha);
					rotulo = obterRotulo();
					codigoFinal.add("JUMP " + rotulo + identacao + "pula para o fim do senao");
					codigoFinal.add(Rotulo.pop() + ": NOOP " + identacao + " senao"); 
					Rotulo.push(rotulo);
				}
				else if(linha[0].equals("fimse")){
					logCodigoFinal.add("Geracao do codigo de termino de comando condicional FIMSE: " + linha);
					rotulo = Rotulo.pop();
					codigoFinal.add(rotulo + ": NOOP" +identacao + "fimse"); 
				}else if(linha[0].equals("enquanto")){
					logCodigoFinal.add("Geracao do codigo de comando de repeticao ENQUANTO: " + linha);
					rotulo = obterRotulo();
					Rotulo.push(rotulo);
					codigoFinal.add(rotulo + ": NOOP"  + identacao + "enquanto");

					//Obtem a posicao do operador logico
					Integer operadorLogico = 0;
					for (int i = 0; i < linha.length; i++) {
						String elemento = linha[i];
						if (elemento.equals("==") || elemento.equals("<") || elemento.equals(">") || 
							elemento.equals("<=") || elemento.equals(">=") ) {
							operadorLogico = i;
							break;
						}
					}

					//pega a expressao antes do operador logico
					ArrayList<String> buffer = new ArrayList<String>();
					for (int i = 1; i < operadorLogico; i++) {
						buffer.add(linha[i]);
					}	 
					gerarCodigoExpressao(buffer);

					//pega a expressao depois do operador logico
					buffer = new ArrayList<String>();
					for (int i = operadorLogico + 1; i < linha.length; i++) {
						buffer.add(linha[i]);
					}	

					gerarCodigoExpressao(buffer);

					//compara as expressoes
					if(linha[operadorLogico].equals("==")){
						gerarCodigoFinal("EQUA"  + identacao +" compara igual");
					}else if(linha[operadorLogico].equals("<")){
						gerarCodigoFinal("LESS"  + identacao +" compara menor");
					}else if(linha[operadorLogico].equals(">")){
						gerarCodigoFinal("GRTR"  + identacao +" compara maior");
					}else if(linha[operadorLogico].equals("<=")){
						gerarCodigoFinal("LEQU"  + identacao +" compara menor ou igual");
					}else if(linha[operadorLogico].equals(">=")){
						gerarCodigoFinal("GEQU"  + identacao + "compara maior ou igual");
					}

				}else if(linha[0].equals("fimenquanto")){
					logCodigoFinal.add("Geracao do codigo de termino de comando repeticao FIMENQUANTO: " + linha);
					rotulo = Rotulo.pop();
					codigoFinal.add("JMPF " + rotulo +   identacao +"fim_enquanto");
				}else if(linha[1].equals("=")){
					Integer posicaoDaTabela = tabelaVariaveis.indexOf(linha[0]);
					
					logCodigoFinal.add("Geracao do codigo de comando Atribuicao: " + linha);
					
					ArrayList<String> buffer = new ArrayList<String>();
					for (int i = 2; i < linha.length; i++) {
						buffer.add(linha[i]);
					}	

					gerarCodigoExpressao(buffer);
					gerarCodigoFinal("STVL 0," + posicaoDaTabela  + identacao +  "guarde o valor da expressao no endereco memoria correspondente " + linha[0]);

				}
            }
		}	

		gerarFechamento();
		return codigoFinal;
		
	}
		

	private void gerarFechamento() {
		gerarCodigoFinal("DLOC " + tabelaVariaveis.size()  + identacao + "Liberar memoria");
		gerarCodigoFinal("STOP");
		gerarCodigoFinal("END");
	
	}

	private String obterRotulo() {
		logCodigoFinal.add("Geracao de codigo de geracao de rotulo para comandos de repeticao ou condicional"); 
		contador += 1;
		return "L" + contador; 		
	}

	private void gerarCodigoExpressao(ArrayList<String> expressao) {
		

		
		logCodigoFinal.add("Geracao de codigo de expressao ");

		for (String item : expressao) {
			if(item.matches("[a-zA-Z]+")) {
				Integer posicaoDaTabela = tabelaVariaveis.indexOf(item);
				gerarCodigoFinal("LDVL 0,"+  posicaoDaTabela  + identacao + "carrega o valor da variavel " + item );
			}else if(item.matches("[0-9]+")){
				gerarCodigoFinal("LDCT " + item +   identacao + "carrega o valor da constante " + item);
			}else if(item.equals("+")){
				gerarCodigoFinal("ADDD"  + identacao + "soma");
			}else if(item.equals("-")){
				gerarCodigoFinal("SUBT" + "subtrai");
			}
			else if(item.equals("*")){
				gerarCodigoFinal("MULT"  + identacao + "multiplica");
			}else if(item.equals("/")){
				gerarCodigoFinal("DIVI"  + identacao + "divide");
			}
		}
		
		
		
	}

	private void alocarMemoriaVariavel() {
		gerarCodigoFinal("ALOC " + tabelaVariaveis.size()  + identacao + "aloca memoria para " + tabelaVariaveis.size() + " variaveis");
		logCodigoFinal.add
		("Alocando memoria para " + tabelaVariaveis.size()  + identacao +  "variaveis");
	}
	
	
	private void gerarCodigoFinal(String linha){
		String identacao = "      ";
		codigoFinal.add(identacao + linha);
	}

	public ArrayList<String> retornaLogFinal() {
		return logCodigoFinal;
	}
	
	

}
