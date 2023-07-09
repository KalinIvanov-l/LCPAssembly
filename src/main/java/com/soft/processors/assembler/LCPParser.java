package com.soft.processors.assembler;

import java.util.List;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.*;

/**
 * Generated from grammar/LCP.g4 by ANTLR 4.5.3
 *
 * @author kalin
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LCPParser extends Parser {
  static {
    RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
          new PredictionContextCache();
  public static final int
          T__0 = 1, T__1 = 2, T__2 = 3, LABEL = 4, CONST = 5, IDENT = 6, INSTR = 7, COMMENT = 8,
          EOL = 9, WHITE_SPACE = 10, ANY = 11;
  public static final int
          RULE_assemblyProg = 0, RULE_line = 1, RULE_directive = 2, RULE_labeledInstruction = 3,
          RULE_instruction = 4, RULE_oper = 5, RULE_expr = 6;
  public static final String[] ruleNames = {
          "assemblyProg", "line", "directive", "labeledInstruction", "instruction",
          "oper", "expr"
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
  public ATN getATN() {
    return _ATN;
  }


  public static Integer pass = 1;

  public LCPParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

  public static class AssemblyProgContext extends ParserRuleContext {
    public TerminalNode EOF() {
      return getToken(LCPParser.EOF, 0);
    }

    public List<LineContext> line() {
      return getRuleContexts(LineContext.class);
    }

    public LineContext line(int i) {
      return getRuleContext(LineContext.class, i);
    }

    public AssemblyProgContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_assemblyProg;
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitAssemblyProg(this);
      else return visitor.visitChildren(this);
    }
  }

  public final AssemblyProgContext assemblyProg() throws RecognitionException {
    AssemblyProgContext _localctx = new AssemblyProgContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_assemblyProg);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(17);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LABEL) | (1L << IDENT) | (1L << INSTR))) != 0)) {
          {
            {
              setState(14);
              line();
            }
          }
          setState(19);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(20);
        match(EOF);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class LineContext extends ParserRuleContext {
    public DirectiveContext directive() {
      return getRuleContext(DirectiveContext.class, 0);
    }

    public LabeledInstructionContext labeledInstruction() {
      return getRuleContext(LabeledInstructionContext.class, 0);
    }

    public LineContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_line;
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitLine(this);
      else return visitor.visitChildren(this);
    }
  }

  public final LineContext line() throws RecognitionException {
    LineContext _localctx = new LineContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_line);
    try {
      setState(24);
      switch (_input.LA(1)) {
        case IDENT:
          enterOuterAlt(_localctx, 1);
        {
          setState(22);
          directive();
        }
        break;
        case LABEL:
        case INSTR:
          enterOuterAlt(_localctx, 2);
        {
          setState(23);
          labeledInstruction();
        }
        break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class DirectiveContext extends ParserRuleContext {
    public TerminalNode IDENT() {
      return getToken(LCPParser.IDENT, 0);
    }

    public TerminalNode CONST() {
      return getToken(LCPParser.CONST, 0);
    }

    public DirectiveContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_directive;
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitDirective(this);
      else return visitor.visitChildren(this);
    }
  }

  public final DirectiveContext directive() throws RecognitionException {
    DirectiveContext _localctx = new DirectiveContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_directive);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(26);
        match(IDENT);
        setState(27);
        match(T__0);
        setState(28);
        match(CONST);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class LabeledInstructionContext extends ParserRuleContext {
    public LabeledInstructionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_labeledInstruction;
    }

    public LabeledInstructionContext() {
    }

    public void copyFrom(LabeledInstructionContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class InstrContext extends LabeledInstructionContext {
    public InstructionContext instruction() {
      return getRuleContext(InstructionContext.class, 0);
    }

    public InstrContext(LabeledInstructionContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitInstr(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class LabeledInstrContext extends LabeledInstructionContext {
    public TerminalNode LABEL() {
      return getToken(LCPParser.LABEL, 0);
    }

    public LabeledInstructionContext labeledInstruction() {
      return getRuleContext(LabeledInstructionContext.class, 0);
    }

    public LabeledInstrContext(LabeledInstructionContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitLabeledInstr(this);
      else return visitor.visitChildren(this);
    }
  }

  public final LabeledInstructionContext labeledInstruction() throws RecognitionException {
    LabeledInstructionContext _localctx = new LabeledInstructionContext(_ctx, getState());
    enterRule(_localctx, 6, RULE_labeledInstruction);
    try {
      setState(34);
      switch (_input.LA(1)) {
        case LABEL:
          _localctx = new LabeledInstrContext(_localctx);
          enterOuterAlt(_localctx, 1);
        {
          setState(30);
          match(LABEL);
          setState(31);
          match(T__1);
          setState(32);
          labeledInstruction();
        }
        break;
        case INSTR:
          _localctx = new InstrContext(_localctx);
          enterOuterAlt(_localctx, 2);
        {
          setState(33);
          instruction();
        }
        break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class InstructionContext extends ParserRuleContext {
    public InstructionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_instruction;
    }

    public InstructionContext() {
    }

    public void copyFrom(InstructionContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class InstrOnlyContext extends InstructionContext {
    public TerminalNode INSTR() {
      return getToken(LCPParser.INSTR, 0);
    }

    public InstrOnlyContext(InstructionContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitInstrOnly(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class InstrOperContext extends InstructionContext {
    public TerminalNode INSTR() {
      return getToken(LCPParser.INSTR, 0);
    }

    public OperContext oper() {
      return getRuleContext(OperContext.class, 0);
    }

    public InstrOperContext(InstructionContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitInstrOper(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class InstrExprContext extends InstructionContext {
    public TerminalNode INSTR() {
      return getToken(LCPParser.INSTR, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public InstrExprContext(InstructionContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitInstrExpr(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class InstrLabelContext extends InstructionContext {
    public TerminalNode INSTR() {
      return getToken(LCPParser.INSTR, 0);
    }

    public TerminalNode LABEL() {
      return getToken(LCPParser.LABEL, 0);
    }

    public InstrLabelContext(InstructionContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitInstrLabel(this);
      else return visitor.visitChildren(this);
    }
  }

  public final InstructionContext instruction() throws RecognitionException {
    InstructionContext _localctx = new InstructionContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_instruction);
    try {
      setState(43);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
        case 1:
          _localctx = new InstrOnlyContext(_localctx);
          enterOuterAlt(_localctx, 1);
        {
          setState(36);
          match(INSTR);
        }
        break;
        case 2:
          _localctx = new InstrOperContext(_localctx);
          enterOuterAlt(_localctx, 2);
        {
          setState(37);
          match(INSTR);
          setState(38);
          oper();
        }
        break;
        case 3:
          _localctx = new InstrExprContext(_localctx);
          enterOuterAlt(_localctx, 3);
        {
          setState(39);
          match(INSTR);
          setState(40);
          expr();
        }
        break;
        case 4:
          _localctx = new InstrLabelContext(_localctx);
          enterOuterAlt(_localctx, 4);
        {
          setState(41);
          match(INSTR);
          setState(42);
          match(LABEL);
        }
        break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class OperContext extends ParserRuleContext {
    public OperContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_oper;
    }

    public OperContext() {
    }

    public void copyFrom(OperContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class OperImmExprContext extends OperContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public OperImmExprContext(OperContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitOperImmExpr(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class OperExprContext extends OperContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public OperExprContext(OperContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitOperExpr(this);
      else return visitor.visitChildren(this);
    }
  }

  public final OperContext oper() throws RecognitionException {
    OperContext _localctx = new OperContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_oper);
    try {
      setState(48);
      switch (_input.LA(1)) {
        case CONST:
        case IDENT:
          _localctx = new OperExprContext(_localctx);
          enterOuterAlt(_localctx, 1);
        {
          setState(45);
          expr();
        }
        break;
        case T__2:
          _localctx = new OperImmExprContext(_localctx);
          enterOuterAlt(_localctx, 2);
        {
          setState(46);
          match(T__2);
          setState(47);
          expr();
        }
        break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ExprContext extends ParserRuleContext {
    public ExprContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_expr;
    }

    public ExprContext() {
    }

    public void copyFrom(ExprContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class ExprIdentContext extends ExprContext {
    public TerminalNode IDENT() {
      return getToken(LCPParser.IDENT, 0);
    }

    public ExprIdentContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitExprIdent(this);
      else return visitor.visitChildren(this);
    }
  }

  public static class ExprConstantContext extends ExprContext {
    public TerminalNode CONST() {
      return getToken(LCPParser.CONST, 0);
    }

    public ExprConstantContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
      if (visitor instanceof LCPVisitor) return ((LCPVisitor<? extends T>) visitor).visitExprConstant(this);
      else return visitor.visitChildren(this);
    }
  }

  public final ExprContext expr() throws RecognitionException {
    ExprContext _localctx = new ExprContext(_ctx, getState());
    enterRule(_localctx, 12, RULE_expr);
    try {
      setState(52);
      switch (_input.LA(1)) {
        case IDENT:
          _localctx = new ExprIdentContext(_localctx);
          enterOuterAlt(_localctx, 1);
        {
          setState(50);
          match(IDENT);
        }
        break;
        case CONST:
          _localctx = new ExprConstantContext(_localctx);
          enterOuterAlt(_localctx, 2);
        {
          setState(51);
          match(CONST);
        }
        break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public static final String _serializedATN =
          "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\r9\4\2\t\2\4\3\t" +
                  "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\7\2\22\n\2\f\2\16\2\25" +
                  "\13\2\3\2\3\2\3\3\3\3\5\3\33\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5%" +
                  "\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6.\n\6\3\7\3\7\3\7\5\7\63\n\7\3\b\3" +
                  "\b\5\b\67\n\b\3\b\2\2\t\2\4\6\b\n\f\16\2\29\2\23\3\2\2\2\4\32\3\2\2\2" +
                  "\6\34\3\2\2\2\b$\3\2\2\2\n-\3\2\2\2\f\62\3\2\2\2\16\66\3\2\2\2\20\22\5" +
                  "\4\3\2\21\20\3\2\2\2\22\25\3\2\2\2\23\21\3\2\2\2\23\24\3\2\2\2\24\26\3" +
                  "\2\2\2\25\23\3\2\2\2\26\27\7\2\2\3\27\3\3\2\2\2\30\33\5\6\4\2\31\33\5" +
                  "\b\5\2\32\30\3\2\2\2\32\31\3\2\2\2\33\5\3\2\2\2\34\35\7\b\2\2\35\36\7" +
                  "\3\2\2\36\37\7\7\2\2\37\7\3\2\2\2 !\7\6\2\2!\"\7\4\2\2\"%\5\b\5\2#%\5" +
                  "\n\6\2$ \3\2\2\2$#\3\2\2\2%\t\3\2\2\2&.\7\t\2\2\'(\7\t\2\2(.\5\f\7\2)" +
                  "*\7\t\2\2*.\5\16\b\2+,\7\t\2\2,.\7\6\2\2-&\3\2\2\2-\'\3\2\2\2-)\3\2\2" +
                  "\2-+\3\2\2\2.\13\3\2\2\2/\63\5\16\b\2\60\61\7\5\2\2\61\63\5\16\b\2\62" +
                  "/\3\2\2\2\62\60\3\2\2\2\63\r\3\2\2\2\64\67\7\b\2\2\65\67\7\7\2\2\66\64" +
                  "\3\2\2\2\66\65\3\2\2\2\67\17\3\2\2\2\b\23\32$-\62\66";
  public static final ATN _ATN =
          new ATNDeserializer().deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}