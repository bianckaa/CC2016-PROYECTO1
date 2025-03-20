public class Main {
    public static void main(String[] args) {
        String filePath = "Documento.lisp";
        Interprete interprete = new Interprete(filePath);

        interprete.interpretar();
    }
}
