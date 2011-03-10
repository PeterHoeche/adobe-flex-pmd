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
 
/*
   @(#) FileReferenceList.as

   Copyright (c) 2005-2007, Bluestreak Technology, Inc. All Rights Reserved.
*/

/**
   @ingroup core_api
   The FileReferenceList class provides features for enumerating directory files. The FileReferenceList object contains 
   an array of FileReference objects where each one is associated to a file in the directory. 
   
   This object does not implement the complete FileReferenceList class, as specified in the Flash&reg; Help panel. 
   
   The MachBlue Player modifies the FileReferenceList() constructor.
   
   @see The FileReference class and @b FileReferenceList() and the @b FileReferenceList class in the Flash&reg; Help panel.
*/
intrinsic class FileReferenceList
{	

 /// @cond hidden

  /**
   * This property is an array containing a reference to each file from a given directory. 
   * @see @b fileList in the Flash&reg; Help panel.
   */
   public var fileList:Array; 
   
 /// @endcond
   
  /**
   * @brief This is the FileReferenceList class contructor.
   *
   * This is the FileReferenceList class contructor.
   * @param localDirectoryPath - The local directory used to populate the object.
   * @return @li Nothing.
   * @see @b FileReferenceList() in the Flash&reg; Help panel.
   */
   public function FileReferenceList(localDirectoryPath : String) ;
   
} ;
