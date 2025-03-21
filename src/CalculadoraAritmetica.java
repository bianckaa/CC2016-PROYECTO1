import java.util.Stack;

/**
 * La clase {@code CalculadoraAritmetica} permite evaluar expresiones aritméticas utilizando una pila de tokens.
 * Los operadores soportados son: suma (+), resta (-), multiplicación (*), división (/) y módulo (%).
 * La expresión debe estar representada en forma de tokens, siendo el primer token un operador seguido de los operandos.
 */
public class CalculadoraAritmetica {

    /**
     * Evalúa una expresión aritmética representada por una pila de tokens.
     * Los tokens deben consistir en un operador seguido de los operandos.
     * 
     * @param tokens La pila de tokens que representa la expresión aritmética.
     * @return El resultado de la operación aritmética.
     * @throws IllegalArgumentException Si la pila de tokens está vacía o si el operador no es válido.
     * @throws ArithmeticException Si ocurre una división por cero.
     */
    public Double evaluar(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Error: Expresión vacía");
        }
        
        String operador = tokens.pop();
        if (!esOperadorValido(operador)) {
            throw new IllegalArgumentException("Error: Operador no válido: " + operador);
        }
        
        double resultado;
        switch (operador) {
            case "+":
                resultado = 0;
                while (!tokens.isEmpty()) {
                    resultado += Double.parseDouble(tokens.pop());
                }
                break;
            case "-":
                resultado = Double.parseDouble(tokens.pop());
                while (!tokens.isEmpty()) {
                    resultado -= Double.parseDouble(tokens.pop());
                }
                break;
            case "*":
                resultado = 1;
                while (!tokens.isEmpty()) {
                    resultado *= Double.parseDouble(tokens.pop());
                }
                break;
            case "/":
                resultado = Double.parseDouble(tokens.pop());
                while (!tokens.isEmpty()) {
                    double divisor = Double.parseDouble(tokens.pop());
                    if (divisor == 0) {
                        throw new ArithmeticException("Error: División por cero");
                    }
                    resultado /= divisor;
                }
                break;
            case "%":
                double a = Double.parseDouble(tokens.pop());
                double b = Double.parseDouble(tokens.pop());
                resultado = a % b;
                break;
            default:
                throw new IllegalArgumentException("Error: Operador desconocido " + operador);
        }
        return resultado;
    }

    /**
     * Verifica si un token es un operador válido.
     * 
     * @param token El token que se va a verificar.
     * @return {@code true} si el token es un operador válido, {@code false} en caso contrario.
     */
    private boolean esOperadorValido(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%");
    }
}

