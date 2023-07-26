package com.soft.processors.assembler;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

/**
 * Generated from grammar/LCP.g4 by ANTLR 4.5.3 or highest
 * This is a simple generation of some rules
 *
 * @author kalin
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LcpLexer extends Lexer {
    static {
        RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, LABEL = 4, CONST = 5, IDENT = 6, INSTR = 7, COMMENT = 8,
            EOL = 9, WHITE_SPACE = 10, ANY = 11;
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    public static final String[] ruleNames = {
            "T__0", "T__1", "T__2", "LABEL", "CONST", "IDENT", "INSTR", "COMMENT",
            "EOL", "WHITE_SPACE", "ANY", "DIGIT", "HEX_DIGIT"
    };

    private static final String[] _LITERAL_NAMES = {
            null, "'EQU'", "':'", "'#'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, "LABEL", "CONST", "IDENT", "INSTR", "COMMENT",
            "EOL", "WHITE_SPACE", "ANY"
    };
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }


    /**
     * Instantiates a new Lcp lexer.
     *
     * @param input the input
     */
    public LcpLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName() {
        return "LCP.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\rc\b\1\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5" +
                    "\6\5(\n\5\r\5\16\5)\3\6\6\6-\n\6\r\6\16\6.\3\6\6\6\62\n\6\r\6\16\6\63" +
                    "\3\6\3\6\5\68\n\6\3\7\3\7\3\7\7\7=\n\7\f\7\16\7@\13\7\3\b\6\bC\n\b\r\b" +
                    "\16\bD\3\t\3\t\7\tI\n\t\f\t\16\tL\13\t\3\t\3\t\3\n\5\nQ\n\n\3\n\3\n\3" +
                    "\n\3\n\3\13\6\13X\n\13\r\13\16\13Y\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16" +
                    "\2\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\2\33\2\3" +
                    "\2\n\3\2\62;\4\2JJjj\4\2aac|\5\2C\\aac|\3\2C\\\3\2\f\f\4\2\13\13\"\"\5" +
                    "\2\62;CHchj\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2" +
                    "\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27" +
                    "\3\2\2\2\3\35\3\2\2\2\5!\3\2\2\2\7#\3\2\2\2\t%\3\2\2\2\13\67\3\2\2\2\r" +
                    "9\3\2\2\2\17B\3\2\2\2\21F\3\2\2\2\23P\3\2\2\2\25W\3\2\2\2\27]\3\2\2\2" +
                    "\31_\3\2\2\2\33a\3\2\2\2\35\36\7G\2\2\36\37\7S\2\2\37 \7W\2\2 \4\3\2\2" +
                    "\2!\"\7<\2\2\"\6\3\2\2\2#$\7%\2\2$\b\3\2\2\2%\'\7N\2\2&(\t\2\2\2\'&\3" +
                    "\2\2\2()\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\n\3\2\2\2+-\5\31\r\2,+\3\2\2\2" +
                    "-.\3\2\2\2.,\3\2\2\2./\3\2\2\2/8\3\2\2\2\60\62\5\33\16\2\61\60\3\2\2\2" +
                    "\62\63\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\65\3\2\2\2\65\66\t\3\2\2" +
                    "\668\3\2\2\2\67,\3\2\2\2\67\61\3\2\2\28\f\3\2\2\29>\t\4\2\2:=\t\5\2\2" +
                    ";=\5\31\r\2<:\3\2\2\2<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?\16\3\2" +
                    "\2\2@>\3\2\2\2AC\t\6\2\2BA\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2E\20\3" +
                    "\2\2\2FJ\7=\2\2GI\n\7\2\2HG\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK\3\2\2\2KM\3" +
                    "\2\2\2LJ\3\2\2\2MN\b\t\2\2N\22\3\2\2\2OQ\7\17\2\2PO\3\2\2\2PQ\3\2\2\2" +
                    "QR\3\2\2\2RS\7\f\2\2ST\3\2\2\2TU\b\n\2\2U\24\3\2\2\2VX\t\b\2\2WV\3\2\2" +
                    "\2XY\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[\\\b\13\2\2\\\26\3\2\2\2]^" +
                    "\13\2\2\2^\30\3\2\2\2_`\t\2\2\2`\32\3\2\2\2ab\t\t\2\2b\34\3\2\2\2\r\2" +
                    ").\63\67<>DJPY\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}