package products;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import basic.InternetPricematch;

import table.RowData;

import myLibrary.Methods;


public class Laptop extends Product {

	
	
	
	
	private String processor;
	private String ram;
	private String screenSize;
	
	
	public Laptop( String plu , double price ){
	
		super( plu, price );
		
	}
	public Laptop( String plu , String upc , double price ){
		
		super( plu , upc , price );
		
		
	}
	public Laptop( String plu , double price , double cost , double psc2 , double psc3 ){
		
		super( plu , price );
		
		this.setCost( cost );
		
		Psc psc_2 = new Psc( 2 , psc2 );
		Psc psc_3 = new Psc( 3 , psc3 );
		setPscGroup( new PscGroup( psc_2 , psc_3 ) );
		
	}
	
	
	//created for use by    laptop.applySaveString( String saveString )
	public Laptop(){
		
		
		
	}
	
	
	public Laptop( String description ){
		
		super( description );
		
	}
	
	
	public void clear(){
		
		super.clear();
		
		
		
		processor = null;
		ram = null;
		screenSize = null;
		
	}
	
	
	

	
	
	public String getShortDescription(){
		
		//System.out.println( "laptop.getShortDescription()" );
		
		String shortDescription = super.getShortDescription();
		
		if( shortDescription == null )
		{
			return null;
		}
		
		if( getProcessor() != null )
		{
			shortDescription = shortDescription.replace( getProcessor() , "" );
		}
		shortDescription = shortDescription.replace( "(Black)" , "" );
		shortDescription = shortDescription.trim();
		
		return shortDescription;
		
	}
	

	public String getProcessor(){
		
		return this.processor;
		
	}
	public String getShortProcessor(){
		
		
		
		
		if( this.processor == null )
		{
			return null;
		}
		
		if( this.processor.equals( "AMD Quad-Core Optimized for Samsung" ) )
		{
			return "AMD QC";
		}
		
		
		String[] shortProcessors = { "Atom" , "Cel" , "PDC" , "i3" , "i5" , "i7" , "E1" , "A4" , "A6" , "A8" , "A10" };
		for( int i=0; i<shortProcessors.length; i++ )
		{
			if( this.processor.contains( shortProcessors[i] ) )
			{
				return shortProcessors[i];
			}
		}
		
		
		
		
		//return null;
		throw new IllegalArgumentException( "getShortProcessor(): " + "could not identify processor" + N + "processor: " + this.processor + N + "PLU: " + this.getPlu() );
		
		
		
		
		
	}
	
	public String getRam(){
		
		return this.ram;
		
	}
	public String getScreenSize(){
		
		return this.screenSize;
		
	}
	
	
	
	
	public void frysInfo() throws IOException{
		
		super.frysInfo();
		
		analyzeDescription2();
		
		
	}
	
	public void setProcessor( String processor ){
		
		this.processor = processor;
		
	}
	public void setRam( String ram ){
		
		this.ram = ram;
		
	}
	public void setScreenSize( String screenSize ){
		
		this.screenSize = screenSize;
		
	}
	
	
	
	public void analyzeDescription(){
		
		String description = this.getDescription();
		
		description = description.replace( ", Touchscreen Display" , " Touchscreen Display" );
		description = description.replace( ", Screen Display" , " Screen Display" );
		description = description.replace( ", SSD Hard Drive" , " SSD Hard Drive" );
		description = description.replace( "AMD, A10" , "AMD A10" );
		description = description.replace( "SSD Hard Drive" , "SSD" );
		
		int index = description.indexOf( " Intel Core" );//System.out.println( "index: " + index );
		if( index != -1   &&   description.charAt( index-1 ) != ',' )
		{
			//System.out.println( "true" );
			description = description.replace( " Intel Core" , ", Intel Core" );
		}
		
		
		description = description.replace( ", Touchscreen " , " Touchscreen," );
		description = description.replace( ",," , "," );
		
		index = description.indexOf( " 15.6,  " );//System.out.println( "index: " + index );
		if( index != -1   &&   description.charAt( index-1 ) != ',' )
		{
			//System.out.println( "true" );
			description = description.replace( " 15.6, " , ", 15.6" );
		}
		
		description = description.replace( "15.6 Touch Screen Notebook With Intel PDC 2117 Processor" , "Intel PDC 2117 Processor, 15.6 Touch Screen" );
		
		
		//
		
		String shortDescription = null;
		String processor = null; 
		String screen = null; 
		String ram = null; 
		String storage = null; 
		String os = null;
		
		System.out.println( description );
		
		
		String[] pieces = description.split( "," );//System.out.println( "pieces: " + pieces.length );
		if( pieces.length == 6 )
		{
			shortDescription = pieces[0].trim();
			processor = pieces[1].trim();
			screen = pieces[2].trim();
			ram = pieces[3].trim();
			storage = pieces[4].trim();
			os = pieces[5].trim();
			
			
		}
		
		
		
		System.out.println( shortDescription + T + processor + T + screen + T + ram + T + storage + T + os );
		
		
	}
	public void analyzeDescription2(){
		
		
		
		updateProcessor();
		updateRam();
		updateScreensize2();
		
		
		//System.out.println( processor );
		
		
		
		
		
	}
	
