import java.util.Stack;

public class Interprete {
    private DocumentControler controller;
    private VariableManagement<Object> variableManager;
    private PredicateEvaluator predicateEvaluator;

    public Interprete(String filePath) {
        controller = new DocumentControler(filePath);
        variableManager = new VariableManagement<>();
        predicateEvaluator = new PredicateEvaluator();
    }

    public void interpretar() {
        try {
            Stack<String> tokens = controller.processLispCode();
            Stack<String> orderedTokens = new Stack<>();

            while (!tokens.isEmpty()) {
                orderedTokens.push(tokens.pop());
            }

            while (!orderedTokens.isEmpty()) {
                String token = orderedTokens.pop();
                System.out.println("Token: " + token);

                if (token.equals("'") || token.equals("quote")) {
                    try {
                        if (!orderedTokens.isEmpty()) {
                            String previousToken = orderedTokens.pop();
                            Object result = QuoteEvaluator.evaluateQuote(previousToken);
                            System.out.println("Resultado de ' (quote): " + result);
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error en el quote: " + e.getMessage());
                    }
                }

                else if (token.equals("setq")) {
                    try {
                        SetqEvaluator.evaluateSetq(orderedTokens, variableManager);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error en setq: " + e.getMessage());
                    }
                }

                else if (esPredicado(token)) {
                    try {
                        Object result = predicateEvaluator.evaluarPredicado(token, orderedTokens);
                        System.out.println("Resultado del predicado " + token + ": " + result);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error en el predicado " + token + ": " + e.getMessage());
                    }
                }

                // Otras implementaciones (Defun, COND, etc.)
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private boolean esPredicado(String token) {
        return token.equals("ATOM") || token.equals("LIST") || token.equals("EQUAL") || 
               token.equals("<") || token.equals(">");
    }
}