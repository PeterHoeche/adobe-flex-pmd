package
{
   import com.bnpp.msms.model.VO.ConfigVO;
   
   import de.polygonal.ds.HashMap;

   public class Looping
   {
      public function Looping()
      {
         for ( var i : int = 0; i < 10; i++ )
         {
            new Foo();
            while( true )
            {
               new Object();
            }
            for each ( var i : Object in list )
            {
               new Object();
            }
         }
         new Object();
         for each ( var i : Object in list )
         {
            new Foo();
            while( true )
            {
               new Object();
            }
            for each ( var i : Object in list )
            {
               var o : Object = new Object();
            }
         }
         while( true )
         {
            new Foo();
            while( true )
            {
               new Object();
            }
            for each ( var i : Object in list )
            {
               new Object();
            }
         }
      }
   }
}