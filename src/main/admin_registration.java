package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class admin_registration extends JFrame {

	private JPanel adminRegisterForm;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtAddress;
	private JTextField txtMobile;
	private JRadioButton rbFemale;
	private JPasswordField txtPassword;
	private JButton createAdmin, home;
	private static String emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\."
			+ "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			admin_registration frame = new admin_registration();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean emailValidation(String email) {

		Pattern p = Pattern.compile(emailFormat);

		if (email == null) {
			return false;
		}
		return p.matcher(email).matches();
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
	 * Create the frame.
	 */

	public admin_registration() {
		setTitle("Expense Tracker Registration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 859, 516);
		adminRegisterForm = new JPanel();
		adminRegisterForm.setBackground(new Color(40, 48, 68));
		adminRegisterForm.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(adminRegisterForm);
		adminRegisterForm.setLayout(null);

		txtName = new JTextField();
		txtName.setBounds(150, 121, 628, 27);
		adminRegisterForm.add(txtName);
		txtName.setColumns(10);
		txtName.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (txtName.getText().length() >= 50)
					e.consume();
			}
		});

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(150, 234, 269, 27);
		txtEmail.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (txtEmail.getText().length() >= 50)
					e.consume();
			}
		});

		adminRegisterForm.add(txtEmail);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(150, 175, 628, 27);
		txtAddress.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (txtEmail.getText().length() >= 150)
					e.consume();
			}
		});
		adminRegisterForm.add(txtAddress);

		txtMobile = new JTextField();
		txtMobile.setColumns(10);
		txtMobile.setBounds(559, 234, 219, 27);
		txtMobile.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (txtMobile.getText().length() >= 11)
					e.consume();
			}

		});
		adminRegisterForm.add(txtMobile);

		ButtonGroup genderRadio = new ButtonGroup();

		JRadioButton rbMale = new JRadioButton("Male");
		rbMale.setForeground(new Color(255, 255, 255));
		rbMale.setBackground(new Color(40, 48, 60));
		rbMale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rbMale.setBounds(150, 289, 75, 23);
		rbMale.setFocusable(false);
		adminRegisterForm.add(rbMale);

		rbFemale = new JRadioButton("Female");
		rbFemale.setForeground(new Color(255, 255, 255));
		rbFemale.setBackground(new Color(40, 48, 60));
		rbFemale.setFocusable(false);
		rbFemale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rbFemale.setBounds(227, 289, 89, 23);
		adminRegisterForm.add(rbFemale);

		genderRadio.add(rbFemale);
		genderRadio.add(rbMale);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(150, 338, 628, 27);
		txtPassword.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			public void keyTyped(KeyEvent e) {
				if (txtPassword.getText().length() >= 50)
					e.consume();
			}
		});
		adminRegisterForm.add(txtPassword);

		createAdmin = new JButton("Create Admin");
		createAdmin.setForeground(Color.WHITE);
		createAdmin.setFocusPainted(false);
		createAdmin.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
		createAdmin.setBackground(new Color(95, 210, 195));
		createAdmin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Pattern p = Pattern.compile(emailFormat);

				if (txtName.getText().length() > 8 && txtAddress.getText().length() > 8
						&& txtMobile.getText().length() > 8 && txtPassword.getText().length() > 8) {

					if (p.matcher(txtEmail.getText()).matches()) {

						try {
							
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hotel_reservation",
									"root", "");
							
							String adminQuery = "insert into registration(name, email, mobilenumber, gender, password, address) values(?, ?, ?, ?, ?, ?)";
							PreparedStatement ps = con.prepareStatement(adminQuery);

							ps.setString(1, txtName.getText());
							ps.setString(2, txtEmail.getText());
							ps.setString(3, txtMobile.getText());

							if (rbMale.isSelected()) {

								ps.setString(4, rbMale.getText());

							}

							else if (rbFemale.isSelected()) {

								ps.setString(4, rbFemale.getText());
							}

							else {

								JOptionPane.showMessageDialog(null, "Please Select Admin Gender", "Registration Error",
										JOptionPane.ERROR_MESSAGE);

							}

							String passwordHash = generatePassword(txtPassword.getText());

							ps.setString(5, passwordHash);

							ps.setString(6, txtAddress.getText());

							ps.executeUpdate();
							JOptionPane.showMessageDialog(createAdmin, "Admin Added");

							ps.close();
							con.close();

							txtName.setText("");
							txtMobile.setText("");
							txtEmail.setText("");
							txtAddress.setText("");
							txtPassword.setText("");
							genderRadio.clearSelection();

							admin_form adminForm = new admin_form();
							adminForm.adminForm.setVisible(true);
							dispose();

						} catch (SQLIntegrityConstraintViolationException e1) {

							JOptionPane.showMessageDialog(null, " Email already registered on the system",
									"Email exists", JOptionPane.ERROR_MESSAGE);

						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(adminRegisterForm, "Fill all the blanks",
									"Registration Error ", JOptionPane.WARNING_MESSAGE);
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

					JOptionPane.showMessageDialog(null, "All fields must be atleast 8 characters above",
							"Something went wrong! ", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		createAdmin.setBounds(459, 401, 137, 35);
		adminRegisterForm.add(createAdmin);

		JLabel nameLabel = new JLabel("Admin Name");
		nameLabel.setForeground(new Color(95, 210, 195));
		nameLabel.setFont(new Font("Dubai", Font.PLAIN, 15));
		nameLabel.setBounds(50, 127, 109, 14);
		adminRegisterForm.add(nameLabel);

		JLabel emailLabel = new JLabel("Email Address");
		emailLabel.setForeground(new Color(95, 210, 195));
		emailLabel.setFont(new Font("Dubai", Font.PLAIN, 15));
		emailLabel.setBounds(42, 240, 109, 14);
		adminRegisterForm.add(emailLabel);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setForeground(new Color(95, 210, 195));
		lblAddress.setFont(new Font("Dubai", Font.PLAIN, 15));
		lblAddress.setBounds(75, 181, 75, 14);
		adminRegisterForm.add(lblAddress);

		JLabel sexLabel = new JLabel("Sex");
		sexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sexLabel.setForeground(new Color(95, 210, 195));
		sexLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sexLabel.setBounds(75, 293, 75, 14);
		adminRegisterForm.add(sexLabel);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(new Color(95, 210, 195));
		passwordLabel.setFont(new Font("Dubai", Font.PLAIN, 15));
		passwordLabel.setBounds(67, 345, 75, 14);
		adminRegisterForm.add(passwordLabel);

		JLabel lblExpenseTracker = new JLabel("Hotel Hub - Admin Registration");
		lblExpenseTracker.setForeground(new Color(95, 210, 195));
		lblExpenseTracker.setBackground(new Color(40, 48, 68));
		lblExpenseTracker.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpenseTracker.setFont(new Font("Dubai Medium", Font.BOLD, 25));
		lblExpenseTracker.setBounds(0, 26, 816, 71);
		adminRegisterForm.add(lblExpenseTracker);

		home = new JButton("Home");
		home.setForeground(Color.WHITE);
		home.setFocusPainted(false);
		home.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
		home.setBackground(new Color(95, 210, 195));
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

				admin_form adminForm = new admin_form();
				adminForm.adminForm.setVisible(true);

			}
		});
		home.setBounds(282, 401, 137, 35);
		adminRegisterForm.add(home);

		JLabel lblMobileNumber_1 = new JLabel("Cellphone No.");
		lblMobileNumber_1.setForeground(new Color(95, 210, 195));
		lblMobileNumber_1.setFont(new Font("Dubai", Font.PLAIN, 15));
		lblMobileNumber_1.setBounds(454, 240, 109, 14);
		adminRegisterForm.add(lblMobileNumber_1);

	}
}
