import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        String filePath = "Documento.lisp"; 
        DocumentControler controller = new DocumentControler(filePath);

        try {
            Stack<String> tokens = controller.processLispCode(); 
            System.out.println("Tokens generados:");
            while (!tokens.isEmpty()) {
                System.out.println(tokens.pop()); 
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}