import java.util.Stack;

public class MainPre {
    public static void main(String[] args) {
        String filePath = "Documento.lisp";
        DocumentControler controller = new DocumentControler(filePath);

        try {
            Stack<String> tokens = controller.processLispCode();

            System.out.println("Tokens generados:");
            Stack<String> tokensCopia = new Stack<>();
            tokensCopia.addAll(tokens);
            while (!tokensCopia.isEmpty()) {
                System.out.println(tokensCopia.pop());
            }

            EvaluadorPre evaluador = new EvaluadorPre();
            Object resultado = evaluador.evaluar(tokens);

            System.out.println("Resultado de la evaluacion: " + resultado);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}