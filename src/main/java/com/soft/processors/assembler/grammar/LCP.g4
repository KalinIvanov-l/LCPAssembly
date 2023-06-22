/* LCP Assembler grammar */

grammar LCP;

@parser::members {
  public static Integer pass = 1;
}

// The start symbol of the grammar is assemblyProg
assemblyProg : line* EOF ;
// Line in assembly code 
line : directive | labeledInstruction ;
// Directive        
directive : IDENT 'EQU' CONST ;
// Labeled instruction
labeledInstruction
    : LABEL ':' labeledInstruction          #labeledInstr
    | instruction                           #instr
    ;    
// Instruction
instruction 
    : INSTR                                 #instrOnly 
    | INSTR oper                            #instrOper
    | INSTR expr                            #instrExpr
    | INSTR LABEL                           #instrLabel
    ;
// Operand
oper
    : expr                                  #operExpr
    | '#' expr                              #operImmExpr
    ;
// Expression
expr
    : IDENT                                 #exprIdent
    | CONST                                 #exprConstant
    ;

// ---- Lexer rules ---------------------------------
LABEL:          'L'[0-9]+ ;
CONST:          DIGIT+ | HEX_DIGIT+ [hH] ;
IDENT:          [a-z_] ([a-zA-Z_] | DIGIT)* ;
INSTR:          [A-Z]+ ;
COMMENT:        ';' ~[\n]*      -> skip ;
EOL:            '\r'? '\n'      -> skip ;
WHITE_SPACE:    [ \t]+          -> skip ;
ANY:                      . ;
// ---- Fragments ------------------------------------
fragment DIGIT:         [0-9] ;
fragment HEX_DIGIT:     [a-fA-F0-9] ;