	private void updateProcessor(){
		
		if( this.getDescription() == null )
		{
			return;
		}
		
		
		
		
		if( this.getPlu().equals( "6676224" ) )
		{
			processor = "i7-2630QM";
			return;
		}
		if( this.getPlu().equals( "7960680" ) )
		{
			processor = "Intel PDC 2117U";
			return;
		}
		if( this.getPlu().equals( "7988560" ) )
		{
			processor = "AMD E1-2100";
			return;
		}
		if( this.getPlu().equals( "8027044" ) )
		{
			processor = "AMD E1-2100";
			return;
		}
		if( this.getPlu().equals( "8050274" ) )
		{
			processor = "Intel Celeron N2815";
			return;
		}
		if( this.getPlu().equals( "8071174" ) )
		{
			processor = "AMD E1-2100";
			return;
		}
		if( this.getPlu().equals( "8027034" ) )
		{
			processor = "AMD Quad-Core Optimized for Samsung";
			return;
		}
		//
		//
		
		
		
		String description = this.getDescription();
		
		
		
		description = description.replace( "Core Ci" , "Core i" );
		description = description.replace( "Quad Core A4" , "A4" );
		
		
		
		
		//String[] acceptedProcessors =  { "Intel Core i7-2677M" , "Intel Core i7-3517"  , "Intel Core i5-3337U" , "AMD A10-4600" , "Intel Core i5-3317" , "AMD, A10-4600" , "Intel Core i7-3635" , "Intel Core i7-4500U" , "Intel Core i5-4200U" , "Intel Core i7-3537U" , "A10-5750M" , "E1-2100" , "Intel Celeron 1007U" , "Intel Core i5-3337" , "Intel Core i3-3217" , "Core i5-4200U" , "Intel Core i7-4700" , "Intel Core i5 3337" , "Intel PDC 2117 Processor" , "AMD A8-4555M" , "AMD A10-5757" , "Intel Core i7- 4702" , "Intel Core i7 4500" , "AMD A10-5745" , "Intel Core i7-4500" , "Intel PDC 2117" , "QC T-Z3740" , "Intel PDC 2117" , "Intel PDC 2117" , "Intel Core i7-3630" , "Intel Core i5-4200" , "Intel Core i7-3537" , "AMD A10-5750" , "AMD, A4-1200" , "AMD A6-5200" , "AMD A6-1450" , "AMD A8-5545" , "Intel Celeron N2820" , "Intel Core i5-3230" , "A4-5000M" , "A6-5200" , "Intel Celeron N2806" , "Intel PDC 3520" , "Intel Celeron 2920" , "Intel Core i5-4210" , "Intel PDC 2127 Processor" , "Intel Core i3-3110" , "Celeron 2815" , "AMD A4-5000" , "Intel Core i5-4202" ,  "PDC 2127U" , "PDC" , "Intel Core i7-4800" , "AMD Quad Core" };		
		String[] acceptedProcessors =  { "AMD Quad Core A10-5745" , "A8-4555M" , "A6-5350M" , "A10-5750M" , "A4-5000M" , "A6-5200" , "A6-4455M" , "AMD A10-4600" , "AMD A10-5745" , "AMD A10-5750" , "AMD A10-5757" , "AMD A4-5000" , "AMD A6-1450" , "AMD A6-5200" , "AMD A8-4555M" , "AMD A8-5545" , "AMD Quad Core" , "AMD, A10-4600" , "AMD, A4-1200" , "Celeron 2815" , "Core i5-4200U" , "E1-2100" , "Intel Celeron 1007U" , "Intel Celeron 2920" , "Intel Celeron N2806" , "Intel Celeron N2820" , "Intel Celeron 2955U" , "Intel Core i3-3110" , "Intel Core i3-3217" , "Intel Core i3-3227U" , "Intel Core i5 3337" , "Intel Core i5-3230" , "Intel Core i5-3317" , "Intel Core i5-3337" , "Intel Core i5-3337U" , "Intel Core i5-4200" , "Intel Core i5-4200U" , "Intel Core i5-4202" , "Intel Core i5-4210" , "Intel Core i7 4500" , "Intel Core i7- 4702" , "Intel Core i7-2677M" , "Intel Core i7-3517" , "Intel Core i7-3537" , "Intel Core i7-3537U" , "Intel Core i7-3630" , "Intel Core i7-3635" , "Intel Core i7-4500" , "Intel Core i7-4500U" , "Intel Core i7-4700" , "Intel Core i7-4800" , "Intel PDC 2117" , "Intel PDC 2117" , "Intel PDC 2117" , "Intel PDC 2117 Processor" , "Intel PDC 2127 Processor" , "Intel PDC 3520" , "PDC" , "PDC 2127U" , "QC T-Z3740" };
		
		
		for( int i=0; i<acceptedProcessors.length; i++)
		{
			
			if( description.toLowerCase().contains( acceptedProcessors[i].toLowerCase() ) )
			{
				processor = acceptedProcessors[i];
				
				i = acceptedProcessors.length;
				
			}
		}
		
		
		
		
		
		
		if( processor != null )
		{
			if( processor.equals( "QC T-Z3740" ) )
			{
				processor = "Intel Atom Z3740";
			}
			
			if( processor.contains( "A4" )   &&   !processor.contains( "AMD" ) )
			{
				processor = processor.replace( "A4" , "AMD A4" );
			}
			
			if( processor.contains( "A6" )   &&   !processor.contains( "AMD" ) )
			{
				processor = processor.replace( "A6" , "AMD A6" );
			}
			
			if( processor.contains( "A8" )   &&   !processor.contains( "AMD" ) )
			{
				processor = processor.replace( "A8" , "AMD A8" );
			}
			
			if( processor.contains( "A10" )   &&   !processor.contains( "AMD" ) )
			{
				processor = processor.replace( "A10" , "AMD A10" );
			}
			
			processor = processor.replace( "AMD, A10" , "AMD A10" );
			
			
			
			
			
		}
		
	}
	private void updateRam(){
		
		if( this.getDescription() == null )
		{
			return;
		}
		
		
		String description = this.getDescription();
		
		String[] acceptedRam = { " 16GB memory" , " 12GB Memory" , " 4GB Memory" , " 6GB Memory" , " 8GB Memory" , " 2GB Memory" , " 4GB" };
		
		for( int i=0; i<acceptedRam.length; i++)
		{
			
			if( description.toLowerCase().contains( acceptedRam[i].toLowerCase() ) )
			{
				ram = acceptedRam[i].replace( " Memory" , "" ).replace( " memory" , "" ).replace( "Notebook With " , "" ).trim();
				
				
				i = acceptedRam.length;
				
			}
		}
		
		
		
		
	}
	private void updateScreensize(){
		
		if( this.getDescription() == null )
		{
			return;
		}
		
		
		String description = this.getDescription().toLowerCase();
		
		int index = description.indexOf( " Screen Display".toLowerCase() );
		if( index != -1 )
		{
			int lastOccuranceOfSpace = Methods.getLastOccuranceOf( description , " " , index );
			String sizeString = description.substring( lastOccuranceOfSpace+1 , index );
			System.out.println( sizeString );
		}
		
			
		
		
		
	}
	private void updateScreensize2(){
		
		if( this.getDescription() == null )
		{
			return;
		}
		
		
		
		if( this.getPlu().equals( "8062204" ) )
		{
			screenSize = "15.6" + Q;
			return;
		}
		
		
		
		
		
		String description = this.getDescription();
		
		String[] acceptedScreensize = { "17.3, Gaming notebook" , "11.6 Screen" , "13.3, Screen" , "17.3, Screen" , "11.6, Screen" , "15.6, Screen" , "13.3, Touchscreen" , "11.6, Touchscreen" , "15.5, Touchscreen" , "15.6 TOUCH Screen" , "15.6, TOUCH" , "15.6, Touchscreen" , "15.6,  Touch Screen" , "12.5, TOUCH Screen" , "10.1, Touchscreen" , "15.5, Screen" , "10.1, Screen" , "14, Screen" , "11.6, TOUCH Screen" , "15.5, Screen" , "17.3, Touch Screen" , "14, Touch Screen" , "13.3, Touch Screen" , "10.1, Touch Screen" , "15.6," , "15.6, Display Screen" , "14, Touchscreen" , "17.3, Display Screen" ,  };
		
		for( int i=0; i<acceptedScreensize.length; i++)
		{
			
			if( description.toLowerCase().contains( acceptedScreensize[i].toLowerCase() ) )
			{
				screenSize = acceptedScreensize[i].replace( "Gaming notebook" , "" ).replace( "Screen" , "" ).replace( "Touchscreen" , "" ).replace( "Touch Screen" , "" ).replace( "TOUCH" , "" ).replace( "Touch" , "" ).replace( "Display" , "" ).replace( "," , "" ).trim();
				screenSize += Q;
				
				return;
				
			}
		}
		
		
//		String[] sizes = { "10.1" , "11.6" , "12.5" , "13.3" , "14" , "15.5" , "15.6" , "17.3" };
//		String[] mids = { ", " , " " };
//		String[] ends = { "Gaming notebook" , "Screen" , "Touchscreen" , "TOUCH Screen" ,  };
		
	}
	
	
	public double getCommission(){
		
		double gp = this.getGp();
		
		//gp is unknown(cost or price is 0)
		if( Double.compare( gp , Double.NaN ) == 0 )
		{
			return Double.NaN;
		}
		
		if( gp < 0.03 )//red tier - 0%
		{
			return 0;
		}
		else if( gp >= 0.03 && gp < 0.07 )//orange tier - 0.5%
		{
			return this.getPrice() * 0.005;
		}
		else if( gp >= 0.07 && gp < 0.10 )//blue tier - 1%
		{
			return this.getPrice() * 0.01;
		}
		else//green tier - 2.25%
		{
			return this.getPrice() * 0.0225;
		}
		
		
		
	}
	
	
	
	
	
