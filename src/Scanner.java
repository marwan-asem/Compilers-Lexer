public class Scanner {
    private String source;
    private int current = 0;
    private int line = 1;
    private int column = 1;

    public Scanner(String source) {
        this.source = source;
    }

    // Main scanning method - returns list of all tokens
    public java.util.List<Token> scanTokens() {
        java.util.List<Token> tokens = new java.util.ArrayList<>();

        while (!isAtEnd()) {
            Token token = scanToken();
            if (token != null) {
                tokens.add(token);
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }

    // Scan a single token
    private Token scanToken() {
        skipWhitespace();

        if (isAtEnd()) return null;

        int startLine = line;
        int startColumn = column;
        char c = advance();

        // Single character tokens
        switch (c) {
            case '(': return new Token(TokenType.LEFT_PAREN, "(", startLine, startColumn);
            case ')': return new Token(TokenType.RIGHT_PAREN, ")", startLine, startColumn);
            case '{': return new Token(TokenType.LEFT_BRACE, "{", startLine, startColumn);
            case '}': return new Token(TokenType.RIGHT_BRACE, "}", startLine, startColumn);
            case ',': return new Token(TokenType.COMMA, ",", startLine, startColumn);
            case ';': return new Token(TokenType.SEMICOLON, ";", startLine, startColumn);
            case '+': return new Token(TokenType.PLUS, "+", startLine, startColumn);
            case '-': return new Token(TokenType.MINUS, "-", startLine, startColumn);
            case '*': return new Token(TokenType.MULTIPLY, "*", startLine, startColumn);
            case '/': 
                // Check for comments
                if (peek() == '/') {
                    // Single-line comment: skip until end of line
                    skipSingleLineComment();
                    return null; // Return null to indicate no token should be added
                } else if (peek() == '*') {
                    // Multi-line comment: skip until */
                    skipMultiLineComment();
                    return null; // Return null to indicate no token should be added
                } else {
                    // Regular division operator
                    return new Token(TokenType.DIVIDE, "/", startLine, startColumn);
                }
        }

        // Two character tokens
        if (c == '=') {
            if (match('=')) {
                return new Token(TokenType.EQUALS, "==", startLine, startColumn);
            }
            return new Token(TokenType.ASSIGN, "=", startLine, startColumn);
        }

        if (c == '!') {
            if (match('=')) {
                return new Token(TokenType.NOT_EQUALS, "!=", startLine, startColumn);
            }
            return new Token(TokenType.UNKNOWN, "!", startLine, startColumn);
        }

        if (c == '<') {
            if (match('=')) {
                return new Token(TokenType.LESS_EQUAL, "<=", startLine, startColumn);
            }
            return new Token(TokenType.LESS_THAN, "<", startLine, startColumn);
        }

        if (c == '>') {
            if (match('=')) {
                return new Token(TokenType.GREATER_EQUAL, ">=", startLine, startColumn);
            }
            return new Token(TokenType.GREATER_THAN, ">", startLine, startColumn);
        }

        // String literals
        if (c == '"') {
            return scanString(startLine, startColumn);
        }

        // Numbers
        if (isDigit(c)) {
            return scanNumber(startLine, startColumn);
        }

        // Identifiers and keywords
        if (isAlpha(c)) {
            return scanIdentifier(startLine, startColumn);
        }

        // Unknown character
        return new Token(TokenType.UNKNOWN, String.valueOf(c), startLine, startColumn);
    }

    // Scan string literal
    private Token scanString(int startLine, int startColumn) {
        StringBuilder value = new StringBuilder();

        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
            value.append(advance());
        }

        if (isAtEnd()) {
            System.err.println("Unterminated string at line " + startLine);
            return new Token(TokenType.UNKNOWN, value.toString(), startLine, startColumn);
        }

        // Consume closing quote
        advance();

        return new Token(TokenType.STRING, value.toString(), startLine, startColumn);
    }

    // Scan number (integer or float)
    private Token scanNumber(int startLine, int startColumn) {
        StringBuilder value = new StringBuilder();

        // Add the first digit we already consumed
        value.append(source.charAt(current - 1));

        // Consume remaining digits
        while (isDigit(peek())) {
            value.append(advance());
        }

        // Check for decimal point
        if (peek() == '.' && isDigit(peekNext())) {
            value.append(advance()); // consume '.'

            while (isDigit(peek())) {
                value.append(advance());
            }
        }

        return new Token(TokenType.NUMBER, value.toString(), startLine, startColumn);
    }

    // Scan identifier or keyword
    private Token scanIdentifier(int startLine, int startColumn) {
        StringBuilder value = new StringBuilder();

        // Add the first character we already consumed
        value.append(source.charAt(current - 1));

        // Consume remaining alphanumeric characters
        while (isAlphaNumeric(peek())) {
            value.append(advance());
        }

        String text = value.toString();
        TokenType type = getKeywordType(text);

        return new Token(type, text, startLine, startColumn);
    }

    // Check if string is a keyword
    private TokenType getKeywordType(String text) {
        switch (text) {
            case "if": return TokenType.IF;
            case "else": return TokenType.ELSE;
            case "while": return TokenType.WHILE;
            case "for": return TokenType.FOR;
            case "int": return TokenType.INT;
            case "float": return TokenType.FLOAT;
            case "return": return TokenType.RETURN;
            case "void": return TokenType.VOID;
            default: return TokenType.IDENTIFIER;
        }
    }

    // Helper methods
    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        column++;
        return source.charAt(current++);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        column++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void skipWhitespace() {
        while (true) {
            char c = peek();
            if (c == ' ' || c == '\r' || c == '\t') {
                advance();
            } else if (c == '\n') {
                line++;
                column = 1;
                advance();
            } else {
                break;
            }
        }
    }

    // Skip single-line comment (// until end of line)
    private void skipSingleLineComment() {
        // Consume the second '/'
        advance();
        
        // Skip everything until newline or end of file
        while (peek() != '\n' && !isAtEnd()) {
            advance();
        }
    }

    // Skip multi-line comment (/* until */)
    private void skipMultiLineComment() {
        // Consume the '*'
        advance();
        
        // Skip everything until we find */
        while (!isAtEnd()) {
            if (peek() == '*') {
                advance();
                if (peek() == '/') {
                    advance(); // Consume the closing '/'
                    break;
                }
            } else if (peek() == '\n') {
                line++;
                column = 1;
                advance();
            } else {
                advance();
            }
        }
    }
}