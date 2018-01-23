package pricematchUI;

import gui.GradientPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableCellEditor;


import myLibrary.Methods;
import myLibrary.Stopwatch;

import basic.*;

import products.*;


import table.*;
import table.MyTable.StringEditor;


public class PricematchPanel extends GradientPanel {
	
	
	final static String N = "\n";
	final static String T = "\t";
	final static String Q = "\"";
	
	final static String SAVE_PATH = "save.txt";
	final static String PASSWORD = "tkpg33";
	
	final static String VERSION = "1.4.1";
	final static GregorianCalendar EXPIRATION_DATE = new GregorianCalendar( 2018 , GregorianCalendar.DECEMBER , 31 );
	
	
	
	
	
	
	
	JPanel passwordPanel = new JPanel();
		JPasswordField passwordField = new JPasswordField( 10 );
	
	JSplitPane sp = new JSplitPane();
	
		JPanel leftPanel = new JPanel();
			JPanel buttonPanel = new JPanel();
				JButton addBtn = new JButton( "Add" );
				JButton removeBtn = new JButton( "Remove" );
				JButton scanBtn = new JButton( "Scan All" );
				JLabel searchLabel = new JLabel( "Search" );
				JTextField searchField = new JTextField( 10 );
				JButton lockBtn = new JButton( "Lock" );
			JScrollPane tablePanel = new JScrollPane();
				ProductTable table = new ProductTable();
			
		JPanel rightPanel = new JPanel();
			JPanel topPanel = new JPanel();
				JLabel titleLabel = new JLabel();
				JLabel imageLabel = new JLabel();
				JLabel cpuLabel = new JLabel();
				JLabel ramLabel = new JLabel();
				JLabel screenLabel = new JLabel();
			JPanel middlePanel = new JPanel();
				JPanel pricematchSelectionPanel = new JPanel();
					JRadioButton[] pricematchButtons = new JRadioButton[0];		ButtonGroup pricematchButtonGroup = new ButtonGroup();
					JLabel pricematchPriceLables[] = new JLabel[0];
					JLabel pricematchLinkLabels[] = new JLabel[0];
				JPanel pscSelectionPanel = new JPanel();
					JRadioButton[] pscButtons = new JRadioButton[0];			ButtonGroup pscButtonGroup = new ButtonGroup();
			JPanel bottomPanel = new JPanel();
				JLabel subtotalLabel = new JLabel( "" );
				JLabel gpLabel = new JLabel( "" );
			
	ScanDialog scanDialog = new ScanDialog();
	
	
	
	Font titleFont = new Font( "Tahoma" , Font.PLAIN , 14 );
	Font detailsFont = new Font( "Tahoma" , Font.PLAIN , 11 );
	Font subtotalGpFont = new Font( "Tahoma" , Font.PLAIN , 12 );
	
	
	public PricematchPanel(){
		
		checkExpired();
		
		build();
		
		
		
		
		
		
		loadProducts();
		
		
		
		
//		ArrayList<Product> products = getLaptopsFromClipboardPluPrice();
//		table.addProducts( products );		
		
//		ArrayList<Product> products = getLaptopsFromClipboard();
//		table.addProducts( products );
		
//		ArrayList<Product> products = getLaptopsFromClipboard_withUPC();//must have UPC on all of them(unless i fix that later)
//		table.addProducts( products );
		
		
		
		
		
		
		
		
		printReport();
		//printStockDisplay();
		
		
		
	}
	public void printReport(){
		
		ArrayList<Product> products = table.getAllProducts();
		for( int i=0; i<products.size(); i++ )
		{
			Product p = products.get( i );
			
			InternetPricematch ip = p.getCheapestPricematch();
			if( ip != null )
			{
				double diff = ip.getPrice() - p.getPrice();
				
				
				if( diff < -20 )
				{
					String diffString = new DecimalFormat( "+$0.00;-$0.00" ).format( diff );
					
					//String pluString = "=" + Q + p.getPlu() + Q;
					//String upcString = "=" + Q + p.getUpc() + Q;
					String pluString = p.getPlu();
					String upcString = p.getUpc();
					String priceString = Product.F.format( p.getPrice() );
					
					
					String line = "";
					line += pluString + T;
					line += upcString + T;
					line += p.getShortDescription() + T;
					line += priceString + T;
					line += diffString + T;
					line += ip.getAbbreviatedCompetitor();
					
					System.out.println( line );
				}
				
					
			}
			
				
			
					
		}
		
	}
	public void printStockDisplay(){
		
		ArrayList<Product> products = table.getAllProducts();
		for( int i=0; i<products.size(); i++ )
		{
			Product p = products.get( i );
			
			String pluString = "=" + Q + p.getPlu() + Q;
			String priceString = Product.F.format( p.getPrice() );
			
			String pscString = "";
			PscGroup psc = p.getPscGroup();
			if( psc != null )
			{
				pscString = psc.toString();
			}
			
			
			String line = "";
			line += pluString + T;
			line += priceString + T;
			line += pscString + T;
			
			System.out.println( line );
			
				
			
					
		}
		
	}
	
	
	
