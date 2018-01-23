package basic;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import myLibrary.*;
import products.Product;


public class PricematchFinder{
	
	
	final public static String[] COMPETITOR_NAMES = 				{ "Micro Center" , "Best Buy" , "Tiger Direct" , "B&H" , "Staples" , "Walmart" , "Office Depot" , "Office Max" , "Newegg" , "Amazon" };
	final public static String[] ABBREVIATED_COMPETITOR_NAMES = 	{ "MC"           , "BB"       , "TD"           , "BH"  , "ST"      , "WM"      , "OD"           , "OM"         , "NE"     , "AZ"     };
	
	
	final static DecimalFormat F = new DecimalFormat( "$0.00" );
	final static String N = "\n";
	final static String T = "\t";
	
	public static InternetPricematch getPriceFromUser( String competitor , String url , String message ){
		
		
//		try{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		
		
		
		
//		String input = JOptionPane.showInputDialog( "Unable to see sale price" );
//		input = input.trim().replace( "$" , "" ).replace( "," , "" );
//		double price = Double.parseDouble( input );
//		return price;
		
		
//		while( true )
//		{
//			String input = JOptionPane.showInputDialog( message );
//			
//			if( input == null )
//			{
//				return null;
//			}
//			
//			
//			try
//			{
//				double price = Double.parseDouble( input );
//				return new CompetitorPrice( competitor , price , url );
//			}
//			catch( NumberFormatException nfe )
//			{
//				JOptionPane.showMessageDialog( null , "Invalid intput.  Try again." );
//			}
//		}
		
		
		return InternetPricematch.SEE_PRICE_IN_CART;
		
		
		
	}
	
	
	
	
	public static InternetPricematch searchCompetitor( int index , String upc ) throws IOException{
		
		if     ( index == 0 )	return searchMicroCenter( upc );
		else if( index == 1 )	return searchBestBuy( upc );
		else if( index == 2 )	return searchTigerDirect( upc );
		else if( index == 3 )	return searchBH( upc );
		else if( index == 4 )	return searchStaples( upc );
		else if( index == 5 )	return searchWalmart( upc );
		else if( index == 6 )	return searchOfficeDepot( upc );
		else if( index == 7 )	return searchOfficeMax( upc );
		else if( index == 8 )	return searchNewegg( upc );
		else if( index == 9 )	return searchAmazon( upc );
		else 					throw new IllegalArgumentException( "searchCompetitor() invalid index: " + index );
		
		
	}
	
	
	
