package pricematchUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import RowFilters.SearchFilter;

import products.*;

import table.MyTable;
import table.MyTableModel;
import table.MyTable.StringRenderer;




public class ProductTable extends MyTable {
	
	
	Product dummy = Product.getDummy();
	public MyTableModel model = new MyTableModel( dummy );
	
	
	
	
	
	
	
	
	public ProductTable(){
		
		this.setModel( model );
		this.updateColumnHeaders();
		//this.setRowHeight( 16 );
		
		
		
		
		
		//68 , 78 , 124 , 279 , 105
		this.lockDisplayedColumnWidth( 0 , 70 );//plu
		this.lockDisplayedColumnWidth( 1 , 80 );//price
		this.lockDisplayedColumnWidth( 2 , 125 );//psc
		this.lockDisplayedColumnWidth( 3 , 50 );//psc
		this.getColumnModel().getColumn( 4 ).setPreferredWidth( 100 );//description, no lock
		this.lockDisplayedColumnWidth( 5 , 110 );//upc
		this.lockDisplayedColumnWidth( 6 , 65 );//IPs > 30
		
		
		
		
		this.setHeaderHeight( 26 );
		this.getTableHeader().setOpaque( false );
		
		
		this.setRowHeight( 24 );//default = 16
		
		
		this.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.setFocusColor( new Color(0,0,0,0) );
		
		
		
		
		
		this.model.addTableModelListener( new TableModelListener(){
			
			public void tableChanged( TableModelEvent e ){
				
				
//				int displayedC = e.getFirstRow();
//				int actualC = model.getActualC( displayedC );
//				
//				int type = e.getType();
//				
//				if( type == TableModelEvent.UPDATE  &&  actualC == 0 )//plu was edited
//				{
//					LaptopTable.this.repaint();
//				}
//				
				int type = e.getType();
				if( type == TableModelEvent.UPDATE )//plu was edited
				{
					ProductTable.this.repaint();
				}
				
				
				
			}
			
			
		});
		
		
		this.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT ).put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER , 0 ) , "Enter" );
		this.getActionMap().put( "Enter" , new AbstractAction() {
	        
			@Override
	        public void actionPerformed(ActionEvent ae) {
				
	            //do something on JTable enter pressed
				if( isEditing() )
				{
					ProductTable.this.editingStopped( null );
				}
				
	        }
			
			
	    });
		
		model.setRowFilter( new SearchFilter() );
		
		
	}
	
	
	
	public int getActualIndexOf( String plu ){
		
		ArrayList<Product> products = getAllProducts();
		for( int i=0; i<products.size(); i++ )
		{
			Product p = products.get( i );
			
			if( p.getPlu().equals( plu ) )
			{
				return i;
			}
			
			
		}
		
		return -1;
		
	}
	public boolean isProductHidden( Product product ){
		
		return model.isRowDataHidden( product );
		
	}
	
	public void addProduct( Product product ){
		
		model.addRow( product );
		
	}
	public void addProducts( ArrayList<Product> products ){
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			addProduct( product );
		}
		
		
	}
	
	
	public void removeSelectedProduct(){
		
		//int displayedR = this.getSelectedRow();
		//int actualR = model.getActualR( displayedR );
		//model.removeActualRow( actualR );
		
		Product selectedProduct = getSelectedProduct();
		model.removeRow( selectedProduct );
		
	}
	
	public Product getSelectedProduct(){
		
		int displayedR = this.getSelectedRow();
		if( displayedR != -1 )
		{
			return (Product) this.model.getDisplayedRowData( displayedR );
		}
		else
		{
			return null;
		}
		
		
	}
	
	
	public ArrayList<Product> getAllProducts(){
		
		ArrayList<Product> products = new ArrayList<Product>();
		
		for( int actualR=0; actualR<model.getActualRowCount(); actualR++ )
		{
			products.add( (Product)model.getActualRowData( actualR ) );
		}
		
		return products;
		
	}
	
	
	
	public void setSearchText( String text ){

		SearchFilter filter = (SearchFilter) model.getRowFilter();
		filter.setSearchText( text );
		model.applyFilter();


    }
	
	
	
	
	
	public TableCellRenderer getCellRenderer( int displayedR , int displayedC ) {
		
		
		int actualC = model.getActualC( displayedC );
		
		//StringRenderer stringRenderer = new StringRenderer( SwingConstants.CENTER , "????" /*, new Color( 250,0,0 ) , new Color( 150,0,0 )*/ );
		StringRenderer stringRenderer = new StringRenderer();
		
		
		NumberRenderer gpRenderer = new NumberRenderer(){
			
			public void setValue( double value )
			{
				
				super.setValue( value*100 );
				
				if( Double.compare( value , Double.NaN ) == 0 )
				{
					setText( "" );
				}
				
				
			}
			
			
		};
		
		
		if( actualC == 0 )			return super.getCellRenderer( displayedR , displayedC );//PLU
		else if( actualC == 1 )		return new NumberRenderer( SwingConstants.CENTER , "$#,##0.00" );//price
		else if( actualC == 2 )		return stringRenderer;//PSC
		else if( actualC == 3 )		return gpRenderer;//GP
		else if( actualC == 4 )		return stringRenderer;//description
		else if( actualC == 5 )		return stringRenderer;//upc
		else						return super.getCellRenderer( displayedR , displayedC );
		
			
		
	}
	public TableCellEditor getCellEditor( int displayedR , int displayedC ){
		
		
		int actualC = model.getActualC( displayedC );
		
		
		StringEditor pluEditor = new StringEditor();
		pluEditor.setDoubleClickToEdit( true );
		pluEditor.setSelectAllOnEdit( true );
		
		DoubleEditor priceEditor = new DoubleEditor( "$#,##0.00" );
		priceEditor.setDoubleClickToEdit( true );
		priceEditor.setSelectAllOnEdit( true );
		
		ComboBoxEditor pscEditor =  new ComboBoxEditor( PscGroup.LAPTOP_PSCS );
		pscEditor.setDoubleClickToEdit( true );
		
		
		
		String gpOptions[] = new String[102];
		gpOptions[0] = "";
		for( int i=0; i<=100; i++ )
		{
			gpOptions[i+1] = String.valueOf( i );
		}
		
		ComboBoxEditor gpEditor =  new ComboBoxEditor( gpOptions );
		gpEditor.setDoubleClickToEdit( true );
		
		
		
		
		if( actualC == 0 )			return pluEditor;//PLU
		else if( actualC == 1 )		return priceEditor;//price
		else if( actualC == 2 )		return pscEditor;//PSC
		else if( actualC == 3 )		return gpEditor;//GP
		else if( actualC == 4 )		return super.getCellEditor( displayedR , displayedC );//description
		else if( actualC == 5 )		return super.getCellEditor( displayedR , displayedC );//upc
		else						return super.getCellEditor( displayedR , displayedC );
		
		
		
		
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		
		Laptop laptop = new Laptop( "8004764" , 398 );
		laptop.frysInfo();
		
		Laptop laptop2 = new Laptop( "7997990" , 298 );
		laptop2.frysInfo();
		
		final ProductTable table = new ProductTable();
		table.addProduct( laptop );
		table.addProduct( laptop2 );
		
		
        
		
        
		
        JFrame frame = new JFrame();
		
		JScrollPane sp = new JScrollPane( table );
		
		frame.addWindowListener( new WindowListener(){
			
			
			public void windowClosing( WindowEvent arg0 ){
				
				//table.printColumnWidths();
				
				//Laptop laptop = (Laptop) table.model.getActualRowData( 0 );
				//System.out.println( laptop.getPsc2() );
				
				
			}
			
			
			public void windowActivated( WindowEvent arg0 ){}
			public void windowClosed( WindowEvent arg0 ){}
			public void windowDeactivated( WindowEvent arg0 ){}
			public void windowDeiconified( WindowEvent arg0 ){}
			public void windowIconified( WindowEvent arg0 ){}
			public void windowOpened( WindowEvent arg0 ){}
			
			
			
		});
		
        frame.add( sp , BorderLayout.CENTER );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize( 800,600 );
		frame.setLocationRelativeTo( null );
        frame.setVisible( true );

        


    }
	
	
	
	
	
	
	
}





