	/* save and load laptops methods
	public String getSaveString(){
		
		String saveString = "";
		
		
		saveString += "PLU: " + getPlu() + T;
		saveString += "UPC: " + getUpc() + T;
		
		saveString += "Description: " + getDescription() + T;
		saveString += "Manufacturer: " + getManufacturer() + T;
		saveString += "ModelNumber: " + getModelNumber() + T;
		
		saveString += "Processor: " + getProcessor() + T;
		saveString += "RAM: " + getRam() + T;
		saveString += "Screen size: " + getScreenSize();
		
		
		
		return saveString;
		
	}
	public static Laptop applySaveString( String saveString ){
		
		Laptop laptop = new Laptop();
		
		
		
		
		
		return laptop;
		
	}
	private void applySavePiece( String variable , String value ){
		
		if( variable.equals( "PLU" ) )					setPlu( value );
		else if( variable.equals( "UPC: " ) )			setUpc( value );
		
		else if( variable.equals( "Description" ) )		setDescription( value );
		else if( variable.equals( "Manufacturer" ) )	setManufacturer( value );
		else if( variable.equals( "ModelNumber" ) )		setModelNumber( value );
		
		else if( variable.equals( "Processor" ) )		setProcessor( value );
		else if( variable.equals( "RAM" ) )				setRam( value );
		else if( variable.equals( "Screen size" ) )		setScreenSize( value );
		
		
		
		
	}
	*/
	
