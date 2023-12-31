package org.calccedo.calccedo;

import org.calccedo.configuration.Configuration;

public abstract class Calccedo extends Configuration {
   public Calccedo() {
      super.init();
   }

   protected void enableDeepTrace(boolean enabled) {
      super.enableDeepTrace(enabled);
   }

   public String toString() {
      return " Calccedo Library is calculation engine Framework library designed to help developers for building applications that need complecated Mathematical equations,\n  Calccedo is not just library, it's work as Interpreter and act as AI engines";
   }
}
