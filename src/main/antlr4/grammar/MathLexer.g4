lexer grammar MathLexer;


// Operatory - KOLEJNOŚĆ MA ZNACZENIE
DIV_BLOCK : '//' ;  // To będzie nasz \frac{}{}
DIV_LINE  : '/' ;   // To zostanie jako zwykły slash /

PLUS      : '+' ;
MINUS     : '-' ;
MUL       : '*' ;
HAT       : '^' ;
L_BRACKET : '(' ;
R_BRACKET : ')';

// Grupowanie
L_CURLY   : '{' ;
R_CURLY   : '}' ;

// Dane
ID        : [a-zA-Z]+ ;
INT       : [0-9]+ ;
WS        : [ \t\r\n]+ -> skip ;