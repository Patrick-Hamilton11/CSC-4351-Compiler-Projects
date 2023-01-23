package Parse;
import ErrorMsg.ErrorMsg;


class Yylex implements Lexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final char YYEOF = '\uFFFF';

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private char yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_start () {
		++yychar;
		++yy_buffer_start;
	}
	private void yy_pushback () {
		--yy_buffer_end;
	}
	private void yy_mark_start () {
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
private int [][] unpackFromString(int size1, int size2, String st)
    {
      int colonIndex = -1;
      String lengthString;
      int sequenceLength = 0;
      int sequenceInteger = 0;
      int commaIndex;
      String workString;
      int res[][] = new int[size1][size2];
      for (int i= 0; i < size1; i++)
	for (int j= 0; j < size2; j++)
	  {
	    if (sequenceLength == 0) 
	      {	
		commaIndex = st.indexOf(',');
		if (commaIndex == -1)
		  workString = st;
		else
		  workString = st.substring(0, commaIndex);
		st = st.substring(commaIndex+1);
		colonIndex = workString.indexOf(':');
		if (colonIndex == -1)
		  {
		    res[i][j] = Integer.parseInt(workString);
		  }
		else 
		  {
		    lengthString = workString.substring(colonIndex+1);  
		    sequenceLength = Integer.parseInt(lengthString);
		    workString = workString.substring(0,colonIndex);
		    sequenceInteger = Integer.parseInt(workString);
		    res[i][j] = sequenceInteger;
		    sequenceLength--;
		  }
	      }
	    else 
	      {
		res[i][j] = sequenceInteger;
		sequenceLength--;
	      }
	  }
      return res;
    }
	private int yy_acpt[] = {
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR
	};
	private int yy_cmap[] = {
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 1, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		2, 0, 0, 0, 0, 0, 3, 0,
		4, 5, 6, 7, 8, 9, 10, 11,
		12, 13, 13, 13, 13, 13, 13, 13,
		13, 13, 14, 15, 16, 17, 18, 0,
		0, 19, 19, 19, 19, 19, 19, 19,
		19, 19, 19, 19, 19, 19, 19, 19,
		19, 19, 19, 19, 19, 19, 19, 19,
		19, 19, 19, 20, 0, 21, 0, 22,
		0, 23, 24, 25, 26, 27, 28, 19,
		29, 30, 19, 31, 32, 19, 33, 34,
		35, 19, 36, 37, 38, 39, 40, 41,
		19, 42, 19, 43, 44, 45, 0, 0
		
	};
	private int yy_rmap[] = {
		0, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 2, 1,
		3, 1, 4, 5, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 5, 5, 5,
		5, 5, 5, 5, 5, 5, 5, 5,
		5, 5, 5, 5, 5, 6, 7, 8,
		9, 10, 11, 12, 13, 14, 15, 16,
		17, 18, 19, 20, 21, 22, 23, 24,
		25, 26, 27, 28, 29, 30, 31, 32,
		33, 34, 35, 36, 37, 38, 39, 40
		
	};
	private int yy_nxt[][] = unpackFromString(41,46,
"1,2,3,4,5,6,7,8,9,10,11,12,13,45,14,15,16,17,18,19,20,21,1,19,75,19,46,61,62,19,47,19,63,64,48,19:3,49,19,65,76,19,22,23,24,-1:63,25,-1:45,26,27,-1:44,28,-1:40,19:2,-1:5,19,-1:2,19:21,-1:15,45:2,-1:44,19:2,-1:5,19,-1:2,19:12,29,19:8,-1:15,19:2,-1:5,19,-1:2,19:6,30,19:4,31,19:9,-1:15,19:2,-1:5,19,-1:2,19:6,32,19:14,-1:15,19:2,-1:5,19,-1:2,19:7,67,19:4,33,19:7,68,-1:15,19:2,-1:5,19,-1:2,19:4,34,19:16,-1:15,19:2,-1:5,19,-1:2,19:14,35,19:6,-1:15,19:2,-1:5,19,-1:2,19:16,36,19:4,-1:15,19:2,-1:5,19,-1:2,19:10,37,19:10,-1:15,19:2,-1:5,19,-1:2,19:14,38,19:6,-1:15,19:2,-1:5,19,-1:2,19:5,39,19:15,-1:15,19:2,-1:5,19,-1:2,19:11,40,19:9,-1:15,19:2,-1:5,19,-1:2,19:5,41,19:15,-1:15,19:2,-1:5,19,-1:2,19:9,42,19:11,-1:15,19:2,-1:5,19,-1:2,19:5,43,19:15,-1:15,19:2,-1:5,19,-1:2,19:11,44,19:9,-1:15,19:2,-1:5,19,-1:2,19:10,66,50,19:9,-1:15,19:2,-1:5,19,-1:2,19:12,51,19:4,79,19:3,-1:15,19:2,-1:5,19,-1:2,19:5,52,19:15,-1:15,19:2,-1:5,19,-1:2,19:8,53,19:12,-1:15,19:2,-1:5,19,-1:2,19,54,19:19,-1:15,19:2,-1:5,19,-1:2,19:15,55,19:5,-1:15,19:2,-1:5,19,-1:2,19:5,56,19:15,-1:15,19:2,-1:5,19,-1:2,19:13,57,19:7,-1:15,19:2,-1:5,19,-1:2,19,58,19:19,-1:15,19:2,-1:5,19,-1:2,19:10,59,19:10,-1:15,19:2,-1:5,19,-1:2,19:12,60,19:8,-1:15,19:2,-1:5,19,-1:2,19:5,69,19:15,-1:15,19:2,-1:5,19,-1:2,19:8,70,19:12,-1:15,19:2,-1:5,19,-1:2,19:8,71,19:12,-1:15,19:2,-1:5,19,-1:2,19:14,72,19:6,-1:15,19:2,-1:5,19,-1:2,19:7,73,19:13,-1:15,19:2,-1:5,19,-1:2,19:16,74,19:4,-1:15,19:2,-1:5,19,-1:2,19:3,77,19:17,-1:15,19:2,-1:5,19,-1:2,19:11,78,19:9,-1:3");
	public java_cup.runtime.Symbol nextToken ()
		throws java.io.IOException {
		char yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			if (YYEOF != yy_lookahead) {
				yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YYEOF == yy_lookahead && true == yy_initial) {

	{
	 return tok(sym.EOF, null);
        }
				}
				else if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_to_mark();
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_pushback();
					}
					if (0 != (YY_START & yy_anchor)) {
						yy_move_start();
					}
					switch (yy_last_accept_state) {
					case 1:
						{ err("Illegal character: " + yytext()); }
					case -2:
						break;
					case 2:
						{newline();}
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{return tok(sym.AND);}
					case -5:
						break;
					case 5:
						{return tok(sym.LPAREN);}
					case -6:
						break;
					case 6:
						{return tok(sym.RPAREN);}
					case -7:
						break;
					case 7:
						{return tok(sym.TIMES);}
					case -8:
						break;
					case 8:
						{return tok(sym.PLUS);}
					case -9:
						break;
					case 9:
						{return tok(sym.COMMA);}
					case -10:
						break;
					case 10:
						{return tok(sym.MINUS);}
					case -11:
						break;
					case 11:
						{return tok(sym.DOT);}
					case -12:
						break;
					case 12:
						{return tok(sym.DIVIDE);}
					case -13:
						break;
					case 13:
						{return tok(sym.INT,yytext());}
					case -14:
						break;
					case 14:
						{return tok(sym.COLON);}
					case -15:
						break;
					case 15:
						{return tok(sym.SEMICOLON);}
					case -16:
						break;
					case 16:
						{return tok(sym.LT);}
					case -17:
						break;
					case 17:
						{return tok(sym.EQ);}
					case -18:
						break;
					case 18:
						{return tok(sym.GT);}
					case -19:
						break;
					case 19:
						{return tok(sym.ID, yytext());}
					case -20:
						break;
					case 20:
						{return tok(sym.LBRACK);}
					case -21:
						break;
					case 21:
						{return tok(sym.RBRACK);}
					case -22:
						break;
					case 22:
						{return tok(sym.LBRACE);}
					case -23:
						break;
					case 23:
						{return tok(sym.OR);}
					case -24:
						break;
					case 24:
						{return tok(sym.RBRACE);}
					case -25:
						break;
					case 25:
						{return tok(sym.ASSIGN);}
					case -26:
						break;
					case 26:
						{return tok(sym.LE);}
					case -27:
						break;
					case 27:
						{return tok(sym.NEQ);}
					case -28:
						break;
					case 28:
						{return tok(sym.GE);}
					case -29:
						break;
					case 29:
						{return tok(sym.DO);}
					case -30:
						break;
					case 30:
						{return tok(sym.IF);}
					case -31:
						break;
					case 31:
						{return tok(sym.IN);}
					case -32:
						break;
					case 32:
						{return tok(sym.OF);}
					case -33:
						break;
					case 33:
						{return tok(sym.TO);}
					case -34:
						break;
					case 34:
						{return tok(sym.END);}
					case -35:
						break;
					case 35:
						{return tok(sym.FOR);}
					case -36:
						break;
					case 36:
						{return tok(sym.LET);}
					case -37:
						break;
					case 37:
						{return tok(sym.NIL);}
					case -38:
						break;
					case 38:
						{return tok(sym.VAR);}
					case -39:
						break;
					case 39:
						{return tok(sym.ELSE);}
					case -40:
						break;
					case 40:
						{return tok(sym.THEN);}
					case -41:
						break;
					case 41:
						{return tok(sym.TYPE);}
					case -42:
						break;
					case 42:
						{return tok(sym.BREAK);}
					case -43:
						break;
					case 43:
						{return tok(sym.WHILE);}
					case -44:
						break;
					case 44:
						{return tok(sym.FUNCTION);}
					case -45:
						break;
					case 45:
						{return tok(sym.INT,yytext());}
					case -46:
						break;
					case 46:
						{return tok(sym.ID, yytext());}
					case -47:
						break;
					case 47:
						{return tok(sym.ID, yytext());}
					case -48:
						break;
					case 48:
						{return tok(sym.ID, yytext());}
					case -49:
						break;
					case 49:
						{return tok(sym.ID, yytext());}
					case -50:
						break;
					case 50:
						{return tok(sym.ID, yytext());}
					case -51:
						break;
					case 51:
						{return tok(sym.ID, yytext());}
					case -52:
						break;
					case 52:
						{return tok(sym.ID, yytext());}
					case -53:
						break;
					case 53:
						{return tok(sym.ID, yytext());}
					case -54:
						break;
					case 54:
						{return tok(sym.ID, yytext());}
					case -55:
						break;
					case 55:
						{return tok(sym.ID, yytext());}
					case -56:
						break;
					case 56:
						{return tok(sym.ID, yytext());}
					case -57:
						break;
					case 57:
						{return tok(sym.ID, yytext());}
					case -58:
						break;
					case 58:
						{return tok(sym.ID, yytext());}
					case -59:
						break;
					case 59:
						{return tok(sym.ID, yytext());}
					case -60:
						break;
					case 60:
						{return tok(sym.ID, yytext());}
					case -61:
						break;
					case 61:
						{return tok(sym.ID, yytext());}
					case -62:
						break;
					case 62:
						{return tok(sym.ID, yytext());}
					case -63:
						break;
					case 63:
						{return tok(sym.ID, yytext());}
					case -64:
						break;
					case 64:
						{return tok(sym.ID, yytext());}
					case -65:
						break;
					case 65:
						{return tok(sym.ID, yytext());}
					case -66:
						break;
					case 66:
						{return tok(sym.ID, yytext());}
					case -67:
						break;
					case 67:
						{return tok(sym.ID, yytext());}
					case -68:
						break;
					case 68:
						{return tok(sym.ID, yytext());}
					case -69:
						break;
					case 69:
						{return tok(sym.ID, yytext());}
					case -70:
						break;
					case 70:
						{return tok(sym.ID, yytext());}
					case -71:
						break;
					case 71:
						{return tok(sym.ID, yytext());}
					case -72:
						break;
					case 72:
						{return tok(sym.ID, yytext());}
					case -73:
						break;
					case 73:
						{return tok(sym.ID, yytext());}
					case -74:
						break;
					case 74:
						{return tok(sym.ID, yytext());}
					case -75:
						break;
					case 75:
						{return tok(sym.ID, yytext());}
					case -76:
						break;
					case 76:
						{return tok(sym.ID, yytext());}
					case -77:
						break;
					case 77:
						{return tok(sym.ID, yytext());}
					case -78:
						break;
					case 78:
						{return tok(sym.ID, yytext());}
					case -79:
						break;
					case 79:
						{return tok(sym.ID, yytext());}
					case -80:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
					}
				}
			}
		}
	}
}
