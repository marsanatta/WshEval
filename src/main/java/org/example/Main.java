package org.example;
import tbl.eval.lexer.Lexer;
import tbl.eval.parser.Parser;

public class Main {
    public static void main(String[] args) {
        try {
            String text = "b = (db+cd) * 5556635 % 3 + 1234 - 567.023 + e++ - --e";
            Lexer lexer = new Lexer(text);
            /*Token cur = lexer.getToken();
            while (cur.getType() != TokenType.EOF) {
                System.out.println(cur);
                cur = lexer.getToken();
            }*/
            Parser parser = new Parser(lexer);
            parser.parse();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}