	public static InternetPricematch searchMicroCenter( String upc ) throws IOException{
		
		
		boolean debug = false;
		boolean printHtml = false;
		
		
		String url = "http://www.microcenter.com/search/search_results.aspx?Ntt=" + upc + "&N=";
		//try{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		//String notFoundTag = "Your search didn't return any results.";
		String notFoundTag = "'category':'',";
		String[] cutoffs = { notFoundTag , "'isMobile':'False'" };
		
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		
		if( debug ) System.out.println( "upc: " + upc + "\n" + "url: " + url );
		
		if( debug && printHtml ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		if( debug && printHtml ) ArrayToString.printArray( htmlArray , "\n" );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "price: " + InternetPricematch.NOT_FOUND.getCompetitor() );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		
		
		
		
		String priceString = Methods.getSubstring( htmlArray , "price:\"" , "\"" );
		if( priceString == null )
		{
			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			System.out.println( "searchMicroCenter: failed to find priceString" );
			System.out.println( "upc: " + upc );
			System.out.println( "url: " + url );
			System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
			ArrayToString.printArray( htmlArray , "\n" );
			return InternetPricematch.FAILED;
		}
		
		priceString = priceString.replace( "," , "" );
		//System.out.println( priceString );
		
		double price = Double.parseDouble( priceString );
		if( debug ) System.out.println( "price: " + F.format( price ) );
		if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
		
		return new InternetPricematch( "Micro Center" , price , url );
		
		
		
		
		
	}	
	public static InternetPricematch searchBestBuy( String upc ) throws IOException{
		
		
		boolean debug = false;
		boolean printHtml = false;
		
		
		//String url = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=-4770458603159431761&id=pcat17071&type=page&ks=960&st=" + upc + "&sc=Global&cp=1&sp=&qp=soldby_facet%3DSold+By~Best+Buy%5Econdition_facet%3DSAAS~Condition~New&list=y&usc=All+Categories&nrp=15&fs=saas&iht=n&seeAll=";	
		
		//http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&id=pcat17071&type=page&st=886227605965&sc=Global&cp=1&sp=&qp=soldby_facet%3DSold+By~Best+Buy%5Econdition_facet%3DSAAS~Condition~New&usc=All+Categories&nrp=15
		String url = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&id=pcat17071&type=page&st=" + upc + "&sc=Global&cp=1&sp=&qp=soldby_facet%3DSold+By~Best+Buy%5Econdition_facet%3DSAAS~Condition~New&usc=All+Categories&nrp=15";
		//try{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		String notFoundTag = "Oops.";
		String[] cutoffs = { notFoundTag , "<footer>" };
		
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		
		if( debug ) System.out.println( "upc: " + upc + "\n" + "url: " + url );
		
		if( debug && printHtml ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		if( debug && printHtml ) ArrayToString.printArray( htmlArray , "\n" );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "price: " + "N/A" + " (not found)" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
				
		String addToCartBtn = "btn_add_to_cart.gif";
		String soldOutBtn = "btn_sold_out_online.gif";
		String comingSoonBtn = "btn_coming_soon.gif";
		
		if( Methods.contains( htmlArray , addToCartBtn ) )
		{
			//in stock
		}
		else if( Methods.contains( htmlArray , soldOutBtn ) )
		{
			if( debug ) System.out.println( "price: " + "N/A" + " (sold out)" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			
			return InternetPricematch.OUT_OF_STOCK;
		}
		else if( Methods.contains( htmlArray , comingSoonBtn ) )
		{
			if( debug ) System.out.println( "price: " + "N/A" + " (coming soon)" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			
			return InternetPricematch.OUT_OF_STOCK;
		}
		else
		{
			String message = "searchBestBuy( upc ): " + "didn't find addToCartBtn nor soldOutBtn";
			message += "\n" + "upc: " + upc;
			message += "\n" + "url: " + url;
			if( debug ) System.out.println( "price: " + "failed" + "\n" + message );
			return InternetPricematch.FAILED;
		}
		
		
		if( Methods.contains( htmlArray , "See price in cart" ) || Methods.contains( htmlArray , "See details in checkout" ) )
		{
			//if( debug ) System.out.println( "price: " + "N/A" + " (See price in cart)" );
			//if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			//return null;
			
			
			
//			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			
//			String input = JOptionPane.showInputDialog( "Unable to see sale price" );
//			input = input.trim().replace( "$" , "" ).replace( "," , "" );
//			double price = Double.parseDouble( input );
			
			
//			double price = getPriceFromUser( "See price in cart" );
//			return new CompetitorPrice( "Best Buy" , price , url );
			
			return getPriceFromUser( "Best Buy" , url , "See price in cart" );
			
			
			
		}
		
		
		
		
		
		
		String priceString = Methods.getSubstring( htmlArray , "<span itemprop=\"price\">$" , "</span>" );//<span itemprop="price">$
		if( priceString == null )
		{
			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			System.out.println( "searchBestBuy: failed to find priceString" );
			System.out.println( "upc: " + upc );
			System.out.println( "url: " + url );
			System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
			ArrayToString.printArray( htmlArray , "\n" );
			return InternetPricematch.FAILED;
		}
		
		
		priceString = priceString.replace( "," , "" );
		//System.out.println( priceString );
		
		double price = Double.parseDouble( priceString );
		if( debug ) System.out.println( "price: " + F.format( price ) );
		if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
		
		return new InternetPricematch( "Best Buy" , price , url );
		
		
		
		
		
	}
	public static InternetPricematch searchTigerDirect( String upc ) throws IOException {
		
		
		boolean debug = false;
		boolean printHtml = false;
		
		
		String url = "http://www.tigerdirect.com/applications/SearchTools/search.asp?keywords=" + upc;	
		//try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		String notFoundTag = "0 Results found";
		String[] cutoffs = { notFoundTag , "Add to Cart" };
		
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		
		if( debug ) System.out.println( upc + "\n" + url + "\n" + "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		if( debug && printHtml ) ArrayToString.printArray( htmlArray , "\n" );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "price: " + "N/A" );
			if( debug ) System.out.println( "------------------end--------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		
		
		if( Methods.contains( htmlArray , "mapprice" ) )
		{
//			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			
//			String input = JOptionPane.showInputDialog( "Unable to see sale price" );
//			input = input.trim().replace( "$" , "" ).replace( "," , "" );
//			double price = Double.parseDouble( input );
			
			return getPriceFromUser( "Tiger Direct" , url , "See price in cart" );
			
			
		}
		
		
		//
		
		
		
//		String priceString = Methods.getSubstring( htmlArray , "<div class=\"salePrice\">" , "\"priceFlag\">" );
//		priceString = priceString.replace( "," , "" );
//		//System.out.println( priceString );
		
		
		
		
		String priceString = Methods.getSubstring( htmlArray , "<div class=\"salePrice\">" , "\"priceFlag\">" );
		if( priceString == null )
		{
			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			System.out.println( "searchTigerDirect: failed to find priceString" );
			System.out.println( "upc: " + upc );
			System.out.println( "url: " + url );
			System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
			ArrayToString.printArray( htmlArray , "\n" );
			return InternetPricematch.FAILED;
		}
		
		String dollarString = Methods.getSubstring( priceString , "$</sup>" , "<sup>" );
		String centsString = Methods.getSubstring( priceString , ">.</span>" , "<span" );
		
		priceString = dollarString + "." + centsString;
		priceString = priceString.replace( "," , "" );
		//System.out.println( priceString );
		
		double price = Double.parseDouble( priceString );
		if( debug ) System.out.println( F.format( price ) );
		if( debug ) System.out.println( "------------------end--------------------" );
		
		return new InternetPricematch( "Tiger Direct" , price , url );
		
		
		
		
		
	}
	public static InternetPricematch searchBH( String upc ) {
		
		boolean debug = false;
		boolean printHtml = false;
		
		
		String url = "http://www.bhphotovideo.com/c/search?Ntt=" + upc;	
		//try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		
		String[] cutoffs = { "cormetricsRunScripts" };
		//String[] cutoffs = null;
		
		
		ArrayList<String> htmlArray;
		try
		{
			htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
			//htmlArray = UrlReader.getHtmlArray( url );
			
			
			if( debug ) System.out.println( "upc: " + upc + "\n" + "url: " + url );
			
			if( debug && printHtml ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
			if( debug && printHtml ) ArrayToString.printArray( htmlArray , "\n" );
			
			
		}
		catch( IOException e )//if no results found, B&H simply rejects connection to the page
		{
			if( debug ) System.out.println( "upc: " + upc + "\n" + "url: " + url );
			if( debug ) System.out.println( "price: " + InternetPricematch.NOT_FOUND.getCompetitor() + "    " + "IOException" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		
		if( Methods.contains( htmlArray , "Temporarily out of stock" ) )
		{
			if( debug ) System.out.println( "price: " + InternetPricematch.OUT_OF_STOCK.getCompetitor() );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.OUT_OF_STOCK;
		}
		
		
		
		
		String stockString = Methods.getSubstring( htmlArray , "\"stockMessage\":\"" , "\"" );
		if( stockString == null )//reduced price code is different :/
		{
			stockString = Methods.getSubstring( htmlArray , "bold	c5 availabilty\">" , "</p>" );
			if( stockString == null )
			{
				stockString = Methods.getSubstring( htmlArray , "c7 availabilty twelve\">" , "</p>" );//DISCONTINUED
				
				if( stockString == null )
				{
					//i dunno what nooowww......
					try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
					System.out.println( "searchBH: failed to find stockString" );
					System.out.println( "upc: " + upc );
					System.out.println( "url: " + url );
					System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
					ArrayToString.printArray( htmlArray , "\n" );
					return InternetPricematch.FAILED;
				}
				
				
			}
			
			
			
			
			
		}
		if( debug ) System.out.println( "stockString: " + stockString );
		
		if( !stockString.equals( "In Stock" ) )
		{
			if( debug ) System.out.println( "price: " + InternetPricematch.OUT_OF_STOCK.getCompetitor() );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.OUT_OF_STOCK;
		}
		
		
		
		
		
		
		String priceString = Methods.getSubstring( htmlArray , "\"price\":\"" , "\"" );
		if( priceString == null )//reduced price code is different :/
		{
			htmlArray = Methods.replace( htmlArray , "price  bold sixteen c7" , "price bold sixteen c7" );
			ArrayList<String> substringArray = Methods.getSubstringArray( htmlArray , "price bold sixteen c7\">" , "</span>" );
			if( substringArray == null )
			{
				substringArray = Methods.getSubstringArray( htmlArray , "<span data-selenium=\"price\" class=\"price  \">" , "</span>" );
				if( substringArray == null )
				{
					try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
					System.out.println( "searchBH: failed to find priceString" );
					System.out.println( "upc: " + upc );
					System.out.println( "url: " + url );
					System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
					ArrayToString.printArray( htmlArray , "\n" );
					return InternetPricematch.FAILED;
				}
				
					
			}
			//ArrayToString.printArray( substringArray , "\n" , true );
			
			priceString = substringArray.get( 0 );
			priceString = priceString.replace( "$" , "" );
		}
		priceString = priceString.replace( "," , "" );
		//if( debug ) System.out.println( "priceString: " + priceString );
		
		
		double price = Double.parseDouble( priceString );
		if( debug ) System.out.println( "price: " + F.format( price ) );
		if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
		
		return new InternetPricematch( "B&H" , price , url );
		
		
		
	}
	public static InternetPricematch searchStaples( String modelNumber ) throws IOException {
		
		
		boolean debug = false;
		boolean printHtml = false;
		
		
		if( modelNumber.equals( "G505" ) )
		{
			return InternetPricematch.NOT_FOUND;
		}
		
		
		
		
		
		//String url = "http://www.staples.com/directory_" + modelNumber + "?";
		String url = "http://www.staples.com/directory_" + "\"" + modelNumber.replace( " " , "+" ) + "\"" + "?";
		//try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		String notFoundTag = "did not match any products";
		String[] cutoffs = { notFoundTag , "Add to Cart" };
		
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		
		if( debug ) System.out.println( "model #: " + modelNumber + N + "url: " + url + N );
		
		if( debug && printHtml ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		if( debug && printHtml ) ArrayToString.printArray( htmlArray , "\n" );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "price: " + "(not found)" );
			if( debug ) System.out.println( "------------------end--------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		
		String priceString = Methods.getSubstring( htmlArray , "<dd class=\"pis\"><b><i>$" , "</i></b></dd>" );
		if( priceString == null )
		{
			//<tr class="total"> <th>Price <strong>after</strong> savings:</th> <td> <b>
			//<tr class="total"> <th>Price <strong>after</strong> rebate:</th> <td> <b>
			
			ArrayList<String> substrings = Methods.getSubstringArray( htmlArray , "<tr class=\"total\"> <th>Price " , "</b> <span class=\"pqty\">" );
			//ArrayToString.printArray( substrings , "\n" , true );
			
			if( substrings == null )
			{
				try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
				System.out.println( "searchStaples: failed to find priceString" );
				System.out.println( "model #: " + modelNumber );
				System.out.println( "url: " + url );
				System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
				ArrayToString.printArray( htmlArray , "\n" );
				return InternetPricematch.FAILED;
			}
			
			
			priceString = substrings.get( 1 );
			priceString = priceString.replace( "$" , "" );
			
		}
		
		priceString = priceString.replace( "," , "" );
		//System.out.println( priceString );
		
		double price = Double.parseDouble( priceString );
		if( debug ) System.out.println( "price: " + F.format( price ) );
		
		return new InternetPricematch( "Staples" , price , url );
		
		
		
		
		
	}
	public static InternetPricematch searchWalmart( String upc ) throws IOException{
		
		
		boolean debug = false;
		boolean printHtml = false;
		
		
		String url = "http://www.walmart.com/search/search-ng.do?search_query=" + upc + "&ic=16_0&Find=Find&search_constraint=3944";
		//try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		String notFoundTag = "0 results";
		String[] cutoffs = { notFoundTag , "<!-- end online availability -->" };
		
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		
		if( debug && printHtml ) System.out.println( upc + "\n" + url + "\n" + "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		if( debug && printHtml ) ArrayToString.printArray( htmlArray , "\n" );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "price: " + "N/A" );
			if( debug ) System.out.println( "------------------end--------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		
		String outOfStockTag = "Out of stock";
		if( Methods.contains( htmlArray , outOfStockTag ) )
		{
			if( debug ) System.out.println( "out of stock" );
			if( debug ) System.out.println( "------------------end--------------------" );
			return InternetPricematch.OUT_OF_STOCK;
		}
		
		
		
		
//		//String priceString = Methods.getSubstring( htmlArray , "<div class=\"camelPrice\">" , "</div>" );
//		String priceString = Methods.getSubstring( htmlArray , "<div class=\"camelPrice\">" , "<span></span></div>" );
//		if( debug ) System.out.println( "priceString: " + priceString );
//		
//		String dollarString = Methods.getSubstring( priceString , "<span class=\"bigPriceText2\">$" , "." );
//		String centsString = Methods.getSubstring( priceString , "<span class=\"smallPriceText2\">" , "<" );
//		
//		priceString = dollarString + "." + centsString;
//		priceString = priceString.replace( "," , "" );
//		//System.out.println( priceString );
//		
//		double price = Double.parseDouble( priceString );
//		
//		if( debug ) System.out.println( F.format( price ) );
//		if( debug ) System.out.println( "------------------end--------------------" );
		
		
		String priceString = Methods.getSubstring( htmlArray , "<span class=\"price price-display\"> <span class=sup>" , "<span class=price-auxblock>" );
		if( debug ) System.out.println( "priceString: " + priceString );
		
		String dollarString = Methods.getSubstring( priceString , "$</span>" , "<span class=visuallyhidden>" );
		String centsString = Methods.getSubstring( priceString , "<span class=sup>" , "</span>" );
		
		priceString = dollarString + "." + centsString;
		priceString = priceString.replace( "," , "" );
		//System.out.println( priceString );
		
		double price = Double.parseDouble( priceString );
		
		if( debug ) System.out.println( F.format( price ) );
		if( debug ) System.out.println( "------------------end--------------------" );		
		
		
		
		
		
		return new InternetPricematch( "Walmart" , price , url );
		
		
		
		
	}
	public static InternetPricematch searchOfficeDepot( String upc ) throws IOException{
		
		
		boolean debug = false;
		boolean printHtml = false;
		
		
		
		//http://www.officedepot.com/catalog/search.do?Ntt=022265636213
		String url = "http://www.officedepot.com/catalog/search.do?Ntt=" + upc;
		//try{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		//String notFoundTag = "did not produce any results";
		String notFoundTag = "Sorry, no matches for";
		
		String outOfStockTag = "Out of stock for delivery";
		String seePriceInCartTag = "See Price In Cart";
		
		String[] cutoffs = { notFoundTag , outOfStockTag , seePriceInCartTag , "About This Product" };
		
//		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		
		if( debug ) System.out.println( "upc: " + upc + "\n" + "url: " + url );
		
		if( debug && printHtml ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		if( debug && printHtml ) ArrayToString.printArray( htmlArray , "\n" );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "price: " + "N/A" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		
		if( Methods.contains( htmlArray , outOfStockTag ) )
		{
			if( debug ) System.out.println( "price: " + "out of stock" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.OUT_OF_STOCK;
		}
		
		
		if( Methods.contains( htmlArray , seePriceInCartTag ) )
		{
			
			
//			String input = JOptionPane.showInputDialog( "Unable to see sale price" );
//			input = input.trim().replace( "$" , "" ).replace( "," , "" );
//			double price = Double.parseDouble( input );
			
			return getPriceFromUser( "Office Depot" , url , "See price in cart" );
			

			
		}
		
		
		
		
		ArrayList<String> priceArray = Methods.getSubstringArray( htmlArray , "<span class=\"price_amount\">" , "</span>" );
		if( priceArray == null )
		{
			//sale price
			priceArray = Methods.getSubstringArray( htmlArray , "<td class=\"price dot_top\"" , "</span>" );
		}
		//ArrayToString.printArray( priceArray , "\n" , true );
		
		if( priceArray == null )
		{
			//another type of sale price
			priceArray = Methods.getSubstringArray( htmlArray , "<td class=\"price\"" , "</span>" );
		}
		
		//
		
		//if STILL null, then we have a problem
		if( priceArray == null )
		{
			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			System.out.println( "searchOfficeDepot: failed to find priceString" );
			System.out.println( "upc: " + upc );
			System.out.println( "url: " + url );
			System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
			ArrayToString.printArray( htmlArray , "\n" );
			return InternetPricematch.FAILED;
		}
		
		
			
		
		
		String priceString = priceArray.get( 1 );
		priceString = priceString.trim();
		priceString = priceString.replace( "$" , "" );
		priceString = priceString.replace( "," , "" );
		//System.out.println( priceString );
		
		double price = Double.parseDouble( priceString );
		if( debug ) System.out.println( "price: " + F.format( price ) );
		if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
		
		return new InternetPricematch( "Office Depot" , price , url );
		
		
		
		
		
	}
	public static InternetPricematch searchOfficeMax( String upc ) throws IOException{
		
		
		boolean debug = false;
		boolean printHtml = false;
		
		
		
		String url = "http://www.officemax.com/catalog/search.jsp?freeText=" + upc;
		//try{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		String notFoundTag = "did not return any results";
		String[] cutoffs = { notFoundTag , "<div class=\"details\"" };
		
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );System.out.println( "done reading html" );
		
		if( debug ) System.out.println( "upc: " + upc + "\n" + "url: " + url );
		
		//if( debug ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		//if( debug ) ArrayToString.printArray( htmlArray , "\n" , false );
		
		
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "price: " + "N/A" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		
		
		ArrayList<String> priceArray = Methods.getSubstringArray( htmlArray , "<span class=\"skuPrice\">Your Price: </span>" , "</span>" );
		if( priceArray == null )
		{
			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			System.out.println( "searchOfficeMax: failed to find priceString" );
			System.out.println( "upc: " + upc );
			System.out.println( "url: " + url );
			System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
			ArrayToString.printArray( htmlArray , "\n" );
			return InternetPricematch.FAILED;
		}
		if( debug && printHtml ) ArrayToString.printArray( priceArray , "\n" );
		
		
		String priceString = priceArray.get( 1 ).trim().replace( "$" , "" ).replace( "," , "" );
		if( priceString == null )
		{
			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			System.out.println( "searchOfficeMax: failed to find priceString" );
			System.out.println( "upc: " + upc );
			System.out.println( "url: " + url );
			System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
			ArrayToString.printArray( htmlArray , "\n" );
			return InternetPricematch.FAILED;
		}
		
		//System.out.println( priceString );
		
		double price = Double.parseDouble( priceString );
		if( debug ) System.out.println( "price: " + F.format( price ) );
		if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
		
		return new InternetPricematch( "Office Max" , price , url );
		
		
		
		
		
	}	
	
	
	//Newegg
	public static InternetPricematch searchNewegg( String upc ) throws IOException {
		
		boolean debug = false;
		boolean printHtml = false;
		
		//http://www.newegg.com/Product/ProductList.aspx?Submit=ENE&Description=027242962880&N=8000
		String url = "http://www.newegg.com/Product/ProductList.aspx?Submit=ENE&Description=" + upc + "&N=8000";//
		
		
		if( debug ) try	{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		String notFoundTag = "Reduce the number of keywords used";
		String notFoundTag2 = "Below are the results for \"" + upc + "\" in \"All Stores\"";
		
		String[] cutoffs = { notFoundTag , notFoundTag2 , "<div id=\"noResData\" style=\"display: none;\">" };
		
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		
		if( debug ) System.out.println( "upc: " + upc + "\n" + "url: " + url );
		
		if( debug && printHtml ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		if( debug && printHtml ) ArrayToString.printArray( htmlArray , "\n" );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "notFoundTag - returning InternetPricematch.NOT_FOUND" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		if( Methods.contains( htmlArray , notFoundTag2 ) )
		{
			if( debug ) System.out.println( "notFoundTag2 - returning InternetPricematch.NOT_FOUND" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		
		//<div class="itemCell" 
		//if (Biz && Biz.ProductList && Biz.ProductList.Compare)
		
		//ArrayList<ArrayList<String>> cells = Methods.getAllSubstringArrays( htmlArray , "<div class=\"itemCell\" " , "<div class=\"call-to-action call-to-action-product-list\">" );
		ArrayList<ArrayList<String>> cells = Methods.getAllSubstringArrays( htmlArray , "<div class=\"itemCell\" " , "if (Biz && Biz.ProductList && Biz.ProductList.Compare)" );
		
		//System.out.println( cells.size() );
		//ArrayToString.printArrayOfArrays( cells );
		
		
		InternetPricematch bestIp = null;
		for( int i=0; i<cells.size(); i++ )
		{
			ArrayList<String> cell = cells.get( i );
			InternetPricematch cellIp = getPrice_NeweggCell( cell , upc );
			
			if( debug ) System.out.println( "\t" + "cell " + i + ": " + T + cellIp );
			
			
			if( cellIp == InternetPricematch.FAILED )
			{
				return InternetPricematch.FAILED;
			}
			
			
			
			if( bestIp == null )
			{
				bestIp = cellIp;
			}
			else if( cellIp.isValid() )
			{
				if( !bestIp.isValid() )
				{
					bestIp = cellIp;
				}
				else if( cellIp.getPrice() < bestIp.getPrice() )
				{
					bestIp = cellIp;
				}
			}
				
			
			
		}
		
		
		
		if( bestIp == null )
		{
			if( debug ) System.out.println( "bestIp == null, so returning InternetPricematch.NOT_FOUND" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		if( debug ) System.out.println( "best IP: " + bestIp );
		if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
		
		return bestIp;
		
		
		//
		//
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	private static InternetPricematch getPrice_NeweggCell( ArrayList<String> cell , String upc ){
		
		
		//ArrayToString.printArray( cell );
		
		ArrayList<String> urlArray = Methods.getSubstringArray( cell , "<div class=\"wrapper\">" , "title=\"View Details\" >" );
		//ArrayToString.printArray( urlArray , N , true );
		
		
		
		String urlLine = urlArray.get( urlArray.size()-1 );
		//System.out.println( urlLine ); //<a href="http://www.newegg.com/Product/Product.aspx?Item=N82E16834231631&ignorebbr=1&cm_re=886227687688-_-34-231-631-_-Product" 
		
		if( !urlLine.contains( "<a href=\"" ) )
		{
			String message = "urlLine does not contain " + "<a href=\"";
			message += N + "urlLine: " + urlLine;
			message += N + "urlArray.size(): " + urlArray.size();
			message += N + ArrayToString.getString( urlArray , N );
			message += N + "upc: " + upc;
			System.out.println( message );
			return InternetPricematch.FAILED;
		}
		
		
		
		String url = urlLine.replace( "<a href=\"" , "" ).replace( "\"" , "" );
		//System.out.println( "url: " + url );
		
		
		
		
		if(   Methods.contains( cell , "See price in cart" )   ||   Methods.contains( cell , "Request Price" )   )
		{
			return getPriceFromUser( "Newegg" , url , "See price in cart" );
		}
		
		if( Methods.contains( cell , "Sold Out" ) || Methods.contains( cell , "OUT OF STOCK" ) )
		{
			return InternetPricematch.OUT_OF_STOCK;
		}
		
		if( Methods.contains( cell , "Open Box" ) )
		{
			return InternetPricematch.OPEN_BOX;
		}
		
		
		
		
		//
		
		
		String priceString = Methods.getSubstring( cell , "$<strong" , "/sup>" );//>469</strong><sup>.99<
		if( priceString == null )
		{
			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			
			String message = "searchTigerDirect: failed to find priceString";
			message += N + "upc: " + upc;
			message += N + "url: " + url;
			message += N + ArrayToString.getString( cell , "\n" );
			System.out.println( message );
			
			return InternetPricematch.FAILED;
		}
		
		//System.out.println( priceString );
		
		String dollarString = Methods.getSubstring( priceString , ">" , "<" );
		String centsString = Methods.getSubstring( priceString , "." , "<" );
		
		priceString = dollarString + "." + centsString;
		priceString = priceString.replace( "," , "" );
		//System.out.println( priceString );
		
		double price = Double.parseDouble( priceString );
		//System.out.println( "price: " + F.format( price ) );
		
		
		
		//----------------------------------------------------------------------------------------------------------------------------
		
		
		
		String shippingString = Methods.getSubstring( cell , "<li class=\"price-ship\">" , " Shipping</li>" );
		//System.out.println( "shipping: " + shippingString );
		
		double shipping;
		if( shippingString.equals( "Free" ) || shippingString.equals( "Special" ) )
		{
			shipping = 0;
		}
		else
		{
			shippingString = shippingString.replace( "$" , "" );
			shipping = Double.parseDouble( shippingString );
		}
		//System.out.println( "shipping: " + F.format( shipping ) );
		
		//----------------------------------------------------------------------------------------------------------------------------
		
		
		
		
		double totalPrice = price + shipping;
		//System.out.println( "total: " +  F.format( totalPrice ) );
		
		return new InternetPricematch( "Newegg" , totalPrice , url );
		
		
		
	}
	

	//Amazon
	public static InternetPricematch searchAmazon( String upc ) throws IOException {
		
		boolean debug = false;
		
		
		String amazonNumber = getAmazonNumber( upc );
		if( amazonNumber == null )
		{
			if( debug ) System.out.println( "upc: " + upc );
			if( debug ) System.out.println( "upc search url: " + "http://www.amazon.com/s/ref=sr_nr_p_n_condition-type_0?rh=i%3Aaps%2Ck%3A" + upc + "%2Cp_n_condition-type%3A6461716011&keywords=" + upc + "&ie=UTF8&qid=1395416403&rnid=6461714011" );
			if( debug ) System.out.println( "product not found" );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return InternetPricematch.NOT_FOUND;
		}
		
		InternetPricematch competitorPrice = searchAmazonList( amazonNumber );
		if( competitorPrice == null )
		{
//			if( debug ) System.out.println( "upc: " + upc );
//			if( debug ) System.out.println( "upc search url: " + "http://www.amazon.com/s/ref=sr_nr_p_n_condition-type_0?rh=i%3Aaps%2Ck%3A" + upc + "%2Cp_n_condition-type%3A6461716011&keywords=" + upc + "&ie=UTF8&qid=1395416403&rnid=6461714011" );
//			if( debug ) System.out.println( "amazonNumber: " + amazonNumber );
//			if( debug ) System.out.println( "listings url: " + "http://www.amazon.com/gp/offer-listing/" + amazonNumber + "/sr=/qid=/ref=olp_tab_new?ie=UTF8&condition=new" );
//			if( debug ) System.out.println( "no authorized vendor found" );
//			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
//			return InternetPricematch.NOT_FOUND;
			throw new IllegalArgumentException( "searchAmazon() - returned null, not supposed to do null no more." );
		}
		
		if( debug ) System.out.println( "upc: " + upc );
		if( debug ) System.out.println( "upc search url: " + "http://www.amazon.com/s/ref=sr_nr_p_n_condition-type_0?rh=i%3Aaps%2Ck%3A" + upc + "%2Cp_n_condition-type%3A6461716011&keywords=" + upc + "&ie=UTF8&qid=1395416403&rnid=6461714011" );
		if( debug ) System.out.println( "amazonNumber: " + amazonNumber );
		if( debug ) System.out.println( "listings url: " + "http://www.amazon.com/gp/offer-listing/" + amazonNumber + "/sr=/qid=/ref=olp_tab_new?ie=UTF8&condition=new" );
		if( debug ) System.out.println( "price: " + F.format( competitorPrice.getPrice() ) );
		if( debug ) System.out.println( "vendor: " + competitorPrice.getCompetitor() );
		if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
		
		
		return competitorPrice;
		
	}
	public static String getAmazonNumber( String upc ) throws IOException{
		
		boolean debug = false;
		
		
		String url = "http://www.amazon.com/s/ref=sr_nr_p_n_condition-type_0?rh=i%3Aaps%2Ck%3A" + upc + "%2Cp_n_condition-type%3A6461716011&keywords=" + upc + "&ie=UTF8&qid=1395416403&rnid=6461714011";
		//String url = "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=" + upc + "&rh=i%3Aaps%2Ck%3A" + upc ;
		
		
		//try	{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		
		//did not match any products
		String notFoundTag = "did not match any products";
		String[] cutoffs = { notFoundTag , "<!-- START SPONSORED LINKS SCRIPT -->" };
		
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url , cutoffs , true );
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		
		
		if( debug ) System.out.println( "upc: " + upc + "\n" + "url: " + url );
		
		//if( debug ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		//if( debug ) ArrayToString.printArray( htmlArray , "\n" , false );
		
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "product not found" );
			if( debug ) System.out.println( "------------------end--------------------" );
			return null;
		}
		
		
		String amazonNumber = Methods.getSubstring( htmlArray , "<div id=\"result_0\" class=\"fstRow prod celwidget\" name=\"" , "\">" );
				
				
				
		if( debug ) System.out.println( "amazonNumber: " + amazonNumber );
		if( debug ) System.out.println( "------------------end--------------------" );
		
		
		return amazonNumber;
		
		
	}
	private static InternetPricematch searchAmazonList( String amazonNumber ) throws IOException{
		
		boolean debug = false;
		
		
		
		//http://www.amazon.com/gp/offer-listing/B00EY50IV8/sr=/qid=/ref=olp_tab_new?ie=UTF8&condition=new
		String url = "http://www.amazon.com/gp/offer-listing/" + amazonNumber + "/sr=/qid=/ref=olp_tab_new?ie=UTF8&condition=new";
		//try	{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( url );
		
		if( debug ) System.out.println( "amazonNumber: " + amazonNumber + "\n" + "url: " + url );
		
		//if( debug ) System.out.println( "------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		//if( debug ) ArrayToString.printArray( htmlArray , "\n" , false );
		
		
		
		String[] delimiters = { "<span class=\"a-size-large a-color-price olp" , "Add to cart " };
		ArrayList<ArrayList<String>> pieces = Methods.split( htmlArray , delimiters );
		pieces.remove( 0 );
		
		//ArrayToString.printArray( pieces );
		
		//analyzeAmazonPiece( pieces.get( 0 ) );
		//System.out.println( "---------------------------------------------------------------------------------------------------------------------------------------------------------------------" );
		//analyzeAmazonPiece( pieces.get( 1 ) , url );
//		System.out.println( "---------------------------------------------------------------------------------------------------------------------------------------------------------------------" );
//		analyzeAmazonPiece( pieces.get( 6 ) );
		
		
		ArrayList<InternetPricematch> ips = new ArrayList<InternetPricematch>();
		
		for( int i=0; i < pieces.size(); i++ )
		{
			ArrayList<String> piece = pieces.get( i );
			InternetPricematch ip = analyzeAmazonPiece( piece , url );
			
			if( ip == InternetPricematch.FAILED || ip.isValid() )
			{
				ips.add( ip );
			}
						
		}
		
		if( ips.size() == 0 )
		{
			return InternetPricematch.NOT_FOUND;
		}
		
		for( int i=0; i<ips.size(); i++ )
		{
			InternetPricematch ip = ips.get( i );
			if( ip == InternetPricematch.FAILED )
			{
				return ip;
			}
		}
		
		
		for( int i=0; i<ips.size(); i++ )
		{
			InternetPricematch ip = ips.get( i );
			if( ip.isValid() )
			{
				return ip;
			}
		}
		
		
		
		
		return null;
		
	}
	private static InternetPricematch analyzeAmazonPiece( ArrayList<String> piece , String url ){
		
		
		boolean debug = false;
		
		
		//if( debug ) System.out.println( "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
		//if( debug ) ArrayToString.printArray( piece , "\n" );
		//if( debug ) System.out.println( "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
		
		
		
		
		
		String acceptedSeller = "http://ecx.images-amazon.com/images/I/01dXM-J1oeL.gif";		
		
		ArrayList<String> substringArray = Methods.getSubstringArray( piece , "<p class=\"a-spacing-small olpSellerName\">" , "</p>" );
		//ArrayToString.printArray( substringArray , "\n" , true );
		
		String seller = substringArray.get( 0 );
		//if( debug ) System.out.println( "seller: " + seller );
		
		if( seller.contains( "<img src=\"" ) )
		{
			seller = Methods.getSubstring( seller , "<img src=\"" , "\"" );
			if( debug ) System.out.println( "seller: " + seller );
		}
		else if( seller.contains( "<a href" ) )
		{
			int index = seller.indexOf( "<a href" );
			int startIndex = seller.indexOf( ">" , index+1 )+1;
			int endIndex = seller.indexOf( "</a>" , startIndex );
			seller = seller.substring( startIndex , endIndex );
			if( debug ) System.out.println( "seller: " + seller );
		}
		
		
		if( !seller.equals( acceptedSeller ) )
		{
			if( debug ) System.out.println(  "not an accepted seller: " + seller );
			if( debug ) System.out.println(  "---------------------------------------------------------------------------------------------------" );
			return InternetPricematch.THIRD_PARTY;
		}
		
		
		
		
		
		
		
		
		
		
		double totalPrice;
		
		if( piece.get( 0 ).startsWith( "to see product details." ) )
		{
//			try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			
//			String input = JOptionPane.showInputDialog( "Unable to see sale price" );
//			input = input.trim().replace( "$" , "" ).replace( "," , "" );
//			totalPrice = Double.parseDouble( input );
			
			return getPriceFromUser( "Amazon" , url , "See price in cart" );
			
			
		}
		else
		{
			String priceString = Methods.getSubstring( piece , "OfferPrice a-text-bold\">" , "</span>" );
			priceString = priceString.trim().replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			if( debug ) System.out.println( "price: " + F.format( price ) );
			
			double shipping;
			if( Methods.contains( piece , "FREE Shipping" ) )
			{
				shipping = 0;
				if( debug ) System.out.println( "shipping: " + "FREE" );
			}
			else
			{
				String shippingString = Methods.getSubstring( piece , "<span class=\"olpShippingPrice\">" , "</span>" );
				shippingString = shippingString.trim().replace( "$" , "" ).replace( "," , "" );
				shipping = Double.parseDouble( shippingString );
				if( debug ) System.out.println( "shipping: " + F.format( shipping ) );
			}
			
				
			
			totalPrice = price + shipping;
		}
		
			
		
		
		ArrayList<String> conditionArray = Methods.getSubstringArray( piece , "<h3 class=\"a-spacing-small olpCondition\">" , "</h3>" );
		if( debug ) System.out.println( "conditionArray" );
		if( debug )	ArrayToString.printArray( conditionArray , N );
		
		if( conditionArray == null )
		{
			System.out.println( "analyzeAmazonPiece: failed to find condition" + " (conditionArray == null)" );
			System.out.println( "url: " + url );
			System.out.println( "--------------------------------------------------------------------------------------------------------------------------" );
			ArrayToString.printArray( piece , "\n" );
			return InternetPricematch.FAILED;
		}
		
		if( conditionArray.size() != 1 )
		{
			System.out.println( "conditionArray.size() != 1" );
			ArrayToString.printArray( conditionArray , N );
			System.out.println( "\t" + url );
			return InternetPricematch.FAILED;
		}
		
		String condition = conditionArray.get( 0 ).trim();
		
		if( debug ) System.out.println( "condition: " + condition );
			
		
		if( !condition.equals( "New" ) )
		{
			System.out.println( "WWWWWWWWTTTTTTTTTTTTTTTFFFFFFFFFFFFFF DIS NIGGUH ISNT NEW!: " + condition );
			System.out.println( "\t" + url );
			return InternetPricematch.FAILED;
		}
		
		
		
		
		
		
		if( debug ) System.out.println(  F.format( totalPrice ) + " - " + seller );
		if( debug ) System.out.println(  "---------------------------------------------------------------------------------------------------" );
		
		return new InternetPricematch( "Amazon" , totalPrice , url );
		
		
	}
	
	
	
	
	
	//doesnt accept upc or model# :(
	

	
	
	public static InternetPricematch searchCostco( String modelNumber ){
		
		String url = "http://www.costco.com/CatalogSearch?catalogId=10701&langId=-1&keyword=" + modelNumber.replace( " " , "+" );
		try{ java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
		return null;
		
	}
	
	
	

	
	
	
	
	
	
	public static ArrayList<Product> loadProductsFromTextFile( String savePath ){
		
		
		File textFile = new File( savePath );
		if( !textFile.exists() )
		{
			return new ArrayList<Product>();
		}
		
		try
		{
			String saveText = Methods.readTextFile( textFile );
			ArrayList<Product> products = Product.loadProducts( saveText );
			System.out.println( "loaded " + products.size() + " products" );
			
			return products;
			
		}
		catch ( IOException e )
		{
			
			e.printStackTrace();
			String message = "failed to load";
			JOptionPane.showMessageDialog( null , message );
			
			return new ArrayList<Product>();
			
		}
	}
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		
		
//		String[] upcList = { "887899509988" , "888440787473" , "888440065274" , "887899501029" , "22265689455" , "886541903846" , "887899463921" , "886227660605" , "884116135364" , "886227605965" , "886227603206" , "888440091907" , "888440087429" , "888440108650" , "886227603190" , "886227651566" , "888440245331" , "887899302770" , "886227505821" , "887899352188" , "888440093376" , "887276979441" , "884116135555" , "888182210864" , "888440091921" , "887899463938" , "888182135204" , "27242963351" , "886227660612" };																							
//		for( int i=0; i<upcList.length; i++ )
//		{
//			//searchMicroCenter( upcList[i] );
//			//searchTigerDirect( upcList[i] );
//			//searchRakuten( upcList[i] );
//			//searchStaples( upcList[i] );
//			//searchSamsClub( upcList[i] );
//			searchBH( upcList[i] );
//			
//			
//		}
		
		
		
//		String SAVE_PATH = "save.txt";
//		ArrayList<Product> products = loadProductsFromTextFile( SAVE_PATH );
//		
//		//int count = products.size();
//		int start = 20;
//		int count = 20;
//		for( int i=start; i<start+count; i++ )
//		{
//			Product p = products.get( i );
//			InternetPricematch ip = searchAmazon( p.getUpc() );
//			System.out.println( ip );
//		}
//		System.out.println( "done scanning " + count + " products." );
		
		
		
		
		//searchNewegg( "610839331574" );
		//System.out.println( searchNewegg( "888772456290" ) );
		
		//searchOfficeDepot( "718037812359" );
		//searchStaples( "027242962361" );
		
		//searchWalmart( "829610880501" );
		searchWalmart( "888772456290" );
		
		
	}
	
	
	
	
	
	
	
}





































