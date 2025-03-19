import java.util.Stack;

<<<<<<<< HEAD:src (unused)/EvaluadorPre.java
public class EvaluadorPre {
    private PredicateEvaluator predicateEvaluator;
    private VariableManagement<Object> variableManager;
   
========
public class PredicateProcessor {
>>>>>>>> 8e1086d79b1b55b4384956d6e7166941e8d4054a:src/PredicateProcessor.java

    public EvaluadorPre(VariableManagement<Object> variableManager) {
        this.variableManager = variableManager;
        this.predicateEvaluator = new PredicateEvaluator();
    }
<<<<<<<< HEAD:src (unused)/EvaluadorPre.java
    public Object evaluarPredicado(String predicado, Stack<String> tokens) {
========

    private Object evaluarTokens(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("No hay tokens para evaluar.");
        }

        String token = tokens.pop();

        if (token.equals("(")) {
            
            return evaluarExpresionAnidada(tokens);
        } else if (token.equals(")")) {
           
            return evaluarTokens(tokens);
        } else if (token.equals("ATOM") || token.equals("LIST") || token.equals("EQUAL") || token.equals("<") || token.equals(">")) {
            
            return evaluarPredicado(token, tokens);
        } else {
            
            return token;
        }
    }

    private Object evaluarExpresionAnidada(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expresion incompleta.");
        }
        String predicado = tokens.pop();
        return evaluarPredicado(predicado, tokens);
    }

    private Object evaluarPredicado(String predicado, Stack<String> tokens) {
        
>>>>>>>> 8e1086d79b1b55b4384956d6e7166941e8d4054a:src/PredicateProcessor.java
        switch (predicado) {
            case "ATOM":
                return predicateEvaluator.isAtom(tokens);
            case "LIST":
                return predicateEvaluator.isList(tokens);
            case "EQUAL":
                return predicateEvaluator.isEqual(tokens);
            case "<":
                return predicateEvaluator.isLessThan(tokens);
            case ">":
                return predicateEvaluator.isGreaterThan(tokens);
            default:
                throw new IllegalArgumentException("Predicado no válido: " + predicado);
        }
    }

    public Object evaluar(Stack<String> tokens)  {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("No hay tokens para evaluar.");
        }

        String token = tokens.pop();

        if (token.equals("(")) {
            return evaluarExpresionAnidada(tokens);
        } else if (token.equals(")")) {
            return evaluar(tokens);
        } else if (token.equals("ATOM") || token.equals("LIST") || token.equals("EQUAL") || 
                   token.equals("<") || token.equals(">")) {
            return evaluarPredicado(token, tokens);
        } else {
           
            Object value = variableManager.getVariable(token);
            if (value != null) {
                return value; 
            }
            
            return token;
        }
    }

    private Object evaluarExpresionAnidada(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expresión incompleta.");
        }

        String predicado = tokens.pop();
        return evaluarPredicado(predicado, tokens);
    }
}