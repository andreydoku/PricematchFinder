package PluChecker;

import gui.GradientPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import products.Product;

import myLibrary.Methods;



public class PluChecker extends JFrame {
	
	
	
	GradientPanel topPanel = new GradientPanel();
		JLabel titleLabel = new JLabel();
	
	GradientPanel centerPanel = new GradientPanel();
		
		JPanel pluPanel = new JPanel();
			JPanel pluInputPanel = new JPanel();
				JLabel pluLabel = new JLabel( "PLU" );
				JTextField pluField = new JTextField( 7 );
				JButton searchBtn = new JButton( "Search" );
			JPanel descriptionPanel = new JPanel();
				JLabel descriptionLabel = new JLabel();
				
				
		JPanel inputPanel = new JPanel();
			JLabel priceLabel = new JLabel( "Fry's price" );	JTextField priceField = new JTextField( 10 );
			JLabel costLabel = new JLabel( "Cost" );			JTextField costField = new JTextField( 10 );
	
			
	GradientPanel bottomPanel = new GradientPanel();
		JLabel subtotalLabel = new JLabel();
		JLabel gpLabel = new JLabel();
		JPanel placeHolder = new JPanel();
		JButton nextBtn = new JButton( "Next >>" );
		JButton backBtn = new JButton( "<< Back" );
		
	
	Font font = new Font( "Tahoma" , Font.PLAIN , 14 );
		
	private int page = 0;
		
	private Product product;	
		
		
	
	public PluChecker(){
		
		build();
		
		setPage( 0 );
		updateBottomLabels();
		
		/*
		addWindowListener( new WindowListener(){
			
			
			public void windowOpened( WindowEvent arg0 ){}
			public void windowIconified( WindowEvent arg0 ){}
			public void windowDeiconified( WindowEvent arg0 ){}
			public void windowDeactivated( WindowEvent arg0 ){}
			public void windowClosed( WindowEvent arg0 ){}
			public void windowActivated( WindowEvent arg0 ){}
			
			public void windowClosing( WindowEvent arg0 ){
				
				
				System.out.println( PluChecker.this.getSize() );
				
			}
			
			
			
			
		});
		*/
		
	}
	
	public void build(){
		
		buildTopPanel();
		buildCenterPanel();
		buildBottomPanel();
		
		
		this.setLayout( new BorderLayout() );
		this.add( topPanel , BorderLayout.NORTH );
		this.add( centerPanel , BorderLayout.CENTER );
		this.add( bottomPanel , BorderLayout.SOUTH );
		
		
		this.setSize( 600,500 );
		this.setLocationRelativeTo( null );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		
		setFont();
		setColors();
		
	}
	
	public void buildTopPanel(){
		
		topPanel.setBackground( new Color( 200,200,200 ) );
		
		
		titleLabel.setHorizontalAlignment( SwingConstants.LEFT );
		
		topPanel.setLayout( new FlowLayout( FlowLayout.LEFT , 15 , 10 ) );
		topPanel.add( titleLabel );
		
		
	}
	public void buildBottomPanel(){
		
		
		bottomPanel.setBackground( new Color( 200,200,200 ) );
		
		
		
		
		
		
		placeHolder.setOpaque( false );
		
		backBtn.addActionListener( new BtnListener() );
		backBtn.setOpaque( false );
		
		nextBtn.addActionListener( new BtnListener() );
		nextBtn.setOpaque( false );		
		
		bottomPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.insets = new Insets( 10 , 10 , 10 , 30 );
		c.gridx = 0; c.gridy = 0; bottomPanel.add( subtotalLabel , c );
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.insets = new Insets( 10 , 0 , 10 , 10 );
		c.gridx = 1; c.gridy = 0; bottomPanel.add( gpLabel , c );
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 2; c.gridy = 0; bottomPanel.add( placeHolder , c );
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.insets = new Insets( 10 , 0 , 10 , 10 );
		c.gridx = 3; c.gridy = 0; bottomPanel.add( backBtn , c );
		
		c.insets = new Insets( 10 , 0 , 10 , 15 );
		c.gridx = 4; c.gridy = 0; bottomPanel.add( nextBtn , c );
		
		
		
		
	}
	
