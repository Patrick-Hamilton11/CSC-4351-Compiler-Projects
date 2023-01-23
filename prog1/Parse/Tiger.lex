package Parse;
import ErrorMsg.ErrorMsg;

%% 

%implements Lexer
%function nextToken
%type java_cup.runtime.Symbol
%char

%{
private void newline() {
  errorMsg.newline(yychar);
}

private void err(int pos, String s) {
  errorMsg.error(pos,s);
}

private void err(String s) {
  err(yychar,s);
}

private java_cup.runtime.Symbol tok(int kind) {
    return tok(kind, null);
}

private java_cup.runtime.Symbol tok(int kind, Object value) {
    return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(), value);
}

private ErrorMsg errorMsg;

Yylex(java.io.InputStream s, ErrorMsg e) {
  this(s);
  errorMsg=e;
}

%}

%eofval{
	{
	 return tok(sym.EOF, null);
        }
%eofval}       


%%
" "	{}
\n	{newline();}
function	{return tok(sym.FUNCTION);}
0|(([1-9])([0-9])*)	{return tok(sym.INT,yytext());}
">"	{return tok(sym.GT);}
"/"	{return tok(sym.DIVIDE);}
":"	{return tok(sym.COLON);}
else	{return tok(sym.ELSE);}
"|"	{return tok(sym.OR);}
nil	{return tok(sym.NIL);}
do	{return tok(sym.DO);}
">="	{return tok(sym.GE);}

"<"	{return tok(sym.LT);}
of	{return tok(sym.OF);}
"-"	{return tok(sym.MINUS);}

type	{return tok(sym.TYPE);}
for	{return tok(sym.FOR);}
to	{return tok(sym.TO);}
"*"	{return tok(sym.TIMES);}
","	{return tok(sym.COMMA);}
"<="	{return tok(sym.LE);}
in	{return tok(sym.IN);}
end	{return tok(sym.END);}
":="	{return tok(sym.ASSIGN);}

"."	{return tok(sym.DOT);}
"("	{return tok(sym.LPAREN);}
")"	{return tok(sym.RPAREN);}
if	{return tok(sym.IF);}
";"	{return tok(sym.SEMICOLON);}
while	{return tok(sym.WHILE);}
"["	{return tok(sym.LBRACK);}
"]"	{return tok(sym.RBRACK);}
"<>"	{return tok(sym.NEQ);}
var	{return tok(sym.VAR);}
break	{return tok(sym.BREAK);}
"&"	{return tok(sym.AND);}
"+"	{return tok(sym.PLUS);}
"{"	{return tok(sym.LBRACE);}
"}"	{return tok(sym.RBRACE);}
let	{return tok(sym.LET);}
then	{return tok(sym.THEN);}
"="	{return tok(sym.EQ);}
([a-z]|[A-Z])([a-z]|[A-Z]|[0-9]|_)*	{return tok(sym.ID, yytext());}
. { err("Illegal character: " + yytext()); }