	//build
	public void build(){
		
		buildPasswordPanel();
		
		
		buildLeftPanel();
		buildRightPanel();
		
		sp = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT , leftPanel , rightPanel );
		sp.setResizeWeight( 0 );
		
		
		this.setLayout( new BorderLayout() );
		//this.add( sp , BorderLayout.CENTER );
		this.add( passwordPanel , BorderLayout.CENTER );
		
		
		table.setOpaque( false );
		leftPanel.setOpaque( false );
		passwordPanel.setOpaque( false );
		sp.setOpaque( false );
		buttonPanel.setOpaque( false );
		tablePanel.setOpaque( false );
		rightPanel.setOpaque( false );
		
		System.out.println( new JPanel().getBackground() );
		
		
		Color themeColor = new Color( 130,150,230 );
		set( 1f,1f , themeColor.darker() , 0,0 , themeColor.brighter() );
		set( 1f,1f , themeColor.brighter() , 0,0 , themeColor.darker() );
		
		
		
//		table.setEvenRowColor( new Color( 255,255,255 , 100 ) );
//		table.setOddRowColor(  new Color( 255,255,255 , 50 ) );
//		
//		//table.setSelectionBackground( new Color( 0,0,0 , 100 ) );
//		
//		table.setGridColor( new Color( 0,0,0 , 100 ) );
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public void buildPasswordPanel(){
		
		
		//System.out.println( passwordField.getFont() );
		passwordField.setFont( new Font( "Tahoma" , Font.PLAIN , 16 ) );
		passwordField.addActionListener( new BtnListener() );
		
		passwordField.setBorder( BorderFactory.createLineBorder( Color.black ) );
		
		
		passwordPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1; 		c.weighty = 1;
		c.insets = new Insets( 0 , 0 , 0 , 0 );
		c.gridx = 0; 		c.gridy = 0; 		passwordPanel.add( passwordField , c );
		
		
	}
	
	public void buildLeftPanel(){
		
		buildButtonPanel();
		buildTablePanel();
		
		
		leftPanel.setLayout( new BorderLayout() );
		leftPanel.add( buttonPanel , BorderLayout.NORTH );
		leftPanel.add( tablePanel , BorderLayout.CENTER );
		
		
	}
	public void buildRightPanel(){
		
		buildTopPanel();
		buildMiddlePanel();
		buildBottomPanel();
		
//		rightPanel.setLayout( new BorderLayout() );
//		rightPanel.add( topPanel , BorderLayout.NORTH );
//		rightPanel.add( middlePanel , BorderLayout.CENTER );
//		rightPanel.add( bottomPanel , BorderLayout.SOUTH );
		
		
		topPanel.setOpaque( false );
		middlePanel.setOpaque( false );
		bottomPanel.setOpaque( false );
		pricematchSelectionPanel.setOpaque( false );
		pscSelectionPanel.setOpaque( false );
		
		
		
		rightPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		//c.insets = new Insets( 10 , 10 , 0 , 10 );
		c.weightx = 1; 		c.weighty = 0;
		
		c.gridx = 0; 		c.gridy = 0; 		rightPanel.add( topPanel , c );
		c.gridx = 0; 		c.gridy = 1; 		rightPanel.add( middlePanel , c );
		
		c.weightx = 1; 		c.weighty = 1;
		c.gridx = 0; 		c.gridy = 2; 		rightPanel.add( bottomPanel , c );
		
		
	}
	
	public void buildButtonPanel(){
		
		
		addBtn.addActionListener( new BtnListener() );
		removeBtn.addActionListener( new BtnListener() );
		scanBtn.addActionListener( new BtnListener() );
		searchField.addKeyListener( new SearchListener() );
		lockBtn.addActionListener( new BtnListener() );
		
		addBtn.setOpaque( false );
		removeBtn.setOpaque( false );
		scanBtn.setOpaque( false );
		lockBtn.setOpaque( false );
		
		
		buttonPanel.add( addBtn );
		buttonPanel.add( removeBtn );
		buttonPanel.add( scanBtn );
		buttonPanel.add( searchLabel );
		buttonPanel.add( searchField );
		buttonPanel.add( lockBtn );
		
	}
	public void buildTablePanel(){
		
		
//		tablePanel = new JScrollPane(){
//			
//			public Dimension getPreferredSize(){
//				
//				return productTable.getMyPreferredSize( ProductPanel.this.getWidth() , ProductPanel.this.getHeight() );
//				
//			}
//		
//		
//		};
		
		
		tablePanel.setCorner( JScrollPane.UPPER_RIGHT_CORNER , MyTable.createCornerComponent( table ) );
		tablePanel.setViewportView( table );
		tablePanel.getViewport().setOpaque( false );
		tablePanel.setOpaque( false );
		
		//tablePanel.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE );
		
		
		//table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		table.setFillsViewportHeight( false );
		//productTable.setFillsViewportWidth( true );
		
		//productTable.getTableHeader().addMouseListener( new ColumnPopupListener( productTable ) );
		
		
		
		table.setOpaque( false );
		table.addMyTableSelectionListener( new TableSelectionListener() );
		
		table.model.addTableModelListener( new MyTableModelListener() );
		
		
		
		
		
		
		tablePanel.setBorder( BorderFactory.createEmptyBorder() );
		
		
		
		
		
	}
	
