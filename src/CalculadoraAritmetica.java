import java.util.Stack;

public class CalculadoraAritmetica {
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

    private boolean esOperadorValido(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%");
    }
}
