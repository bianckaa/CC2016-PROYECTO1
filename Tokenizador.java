import java.util.Stack;
import java.util.StringTokenizer;

public class Tokenizador {

    public Stack<String> tokenize(String code) {
        code = code.replace("(", " ( ").replace(")", " ) ")
                   .replace("<=", " <= ").replace("-", " - ")
                   .replace("*", " * ");

        StringTokenizer tokenizer = new StringTokenizer(code);
        Stack<String> tokens = new Stack<>();
        while (tokenizer.hasMoreTokens()) {

            tokens.push(tokenizer.nextToken());
        }
        return tokens;
    }
}