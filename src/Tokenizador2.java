import java.util.Stack;

public class Tokenizador2 {
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
                i = handleQuote(code, i, tokens);
                continue;
            }

            if (code.startsWith("'", i)) {
                i += 1; 
                
                i = handleQuote(code, i, tokens);
                continue;
            }

            // Manejo de Setq
            if (code.startsWith("setq", i)) {
                i += 4; 
                tokens.push("setq"); 

                i = code.indexOf(" ", i); 
                if (i != -1) {
                    i++; 
                    int startIdx = i; 

                    while (i < code.length() && Character.isLetterOrDigit(code.charAt(i))) {
                        i++;
                    }
                    String varName = code.substring(startIdx, i).trim(); 

                    i = code.indexOf(" ", i); 
                    if (i != -1) {
                        i++;
                        startIdx = i;

                        if (code.charAt(i) == '"') {
                            i++; 
                            startIdx = i;

                            while (i < code.length() && code.charAt(i) != '"') {
                                i++;
                            }
                            String value = code.substring(startIdx, i); 
                            tokens.push(varName);
                            tokens.push(value);
                            i++; 
                        } else {
                            
                            while (i < code.length() && (Character.isDigit(code.charAt(i)) || code.charAt(i) == '.')) {
                                i++; 
                            }
                            String value = code.substring(startIdx, i).trim(); 
                            tokens.push(varName);
                            tokens.push(value); 
                        }
                    }
                }
                continue;
            }

            /*// Manejo de Defun
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
                    throw new IllegalArgumentException("Error: defun debe ir seguido de una lista de parámetros entre paréntesis");
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
                    throw new IllegalArgumentException("Error: Paréntesis no balanceados");
                }
                tokens.push(")");
                i++;

                while (i < n && Character.isWhitespace(code.charAt(i))) {
                    i++;
                }

                if (i >= n) {
                    throw new IllegalArgumentException("Error: El cuerpo de la función no puede estar vacío");
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
                        throw new IllegalArgumentException("Error: Paréntesis no balanceados en el cuerpo de la función");
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
            }*/
            
            while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != '(' && code.charAt(i) != ')') {
                token.append(code.charAt(i));
                i++;
            }

            /**String tokenStr = token.toString();
            if (esOperadorValido(tokenStr) || esNumeroValido(tokenStr)) {
                tokens.push(tokenStr);
            } else {
                throw new IllegalArgumentException("Token inválido: " + tokenStr);
            }*/

            token.setLength(0); 
        }
        return tokens;
    }

    private int handleQuote(String code, int startIndex, Stack<String> tokens) {
        int i = startIndex;
        int n = code.length();

        while (i < n && Character.isWhitespace(code.charAt(i))) {
            i++;
        }

        // Caso 1: (QUOTE ...)
        if (code.charAt(i) == '(') {
            int balance = 1;
            int start = i;
            i++;
    
            while (i < n && balance > 0) {
                if (code.charAt(i) == '(') balance++;
                if (code.charAt(i) == ')') balance--;
                i++;
            }
    
            if (balance != 0) {
                throw new IllegalArgumentException("Error: Paréntesis no balanceados después de quote");
            }
    
            String quoteBlock = code.substring(start, i);
            tokens.push("quote");
            tokens.push(quoteBlock);
        }

        // Caso 2: (' z)
        else {
            StringBuilder token = new StringBuilder();
            while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != '(' && code.charAt(i) != ')') {
                token.append(code.charAt(i));
                i++;
            }
    
            if (token.length() == 0) {
                throw new IllegalArgumentException("Error: La forma abreviada de quote debe ir seguida de un token");
            }
    
            tokens.push("quote");
            tokens.push(token.toString());
        } 
        return i; 
    }
}