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
package com.adobe.ac.pmd.nodes;

/**
 * @author xagnetti
 */
public enum MetaData
{
   ARRAY_ELEMENT_TYPE("ArrayElementType"),
   BEFORE("Before"),
   BINDABLE("Bindable"),
   DEFAULT_PROPERTY("DefaultProperty"),
   DEPRECATED("Deprecated"),
   EFFECT("Effect"),
   EMBED("Embed"),
   EVENT("Event"),
   EXCLUDE("Exclude"),
   EXCLUDE_CLASS("Exclude"),
   ICON_FILE("IconFile"),
   INSPECTABLE("Inspectable"),
   INSTANCE_TYPE("InstanceType"),
   NON_COMITTING_CHANGE_EVENT("NonCommittingChangeEvent"),
   OTHER("Other"),
   REMOTE_CLASS("RemoteClass"),
   STYLE("Style"),
   TEST("Test"),
   TRANSIENT("Transient");

   /**
    * @param metaDataName
    * @return
    */
   public static MetaData create( final String metaDataName )
   {
      MetaData metaData = null;

      for ( final MetaData currentMetadata : values() )
      {
         if ( currentMetadata.toString().compareTo( metaDataName ) == 0 )
         {
            metaData = currentMetadata;
            break;
         }
      }
      if ( metaData == null )
      {
         metaData = MetaData.OTHER;
         metaData.name = metaDataName;
      }

      return metaData;
   }

   private String name;

   private MetaData( final String nameToBeSet )
   {
      name = nameToBeSet;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Enum#toString()
    */
   @Override
   public String toString()
   {
      return name;
   }
}
