package org.calccedo.handlers;

class Quote {
   String tokenName;
   String subformula;
   int offsetA;
   int offsetZ;

   public Quote(String subformula, int offsetA, int offsetZ) {
      this.subformula = subformula;
      this.offsetA = offsetA;
      this.offsetZ = offsetZ;
   }

   public Quote(String tokenName, String subformula, int offsetA, int offsetZ) {
      this.tokenName = tokenName;
      this.subformula = subformula;
      this.offsetA = offsetA;
      this.offsetZ = offsetZ;
   }
}
