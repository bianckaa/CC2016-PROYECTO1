import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class DocumentControler<T>{

    private String path;

    public DocumentControler(String path){
        this.path = path;
    
    }

    public String ReadDocument(String Path){ //Lee el archivo de Lisp y regresa de forma de cadena su contenido
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

}