package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class admin_form {

	JFrame adminForm;
	private JTextField txtEmail, txtPassword;

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			admin_form window = new admin_form();
			window.adminForm.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public admin_form() {
		initialize();
	}

	// Password Hashing

	private byte[] getSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		return salt;
	}

	public String generatePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = getSalt();

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		byte[] hash = factory.generateSecret(spec).getEncoded();

		String hashedPass = encodeHexString(hash) + "." + encodeHexString(salt);

		return hashedPass;
	}

	public String generatePassword(String password, String hexSalt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = decodeHexString(hexSalt);

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		byte[] hash = factory.generateSecret(spec).getEncoded();

		String hashedPass = encodeHexString(hash) + "." + encodeHexString(salt);

		return hashedPass;
	}

	public String encodeHexString(byte[] arr) {

		StringBuilder hashedPass = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			hashedPass.append(byteToHex(arr[i]));
		}

		return hashedPass.toString();
	}

	public byte[] decodeHexString(String hex) {
		byte[] salt = new byte[hex.length() / 2];

		for (int i = 0; i < hex.length(); i += 2) {
			salt[i / 2] = hexToByte(hex.substring(i, i + 2));
		}

		return salt;
	}

	public String byteToHex(byte num) {
		char[] hexDigits = new char[2];
		hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
		hexDigits[1] = Character.forDigit((num & 0xF), 16);
		return new String(hexDigits);
	}

	public byte hexToByte(String hexString) {
		int firstDigit = toDigit(hexString.charAt(0));
		int secondDigit = toDigit(hexString.charAt(1));
		return (byte) ((firstDigit << 4) + secondDigit);
	}

	private int toDigit(char hexChar) {
		int digit = Character.digit(hexChar, 16);
		if (digit == -1) {
			throw new IllegalArgumentException("Invalid Hexadecimal Character: " + hexChar);
		}
		return digit;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		adminForm = new JFrame();
		adminForm.getContentPane().setBackground(new Color(40, 48, 68));

		adminForm.setTitle("Hotel Hub");
		adminForm.setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\Eclipse\\Hotel Reservation\\img\\hotel.png"));
		adminForm.setResizable(false);
		adminForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminForm.setBounds(100, 100, 475, 454);
		adminForm.getContentPane().setLayout(null);

		JLabel title = new JLabel("WELCOME TO HOTELHUB");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Dubai Medium", Font.BOLD, 30));
		title.setBounds(52, 25, 365, 83);
		adminForm.getContentPane().add(title);

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setForeground(new Color(95, 210, 195));
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailLabel.setBounds(39, 119, 157, 32);
		adminForm.getContentPane().add(emailLabel);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(new Color(95, 210, 195));
		passwordLabel.setBackground(new Color(95, 210, 195));
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordLabel.setBounds(39, 205, 98, 32);
		adminForm.getContentPane().add(passwordLabel);

		txtEmail = new JTextField();
		txtEmail.setBounds(63, 162, 340, 32);
		txtEmail.setColumns(10);
		adminForm.getContentPane().add(txtEmail);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(63, 248, 340, 32);
		adminForm.getContentPane().add(txtPassword);

		JButton back = new JButton("Back");
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				login_form lf;
				try {
					lf = new login_form();
					lf.setVisible(true);
					adminForm.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		back.setForeground(Color.WHITE);
		back.setFont(new Font("Dubai Light", Font.BOLD, 17));
		back.setFocusable(false);
		back.setBackground(new Color(95, 210, 195));
		back.setBounds(65, 325, 98, 32);
		adminForm.getContentPane().add(back);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				try {

					PreparedStatement view;

					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hotel_reservation", "root",
							"");
					view = con.prepareStatement("Select * from registration where email = ? ");

					view.setString(1, txtEmail.getText());

					ResultSet rs = view.executeQuery();

					if (rs.next()) {

						String originalPassword = rs.getString("password");

						String hashedPass = generatePassword(txtPassword.getText(),
								originalPassword.substring(originalPassword.indexOf(".") + 1));

						if (hashedPass.equals(originalPassword)) {
							admin_page ap = new admin_page(rs.getString("name"));
							ap.frame.setVisible(true);
							
							adminForm.dispose();
						}

						else {
							JOptionPane.showMessageDialog(null, "Incorrect Password");
						}

					} else {
						JOptionPane.showMessageDialog(null, "Incorrect Credentials");
					}
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		});
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Dubai Light", Font.BOLD, 17));
		btnLogin.setFocusable(false);
		btnLogin.setBackground(new Color(95, 210, 195));
		btnLogin.setBounds(300, 325, 98, 32);
		adminForm.getContentPane().add(btnLogin);

		JButton btnRegister = new JButton("Register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				admin_registration admin_reg = new admin_registration();
				admin_reg.setVisible(true);
				adminForm.dispose();

			}

		});
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFont(new Font("Dubai Light", Font.BOLD, 17));
		btnRegister.setFocusable(false);
		btnRegister.setBackground(new Color(95, 210, 195));
		btnRegister.setBounds(185, 325, 98, 32);
		adminForm.getContentPane().add(btnRegister);

	}
}