	public String toString_old(){
		
		DecimalFormat f = new DecimalFormat( "$0.00" );
		
		if( this.getUpc() == null )
		{
			return "= " + Q + this.getPlu() + Q;
		}
		
		
		
		//String priceString = "";
		String priceString = f.format( this.getPrice() );
		
		
		
		
		String plu = "= " + Q + this.getPlu() + Q;
		String upc = "= " + Q + this.getUpc() + Q;
		
		String shortCPU = this.getShortProcessor();
		if( shortCPU == null )
		{
			shortCPU = "?";
		}
		
		//String output = plu + T + upc + T + shortDescription + T + priceString;
		String output = plu + T + upc + T + getShortDescription() + T + shortCPU + T + getRam() + T + getScreenSize() + T + priceString;
		
		//output += T + modelNumber;
		
		
		
		if( this.getCompetitorPrices().size() > 0 )
		{
			InternetPricematch c = this.getCompetitorPrices().get( 0 );
			
//			double limit = -20;
//			if( c.getPrice() - this.getPrice() < limit )
//			{
//				//=HYPERLINK( "https://www.google.com/" , "google" )
//				String competitor = "=HYPERLINK( " + Q + c.getUrl().replace( Q , "\"\"" ) + Q + " , " + Q + c.getCompetitor() + Q + " )";
//				String competitorPrice = f.format( c.getPrice() );
//				
//				output += T + competitor + T + competitorPrice;
//			}
			
			
			String competitor = "=HYPERLINK( " + Q + c.getUrl().replace( Q , "\"\"" ) + Q + " , " + Q + c.getCompetitor() + Q + " )";
			String competitorPrice = f.format( c.getPrice() );
			
			output += T + competitor + T + competitorPrice;
			
			
				
		}
		
		
		
		
		
		return output;
		
		
	}
	public String toString2(){
		
		
		if( this.getUpc() == null )
		{
			return "= " + Q + this.getPlu() + Q;
		}
		
		
		String plu = "= " + Q + this.getPlu() + Q;
		String upc = "= " + Q + this.getUpc() + Q;
		
		String shortCPU = this.getShortProcessor();
		if( shortCPU == null )
		{
			shortCPU = "?";
		}
		
		//String output = plu + T + upc + T + shortDescription + T + priceString;
		String output = plu + T + upc + T + getDescription() + T + shortCPU + T + getRam() + T + getScreenSize();
		
		
		
		return output;
		
	}
	
