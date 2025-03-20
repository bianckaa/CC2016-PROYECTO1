import java.util.Stack;

public class SetqEvaluator {
    private final VariableManagement<Object> variableManagement;
    private final CalculadoraAritmetica calculadora;

    public SetqEvaluator(VariableManagement<Object> variableManagement) {
        this.variableManagement = variableManagement;
        this.calculadora = new CalculadoraAritmetica();
    }

    public void evaluateSetq(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Error: setq necesita al menos dos parámetros (variable y valor)");
        }

        String variable = tokens.pop();

        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Error: setq necesita un valor para asignar a " + variable);
        }

        String valor = tokens.pop();
        System.out.println("Procesando setq: " + variable + " = " + valor);

        if (esNumeroValido(valor)) {
            double numero = Double.parseDouble(valor);
            variableManagement.setVariable(variable, numero);
            System.out.println("Asignado directamente: " + variable + " = " + numero);
        } else if (valor.startsWith("(")) {
            String expresionMatematica = extraerExpresion(tokens, valor);
            expresionMatematica = limpiarParentesis(expresionMatematica);
            System.out.println("Evaluando expresión matemática: " + expresionMatematica);
            
            // Convertimos la expresión en una pila de tokens para evaluar correctamente
            Stack<String> expresionTokens = new Tokenizador2().tokenize(expresionMatematica);
            Stack<String> expresionRevertida = new Stack<>();
            
            while (!expresionTokens.isEmpty()) {
                expresionRevertida.push(expresionTokens.pop());
            }
            
            Double resultado = calculadora.evaluar(expresionRevertida);
            
            variableManagement.setVariable(variable, resultado);
            System.out.println("Asignado después de evaluar: " + variable + " = " + resultado);
        } else {
            throw new IllegalArgumentException("Error: setq solo puede asignar números o expresiones matemáticas");
        }
    }

    private String extraerExpresion(Stack<String> tokens, String inicio) {
        StringBuilder expresion = new StringBuilder(inicio);
        int balance = 1;
        
        while (!tokens.isEmpty()) {
            String nextToken = tokens.pop();
            if (nextToken.equals("(")) balance++;
            if (nextToken.equals(")")) balance--;
            expresion.append(" ").append(nextToken);
            if (balance == 0) break;
        }
        return expresion.toString();
    }

    private String limpiarParentesis(String expresion) {
        if (expresion.startsWith("(") && expresion.endsWith(")")) {
            return expresion.substring(1, expresion.length() - 1).trim();
        }
        return expresion;
    }

    private boolean esNumeroValido(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}