package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;

public class login_form extends JFrame {

	JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login_form frame = new login_form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	login_form() throws IOException {
		
		setTitle("Hotel Hub");
		setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\Eclipse\\Hotel Reservation\\img\\hotel.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 472, 314);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(40, 44, 68));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("WELCOME TO HOTELHUB");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Dubai Medium", Font.BOLD, 30));
		lblNewLabel.setBounds(49, 23, 365, 83);
		contentPane.add(lblNewLabel);

		
		// Go to Reservation 
		JButton hotelReserve = new JButton("Hotel Reservation");
		hotelReserve.setForeground(new Color(255, 255, 255));
		hotelReserve.setFocusable(false);
		hotelReserve.setBackground(new Color(95, 210, 195));
		hotelReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservation reserve = new reservation();
				reserve.frmHotelHub.setVisible(true);
				dispose();
				
			}
		});
		hotelReserve.setFont(new Font("Dubai Light", Font.BOLD, 17));
		hotelReserve.setBounds(137, 182, 174, 39);
		contentPane.add(hotelReserve);

		
		// Go to Admin
		JButton hotelAdmin = new JButton("Hotel Admin");
		hotelAdmin.setForeground(new Color(255, 255, 255));
		hotelAdmin.setFocusable(false);
		hotelAdmin.setBackground(new Color(95, 210, 195));
		hotelAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin_form adminForm = new admin_form();
				adminForm.adminForm.setVisible(true);
				dispose();
				
			}
			
		});
		hotelAdmin.setFont(new Font("Dubai Light", Font.BOLD, 17));
		hotelAdmin.setBounds(137, 116, 174, 39);
		contentPane.add(hotelAdmin);
	
	

	}
}
