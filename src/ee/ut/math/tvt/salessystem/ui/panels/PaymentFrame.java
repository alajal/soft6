package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

public class PaymentFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(PaymentFrame.class);

	// text fields for totalOrderAmount, paymentAmount, changeAmount
	private JTextField totalOrderAmountField;
	private JTextField paymentAmountField;
	private JTextField changeAmountField;
	// buttons for accepting and cancelling
	private JButton acceptButton;
	private JButton cancelButton;

	// create panel and add stuff to it
	public PaymentFrame() {
		drawPaymentPanel();

		this.setTitle("Payment");

		// set L&F to the nice Windows style
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());

		} catch (UnsupportedLookAndFeelException e1) {
			log.warn(e1.getMessage());
		}

		// size
		int width = 600;
		int height = 400;
		this.setSize(width, height);
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation((screen.width - width) / 2, (screen.height - height) / 2);
	}

	private void drawPaymentPanel() {
		JPanel panel = new JPanel();  // panel for holding stuff
		
		this.totalOrderAmountField = new JTextField("0");
		this.paymentAmountField = new JTextField("0");
		this.changeAmountField = new JTextField("0");
		this.acceptButton = createAcceptButton();
		this.cancelButton = createCancelButton();

		panel.setLayout(new GridLayout(4, 2)); // 4 rows, 2 cols

		// add labels and fields to panel
		panel.add(new JLabel("Order total sum"));
		panel.add(totalOrderAmountField);

		panel.add(new JLabel("Payment amount"));
		panel.add(paymentAmountField);

		panel.add(new JLabel("Change amount"));
		panel.add(changeAmountField);

		panel.add(acceptButton);
		panel.add(cancelButton);
		
		this.add(panel);  // add panel to frame

	}

	// creates Accept button
	private JButton createAcceptButton() {
		JButton b = new JButton("Accept");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				acceptButtonClicked();

			}
		});
		return b;
	}

	// creates Cancel button
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonClicked();

			}
		});
		return b;
	}

	protected void cancelButtonClicked() {
		// TODO Auto-generated method stub

	}

	protected void acceptButtonClicked() {
		// TODO Auto-generated method stub

	}

}
