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
            String token = tokenizer.nextToken().trim();
            
            if (token.equals("'") || token.equals("quote")) {
                
                tokens.push(token);
                
                
                StringBuilder quotedExpression = new StringBuilder();
                int parenthesesBalance = 1; 
                quotedExpression.append(" "); 
                
                while (tokenizer.hasMoreTokens() && parenthesesBalance > 0) {
                    token = tokenizer.nextToken().trim();
                    quotedExpression.append(token).append(" ");
                    
                    
                    if (token.equals("(")) {
                        parenthesesBalance++;
                    } else if (token.equals(")")) {
                        parenthesesBalance--;
                    }
                }
                
                tokens.push(quotedExpression.toString().trim());
            } else {
                
                tokens.push(token);
            }
        }

        return tokens;
    }
}