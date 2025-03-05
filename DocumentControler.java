import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;


public class DocumentControler{

    private String path;

    public DocumentControler(String path){
        this.path = path;
    
    }

    public String ReadDocument(){ //Lee el archivo de Lisp y regresa de forma de cadena su contenido
        StringBuilder content = new StringBuilder(this.path);
        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine())!= null) {
                content.append(line).append(" \n ");

            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }
        return content.toString();
    }

    public Stack<String> processCodeLisp() {
        String code = ReadDocument();
        StructureValidator.validateSyntax(code);
        Tokenizer tokenizer = new Tokenizer();
        return tokenizer.tokenize(code);
    }


}