import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String fileName = "test.c"; // Default file
        
        // Check if a filename was provided as command line argument
        if (args.length > 0) {
            fileName = args[0];
        }
        
        try {
            // Read the source code from file
            String sourceCode = Files.readString(Paths.get(fileName));
            
            // Create output file
            String outputFileName = "output.txt";
            PrintWriter writer = new PrintWriter(outputFileName);
            
            // Write only to file (no console output)
            String header1 = "Reading from file: " + fileName;
            String header2 = "Source code:";
            String separator = "=" + "=".repeat(50);
            
            writer.println(header1);
            writer.println(header2);
            writer.println(separator);
            writer.println(sourceCode);
            writer.println(separator);
            writer.println();
            
            Scanner scanner = new Scanner(sourceCode);
            java.util.List<Token> tokens = scanner.scanTokens();

            String tokensHeader = "Tokens:";
            writer.println(tokensHeader);
            
            for (Token token : tokens) {
                writer.println(token.toString());
            }
            
            writer.close();
            System.out.println("Lexical analysis completed. Output written to: " + outputFileName);
            
        } catch (IOException e) {
            System.err.println("Error reading file '" + fileName + "': " + e.getMessage());
            System.err.println("Make sure the file exists in the project directory.");
            System.exit(1);
        }
    }
}
