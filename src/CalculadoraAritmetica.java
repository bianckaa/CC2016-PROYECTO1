import java.util.Stack;

public class CalculadoraAritmetica<T extends Number> {

    private Tokenizador2 tokenizador;

    public CalculadoraAritmetica() {
        this.tokenizador = new Tokenizador2();
    }

    public Double calcularExpresionLisp(String expresionLisp) {
        // Tokenizar la expresión Lisp
        Stack<String> tokens = tokenizador.tokenize(expresionLisp);

        // Validar la expresión usando los métodos del tokenizador
        if (!tokenizador.esExpresionValida(tokens)) {
            throw new IllegalArgumentException("La expresión está vacía");
        }

        // El primer token debe ser el operador
        String operador = tokens.pop();
        if (!tokenizador.esPrimerTokenOperador(tokens)) {
            throw new IllegalArgumentException("Operador no válido: " + operador);
        }

        // Procesar los operandos
        Stack<Double> operandos = new Stack<>();
        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            if (tokenizador.esNumeroValido(token)) {
                double operando = Double.parseDouble(token);
                operandos.push(operando);
            } else {
                throw new IllegalArgumentException("Operando inválido: " + token);
            }
        }

        // Realizar la operación
        return calcular(operador, operandos);
    }

    private Double calcular(String operador, Stack<Double> operandos) {
        if (operandos.isEmpty()) {
            throw new IllegalArgumentException("La lista de operandos está vacía");
        }

        // Realizar la operación
        switch (operador) {
            case "+": return sumar(operandos);
            case "-": return restar(operandos);
            case "*": return multiplicar(operandos);
            case "/": return dividir(operandos);
            default: throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }

    private double sumar(Stack<Double> values) {
        double resultado = 0.0;
        while (!values.isEmpty()) {
            resultado += values.pop();
        }
        return resultado;
    }

    private double restar(Stack<Double> values) {
        double resultado = values.pop();
        while (!values.isEmpty()) {
            resultado -= values.pop();
        }
        return resultado;
    }

    private double multiplicar(Stack<Double> values) {
        double resultado = 1.0;
        while (!values.isEmpty()) {
            resultado *= values.pop();
        }
        return resultado;
    }

    private double dividir(Stack<Double> values) {
        double resultado = values.pop();
        while (!values.isEmpty()) {
            resultado /= values.pop();
        }
        return resultado;
    }
}
