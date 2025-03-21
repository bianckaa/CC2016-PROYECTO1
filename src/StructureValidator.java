import java.util.Stack;

/**
 * La clase StructureValidator proporciona métodos para validar la sintaxis
 * de expresiones, asegurando que los paréntesis estén balanceados y que la 
 * estructura sea correcta.
 */
public class StructureValidator {

    /**
     * Verifica si los paréntesis en una expresión están balanceados.
     *
     * @param code La cadena de código que se evaluará.
     * @return {@code true} si los paréntesis están balanceados, {@code false} en caso contrario.
     */
    public static boolean BalancedParentheses(String code) {
        Stack<Character> stack = new Stack<>();
        for (char ch : code.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * Verifica si la estructura de la expresión es correcta.
     * Una estructura incorrecta incluye paréntesis vacíos "( )".
     *
     * @param code La cadena de código que se evaluará.
     * @return {@code false} si la expresión contiene paréntesis vacíos, {@code true} en caso contrario.
     */
    public static boolean CorrectStructure(String code) {
        return !code.contains("( )");
    }

    /**
     * Valida la sintaxis de una expresión, verificando que los paréntesis estén balanceados
     * y que la estructura sea correcta. Si la validación falla, lanza una excepción.
     *
     * @param code La cadena de código que se evaluará.
     * @throws IllegalArgumentException Si los paréntesis no están balanceados o la estructura es incorrecta.
     */
    public static void validateSyntax(String code) {
        if (!BalancedParentheses(code)) {
            throw new IllegalArgumentException("Error, los paréntesis no están balanceados");
        }
        if (!CorrectStructure(code)) {
            throw new IllegalArgumentException("Error, la expresión contiene paréntesis vacíos o está mal estructurada");
        }
    }
}

    

