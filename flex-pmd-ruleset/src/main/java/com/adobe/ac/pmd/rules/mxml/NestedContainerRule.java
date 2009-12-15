package com.adobe.ac.pmd.rules.mxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.rules.core.AbstractFlexRule;
import com.adobe.ac.pmd.rules.core.ViolationPriority;

public class NestedContainerRule extends AbstractFlexRule
{
   public Document loadDocument() throws DocumentException,
                                 FileNotFoundException
   {
      return new SAXReader().read( new FileInputStream( new File( getCurrentFile().getFilePath() ) ) );
   }

   @Override
   protected List< IFlexViolation > findViolationsInCurrentFile()
   {
      final List< IFlexViolation > violations = new ArrayList< IFlexViolation >();
      try
      {
         final Document document = loadDocument();
      }
      catch ( FileNotFoundException e )
      {
         e.printStackTrace();
      }
      catch ( DocumentException e )
      {
         e.printStackTrace();
      }

      return violations;
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   @Override
   protected boolean isConcernedByTheCurrentFile()
   {
      return getCurrentFile().isMxml();
   }
}
