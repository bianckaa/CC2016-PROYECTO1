import java.util.Map;
import java.util.Stack;

public class Interprete {
    private DocumentControler controller;
    private VariableManagement<Object> variableManager;
    private PredicateEvaluator predicateEvaluator;
    private CondEvaluator condEvaluator;

    public Interprete(String filePath) {
        controller = new DocumentControler(filePath);
        variableManager = new VariableManagement<>();
        predicateEvaluator = new PredicateEvaluator(variableManager);
        this.condEvaluator = new CondEvaluator(variableManager);
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
                //System.out.println("Token: " + token);

                if (token.equals("'") || token.equalsIgnoreCase("quote")) {
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

                else if (token.equalsIgnoreCase("setq")) {
                    try {
                        SetqEvaluator setqEvaluator = new SetqEvaluator(variableManager);  
                        setqEvaluator.evaluateSetq(orderedTokens);  
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error en setq: " + e.getMessage());
                    }
                }
                

                else if (token.equals("defun")) {
                    try {
                        DefunEvaluator.evaluateDefun(orderedTokens, variableManager);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error en defun: " + e.getMessage());
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

                else if (token.equalsIgnoreCase("cond")) {
                    String resultado = condEvaluator.evaluarCOND(orderedTokens);
                    System.out.println("Resultado de COND: " + resultado);
                }

                else if (variableManager.getVariable(token) != null) {
                    
                    Map<String, Object> funcion = (Map<String, Object>) variableManager.getVariable(token);
                    Stack<String> parametros = (Stack<String>) funcion.get("parametros");
                    Stack<String> cuerpo = (Stack<String>) funcion.get("cuerpo");
                
                    for (String parametro : parametros) {
                        if (orderedTokens.isEmpty()) {
                            throw new IllegalArgumentException("Error: Faltan argumentos para la función " + token);
                        }
                        String valor = orderedTokens.pop();
                        variableManager.setVariable(parametro, valor);
                    }                        
                    double resultado = DefunEvaluator.evaluarExpresionAritmetica(cuerpo, variableManager);
                    System.out.println("Resultado de la función " + token + ": " + resultado);
                }
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