import java.util.Stack;

public class MainSetq {
    public static void main(String[] args) {
        String filePath = "Documento.lisp"; 
        DocumentControler controller = new DocumentControler(filePath);

        try {
            Stack<String> tokens = controller.processLispCode();
            Stack<String> orderedTokens = new Stack<>();

            while (!tokens.isEmpty()) {
                orderedTokens.push(tokens.pop());
            }
            VariableManagement<Object> variableManager = new VariableManagement<>();

            while (!orderedTokens.isEmpty()) {
                String token = orderedTokens.pop();
                System.out.println("Token: " + token);

                if (token.equals("setq")) {
                    try {
                        SetqEvaluator.evaluateSetq(orderedTokens, variableManager);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error en setq: " + e.getMessage());
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
