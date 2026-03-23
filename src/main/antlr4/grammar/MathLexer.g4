lexer grammar MathLexer;

INTEGRAL_MATH
    : 'calka' | 'int' | 'integral';
FROM_NATURAL
    : 'od' | 'from';
TO_NATURAL
    : 'do' | 'to' ;
OVER_NATURAL
    : 'po' | 'over';

GREEK
    // small
    : 'alfa' | 'alpha' | 'beta' | 'gamma' | 'delta' | 'epsilon' | 'varepsilon'
    | 'zeta' | 'eta' | 'theta' | 'vartheta' | 'iota' | 'kappa' | 'kapa' | 'lambda'
    | 'mu' | 'nu' | 'xi' | 'omicron' | 'pi' | 'varpi' | 'rho' | 'varrho'
    | 'sigma' | 'varsigma' | 'tau' | 'upsilon' | 'phi' | 'fi' | 'varphi' | 'chi'
    | 'psi' | 'omega'

    // Big
    | 'Gamma' | 'Delta' | 'Theta' | 'Lambda' | 'Xi' | 'Pi' | 'Sigma'
    | 'Upsilon' | 'Phi' | 'Fi' | 'Psi' | 'Omega'
    ;

INFINITY : 'nieskonczonosc' | 'infty' | 'infinity' | 'oo' | 'inf';
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