	public void buildTopPanel(){
		
		
		
		titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
		titleLabel.setFont( titleFont );
		
		
		
		
		imageLabel.setHorizontalAlignment( SwingConstants.CENTER );
		
		
		
		
		
		
		
		
		topPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1; 		c.weighty = 1;
		c.gridwidth = 2; 	c.gridheight = 1;
		c.insets = new Insets( 8 , 0 , 8 , 0 );
		c.gridx = 0; 		c.gridy = 0; 		topPanel.add( titleLabel , c );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets( 0 , 0 , 0 , 0 );
		c.weightx = 1; 		c.weighty = 1;
		c.gridwidth = 1; 	c.gridheight = 4;
		c.gridx = 0; 		c.gridy = 1; 		topPanel.add( imageLabel , c );
		
//		c.fill = GridBagConstraints.HORIZONTAL;
//		//c.anchor = GridBagConstraints.PAGE_START;
//		c.weightx = 1; 		c.weighty = 0;
//		c.gridwidth = 1; 	c.gridheight = 1;
//		c.insets = new Insets( 0 , 10 , 10 , 10 );
//		c.gridx = 1; c.gridy = 1; topPanel.add( cpuLabel , c );
//		c.gridx = 1; c.gridy = 2; topPanel.add( ramLabel , c );
//		c.gridx = 1; c.gridy = 3; topPanel.add( screenLabel , c );
//		c.gridx = 1; c.gridy = 4; topPanel.add( new JPanel() , c );
		
	}
	public void buildMiddlePanel(){
		
		
		
		pricematchSelectionPanel.setLayout( new BoxLayout( pricematchSelectionPanel , BoxLayout.Y_AXIS ) );
		pscSelectionPanel.setLayout( new BoxLayout( pscSelectionPanel , BoxLayout.Y_AXIS ) );
		
		
		
		
		middlePanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets( 10 , 10 , 0 , 10 );
		c.weightx = 1; 		c.weighty = 1;
		c.gridx = 0; 		c.gridy = 0; 		middlePanel.add( pricematchSelectionPanel , c );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets( 10 , 0 , 0 , 10 );
		c.weightx = 1; 		c.weighty = 1;
		c.gridx = 1; 		c.gridy = 0; 		middlePanel.add( pscSelectionPanel , c );
		
		
		
		
		
		
	}
	public void buildBottomPanel(){
		
		
		subtotalLabel.setHorizontalAlignment( SwingConstants.LEFT );
		gpLabel.setHorizontalAlignment( SwingConstants.LEFT );
		
		subtotalLabel.setFont( subtotalGpFont );
		gpLabel.setFont( subtotalGpFont );
		
		
		bottomPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets( 20 , 20 , 0 , 10 );
		c.weightx = 1; 		c.weighty = 1;
		c.gridx = 0; 		c.gridy = 0; 		bottomPanel.add( subtotalLabel , c );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets( 10 , 20 , 10 , 10 );
		c.weightx = 1; 		c.weighty = 1;
		c.gridx = 0; 		c.gridy = 1; 		bottomPanel.add( gpLabel , c );
		
		
	}
	
	
	
	
	
	
	//set right panel
	public void setRightPanel( Product product ){
		
		
		if( product == null )
		{
			titleLabel.setText( "" );
			cpuLabel.setText( "" );
			ramLabel.setText( "" );
			screenLabel.setText( "" );
			
			imageLabel.setIcon( null );
			
			setPricematchSelectionPanel( null );
			
			
			return;
			
		}
		
		
		if( product.getShortDescription() == null )
		{
			titleLabel.setText( " " );
			//titleLabel.setBorder( null );
		}
		else 
		{ 	
			titleLabel.setText( product.getShortDescription() );
			//titleLabel.setBorder( BorderFactory.createMatteBorder( 0 , 0 , 1 , 0 , new Color(128,128,128) ) );
		}
		//if( product.getProcessor() == null )			cpuLabel.setText( "" );			else cpuLabel.setText( "CPU: " + product.getProcessor() );
		//if( product.getRam() == null )				ramLabel.setText( "" );			else ramLabel.setText( "RAM: " + product.getRam() );
		//if( product.getScreenSize() == null )		screenLabel.setText( "" );		else screenLabel.setText( "Screen: " + product.getScreenSize() );
		
		if( product.getPlu() != null && !product.getPlu().trim().isEmpty() )
		{
			setImage( product );
			//imageLabel.setBorder( BorderFactory.createLineBorder( new Color( 128,128,128 ) ) );
		}
		else
		{
			imageLabel.setIcon( null );
			imageLabel.setBorder( null );
		}
		
		
		setPricematchSelectionPanel( product );
		setPscSelectionPanel( product );
		setBottomPanel( product );
		
	}
	public void setImage( Product product ){
		
		
		
		
		
//		try
//		{
//			
//			String address = "http://images.frys.com/art/product/300x300/" + product.getPlu() + ".01.prod.jpg";
//			//String address = "http://images.frys.com/art/product/box_shots/" + product.getPlu() + ".box.GIF";
//			
//			URL url = new URL( address );
//			BufferedImage image = ImageIO.read( url );
//			image = Methods.makeWhiteTransparent( image , 250 );
//			
//			
//			imageLabel.setIcon( new ImageIcon( image ) );
//			
//		}
//		catch( MalformedURLException e )
//		{
//			e.printStackTrace();
//		}
//		catch( IOException e )
//		{
//			String message = "failed to setImage()";
//			message += N + "plu: " + product.getPlu();
//			System.out.println( message );
//			
//			e.printStackTrace();
//		}
		
		
	}
	
