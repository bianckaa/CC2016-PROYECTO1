import java.util.Stack;

public class PredicateProcessor {

    private PredicateEvaluator predicateEvaluator = new PredicateEvaluator();

    public Object evaluar(Stack<String> tokens) {
        
        Stack<String> tokensInvertidos = new Stack<>();
        while (!tokens.isEmpty()) {
            tokensInvertidos.push(tokens.pop());
        }

        return evaluarTokens(tokensInvertidos);
    }

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
}