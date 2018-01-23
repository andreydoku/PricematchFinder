package products;

import java.io.IOException;
import java.text.DecimalFormat;

import basic.InternetPricematch;

import myLibrary.Methods;


public class Monitor extends Product {
	
	
	double screenSize = Double.NaN;
	
	
	
	public Monitor( String plu , double price ){
	
		super( plu, price );
		
	}
	
	
	//for testing purposes - delete after
	public Monitor( String description ){
		
		super( description );
		
	}
	
	public String getShortDescription(){
		
		return this.getManufacturer() + " " + this.getModelNumber();
		
		
	}
		
	
	public double getScreenSize(){
		
		return this.screenSize;
		
	}
	
	
	public void frysInfo() throws IOException{
		
		super.frysInfo();
		
		//System.out.println( "laptop.frysInfo()" );
		
		analyzeDescription();
		
	}
	public void analyzeDescription(){
		
		
		updateSreenSize();
		
		
		
	}
	public void updateSreenSize(){
		
		String description = this.getDescription();
		
		
		description = description.replace( "1920x1080" , "" );
		description = description.replace( "1920 x 1080" , "" );
		description = description.replace( "2560 x 1440" , "" );
		description = description.replace( "2560x1080" , "" );
		//
		
		
		if( description.contains( "ASUS VS208N-P" ) )
		{
			screenSize = 20;
			return;
		}
		if( description.contains( "V7 LED185W2S-9N" ) )
		{
			screenSize = 19;
			return;
		}
		
		
		
		
		String[] acceptedScreensize = { "19.5" , "21.5" , "22" , "23" , "23.6" , "25" , "26" , "24" , "27" , "29" };
		
		for( int i=0; i<acceptedScreensize.length; i++)
		{
			
			if( description.toLowerCase().contains( acceptedScreensize[i].toLowerCase() ) )
			{
				screenSize = Double.parseDouble( acceptedScreensize[i] );
				return;
				
			}
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	public String toString(){
		
		DecimalFormat f = new DecimalFormat( "$0.00" );
		
		if( this.getUpc() == null )
		{
			return "= " + Q + this.getPlu() + Q;
		}
		
		
		
		//String priceString = "";
		String priceString = f.format( this.getPrice() );
		String screenSizeString = new DecimalFormat( "#.#" ).format( getScreenSize() ) + Q;
		
		
		
		String plu = "= " + Q + this.getPlu() + Q;
		String upc = "= " + Q + this.getUpc() + Q;
		
		
		//String output = plu + T + upc + T + shortDescription + T + priceString;
		String output = plu + T + upc + T + getShortDescription() + T + screenSizeString + T + priceString;
		
		//output += T + modelNumber;
		
		
		
		if( this.getCompetitorPrices().size() > 0 )
		{
			InternetPricematch c = this.getCompetitorPrices().get( 0 );
			
			double limit = -20;
			if( c.getPrice() - this.getPrice() < limit )
			{
				//=HYPERLINK( "https://www.google.com/" , "google" )
				String competitor = "=HYPERLINK( " + Q + c.getUrl().replace( Q , "\"\"" ) + Q + " , " + Q + c.getCompetitor() + Q + " )";
				String competitorPrice = f.format( c.getPrice() );
				
				output += T + competitor + T + competitorPrice;
			}
			
			
				
		}
		
		
		return output;
		
	}
	
	
	public static void analyzeDescriptionsFromClipboard(){
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		
		
		for( int i=0; i<lines.length; i++ )
		{
			String description = lines[i];
			
			Monitor monitor = new Monitor( description );
			
			monitor.analyzeDescription();
			
			
			System.out.println( monitor.getScreenSize() );
			
			
		}
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		
		
		analyzeDescriptionsFromClipboard();
		
		
		
	}
	
	
	
	
	
}









