	public void setPricematchSelectionPanel( Product product ){
		
		
		
		
		if( product == null || product.getPlu() == null )
		{
			pricematchSelectionPanel.setBorder( null );
			
			pricematchSelectionPanel.removeAll();
			pricematchSelectionPanel.revalidate();
			pricematchSelectionPanel.repaint();
			return;
		}
		
		
		pricematchSelectionPanel.setBorder( BorderFactory.createTitledBorder( "IP" ) );
		
		pricematchSelectionPanel.removeAll();
		
		
		
		pricematchSelectionPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		
		pricematchButtons = new JRadioButton[ product.getCompetitorPriceCount()+1 ];
		pricematchPriceLables = new JLabel[ product.getCompetitorPriceCount()+1 ];
		pricematchLinkLabels = new JLabel[ product.getCompetitorPriceCount()+1 ];
		
		
		//button
		String buttonText = "No IP";
		pricematchButtons[0] = new JRadioButton( buttonText );
		pricematchButtons[0].setFont( detailsFont );
		pricematchButtons[0].setOpaque( false );
		pricematchButtons[0].addActionListener( new BtnListener() );
		pricematchButtonGroup.add( pricematchButtons[0] );
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0; 		c.weighty = 0;
		c.insets = new Insets( 0 , 0 , 0 , 0 );
		c.gridx = 0; 		c.gridy = 0; 		pricematchSelectionPanel.add( pricematchButtons[0] , c );
		
		
		//price
		String priceText = "";
		if( product.getPrice() != 0 )
		{
			priceText = Product.F.format( product.getPrice() );
		}
		pricematchPriceLables[0] = new JLabel( priceText );
		pricematchPriceLables[0].setFont( detailsFont );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1; 		c.weighty = 0;
		c.insets = new Insets( 0 , 5 , 0 , 0 );
		c.gridx = 1; 		c.gridy = 0; 		pricematchSelectionPanel.add( pricematchPriceLables[0] , c );
		
		
		//link
		pricematchLinkLabels[0] = new JLabel();
		pricematchLinkLabels[0].setHorizontalAlignment( SwingConstants.LEFT );
		pricematchLinkLabels[0].setFont( detailsFont );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1; 		c.weighty = 0;
		c.insets = new Insets( 0 , 5 , 0 , 5 );
		c.gridx = 2; 		c.gridy = 0; 		pricematchSelectionPanel.add( pricematchLinkLabels[0] , c );
		
		if( product.getSelectedIp() == null )
		{
			pricematchButtons[0].setSelected( true );
		}
		
		
		for( int i=0; i<product.getCompetitorPriceCount(); i++ )
		{
			InternetPricematch ip = product.getCompetitorPrice( i );
			
			
			
			//button
			buttonText = ip.getAbbreviatedCompetitor();
			pricematchButtons[i+1] = new JRadioButton( buttonText );
			pricematchButtons[i+1].setFont( detailsFont );
			pricematchButtons[i+1].setOpaque( false );
			pricematchButtons[i+1].addActionListener( new BtnListener() );
			pricematchButtonGroup.add( pricematchButtons[i+1] );
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0; 		c.weighty = 0;
			c.insets = new Insets( 0 , 0 , 0 , 0 );
			c.gridx = 0; 		c.gridy = i+1; 		pricematchSelectionPanel.add( pricematchButtons[i+1] , c );
			
			
			//price
			priceText = ip.getFormattedPrice();
			if( product.getPrice() != 0 )
			{
				double diff = ip.getPrice() - product.getPrice();
				String diffString = new DecimalFormat( "+$0.00;-$0.00" ).format( diff );
				priceText += " " + "(" + diffString + ")";
			}
			pricematchPriceLables[i+1] = new JLabel( priceText );
			pricematchPriceLables[i+1].setFont( detailsFont );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1; 		c.weighty = 0;
			c.insets = new Insets( 0 , 5 , 0 , 0 );
			c.gridx = 1; 		c.gridy = i+1; 		pricematchSelectionPanel.add( pricematchPriceLables[i+1] , c );
			
			
			
			//link
			String url = ip.getUrl();
			String viewLabelText = "link";
			String viewLabelHtml = "<HTML>" + "<FONT color=\"#000099\"><U>" + viewLabelText + "</U></FONT>" + "</HTML>";
			pricematchLinkLabels[i+1] = new JLabel( viewLabelHtml );
			pricematchLinkLabels[i+1].setFont( detailsFont );
			pricematchLinkLabels[i+1].setHorizontalAlignment( SwingConstants.LEFT );
			pricematchLinkLabels[i+1].addMouseListener( new ViewLabelMouseListener( url ) );
			pricematchLinkLabels[i+1].setCursor( new Cursor(Cursor.HAND_CURSOR) );
			pricematchLinkLabels[i+1].setToolTipText( url );
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1; 		c.weighty = 0;
			c.insets = new Insets( 0 , 5 , 0 , 5 );
			c.gridx = 2; 		c.gridy = i+1; 		pricematchSelectionPanel.add( pricematchLinkLabels[i+1] , c );
			
			
			
			if( ip == product.getSelectedIp() )
			{
				pricematchButtons[i+1].setSelected( true );
			}
			
			
		}
		
		pricematchSelectionPanel.revalidate();
		pricematchSelectionPanel.repaint();
		
		
		
		
		
		
		
		
		
	}
	public void setPscSelectionPanel( Product product ){
		
		if( product == null || product.getPlu() == null )
		{
			pscSelectionPanel.setBorder( null );
			
			pscSelectionPanel.removeAll();
			pscSelectionPanel.revalidate();
			pscSelectionPanel.repaint();
			return;
		}
		
		
		pscSelectionPanel.setBorder( BorderFactory.createTitledBorder( "PSC" ) );
		
		
		pscSelectionPanel.removeAll();
		
		PscGroup pscGroup = product.getPscGroup();
		
		if( pscGroup == null )
		{
			pscButtons = new JRadioButton[ 1 ];
			
			pscButtons[0] = new JRadioButton( "No PSC" );
			pscButtons[0].setFont( detailsFont );
			pscButtons[0].setOpaque( false );
			pscButtons[0].addActionListener( new BtnListener() );
			pscButtonGroup.add( pscButtons[0] );
			pscSelectionPanel.add( pscButtons[0] );
			
			if( product.getSelectedPsc() == null )
			{
				pscButtons[0].setSelected( true );
			}
			
		}
		else
		{
			
			pscButtons = new JRadioButton[ pscGroup.getCount() + 1 ];
			
			pscButtons[0] = new JRadioButton( "No PSC" );
			pscButtons[0].setFont( detailsFont );
			pscButtons[0].setOpaque( false );
			pscButtons[0].addActionListener( new BtnListener() );
			pscButtonGroup.add( pscButtons[0] );
			pscSelectionPanel.add( pscButtons[0] );
			
			if( product.getSelectedPsc() == null )
			{
				pscButtons[0].setSelected( true );
			}
			
			
			for( int i=0; i<pscGroup.getCount(); i++ )
			{
				Psc psc = pscGroup.get( i );
				
				pscButtons[i+1] = new JRadioButton( psc.getYears() + "yr: " + Product.F.format( psc.getPrice() ) );
				pscButtons[i+1].setFont( detailsFont );
				pscButtons[i+1].setOpaque( false );
				pscButtons[i+1].addActionListener( new BtnListener() );
				pscButtonGroup.add( pscButtons[i+1] );
				pscSelectionPanel.add( pscButtons[i+1] );
				
				if( product.getSelectedPsc() == psc )
				{
					pscButtons[i+1].setSelected( true );
				}
				
			}
			
			
		}
		
		
		pscSelectionPanel.revalidate();
		pscSelectionPanel.repaint();
		
		
		
	}
	
