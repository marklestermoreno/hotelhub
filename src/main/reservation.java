package main;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class reservation {

	JFrame frmHotelHub;
	private JTextField guestName, contactNumber, emailTxt;
	Choice dayOfReservation, month_reservation, year_Reservation;

	private static String emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\."
			+ "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			reservation window = new reservation();
			window.frmHotelHub.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public reservation() {
		initialize();
	}

	public static boolean emailValidation(String email) {

		Pattern p = Pattern.compile(emailFormat);

		if (email == null) {
			return false;
		}
		return p.matcher(email).matches();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		Date date = new java.util.Date();

		frmHotelHub = new JFrame();
		frmHotelHub.setResizable(false);
		frmHotelHub.setTitle("Hotel Hub");
		frmHotelHub
				.setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\Eclipse\\Hotel Reservation\\img\\hotel.png"));
		frmHotelHub.getContentPane().setBackground(new Color(40, 48, 68));
		frmHotelHub.setBounds(100, 100, 794, 486);
		frmHotelHub.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHotelHub.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Hotel Hub Reservation");
		lblNewLabel.setFont(new Font("Dubai Medium", Font.BOLD, 22));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(49, 36, 247, 45);
		frmHotelHub.getContentPane().add(lblNewLabel);

		JButton homeButton = new JButton("Home");
		homeButton.setForeground(Color.WHITE);
		homeButton.setFont(new Font("Dubai Light", Font.BOLD, 17));
		homeButton.setFocusable(false);
		homeButton.setBackground(new Color(95, 210, 195));
		homeButton.setBounds(633, 43, 98, 32);
		homeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				login_form lf;
				try {
					lf = new login_form();
					lf.setVisible(true);
					frmHotelHub.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		frmHotelHub.getContentPane().add(homeButton);

		JPanel formBg = new JPanel();
		formBg.setBackground(Color.WHITE);
		formBg.setBorder(UIManager.getBorder("Button.border"));
		formBg.setBounds(48, 116, 683, 309);
		frmHotelHub.getContentPane().add(formBg);
		formBg.setLayout(null);

		/*----------------------------------------------------------------- Form -------------------------------------------------------------- */

		JLabel lblNewLabel_2 = new JLabel("Guest Name");
		lblNewLabel_2.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(30, 31, 112, 14);
		formBg.add(lblNewLabel_2);

		guestName = new JTextField();
		guestName.setColumns(10);
		guestName.setBounds(122, 25, 518, 27);
		formBg.add(guestName);

		contactNumber = new JTextField();
		contactNumber.setColumns(10);
		contactNumber.setBounds(122, 70, 227, 27);
		formBg.add(contactNumber);

		contactNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyCode() == 8) {
					contactNumber.setEditable(true);
				} else {
					contactNumber.setEditable(false);
				}

			}

		});

		JLabel contactLabel = new JLabel("Contact No.");
		contactLabel.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		contactLabel.setBounds(30, 76, 112, 14);
		formBg.add(contactLabel);

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		emailLabel.setBounds(362, 76, 112, 14);
		formBg.add(emailLabel);

		emailTxt = new JTextField();
		emailTxt.setColumns(10);
		emailTxt.setBounds(404, 70, 236, 27);
		formBg.add(emailTxt);

		JLabel reserveLabel = new JLabel("Reservation Date");
		reserveLabel.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		reserveLabel.setBounds(30, 128, 112, 14);
		formBg.add(reserveLabel);

		JLabel lblReservationType = new JLabel("Reservation Type");
		lblReservationType.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblReservationType.setBounds(30, 176, 112, 14);
		formBg.add(lblReservationType);

		Choice reservationType = new Choice();
		reservationType.setBounds(150, 176, 200, 20);

		reservationType.add("VIP Class");
		reservationType.add("High Class");
		reservationType.add("Normal Class");
		reservationType.add("Low Class");

		formBg.add(reservationType);

		Choice hotelRoom = new Choice();
		hotelRoom.setBounds(150, 216, 200, 20);

		int roomNum;
		String dateOfMonth[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
				"34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" };

		for (roomNum = 0; roomNum <= dateOfMonth.length - 1; roomNum++) {
			hotelRoom.add("Room No. " + dateOfMonth[roomNum]);
		}

		formBg.add(hotelRoom);

		JLabel lblReservationNumber = new JLabel("Reservation No.");
		lblReservationNumber.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblReservationNumber.setBounds(30, 221, 128, 14);
		formBg.add(lblReservationNumber);

		JDateChooser calendarChooser = new JDateChooser();
		calendarChooser.setDate(date);
		calendarChooser.setBounds(150, 125, 200, 20);

		String dob = "" + calendarChooser.getDate();

		String monthSelected = dob.substring(4, 7);
		String yearSelected = dob.substring(24, 28);
		String daySelected = dob.substring(8, 10);

		formBg.add(calendarChooser);

		JButton reserveHotel = new JButton("Reserve Hotel");
		reserveHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Pattern p = Pattern.compile(emailFormat);

				String status = "Reserved";

				if (contactNumber.getText().length() <= 11) {
					if (guestName.getText().length() > 6 && contactNumber.getText().length() > 6
							&& emailTxt.getText().length() > 6) {

						if (p.matcher(emailTxt.getText()).matches()) {

							try {

								Class.forName("com.mysql.cj.jdbc.Driver");
								Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hotel_reservation",
										"root", "");

								String adminQuery = "insert into reservation(name, contactnumber, email, month, day, year, roomtype, roomnum, status) "
										+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
								PreparedStatement ps = con.prepareStatement(adminQuery);

								ps.setString(1, guestName.getText());
								ps.setString(2, contactNumber.getText());
								ps.setString(3, emailTxt.getText());

								ps.setString(4, monthSelected);
								ps.setString(5, daySelected);
								ps.setString(6, yearSelected);

								ps.setString(7, reservationType.getSelectedItem());
								ps.setString(8, hotelRoom.getSelectedItem());

								ps.setString(9, status);

								ps.executeUpdate();
								JOptionPane.showMessageDialog(frmHotelHub, "Hotel Reserved");

								ps.close();
								con.close();

								guestName.setText("");
								contactNumber.setText("");
								emailTxt.setText("");
								calendarChooser.setDate(date);

								admin_form adminForm = new admin_form();
								adminForm.adminForm.setVisible(true);
								frmHotelHub.dispose();

							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(frmHotelHub, "Fill all the blanks", "Registration Error ",
										JOptionPane.WARNING_MESSAGE);
								e1.printStackTrace();
							}

							catch (Exception e1) {
								e1.printStackTrace();
							}
						}

						else {
							JOptionPane.showMessageDialog(null, "Invalid Email", "Something went wrong! ",
									JOptionPane.ERROR_MESSAGE);
						}
					}

					else {

						JOptionPane.showMessageDialog(null, "All fields must be atleast 6 characters above",
								"Something went wrong! ", JOptionPane.ERROR_MESSAGE);
					}

				}
				else {
					JOptionPane.showMessageDialog(null, "Contact Number must be 11 digits below",
							"Something went wrong! ", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		reserveHotel.setForeground(Color.WHITE);
		reserveHotel.setFont(new Font("Dubai Light", Font.BOLD, 17));
		reserveHotel.setFocusable(false);
		reserveHotel.setBackground(new Color(95, 210, 195));
		reserveHotel.setBounds(290, 260, 142, 32);
		formBg.add(reserveHotel);
	}
}
