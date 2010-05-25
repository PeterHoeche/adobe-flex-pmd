/**
 *    Copyright (c) 2009, Adobe Systems, Incorporated
 *    All rights reserved.
 *
 *    Redistribution  and  use  in  source  and  binary  forms, with or without
 *    modification,  are  permitted  provided  that  the  following  conditions
 *    are met:
 *
 *      * Redistributions  of  source  code  must  retain  the  above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions  in  binary  form  must reproduce the above copyright
 *        notice,  this  list  of  conditions  and  the following disclaimer in
 *        the    documentation   and/or   other  materials  provided  with  the
 *        distribution.
 *      * Neither the name of the Adobe Systems, Incorporated. nor the names of
 *        its  contributors  may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS  SOFTWARE  IS  PROVIDED  BY THE  COPYRIGHT  HOLDERS AND CONTRIBUTORS
 *    "AS IS"  AND  ANY  EXPRESS  OR  IMPLIED  WARRANTIES,  INCLUDING,  BUT NOT
 *    LIMITED  TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,  INCIDENTAL,  SPECIAL,
 *    EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES  (INCLUDING,  BUT  NOT  LIMITED TO,
 *    PROCUREMENT  OF  SUBSTITUTE   GOODS  OR   SERVICES;  LOSS  OF  USE,  DATA,
 *    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY,  WHETHER  IN  CONTRACT,  STRICT  LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE  OR  OTHERWISE)  ARISING  IN  ANY  WAY  OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.rules.core;

import java.util.Formatter;

import org.apache.commons.lang.StringUtils;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.files.IFlexFile;

/**
 * @author xagnetti
 */
public final class Violation implements IFlexViolation
{
   public static final String RULESET_CREATOR_URL = "http://opensource.adobe.com/svn/opensource/"
                                                        + "flexpmd/bin/flex-pmd-ruleset-creator.html?rule=";
   private final int          beginColumn;
   private final int          beginLine;
   private int                endColumn;
   private final int          endLine;
   private final IFlexFile    file;
   private final IFlexRule    rule;
   private String             ruleMessage         = "";

   /**
    * @param position
    * @param violatedRule
    * @param violatedFile
    */
   public Violation( final ViolationPosition position,
                     final IFlexRule violatedRule,
                     final IFlexFile violatedFile )
   {
      beginLine = position.getBeginLine();
      endLine = position.getEndLine();
      beginColumn = position.getBeginColumn();
      endColumn = position.getEndColumn();
      rule = violatedRule;
      file = violatedFile;

      if ( violatedRule != null )
      {
         ruleMessage = violatedRule.getMessage() == null ? ""
                                                        : violatedRule.getMessage();
      }
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.IFlexViolation#appendToMessage(java.lang.String)
    */
   public void appendToMessage( final String messageToAppend )
   {
      ruleMessage += messageToAppend;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo( final IFlexViolation otherViolation ) // NO_UCD
   {
      int res;
      final int priorityOrder = getPrioriyOrder( otherViolation );

      if ( priorityOrder == 0 )
      {
         res = getLinePriority( otherViolation );
      }
      else
      {
         res = priorityOrder;
      }
      return res;
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getBeginColumn()
    */
   public int getBeginColumn()
   {
      return beginColumn;
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getBeginLine()
    */
   public int getBeginLine()
   {
      return beginLine;
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getClassName()
    */
   public String getClassName()
   {
      return "";
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getDescription()
    */
   public String getDescription()
   {
      return ruleMessage;
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getEndColumn()
    */
   public int getEndColumn()
   {
      return endColumn;
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getEndLine()
    */
   public int getEndLine()
   {
      return endLine;
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getFilename()
    */
   public String getFilename()
   {
      return file.getFullyQualifiedName();
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getMethodName()
    */
   public String getMethodName()
   {
      return "";
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getPackageName()
    */
   public String getPackageName()
   {
      return file.getPackageName();
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getRule()
    */
   public IFlexRule getRule()
   {
      return rule;
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.IFlexViolation#getRuleMessage()
    */
   public String getRuleMessage()
   {
      return ruleMessage.endsWith( "." ) ? ruleMessage.substring( 0,
                                                                  ruleMessage.length() - 1 )
                                        : ruleMessage;
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#getVariableName()
    */
   public String getVariableName()
   {
      return "";
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.IRuleViolation#isSuppressed()
    */
   public boolean isSuppressed()
   {
      return false;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.IFlexViolation#replacePlaceholderInMessage(java.lang.
    * String, int)
    */
   public void replacePlaceholderInMessage( final String replacement,
                                            final int index )
   {
      ruleMessage = ruleMessage.replace( "{"
                                               + index + "}",
                                         replacement );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.IFlexViolation#setEndColumn(int)
    */
   public void setEndColumn( final int column )
   {
      endColumn = column;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.IFlexViolation#toXmlString(com.adobe.ac.pmd.files.IFlexFile
    * , java.lang.String)
    */
   public String toXmlString( final IFlexFile violatedFile,
                              final String ruleSetName )
   {
      final Formatter formatter = new Formatter();

      if ( rule != null )
      {
         final StringBuffer message = new StringBuffer( getRuleMessage() );

         formatter.format( "      <violation beginline=\"%d\" "
                                 + "endline=\"%d\" begincolumn=\"%d\" " + "endcolumn=\"%d\" rule=\"%s\" "
                                 + "ruleset=\"%s\" package=\"%s\" " + "class=\"%s\" externalInfoUrl=\"%s\" "
                                 + "priority=\"%s\">%s</violation>" + getNewLine(),
                           beginLine,
                           endLine,
                           beginColumn,
                           endColumn,
                           rule.getRuleName(),
                           ruleSetName,
                           violatedFile.getPackageName(),
                           violatedFile.getClassName(),
                           RULESET_CREATOR_URL
                                 + extractShortName( rule.getName() ),
                           rule.getPriority(),
                           message );
      }
      return formatter.toString();
   }

   /**
    * @return
    */
   String getNewLine()
   {
      return System.getProperty( "line.separator" );
   }

   private String extractShortName( final String name )
   {
      return StringUtils.substringAfterLast( name,
                                             "." );
   }

   private int getLinePriority( final IFlexViolation otherViolation )
   {
      int res;

      if ( beginLine > otherViolation.getBeginLine() )
      {
         res = 1;
      }
      else if ( beginLine < otherViolation.getBeginLine() )
      {
         res = -1;
      }
      else
      {
         res = 0;
      }

      return res;
   }

   private int getPrioriyOrder( final IFlexViolation otherViolation )
   {
      int res;

      if ( rule.getPriority() > otherViolation.getRule().getPriority() )
      {
         res = 1;
      }
      else if ( rule.getPriority() < otherViolation.getRule().getPriority() )
      {
         res = -1;
      }
      else
      {
         res = 0;
      }

      return res;
   }
}