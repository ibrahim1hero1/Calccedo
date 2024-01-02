package org.calccedo.handlers;

class Semantic {
   String name;
   double value;

   public Semantic(String operandName, double value) {
      this.name = operandName;
      this.value = value;
   }

   public String getOperandName() {
      return this.name;
   }

   public void setOperandName(String operandName) {
      this.name = operandName;
   }

   public double getValue() {
      return this.value;
   }

   public void setValue(double value) {
      this.value = value;
   }
}
