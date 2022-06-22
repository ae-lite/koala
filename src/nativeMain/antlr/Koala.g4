grammar Koala;

parse: clazz;
clazz: CLASS TYPE nativeMethod* method*;

nativeMethod: NATIVE ID LPAREN formalParameters? RPAREN (COLON TYPE)?;
method: ID LPAREN formalParameters? RPAREN (COLON TYPE)? LBRACE statement* RBRACE;

statement: expression;

expression:
      expression DOT ID
    | expression DOT ID LPAREN actualParameters? RPAREN
    | ID
    | LPAREN expression RPAREN
    | STRING_LITERAL;

formalParameters: formalParameter (COMMA formalParameter)*;
formalParameter: ID COLON TYPE;
actualParameters: actualParameter (COMMA actualParameter)*;
actualParameter: ID COLON expression;

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
