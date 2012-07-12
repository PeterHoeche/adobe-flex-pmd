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
package com.adobe.ac.pmd.rules.mxml;

import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import net.sourceforge.pmd.PropertyDescriptor;

import org.w3c.dom.Document;

import com.adobe.ac.pmd.IFlexViolation;
import com.adobe.ac.pmd.rules.core.AbstractXpathRelatedRule;
import com.adobe.ac.pmd.rules.core.ViolationPosition;
import com.adobe.ac.pmd.rules.core.ViolationPriority;
import com.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule;

/**
 * @author xagnetti
 */
public class TooManyStatesInMxmlRule extends AbstractXpathRelatedRule implements IThresholdedRule
{
   private Double statesNb = 0.0;

   /*
    * (non-Javadoc)
    * @seecom.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule#
    * getActualValueForTheCurrentViolation()
    */
   public int getActualValueForTheCurrentViolation()
   {
      return statesNb.intValue();
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule#getDefaultThreshold
    * ()
    */
   public int getDefaultThreshold()
   {
      return 5;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule#getThreshold()
    */
   public int getThreshold()
   {
      return getIntProperty( propertyDescriptorFor( getThresholdName() ) );
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.thresholded.IThresholdedRule#getThresholdName
    * ()
    */
   public final String getThresholdName()
   {
      return MAXIMUM;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractXpathRelatedRule#evaluate(org.w3c.
    * dom.Document, javax.xml.xpath.XPath)
    */
   @Override
   protected Object evaluate( final Document doc,
                              final XPath xPath ) throws XPathExpressionException
   {
      return xPath.evaluate( getXPathExpression(),
                             doc,
                             XPathConstants.NUMBER );
   }

   /*
    * (non-Javadoc)
    * @see com.adobe.ac.pmd.rules.core.AbstractFlexRule#getDefaultPriority()
    */
   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.NORMAL;
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractXpathRelatedRule#getXPathExpression()
    */
   @Override
   protected String getXPathExpression()
   {
      return "count(//mx:states/*)";
   }

   /*
    * (non-Javadoc)
    * @see
    * com.adobe.ac.pmd.rules.core.AbstractXpathRelatedRule#onEvaluated(java.
    * util.List, org.w3c.dom.Document, javax.xml.xpath.XPath)
    */
   @Override
   protected void onEvaluated( final List< IFlexViolation > violations,
                               final Document doc,
                               final XPath xPath ) throws XPathExpressionException
   {
      statesNb = ( Double ) evaluate( doc,
                                      xPath );

      if ( statesNb >= getThreshold() )
      {
         xPath.evaluate( "//mx:states/*",
                         doc,
                         XPathConstants.NODESET );

         addViolation( violations,
                       ViolationPosition.create( 1,
                                                 1,
                                                 0,
                                                 0 ),
                       String.valueOf( getActualValueForTheCurrentViolation() ) );
      }
   }

   /*
    * (non-Javadoc)
    * @see net.sourceforge.pmd.CommonAbstractRule#propertiesByName()
    */
   @Override
   protected final Map< String, PropertyDescriptor > propertiesByName()
   {
      return getThresholdedRuleProperties( this );
   }
}
