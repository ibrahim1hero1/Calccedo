package org.calccedo.handlers;

import org.calccedo.configuration.Configuration;
import java.util.ArrayList;

class Eshortuct extends CalccedoHandler {
   StringBuilder leftTemp;
   char operandType;
   int leftOperandIndex;
   int rightOperandIndex;
   ArrayList<Character> list = new ArrayList();

   public Eshortuct() {
   }

   public Eshortuct(StringBuilder leftTemp, char operandType, int leftOperandIndex, int rightOperandIndex) {
      this.leftTemp = leftTemp;
      this.operandType = operandType;
      this.leftOperandIndex = leftOperandIndex;
      this.rightOperandIndex = rightOperandIndex;
   }

   public Eshortuct process(StringBuilder subformula, int Eindex) {
      StringBuilder leftTemp = new StringBuilder("E");
      char operandType = 0;
      int leftOperandIndex = 0;
      int rightOperandIndex = 0;
      boolean ignore = false;
      this.list.clear();
      this.list.add('/');
      this.list.add('%');
      this.list.add('*');
      this.list.add('^');
      this.list.add('(');
      this.list.add(')');

      int j;
      for(j = Eindex; j >= 0; --j) {
         if (!this.isNumber(subformula.charAt(j))) {
            if (this.isMinus(subformula.charAt(j)) && !ignore) {
               if (j == 1) {
                  leftOperandIndex = 0;
               } else {
                  leftOperandIndex = j;
               }

               leftTemp.insert(0, subformula.charAt(j));
               break;
            }

            if (this.isPlus(subformula.charAt(j))) {
               leftOperandIndex = j;
               break;
            }

            if (this.list.contains(subformula.charAt(j))) {
               leftOperandIndex = j;
               break;
            }
         } else {
            leftTemp.insert(0, subformula.charAt(j));
         }
      }

      for(j = Eindex; j < subformula.length(); ++j) {
         if (this.isNumber(subformula.charAt(j))) {
            leftTemp.append(subformula.charAt(j));
         } else if (this.isMinus(subformula.charAt(j)) && subformula.charAt(j - 1) == 'E' && !ignore) {
            leftTemp.append('-');
            ignore = true;
         } else {
            if (this.isPlus(subformula.charAt(j))) {
               operandType = '+';
               rightOperandIndex = j;
               break;
            }

            if (ignore && (this.isMinus(subformula.charAt(j)) || this.isPlus(subformula.charAt(j)))) {
               operandType = subformula.charAt(j);
               rightOperandIndex = j;
               break;
            }

            if (this.isMinus(subformula.charAt(j)) || this.isPlus(subformula.charAt(j))) {
               operandType = subformula.charAt(j);
               rightOperandIndex = j;
               break;
            }

            if (this.list.contains(subformula.charAt(j))) {
               operandType = subformula.charAt(j);
               rightOperandIndex = j;
               break;
            }
         }
      }

      if (Configuration.deepTracing) {
         System.out.println("leftTemp:" + leftTemp + "\noperandType:" + operandType + "\nleftOperandIndex" + leftOperandIndex + "\nrightOperandIndex" + rightOperandIndex + "\n");
      }

      return new Eshortuct(this.optemizeNumeric(leftTemp), operandType, leftOperandIndex, rightOperandIndex);
   }

   public boolean isMinus(char c) {
      return c == '-';
   }

   public boolean isPlus(char c) {
      return c == '+';
   }

   public StringBuilder optemizeNumeric(StringBuilder string_number) {
      if (string_number.substring(string_number.length() - 2, string_number.length()).equals(".0")) {
         string_number.replace(string_number.length() - 2, string_number.length(), "");
      }

      return string_number;
   }
}
