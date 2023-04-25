package compiladorWenderson.compilador.semantico;

import java.util.HashMap;
import java.util.Map;



public class TabelaDeSimbolos {

    private Map<String, Integer> tabela = new HashMap<>();

    public void adicionarSimbolo(String identificador, int valor) {
        if (!tabela.containsKey(identificador)) {
            tabela.put(identificador, valor);
        } else {
            throw new RuntimeException("Identificador j� declarado: " + identificador);
        }
    }

    public int obterValor(String identificador) {
        if (tabela.containsKey(identificador)) {
            return tabela.get(identificador);
        } else {
            throw new RuntimeException("Identificador n�o declarado: " + identificador);
        }
    }
    
    public boolean verificaDeclarado(String identificador) {
        if (tabela.containsKey(identificador)) {
            return true;
        } else {
            throw new RuntimeException("Identificador n�o declarado: " + identificador);
        }
    }

}
