# Project Name: LCP Assembler
# File: Makefile
# Description: Make file for the LCP Assembler
JAVA  = java
JAVAC  = javac
SRC = src
BIN = bin
LIB = lib
ANTLR = org.antlr.v4.Tool
ANTLR_LIB = $(LIB)/antlr-4.5.3-complete.jar
LIBS = $(ANTLR_LIB)
GRAMMAR_PATH = grammar
PARSER_GRAMMAR = $(GRAMMAR_PATH)/LCP.g4
MAIN_CLASS = LCP
#OPTIONS = -V

make:  parser_grammar parser

parser_grammar: $(PARSER_GRAMMAR)
		$(JAVA) -cp $(ANTLR_LIB) $(ANTLR) -lib $(GRAMMAR_PATH) -o $(SRC) -visitor -no-listener $(PARSER_GRAMMAR)

parser: $(PARSER_GRAMMAR)
		$(JAVAC) -d $(BIN)/ -cp $(SRC);$(LIBS) $(SRC)/*.java 

clean:
		del $(BIN)\*.class 
