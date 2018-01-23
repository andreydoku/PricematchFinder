package basic;

import products.Product;



public class InternetPricematch implements Comparable {
		
	
	final public static InternetPricematch NOT_FOUND = new InternetPricematch( "not found" , 0 , null );
	final public static InternetPricematch OUT_OF_STOCK = new InternetPricematch( "out of stock" , 0 , null );
	final public static InternetPricematch FAILED = new InternetPricematch( "failed" , 0 , null );
	final public static InternetPricematch SEE_PRICE_IN_CART = new InternetPricematch( "see price in cart" , 0 , null );
	final public static InternetPricematch THIRD_PARTY = new InternetPricematch( "third party" , 0 , null );
	final public static InternetPricematch OPEN_BOX = new InternetPricematch( "open box" , 0 , null );
	
	
	
	
	
	
	private String competitor;
	private double price;
	private String url;
	
	public InternetPricematch( String competitor , double price , String url ){
		
		this.competitor = competitor;
		this.price = price;
		this.url = url;
		
	}
	
	public String getCompetitor(){
		
		return this.competitor;
		
	}
	public double getPrice(){
		
		return this.price;
		
	}
	public String getUrl(){
		
		return this.url;
		
	}
	
	public String getAbbreviatedCompetitor(){
		
		for( int i=0; i<PricematchFinder.COMPETITOR_NAMES.length; i++ )
		{
			String competitor = PricematchFinder.COMPETITOR_NAMES[i];
			String abbreviatedCompetitor = PricematchFinder.ABBREVIATED_COMPETITOR_NAMES[i];
			
			if( this.competitor.equals( competitor ) )
			{
				return abbreviatedCompetitor;
			}
		}
		
		String message = "getAbbreviatedCompetitor() - invalid competitor: " + this.competitor;
		throw new IllegalArgumentException( message );
		
		
		
	}
	public String getFormattedPrice(){
		
		return Product.F.format( price );
		
	}
	
	
	public boolean isValid(){
		
		return this != NOT_FOUND && this != OUT_OF_STOCK && this != FAILED && this != SEE_PRICE_IN_CART && this != THIRD_PARTY && this != OPEN_BOX;
		
	}
	
	
	public int compareTo( Object o ){
		
		if( o instanceof InternetPricematch )
		{
			InternetPricematch c = (InternetPricematch) o;
			
			if( this.price < c.price )
			{
				return 1;
			}
			else
			{
				return -1;
			}
			
		}
		else
		{
			return -1;
		}
		
	}
	
	
	public String toString(){
		
		return price + "\t" + competitor + "\t" + url;
		
	}
	
	
	
}


	