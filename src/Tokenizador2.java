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

            if (code.startsWith("quote", i)) {
                i += 5;

                while (i < n && Character.isWhitespace(code.charAt(i))) {
                    i++;
                }

                if (i >= n || code.charAt(i) != '(') {
                    throw new IllegalArgumentException("Error: quote debe ir seguido de una expresión entre paréntesis");
                }

                int balance = 1;
                int start = i;
                i++;

                while (i < n && balance > 0) {
                    if (code.charAt(i) == '(') balance++;
                    if (code.charAt(i) == ')') balance--;
                    i++;
                }

                if (balance != 0) {
                    throw new IllegalArgumentException("Error: Paréntesis no balanceados");
                }

                String quoteBlock = code.substring(start, i);
                tokens.push("quote");
                tokens.push(quoteBlock);
                continue;
            }

            if (code.startsWith("'", i)) {
                i += 1;

                if (i >= n || code.charAt(i) != '(') {
                    throw new IllegalArgumentException("Error: debe de ir pegado el apostrofe con un paréntesis");
                }

                while (i < n && Character.isWhitespace(code.charAt(i))) {
                    i++;
                }

                if (i >= n || code.charAt(i) != '(') {
                    throw new IllegalArgumentException("Error: quote debe ir seguido de una expresión entre paréntesis");
                }

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
                continue;
            }

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
            }

            while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != '(' && code.charAt(i) != ')') {
                token.append(code.charAt(i));
                i++;
            }

            String tokenStr = token.toString();
            if (esOperadorValido(tokenStr) || esNumeroValido(tokenStr)) {
                tokens.push(tokenStr);
            } else {
                throw new IllegalArgumentException("Token inválido: " + tokenStr);
            }

            token.setLength(0); 
        }

        return tokens;
    }


    public boolean esOperadorValido(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    public boolean esNumeroValido(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean esExpresionValida(Stack<String> tokens) {
        return !tokens.isEmpty();
    }

    public boolean esPrimerTokenOperador(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            return false;
        }
        String primerToken = tokens.peek();
        return esOperadorValido(primerToken);
    }
}