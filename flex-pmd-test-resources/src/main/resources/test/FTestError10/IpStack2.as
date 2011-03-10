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
/* ************ COPYRIGHT AND CONFIDENTIALITY INFORMATION *****************
** Originale File : net.thomson.vs.device.IpStack
**  Copyright (c) 2008-2009  Thomson
**  All Rights Reserved
**
** This program contains proprietary information which is a trade
** secret of THOMSON and/or its affiliates and also is protected as
** an unpublished work under applicable Copyright laws. Recipient is
** to retain this program in confidence and is not permitted to use or
** make copies thereof other than as permitted in a written agreement
** with THOMSON, unless otherwise expressly allowed by applicable laws.
**************************************************************************/

import net.thomson.vs.device.IpInterface;
import net.thomson.vs.common.enum.Status;
/**
  * This class is used to manage the single IP stack.
  *
  * There is only one IP stack in the decoder that can address several IP interfaces.
  *
  * - Project: ThomAS
  * - Package: net.thomson.vs.device
  * - Class: IpStack
  * - File: IpStack.as
  */
intrinsic class net.thomson.vs.device.IpStack2
{
  
  /**
   * Retrieve all available IP interfaces
   * @param None No input parameter
   * @return An array containing all IpInterface instances
   * @see IpInterface
   */
  public function getAllInterfaces():Array;
}