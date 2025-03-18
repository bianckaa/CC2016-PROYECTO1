import java.util.Stack;


public class EvaluadorPre{

    private PredicateEvaluator predicateEvaluator = new PredicateEvaluator();

    
    public Object evaluar(Stack<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("No hay tokens para evaluar.");
        }

        String token = tokens.pop();

        switch (token) {
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
            case "(":
                
                return evaluarExpresionAnidada(tokens);
            case ")":
                
                return null;
            default:
                
                return token;
        }
    }

    
    private Object evaluarExpresionAnidada(Stack<String> tokens) {
        
        Object resultado = null;
        while (!tokens.isEmpty() && !tokens.peek().equals(")")) {
            resultado = evaluar(tokens);
        }
        if (!tokens.isEmpty() && tokens.peek().equals(")")) {
            tokens.pop(); 
        }
        return resultado;
    }
}