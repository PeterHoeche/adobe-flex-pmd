/**
 *    Copyright (c) 2008. Adobe Systems Incorporated.
 *    All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in
 *        the documentation and/or other materials provided with the
 *        distribution.
 *      * Neither the name of Adobe Systems Incorporated nor the names of
 *        its contributors may be used to endorse or promote products derived
 *        from this software without specific prior written permission.
 *
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.adobe.ac.pmd.rules.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.sourceforge.pmd.PropertyDescriptor;
import net.sourceforge.pmd.properties.StringProperty;

import org.apache.tools.ant.filters.StringInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.adobe.ac.pmd.StackTraceUtils;
import com.adobe.ac.pmd.Violation;
import com.adobe.ac.pmd.files.AbstractFlexFile;
import com.adobe.ac.pmd.nodes.PackageNode;
import com.adobe.ac.pmd.rules.core.exceptions.UnspecifiedXPath;

import de.bokelberg.flex.parser.ASTToXMLConverter;
import de.bokelberg.flex.parser.Node;

public class XpathFlexRule extends AbstractFlexRule
{
   private static final Logger LOGGER              = Logger.getLogger( XpathFlexRule.class.getName() );
   private static final String XPATH_PROPERTY_NAME = "xpath";

   public boolean isConcernedByTheGivenFile( final AbstractFlexFile file )
   {
      return true;
   }

   public void setXPathExpression( final String xpathExpression )
   {
      setProperty( new StringProperty( XPATH_PROPERTY_NAME, "", "", 0 ),
                   xpathExpression );
   }

   @Override
   protected ViolationPriority getDefaultPriority()
   {
      return ViolationPriority.INFO;
   }

   @Override
   protected List< Violation > processFileBody( final PackageNode rootNode,
                                                final AbstractFlexFile file,
                                                final Map< String, AbstractFlexFile > files )
   {
      final List< Violation > violations = new ArrayList< Violation >();

      try
      {
         if ( rootNode != null )
         {
            final Document doc = createASTXmlDocument( rootNode.getInternalNode() );
            final XPathExpression xpathExpression = getXPathExpression();
            final NodeList violationsNode = ( NodeList ) xpathExpression.evaluate( doc,
                                                                                   XPathConstants.NODESET );

            for ( int i = 0; i < violationsNode.getLength(); i++ )
            {
               final org.w3c.dom.Node beginningNode = violationsNode.item( i );
               final Violation violation = computeViolation( beginningNode,
                                                             file );

               violations.add( violation );
            }
         }
      }
      catch ( final UnspecifiedXPath e )
      {
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
      catch ( final XPathExpressionException e )
      {
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
      catch ( final ParserConfigurationException e )
      {
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
      catch ( final SAXException e )
      {
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
      catch ( final IOException e )
      {
         LOGGER.warning( StackTraceUtils.print( e ) );
      }
      return violations;
   }

   @Override
   protected Map< String, PropertyDescriptor > propertiesByName()
   {
      final Map< String, PropertyDescriptor > properties = new HashMap< String, PropertyDescriptor >();

      properties.put( XPATH_PROPERTY_NAME,
                      new StringProperty( XPATH_PROPERTY_NAME, "", "", 0 ) );

      return properties;
   }

   private Violation computeViolation( final org.w3c.dom.Node beginningNode,
                                       final AbstractFlexFile file )
   {
      final org.w3c.dom.Node lastChild = beginningNode.getLastChild();

      final Violation violation = new Violation( new ViolationPosition( getLineValueInNode( beginningNode ),
                                                                        getLineValueInNode( lastChild ),
                                                                        getColumnValueInNode( beginningNode ),
                                                                        getColumnValueInNode( lastChild ) ), this, file );

      return violation;
   }

   private Document createASTXmlDocument( final Node rootNode ) throws ParserConfigurationException,
                                                               SAXException,
                                                               IOException
   {
      final String astXml = new ASTToXMLConverter().convert( rootNode );
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      factory.setNamespaceAware( true ); // never forget this!
      final DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse( new StringInputStream( astXml ) );
   }

   private Integer getColumnValueInNode( final org.w3c.dom.Node beginningNode )
   {
      final org.w3c.dom.Node beginningColumnNode = beginningNode.getAttributes().getNamedItem( "column" );
      final Integer beginningColumn = Integer.valueOf( beginningColumnNode.getNodeValue() );
      return beginningColumn;
   }

   private Integer getLineValueInNode( final org.w3c.dom.Node beginningNode )
   {
      final org.w3c.dom.Node beginningLineNode = beginningNode.getAttributes().getNamedItem( "line" );
      final Integer beginningLine = Integer.valueOf( beginningLineNode.getNodeValue() );
      return beginningLine;
   }

   private XPathExpression getXPathExpression() throws UnspecifiedXPath,
                                               XPathExpressionException
   {
      String xPathExpressionString;
      final XPathFactory factory = XPathFactory.newInstance();
      try
      {
         final PropertyDescriptor propertyDescriptor = propertyDescriptorFor( XPATH_PROPERTY_NAME );
         xPathExpressionString = getStringProperty( propertyDescriptor );
      }
      catch ( final IllegalArgumentException e )
      {
         throw new UnspecifiedXPath( e );
      }

      return factory.newXPath().compile( xPathExpressionString );
   }
}
