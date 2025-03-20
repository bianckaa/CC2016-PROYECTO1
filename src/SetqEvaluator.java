import java.util.Stack;

/**
 * La clase SetqEvaluator maneja la asignación de valores a variables en un entorno de ejecución,
 * permitiendo almacenar números y evaluar expresiones matemáticas.
 */
public class SetqEvaluator {
    private final VariableManagement<Object> variableManagement;
    private final CalculadoraAritmetica calculadora;

    /**
     * Constructor que inicializa el evaluador con un gestor de variables.
     *
     * @param variableManagement El gestor de variables donde se almacenarán los valores asignados.
     */
    public SetqEvaluator(VariableManagement<Object> variableManagement) {
        this.variableManagement = variableManagement;
        this.calculadora = new CalculadoraAritmetica();
    }

    /**
     * Evalúa una expresión de asignación (setq) y almacena el resultado en la variable correspondiente.
     *
     * @param tokens Pila de tokens que representan la instrucción setq.
     * @throws IllegalArgumentException Si la cantidad de parámetros es incorrecta o el valor no es válido.
     */
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

    /**
     * Extrae una expresión matemática de la pila de tokens, asegurando que los paréntesis estén balanceados.
     *
     * @param tokens Pila de tokens.
     * @param inicio Primer token de la expresión.
     * @return La expresión completa en formato de cadena.
     */
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

    /**
     * Elimina los paréntesis exteriores de una expresión si están presentes.
     *
     * @param expresion La expresión matemática en formato de cadena.
     * @return La expresión sin paréntesis exteriores.
     */
    private String limpiarParentesis(String expresion) {
        if (expresion.startsWith("(") && expresion.endsWith(")")) {
            return expresion.substring(1, expresion.length() - 1).trim();
        }
        return expresion;
    }

    /**
     * Verifica si un token representa un número válido.
     *
     * @param token El token a evaluar.
     * @return {@code true} si el token es un número válido, {@code false} en caso contrario.
     */
    private boolean esNumeroValido(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
