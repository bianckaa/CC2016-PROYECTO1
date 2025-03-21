import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Clase que proporciona métodos para evaluar y definir funciones en un intérprete de Lisp.
 */
public class DefunEvaluator {

    /**
     * Evalúa la definición de una función (defun) a partir de una pila de tokens.
     *
     * @param tokens           Pila de tokens que representan la definición de la función.
     * @param variableManager  Administrador de variables donde se almacenará la función.
     * @throws IllegalArgumentException Si la sintaxis de la definición de la función es incorrecta.
     */
    public static void evaluateDefun(Stack<String> tokens, VariableManagement<Object> variableManager) {
        if (tokens.size() < 4) {
            throw new IllegalArgumentException("Error: defun requiere al menos tres argumentos (nombre, parámetros y cuerpo).");
        }

        String nombreFuncion = tokens.pop();

        if (!tokens.peek().equals("(")) {
            throw new IllegalArgumentException("Error: Se esperaba una lista de parámetros después del nombre de la función.");
        }
        tokens.pop();

        Stack<String> parametros = new Stack<>();
        while (!tokens.isEmpty() && !tokens.peek().equals(")")) {
            parametros.push(tokens.pop());
        }

        if (tokens.isEmpty() || !tokens.pop().equals(")")) {
            throw new IllegalArgumentException("Error: Paréntesis no balanceados en la lista de parámetros.");
        }

        Stack<String> cuerpo = new Stack<>();
        while (!tokens.isEmpty() && !tokens.peek().equals(")")) {
            cuerpo.push(tokens.pop());
        }

        if (tokens.isEmpty() || !tokens.pop().equals(")")) {
            throw new IllegalArgumentException("Error: Paréntesis no balanceados en el cuerpo de la función.");
        }

        Map<String, Object> funcion = new HashMap<>();
        funcion.put("parametros", parametros);
        funcion.put("cuerpo", cuerpo);
        variableManager.setVariable(nombreFuncion, funcion);

        System.out.println("Función " + nombreFuncion + " definida con éxito.");
    }

    /**
     * Evalúa una operación aritmética entre dos operandos.
     *
     * @param operador Operador aritmético (+, -, *, /).
     * @param a        Primer operando.
     * @param b        Segundo operando.
     * @return Resultado de la operación aritmética.
     * @throws IllegalArgumentException Si el operador no es válido o si hay una división por cero.
     */
    public static double evaluarAritmetica(String operador, double a, double b) {
        switch (operador) {
            case "+":
                return b + a;
            case "-":
                return b - a;
            case "*":
                return b * a;
            case "/":
                if (a == 0) {
                    throw new IllegalArgumentException("Error: División por cero.");
                }
                return b / a;
            default:
                throw new IllegalArgumentException("Error: Operador aritmético no válido: " + operador);
        }
    }

    /**
     * Evalúa una expresión aritmética representada en una pila de tokens.
     *
     * @param expresion        Pila de tokens que representan la expresión aritmética.
     * @param variableManager  Administrador de variables para la resolución de identificadores.
     * @return Resultado de la evaluación de la expresión aritmética.
     * @throws IllegalArgumentException Si la expresión está mal formada o contiene variables no definidas.
     */
    public static double evaluarExpresionAritmetica(Stack<String> expresion, VariableManagement<Object> variableManager) {
        Stack<Double> valores = new Stack<>();

        while (!expresion.isEmpty()) {
            String token = expresion.pop();

            if (token.equals("(") || token.equals(")")) {
                continue;
            }

            if (esNumero(token)) {
                valores.push(Double.parseDouble(token));
            } else if (esOperadorAritmetico(token)) {
                if (valores.size() < 2) {
                    throw new IllegalArgumentException("Error: Faltan operandos para el operador " + token);
                }
                double b = valores.pop();
                double a = valores.pop();
                double resultado = evaluarAritmetica(token, a, b);
                valores.push(resultado);
            } else {
                Object valorVariable = variableManager.getVariable(token);
                if (valorVariable == null) {
                    throw new IllegalArgumentException("Error: Variable no definida: " + token);
                }
                valores.push(Double.parseDouble(valorVariable.toString()));
            }
        }

        if (valores.size() != 1) {
            throw new IllegalArgumentException("Error: Expresión aritmética mal formada.");
        }

        return valores.pop();
    }

    /**
     * Verifica si un token representa un número válido.
     *
     * @param token Token a verificar.
     * @return true si el token es un número, false en caso contrario.
     */
    private static boolean esNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica si un token es un operador aritmético válido.
     *
     * @param token Token a verificar.
     * @return true si es un operador aritmético (+, -, *, /), false en caso contrario.
     */
    private static boolean esOperadorAritmetico(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }
}
