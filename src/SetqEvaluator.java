import java.util.Stack;

public class SetqEvaluator {
    public static void evaluateSetq(Stack<String> tokens, VariableManagement<Object> variableManager) {
        if (tokens.size() < 3) {
            throw new IllegalArgumentException("Error: setq requiere al menos dos argumentos.");
        }
    
        String varName = tokens.pop();
        String value = tokens.pop();
    
        variableManager.setVariable(varName, value);
        System.out.println("Variable " + varName + " asignada a " + value);
    }
}
