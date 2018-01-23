package products;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.UIManager;

import table.RowData;

import basic.InternetPricematch;

import myLibrary.*;


public class Product implements RowData {
	
	
	final static double SPECIAL_PRICE = Double.NaN;
	public final static DecimalFormat F = new DecimalFormat( "$0.00" );
	public final static DecimalFormat P = new DecimalFormat( "0%" );
	
	final static String T = "\t";
	final static String N = "\n";
	final static String Q = "\"";
	
	
	
	
	
	private String plu;
	private String upc;
	
	private String description;
	private String manufacturer;
	private String modelNumber;
	private double price;
	private double cost;
	
	private PscGroup pscGroup;
	
	
	
	private ArrayList<InternetPricematch> competitorPrices = new ArrayList<InternetPricematch>();
	
	
	
	
	private InternetPricematch selectedIp = null;
	private Psc selectedPsc = null;
	
	
	
	
	public Product( String plu , double price ){
		
		this.plu = plu;
		this.price = price;
		
	}
	
	//for testing purposes - delete after
	public Product( String description ){
		
		this.description = description;
		
	}
	public Product( String plu , String upc , double price ){
		
		this.plu = plu;
		this.upc = upc;
		this.price = price;
		
	}
	
	
	//created for use by    laptop.applySaveString( String saveString )
	public Product(){
		
		
	}
	
	
	public void clear(){
		
		this.plu = null;
		this.upc = null;
		
		this.description = null;
		this.manufacturer = null;
		this.modelNumber = null;
		this.price = 0;
		this.cost = 0;
		
		competitorPrices = new ArrayList<InternetPricematch>();
		
		pscGroup = null;
		
		
	}
	
	//get
	public String getPlu(){
		
		return this.plu;
		
	}
	public String getUpc(){
		
		return this.upc;
		
	}
	public String getDescription(){
		
		return this.description;
		
	}
	public String getManufacturer(){
		
		return this.manufacturer;
		
	}
	public String getModelNumber(){
		
		return this.modelNumber;
		
	}
	public double getPrice(){
		
		return this.price;
		
	}
	public double getCost(){
		
		return this.cost;
		
	}
	
	public String getShortDescription(){
		
		if( description == null )
		{
			//return "?";
			return null;
		}
		
		int commaIndex = description.indexOf( "," );
		if( commaIndex != -1 )
		{
			String shortDescription = description.substring( 0 , commaIndex );
			return shortDescription;
		}
		else
		{
			return description;
		}
		
		
		
		
	}
	
	
	//psc
	public PscGroup getPscGroup(){
		
		return this.pscGroup;
		
	}
	public double getPscPrice2(){
		
		return this.pscGroup.getByYears( 2 ).getPrice();
		
	}
	public double getPscPrice3(){
		
		return this.pscGroup.getByYears( 3 ).getPrice();
		
	}
	public int getPscCount(){
		
		return getPscGroup().getCount();
		
	}
	public Psc getPsc( int i ){
		
		return getPscGroup().get( i );
		
	}
	public void setPscGroup( PscGroup pscGroup ){
		
		this.pscGroup = pscGroup;
		
	}
	
	
	
	
	
	
	//competitor price
	public ArrayList<InternetPricematch> getCompetitorPrices(){
		
		return this.competitorPrices;
		
	}
	public int getCompetitorPriceCount(){
		
		return this.competitorPrices.size();
		
	}
	public int getCompetitorPriceCount( double minDiff ){
		
		int count = 0;
		for( int i=0; i<competitorPrices.size(); i++ )
		{
			InternetPricematch ip = competitorPrices.get( i );
			if( this.getPrice() == 0 )
			{
				count++;
			}
			else
			{
				double diff = ip.getPrice() - this.getPrice();
				if( diff < minDiff )//more negative
				{
					count++;
				}
			}
				
		}
		
		return count;
		
	}
	public InternetPricematch getCompetitorPrice( int i ){
		
		return this.competitorPrices.get( i );
		
	}
	public InternetPricematch getCheapestPricematch(){
		
		if( competitorPrices.size() == 0 )
		{
			return null;
		}
		
		int lastIndex = competitorPrices.size() - 1;
		return this.competitorPrices.get( lastIndex );
		
	}
	public void clearCompetitorPrices(){
		
		competitorPrices.clear();
		
	}
	
	
	//calculations
	public double getSelectedSubtotal(){
		
		//InternetPricematch selectedIp , Psc selectedPsc;
		
		double price = this.getPrice();
		if( this.selectedIp != null )
		{
			price = this.selectedIp.getPrice();
		}
		
		double pscPrice = 0;
		if( this.selectedPsc != null )
		{
			pscPrice = this.selectedPsc.getPrice();
		}
		
		return price + pscPrice;
		
	}
	public double getSelectedCost(){
		
		if( this.cost == 0 )
		{
			return Double.NaN;
		}
		
		
		if( this.selectedPsc == null )
		{
			return this.getCost();
		}
		else
		{
			if( selectedPsc.getCost() == 0 )
			{
				return Double.NaN;
			}
			
			return this.getCost() + selectedPsc.getCost();
		}
		
		
	}
	public double getSelectedGp(){
		
		
		double selectedSubtotal = getSelectedSubtotal();
		double selectedCost = getSelectedCost();
		
		if(   Double.compare( selectedSubtotal , Double.NaN ) == 0   ||   Double.compare( selectedCost , Double.NaN ) == 0   )
		{
			return Double.NaN;
		}
		
		
		return ( selectedSubtotal - selectedCost ) / selectedSubtotal;
		
		
	}
	
