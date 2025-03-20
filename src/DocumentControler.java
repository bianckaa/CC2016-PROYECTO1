/**
 * Clase encargada de controlar la lectura y el procesamiento de documentos.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * Clase que permite leer documentos y procesar código Lisp.
 */
public class DocumentControler {
    private String path;

    /**
     * Constructor de la clase DocumentControler.
     * 
     * @param path Ruta del archivo a leer.
     */
    public DocumentControler(String path) {
        this.path = path;
    }

    /**
     * Lee el contenido de un documento y lo devuelve como una cadena de texto.
     * 
     * @return Contenido del archivo en formato de cadena.
     * @throws RuntimeException Si ocurre un error al leer el archivo.
     */
    public String ReadDocument() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(" \n ");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }
        return content.toString();
    }

    /**
     * Procesa el código Lisp contenido en el documento.
     * 
     * @return Pila de cadenas con los tokens del código Lisp.
     */
    public Stack<String> processLispCode() {
        String code = ReadDocument();
        StructureValidator.validateSyntax(code);
        Tokenizador2 tokenizer = new Tokenizador2();
        return tokenizer.tokenize(code);
    }
}
