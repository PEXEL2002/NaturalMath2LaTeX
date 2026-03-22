parser grammar MathParser;

options { tokenVocab=MathLexer; }

program : expression EOF ;

expression
    : (L_CURLY | L_BRACKET) expression (R_CURLY |  R_BRACKET) # Grouping
    | left=expression HAT right=expression         # Power
    | left=expression right=expression             # ImplicitMul // TA LINIA TO KLUCZ
    | left=expression (MUL | DIV_BLOCK | DIV_LINE) right=expression # MultDiv
    | left=expression (PLUS | MINUS) right=expression # AddSub
    | ID                                           # Variable
    | GREEK                                        # Greek
    | INT                                          # Constant
    ;