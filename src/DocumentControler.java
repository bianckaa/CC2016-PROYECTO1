import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class DocumentControler{
    private String path;

    public DocumentControler(String path){
        this.path = path;
    }

    public String ReadDocument(){ 
        StringBuilder content = new StringBuilder();
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

    public Stack<String> processLispCode() {
        String code = ReadDocument(); 
        StructureValidator.validateSyntax(code); 
        Tokenizador2 tokenizer = new Tokenizador2(); 
        return tokenizer.tokenize(code); 
    }

   

}