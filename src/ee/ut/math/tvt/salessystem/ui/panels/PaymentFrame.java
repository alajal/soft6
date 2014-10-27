package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTabModel;
import org.apache.log4j.Logger;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;

public class PaymentFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(PaymentFrame.class);

    private JTextField totalOrderAmount;
    private JTextField paymentAmount;
    private JTextField changeAmount;
    private JButton acceptButton;
    private JButton cancelButton;
    private List<SoldItem> soldItems;
    private final SalesDomainController domainController;
    private SalesSystemModel model;
    private PurchaseTab purchaseTab;

    // create panel and add stuff to it
    public PaymentFrame(List<SoldItem> soldItems, SalesDomainController controller, SalesSystemModel model, PurchaseTab purchaseTab,
                        double totalAmount) {
        this.soldItems = soldItems;
        this.domainController = controller;
        this.model = model;
        this.purchaseTab = purchaseTab;

        drawPaymentPanel(totalAmount);
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

    private void drawPaymentPanel(double totalAmount) {
        JPanel panel = new JPanel();  // panel for holding stuff

        this.totalOrderAmount = createTotalOrderAmountField(totalAmount);
        this.paymentAmount = createPaymentAmountField();
        this.changeAmount = createChangeAmountField();
        this.acceptButton = createAcceptButton();
        this.cancelButton = createCancelButton();

        panel.setLayout(new GridLayout(4, 2)); // 4 rows, 2 cols

        // add labels and fields to panel
        panel.add(new JLabel("Order total sum"));
        panel.add(totalOrderAmount);

        panel.add(new JLabel("Payment amount"));
        panel.add(paymentAmount);

        panel.add(new JLabel("Change amount"));
        panel.add(changeAmount);

        panel.add(acceptButton);
        panel.add(cancelButton);

        this.add(panel);  // add panel to frame

    }

    // sum up price*quantity for all items, put it in text field
    private JTextField createTotalOrderAmountField(double totalAmount) {
        JTextField t = new JTextField(Double.toString(totalAmount));
        t.setEditable(false);
        t.setEnabled(false);

        return t;
    }

    // create field for entering payment, add listener for change
    private JTextField createPaymentAmountField() {
        JTextField t = new JTextField("0.00");

        t.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    double orderAmt = Double.parseDouble(totalOrderAmount.getText());
                    double paymentAmt = Double.parseDouble(paymentAmount.getText());
                    double changeAmt = round(Math.max(0, paymentAmt - orderAmt), 2);

                    changeAmount.setText(Double.toString(changeAmt));
                } catch (NumberFormatException e1) {
                    paymentAmount.setText("0.00");
                    JOptionPane.showMessageDialog(null, "Incorrect payment format!");
                }
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
        log.info("Payment cancelled");
        this.setVisible(false);

    }

    // if payment >= orderAmount the order is submitted and window hidden
    protected void acceptButtonClicked() {
        try {
            double orderAmt = Double.parseDouble(totalOrderAmount.getText());

            String paymentText = paymentAmount.getText();
            double paymentAmt = Double.parseDouble(paymentText);
            // L: check if too many decimal points
            int decimalPlaces;
            String[] aa = paymentText.split("\\.");
            if (aa.length <= 1) {
                decimalPlaces = 0;
            } else {
                decimalPlaces = aa[1].length();
            }

            if (paymentAmt >= orderAmt) {
                if (decimalPlaces <= 2) {
                    log.info("Sale confirmed and payment accepted.");

                    //HistoryTabModel comes in
                    HistoryTabModel historyTabModel = model.getHistoryTabModel();
                    Order order = new Order(soldItems, new Date());
                    historyTabModel.addData(order);

                    this.domainController.submitCurrentPurchase(soldItems);
                    model.getCurrentPurchaseTableModel().clear();

                    this.setVisible(false);
                    purchaseTab.endSale();  // L: sooo ugly

                } else {
                    JOptionPane.showMessageDialog(null, "Too many decimal places!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Too small payment!");
            }

        } catch (VerificationFailedException e) {
            log.error(e.getMessage());
        }

    }


    // L: helper f-n for rounding
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