	public void buildCenterPanel(){
		
		
		buildPluPanel();
		buildInputPanel();
		
		centerPanel.setLayout( new BorderLayout() );
		
		
		
		pluPanel.setOpaque( false );
		pluInputPanel.setOpaque( false );
		descriptionPanel.setOpaque( false );
		inputPanel.setOpaque( false );
		
		
		
		
		
		
		
		//topPanel.set( 0.5f, 0.5f , themeColor.brighter() , 0.5f , 0f , themeColor );
		
		
	}
	public void buildPluPanel(){
		
		//pluPanel.setBackground( Color.white );
		
		
		
		pluField.setHorizontalAlignment( JTextField.CENTER );
		pluField.addActionListener( new BtnListener() );
		searchBtn.addActionListener( new BtnListener() );
		searchBtn.setOpaque( false );
		
		
		pluInputPanel.setLayout( new FlowLayout( FlowLayout.CENTER , 10 , 20 ) );
		pluInputPanel.add( pluLabel );
		pluInputPanel.add( pluField );
		pluInputPanel.add( searchBtn );
		
		
		//descriptionLabel.setWrapStyleWord( true );
		descriptionLabel.setHorizontalAlignment( SwingConstants.CENTER );
		
		//descriptionLabel.setVerticalTextPosition( SwingConstants.TOP );
		//descriptionLabel.setHorizontalTextPosition( SwingConstants.CENTER );
		
		
		descriptionLabel.setBorder( BorderFactory.createEmptyBorder( 30 , 30 , 30 , 30 ) );
		//descriptionLabel.setBackground( Color.white );
		//descriptionLabel.setOpaque( true );
		
		descriptionPanel.setLayout( new BorderLayout() );
		descriptionPanel.add( descriptionLabel , BorderLayout.CENTER );
		
		
		
		pluPanel.setLayout( new BorderLayout() );
		pluPanel.add( pluInputPanel , BorderLayout.NORTH );
		pluPanel.add( descriptionPanel , BorderLayout.CENTER );
		//pluPanel.add( descriptionSp , BorderLayout.CENTER );
		
		
	}
	public void buildInputPanel(){
		
		
		priceField.setHorizontalAlignment( SwingConstants.CENTER );
		costField.setHorizontalAlignment( SwingConstants.CENTER );
		
		
		priceField.addActionListener( new BtnListener() );
		priceField.addFocusListener( new BtnListener() );
		
		costField.addActionListener( new BtnListener() );
		costField.addFocusListener( new BtnListener() );
		
		
		inputPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets( 0 , 0 , 0 , 0 );		c.gridx = 0; c.gridy = 0;	inputPanel.add( priceLabel , c );
		c.insets = new Insets( 0 , 10 , 0 , 0 );	c.gridx = 1; c.gridy = 0;	inputPanel.add( priceField , c );
		
		c.insets = new Insets( 10 , 0 , 0 , 0 );	c.gridx = 0; c.gridy = 1;	inputPanel.add( costLabel , c );
		c.insets = new Insets( 10 , 10 , 0 , 0 );	c.gridx = 1; c.gridy = 1;	inputPanel.add( costField , c );
		
		
	}
	
	
	public void setColors(){
		
		Color themeColor = new Color( 130,150,230 );
		topPanel.set( 0f,0f , themeColor.brighter() , 1f,1f , themeColor/*.darker()*/ );
		bottomPanel.set( 0f,0f , themeColor.brighter() , 1f,1f , themeColor/*.darker()*/ );
		
		
		
		Color centerColor = new Color( 230,230,255 );
		centerPanel.set( 0f,0f , Color.white , 1f,1f , centerColor );
		
		
		
		
		topPanel.setBorder( BorderFactory.createMatteBorder( 0 , 0 , 1 , 0 , Color.gray ) );
		bottomPanel.setBorder( BorderFactory.createMatteBorder( 1 , 0 , 0 , 0 , Color.gray ) );
		
	}
	public void setFont(){
		
		titleLabel.setFont( font );
		
		
		
		
		
		pluLabel.setFont( font );
		pluField.setFont( font );
		searchBtn.setFont( font );
		descriptionLabel.setFont( font );
		
		
		
		priceLabel.setFont( font );
		priceField.setFont( font );
		costLabel.setFont( font );
		costField.setFont( font );
		
		
		
		subtotalLabel.setFont( font );
		gpLabel.setFont( font );
		
		nextBtn.setFont( font );
		backBtn.setFont( font );
		
		
	}
	
	
	
	
	
	
	
	
	
