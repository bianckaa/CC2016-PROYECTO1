/**
 * Clase principal que inicia la ejecución del intérprete de Lisp.
 */
public class Main {
    /**
     * Método principal que crea una instancia del intérprete y procesa el archivo Lisp.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        String filePath = "Documento.lisp";
        Interprete interprete = new Interprete(filePath);

        interprete.interpretar();
    }
}
