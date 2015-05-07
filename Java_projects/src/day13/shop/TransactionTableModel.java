package day13.shop;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TransactionTableModel extends AbstractTableModel{
	public List <Transaction> register;
	
	public TransactionTableModel(){
		register = new ArrayList<>();
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return register.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
