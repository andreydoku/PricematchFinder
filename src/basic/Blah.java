package basic;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.UIManager;

import products.*;

import myLibrary.Methods;
import myLibrary.Stopwatch;


public class Blah{
	
	
	final static DecimalFormat F = new DecimalFormat( "$0.00" );
	final static String T = "\t";
	final static String N = "\n";
	final static String Q = "\"";
	
	
	public static void printPluList(){
		
		String clipboardString = Methods.getClipboardString();
		
		String[] pluList = clipboardString.split( "\n" );
		
		String output = "String[] pluList = { ";
		for( int i=0; i<pluList.length; i++ )
		{
			String plu = pluList[i];
			output += Q + plu + Q + " , ";
		}
		output = output.substring( 0 , output.length()-2 );//cut off the ", " at the end
		output += " };";
		
		System.out.println( output );
		
	}
	public static void printUpcList(){
		
		
		
	}
	
	
	public static void frysInfoAll( ArrayList<Product> products ) throws IOException{
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			product.frysInfo();
			
			System.out.println( product.toString() );
			
			
		}
		
	}
	
	public static void checkAllCompetitors( Product product ) {
		
		
		try
		{
			product.frysInfo();
		}
		catch ( IOException e )
		{
			System.out.println( "Failed to connect to frys.com" + N + e.getMessage() );
		}
		
		
		if( product.getUpc() != null )
		{
			
			//Microcenter
			try
			{
				InternetPricematch microCenterPrice = PricematchFinder.searchMicroCenter( product.getUpc() );
				if( microCenterPrice.isValid() )
				{
					product.addPriceMatch( microCenterPrice );
				}
				
			}
			catch( IOException e )
			{
				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
				System.out.println( "Failed to connect to microcenter.com" + N + e.getMessage() );
				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
			}
			
			
//			//Best Buy
//			try
//			{
//				InternetPricematch bestBuyPrice = Pricematch.searchBestBuy( product.getUpc() );
//				if( bestBuyPrice.isValid() )
//				{
//					product.addPriceMatch( bestBuyPrice );
//				}
//			}
//			catch( IOException e )
//			{
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//				System.out.println( "Failed to connect to bestbuy.com" + N + e.getMessage() );
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//			}
//			
//			
//			//Tigerdirect
//			try
//			{
//				InternetPricematch tigerDirectPrice = Pricematch.searchTigerDirect( product.getUpc() );
//				if( tigerDirectPrice.isValid() )
//				{
//					product.addPriceMatch( tigerDirectPrice );
//				}
//			}
//			catch( IOException e )
//			{
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//				System.out.println( "Failed to connect to tigerdirect.com" + N + e.getMessage() );
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//			}
//			
//			//Newegg
//			try
//			{
//				InternetPricematch neweggPrice = Pricematch.searchNewegg( product.getUpc() );
//				if( neweggPrice.isValid() )
//				{
//					product.addPriceMatch( neweggPrice );
//				}
//			}
//			catch( IOException e )
//			{
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//				System.out.println( "Failed to connect to newegg.com" + N + e.getMessage() );
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//			}
//			
//			
//			//B&H
//			InternetPricematch bhPrice = Pricematch.searchBH( product.getUpc() );
//			if( bhPrice.isValid() )
//			{
//				product.addPriceMatch( bhPrice );
//			}
//				
//			
//			//Staples
//			try
//			{
//				InternetPricematch staplesPrice = Pricematch.searchStaples( product.getModelNumber() );
//				if( staplesPrice.isValid() )
//				{
//					product.addPriceMatch( staplesPrice );
//				}
//			}
//			catch( IOException e )
//			{
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//				System.out.println( "Failed to connect to staples.com" + N + e.getMessage() );
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//			}
//			
//			
//			//Walmart
//			try
//			{
//				InternetPricematch walmartPrice = Pricematch.searchWalmart( product.getUpc() );
//				if( walmartPrice.isValid() )
//				{
//					product.addPriceMatch( walmartPrice );
//				}
//			}
//			catch( IOException e )
//			{
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//				System.out.println( "Failed to connect to walmart.com" + N + e.getMessage() );
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//			}
//			
//			
//			//Office Depot
//			try
//			{
//				InternetPricematch officeDepotPrice = Pricematch.searchOfficeDepot( product.getUpc() );
//				if( officeDepotPrice.isValid() )
//				{
//					product.addPriceMatch( officeDepotPrice );
//				}
//			}
//			catch( IOException e )
//			{
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//				System.out.println( "Failed to connect to officedepot.com" + N + e.getMessage() );
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//			}
//			
//			
//			
//			//Office Max
//			try
//			{
//				InternetPricematch officeMaxPrice = Pricematch.searchOfficeMax( product.getUpc() );
//				if( officeMaxPrice.isValid() )
//				{
//					product.addPriceMatch( officeMaxPrice );
//				}
//			}
//			catch( IOException e )
//			{
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//				System.out.println( "Failed to connect to officemax.com" + N + e.getMessage() );
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//			}
//			
//			
//			//Amazon
//			try
//			{
//				InternetPricematch amazonPrice = Pricematch.searchAmazon( product.getUpc() );
//				if( amazonPrice.isValid() )
//				{
//					product.addPriceMatch( amazonPrice );
//				}
//			}
//			catch( IOException e )
//			{
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//				System.out.println( "Failed to connect to amazon.com" + N + e.getMessage() );
//				System.out.println( "-------------------------------------------------------------------------------------------------------------------------" );
//			}
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
	}
	public static void checkAllCompetitors( ArrayList<Product> products ) throws IOException{
		
		
		//System.out.println( "PLU" + T + "UPC" + T + "Description" + T + "CPU" + T + "RAM" + T + "Screen" + T + "Price" + T + "Cost" + T + "GP" + T + "Competitor" + T + "C. Price" + T + "Difference" + T + "2yr" + T + "3yr" + T + "w/ 2yr" + T + "w/ 3yr" );
		
		
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start();
		
		
		int count = products.size();
		//count = 10;
		
		for( int i=0; i<count; i++ )
		{
			Product product = products.get( i );
			checkAllCompetitors( product );
			
			
			
			if( product instanceof Laptop ){
				
				Laptop laptop = (Laptop) product;
				System.out.println( laptop );
			}
			else
			{
				System.out.println( product );
			}
			
			
		}
		
		
		double timeInSeconds = stopwatch.lap();
		System.out.println( "\n\n\n" + "done in " + Stopwatch.convertSecondsToHMS( timeInSeconds ) );
		
	}
	
	
	
	public static ArrayList<Product> getProductsFromClipboard(){
		
		ArrayList<Product> products = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( pieces[0] + T + pieces[1] );
			String plu = pieces[0];
			String priceString = pieces[1].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			
			Product product = new Product( plu , price );
			products.add( product );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return products;
		
	}
	
	public static ArrayList<Product> getLaptopsFromClipboard_justPlu(){
		
		ArrayList<Product> products = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( pieces[0] + T + pieces[1] );
			String plu = pieces[0];
			
			Laptop laptop = new Laptop();
			laptop.setPlu( plu );
			products.add( laptop );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return products;
		
	}
	
	public static ArrayList<Product> getLaptopsFromClipboard(){
		
		ArrayList<Product> laptops = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( pieces[0] + T + pieces[1] );
			String plu = pieces[0];
			String priceString = pieces[1].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			
			Laptop laptop = new Laptop( plu , price );
			laptops.add( laptop );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return laptops;
		
	}
	public static ArrayList<Product> getLaptopsFromClipboard_withUPC(){
		
		ArrayList<Product> laptops = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( pieces[0] + T + pieces[1] );
			String plu = pieces[0];
			String upc = pieces[1];
			String priceString = pieces[2].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			
			Laptop laptop = new Laptop( plu , upc , price );
			laptops.add( laptop );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return laptops;
		
	}
	public static ArrayList<Product> getLaptopsFromClipboard_withPSC(){
		
		ArrayList<Product> laptops = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( pieces[0] + T + pieces[1] );
			String plu = pieces[0];
			
			String priceString = pieces[1].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			
			String costString = pieces[2].replace( "$" , "" ).replace( "," , "" );
			double cost = Double.parseDouble( costString );
			
			String psc2_string = pieces[3].replace( "$" , "" ).replace( "," , "" );
			double psc2 = Double.parseDouble( psc2_string );
			
			String psc3_string = pieces[4].replace( "$" , "" ).replace( "," , "" );
			double psc3 = Double.parseDouble( psc3_string );
			
			Laptop laptop = new Laptop( plu , price , cost , psc2 , psc3 );
			laptops.add( laptop );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return laptops;
		
	}
	
	
	
	public static ArrayList<Product> getDesktopsFromClipboard(){
		
		ArrayList<Product> desktops = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( pieces[0] + T + pieces[1] );
			String plu = pieces[0];
			String priceString = pieces[1].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			
			Desktop desktop = new Desktop( plu , price );
			desktops.add( desktop );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return desktops;
		
	}
	public static ArrayList<Product> getMonitorsFromClipboard(){
		
		ArrayList<Product> monitors = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( pieces[0] + T + pieces[1] );
			String plu = pieces[0];
			String priceString = pieces[1].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			
			Monitor monitor = new Monitor( plu , price );
			monitors.add( monitor );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return monitors;
		
	}
	
	
	
	
	
	
	public static void main_old(String[] args) throws IOException {
		
		//printPluList();
		
		
		
//		String[] pluList = { "7994640" , "8014654" , "7988560" , "7994630" , "7970200" , "8004764" , "8004784" , "7997990" , "7996010" , "7872869" , "8000684" , "7988500" , "7988410" , "8000714" , "8000694"   , 
//				             "8027074" , "7988570" , "8004754" , "7825519" , "7853219" , "7988490" , "7971270" , "7995970" , "7997220" , "7988520" , "8004774" , "8000734" , "7873019" , "8004274" };
		
		//String[] pluList = { "7970200" , "8014654" , "8000684" , "7994630" , "7988570" , "7997990" , "7996010" , "7960680" , "8004764" , "7872869" , "8000714" , "8000694" , "7995990" , "8027074" , "8004774" , "7988560" , "7988500" , "7971270" , "7988490" , "7988520" , "7997220" , "7825519" , "7995970" , "8000734" , "8004274" , "7970190" , "7917530" , "7988530" , "8004244" , "8037244" , "7856739" , "7995930" , "7993960" , "7856729" , "7859869" , "7872919" , "7970180" , "7970230" , "7940380" , "7988590"  };
		
		
		//String[] pluList1 = { "7344074" , "6828426" , "7970200" , "7888549" , "8014654" , "7701278" , "7997990" , "7922210" , "8000684" , "7701348" , "7859879" , "7861709" , "7856119" , "7994640" , "7701298" , "7988570" , "7851349" , "7994560" , "7996010" , "7960680" , "7994630" , "8004764" , "7872869" , "7860759" , "7988410" , "8000714" , "7912900" , "7853249" , "8000694" , "7859849" , "8000704"  };
		//String[] pluList2 = { "7995990" , "8027074" , "8004754" , "7672387" , "7988560" , "7997660" , "8004774" , "8004784" , "7544646" , "7701258" , "7378294" , "7997220" , "7988500" , "7853219" , "7971270" , "7988490" , "7988520" , "7825519" , "7685917" , "7995970" , "7888439" , "8000734" , "7873019" , "8027044" , "7944610" , "8018664" , "8004274" , "7970190" , "7350214" , "7888479" , "7361674"  };

		
		//checkAllCompetitors( pluList1 );
		//checkAllCompetitors( pluList2 );
		
		
		
		
		
		
		//blah( "7701298" );
		
		
		
		
		
		
//		Product product = new Product( "7960680" );
//		product.frysInfo();
//		Methods.analyzeString( product.getDescription() );
		
		
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
//		ArrayList<Product> products = getProductsFromClipboard();
//		
//		frysInfoAll( products );
//		//checkAllCompetitors( products );
		
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
		
		
		ArrayList<Product> laptops = getLaptopsFromClipboard_withPSC();
		//ArrayList<Product> laptops = getLaptopsFromClipboard_withUPC();
		
		//frysInfoAll( laptops );
		//System.out.println( "----------------------------------------------------------------------------------------------------------------" );
		
		checkAllCompetitors( laptops );	//prints too
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
//		ArrayList<Product> desktops = getDesktopsFromClipboard();
//		//ArrayList<Product> laptops = getLaptopsFromClipboard_withUPC();
//		
//		//frysInfoAll( desktops );
//		//System.out.println( "----------------------------------------------------------------------------------------------------------------" );
//		
//		checkAllCompetitors( desktops );	//prints too
		
		
		
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		
		
//		ArrayList<Product> monitors = getMonitorsFromClipboard();
//		
//		//frysInfoAll( monitors );
//		//System.out.println( "----------------------------------------------------------------------------------------------------------------" );
//		
//		checkAllCompetitors( monitors );//prints too
		
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
		//Product p = new Product( "7859929" , 848 );
		//checkAllCompetitors( p );
		
		
		
		
		
		
		
		
		

		
		
		
		
		
	}
	
	

	
	
	
}


































