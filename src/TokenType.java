// Token types enumeration
enum TokenType {
    // Literals
    NUMBER,
    IDENTIFIER,
    STRING,

    // Keywords
    IF, ELSE, WHILE, FOR, INT, FLOAT, RETURN, VOID,

    // Operators
    PLUS, MINUS, MULTIPLY, DIVIDE, ASSIGN,
    EQUALS, NOT_EQUALS, LESS_THAN, GREATER_THAN,
    LESS_EQUAL, GREATER_EQUAL,

    // Delimiters
    SEMICOLON, COMMA, LEFT_PAREN, RIGHT_PAREN,
    LEFT_BRACE, RIGHT_BRACE,

    // Special
    EOF, UNKNOWN
}
