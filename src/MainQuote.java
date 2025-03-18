import java.util.Stack;

///Prueba para mostrar el tokenizador con
///  el document controler aparte
public class MainQuote {
    public static void main(String[] args) {
        String filePath = "Documento.lisp"; 
        DocumentControler controller = new DocumentControler(filePath);

        try {
            Stack<String> tokens = controller.processLispCode(); 
            System.out.println("Tokens generados:");

            
            String previousToken = null;

            while (!tokens.isEmpty()) {
                String token = tokens.pop();
                System.out.println("Token: " + token);

                
                if (token.equals("'") || token.equals("quote")) {
                    if (previousToken != null) {
                        
                        try {
                            Object result = QuoteEvaluator.evaluateQuote(previousToken);
                            System.out.println("Resultado de ' (quote): " + result);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Error en el quote: " + e.getMessage());
                        }
                    }
                }

                
                previousToken = token;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}