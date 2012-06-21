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
package com.adobe.ac.pmd.rules.parsley.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.adobe.ac.pmd.rules.parsley.utils.MetaDataTag.Location;

public final class ParsleyMetaData
{
   private ParsleyMetaData()
   {
      super();
   }
 
   private static final String                     	KEY                  = "key";

   private static final String                     	BUNDLE               = "bundle";

   private static final String                     	ORDER                = "order";

   private static final String                     	SINGLETON            = "singleton";

   private static final String                     	LAZY                 = "lazy";

   private static final String                     	MESSAGE_PROPERTIES   = "messageProperties";

   private static final String                     	PROPERTY             = "property";

   private static final String                     	TARGET_PROPERTY      = "targetProperty";

   private static final String                     	SCOPE                = "scope";

   private static final String                     	REQUIRED             = "required";

   private static final String                     	ERROR_EVENT          = "errorEvent";

   private static final String                     	COMPLETE_EVENT       = "completeEvent";

   private static final String                     	SELECTOR2            = "selector";

   private static final String                     	MESSAGE_PROPERTY     = "messageProperty";

   private static final String                     	TYPE                 = "type";

   private static final String                     	NAMES                = "names";

   private static final String                     	ID_PROPERTY          = "id";

   private static final String                     	METHOD               = "method";
   
   private static final String						OBJECT_ID			= "objectId";
   
   private static final String						MANAGED				= "managed";
   
   private static final String						CHANGE_EVENT		= "changeEvent";
   
   private static final String						PERSISTENT			= "persistent";
   
   private static final String						PHASE				= "phase";

   private static final MetaDataTag.Location[]     CLASS_LOCATION       = new MetaDataTag.Location[]
                                                                        { 
	   																		MetaDataTag.Location.CLASS_DECLARATION 
	   																	};

   private static final MetaDataTag.Location[]     FUNCTION_LOCATION    = new MetaDataTag.Location[]
                                                                        { 
	   																		MetaDataTag.Location.FUNCTION 
	   																	};

   private static final MetaDataTag.Location[]     ATTR_FCTION_LOCATION = new MetaDataTag.Location[]
                                                                       	{ 
	   																		MetaDataTag.Location.ATTRIBUTE,
	   																		MetaDataTag.Location.FUNCTION                           
                                                                       	};

   private static final MetaDataTag.Location[]     ATTRIBUTE_LOCATION   = new MetaDataTag.Location[]
                                                                        { 
	   																		MetaDataTag.Location.ATTRIBUTE 
	   																	};

   public static final MetaDataTag                 ASYNC_INIT           = new MetaDataTag( "AsyncInit",
                                                                                           	new String[]
                                                                                           	{ 
		   																						COMPLETE_EVENT,
		   																						ERROR_EVENT 
		   																					}, 
		   																					CLASS_LOCATION );
   
   public static final MetaDataTag                 COMMAND              = new MetaDataTag( "Command",
																				           		new String[]
																				        		{
		   																							TYPE,
		   																							MESSAGE_PROPERTIES,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							ORDER,
		   																							METHOD
																				        		},
																				        		FUNCTION_LOCATION );
   
   public static final MetaDataTag                 COMMAND_COMPLETE     = new MetaDataTag( "CommandComplete",
																						    	new String[]
																						    	{
		   																							TYPE,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							ORDER,
		   																							METHOD
																						    	},
																						    	FUNCTION_LOCATION );

   public static final MetaDataTag                 COMMAND_RESULT		= new MetaDataTag( "CommandResult",
		   																						new String[]
		   																						{
		   																							TYPE,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							ORDER,
		   																							METHOD
		   																						},
		   																						FUNCTION_LOCATION );
   
   public static final MetaDataTag                 COMMAND_STATUS		= new MetaDataTag( "CommandStatus",
		   																						new String[]
		   																						{
		   																							TYPE,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							ORDER,
		   																							PROPERTY
		   																						},
		   																						FUNCTION_LOCATION );

   public static final MetaDataTag                 COMMAND_ERROR		= new MetaDataTag( "CommandError",
		   																						new String[]
		   																						{
		   																							TYPE,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							ORDER,
		   																							METHOD
		   																						},
		   																						FUNCTION_LOCATION );


   public static final MetaDataTag                 DESTROY              = new MetaDataTag( "Destroy",
                                                                                           		new String[]
                                                                                           		{ 
		   																							METHOD 
		   																						},
		   																						FUNCTION_LOCATION );

   public static final MetaDataTag                 FACTORY              = new MetaDataTag( "Factory",
                                                                                           		new String[]
                                                                                           		{ 
		   																							METHOD 
		   																						},
		   																						FUNCTION_LOCATION );

   public static final MetaDataTag                 INIT                 = new MetaDataTag( "Init",
                                                                                           		new String[]
                                                                                           		{ 
		   																							METHOD 
                                                                                           		},
                                                                                           		FUNCTION_LOCATION );

