package org.calccedo.handlers;

class QuoteResult {
   int offsetA;
   int offsetZ;
   Number result;

   public QuoteResult(int offsetA, int offsetZ, Number result) {
      this.offsetA = offsetA;
      this.offsetZ = offsetZ;
      this.result = result;
   }
}
