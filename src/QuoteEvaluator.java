
public class QuoteEvaluator {
    
   
    public static Object evaluateQuote(String token) {
        
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Error: Se esperaba un valor después de ' (quote).");
        }

        
        return token;
    }
}
