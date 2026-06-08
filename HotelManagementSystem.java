import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HotelManagementSystem {
    public static void main(String[] args) {
        new LoginPage();
    }
}

/* ---------------- LOGIN PAGE ---------------- */

class LoginPage extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    LoginPage() {

        setTitle("Sunrise Hotel - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(245, 240, 255));

        JLabel title = new JLabel("SUNRISE HOTEL LOGIN");
        title.setBounds(60, 30, 300, 30);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(new Color(123, 97, 255));
        add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(60, 90, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 90, 160, 30);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(60, 130, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 130, 160, 30);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(130, 190, 120, 35);
        loginButton.setBackground(new Color(123, 97, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);
        add(loginButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.equals("admin") && pass.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Login Successful");
            new BookingPage();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
        }
    }
}

/* ---------------- BOOKING PAGE ---------------- */

class BookingPage extends JFrame implements ActionListener {

    JTextField nameField, contactField, daysField;
    JRadioButton standard, deluxe, suite;
    JComboBox<String> foodBox;
    JRadioButton cash, card, upi;
    JButton billButton;

    BookingPage() {

        setTitle("Sunrise Hotel - Room Booking");
        setSize(650, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(245, 240, 255));

        JLabel heading = new JLabel("ROOM BOOKING FORM");
        heading.setFont(new Font("Serif", Font.BOLD, 24));
        heading.setBounds(180, 20, 400, 30);
        heading.setForeground(new Color(123, 97, 255));
        add(heading);

        addLabel("Guest Name:", 80);
        nameField = addField(250, 80);

        addLabel("Contact:", 120);
        contactField = addField(250, 120);

        addLabel("Number of Days:", 160);
        daysField = addField(250, 160);

        addLabel("Room Type:", 200);

        standard = new JRadioButton("Standard - Rs 1000/day");
        deluxe = new JRadioButton("Deluxe - Rs 2000/day");
        suite = new JRadioButton("Suite - Rs 3000/day");

        standard.setBounds(250, 200, 250, 25);
        deluxe.setBounds(250, 230, 250, 25);
        suite.setBounds(250, 260, 250, 25);

        ButtonGroup roomGroup = new ButtonGroup();
        roomGroup.add(standard);
        roomGroup.add(deluxe);
        roomGroup.add(suite);

        add(standard);
        add(deluxe);
        add(suite);

        addLabel("Food Option:", 300);

        String[] foods = {"None", "Veg - Rs 300/day", "Non-Veg - Rs 500/day"};
        foodBox = new JComboBox<>(foods);
        foodBox.setBounds(250, 300, 250, 30);
        add(foodBox);

        addLabel("Payment Mode:", 350);

        cash = new JRadioButton("Cash");
        card = new JRadioButton("Card");
        upi = new JRadioButton("UPI");

        cash.setBounds(250, 350, 80, 25);
        card.setBounds(330, 350, 80, 25);
        upi.setBounds(410, 350, 80, 25);

        ButtonGroup payGroup = new ButtonGroup();
        payGroup.add(cash);
        payGroup.add(card);
        payGroup.add(upi);

        add(cash);
        add(card);
        add(upi);

        billButton = new JButton("Generate Bill");
        billButton.setBounds(230, 430, 180, 40);
        billButton.setBackground(new Color(123, 97, 255));
        billButton.setForeground(Color.WHITE);
        billButton.setFocusPainted(false);
        billButton.addActionListener(this);
        add(billButton);

        setVisible(true);
    }

    private void addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(80, y, 150, 25);
        add(label);
    }

    private JTextField addField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 250, 30);
        add(field);
        return field;
    }

    public void actionPerformed(ActionEvent e) {

        try {
            String name = nameField.getText();
            String contact = contactField.getText();
            int days = Integer.parseInt(daysField.getText());

            int roomRate = 0;
            String roomType = "";

            if (standard.isSelected()) {
                roomRate = 1000;
                roomType = "Standard";
            } else if (deluxe.isSelected()) {
                roomRate = 2000;
                roomType = "Deluxe";
            } else if (suite.isSelected()) {
                roomRate = 3000;
                roomType = "Suite";
            } else {
                JOptionPane.showMessageDialog(this, "Select Room Type!");
                return;
            }

            int total = roomRate * days;

            String food = foodBox.getSelectedItem().toString();
            if (food.contains("Veg")) total += 300 * days;
            else if (food.contains("Non-Veg")) total += 500 * days;

            String payment = "";
            if (cash.isSelected()) payment = "Cash";
            else if (card.isSelected()) payment = "Card";
            else if (upi.isSelected()) payment = "UPI";
            else {
                JOptionPane.showMessageDialog(this, "Select Payment Mode!");
                return;
            }

            double gst = total * 0.18;
            double finalAmount = total + gst;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String dateTime = dtf.format(LocalDateTime.now());

            StringBuilder bill = new StringBuilder();

            bill.append("====================================\n");
            bill.append("           SUNRISE HOTEL\n");
            bill.append("        Pune, Maharashtra\n");
            bill.append("====================================\n");
            bill.append("Date: ").append(dateTime).append("\n");
            bill.append("------------------------------------\n");
            bill.append(String.format("%-18s: %s\n", "Guest Name", name));
            bill.append(String.format("%-18s: %s\n", "Contact", contact));
            bill.append(String.format("%-18s: %d\n", "Days", days));
            bill.append(String.format("%-18s: %s\n", "Room Type", roomType));
            bill.append(String.format("%-18s: %s\n", "Food", food));
            bill.append(String.format("%-18s: %s\n", "Payment", payment));
            bill.append("------------------------------------\n");
            bill.append(String.format("%-20s Rs %.2f\n", "Room Charges", (roomRate * days * 1.0)));
            bill.append(String.format("%-20s Rs %.2f\n", "GST (18%)", gst));
            bill.append("------------------------------------\n");
            bill.append(String.format("%-20s Rs %.2f\n", "TOTAL AMOUNT", finalAmount));
            bill.append("====================================\n");
            bill.append("        Thank You! Visit Again\n");
            bill.append("====================================\n");

            JTextArea textArea = new JTextArea(bill.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 500));

            JOptionPane.showMessageDialog(this, scrollPane,
                    "Hotel Bill Receipt", JOptionPane.PLAIN_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid number of days!");
        }
    }
}
