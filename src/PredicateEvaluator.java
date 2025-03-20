import java.util.Stack;

public class PredicateEvaluator {
    private VariableManagement<Object> variableManager;


    public PredicateEvaluator(VariableManagement<Object> variableManager) {
        this.variableManager = variableManager;
    }

    public Object evaluarPredicado(String predicado, Stack<String> tokens) {
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
                throw new IllegalArgumentException("Predicado no valido: " + predicado);
        }
    }

    public String isAtom(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Error: ATOM necesita un argumento.");
        }

        String expresion = tokens.pop();

        if (expresion.startsWith("(")) {
            return "nil";
        }

        if (expresion.equals("'")) {
            expresion = tokens.pop();
        }

        if (expresion.startsWith("(")) {
            return "nil";
        }

        return "T";
    }

    public String isList(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Error: LIST necesita un argumento.");
        }

        String expresion = tokens.pop();

        if (expresion.equals("'")) {
            expresion = tokens.pop();
        }

        return expresion.startsWith("(") ? "T" : "nil";
    }

    public String isEqual(Stack<String> tokens) {
        if (tokens.size() < 2) {
            throw new IllegalArgumentException("Error: EQUAL necesita dos argumentos.");
        }

        String expresion2 = obtenerValor(tokens.pop());
        String expresion1 = obtenerValor(tokens.pop());

        if (expresion1.equals("'")) {
            expresion1 = tokens.pop();
        }
        if (expresion2.equals("'")) {
            expresion2 = tokens.pop();
        }

        return expresion1.equals(expresion2) ? "T" : "nil";
    }

    public String isLessThan(Stack<String> tokens) {
        if (tokens.size() < 2) {
            throw new IllegalArgumentException("Error: < necesita dos argumentos.");
        }

        String expresion2 = obtenerValor(tokens.pop());
        String expresion1 = obtenerValor(tokens.pop());

        if (expresion1.equals("'")) {
            expresion1 = tokens.pop();
        }
        if (expresion2.equals("'")) {
            expresion2 = tokens.pop();
        }

        try {
            double num1 = Double.parseDouble(expresion1);
            double num2 = Double.parseDouble(expresion2);
            return num2 < num1 ? "T" : "nil";
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: < solo acepta valores numericos.");
        }
    }

    public String isGreaterThan(Stack<String> tokens) {
        if (tokens.size() < 2) {
            throw new IllegalArgumentException("Error: > necesita dos argumentos.");
        }

        String expresion2 = obtenerValor(tokens.pop());
        String expresion1 = obtenerValor(tokens.pop());

        if (expresion1.equals("'")) {
            expresion1 = tokens.pop();
        }
        if (expresion2.equals("'")) {
            expresion2 = tokens.pop();
        }

        try {
            double num1 = Double.parseDouble(expresion1);
            double num2 = Double.parseDouble(expresion2);
            return num2 > num1 ? "T" : "nil";
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: > solo acepta valores numericos.");
        }
        
    }
    public String obtenerValor(String token) {
       
        Object valorVariable = variableManager.getVariable(token);
        if (valorVariable != null) {
            return valorVariable.toString(); 
        }

        
        return token;
    }
}