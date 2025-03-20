import java.util.LinkedList;
import java.util.Queue;

public class CalculadoraAritmetica {

    private final Tokenizador2 tokenizador;

    public CalculadoraAritmetica() {
        this.tokenizador = new Tokenizador2();
    }

    public Double calcularExpresionLisp(String expresionLisp) {
        Queue<String> tokens = new LinkedList<>(tokenizador.tokenize(expresionLisp));

        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expresión vacía o incompleta.");
        }

        return evaluar(tokens);
    }

    private Double evaluar(Queue<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expresión vacía o incompleta.");
        }

        String operador = tokens.poll(); // Extraer el operador

        if (!tokenizador.esOperadorValido(operador)) {
            throw new IllegalArgumentException("Operador no válido: " + operador);
        }

        // Leer los operandos en orden correcto
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Faltan operandos en la expresión.");
        }

        double resultado = Double.parseDouble(tokens.poll()); // Tomar el primer operando

        while (!tokens.isEmpty()) {
            double valor = Double.parseDouble(tokens.poll());
            switch (operador) {
                case "+" -> resultado += valor;
                case "-" -> resultado -= valor;
                case "*" -> resultado *= valor;
                case "/" -> {
                    if (valor == 0) throw new ArithmeticException("División por cero");
                    resultado /= valor;
                }
                case "%" -> resultado %= valor;
                default -> throw new IllegalArgumentException("Operador desconocido.");
            }
        }
        return resultado;
    }
}