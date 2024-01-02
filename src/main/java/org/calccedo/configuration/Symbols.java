package org.calccedo.configuration;

import java.util.ArrayList;

public class Symbols {
   public static ArrayList<Character> postfixSymbolsList = new ArrayList();
   public static ArrayList<Character> prefixSymbolsList = new ArrayList();
   public static ArrayList<Character> basicOperatorsList = new ArrayList();
   public static ArrayList<Character> plus_minus_OperatorsList = new ArrayList();

   static boolean addPostfixSymbol(char c) {
      if (!postfixSymbolsList.contains(c)) {
         postfixSymbolsList.add(c);
         return true;
      } else {
         return false;
      }
   }

   static boolean removePostfixSymbol(char c) {
      postfixSymbolsList.remove(postfixSymbolsList.indexOf(c));
      return true;
   }

   static boolean addPrefixSymbol(char c) {
      if (!prefixSymbolsList.contains(c)) {
         prefixSymbolsList.add(c);
         return true;
      } else {
         return false;
      }
   }

   static boolean removePrefixSymbol(char c) {
      prefixSymbolsList.remove(prefixSymbolsList.indexOf(c));
      return true;
   }

   static void configurePostfixSymbols() {
      addPostfixSymbol('l');
      addPostfixSymbol('<');
      addPostfixSymbol('(');
   }

   static void configurePrefixSymbols() {
      addPrefixSymbol(')');
      addPrefixSymbol('>');
      addPrefixSymbol('.');
      addPrefixSymbol('0');
      addPrefixSymbol('1');
      addPrefixSymbol('2');
      addPrefixSymbol('3');
      addPrefixSymbol('4');
      addPrefixSymbol('5');
      addPrefixSymbol('6');
      addPrefixSymbol('7');
      addPrefixSymbol('8');
      addPrefixSymbol('9');
   }

   static void configureBasicOperators() {
      basicOperatorsList.add('/');
      basicOperatorsList.add('%');
      basicOperatorsList.add('*');
      basicOperatorsList.add('+');
      basicOperatorsList.add('-');
      basicOperatorsList.add('^');
      basicOperatorsList.add('(');
      basicOperatorsList.add(')');
      basicOperatorsList.add(',');
   }

   static void configurePlusMinusOperators() {
      plus_minus_OperatorsList.add('+');
      plus_minus_OperatorsList.add('-');
   }
}