	public void setBottomPanel( Product product ){
		
		if( product == null || product.getPlu() == null )
		{
			subtotalLabel.setText( "" );
			gpLabel.setText( "" );
			return;
		}
		
		double subtotal = product.getSelectedSubtotal();
		subtotalLabel.setText( "Subtotal: " + Product.F.format( subtotal ) );
		
		double gp = product.getSelectedGp();
		gpLabel.setText( "GP: " + Product.P.format( gp ) );
		if( Double.compare( gp , Double.NaN ) == 0 )
		{
			gpLabel.setText( "GP: " + "?" );
		}
		
		
	}
	
	
	
	
	
	public void lock(){
		
		this.removeAll();
		this.add( passwordPanel , BorderLayout.CENTER );
		passwordField.setText( "" );
		passwordField.requestFocus();
		passwordField.setCaretPosition( 0 );
		
		this.revalidate();
		this.repaint();
		
	}
	public void unlock(){
		
		this.removeAll();
		this.add( sp , BorderLayout.CENTER );
		this.revalidate();
		this.repaint();
		
	}
	
	
	
	
	public void checkExpired(){
		
		GregorianCalendar today = (GregorianCalendar) GregorianCalendar.getInstance();
		
		if( Methods.isLaterThan( today , EXPIRATION_DATE ) )
		{
			String message = "This version is expired.";
			JOptionPane.showMessageDialog( null , message );
			System.exit( 0 );
		}
		
	}
	
	
	
	
	public void saveProducts(){
		
		ArrayList<Product> products = table.getAllProducts();
		System.out.println( "saving " + products.size() + " products" );
		
		String text = Product.getSaveText( products );
		
		
		File textFile = new File( SAVE_PATH );
		
		try
		{
			Methods.writeTextFile( textFile , text );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			String message = "failed to save";
			JOptionPane.showMessageDialog( null , message );
		}
		
	}
	public void loadProducts(){
		
		File textFile = new File( SAVE_PATH );
		if( !textFile.exists() )
		{
			return;
		}
		
		try
		{
			String saveText = Methods.readTextFile( textFile );
			ArrayList<Product> products = Product.loadProducts( saveText );
			table.addProducts( products );
			System.out.println( "loaded " + products.size() + " products" );
		}
		catch ( IOException e )
		{
			
			e.printStackTrace();
			String message = "failed to load";
			JOptionPane.showMessageDialog( null , message );
			
			return;
		}
		
		
	}
	
