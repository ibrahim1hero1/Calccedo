package org.calccedo.handlers;

interface Handler {
   String calculate(String var1);

   boolean isNumber(char var1);

   boolean isNumber(StringBuilder var1);

   boolean initValidation(String var1);

   StringBuilder optimizeFormula(StringBuilder var1);

   StringBuilder optimizeFormulaFromConstants(StringBuilder var1);

   StringBuilder optimizeFormulaFromVariables(StringBuilder var1);
}
