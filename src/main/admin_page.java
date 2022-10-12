package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Choice;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import javax.swing.SwingConstants;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class admin_page {

	JFrame frame;
	private JTable table;
	private static String name;

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			admin_page window = new admin_page(name);
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */

	public admin_page(String name) {
		this.name = name;
		initialize();
		reservationData();
	}

	Connection con;
	PreparedStatement insert;
	private JTextField emailTF;

	private void reservationData() {

		try {

			PreparedStatement view;

			int c;

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hotel_reservation", "root", "");

			view = con.prepareStatement("select * from reservation order by cus_id desc");

			ResultSet rs = view.executeQuery();
			java.sql.ResultSetMetaData rss = rs.getMetaData();
			c = rss.getColumnCount();

			DefaultTableModel model = (DefaultTableModel) table.getModel();

			int cols = rss.getColumnCount();
			String[] colName = new String[cols];

			for (int i = 0; i < cols; i++) {
				colName[i] = rss.getColumnName(i + 1);
			}

			model.setColumnIdentifiers(colName);

			String id, name, number, email, month, day, year, roomtype, roomnum, status;

			while (rs.next()) {

				id = rs.getString(1);
				name = rs.getString(2);
				number = rs.getString(3);
				email = rs.getString(4);
				month = rs.getString(5);
				day = rs.getString(6);
				year = rs.getString(7);
				roomtype = rs.getString(8);
				roomnum = rs.getString(9);
				status = rs.getString(10);

				String[] row = { id, name, number, email, month, day, year, roomtype, roomnum, status };
				model.addRow(row);
			}

			table.getColumnModel().getColumn(0).setHeaderValue("ID");
			table.getColumnModel().getColumn(1).setHeaderValue("Name");
			table.getColumnModel().getColumn(2).setHeaderValue("Contact");
			table.getColumnModel().getColumn(3).setHeaderValue("Email");
			table.getColumnModel().getColumn(4).setHeaderValue("Month");
			table.getColumnModel().getColumn(5).setHeaderValue("Day");
			table.getColumnModel().getColumn(6).setHeaderValue("Year");
			table.getColumnModel().getColumn(7).setHeaderValue("Room Type");
			table.getColumnModel().getColumn(8).setHeaderValue("Room No");
			table.getColumnModel().getColumn(9).setHeaderValue("Status");

			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(220);
			table.getColumnModel().getColumn(4).setPreferredWidth(50);
			table.getColumnModel().getColumn(5).setPreferredWidth(40);
			table.getColumnModel().getColumn(6).setPreferredWidth(50);

		} catch (ClassNotFoundException ex) {

			ex.getStackTrace();
			ex.getMessage();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(40, 48, 68));
		frame.setResizable(false);
		frame.setBounds(100, 100, 976, 606);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome To Hotel Hub ");
		lblNewLabel.setFont(new Font("Dubai Medium", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(57, 9, 392, 86);
		frame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(39, 170, 897, 371);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);

		scrollPane.setViewportView(table);

		class CenterCellRenderer extends DefaultTableCellRenderer {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel renderedLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
						row, column);
				renderedLabel.setHorizontalAlignment(JLabel.CENTER);
				return renderedLabel;
			}
		}

		TableCellRenderer renderer = new CenterCellRenderer();
		table.setDefaultRenderer(Object.class, renderer);

		table.setModel(new DefaultTableModel(null, new String[] { "Id", "Name", "Contact", "Email", "Month", "Day",
				"Year", "Room Type", "Room Num", "Status" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PreparedStatement delete;
				DefaultTableModel df = (DefaultTableModel) table.getModel();

				int selectedIndex = table.getSelectedRow();

				try {

					int customer_id = Integer.parseInt(df.getValueAt(selectedIndex, 0).toString());

					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete this record",
							"Warning", JOptionPane.YES_NO_OPTION);

					if (dialogResult == JOptionPane.YES_OPTION) {

						Class.forName("com.mysql.cj.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost/hotel_reservation", "root", "");
						delete = con.prepareStatement("delete from reservation where cus_id = ? ");

						delete.setInt(1, customer_id);

						delete.executeUpdate();

						JOptionPane.showMessageDialog(frame, "Reservation Deleted", "Delete Hotel Reservation? ",
								JOptionPane.INFORMATION_MESSAGE);

					}

					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setRowCount(0);
					reservationData();

				} catch (ClassNotFoundException ex) {

					ex.getStackTrace();
					ex.getMessage();
				}

				catch (SQLException ex) {

					ex.getStackTrace();
					ex.getMessage();
				}

				catch (ArrayIndexOutOfBoundsException ex) {

					JOptionPane.showMessageDialog(frame,
							"Make sure to double click rows to select before clicking delete", "Something went wrong! ",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Dubai Light", Font.BOLD, 17));
		btnDelete.setFocusable(false);
		btnDelete.setBackground(new Color(95, 210, 195));
		btnDelete.setBounds(806, 107, 98, 27);
		frame.getContentPane().add(btnDelete);

		emailTF = new JTextField();
		emailTF.setColumns(10);
		emailTF.setBounds(100, 106, 333, 27);
		frame.getContentPane().add(emailTF);

		JLabel lblNewLabel_2 = new JLabel("Search");
		lblNewLabel_2.setForeground(new Color(95, 210, 195));
		lblNewLabel_2.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(39, 112, 112, 14);
		frame.getContentPane().add(lblNewLabel_2);

		JButton btnFilterByName = new JButton("Filter Search");
		btnFilterByName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				DefaultTableModel model = (DefaultTableModel) table.getModel();

				final TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

				table.setRowSorter(sorter);

				String nameFilter = emailTF.getText();

				if (nameFilter.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					try {
						sorter.setRowFilter(RowFilter.regexFilter(nameFilter));
					} catch (PatternSyntaxException pse) {
						System.out.println("Bad regex pattern");
					}
				}

				model.setRowCount(0);
				reservationData();

			}
		});

		JButton refreshData = new JButton("Refresh");
		refreshData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();

				final TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
				table.setRowSorter(sorter);
				sorter.setRowFilter(null);

			}
		});
		btnFilterByName.setForeground(Color.WHITE);
		btnFilterByName.setFont(new Font("Dubai Light", Font.BOLD, 14));
		btnFilterByName.setFocusable(false);
		btnFilterByName.setBackground(new Color(95, 210, 195));
		btnFilterByName.setBounds(457, 108, 131, 22);
		frame.getContentPane().add(btnFilterByName);

		refreshData.setForeground(Color.WHITE);
		refreshData.setFont(new Font("Dubai Light", Font.BOLD, 17));
		refreshData.setFocusable(false);
		refreshData.setBackground(new Color(95, 210, 195));
		refreshData.setBounds(691, 107, 98, 27);
		frame.getContentPane().add(refreshData);

		JButton logout = new JButton("Logout");
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				admin_form af = new admin_form();
				af.adminForm.setVisible(true);
				frame.dispose();
			}
		});
	
		logout.setForeground(new Color(95, 210, 195));
		logout.setFont(new Font("Dubai Light", Font.BOLD, 17));
		logout.setFocusable(false);
		logout.setBackground(Color.WHITE);
		logout.setBounds(806, 34, 98, 32);
		frame.getContentPane().add(logout);

		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();

					PreparedStatement update;
					DefaultTableModel df = (DefaultTableModel) table.getModel();

					try {

						int selectedIndex = table.getSelectedRow();

						int customer_id = Integer.parseInt(df.getValueAt(selectedIndex, 0).toString());

						String[] choices = { "Reserved", "Checked In", "Checked Out" };
						String input = (String) JOptionPane.showInputDialog(null, "Choose", "Change Status? ",
								JOptionPane.QUESTION_MESSAGE, null, choices, // Array of choices
								choices[1]); // Initial choice

						Class.forName("com.mysql.cj.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost/hotel_reservation", "root", "");

						update = con.prepareStatement("update reservation set status = ? where cus_id = ? ");

						update.setString(1, input);
						update.setInt(2, customer_id);

						update.executeUpdate();

						JOptionPane.showMessageDialog(frame, "Reservation Updated", "Update Hotel Reservation? ",
								JOptionPane.INFORMATION_MESSAGE);

						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.setRowCount(0);
						reservationData();

					} catch (ClassNotFoundException ex) {

						ex.getStackTrace();
						ex.getMessage();
					}

					catch (SQLException ex) {

						ex.getStackTrace();
						ex.getMessage();
					}

					catch (ArrayIndexOutOfBoundsException ex) {

						JOptionPane.showMessageDialog(frame,
								"Make sure to double click rows to select before clicking delete",
								"Something went wrong! ", JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});
	}
}
