package src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class MainTest2 {

    public static void main(String[] args) {

        String filePath = "Documento.Lisp";

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n"); // Conservar saltos de línea
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

       
        Tokenizador2 tokenizador = new Tokenizador2();
        Stack<String> tokens = tokenizador.tokenize(content.toString());

        
        System.out.println("Tokens encontrados:");
        while (!tokens.isEmpty()) {
            System.out.println(tokens.pop());
        }
    }
}