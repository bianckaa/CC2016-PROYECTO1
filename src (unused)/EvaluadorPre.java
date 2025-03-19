import java.util.Stack;

public class EvaluadorPre {
    private PredicateEvaluator predicateEvaluator;
    private VariableManagement<Object> variableManager;
   

    public EvaluadorPre(VariableManagement<Object> variableManager) {
        this.variableManager = variableManager;
        this.predicateEvaluator = new PredicateEvaluator();
    }
    public Object evaluarPredicado(String predicado, Stack<String> tokens) {
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