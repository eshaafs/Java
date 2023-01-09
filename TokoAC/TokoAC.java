package com.exam;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;


public class TokoAC extends JFrame implements ActionListener, MouseListener, ItemListener, ChangeListener {
	// Singleton Object Connection and ResultSet Object
	private Connect conn;
	private ResultSet rs = null;
	
	// Table
	private Vector<Vector<String>> dataTable = new Vector<Vector<String>>();
	private Vector<String> headerTable = new Vector<String>();
	private DefaultTableModel dtm = new DefaultTableModel(dataTable, headerTable);
	private JTable table = new JTable();
	private JLabel lbl_transactionHistory = new JLabel("Transaction History", JLabel.CENTER);
	private JScrollPane scrollPane = new JScrollPane();
	
	// Form
	private JLabel lbl_namaPembeli, lbl_jenisAC, lbl_merk, lbl_pk, lbl_qty, lbl_totalHarga;
	private JTextField tf_namaPembeli;
	private JFormattedTextField tf_totalHarga;
	private JSpinner js_qty;
	private JComboBox<String> cb_jenisAC, cb_merk;
	private JComboBox<Float> cb_pk;
	private Vector<Float> v_pk;
	private Vector<String> v_jenisAC, v_merk;
	private JPanel panelForm = new JPanel();	
	private JButton btn_create, btn_update, btn_delete, btn_deleteAll, btn_clear;
	private GridBagConstraints c = new GridBagConstraints();
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public TokoAC() {
		setTitle("Cold Fusion Shop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout());
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridx = 0;
		
		/* *****************************************************************
		 * Left Side: History Transaction
		***************************************************************** */
		table.addMouseListener(this);
		JPanel panelHistory = new JPanel(new BorderLayout(0, 20));
		panelHistory.setPreferredSize(new Dimension(650, 390));
		lbl_transactionHistory.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelHistory.add(lbl_transactionHistory, BorderLayout.NORTH);
		refreshData();
		scrollPane.setViewportView(table);
		JPanel panelDelete = new JPanel();
		panelDelete.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		btn_delete = new JButton("Delete");
		btn_delete.addActionListener(this);
		btn_deleteAll = new JButton("Delete All");
		btn_deleteAll.addActionListener(this);
		panelDelete.add(btn_delete);
		panelDelete.add(btn_deleteAll);
		panelHistory.add(scrollPane, BorderLayout.CENTER);
		panelHistory.add(panelDelete, BorderLayout.SOUTH);
		add(panelHistory, gbc);
		
		
		/* *****************************************************************
		 * Right Side: Manage Transaction
		***************************************************************** */
		
		panelForm.setLayout(new GridBagLayout());
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.WEST;
		
		/* Nama Pembeli
		 * ================================================================
		 */
		c.gridx = 0;
		c.gridy = 0;
		
		lbl_namaPembeli = new JLabel("Nama Pembeli", JLabel.LEFT);
		panelForm.add(lbl_namaPembeli, c);
		
		tf_namaPembeli = new JTextField(20);
		tf_namaPembeli.setPreferredSize(new Dimension(225, 25));
		c.gridx = 1;
		panelForm.add(tf_namaPembeli, c);
		
		
		/* Jenis AC
		 * ================================================================
		 */
		lbl_jenisAC = new JLabel("Jenis AC");
		c.gridx = 0;
		c.gridy = 1;
		panelForm.add(lbl_jenisAC, c);
		
		v_jenisAC = new Vector<String>();
		v_jenisAC.add("Split Wall");
		v_jenisAC.add("Standing Floor");
		cb_jenisAC = new JComboBox<String>(v_jenisAC);
		cb_jenisAC.setSelectedIndex(-1);
		cb_jenisAC.setPreferredSize(new Dimension(225, 25));
		c.gridx = 1;
		panelForm.add(cb_jenisAC, c);
		
		
		/* Merk
		 * ================================================================
		 */
		lbl_merk = new JLabel("Merk");
		c.gridx = 0;
		c.gridy = 2;
		panelForm.add(lbl_merk, c);
		
		v_merk = new Vector<String>();
		v_merk.add("Sharp");
		v_merk.add("TCL");
		v_merk.add("Samsung");
		cb_merk = new JComboBox<String>(v_merk);
		cb_merk.setSelectedIndex(-1);
		cb_merk.setPreferredSize(new Dimension(225, 25));
		c.gridx = 1;
		panelForm.add(cb_merk, c);
		
		
		/* PK 
		 * ================================================================
		 */
		lbl_pk = new JLabel("PK");
		c.gridx = 0;
		c.gridy = 3;
		panelForm.add(lbl_pk, c);
		
		v_pk = new Vector<Float>();
		v_pk.add((float)0.5);
		v_pk.add((float)1);
		v_pk.add((float)1.5);
		v_pk.add((float)2);
		cb_pk = new JComboBox<Float>(v_pk);
		cb_pk.addItemListener(this);
		cb_pk.setSelectedIndex(-1);
		cb_pk.setPreferredSize(new Dimension(225, 25));
		c.gridx = 1;
		panelForm.add(cb_pk, c);
		
		
		/* Qty
		 * ================================================================
		 */
		
		lbl_qty = new JLabel("Qty");
		c.gridx = 0;
		c.gridy = 4;
		panelForm.add(lbl_qty, c);

		SpinnerModel sm_qty = new SpinnerNumberModel(1, 1, 10, 1);
		sm_qty.setValue(0);
		js_qty = new JSpinner(sm_qty);
		js_qty.addChangeListener(this);
		js_qty.setPreferredSize(new Dimension(225, 25));
		c.gridx = 1;
		panelForm.add(js_qty, c);
		
		
		/* Total Harga
		 * ================================================================
		 */
		lbl_totalHarga = new JLabel("Total Harga");
		c.gridx = 0;
		c.gridy = 5;
		panelForm.add(lbl_totalHarga, c);
		
		// Only Accept Integer
		NumberFormat format = NumberFormat.getIntegerInstance();
	    NumberFormatter formatter = new NumberFormatter(format){
	        @Override
	        public Object stringToValue(String text) throws ParseException {
	            if (text.length() == 0)
	                return null;
	            return super.stringToValue(text);
	        }
	    };
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(-1);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
		tf_totalHarga = new JFormattedTextField(formatter);
		tf_totalHarga.setPreferredSize(new Dimension(225, 25));
		c.gridx = 1;
		panelForm.add(tf_totalHarga, c);
		
		
		/* Panel Submit Button
		 * ================================================================
		 */
		JPanel panelSubmit = new JPanel();
		panelSubmit.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		btn_create = new JButton("Create");
		btn_create.addActionListener(this);
		btn_update = new JButton("Update");
		btn_update.addActionListener(this);
		btn_clear = new JButton("Clear");
		btn_clear.addActionListener(this);
		panelSubmit.add(btn_create);
		panelSubmit.add(btn_update);
		panelSubmit.add(btn_clear);
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		panelForm.add(panelSubmit, c);
		
		
		/* Panel Manage Transaction
		 * ================================================================
		 */
		JPanel panelManageTransaction = new JPanel(new BorderLayout(0, 10));
		panelManageTransaction.setPreferredSize(new Dimension(450, 400));
		JLabel lbl_manageTransaction = new JLabel("Manage Transaction", JLabel.CENTER);
		lbl_manageTransaction.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		panelManageTransaction.add(lbl_manageTransaction, BorderLayout.NORTH);
		panelManageTransaction.add(panelForm, BorderLayout.CENTER);
		
		gbc.gridx = 1;
		add(panelManageTransaction, gbc);
		
		
		/* Set Visibility and Frame Size
		 * ================================================================
		 */
		setVisible(true);
		this.pack();
	}
	
	public void refreshData() {
		dtm.fireTableDataChanged();
		dtm.setRowCount(0);
		
		try {
			conn = Connect.getInstance();
		} catch(Exception e) {
			System.err.println(e);
		}
		
		try {
			String selectQuery = "Select * from ac";
			rs = conn.executeQuery(selectQuery);
			
			// Header table
			headerTable.add("ID");
			headerTable.add("Nama Pembeli");
			headerTable.add("Jenis AC");
			headerTable.add("Merk");
			headerTable.add("PK");
			headerTable.add("Qty");
			headerTable.add("Total Harga");
			
			// Get Database Data
			while (rs.next()) 
	        {  
	            Vector<String> dbData = new Vector<String>();
	            String id = rs.getString(1);
	            String namaPembeli = rs.getString(2);
	            String jenisAC = rs.getString(3);
	            String merk = rs.getString(4);
	            String pk = Float.toString(rs.getFloat(5));
	            String qty = Integer.toString(rs.getInt(6));
	            String totalHarga = Integer.toString(rs.getInt(7));
	            
	            dbData.addElement(id);
	            dbData.addElement(namaPembeli);
	            dbData.addElement(jenisAC);
	            dbData.addElement(merk);
	            dbData.addElement(pk);
	            dbData.addElement(qty);
	            dbData.addElement(totalHarga);
	            dataTable.add(dbData);
	        }  
		} catch(Exception e) {
			System.err.println(e);
		} finally {  
            try 
            {  
                conn.getConnection().close();  
                System.out.println("Connection Closed");
            } 
            catch (Exception e) 
            {  
                System.err.println(e);
            }  
        }	
		
		// Populate table
		table.setModel(dtm);
		table.setDefaultEditor(Object.class, null);
	}
	
	private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
	
	private void cleanForm() {
		tf_namaPembeli.setText(null);
		cb_jenisAC.setSelectedIndex(-1);
		cb_merk.setSelectedIndex(-1);
		cb_pk.setSelectedIndex(-1);
		js_qty.setValue(0);
		tf_totalHarga.setText(null);
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			conn = Connect.getInstance();
		} catch(Exception e1) {
			System.err.println(e1);
		}
		
		if(e.getSource().equals(btn_create)) {
			if(tf_namaPembeli.getText().equals("")) {
				showErrorMessage("Nama Pembeli Tidak Boleh Kosong!");
			} else if(cb_jenisAC.getSelectedItem() == null) {
				showErrorMessage("Jenis AC Harus Dipilih!");
			} else if(cb_merk.getSelectedItem() == null) {
				showErrorMessage("Merk Harus Dipilih!");
			} else if(cb_pk.getSelectedItem() == null) {
				showErrorMessage("Ukuran PK Harus Dipilih!");
			} else if((int)js_qty.getValue() == 0 || (int)js_qty.getValue() > 10) {
				showErrorMessage("Qty Harus Diisi 1-10!");
			} else if(tf_totalHarga.getValue() == null || (int) tf_totalHarga.getValue() == 0) {
				showErrorMessage("Total Harga Harus Diisi!");
			} else {
				PreparedStatement stmtUpdateID = null, stmtGetID = null;
				try {
					stmtGetID = conn.getConnection().prepareStatement("select counter from id_counter where type=?");
					stmtUpdateID = conn.getConnection().prepareStatement("update id_counter set counter=? where type=?");
				} catch(Exception e2) {
					e2.printStackTrace();
					System.err.println("Failed to get id counter!");
				}
				
				int counter = 0;
				String id = null;
				if(cb_jenisAC.getSelectedIndex() == 0) {
					try {
						stmtGetID.setString(1, "SW");
						rs = stmtGetID.executeQuery();
						rs.next();
						counter = rs.getInt(1);
						id = "SW" + String.format("%03d", counter);
						counter++;
						stmtUpdateID.setInt(1, counter);
						stmtUpdateID.setString(2, "SW");
						stmtUpdateID.executeUpdate();
					} catch (SQLException e1) {
						e1.printStackTrace();
						System.err.println("Failed to get SW counter");
					}
					
				} else if(cb_jenisAC.getSelectedIndex() == 1) {
					try {
						stmtGetID.setString(1, "SF");
						rs = stmtGetID.executeQuery();
						rs.next();
						counter = rs.getInt(1);
						id = "SF" + String.format("%03d", counter);
						counter++;
						stmtUpdateID.setInt(1, counter);
						stmtUpdateID.setString(2, "SF");
						stmtUpdateID.executeUpdate();
					} catch (SQLException e1) {
						e1.printStackTrace();
						System.err.println("Failed to get SF counter");
					}
				}
				
				// Insert into database
				try {
					PreparedStatement stmtCreate = conn.getConnection().prepareStatement("insert into ac values(?, ?, ?, ?, ?, ?, ?)");
					stmtCreate.setString(1, id);
					stmtCreate.setString(2, tf_namaPembeli.getText());
					stmtCreate.setString(3, (String) cb_jenisAC.getSelectedItem());
					stmtCreate.setString(4, (String) cb_merk.getSelectedItem());
					stmtCreate.setFloat(5, (float) cb_pk.getSelectedItem());
					stmtCreate.setInt(6, (int) js_qty.getValue());
					stmtCreate.setInt(7, (int) tf_totalHarga.getValue());
					stmtCreate.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.err.println("Failed to create new transaction!");
				}
				
				JOptionPane.showMessageDialog(this, "Success");
				
				// Refresh Table and Reset Form Input
				refreshData();
				cleanForm();
			}
		} 
		
		else if(e.getSource().equals(btn_deleteAll)) {
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete All Transaction", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);			
			if(confirm == JOptionPane.YES_OPTION) {
				String query = "delete from ac";
				try {
					conn.executeUpdate(query);
					System.out.println("All transaction successfully deleted!");
					JOptionPane.showMessageDialog(this, "Success");
					refreshData();
				} catch (Exception e1) {
					e1.printStackTrace();
					System.err.println("Failed to delete all transaction!");
				}	
			}
		} 
		
		else if(e.getSource().equals(btn_delete)) {
			int[] tableRow = table.getSelectedRows();
			for(int i = 0; i < tableRow.length; i++) System.out.println(tableRow[i]);
			Vector<Integer> rowToDelete = new Vector<Integer>();
			for(int i = 0; i < tableRow.length; i++) {
				rowToDelete.add(tableRow[i]);
			}
			for(int i = 0; i < rowToDelete.size(); i++) {
				String idToDelete = (String)table.getValueAt(rowToDelete.elementAt(i), 0);
				System.out.println(idToDelete);
			}
			PreparedStatement stmtDelete = null;
			try {
				stmtDelete = conn.getConnection().prepareStatement("delete from ac where id=?");
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.err.println("Failed to create preparedStatement stmtDelete!");
			}
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete Selected Transaction", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);			
			if(confirm == JOptionPane.YES_OPTION) {
				for(int i = 0; i < rowToDelete.size(); i++) {
					String idToDelete = (String)table.getValueAt(rowToDelete.elementAt(i), 0);
					try {
						stmtDelete.setString(1, idToDelete);
						stmtDelete.executeUpdate();
						System.out.println("Transaction ID " + idToDelete + " was successfully deleted!");
						if(i == rowToDelete.size()-1) {
							JOptionPane.showMessageDialog(this, "Success");
						}
						cleanForm();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.err.println("Failed to delete transaction id " + idToDelete);
					}
				}
				refreshData();
			}						
		}
		
		else if(e.getSource().equals(btn_update)) {
			if(table.getSelectedRow() != -1) {
				PreparedStatement stmtUpdate = null;
				try {
					stmtUpdate = conn.getConnection().prepareStatement("UPDATE `ac` SET `NamaPembeli`=?,`JenisAC`=?,`Merk`=?,`PK`=?,`Qty`=?,`TotalHarga`=? WHERE `ID`=?");
					stmtUpdate.setString(1, tf_namaPembeli.getText());
					stmtUpdate.setString(2, cb_jenisAC.getSelectedItem().toString());
					stmtUpdate.setString(3, cb_merk.getSelectedItem().toString());
					stmtUpdate.setFloat(4, Float.parseFloat(cb_pk.getSelectedItem().toString()));
					stmtUpdate.setInt(5, (int) js_qty.getValue());
					stmtUpdate.setInt(6, (int) tf_totalHarga.getValue());
					stmtUpdate.setString(7, table.getValueAt(table.getSelectedRow(), 0).toString());
					stmtUpdate.executeUpdate();
					System.out.println("Data successfully updated!");
					JOptionPane.showMessageDialog(this, "Success");
					cleanForm();
					refreshData();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.err.println("Failed to update data!");
				}
			} else {
				showErrorMessage("Please select a data from the table beside!");
			}				
		}
		
		else if(e.getSource().equals(btn_clear)) {
			cleanForm();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(table)) {
			try {
				cleanForm();
				tf_namaPembeli.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				if(table.getValueAt(table.getSelectedRow(), 2).toString().equals("Split Wall")) {
					cb_jenisAC.setSelectedIndex(0);
				} else if(table.getValueAt(table.getSelectedRow(), 2).toString().equals("Standing Floor")) {
					cb_jenisAC.setSelectedIndex(1);
				}
				if(table.getValueAt(table.getSelectedRow(), 3).toString().equals("Sharp")) {
					cb_merk.setSelectedIndex(0);
				} else if(table.getValueAt(table.getSelectedRow(), 3).toString().equals("TCL")) {
					cb_merk.setSelectedIndex(1);
				} else {
					cb_merk.setSelectedIndex(2);
				}
				if(table.getValueAt(table.getSelectedRow(), 4).toString().equals("0.5")) {
					cb_pk.setSelectedIndex(0);
				} else if(table.getValueAt(table.getSelectedRow(), 4).toString().equals("1.0")) {
					cb_pk.setSelectedIndex(1);
				} else if(table.getValueAt(table.getSelectedRow(), 4).toString().equals("1.5")) {
					cb_pk.setSelectedIndex(2);
				} else if(table.getValueAt(table.getSelectedRow(), 4).toString().equals("2.0")) {
					cb_pk.setSelectedIndex(3);
				}
				String qty = table.getValueAt(table.getSelectedRow(), 5).toString();
				js_qty.setValue(Integer.parseInt(qty));
				tf_totalHarga.setText(table.getValueAt(table.getSelectedRow(), 6).toString());
			} catch(Exception e1) {
				e1.printStackTrace();
				System.err.println("Failed to auto input data!");
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(cb_pk.getSelectedIndex() != -1) {
			if(cb_jenisAC.getSelectedIndex() == 1) {
				if(cb_pk.getSelectedIndex() != 0 && cb_pk.getSelectedIndex() != 1) {
					showErrorMessage("Standing Floor AC only available with 0.5 and 1 PK");
					cb_pk.setSelectedIndex(-1);		
				}
			}
		}		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// Auto fill tf_totalHarga based on price per-AC
		int harga = 0;
		// Split Wall
		if(cb_jenisAC.getSelectedIndex() == 0 ) {
			// Sharp
			if(cb_merk.getSelectedIndex() == 0) {
				if(cb_pk.getSelectedIndex() == 0) {
					harga = 2250000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 1) {
					harga = 2750000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 2) {
					harga = 3250000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 3) {
					harga = 3750000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				}
			} 
			
			// TCL
			if(cb_merk.getSelectedIndex() == 1) {
				if(cb_pk.getSelectedIndex() == 0) {
					harga = 2000000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 1) {
					harga = 2500000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 2) {
					harga = 3000000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 3) {
					harga = 3500000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				}
			}
			
			// Samsung
			if(cb_merk.getSelectedIndex() == 2) {
				if(cb_pk.getSelectedIndex() == 0) {
					harga = 2150000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 1) {
					harga = 2650000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 2) {
					harga = 3150000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 3) {
					harga = 3650000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				}
			}
		}
		
		// Standing Floor
		if(cb_jenisAC.getSelectedIndex() == 1 ) {
			// Sharp
			if(cb_merk.getSelectedIndex() == 0) {
				if(cb_pk.getSelectedIndex() == 0) {
					harga = 1250000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 1) {
					harga = 1850000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} 
			}
			
			// TCL
			if(cb_merk.getSelectedIndex() == 1) {
				if(cb_pk.getSelectedIndex() == 0) {
					harga = 1000000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 1) {
					harga = 1600000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} 
			}
			
			// Samsung
			if(cb_merk.getSelectedIndex() == 2) {
				if(cb_pk.getSelectedIndex() == 0) {
					harga = 1150000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} else if(cb_pk.getSelectedIndex() == 1) {
					harga = 1750000 * (int) js_qty.getValue();
					tf_totalHarga.setValue(harga);
				} 
			}
		}
	}
}
