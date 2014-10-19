package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

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

    private List<SoldItem> soldItems;

    private final SalesDomainController domainController;
    private SalesSystemModel model;

    // create panel and add stuff to it
    public PaymentFrame(List<SoldItem> soldItems, SalesDomainController controller, SalesSystemModel model) {
        this.soldItems = soldItems;
        this.domainController = controller;
        this.model = model;

        drawPaymentPanel();

        this.setTitle("Payment");

        // set L&F to the nice Windows style
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());

        } catch (UnsupportedLookAndFeelException e1) {
            log.warn(e1.getMessage());
        }

        // size
        int width = 300;
        int height = 200;
        this.setSize(width, height);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - width) / 2, (screen.height - height) / 2);
    }

    private void drawPaymentPanel() {
        JPanel panel = new JPanel();  // panel for holding stuff

        this.totalOrderAmountField = createTotalOrderAmountField();
        this.paymentAmountField = createPaymentAmountField();
        this.changeAmountField = createChangeAmountField();
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

    // sum up price*quantity for all items, put it in text field
    private JTextField createTotalOrderAmountField() {
        double totalAmount = 0.0;
        for (SoldItem item : this.soldItems) {
            totalAmount += item.getSum();
        }

        JTextField t = new JTextField(Double.toString(totalAmount));
        t.setEditable(false);
        t.setEnabled(false);

        return t;
    }

    // create field for entering payment, add listener for change
    private JTextField createPaymentAmountField() {
        JTextField t = new JTextField("0");

        t.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                double orderAmt = Double.parseDouble(totalOrderAmountField.getText());
                double paymentAmt = Double.parseDouble(paymentAmountField.getText());
                double changeAmt = Math.max(0, paymentAmt - orderAmt);

                changeAmountField.setText(Double.toString(changeAmt));
            }

            @Override
            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub

            }
        });

        return t;
    }


    private JTextField createChangeAmountField() {
        JTextField t = new JTextField("0.0");
        t.setEditable(false);
        t.setEnabled(false);

        return t;
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

    // cancel just hides the window
    protected void cancelButtonClicked() {
        this.setVisible(false);

    }

    // if payment >= orderAmount the order is submitted and window hidden
    protected void acceptButtonClicked() {
        try {
            double orderAmt = Double.parseDouble(totalOrderAmountField.getText());
            double paymentAmt = Double.parseDouble(paymentAmountField.getText());
            if (paymentAmt >= orderAmt) {
                this.domainController.submitCurrentPurchase(soldItems);
                model.getCurrentPurchaseTableModel().clear();

                this.setVisible(false);
            }

        } catch (VerificationFailedException e) {
            log.error(e.getMessage());
        }

    }

}
