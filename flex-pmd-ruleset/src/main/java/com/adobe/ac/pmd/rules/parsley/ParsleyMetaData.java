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
package com.adobe.ac.pmd.rules.parsley;

import java.util.HashMap;
import java.util.Map;

public class ParsleyMetaData
{
   public static final MetaDataTag                 ASYNC_INIT          = new MetaDataTag( "AsyncInit",
                                                                                          new String[]
                                                                                          { "completeEvent",
               "errorEvent"                                                              },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.CLASS_DECLARATION } );

   public static final MetaDataTag                 DESTROY             = new MetaDataTag( "Destroy",
                                                                                          new String[]
                                                                                          { "method" },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.FUNCTION } );

   public static final MetaDataTag                 FACTORY             = new MetaDataTag( "Factory",
                                                                                          new String[]
                                                                                          { "method" },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.FUNCTION } );

   public static final MetaDataTag                 INIT                = new MetaDataTag( "Init",
                                                                                          new String[]
                                                                                          { "method" },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.FUNCTION } );

   public static final MetaDataTag                 INJECT              = new MetaDataTag( "Inject",
                                                                                          new String[]
                                                                                          { "id",
               "required"                                                                },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.ATTRIBUTE,
               MetaDataTag.Location.FUNCTION                                             } );

   public static final MetaDataTag                 INJECT_CONSTRUCTOR  = new MetaDataTag( "InjectConstructor",
                                                                                          new String[]
                                                                                          {},
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.CLASS_DECLARATION } );

   public static final MetaDataTag                 INTERNAL            = new MetaDataTag( "Internal",
                                                                                          new String[]
                                                                                          {},
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.ATTRIBUTE } );

   public static final MetaDataTag                 MANAGED_EVENTS      = new MetaDataTag( "ManagedEvents",
                                                                                          new String[]
                                                                                          { "names",
               "scope"                                                                   },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.CLASS_DECLARATION } );

   public static final MetaDataTag                 MESSAGE_BINDING     = new MetaDataTag( "MessageBinding",
                                                                                          new String[]
                                                                                          { "type",
               "messageProperty",
               "selector",
               "scope",
               "targetProperty"                                                          },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.ATTRIBUTE } );

   public static final MetaDataTag                 MESSAGE_DISPATCHER  = new MetaDataTag( "MessageDispatcher",
                                                                                          new String[]
                                                                                          { "property",
               "scope"                                                                   },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.ATTRIBUTE } );

   public static final MetaDataTag                 MESSAGE_ERROR       = new MetaDataTag( "MessageError",
                                                                                          new String[]
                                                                                          { "type",
               "selector",
               "scope",
               "method"                                                                  },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.FUNCTION } );

   public static final MetaDataTag                 MESSAGE_HANDLER     = new MetaDataTag( "MessageHandler",
                                                                                          new String[]
                                                                                          { "type",
               "messageProperties",
               "selector",
               "scope",
               "method"                                                                  },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.FUNCTION } );

   public static final MetaDataTag                 MESSAGE_INTERCEPTOR = new MetaDataTag( "MessageInterceptor",
                                                                                          new String[]
                                                                                          { "type",
               "selector",
               "scope",
               "method"                                                                  },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.FUNCTION } );

   public static final MetaDataTag                 OBJECT_DEFINITION   = new MetaDataTag( "ObjectDefinition",
                                                                                          new String[]
                                                                                          { "lazy",
               "singleton",
               "id",
               "order"                                                                   },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.ATTRIBUTE } );

   public static final MetaDataTag                 RESOURCE_BINDING    = new MetaDataTag( "ResourceBinding",
                                                                                          new String[]
                                                                                          { "bundle",
               "key",
               "property"                                                                },
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.ATTRIBUTE } );

   public static final MetaDataTag                 SELECTOR            = new MetaDataTag( "Selector",
                                                                                          new String[]
                                                                                          {},
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.ATTRIBUTE } );

   public static final MetaDataTag                 TARGET              = new MetaDataTag( "Target",
                                                                                          new String[]
                                                                                          {},
                                                                                          new MetaDataTag.Location[]
                                                                                          { MetaDataTag.Location.ATTRIBUTE } );

   public static final MetaDataTag[]               ALL_TAGS            =
                                                                       { INJECT,
               INJECT_CONSTRUCTOR,
               MANAGED_EVENTS,
               MESSAGE_BINDING,
               MESSAGE_DISPATCHER,
               MESSAGE_ERROR,
               MESSAGE_HANDLER,
               MESSAGE_INTERCEPTOR,
               ASYNC_INIT,
               INIT,
               DESTROY,
               RESOURCE_BINDING,
               FACTORY,
               OBJECT_DEFINITION,
               TARGET,
               INTERNAL                                               };

   private static final Map< String, MetaDataTag > TAG_MAP             = new HashMap< String, MetaDataTag >();

   static
   {
      TAG_MAP.put( INJECT.getName(),
                   INJECT );
      TAG_MAP.put( INJECT_CONSTRUCTOR.getName(),
                   INJECT_CONSTRUCTOR );
      TAG_MAP.put( MANAGED_EVENTS.getName(),
                   MANAGED_EVENTS );
      TAG_MAP.put( MESSAGE_BINDING.getName(),
                   MESSAGE_BINDING );
      TAG_MAP.put( MESSAGE_DISPATCHER.getName(),
                   MESSAGE_DISPATCHER );
      TAG_MAP.put( MESSAGE_ERROR.getName(),
                   MESSAGE_ERROR );
      TAG_MAP.put( MESSAGE_HANDLER.getName(),
                   MESSAGE_HANDLER );
      TAG_MAP.put( MESSAGE_INTERCEPTOR.getName(),
                   MESSAGE_INTERCEPTOR );
      TAG_MAP.put( ASYNC_INIT.getName(),
                   ASYNC_INIT );
      TAG_MAP.put( INIT.getName(),
                   INIT );
      TAG_MAP.put( DESTROY.getName(),
                   DESTROY );
      TAG_MAP.put( RESOURCE_BINDING.getName(),
                   RESOURCE_BINDING );
      TAG_MAP.put( FACTORY.getName(),
                   FACTORY );
      TAG_MAP.put( OBJECT_DEFINITION.getName(),
                   OBJECT_DEFINITION );
      TAG_MAP.put( TARGET.getName(),
                   TARGET );
      TAG_MAP.put( INTERNAL.getName(),
                   INTERNAL );
   }

   public static MetaDataTag getTag( final String name )
   {
      return TAG_MAP.get( name );
   }

   public static boolean isParsleyMetaData( final String name )
   {
      return TAG_MAP.containsKey( name );
   }
}