	public double getGp(){
		
		if( this.cost == 0 || this.price == 0 )
		{
			return Double.NaN;
		}
		
		return ( this.getPrice() - this.getCost() ) / this.getPrice();
		
	}
	public void setGp( double gp ){
		
//		System.out.println( "setGP( " + gp + " )" );
//		System.out.println( "current price: " + price );
//		System.out.println( "current cost: " + cost );
//		System.out.println( "---" );
		
		if( Double.compare( gp , Double.NaN ) == 0 )
		{
			this.setCost( 0 );
			return;
		}
		
		
		
		double newCost = price * ( 1-gp );
		this.setCost( newCost );
		
		
		
		System.out.println( "new price: " + price );
		System.out.println( "new cost: " + cost );
		System.out.println( "new GP: " + this.getGp() );
		
		
	}
	
	
	//selections
	public void setSelectedIp( InternetPricematch ip ){
		
		this.selectedIp = ip;
		
	}
	public void setSelectedPsc( Psc psc ){
		
		this.selectedPsc = psc;
		
	}
	
	public InternetPricematch getSelectedIp(){
		
		return this.selectedIp;
		
	}
	public Psc getSelectedPsc(){
		
		return this.selectedPsc;
		
	}
	
	
	
	
	//set
	public void setPlu( String plu ){
		
		this.plu = plu;
		
	}
	public void setUpc( String upc ){
		
		this.upc = upc;
		
	}
	
	public void setDescription( String description ){
		
		this.description = description;
		
	}	
	public void setManufacturer( String manufacturer ){
		
		this.manufacturer = manufacturer;
		
	}
	public void setModelNumber( String modelNumber ){
		
		this.modelNumber = modelNumber;
		
	}
	public void setPrice( double price ){
	
		this.price = price;
	
	}
	public void setCost( double cost ){
		
		this.cost = cost;
		
	}
	
	
	
	
	
	public void addPriceMatch( InternetPricematch compPrice ){
		
		if( compPrice != null )
		{
			competitorPrices.add( compPrice );
			Collections.sort( this.competitorPrices );
		}
		
			
		
	}
	
	
	public void frysInfo() throws IOException{
		
		
		boolean debug = false;
		
		
		
		
		
		String address = "http://www.frys.com/product/" + plu;
		
		String notFoundTag = "Sorry, but no products were found.";
		String[] cutoffs = { notFoundTag , "Detailed Description" };
		
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( address );
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( address , cutoffs , true );
		//if( debug ) Methods.printArray( htmlArray , "\n" , false );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "Frys: " + notFoundTag );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			throw new IllegalArgumentException( "PLU not found: " + plu );
		}
		
		
		this.upc = Methods.getSubstring( htmlArray , "<li style=\"width: 160px;\">UPC #" , "</li>" );
		if( upc.isEmpty() )
		{
			upc = null;
		}
		if( debug ) System.out.println( "upc: " + this.upc );
		
		
		
		
		
