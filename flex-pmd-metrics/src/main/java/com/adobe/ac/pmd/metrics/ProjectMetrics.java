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
package com.adobe.ac.pmd.metrics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class ProjectMetrics
{
   private AverageFunctionMetrics        averageFunctions;
   private AverageClassMetrics           averageObjects;
   private final List< ClassMetrics >    classMetrics;
   private final String                  date;
   private final List< FunctionMetrics > functionMetrics;
   private final List< PackageMetrics >  packageMetrics;
   private final String                  time;
   private TotalPackageMetrics           totalPackages;

   public ProjectMetrics()
   {
      super();

      final Date now = new Date();

      date = new SimpleDateFormat( "yyyy-M-d", Locale.US ).format( now );
      time = new SimpleDateFormat( "k:m:s", Locale.US ).format( now );
      classMetrics = new ArrayList< ClassMetrics >();
      functionMetrics = new ArrayList< FunctionMetrics >();
      packageMetrics = new ArrayList< PackageMetrics >();
   }

   public AverageFunctionMetrics getAverageFunctions()
   {
      return averageFunctions;
   }

   public AverageClassMetrics getAverageObjects()
   {
      return averageObjects;
   }

   public List< ClassMetrics > getClasses()
   {
      return classMetrics;
   }

   public List< ClassMetrics > getClassMetrics()
   {
      return classMetrics;
   }

   public String getDate()
   {
      return date;
   }

   public List< FunctionMetrics > getFunctionMetrics()
   {
      return functionMetrics;
   }

   public List< FunctionMetrics > getFunctions()
   {
      return functionMetrics;
   }

   public List< PackageMetrics > getPackageMetrics()
   {
      return packageMetrics;
   }

   public List< PackageMetrics > getPackages()
   {
      return packageMetrics;
   }

   public String getTime()
   {
      return time;
   }

   public TotalPackageMetrics getTotalPackages()
   {
      return totalPackages;
   }

   public void setAverageFunctions( final AverageFunctionMetrics averageFunctionsToBeSet )
   {
      averageFunctions = averageFunctionsToBeSet;
   }

   public void setAverageObjects( final AverageClassMetrics averageObjectsToBeSet )
   {
      averageObjects = averageObjectsToBeSet;
   }

   public void setTotalPackages( final TotalPackageMetrics totalPackagesToBeSet )
   {
      totalPackages = totalPackagesToBeSet;
   }
}
