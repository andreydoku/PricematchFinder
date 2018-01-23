package products;


public class Psc{
	
	
	
	
	private String plu;
	private int years;
	private double price;
	private double cost;
	
	
	
	
	
	
	public Psc( String plu , int years , double price , double cost ){
		
		this.plu = plu;
		this.years = years;
		this.price = price;
		this.cost = cost;
		
	}
	
	//delete later - used for old stock display entry which uses prices for the two pscs
	public Psc( int years , double price ){
		
		this.years = years;
		this.price = price;
		
	}
	
	
	public String getPlu(){
		
		return this.plu;
		
	}
	public int getYears(){
		
		return this.years;
		
	}
	public double getPrice(){
		
		return this.price;
		
	}
	public double getCost(){
		
		return this.cost;
		
	}
	

	
	
	public double getGp(){
		
		return ( price - cost ) / ( price ); 
		
	}
	
	
	
	
	
	
}