		this.description = Methods.getSubstring( htmlArray , "<meta property=\"og:description\" content=\"" , "\"/>" );
		this.description = this.description.replace( "Dekstop PC" , "Desktop PC" );
		this.description = this.description.replace( "Intel Pentium Dual Core Processor" , "PDC" );
		this.description = this.description.replace( "Intel Pentium Dual Core" , "PDC" );
		this.description = this.description.replace( "  " , ", " );
		this.description = this.description.trim();
		if( debug ) System.out.println( "description: " + this.description );
		
		
		this.manufacturer = Methods.getSubstring( htmlArray , "Manufacturer: " , "</li>" );
		this.manufacturer = this.manufacturer.trim();
		if( this.manufacturer.equals( "I" ) )
		{
			this.manufacturer = "V7";
		}
		if( debug ) System.out.println( "manufacturer: " + this.manufacturer );
		
		
		this.modelNumber = Methods.getSubstring( htmlArray , "Model #" , "</li>" );
		this.modelNumber = this.modelNumber.trim();
		if( this.modelNumber.equals( "" ) )
		{
			int commaIndex = description.indexOf( "," );
			String shortDescription = description.substring( 0 , commaIndex );
			this.modelNumber = shortDescription.replace( this.manufacturer , "" ).trim();
			//System.out.println( "*************" + T +  this.modelNumber );
		}
		if( debug ) System.out.println( "modelNumber: " + this.modelNumber );
		
		
		
		
		
//		//read price from website
//		String priceString = Methods.getSubstring( htmlArray , "price1_value_" + plu + "\" class=\"\">" , "</label>" );
//		priceString = priceString.replace( "$" , "" );
//		priceString = priceString.replace( "," , "" );
//		if( priceString.contains( "<s>" ) )
//		{
//			priceString = priceString.replace( "<s>" , "" );
//			priceString = priceString.replace( "</s>" , "" );
//			
//			this.price = SPECIAL_PRICE;
//			if( debug ) System.out.println( "price: " + "<special price>" );
//		}
//		else
//		{
//			this.price = Double.parseDouble( priceString );
//			if( debug ) System.out.println( "price: " + F.format( price ) );
//		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	// save & load
	public String getSaveString(){
		
		String saveString = "";
		
		
		saveString += "PLU: " + getPlu() + N;
		saveString += "UPC: " + getUpc() + N;
		
		saveString += "Description: " + getDescription() + N;
		//saveString += "Manufacturer: " + getManufacturer() + N;
		//saveString += "ModelNumber: " + getModelNumber() + N;
		
		saveString += "Price: " + F.format( getPrice() ) + N;
		saveString += "PSC: " + getPscSaveString() + N;
		saveString += "GP: " + this.getGp() + N;
		
		for( int i=0; i<competitorPrices.size(); i++ )
		{
			InternetPricematch ip = competitorPrices.get( i );
			saveString += "IP: " + getPricematchString( ip ) + N;
			
//			if( i != competitorPrices.size()-1 )
//			{
//				saveString += N;
//			}
			
		}
		
		if( saveString.endsWith( N ) )
		{
			saveString = saveString.substring( 0 , saveString.length()-1 );
		}
		
		System.out.println( saveString );
		
		return saveString;
		
	}
	
	
	
	public String getPricematchString( InternetPricematch ip ){
		
		return ip.getCompetitor() + T + F.format( ip.getPrice() ) + T + ip.getUrl();
		
	}
	public InternetPricematch getPricematch( String saveString ){
		
		String[] pieces = saveString.split( T );
		String competitor = pieces[0];
		String priceString = pieces[1];
		String url = pieces[2];
		
		double price = Double.parseDouble( priceString.replace( "$" , "" ).replace( "," , "" ) );
		
		return new InternetPricematch( competitor , price , url );
		
	}
	
	
	

