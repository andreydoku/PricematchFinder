package products;


public class PscGroup {
	
	
	
	
	
	
//	public static PscGroup[] LAPTOP_PSCS = {      	null                                                                                                               ,
//													new PscGroup(   new Psc( "6092928" , 2 ,  99.99 , 10.98 )   ,   new Psc( "6092958" , 3 , 129.99 , 24.05 )   )      , 
//	      											new PscGroup(   new Psc( "6092978" , 2 , 119.99 , 16.01 )   ,   new Psc( "6093018" , 3 , 149.99 , 25.07 )   )      };
//	
	public static PscGroup[] LAPTOP_PSCS = {      	null                                                                                                                ,
													new PscGroup(   new Psc( "6092908" , 2 ,  69.99 ,  7.89 )   ,   new Psc( "9143219" , 3 ,  99.99 , 20.00  )   )      ,
													new PscGroup(   new Psc( "6092928" , 2 ,  99.99 , 10.98 )   ,   new Psc( "6092958" , 3 , 129.99 , 24.05  )   )      ,
													new PscGroup(   new Psc( "6092978" , 2 , 119.99 , 16.01 )   ,   new Psc( "6093018" , 3 , 149.99 , 25.07  )   )      ,
													new PscGroup(   new Psc( "6093028" , 2 , 139.99 , 19.32 )   ,   new Psc( "6093048" , 3 , 169.99 , 34.00  )   )      ,
													new PscGroup(   new Psc( "6093158" , 2 , 159.99 , 22.78 )   ,   new Psc( "6093178" , 3 , 189.99 , 17.15  )   )      ,
													new PscGroup(   new Psc( "6093188" , 2 , 179.99 , 19.93 )   ,   new Psc( "6093218" , 3 , 209.99 , 42.00  )   )      ,
													new PscGroup(   new Psc( "6093228" , 2 , 199.99 , 19.37 )   ,   new Psc( "6093238" , 3 , 229.99 , 46.00  )   )      ,
													new PscGroup(   new Psc( "6093248" , 2 , 219.99 , 15.21 )   ,   new Psc( "6093258" , 3 , 249.99 , 50.00  )   )      ,
													new PscGroup(   new Psc( "1714797" , 2 , 259.99 , 83.51 )   ,   new Psc( "1714801" , 3 , 299.99 , 153.84 )   )      ,
													new PscGroup(   new Psc( "1714823" , 2 , 309.99 , 00.00 )   ,   new Psc( "1714834" , 3 , 399.99 , 000.00 )   )		};
	
	
	
	
	
	private Psc[] pscs;
	
	
	
	public PscGroup( Psc psc1 , Psc psc2 ){
		
		pscs = new Psc[2];
		pscs[0] = psc1;
		pscs[1] = psc2;
		
	}
	
	
	
	public String toString(){
		
		return Product.F.format( pscs[0].getPrice() ) + " / " + Product.F.format( pscs[1].getPrice() );
		
		
	}
	
	
	
	public Psc getByYears( int years ){
		
		for( int i=0; i<pscs.length; i++ )
		{
			Psc psc = pscs[i];
			if( psc.getYears() == years )
			{
				return psc;
			}
		}
		
		return null;
		
	}
	public Psc get( int i ){
		
		return pscs[i];
		
	}

	public int getCount(){
		
		return pscs.length;
		
	}
	
	
	
	
	
	
	
}