	public void removeEmptyLines(){
		
		ArrayList<Product> products = table.getAllProducts();
		for( int i=0; i<products.size(); i++ )
		{
			Product p = products.get( i );
			if( p.getPlu() == null || p.getPlu().isEmpty() )
			{
				table.model.removeRow( p );
			}
		}
		
	}
	
	
	
	//button methods
	public void passwordEntered(){
		
		char[] passwordChar = passwordField.getPassword();
		String password = String.valueOf( passwordChar );
		//System.out.println( "password entered: " + password );
		
		if( password.equals( PASSWORD ) )
		{
			unlock();
		}
		
	}
	
	public void addPushed(){
		
		checkExpired();
		
		Product product = new Product();
		table.addProduct( product );
		
				
		
		int displayedR = table.model.getDisplayedIndexOf( product );
		int displayedC = table.model.getDisplayedC( 0 );
				
		table.clearSelection();
		table.setSelected( product , true );
		
		table.editCellAt( displayedR , displayedC );
		table.transferFocus();
		table.scrollRectToVisible( new Rectangle( table.getCellRect( displayedR , 0 , true ) ) );
		
		
		
	}
	public void removePushed(){
		
		table.removeSelectedProduct();
		
	}
	public void scanPushed(){
		
		checkExpired();
		
		removeEmptyLines();
		
		Thread thread = new Thread(){
			
			public void run(){
				
				scan();
				
			}
			
		};
		thread.start();
		
		
	}
	public void lockPushed(){
		
		lock();
		
	}
	
	private void scan(){
		
		
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start();
		
		scanDialog.clear();
		
		//scanDialog.setModal( true );
		scanDialog.setVisible( true );
		scanDialog.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
		
		
		ArrayList<Product> products = table.getAllProducts();
		
		
		scanDialog.titleLabel.setText( "Searching frys.com for product information" );
		frysInfoAll( products );
		
		scanDialog.titleLabel.setText( "Searching for all competitor prices" );
		scanAllCompetitors( products );
		
		
		double timeInSeconds = stopwatch.lap();
		
		String text = "Finished in " + Stopwatch.convertSecondsToHMS( timeInSeconds );System.out.println( text );
		scanDialog.titleLabel.setText( text );
		scanDialog.setCursor( Cursor.getDefaultCursor() );
		
		
		
		
		Product product = table.getSelectedProduct();
		selectedProduct( product );
		
		
	}
	public void frysInfoAll( ArrayList<Product> products ){
		
		
		
		scanDialog.bar.setMaximum( products.size() );
		scanDialog.bar.setValue( 0 );
		
		
		
		for( int i=0; i<products.size(); i++ )
		{
			
			
			Product product = products.get( i );
			
			if( !table.isProductHidden( product ) )
			{
				table.clearSelection();
				table.setSelected( product , true );
			}
			
			
			scanDialog.append( "Searching frys.com for " + product.getPlu() + "..." );
			
			
			try
			{
				product.frysInfo();
				
				scanDialog.append( " found" + N );
				scanDialog.append( T + "UPC: " + product.getUpc() + N );
				scanDialog.append( T + "Description: " + product.getShortDescription() + N );
				//scanDialog.append( T + "Processor: " + laptop.getProcessor() + N );
				//scanDialog.append( T + "RAM: " + laptop.getRam() + N );
				//scanDialog.append( T + "Screen: " + laptop.getScreenSize() + N );
				
				scanDialog.bar.setValue( i+1 );
				
			}
			catch ( IOException e )
			{
				JOptionPane.showMessageDialog( null , "Unable to connect to frys.com" );
				return;
			}
			catch ( IllegalArgumentException e )
			{
				JOptionPane.showMessageDialog( null , e.getMessage() );
				
				int displayedR = table.model.getDisplayedIndexOf( product );
				int displayedC = table.model.getDisplayedC( 0 );
						
				
				table.editCellAt( displayedR , displayedC );
				table.transferFocus();
				
				
				return;
			}
		}
		
	}
	public void scanAllCompetitors( ArrayList<Product> products ){
		
		int productsCount = products.size();
		int competitorCount = PricematchFinder.COMPETITOR_NAMES.length;
		
		int count = 0;
		
		scanDialog.bar.setMaximum( productsCount * competitorCount );
		scanDialog.bar.setValue( 0 );
				
		
		for( int i=0; i<productsCount; i++ )
		{
			
			
			Product product = products.get( i );
			table.clearSelection();
			table.setSelected( product , true );
			
			
			
			scanDialog.append( N + "Searching for pricematches for " + product.getPlu() + "..." + N );
			if( product.getUpc() == null )
			{
				scanDialog.append( T + "UPC unknown, cannot search for pricematches" );
				count += competitorCount;
				scanDialog.bar.setValue( count );
			}
			else
			{
				product.clearCompetitorPrices();
				for( int j=0; j<competitorCount; j++ )
				{
	//				if( j == 9 )//Amazon only
					tryCompetitor( product , j );
					
						
					
					count++;
					scanDialog.bar.setValue( count );
				}
			}
			
			
				
			
					
			
			
		}
			
		
	}
	public void tryCompetitor( Product product , int index ){
		
		String competitor = PricematchFinder.COMPETITOR_NAMES[index];
		
		
		
		
		
		String upc = product.getUpc();
		if( upc == null )
		{
			System.out.println( "tryCompetitor(): upc == null, cant scan" );
			return;
		}
		
		
		try
		{
			scanDialog.append( T + competitor + "... " );
			
			InternetPricematch competitorPrice = PricematchFinder.searchCompetitor( index , upc );
			
			if( competitorPrice == null )
			{
				scanDialog.append( "(failed to extract price, returned null)" + N );
			}
			else if( competitorPrice == InternetPricematch.FAILED )
			{
				scanDialog.append( "(failed to extract price)" + N );
			}
			else if( competitorPrice == InternetPricematch.OPEN_BOX )
			{
				scanDialog.append( "(open box)" + N );
			}
			else if( competitorPrice == InternetPricematch.OUT_OF_STOCK )
			{
				scanDialog.append( "(out of stock)" + N );
			}
			else if( competitorPrice == InternetPricematch.NOT_FOUND )
			{
				scanDialog.append( "(not found)" + N );
			}
			else if( competitorPrice == InternetPricematch.SEE_PRICE_IN_CART )
			{
				scanDialog.append( "(see price in cart)" + N );
			}
			else if( competitorPrice == InternetPricematch.THIRD_PARTY )
			{
				scanDialog.append( "(invalid 3rd party retailer)" + N );
			}
			else
			{
				product.addPriceMatch( competitorPrice );
				
				scanDialog.append( competitorPrice.getFormattedPrice() + N );
			}
			
			
				
			
		}
		catch( IOException e )
		{
			String message = "Failed to connect to " + competitor + N + e.getMessage();
			scanDialog.append( message );
			
		}
		
		
	}
	
