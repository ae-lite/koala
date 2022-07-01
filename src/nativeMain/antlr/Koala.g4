grammar Koala;

parse: clazz;
clazz: CLASS name=TYPE nativeMethods+=nativeMethod* methods+=method*;

nativeMethod: NATIVE name=ID LPAREN (parameters+=formalParameter (COMMA parameters+=formalParameter)*)? RPAREN (COLON returnType=TYPE)?;
method: name=ID LPAREN (parameters+=formalParameter (COMMA parameters+=formalParameter)*)? RPAREN (COLON returnType=TYPE)? LBRACE statements+=statement* RBRACE;

statement
    : expression #expressionStatement;

expression
    : obj=expression DOT name=ID LPAREN (parameters+=expression (COMMA parameters+=expression)*)? RPAREN #methodCallExpression
    | name=ID #variableExpression
    | value=STRING_LITERAL #stringLiteralExpression;

formalParameter: name=ID COLON type=TYPE;

CLASS: 'class';
NATIVE: 'native';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
COLON: ':';
DOT: '.';
COMMA: ',';
STRING_LITERAL: '"'.*?'"';
ID: [a-z][a-zA-Z0-9]*;
TYPE: [A-Z][a-zA-Z0-9]*;
WS: [ \t\r\n]+ -> skip;
