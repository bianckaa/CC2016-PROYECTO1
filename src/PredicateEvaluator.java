/**
 * Clase que evalúa predicados en expresiones Lisp simuladas.
 */
import java.util.Stack;

/**
 * Clase que permite evaluar predicados en una estructura de tokens.
 */
public class PredicateEvaluator {
    private VariableManagement<Object> variableManager;

    /**
     * Constructor de la clase PredicateEvaluator.
     * 
     * @param variableManager Gestor de variables utilizado en la evaluación.
     */
    public PredicateEvaluator(VariableManagement<Object> variableManager) {
        this.variableManager = variableManager;
    }

    /**
     * Evalúa un predicado específico con los tokens proporcionados.
     * 
     * @param predicado Nombre del predicado a evaluar.
     * @param tokens Pila de tokens sobre la cual se evaluará el predicado.
     * @return Resultado de la evaluación del predicado.
     * @throws IllegalArgumentException Si el predicado no es válido.
     */
    public Object evaluarPredicado(String predicado, Stack<String> tokens) {
        predicado = predicado.toUpperCase();
        
        switch (predicado) {
            case "ATOM":
                return isAtom(tokens);
            case "LIST":
                return isList(tokens);
            case "EQUAL":
                return isEqual(tokens);
            case "<":
                return isLessThan(tokens);
            case ">":
                return isGreaterThan(tokens);
            default:
                throw new IllegalArgumentException("Predicado no válido: " + predicado);
        }
    }

    /**
     * Determina si un token es un átomo.
     * 
     * @param tokens Pila de tokens.
     * @return "T" si es un átomo, "nil" en caso contrario.
     */
    private String isAtom(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Error: ATOM necesita un argumento.");
        }
        String expresion = tokens.pop();
        return expresion.startsWith("(") ? "nil" : "T";
    }

    /**
     * Determina si un token representa una lista.
     * 
     * @param tokens Pila de tokens.
     * @return "T" si es una lista, "nil" en caso contrario.
     */
    private String isList(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Error: LIST necesita un argumento.");
        }
        return tokens.pop().startsWith("(") ? "T" : "nil";
    }

    /**
     * Determina si dos expresiones son iguales.
     * 
     * @param tokens Pila de tokens.
     * @return "T" si son iguales, "nil" en caso contrario.
     */
    private String isEqual(Stack<String> tokens) {
        if (tokens.size() < 2) {
            throw new IllegalArgumentException("Error: EQUAL necesita dos argumentos.");
        }
        return obtenerValor(tokens.pop()).equals(obtenerValor(tokens.pop())) ? "T" : "nil";
    }

    /**
     * Determina si un número es menor que otro.
     * 
     * @param tokens Pila de tokens.
     * @return "T" si el primer número es menor que el segundo, "nil" en caso contrario.
     */
    String isLessThan(Stack<String> tokens) {
        if (tokens.size() < 2) {
            throw new IllegalArgumentException("Error: < necesita dos argumentos.");
        }
        try {
            return Double.parseDouble(obtenerValor(tokens.pop())) < Double.parseDouble(obtenerValor(tokens.pop())) ? "T" : "nil";
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: < solo acepta valores numéricos.");
        }
    }

    /**
     * Determina si un número es mayor que otro.
     * 
     * @param tokens Pila de tokens.
     * @return "T" si el primer número es mayor que el segundo, "nil" en caso contrario.
     */
    private String isGreaterThan(Stack<String> tokens) {
        if (tokens.size() < 2) {
            throw new IllegalArgumentException("Error: > necesita dos argumentos.");
        }
        try {
            return Double.parseDouble(obtenerValor(tokens.pop())) > Double.parseDouble(obtenerValor(tokens.pop())) ? "T" : "nil";
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: > solo acepta valores numéricos.");
        }
    }
    
    /**
     * Obtiene el valor de un token, considerando si es una variable almacenada.
     * 
     * @param token Token a evaluar.
     * @return Valor del token o su representación original.
     */
    private String obtenerValor(String token) {
        Object valorVariable = variableManager.getVariable(token);
        return (valorVariable != null) ? valorVariable.toString() : token;
    }
}
