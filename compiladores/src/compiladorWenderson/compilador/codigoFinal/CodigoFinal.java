/**
 * 
 */
package compiladorWenderson.compilador.codigoFinal;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * @author wende
 *
 */
public class CodigoFinal {

	private ArrayList<String> codigoIntermediario = new ArrayList<String>();
	private ArrayList<String> codigoFinal = new ArrayList<String>();
	private ArrayList<String> logCodigoIntermediario = new ArrayList<String>();
	private Integer contador = 0;
	private Integer contadorvariavel = 0;
	private Stack<String> Rotulo= new Stack<String>();
	private String rotulo = "";
	private Map<String, Integer> tabelaVariaveis = new Map<String, Integer>();

	public CodigoFinal(ArrayList<String> codigoIntermediario) {
		this.codigoIntermediario = codigoIntermediario;
		
	}

	public ArrayList<String> gerarCodigoFinal() {
		
		for (String elemento : codigoIntermediario) {

            if (!elemento.substring(0, 4).equals("_var")) {
                tabelaVariaveis.put(elemento, contadorvariavel++);
            }
        }

		codigoFinal.add("MAIN inicio_programa");
		alocarMemoriaVariavel();

		for (String comando : codigoIntermediario) {
			String[] linha = comando.split(" ");
            if (!comando.substring(0, 4).equals("_var")) {
                if(linha[0].equals("leia"){
					logCodigoIntermediario.add("Geracao comando READ " + linha);
					codigoFinal.add("READ leia");
					codigoFinal.add("STVL 0," + tabelaVariaveis.get(linha[1]) + "carregue valor no endereco de memoria 0," + tabelaVariaveis.get(linha[1]));
				}else if(linha[0].equals("escreva")){
					logCodigoIntermediario.add("Geracao comando WRITE " + linha);
					
					ArrayList<String> buffer = new ArrayList<String>();
					for (int i = 1; i < linha.length; i++) {
						buffer.add(linha[i]);
					}	
					
					gerarCodigoExpressao(buffer);
					codigoFinal.add("PRNT escreva");
				}
            }
        
		
		
		return null;
	}

	private void gerarCodigoExpressao(ArrayList<String> expressao) {
		logCodigoIntermediario.add("Geracao de codigo de expressao ");
		String letra = "";

		for (String item : expressao) {
			if(item.matches("[a-zA-Z]+")) {
			
			}
		}
		
		
		
	}

	private void alocarMemoriaVariavel() {
		codigoFinal.add("ALOC " + tabelaVariaveis.size());
		logCodigoIntermediario.add
		("Alocando memoria para " + tabelaVariaveis.size() +  " variaveis");
	}
	
	
	
	
}
