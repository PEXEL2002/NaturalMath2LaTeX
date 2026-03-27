lexer grammar MathLexer;

INTEGRAL_MATH
    : 'calka' | 'int' | 'integral';

SUM_MATH
    : 'suma' | 'sum' ;
PROD_MATH
    : 'iloczyn' | 'prod' | 'product' ;

LIMIT_MATH
    : 'granica' | 'lim' | 'limes' ;
WHEN
    : 'przy' | 'gdy' | 'dla' ;
APPROACHES
    : 'dazy' | 'dazacym' | 'zmierza' | '->' ;

FROM_NATURAL
    : 'od' | 'from';
TO_NATURAL
    : 'do' | 'to' ;
OVER_NATURAL
    : 'po' | 'over';

LOGIC_AND     : 'and' | 'oraz' | '&&' ;
LOGIC_OR      : 'lub' | 'or' | 'albo' | '||' ;
LOGIC_IMPLIES : '=>' | 'implikuje' | 'wynika' | 'zatem' ;
LOGIC_IFF     : '<=>' | 'wtw' | 'wtedyitylkowtedy' | 'rownowaznie' ;
LOGIC_NOT     : 'nie' | 'not' | '~' ;

LEQ   : '<=' | '≤' ;
GEQ   : '>=' | '≥' ;
NEQ   : '!=' | '<>' | '≠' ;
LT    : '<' ;
GT    : '>' ;
EQ    : '='| '==' | 'rowne' ;
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

DIV_BLOCK : '//' ;
DIV_LINE  : '/' ;

PLUS      : '+' ;
MINUS     : '-' ;
MUL       : '*' ;
HAT       : '^' ;
UNDERSCORE: '_' ;
L_BRACKET : '(' ;
R_BRACKET : ')';

L_CURLY   : '{' ;
R_CURLY   : '}' ;

ID        : [a-zA-Z]+ ;
NUMBER    : [0-9]+ ( ('.'|',') [0-9]+ )?;
WS        : [ \t\r\n]+ -> skip ;