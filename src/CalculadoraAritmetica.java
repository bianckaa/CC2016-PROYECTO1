package src;
import java.util.List;
import java.util.Stack;

public class CalculadoraAritmetica<T> {

    public Double calculate(List<T> prefixList) {  
        Stack<Double> result = new Stack<>();

        if (prefixList.isEmpty()) {
            throw new IllegalArgumentException("La lista de expresión está vacía");
        }
        
        T signo = prefixList.get(0); 

        for (int i = 1; i < prefixList.size(); i++) {
            T elemento = prefixList.get(i);

            if (elemento instanceof Number) {
                result.push(((Number) elemento).doubleValue());
            }
        }

        if (signo instanceof String) {
            String operador = (String) signo;

            switch (operador) {
                case "+": return sumar(result);
                case "-": return restar(result);
                case "*": return multiplicar(result);
                case "/": return dividir(result);
                default: throw new IllegalArgumentException("Operador no válido");
            }
        }

        throw new IllegalArgumentException("El primer elemento debe ser un operador");
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