   public static final MetaDataTag                 INJECT               = new MetaDataTag( "Inject",
                                                                                           		new String[]
                                                                                           		{ 
		   																							ID_PROPERTY,
		   																							REQUIRED                                                                   
                                                                                           		},
                                                                                           		ATTR_FCTION_LOCATION );

   public static final MetaDataTag                 INJECT_CONSTRUCTOR   = new MetaDataTag( "InjectConstructor",
                                                                                           		new String[]
                                                                                           		{},
                                                                                           		CLASS_LOCATION );

   public static final MetaDataTag                 INTERNAL             = new MetaDataTag( "Internal",
                                                                                           		new String[]
                                                                                           		{},
                                                                                           		ATTRIBUTE_LOCATION );

   public static final MetaDataTag                 MANAGED_EVENTS       = new MetaDataTag( "ManagedEvents",
                                                                                           		new String[]
                                                                                           		{
		   																							NAMES,
		   																							SCOPE                                                                      
                                                                                           		},
                                                                                           		CLASS_LOCATION );

   public static final MetaDataTag                 MESSAGE_BINDING      = new MetaDataTag( "MessageBinding",
                                                                                           		new String[]
                                                                                           		{ 
		   																							TYPE,
		   																							MESSAGE_PROPERTY,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							ORDER,
		   																							TARGET_PROPERTY                                                            
                                                                                           		},
                                                                                           		ATTRIBUTE_LOCATION );

   public static final MetaDataTag                 MESSAGE_DISPATCHER   = new MetaDataTag( "MessageDispatcher",
                                                                                           		new String[]
                                                                                           		{ 
		   																							PROPERTY,
		   																							SCOPE                                                                      
                                                                                           		},
                                                                                           		ATTRIBUTE_LOCATION );

   public static final MetaDataTag                 MESSAGE_ERROR        = new MetaDataTag( "MessageError",
                                                                                           		new String[]
                                                                                           		{ 
		   																							TYPE,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							ORDER,
		   																							METHOD                                                                     
                                                                                           		},
                                                                                           		FUNCTION_LOCATION );

   public static final MetaDataTag                 MESSAGE_HANDLER      = new MetaDataTag( "MessageHandler",
                                                                                           		new String[]
                                                                                           		{ 
		   																							TYPE,
		   																							MESSAGE_PROPERTIES,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							ORDER,
		   																							METHOD                                                                     
                                                                                           		},
                                                                                           		FUNCTION_LOCATION );
   
   public static final MetaDataTag				   MESSAGE_INTERCEPTOR  = new MetaDataTag( "MessageInterceptor",
           																						new String[]
           																						{ 
		   																							TYPE,
		   																							SELECTOR2,
		   																							SCOPE,
		   																							METHOD                                                                     
		   																						},
		   																						FUNCTION_LOCATION );

   public static final MetaDataTag                 OBJECT_DEFINITION    = new MetaDataTag( "ObjectDefinition",
                                                                                           		new String[]
                                                                                           		{ 
		   																							LAZY,
		   																							SINGLETON,
		   																							ID_PROPERTY,
		   																							ORDER                                                                      
                                                                                           		},
                                                                                           		ATTRIBUTE_LOCATION );
   
   public static final MetaDataTag					OBSERVE				= new MetaDataTag( "Observe",
		   																						new String[]
		   																						{
		   																							PHASE,
		   																							OBJECT_ID,
		   																							METHOD
		   																						},
		   																						FUNCTION_LOCATION );
   
   public static final MetaDataTag					PUBLISH				= new MetaDataTag( "Publish",
		   																						new String[]
		   																						{
		   																							OBJECT_ID,
		   																							SCOPE,
		   																							MANAGED,
		   																							CHANGE_EVENT,
		   																							PROPERTY
		   																						},
		   																						FUNCTION_LOCATION );
   
   public static final MetaDataTag					PUBLISH_SUBSCRIBE	= new MetaDataTag( "PublishSubscribe",
		   																						new String[]
		   																						{
		   																							OBJECT_ID,
		   																							SCOPE,
		   																							MANAGED,
		   																							PERSISTENT,
		   																							CHANGE_EVENT,
		   																							PROPERTY
		   																						},
		   																						FUNCTION_LOCATION );

   public static final MetaDataTag                 RESOURCE_BINDING     = new MetaDataTag( "ResourceBinding",
                                                                                           		new String[]
                                                                                           		{ 
		   																							BUNDLE,
		   																							KEY,
		   																							PROPERTY                                                                   
                                                                                           		},
                                                                                           		ATTRIBUTE_LOCATION );

   public static final MetaDataTag                 SELECTOR             = new MetaDataTag( "Selector",
                                                                                           		new String[]
                                                                                           		{},
                                                                                           		ATTRIBUTE_LOCATION );
   
