import java.util.Stack;
///Tokenizador descartado

public class Tokenizador2{

    public Stack<String> tokenize(String code) {
        Stack<String> tokens = new Stack<>();
        int i = 0;
        int n = code.length();
        StringBuilder token = new StringBuilder();

        while (i < n) {
            char ch = code.charAt(i);

            if (Character.isWhitespace(ch)) {  
                i++;
                continue;
            }

            
            if (ch == '(' || ch == ')') { 
                tokens.push(String.valueOf(ch));
                i++;
                continue;
            }

            // Manejo de Quote
            if (code.toLowerCase().startsWith("quote", i)) {
                i += 5;
                i = QuoteEvaluator.handleQuote(code, i, tokens);
                continue;
            }

            if (code.startsWith("'", i)) {
                i += 1; 
                
                i = QuoteEvaluator.handleQuote(code, i, tokens);
                continue;
            }

            // Manejo de "defun"
            if (code.startsWith("defun", i)) {
                i += 5;
            
                while (i < n && Character.isWhitespace(code.charAt(i))) {
                    i++;
                }
            
                int start = i;
                while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != '(') {
                    i++;
                }
                String nombreFuncion = code.substring(start, i);
                tokens.push("defun");
                tokens.push(nombreFuncion);
            
                while (i < n && Character.isWhitespace(code.charAt(i))) {
                    i++;
                }
            
                if (i >= n || code.charAt(i) != '(') {
                    throw new IllegalArgumentException("Error: defun debe ir seguido de una lista de parametros entre parentesis");
                }
            
                tokens.push("(");
                i++;
            
                while (i < n && code.charAt(i) != ')') {
                    if (Character.isWhitespace(code.charAt(i))) {
                        i++;
                        continue;
                    }
            
                    start = i;
                    while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != ')') {
                        i++;
                    }
                    String parametro = code.substring(start, i);
                    tokens.push(parametro);
                }
            
                if (i >= n || code.charAt(i) != ')') {
                    throw new IllegalArgumentException("Error: Parentesis no balanceados ");
                }
                tokens.push(")");
                i++;
            
                while (i < n && Character.isWhitespace(code.charAt(i))) {
                    i++;
                }
            
                
                if (i >= n) {
                    throw new IllegalArgumentException("Error: El cuerpo de la funcion no puede estar vacio");
                }
            
                if (code.charAt(i) == '(') {
                    tokens.push("(");
                    i++;
            
                    while (i < n && code.charAt(i) != ')') {
                        if (Character.isWhitespace(code.charAt(i))) {
                            i++;
                            continue;
                        }
            
                        if (code.charAt(i) == '(') {
                            tokens.push("(");
                            i++;
                            continue;
                        }
            
                        if (code.charAt(i) == ')') {
                            tokens.push(")");
                            i++;
                            continue;
                        }
            
                        start = i;
                        while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != '(' && code.charAt(i) != ')') {
                            i++;
                        }
                        String tokenCuerpo = code.substring(start, i);
                        tokens.push(tokenCuerpo);
                    }
            
                    if (i >= n || code.charAt(i) != ')') {
                        throw new IllegalArgumentException("Error: Parentesis no balanceados en el cuerpo de la funcion");
                    }
                    tokens.push(")");
                    i++;
                } else {
                    
                    start = i;
                    while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != ')') {
                        i++;
                    }
                    String cuerpo = code.substring(start, i);
                    tokens.push(cuerpo);
                }
            
                continue;
            }
            
            while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != '(' && code.charAt(i) != ')') {
                token.append(code.charAt(i));
                i++;
            }
            tokens.push(token.toString());
            token.setLength(0);
        }
        return tokens;
    }
}