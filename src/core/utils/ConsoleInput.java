package core.utils;

import java.io.*;

public class ConsoleInput
{
   InputStreamReader isr = new InputStreamReader(System.in);
   StreamTokenizer  st = new StreamTokenizer(isr);

public static char readChar( String prompt)
   {
   System.out.print( prompt + ":");
   return readChar();
   }

public static char readChar()
   {
   char ch = ' ';
   try { 
       ch = (char)System.in.read();
       }
   catch ( Exception e ) {};
   return ch;
   }

public static String readLine()
   {
   String line = "";
   char ch;

   ch = readChar();
   while ((ch != '\n') && (ch != '\r')) {  // Windows is \r\n, Linux is just \n
      line += ch;
      ch = readChar();
      }
   if (ch == '\r')
      ch = readChar();  // Eat the trailing \n in Windows

   return line;
   }

public static String readLine( String prompt)
   {
   String line = "";
   char ch;
   System.out.print(prompt);

   ch = readChar();
   while ((ch != '\n') && (ch != '\r')) {  // Windows is \r\n, Linux is just \n
      line += ch;
      ch = readChar();
      }
   if (ch == '\r')
      ch = readChar();  // Eat the trailing \n in Windows

   return line;
   }

public static void clearLine()
   {
   char ch;
   do {
      ch = readChar();
      } while ( ch != '\n');
   }

public static String readWord( String prompt)
   {
   System.out.print( prompt + ":");
   return readWord();
   }

public static String readWord()
   {
   InputStreamReader isr = new InputStreamReader(System.in);
   StreamTokenizer  st = new StreamTokenizer(isr);
   String word = "";

   try {
       if ( st.nextToken() == st.TT_WORD )
          word = st.sval;
       }
   catch( IOException e ) {};
       
   return word;
   }

public static double readDouble( String prompt)
   {
   System.out.print( prompt + ":");
   return readDouble();
   }

public static double readDouble()
   {
   InputStreamReader isr = new InputStreamReader(System.in);
   StreamTokenizer  st = new StreamTokenizer(isr);
   double number = -1.0;
   boolean   gotNumber = false;

   while ( !gotNumber )
      {
      try {
          if ( st.nextToken() == st.TT_NUMBER )
              {
              number = st.nval;
              gotNumber = true;
              }
          else
             {
             System.out.println("Non Numeric Value Entered for Input");
             System.out.println("Try Again");
             };
          }
      catch( IOException e ) {};
      }

   return number;
   }

public static int readInt( String prompt)
   {
   System.out.print( prompt + ":");
   return readInt();
   }

public static int readInt()
   {
   InputStreamReader isr = new InputStreamReader(System.in);
   StreamTokenizer  st = new StreamTokenizer(isr);
   Double tmp;
   int number = -1;
   boolean   gotNumber = false;

   while ( !gotNumber )
      {
      try {
          if ( st.nextToken() == st.TT_NUMBER )
              {
              tmp = new Double( st.nval);
              number = tmp.intValue();
              gotNumber = true;
              }
          else
             {
             System.out.println("Non Integer Value Entered for Input");
             System.out.println("Try Again");
             };
          }
      catch( IOException e ) {};
      }

   return number;
   }
}