   public static final MetaDataTag                 SUBSCRIBE             = new MetaDataTag( "Subscribe",
		   																						new String[]
		   																						{
		   																							OBJECT_ID,
		   																							SCOPE,
		   																							PROPERTY
		   																						},
		   																						ATTRIBUTE_LOCATION );

   public static final MetaDataTag                 TARGET               = new MetaDataTag( "Target",
                                                                                           		new String[]
                                                                                           		{},
                                                                                           		ATTRIBUTE_LOCATION );

   public static final MetaDataTag[]               ALL_TAGS             = { INJECT,
	   																		INJECT_CONSTRUCTOR,
	   																		FACTORY,
	   																		INIT,
	   																		DESTROY,
	   																		OBSERVE,
	   																		ASYNC_INIT,
	   																		MESSAGE_DISPATCHER,
	   																		MANAGED_EVENTS,
	   																		MESSAGE_HANDLER,
	   																		MESSAGE_INTERCEPTOR,
	   																		MESSAGE_BINDING,
	   																		MESSAGE_ERROR,
	   																		COMMAND,
	   																		COMMAND_COMPLETE,
	   																		COMMAND_RESULT,
	   																		COMMAND_STATUS,
	   																		COMMAND_ERROR,
	   																		PUBLISH,
	   																		SUBSCRIBE,
	   																		PUBLISH_SUBSCRIBE,
	   																		RESOURCE_BINDING,
	   																		SELECTOR,
	   																		TARGET,
	   																		INTERNAL,
	   																		OBJECT_DEFINITION,
	   																		};
	   																		

   private static final Map< String, MetaDataTag > TAG_MAP              = new LinkedHashMap< String, MetaDataTag >();

   static
   {
      TAG_MAP.put( INJECT.getName(), INJECT );
      TAG_MAP.put( INJECT_CONSTRUCTOR.getName(), INJECT_CONSTRUCTOR );
      TAG_MAP.put( FACTORY.getName(), FACTORY );
      TAG_MAP.put( INIT.getName(), INIT );
      TAG_MAP.put( DESTROY.getName(), DESTROY );
      TAG_MAP.put( OBSERVE.getName(), OBSERVE );
      TAG_MAP.put( ASYNC_INIT.getName(), ASYNC_INIT );
      TAG_MAP.put( MESSAGE_DISPATCHER.getName(), MESSAGE_DISPATCHER );
      TAG_MAP.put( MANAGED_EVENTS.getName(), MANAGED_EVENTS );
      TAG_MAP.put( MESSAGE_HANDLER.getName(), MESSAGE_HANDLER );
      TAG_MAP.put( MESSAGE_INTERCEPTOR.getName(), MESSAGE_INTERCEPTOR );
      TAG_MAP.put( MESSAGE_BINDING.getName(), MESSAGE_BINDING );
      TAG_MAP.put( MESSAGE_ERROR.getName(), MESSAGE_ERROR );
      TAG_MAP.put( COMMAND.getName(), COMMAND );
      TAG_MAP.put( COMMAND_COMPLETE.getName(), COMMAND_COMPLETE );
      TAG_MAP.put( COMMAND_RESULT.getName(), COMMAND_RESULT );
      TAG_MAP.put( COMMAND_STATUS.getName(), COMMAND_STATUS );
      TAG_MAP.put( COMMAND_ERROR.getName(), COMMAND_ERROR );
      TAG_MAP.put( PUBLISH.getName(), PUBLISH );
      TAG_MAP.put( SUBSCRIBE.getName(), SUBSCRIBE );
      TAG_MAP.put( PUBLISH_SUBSCRIBE.getName(), PUBLISH_SUBSCRIBE );
      TAG_MAP.put( RESOURCE_BINDING.getName(), RESOURCE_BINDING );
      TAG_MAP.put( SELECTOR.getName(), SELECTOR );
      TAG_MAP.put( TARGET.getName(), TARGET );
      TAG_MAP.put( INTERNAL.getName(), INTERNAL );
      TAG_MAP.put( OBJECT_DEFINITION.getName(), OBJECT_DEFINITION );
   }

   public static MetaDataTag getTag( final String name )
   {
      return TAG_MAP.get( name );
   }

   public static boolean isParsleyMetaData( final String name )
   {
      return TAG_MAP.containsKey( name );
   }
   
   public static Map< String, MetaDataTag > getPossibleMetaDataFromLocation( Location location )
   {
      Map< String, MetaDataTag > tags = new HashMap< String, MetaDataTag >();
      
      for ( MetaDataTag metaDataTag : ALL_TAGS )
      {
         if ( metaDataTag.getPlacedOn().contains( location ) )
         {
            tags.put( metaDataTag.getName(), metaDataTag );
         }
      }
      return tags;
   }
}