	public void selectedProduct( Product product ){
		
		
		
		setRightPanel( product );
		
		
		
		
	}
	public void selectedIpButton( int i ){
		
		
		Product selectedProduct = table.getSelectedProduct();
		
		
		
		if( i == 0 )
		{
			InternetPricematch selectedIp = null;
			selectedProduct.setSelectedIp( selectedIp );
		}
		else
		{
			InternetPricematch selectedIp = selectedProduct.getCompetitorPrice( i-1 );
			selectedProduct.setSelectedIp( selectedIp );
		}
		
		setBottomPanel( selectedProduct );
		
		
	}
	public void selectedPscButton( int i ){
		
		
		Product selectedProduct = table.getSelectedProduct();
		
		if( i == 0 )
		{
			Psc selectedPsc = null;
			selectedProduct.setSelectedPsc( selectedPsc );
		}
		else
		{
			Psc selectedPsc = selectedProduct.getPsc( i-1 );
			selectedProduct.setSelectedPsc( selectedPsc );
		}
		
		setBottomPanel( selectedProduct );
		
	}
	
	
	
	private class BtnListener implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ){
		
			if( e.getSource() == addBtn )				addPushed();
			else if( e.getSource() == removeBtn )		removePushed();
			else if( e.getSource() == scanBtn )			scanPushed();
			else if( e.getSource() == passwordField )	passwordEntered();
			else if( e.getSource() == lockBtn )			lockPushed();
			
			else
			{
				for( int i=0; i<pricematchButtons.length; i++ )
				{
					if( e.getSource() == pricematchButtons[i] )
					{
						selectedIpButton( i );
					}
				}
				
				for( int i=0; i<pscButtons.length; i++ )
				{
					if( e.getSource() == pscButtons[i] )
					{
						selectedPscButton( i );
					}
				}
			}
				
			
		 	
		}
		
		
		
		
	}
	private class TableSelectionListener extends MyTableSelectionListener {
		
		
		public void selectionChanged(){
			
			
			//int selectedDisplayedR = this.getNewRows();
			Product product = table.getSelectedProduct();
			selectedProduct( product );
			
			
		}



	}
	private class MyTableModelListener implements TableModelListener {

		@Override
		public void tableChanged( TableModelEvent e ){
		
			if( e.getType() == TableModelEvent.UPDATE )
			{
				Product product = table.getSelectedProduct();
				setRightPanel( product );
			}
			
		}
		
		
		
		
	}
	private class ViewLabelMouseListener implements MouseListener {
		
		
		String url;
		
		public ViewLabelMouseListener( String url ){
			
			this.url = url;
			
		}
		
		
		public void mouseClicked( MouseEvent e ){
			
			
			try	
			{ 
				java.awt.Desktop.getDesktop().browse( new URI( url ) ); 
			}
			catch ( IOException | URISyntaxException ex )
			{ 
				ex.printStackTrace();
				JOptionPane.showMessageDialog( null , "failed to open: " + url );
			}
			
			
			
			
		}
		public void mouseEntered( MouseEvent e ){}
		
		
		
		
		
		
		
		
		public void mouseExited( MouseEvent e ){}
		public void mousePressed( MouseEvent e ){}
		public void mouseReleased( MouseEvent e ){}
		
		
		
		
	}
	private class SearchListener implements KeyListener{

		public void keyTyped( KeyEvent e ) {}
		public void keyPressed( KeyEvent e ) {}
		
		public void keyReleased( KeyEvent e ) {
			
			if ( e.getSource() == searchField )
			{

				table.setSearchText( searchField.getText() );
				
			}
			
			
			
		}
		
		
		
		
	}
	
	
	
	
	public static ArrayList<Product> getLaptopsFromClipboardPluPrice(){
		
		/* 
		
		| 7994630 | $298 |
		
		*/
		
		
		ArrayList<Product> laptops = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( "# of pieces: " + pieces.length );
			//System.out.println( pieces[0] + T + pieces[1] );
			
			//plu
			String plu = pieces[0];
			
			//price
			String priceString = pieces[1].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );		
			
			Laptop laptop = new Laptop( plu , price );
			laptops.add( laptop );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return laptops;
		
	}
	public static ArrayList<Product> getLaptopsFromClipboard(){
		
		/* 
		
		| 7994630 | $298 | "$99.99 / $129.99" |
		
		*/
		
		
		ArrayList<Product> laptops = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			//System.out.println( "# of pieces: " + pieces.length );
			//System.out.println( pieces[0] + T + pieces[1] );
			
			//plu
			String plu = pieces[0];
			
			//price
			String priceString = pieces[1].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			
			//psc
			String pscString = "";
			if( pieces.length == 3 )
			{
				pscString = pieces[2];
			}
			
			
			Laptop laptop = new Laptop( plu , price );
			laptop.setPsc( pscString );
			
			laptops.add( laptop );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return laptops;
		
	}
	public static ArrayList<Product> getLaptopsFromClipboard_withUPC(){
		
		/* 
		
		| 7994630 | $298 | $99.99 / $129.99 | UPC |
		
		*/
		
		
		ArrayList<Product> laptops = new ArrayList<Product>();
		
		String clipboard = Methods.getClipboardString();
		String[] lines = clipboard.split( "\n" );
		for( int i=0; i<lines.length; i++ )
		{
			String line = lines[i];
			String[] pieces = line.split( T );
			
			System.out.println( "line: " + line );
			System.out.println( "# of pieces: " + pieces.length );
			//System.out.println( pieces[0] + T + pieces[1] );
			
			//plu
			String plu = pieces[0];
			
			//price
			String priceString = pieces[1].replace( "$" , "" ).replace( "," , "" );
			double price = Double.parseDouble( priceString );
			
			//psc
			String pscString = pieces[2];
			
			//UPC
			String upc = pieces[3];
			
			
			Laptop laptop = new Laptop( plu , price );
			laptop.setPsc( pscString );
			laptop.setUpc( upc );
			
			laptops.add( laptop );
			
		}
		
		//Methods.printArray( products , "\n\n" , false );
		
		return laptops;
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		
		final PricematchPanel panel = new PricematchPanel();
		
		
		
//		Laptop laptop = new Laptop();
//		laptop.setPlu( "7997990" );
//		laptop.frysInfo();
//		//Blah.checkAllCompetitors( laptop );
//		laptop.addPriceMatch( new CompetitorPrice( "Micro Center" , 249.99 , "url" ) );
//		laptop.addPriceMatch( new CompetitorPrice( "Walmart" , 289.99 , "url" ) );
//		laptop.addPriceMatch( new CompetitorPrice( "Newegg" , 319.99 , "url" ) );
//		laptop.addPriceMatch( new CompetitorPrice( "Staples" , 343.49 , "url" ) );
//		panel.table.addLaptop( laptop );
		
		
		
		//getLaptopsFromClipboard_withUPC();
		
		
		
		
		
		
		final JFrame frame = new JFrame();
		frame.setSize( 1100,650 );	panel.sp.setDividerLocation( 724 );
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.addWindowListener( new WindowListener(){
			
			public void windowClosing( WindowEvent arg0 ){
				
				panel.saveProducts();
				
				System.out.println( "window: " + frame.getSize() );
				System.out.println( "divider location: " + panel.sp.getDividerLocation() );
				
			}
			
			
			
			public void windowOpened( WindowEvent arg0 ){}
			public void windowIconified( WindowEvent arg0 ){}
			public void windowDeiconified( WindowEvent arg0 ){}
			public void windowDeactivated( WindowEvent arg0 ){}
			public void windowClosed( WindowEvent arg0 ){}
			public void windowActivated( WindowEvent arg0 ){}
			
			
		});
		frame.setTitle( "Plu Checker v" + VERSION );
		
		frame.setLayout( new BorderLayout() );
		frame.add( panel , BorderLayout.CENTER );
		frame.setVisible( true );
		
		
		//String url = "http://www.newegg.com/Product/Product.aspx?Item=N82E16826104840&cm_re=097855094476-_-26-104-840-_-Product";
		//try { java.awt.Desktop.getDesktop().browse( new URI( url ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
		
		
	}
	
	
	
	
	
	
}




















