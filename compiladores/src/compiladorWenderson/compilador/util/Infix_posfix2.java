package compiladorWenderson.compilador.util;

import java.util.Stack;

public class Infix_posfix2 {


public String InFixToPosFix(String InFix){
    StringBuilder postfix  = new StringBuilder();
    Stack<Character> stack = new Stack<>();

    for (char ch : InFix.toCharArray()) {
        if (Character.isLetterOrDigit(ch)) {
            postfix.append(ch);
        } else if (ch == '(') {
            stack.push(ch);
        } else if (ch == ')') {
            while (!stack.isEmpty() && stack.peek() != '(') {
                postfix.append(" ");
                postfix.append(stack.pop());
            }
            if (!stack.isEmpty() && stack.peek() != '(') {
                // Expressão inválida
                return null;
            } else {
                stack.pop(); // Remover '('
            }
        } else {
            while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                postfix.append(" ");
                postfix.append(stack.pop());
            }
            postfix.append(" ");
            stack.push(ch);
        }
    }

    while (!stack.isEmpty()) {
        if (stack.peek() == '(') {
            // Expressão inválida
            return null;
        }
        postfix.append(" ");
        postfix.append(stack.pop());
    }

    return postfix.toString();

}

private static int precedence(char ch) {
    switch (ch) {
        case '+':
        case '-':
            return 1;
        case '*':
        case '/':
            return 2;
       
    }
    return -1;
}
}