import java.util.Stack;

public class QuoteEvaluator {
    public static int handleQuote(String code, int startIndex, Stack<String> tokens) {
        int i = startIndex;
        int n = code.length();

        // Saltar espacios en blanco
        while (i < n && Character.isWhitespace(code.charAt(i))) {
            i++;
        }

        // Verificar que el siguiente carácter sea '('
        if (i >= n || code.charAt(i) != '(') {
            throw new IllegalArgumentException("Error: quote debe ir seguido de una expresión entre paréntesis");
        }

        // Extraer el bloque entre paréntesis
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

        // Agregar el bloque a la pila de tokens
        String quoteBlock = code.substring(start, i);
        tokens.push("quote");
        tokens.push(quoteBlock);

        return i; // Devolver el nuevo índice
    }

    public static Object evaluateQuote(String expression) {
        return expression;
    }
}
