package products;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import basic.InternetPricematch;

import table.RowData;

import myLibrary.Methods;


public class Desktop extends Product implements RowData {

	
	
	private String processor = null;
	private String ram = null;
	private String type = null;
	
	public Desktop( String plu , double price ){
	
		super( plu, price );
		// TODO Auto-generated constructor stub
		
		
		
	}
	public Desktop( String plu , String upc , double price ){
		
		super( plu , upc , price );
		
		
	}
	
	
	
	//for testing purposes - delete after
	public Desktop( String description ){
	
		super( description );
		
	}
	
	public String getShortDescription(){
		
		ArrayList<String> delimiters = new ArrayList<String>();
		delimiters.add( "Desktop" );
		delimiters.add( "Gaming PC" );
		delimiters.add( "Touch All-in-One" );
		delimiters.add( "All-in-One PC" );
		
		
		
		ArrayList<String> x = Methods.split( this.getDescription() , delimiters , true , true , false );
		
		String shortDescription = x.get( 0 );
		
//		// add type to end of short description
//		for( int i=0; i<delimiters.size(); i++ )
//		{
//			String d = delimiters.get( i );
//			
//			if( this.getDescription().contains( d ) )
//			{
//				shortDescription += " " + d;
//				return shortDescription;
//			}
//		}
		
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
		
		if( this.processor.contains( "J2900" ) || this.processor.contains( "J2850" ) )
		{
			return "PQC";
		}
		
		
		
		
		String[] shortProcessors = { "Atom" , "Cel" , "PDC" , "i3" , "i5" , "i7" , "E1" , "A4" , "A6" , "A8" , "A10" , "FX" };
		for( int i=0; i<shortProcessors.length; i++ )
		{
			if( this.processor.contains( shortProcessors[i] ) )
			{
				return shortProcessors[i];
			}
		}
		
		
		
		return null;
		//throw new IllegalArgumentException( "getShortProcessor(): " + "could not identify processor" + "\n" + "processor: " + this.processor );
		
		
		
		
		
	}
	
	public String getRam(){
		
		return this.ram;
		
	}
	public String getType(){
		
		return this.type;
		
	}
	
	
	
	
	public void frysInfo() throws IOException{
		
		super.frysInfo();
		
		//System.out.println( "laptop.frysInfo()" );
		
		analyzeDescription2();
		
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
		updateType();
		
		
		//System.out.println( processor );
		
		
		
		
		
	}
	
