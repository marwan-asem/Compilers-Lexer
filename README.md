# C Lexical Analyzer (Lexer)

A Java-based lexical analyzer that tokenizes C source code files. This project implements a complete lexer that can identify and categorize various tokens in C programming language syntax.

## � Quick Start

```bash
# Clone the repository
git clone git@github.com:marwan-asem/Compilers-Lexer.git
cd Compilers-Lexer

# Compile the project
javac -d out src/*.java

# Run with the sample file
java -cp out Main test.c
```

**Output**: The lexical analysis results will be saved to `output.txt`

## �📋 Table of Contents

- [Quick Start](#quick-start)
- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Token Types](#token-types)
- [Examples](#examples)
- [Development](#development)
- [Contributing](#contributing)

## 🔍 Overview

This lexical analyzer is the first phase of a compiler pipeline. It takes C source code as input and breaks it down into a sequence of tokens (lexemes) that can be processed by subsequent phases like parsing and semantic analysis.

## ✨ Features

- **Complete C Token Recognition**: Identifies keywords, identifiers, operators, literals, and delimiters
- **File I/O Support**: Reads from input files and writes results to output files
- **Position Tracking**: Tracks line and column numbers for each token
- **Error Handling**: Provides meaningful error messages for file operations
- **Command Line Interface**: Supports custom input files via command line arguments
- **Clean Output**: Generates well-formatted token analysis in output files

## 📁 Project Structure

```
lexer/
├── README.md                   # Project documentation
├── src/                        # Source code directory
│   ├── Main.java              # Main application entry point
│   ├── Scanner.java           # Core lexical analyzer
│   ├── Token.java             # Token data structure
│   └── TokenType.java         # Token type enumeration
├── out/                       # Compiled Java classes
│   ├── Main.class
│   ├── Scanner.class
│   ├── Token.class
│   └── TokenType.class
├── test.c                     # Sample C source file
└── output.txt                 # Generated token analysis output
```

### 📄 File Descriptions

#### Core Components

- **`Main.java`**: Application entry point that handles file I/O and coordinates the lexical analysis process
- **`Scanner.java`**: The main lexical analyzer that implements the tokenization logic
- **`Token.java`**: Data structure representing individual tokens with type, value, and position information
- **`TokenType.java`**: Enumeration defining all supported token types

#### Input/Output Files

- **`test.c`**: Default sample C source file for testing
- **`output.txt`**: Generated file containing the complete lexical analysis results

## 🚀 Installation

### Prerequisites

- **Java Development Kit (JDK) 11 or higher**
- **Command line access** (PowerShell, Command Prompt, or Terminal)

### Setup Steps

1. **Clone the project**:
   ```bash
   # Using SSH (recommended if you have SSH keys set up)
   git clone git@github.com:marwan-asem/Compilers-Lexer.git
   cd Compilers-Lexer
   ```
   
   Or using HTTPS:
   ```bash
   # Using HTTPS
   git clone https://github.com/marwan-asem/Compilers-Lexer.git
   cd Compilers-Lexer
   ```

2. **Compile the Java source files**:
   ```powershell
   javac -d out src/*.java
   ```

3. **Verify compilation**:
   ```powershell
   dir out\
   # Should show: Main.class, Scanner.class, Token.class, TokenType.class
   ```

## 💻 Usage

### Basic Usage (Default Input File)

```powershell
# Navigate to project directory
cd "path/to/lexer"

# Compile (if not already done)
javac -d out src/*.java

# Run with default input file (test.c)
java -cp out Main
```

### Custom Input File

```powershell
# Run with a specific C source file
java -cp out Main "myprogram.c"
java -cp out Main "algorithm.c"
java -cp out Main "functions.c"
```

### Expected Output

After running the lexer, you'll see:
```
Lexical analysis completed. Output written to: output.txt
```

The detailed analysis will be saved in `output.txt` containing:
- Source file information
- Complete source code
- List of all identified tokens with their properties

## 🏷️ Token Types

The lexer recognizes the following categories of tokens:

### Keywords
- **Data Types**: `int`, `float`, `void`
- **Control Flow**: `if`, `else`, `while`, `for`, `return`

### Operators
- **Arithmetic**: `+`, `-`, `*`, `/`
- **Assignment**: `=`
- **Comparison**: `==`, `!=`, `<`, `>`, `<=`, `>=`

### Delimiters
- **Parentheses**: `(`, `)`
- **Braces**: `{`, `}`
- **Punctuation**: `;`, `,`

### Literals
- **Numbers**: Integer and floating-point literals (`42`, `3.14`)
- **Strings**: String literals (`"Hello World"`)

### Identifiers
- Variable names, function names, and other user-defined identifiers

### Special
- **EOF**: End-of-file marker
- **UNKNOWN**: Unrecognized characters

## 📝 Examples

### Sample Input (`test.c`)
```c
int main() {
    int x = 42;
    float y = 3.14;
    char* name = "Hello World";
    
    if (x > 0) {
        printf("x is positive: %d\n", x);
        return x + y;
    } else {
        printf("x is not positive\n");
    }
    
    for (int i = 0; i < 10; i++) {
        printf("Count: %d\n", i);
    }
    
    return 0;
}
```

### Sample Output (`output.txt`)
```
Reading from file: test.c
Source code:
===================================================
int main() {
    int x = 42;
    float y = 3.14;
    char* name = "Hello World";
    
    if (x > 0) {
        printf("x is positive: %d\n", x);
        return x + y;
    } else {
        printf("x is not positive\n");
    }
    
    for (int i = 0; i < 10; i++) {
        printf("Count: %d\n", i);
    }
    
    return 0;
}
===================================================

Tokens:
Token{type=INT, value='int', line=1, col=1}
Token{type=IDENTIFIER, value='main', line=1, col=5}
Token{type=LEFT_PAREN, value='(', line=1, col=9}
Token{type=RIGHT_PAREN, value=')', line=1, col=10}
Token{type=LEFT_BRACE, value='{', line=1, col=12}
Token{type=INT, value='int', line=2, col=6}
Token{type=IDENTIFIER, value='x', line=2, col=10}
Token{type=ASSIGN, value='=', line=2, col=12}
Token{type=NUMBER, value='42', line=2, col=14}
...
Token{type=EOF, value='', line=18, col=2}
```

## 🛠️ Development

### Project Architecture

The lexer follows a modular design:

1. **`Main`** class handles:
   - Command line argument processing
   - File input/output operations
   - Error handling and user feedback

2. **`Scanner`** class implements:
   - Character-by-character source code analysis
   - Token recognition algorithms
   - Position tracking (line/column numbers)

3. **`Token`** class provides:
   - Token data encapsulation
   - String representation for output formatting

4. **`TokenType`** enum defines:
   - All supported token categories
   - Type safety for token classification

### Extending the Lexer

To add support for new tokens:

1. **Add new token types** to `TokenType.java`
2. **Update recognition logic** in `Scanner.java`
3. **Test with sample code** containing the new tokens

### Building and Testing

```powershell
# Clean build
rm -r out/
mkdir out
javac -d out src/*.java

# Test with various input files
java -cp out Main "test.c"
java -cp out Main "complex_program.c"
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-token-support`)
3. Make your changes
4. Test thoroughly with various C source files
5. Commit your changes (`git commit -am 'Add support for new tokens'`)
6. Push to the branch (`git push origin feature/new-token-support`)
7. Create a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🐛 Troubleshooting

### Common Issues

1. **"Error reading file"**: Ensure the input file exists in the project directory
2. **"Cannot find Main class"**: Make sure classes are compiled to the `out` directory
3. **"Permission denied"**: Check file permissions for input files and output directory

### Getting Help

If you encounter issues:
1. Check the error messages in the terminal
2. Verify file paths and permissions
3. Ensure Java is properly installed (`java -version`)
4. Review the sample input/output for expected format

---

**Note**: This lexer is designed for educational purposes and implements a subset of C language tokens. For production use, consider extending it to handle additional C language features and edge cases.
