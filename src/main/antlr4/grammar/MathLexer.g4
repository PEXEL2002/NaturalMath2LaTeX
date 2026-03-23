lexer grammar MathLexer;

INTEGRAL_MATH
    : 'calka' | 'int' | 'integral';
FROM_NATURAL
    : 'od' | 'from';
TO_NATURAL
    : 'do' | 'to' ;
OVER_NATURAL
    : 'po' | 'over';

LOGIC_AND     : 'i' | 'and' | 'oraz' | '&&' ;
LOGIC_OR      : 'lub' | 'or' | 'albo' | '||' ;
LOGIC_IMPLIES : '=>' | 'implikuje' | 'wynika' | 'zatem' ;
LOGIC_IFF     : '<=>' | 'wtw' | 'wtedyitylkowtedy' | 'rownowaznie' ;
LOGIC_NOT     : 'nie' | 'not' | '~' ;

TRIGONOMETRIC
    : 'sin' | 'sinus'
    | 'cos' | 'cosinus'
    | 'tan' | 'tg' | 'tangens'
    | 'cot' | 'ctg' | 'cotangens' | 'kotangens'
    | 'sec' | 'secans' | 'sekans'
    | 'csc' | 'cosecans' | 'kosekans'

    | 'arcsin' | 'arcussinus'
    | 'arccos' | 'arcuscosinus'
    | 'arctan' | 'arctg' | 'arcustangens'
    | 'arccot' | 'arcctg' | 'arcuscotangens' | 'arcuskotangens'
    | 'arcsec' | 'arcussecans' | 'arcussekans'
    | 'arccsc' | 'arcuscosecans' | 'arcuskosekans'

    | 'sinh' | 'sinushiperboliczny'
    | 'cosh' | 'cosinushiperboliczny'
    | 'tanh' | 'tgh' | 'tangenshiperboliczny'
    | 'coth' | 'ctgh' | 'cotangenshiperboliczny' | 'kotangenshiperboliczny'
    | 'sech' | 'secanshiperboliczny'
    | 'csch' | 'cosecanshiperboliczny'

    | 'arcsinh' | 'arsinh' | 'areasinushiperboliczny'
    | 'arccosh' | 'arcosh' | 'areacosinushiperboliczny'
    | 'arctanh' | 'artanh' | 'areatangenshiperboliczny'
    | 'arccoth' | 'arcoth' | 'areacotangenshiperboliczny'
    | 'arcsech' | 'arsech' | 'areasecanshiperboliczny'
    | 'arccsch' | 'arcsch' | 'areacosecanshiperboliczny'
    ;

DEGREE : 'deg' | 'stopni' | 'stopnie' | '°' ;


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