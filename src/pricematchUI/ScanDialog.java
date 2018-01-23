package pricematchUI;


import java.awt.*;

import javax.swing.*;


public class ScanDialog extends JFrame {
	
	
	JPanel topPanel = new JPanel();
		JLabel titleLabel = new JLabel( "Scanning frys.com for product information..." );
		JProgressBar bar = new JProgressBar();
		
	JPanel centerPanel = new JPanel();
		JScrollPane sp = new JScrollPane();
			JTextArea area = new JTextArea();
	
	int b = 15;		
			
			
	public ScanDialog(){
		
		build();
		
		
	}
	public void build(){
		
		titleLabel.setFont( new Font( "Tahoma" , Font.PLAIN , 14 ) );
		//System.out.println( titleLabel.getFont() );
		
		//System.out.println( area.getFont() );
		area.setFont( new Font( "Monospaced" , Font.PLAIN , 11 ) );
		
		buildTopPanel();
		buildCenterPanel();
		
		
		this.setLayout( new BorderLayout() );
		this.add( topPanel , BorderLayout.NORTH );
		this.add( centerPanel , BorderLayout.CENTER );
		
		
		this.setSize( 500,500 );
		this.setLocationRelativeTo( null );
		this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		
		
	}
	public void buildTopPanel(){
		
		
		bar.setPreferredSize( new Dimension( 100 , 26 ) );
		
		
		
		topPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets( b , b , 0 , b );
		c.gridx = 0; c.gridy = 0; topPanel.add( titleLabel , c );
		
		c.insets = new Insets( b , b , 0 , b );
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0; c.gridy = 1; topPanel.add( bar , c );
		
	}
	public void buildCenterPanel(){
		
		
		centerPanel.setBorder( BorderFactory.createEmptyBorder( b , b , b , b ) );
		area.setEditable( false );
		area.setBorder( BorderFactory.createLineBorder( Color.black ) );
		
		
		
		sp.setViewportView( area );
		
		centerPanel.setLayout( new BorderLayout() );
		centerPanel.add( sp , BorderLayout.CENTER );
		
		
	}
	
	
	
	public void append( String string ){
		
		area.append( string );
		area.setCaretPosition( area.getDocument().getLength() );
		
	}
	public void clear(){
		
		area.setText( "" );
		
	}
	
	
	
	public static void main(String[] args) {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		
		
		
		ScanDialog sd = new ScanDialog();
		
		
		String string = "<html> <font color=\"red\"> Hi </font></html>";
		//string = "blah";
		sd.append( string );
		
		
		sd.setVisible( true );
		
		
		
	}
	
	
	
	
}


