	private String getPscSaveString(){
		
		
		if( pscGroup == null )
		{
			return "null";
		}
		
		
		
		String output = "";
		
		for( int i=0; i<pscGroup.getCount();i++ )
		{
			Psc psc = pscGroup.get( i );
			output += F.format( psc.getPrice() );
			
			if( i != pscGroup.getCount()-1 )
			{
				output += " / ";
			}
			
		}
		
		return output;
		
	}
	public void setPsc( String pscString ){
		
		if( pscString == null || pscString.isEmpty() )
		{
			this.pscGroup = null;
			return;
		}
		
		
		String[] pieces = pscString.split( " / " );
		String psc2_string = pieces[0].replace( "$" , "" ).replace( "," , "" );		double psc2 = Double.parseDouble( psc2_string );
		String psc3_string = pieces[1].replace( "$" , "" ).replace( "," , "" );		double psc3 = Double.parseDouble( psc3_string );
		
		for( int i=1; i<PscGroup.LAPTOP_PSCS.length; i++ )
		{
			PscGroup pscGroup = PscGroup.LAPTOP_PSCS[i];
			if(   pscGroup.get( 0 ).getPrice() == psc2   &&   pscGroup.get( 1 ).getPrice() == psc3   )
			{
				this.pscGroup = PscGroup.LAPTOP_PSCS[i];
				return;
			}
		}
		
		String message = "Product.setPsc( pscString ) - failed to find a matching PSC group";
		message += "pscString: " + pscString;
		throw new IllegalArgumentException( message );
		
		
	}
	
	
	
 	public static Product loadProduct( String saveString ){
		
		Product product = new Product();
		
		String[] lines = saveString.split( N );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] lineParts = line.split( ": " );
			
//			if( lineParts.length != 2 )
//			{
//				System.out.println( "lineParts.length != 2" + N + "line: " + line + N + T + Methods.getString( lineParts , N+T ) );
//			}
			
			
			String variable = lineParts[0];
			
