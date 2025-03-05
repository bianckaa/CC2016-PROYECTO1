import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Ivana\\Desktop\\Estructuras\\Proyecto 1\\CC2016-PROYECTO1\\Documento.lisp"; // Cambia esto por la ruta de tu archivo
        DocumentControler controller = new DocumentControler(filePath);

        try {
            Stack<String> tokens = controller.processCodeLisp(); // Obtener los tokens en una pila
            System.out.println("Tokens generados:");
            while (!tokens.isEmpty()) {
                System.out.println(tokens.pop()); // Imprimir los tokens (en orden inverso)
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}