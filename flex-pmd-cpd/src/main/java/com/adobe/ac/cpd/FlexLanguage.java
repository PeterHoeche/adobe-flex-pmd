package com.adobe.ac.cpd;

import net.sourceforge.pmd.cpd.AbstractLanguage;
import net.sourceforge.pmd.cpd.Language;

public class FlexLanguage extends AbstractLanguage implements Language
{
   public FlexLanguage()
   {
      super( new FlexTokenizer(), "as", "mxml" );
   }
}