			String value;
			if( lineParts.length == 1 )// UPC was blank
			{
				value = "null";
			}
			else
			{
				value = lineParts[1];
			}
			
			
			product.applySavePiece( variable , value );
			
			
		}
		
		
		return product;
		
	}
	private void applySavePiece( String variable , String value ){
		
		if( value.equals( "null" ) )
		{
			value = null;
		}
		
		if( variable.equals( "PLU" ) )					setPlu( value );
		else if( variable.equals( "UPC" ) )				setUpc( value );
		
		else if( variable.equals( "Description" ) )		setDescription( value );
		//else if( variable.equals( "Manufacturer" ) )	setManufacturer( value );
		//else if( variable.equals( "ModelNumber" ) )	setModelNumber( value );
		
		else if( variable.equals( "Price" ) )			setPrice( Double.parseDouble( value.replace( "$" , "" ).replace( "," , "" ) ) );
		else if( variable.equals( "PSC" ) )				setPsc( value );
		else if( variable.equals( "GP" ) )				setGp( Double.parseDouble( value ) );
		else if( variable.equals( "IP" ) )				addPriceMatch(   getPricematch( value )   );
		
		
	}
	
	
	public static String getSaveText( ArrayList<Product> products ){
		
		String output = "";
		String divider = N+N;
		
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			output += product.getSaveString();
			
			if( i != products.size()-1 )
			{
				output += divider;
			}
			
		}
		
		System.out.println( "==========================================================================================================================================================" );
		System.out.println( output );
		System.out.println( "==========================================================================================================================================================" );
		
		return output;
		
	}
	public static ArrayList<Product> loadProducts( String saveText ){
		
		ArrayList<Product> products = new ArrayList<Product>();
		String divider = N+N;
		
		String[] saveStrings = saveText.split( divider );//System.out.println( "loadProducts: " + "found " + saveStrings.length + " products" );
		for( int i=0; i<saveStrings.length; i++ )
		{
			String saveString = saveStrings[i];
			Product product = loadProduct( saveString );
			products.add( product );
			
		}
		
		
		return products;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public String toString2(){
		
		DecimalFormat f = new DecimalFormat( "$0.00" );
		
		String output = description;
		output += "\n" + "PLU: " + plu;
		output += "\n" + "UPC: " + upc;
		output += "\n" + "price: " + f.format( price );
		
		for( int i=0; i<competitorPrices.size(); i++ )
		{
			InternetPricematch c = competitorPrices.get( i );
			String competitorPrice = f.format( c.getPrice() );
			String difference = f.format( c.getPrice() - price );
			
			output += "\n" + T + c.getCompetitor() + ": " + competitorPrice + " " + "(" + difference + ")";
		}
		
		return output;
		
	}
	public String toString(){
		
		DecimalFormat f = new DecimalFormat( "$0.00" );
		
		if( upc == null )
		{
			return "= " + Q + this.plu + Q;
		}
		
		
		
		//String priceString = "";
		String priceString = f.format( price );
		
		
		
		
		String plu = "= " + Q + this.plu + Q;
		String upc = "= " + Q + this.upc + Q;
		
		
		//String output = plu + T + upc + T + getShortDescription() + T + priceString;
		String output = plu + T + upc + T + description;
		
		//output += T + modelNumber;
		
		
		
//		if( competitorPrices.size() > 0 )
//		{
//			CompetitorPrice c = competitorPrices.get( 0 );
//			
//			double limit = -20;
//			if( c.getPrice() - this.getPrice() < limit )
//			{
//				//=HYPERLINK( "https://www.google.com/" , "google" )
//				String competitor = "=HYPERLINK( " + Q + c.getUrl().replace( Q , "\Q ) + Q + " , " + Q + c.getCompetitor() + Q + " )";
//				String competitorPrice = f.format( c.getPrice() );
//				
//				output += T + competitor + T + competitorPrice;
//			}
//			
//			
//				
//		}
		
		
		return output;
		
	}
	
	
		
	
	
	
	
	public static Product getProduct( String plu ) throws IOException, IllegalArgumentException  {
		
		
		boolean debug = false;
		
		
		
		
		if( ! isPlu( plu ) )
		{
			throw new IllegalArgumentException( "PLU must be a 7-digit number." );
		}
		
		
		
		
		
		
		
		
		String address = "http://www.frys.com/product/" + plu;
		
		String notFoundTag = "Sorry, but no products were found.";
		String[] cutoffs = { notFoundTag , "Detailed Description" };
		
		//ArrayList<String> htmlArray = UrlReader.getHtmlArray( address );
		ArrayList<String> htmlArray = UrlReader.getHtmlArray( address , cutoffs , true );
		//if( debug ) Methods.printArray( htmlArray , "\n" , false );
		
		if( Methods.contains( htmlArray , notFoundTag ) )
		{
			if( debug ) System.out.println( "Frys: " + notFoundTag );
			if( debug ) System.out.println( "------------------end--------------------------------------------------------------------" );
			return null;
		}
		
		
		Product product = new Product();
		product.setPlu( plu );
		
		
		product.upc = Methods.getSubstring( htmlArray , "<li style=\"width: 160px;\">UPC #" , "</li>" );
		if( debug ) System.out.println( "upc: " + product.upc );
		
		
		
		
		
		product.description = Methods.getSubstring( htmlArray , "<meta property=\"og:description\" content=\"" , "\"/>" );
		product.description = product.description.replace( "Dekstop PC" , "Desktop PC" );
		product.description = product.description.replace( "Intel Pentium Dual Core Processor" , "PDC" );
		product.description = product.description.replace( "Intel Pentium Dual Core" , "PDC" );
		product.description = product.description.replace( "  " , ", " );
		product.description = product.description.trim();
		if( debug ) System.out.println( "description: " + product.description );
		
		
		product.manufacturer = Methods.getSubstring( htmlArray , "Manufacturer: " , "</li>" );
		product.manufacturer = product.manufacturer.trim();
		if( product.manufacturer.equals( "I" ) )
		{
			product.manufacturer = "V7";
		}
		if( debug ) System.out.println( "manufacturer: " + product.manufacturer );
		
		
		product.modelNumber = Methods.getSubstring( htmlArray , "Model #" , "</li>" );
		product.modelNumber = product.modelNumber.trim();
		if( product.modelNumber.equals( "" ) )
		{
			int commaIndex = product.description.indexOf( "," );
			String shortDescription = product.description.substring( 0 , commaIndex );
			product.modelNumber = shortDescription.replace( product.manufacturer , "" ).trim();
			//System.out.println( "*************" + T +  product.modelNumber );
		}
		if( debug ) System.out.println( "modelNumber: " + product.modelNumber );
		
		
		
		
		
//		//read price from website
//		String priceString = Methods.getSubstring( htmlArray , "price1_value_" + plu + "\" class=\"\">" , "</label>" );
//		priceString = priceString.replace( "$" , "" );
//		priceString = priceString.replace( "," , "" );
//		if( priceString.contains( "<s>" ) )
//		{
//			priceString = priceString.replace( "<s>" , "" );
//			priceString = priceString.replace( "</s>" , "" );
//			
//			product.price = SPECIAL_PRICE;
//			if( debug ) System.out.println( "price: " + "<special price>" );
//		}
//		else
//		{
//			product.price = Double.parseDouble( priceString );
//			if( debug ) System.out.println( "price: " + F.format( price ) );
//		}
		
		
		return product;
		
	}
	public static boolean isPlu( String plu ){
		
		if( plu.length() != 7 )
		{
			return false;
		}
		
		for( int i=0; i<7; i++ )
		{
			char c = plu.charAt( i );
			if( ! Character.isDigit( c ) )
			{
				return false;
			}
		}
		
		return true;
		
	}
	
	
	
	
	
	
	
	public static Product getDummy(){
		
		return new Product();
		
	}
	
	@Override
	public Object getValueAt( int actualC ){
	
		if		( actualC == 0 )	return this.getPlu();
		else if	( actualC == 1 )	return this.getPrice();
		else if	( actualC == 2 )	return this.getPscGroup();
		else if	( actualC == 3 )	return this.getGp();
		else if	( actualC == 4 )	return this.getShortDescription();
		else if	( actualC == 5 )	return this.getUpc();
		else if	( actualC == 6 )	return this.getCompetitorPriceCount( -30 );
		else						throw new IllegalArgumentException();
		
	}
	@Override
	public void setValueAt( int actualC , Object object ){
		
		
		if( actualC == 0 )	
		{ 	
			String newPlu = (String) object;
			String oldPlu = this.getPlu();
			
			if( ! newPlu.equals( oldPlu ) )
			{
				
				this.clear(); 
				this.setPlu( newPlu );
			}
			
			
			
		}//plu
		else if	( actualC == 1 )	{	if( object != null )	this.setPrice( (double) object );	}//price
		else if	( actualC == 2 )	{	this.setPscGroup( (PscGroup) object );   }//psc
		else if	( actualC == 3 )
		{
			//System.out.println( "changed GP: " + object + T + object.getClass() );//gp
			String input = (String) object;
			if( input.isEmpty() )
			{
				this.setCost( 0 );
			}
			else
			{
				double gp = Double.parseDouble( input ) * 0.01;
				this.setGp( gp );
			}
			
		}
		else if	( actualC == 4 )	throw new UnsupportedOperationException( "Not supported yet." );//description
		else if	( actualC == 5 )	throw new UnsupportedOperationException( "Not supported yet." );//upc
		else if	( actualC == 6 )	throw new UnsupportedOperationException( "Not supported yet." );//matches count
		else						throw new IllegalArgumentException();
		
		
	}
	@Override
	public int getColumnCount(){
	
		return 7;
		
	}
	@Override
	public ArrayList<String> getColumnNames(){
	
		
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add( "PLU" );
		columnNames.add( "Price" );
		columnNames.add( "PSC" );
		columnNames.add( "GP" );
		columnNames.add( "Description" );
		columnNames.add( "UPC" );
		columnNames.add( "IPs > $30" );
		
		return columnNames;
		
		
	}
	@Override
	public Class<?> getColumnClass( int actualC ){
	
		if		( actualC == 0 )	return String.class;//plu
		else if	( actualC == 1 )	return String.class;//price
		else if	( actualC == 2 )	return PscGroup.class;//psc
		else if	( actualC == 3 )	return Double.class;//GP
		else if	( actualC == 4 )	return String.class;//description
		else if	( actualC == 5 )	return String.class;//upc
		else if	( actualC == 6 )	return Integer.class;//matches count
		else						throw new IllegalArgumentException();
		
	}
	@Override
	public boolean isCellEditable( int actualC ){
	
		if		( actualC == 0 )	return true;//plu
		else if	( actualC == 1 )	return true;//price
		else if	( actualC == 2 )	return true;//psc
		else if	( actualC == 3 )	return true;//GP
		else if	( actualC == 4 )	return false;//description
		else if	( actualC == 5 )	return false;//upc
		else if	( actualC == 6 )	return false;//matches count
		else						throw new IllegalArgumentException();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
























