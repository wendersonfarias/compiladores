package compiladorWenderson.compilador.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Infixa_posfixa {
	public static String infixa_posfixa(String infixExpression) {
        StringBuilder postfixExpression = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        Map<Character, Integer> precedence = new HashMap<>();
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);
        

        for (Character c : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                postfixExpression.append(c);
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                	postfixExpression.append(" ");
                	postfixExpression.append(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                	return null;
                }else {
                    operatorStack.pop();

                }
            } else {
            	System.out.println(c);
            	System.out.println(precedence.get(c));
            	if(!c.equals(null) ) {
	                while (!operatorStack.isEmpty() && precedence.get(c) <= precedence.get(operatorStack.peek())) {
	                	postfixExpression.append(" ");
	                    postfixExpression.append(operatorStack.pop());
	                }
	                postfixExpression.append(" ");
	                operatorStack.push(c);
            	} 
            }
        }

        while (!operatorStack.isEmpty()) {
        	if(operatorStack.peek() == '(') {
        		return null;
        	}
        	postfixExpression.append(" ");
            postfixExpression.append(operatorStack.pop());
        }

        return postfixExpression.toString();
    }
}
