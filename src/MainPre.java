import java.util.Stack;


public class MainPre {
    public static void main(String[] args) {
        
        String filePath = "src/Documento.lisp";

        
        DocumentControler controller = new DocumentControler(filePath);

        try {
           
            Stack<String> tokens = controller.processLispCode();
            

            
            System.out.println("Tokens generados:");
            Stack<String> tokensCopia = new Stack<>();
            tokensCopia.addAll(tokens); // Copiamos los tokens para no perderlos.
            while (!tokensCopia.isEmpty()) {
                System.out.println(tokensCopia.pop());
            }

            // Evaluar el código Lisp.
            EvaluadorPre evaluador = new EvaluadorPre();
            Object resultado = evaluador.evaluar(tokens);

            // Mostrar el resultado de la evaluación.
            System.out.println("Resultado de la evaluación: " + resultado);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}