	public void setPage( int page ){
		
		this.page = page;
		
		if( page == 0 )
		{
			titleLabel.setText( "Enter PLU" );
			
			centerPanel.removeAll();
			centerPanel.add( pluPanel , BorderLayout.CENTER );
			centerPanel.revalidate();centerPanel.repaint();
			
			backBtn.setEnabled( false );
			nextBtn.setEnabled( this.product != null );
			
		}
		else if( page == 1 )
		{
			titleLabel.setText( "Enter price and cost (what Frys payed the distributor)" );
			
			centerPanel.removeAll();
			centerPanel.add( inputPanel , BorderLayout.CENTER );
			centerPanel.revalidate();centerPanel.repaint();
			
			backBtn.setEnabled( true );
			nextBtn.setEnabled( false );
			
			updatePriceField();
			updateCostField();
			
		}
			
		
	}
	
	public void updateBottomLabels(){
		
		if( this.product == null )
		{
			subtotalLabel.setText( "" );
			gpLabel.setText( "" );
			return;
		}
		
		
		//total label
		if( product.getPrice() == 0 )
		{
			subtotalLabel.setText( "Total: " + "(need price)" );
		}
		else
		{
			double total = this.product.getPrice();
			String totalString = Product.F.format( total );
			subtotalLabel.setText( "Total: " + totalString );
		}
			
		
		
		//gp label
		if( product.getPrice() == 0 )
		{
			gpLabel.setText( "GP: " + "(need price)" );
		}
		else if( product.getCost() == 0 )
		{
			gpLabel.setText( "GP: " + "(need cost)" );
		}
		else
		{
			double gp = product.getGp();
			
			String gpString = Product.P.format( gp );
			gpLabel.setText( "GP: " + gpString );
		}
			
		
	}
	
	
	//button methods
	public void searchPlu() {
		
		
		String plu = pluField.getText().trim();
		pluField.setText( plu );
		
		
		Product newProduct;
		
		try
		{
			newProduct = Product.getProduct( plu );
		}
		catch ( IllegalArgumentException e )
		{
			String message = "PLU must be a 7 digit number";
			JOptionPane.showMessageDialog( null , message );
			
			pluField.requestFocus();
			pluField.selectAll();
			
			return;
		}
		catch ( IOException e )
		{
			String message = "Failed to connect to frys.com/product/" + plu;
			JOptionPane.showMessageDialog( null , message );
			return;
		}
		
		if( newProduct == null )
		{
			String message = "Invalid PLU";
			JOptionPane.showMessageDialog( null , message );
			
			pluField.requestFocus();
			pluField.selectAll();
			
			return;
		}
		
		
		this.product = newProduct;
		
		System.out.println( newProduct.getPlu() );
		
		try
		{
			//String address = "http://images.frys.com/art/product/300x300/7997990.01.prod.jpg";
			//String address = "http://images.frys.com/art/product/box_shots/7997990.box.GIF";
			
			String address = "http://images.frys.com/art/product/300x300/" + product.getPlu() + ".01.prod.jpg";
			//String address = "http://images.frys.com/art/product/box_shots/" + product.getPlu() + ".box.GIF";
			
			//address = "http://images.frys.com/art/product/box_shots/7824429.box.GIF";
			//try { java.awt.Desktop.getDesktop().browse( new URI( address ) ); }		catch ( IOException | URISyntaxException e ){ e.printStackTrace(); }
			
			URL url = new URL( address );
			BufferedImage image = ImageIO.read( url );
			image = Methods.makeWhiteTransparent( image , 250 );
			
			descriptionLabel.setIcon( new ImageIcon( image ) );
		}
		catch( MalformedURLException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		
		
		descriptionLabel.setText( "<html>" + product.getDescription() + "</html>" );
		//descriptionLabel.setText( product.getDescription() );
		
		updateBottomLabels();
		nextBtn.setEnabled( true );
		nextBtn.requestFocus();
		
		
		
		
		
		
	}
	public void searchPressed(){
		
		setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
		pluField.setEditable( false );
		searchBtn.setEnabled( false );
		
		Thread thread = new Thread(){
			
			public void run(){
				
				searchPlu();
				
				pluField.setEditable( true );
				searchBtn.setEnabled( true );
				setCursor( Cursor.getDefaultCursor() );
			}
			
		};
		thread.start();
		
		
		
		
		//searchPlu();
		
	}
	
	public void backPressed(){
		
		setPage( this.page-1  );
		
	}
	public void nextPressed(){
		
		setPage( this.page+1  );
		
	}
	
	
	public void updatePriceField(){
		
		double price = this.product.getPrice();
		
				
		if( price == 0 )
		{
			priceField.setText( "?" );
		}
		else
		{
			priceField.setText( Product.F.format( price ) );
		}
		
		
	}
	public void updateCostField(){
		
		double cost = this.product.getCost();
		
		if( cost == 0 )
		{
			costField.setText( "?" );
		}
		else
		{
			costField.setText( Product.F.format( cost ) );
		}
		
	}
	
	
	public double getPriceInput(){
		
		
		String input = priceField.getText();
		input = input.replace( "$" , "" ).replace( "," , "" ).trim();
		
		if( input.equals( "" ) || input.equals( "?" ) )
		{
			return 0;
		}
		
		try
		{
			return Double.parseDouble( input );
		}
		catch( NumberFormatException nfe )
		{
			return -1;
		}
		
		
	}
	public double getCostInput(){
		
		
		String input = costField.getText();
		input = input.replace( "$" , "" ).replace( "," , "" ).trim();
		
		if( input.equals( "" ) || input.equals( "?" ) )
		{
			return 0;
		}
		
		try
		{
			return Double.parseDouble( input );
		}
		catch( NumberFormatException nfe )
		{
			return -1;
		}
		
		
	}
	
	
	public void priceChanged(){
		
		
		double inputPrice = getPriceInput();
		if( inputPrice < 0 )
		{
			updatePriceField();
			
		}
		else
		{
			this.product.setPrice( inputPrice );
			updatePriceField();
			
			updateBottomLabels();
			
			costField.requestFocus();
			costField.selectAll();
		}
		
		
		
		
	}
	public void costChanged(){
		
		double inputCost = getCostInput();
		if( inputCost < 0 )
		{
			updateCostField();
			
			
		}
		else
		{
			this.product.setCost( inputCost );
			updateCostField();
			
			updateBottomLabels();
			
			//nextBtn.requestFocus();
			
		}
		
	}
	
	
	private class BtnListener implements ActionListener, FocusListener {

		
		public void actionPerformed( ActionEvent e ){
		
			if( e.getSource() == pluField || e.getSource() == searchBtn )
			{
				searchPressed();
			}
			else if( e.getSource() == backBtn )
			{
				backPressed();
			}
			else if( e.getSource() == nextBtn )
			{
				nextPressed();
			}
			else if( e.getSource() == priceField )
			{
				priceChanged();
			}
			else if( e.getSource() == costField )
			{
				costChanged();
			}
			
		}

		
		public void focusGained( FocusEvent e ){
			
			if( e.getSource() == priceField )
			{
				priceField.selectAll();
			}
			else if( e.getSource() == costField )
			{
				costField.selectAll();
			}
			
		}

		public void focusLost( FocusEvent e ){
			
			if( e.getSource() == priceField )
			{
				//updatePriceField();
				priceChanged();
			}
			else if( e.getSource() == costField )
			{
				//updateCostField();
				costChanged();
			}
			
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		
		PluChecker pluChecker = new PluChecker();
		pluChecker.setVisible( true );
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}









