	private void updateProcessor(){
		
		//System.out.println( "Description: " + this.getDescription() );
		
		if( this.getDescription() == null )
		{
			//System.out.println( "asdfasdfasdf" );
			return;
		}
		
				
		String description = this.getDescription();
		
		
		
		//description = description.replace( "Core Ci" , "Core i" );
		//description = description.replace( "Quad Core A4" , "A4" );
		
		
		
		
		String[] acceptedProcessors =  { "Celeron G1610T" , "Celeron J1750" , "Pentium J2900" , "Intel Pentium Quad-Core J2850" , "i3-3220" , "i5-3330" , "i3-4010u" , "i3-4130" , "i5-3337u" , "i5-3350" , "i5-4440" , "i5-4570" , "i7-3770" , "i7-4770" ,  
										 "A4-5000" , "A4-6300" , "A6-5200" , "A8-5500" , "A8-6500" , "A10-6700" , "FX-4130" , "FX-4300" , "FX-6300" , "FX-8300" };
		
		
		for( int i=0; i<acceptedProcessors.length; i++)
		{
			
			if( description.toLowerCase().contains( acceptedProcessors[i].toLowerCase() ) )
			{
				processor = acceptedProcessors[i];
				//System.out.println( "found one" );
				i = acceptedProcessors.length;
				
			}
		}
		
		
		
		
		
		
		if( processor != null )
		{
			if( processor.equals( "QC T-Z3740" ) )
			{
				processor = "Intel Atom Z3740";
			}
			
			
			
			if( processor.contains( "i3" )   &&   !processor.contains( "Intel" ) )
			{
				processor = processor.replace( "i3" , "Intel i3" );
			}
			if( processor.contains( "i5" )   &&   !processor.contains( "Intel" ) )
			{
				processor = processor.replace( "i5" , "Intel i5" );
			}
			if( processor.contains( "i7" )   &&   !processor.contains( "Intel" ) )
			{
				processor = processor.replace( "i7" , "Intel i7" );
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
			if( processor.contains( "FX" )   &&   !processor.contains( "AMD" ) )
			{
				processor = processor.replace( "FX" , "AMD FX" );
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
		
		String[] acceptedRam = { "4GB Memory" , "4GB DDR3 Memory" , "6GB Memory" , "6GB DDR3 Memory" , "8GB Memory" , "8GB DDR3 Memory" , "10GB Memory" , "10GB DDR3 Memory" , "12GB Memory" , "12GB DDR3 Memory" , "12GB Memory" , "16GB DDR3 Memory" };
		
		for( int i=0; i<acceptedRam.length; i++)
		{
			
			if( description.toLowerCase().contains( acceptedRam[i].toLowerCase() ) )
			{
				ram = acceptedRam[i].replace( " DDR3 Memory" , "" ).replace( " Memory" , "" ).trim();
				
				
				i = acceptedRam.length;
				
			}
		}
		
		
		
		
	}
	private void updateType(){
		
		
		if( this.getDescription().contains( "Desktop" ) )
		{
			this.type = "Desktop";
		}
		else if( this.getDescription().contains( "All-in-One" ) )
		{
			this.type = "All-in-One";
		}
		else if( this.getDescription().contains( "Gaming PC" ) )
		{
			this.type = "Gaming PC";
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
		
		
		
		
		String plu = "= " + Q + this.getPlu() + Q;
		String upc = "= " + Q + this.getUpc() + Q;
		
		String shortCPU = this.getShortProcessor();
		if( shortCPU == null )
		{
			shortCPU = "?";
		}
		
		//String output = plu + T + upc + T + shortDescription + T + priceString;
		
		//String output = plu + T + upc + T + getDescription() + T + getShortDescription() + T + getManufacturer() + T + getModelNumber() + T + shortCPU + T + getRam() + T +  type;
		String output = plu + T + upc + T + getShortDescription() + T + getType() + T + shortCPU + T + getRam() + T + priceString;
		
		
		
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
			
			Desktop desktop = new Desktop( description );
			
			desktop.analyzeDescription2();
			
			
			System.out.println( desktop.getDescription() + T + desktop.getShortDescription() + T + "" + T + "" + T + desktop.getShortProcessor() + T + desktop.getRam() + T + desktop.getType() );
			
		}
		
	}
	
	public static Desktop getDummy(){
		
		return new Desktop( "" , 0 );
		
	}
	
	
	
	
	
	

	@Override
	public Object getValueAt( int actualC ){
	
		if		( actualC == 0 )	return this.getPlu();
		else if	( actualC == 1 )	return this.getUpc();
		else if	( actualC == 2 )	return this.getShortDescription();
		else if	( actualC == 3 )	return this.getShortProcessor();
		else if	( actualC == 4 )	return this.getRam();
		else if	( actualC == 5 )	return this.getPrice();
		else						throw new IllegalArgumentException();
		
	}
	@Override
	public void setValueAt( int actualC , Object object ){
	
		throw new UnsupportedOperationException( "Not supported yet." );
		
	}
	@Override
	public int getColumnCount(){
	
		return 7;
		
	}
	@Override
	public ArrayList<String> getColumnNames(){
	
		
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add( "PLU" );
		columnNames.add( "UPC" );
		columnNames.add( "Description" );
		columnNames.add( "CPU" );
		columnNames.add( "RAM" );
		columnNames.add( "Screen" );
		columnNames.add( "Price" );
		
		//columnNames.add( "Competitor" );
		//columnNames.add( "C. Price" );
		//columnNames.add( "Difference" );
		
		return columnNames;
		
		
	}
	@Override
	public Class<?> getColumnClass( int actualC ){
	
		if		( actualC == 0 )	return String.class;
		else if	( actualC == 1 )	return String.class;
		else if	( actualC == 2 )	return String.class;
		else if	( actualC == 3 )	return String.class;
		else if	( actualC == 4 )	return String.class;
		else if	( actualC == 5 )	return String.class;
		else if	( actualC == 6 )	return Double.class;
		else						throw new IllegalArgumentException();
		
	}
	@Override
	public boolean isCellEditable( int actualC ){
	
		return false;
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		
		analyzeDescriptionsFromClipboard();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}



























