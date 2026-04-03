parser grammar MathParser;

options { tokenVocab=MathLexer; }

program : expression EOF ;

expression
    : INTEGRAL_MATH (FROM_NATURAL lower=expression TO_NATURAL upper=expression)? body=expression (OVER_NATURAL var=ID)? # Integral
    | SUM_MATH FROM_NATURAL? var=ID EQ lower=expression TO_NATURAL? upper=expression L_BRACKET body=expression R_BRACKET  # Sum
    | PROD_MATH FROM_NATURAL? var=ID EQ lower=expression TO_NATURAL? upper=expression L_BRACKET body=expression R_BRACKET # Product
    | LIMIT_MATH WHEN? var=ID APPROACHES TO_NATURAL? target=expression L_BRACKET? body=expression R_BRACKET? # Limit
    | EQ expression # Equality
    | (L_CURLY | L_BRACKET) expression (R_CURLY |  R_BRACKET)       # Grouping
    | TRIGONOMETRIC '(' expression ')'                              # TrigonometricParen
    | TRIGONOMETRIC HAT power=expression '(' arg=expression ')'     # FunctionPowerParen
    | expression DEGREE                                             # Degree
    | LOGIC_NOT expression                                          # LogicNot
    | (PLUS | MINUS) expression                                     # UnarySign
    | TRIGONOMETRIC expression                                      # TrigonometricNoParen
    | left=expression UNDERSCORE right=expression                    # Underscore
    | left=expression HAT right=expression                          # Power
    | TRIGONOMETRIC HAT power=expression arg=expression             # FunctionPowerNoParen
    | left=expression right=expression                              # ImplicitMul
    | left=expression (MUL | DIV_BLOCK | DIV_LINE) right=expression # MultDiv
    | left=expression (PLUS | MINUS) right=expression               # AddSub
    | left=expression (LOGIC_AND | LOGIC_OR) right=expression       # LogicAndOr
    | left=expression (LOGIC_IMPLIES | LOGIC_IFF) right=expression  # LogicImplIff
    | ID                                                            # Variable
    | GREEK                                                         # Greek
    | INFINITY                                                      # Infinity
    | INT                                                           # Constant
    ;