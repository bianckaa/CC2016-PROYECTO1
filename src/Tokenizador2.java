package src;
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

            
            if (ch == '(' || ch == ')') { // Ver como manejar los parentesis al momento de tokenizar
                tokens.push(String.valueOf(ch));
                i++;
                continue;
            }

            
            if (code.startsWith("quote", i)) {
                
                i += 5;

                
                while (i < n && Character.isWhitespace(code.charAt(i))) { //despues de un quote ignorar los espacios para tomar como un solo token
                    i++;
                }

                // Verificar que lo que sigue sea la apretura de parentesis
                if (i >= n || code.charAt(i) != '(') {
                    throw new IllegalArgumentException("Error: quote debe ir seguido de una expresion entre parentesis");
                }

                // Capturar todo hasta el parentesis de cierre correspondiente
                int balance = 1;
                int start = i;
                i++;

                while (i < n && balance > 0) {
                    if (code.charAt(i) == '(') balance++;
                    if (code.charAt(i) == ')') balance--;
                    i++;
                }

                if (balance != 0) {
                    throw new IllegalArgumentException("Error: Parentesis no balanceados después de quote");
                }

                // Agregar el quote y su bloque como tokens
                String quoteBlock = code.substring(start, i);
                tokens.push("quote");
                tokens.push(quoteBlock);
                continue;
            }

            
            
            while (i < n && !Character.isWhitespace(code.charAt(i)) && code.charAt(i) != '(' && code.charAt(i) != ')') {
                token.append(code.charAt(i));
                i++;
            }
            tokens.push(token.toString());
        }

        return tokens;
    }
}