	public String toString(){
		
		
		//int row = i+6;
		
		
		if( this.getUpc() == null )
		{
			return "= " + Q + this.getPlu() + Q;
		}
		
		
		
		
		
		
		//String priceString = "";
		String priceString = F.format( this.getPrice() );
		String costString = F.format( this.getCost() );
		
		
		double gp = this.getGp();
		String gpString = P.format( gp );
		
		
		String plu = "= " + Q + this.getPlu() + Q;
		String upc = "= " + Q + this.getUpc() + Q;
		
		String shortCPU = this.getShortProcessor();
		if( shortCPU == null )
		{
			shortCPU = "?";
		}
		
		
		String competitor = "";
		String cPrice = "";
		String diffString = "";
		String managerSpecial2 = "";
		String managerSpecial3 = "";
		
		if( this.getCompetitorPrices().size() > 0 )
		{
			InternetPricematch c = this.getCheapestPricematch();
			
//			double limit = -20;
//			if( c.getPrice() - this.getPrice() < limit )
//			{
//				
//			}
			
			//=HYPERLINK( "https://www.google.com/" , "google" )
			competitor = "=HYPERLINK( " + Q + c.getUrl().replace( Q , "\"\"" ) + Q + " , " + Q + c.getCompetitor() + Q + " )";
			cPrice = F.format( c.getPrice() );
			
			double diff = c.getPrice() - this.getPrice();
			diffString = F.format( diff );
			
			managerSpecial2 = F.format( c.getPrice() + this.getPscPrice2() );
			managerSpecial3 = F.format( c.getPrice() + this.getPscPrice3() );
			
			
		}
		
		
		String psc2_string = F.format( this.getPscPrice2() );
		String psc3_string = F.format( this.getPscPrice3() );
		
		
		
		//String output = plu + T + upc + T + shortDescription + T + priceString;
		String output = plu + T + upc + T + getShortDescription() + T + shortCPU + T + getRam() + T + getScreenSize() + T + priceString + T + costString + T + gpString + T + competitor + T + cPrice + T + diffString + T + psc2_string + T + psc3_string + T + managerSpecial2 + T + managerSpecial3 ;
		//output += T + managerSpecial2 + T + managerSpecial3;
		
		
		return output;
		
		
	}
	
	
	
	
	public static void analyzeDescriptionsFromClipboard(){
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		
		
		for( int i=0; i<lines.length; i++ )
		{
			String description = lines[i];
			
			Laptop laptop = new Laptop( description );
			
			laptop.analyzeDescription2();
			
			
			System.out.println( laptop.getProcessor() + T + laptop.getShortProcessor() + T + laptop.getRam() + T + laptop.getScreenSize() );
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		//analyzeDescriptionsFromClipboard();
		
		
//		//String[] acceptedProcessors =  { "Intel Core i7-2677M" , "Intel Core i7-3517"  , "Intel Core i5-3337U" , "AMD A10-4600" , "Intel Core i5-3317" , "AMD, A10-4600" , "Intel Core i7-3635" , "Intel Core i7-4500U" , "Intel Core i5-4200U" , "Intel Core i7-3537U" , "A10-5750M" , "E1-2100" , "Intel Celeron 1007U" , "Intel Core i5-3337" , "Intel Core i3-3217" , "Core i5-4200U" , "Intel Core i7-4700" , "Intel Core i5 3337" , "Intel PDC 2117 Processor" , "AMD A8-4555M" , "AMD A10-5757" , "Intel Core i7- 4702" , "Intel Core i7 4500" , "AMD A10-5745" , "Intel Core i7-4500" , "Intel PDC 2117" , "QC T-Z3740" , "Intel PDC 2117" , "Intel PDC 2117" , "Intel Core i7-3630" , "Intel Core i5-4200" , "Intel Core i7-3537" , "AMD A10-5750" , "AMD, A4-1200" , "AMD A6-5200" , "AMD A6-1450" , "AMD A8-5545" , "Intel Celeron N2820" , "Intel Core i5-3230" , "A4-5000M" , "A6-5200" , "Intel Celeron N2806" , "Intel PDC 3520" , "Intel Celeron 2920" , "Intel Core i5-4210" , "Intel PDC 2127 Processor" , "Intel Core i3-3110" , "Celeron 2815" , "AMD A4-5000" , "Intel Core i5-4202" ,  "PDC 2127U" , "PDC" , "Intel Core i7-4800" , "AMD Quad Core" };		
//		String[] acceptedProcessors =  { "A10-5750M" , "A4-5000M" , "A6-5200" , "AMD A10-4600" , "AMD A10-5745" , "AMD A10-5750" , "AMD A10-5757" , "AMD A4-5000" , "AMD A6-1450" , "AMD A6-5200" , "AMD A8-4555M" , "AMD A8-5545" , "AMD Quad Core" , "AMD, A10-4600" , "AMD, A4-1200" , "Celeron 2815" , "Core i5-4200U" , "E1-2100" , "Intel Celeron 1007U" , "Intel Celeron 2920" , "Intel Celeron N2806" , "Intel Celeron N2820" , "Intel Core i3-3110" , "Intel Core i3-3217" , "Intel Core i5 3337" , "Intel Core i5-3230" , "Intel Core i5-3317" , "Intel Core i5-3337" , "Intel Core i5-3337U" , "Intel Core i5-4200" , "Intel Core i5-4200U" , "Intel Core i5-4202" , "Intel Core i5-4210" , "Intel Core i7 4500" , "Intel Core i7- 4702" , "Intel Core i7-2677M" , "Intel Core i7-3517" , "Intel Core i7-3537" , "Intel Core i7-3537U" , "Intel Core i7-3630" , "Intel Core i7-3635" , "Intel Core i7-4500" , "Intel Core i7-4500U" , "Intel Core i7-4700" , "Intel Core i7-4800" , "Intel PDC 2117" , "Intel PDC 2117" , "Intel PDC 2117" , "Intel PDC 2117 Processor" , "Intel PDC 2127 Processor" , "Intel PDC 3520" , "PDC" , "PDC 2127U" , "QC T-Z3740" };
//		
//		
//		ArrayList<String> list = new ArrayList<String>();
//		for( int i=0; i<acceptedProcessors.length; i++ )
//		{
//			list.add( acceptedProcessors[i] );
//		}
//		
//		Collections.sort( list );
//		
//		Methods.printArray( list );
//		
//		String s = "String[] acceptedProcessors =  { ";
//		for( int i=0; i<list.size(); i++ )
//		{
//			s += Q + list.get( i ) + Q + " , ";
//		}
//		s = s.substring( 0 , s.length()-3 );//cut off the last " , "
//		s += " };";
//		
//		System.out.println( s );
		
		
		
		
		
	}
	
	
	
	
	
	
	
}



























