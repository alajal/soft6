package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.service.HibernateDataService;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;


import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.JTableHeader;


public class StockTab {

	private StockItem stockItem;
	private static final Logger log = Logger.getLogger(StockTab.class);
	private SalesSystemModel model;
	private JButton addButton;
    private JButton submitAdd;
    private JTextField addIdField;
    private JTextField addNameField;
    private JTextField addPriceField;
    private JTextField addQuantityField;

    private Long id;
	private String name;
	private double price;
	private int quantity;


    public StockTab(SalesSystemModel model) {
        this.model = model;

    }

    // warehouse stock tab - consists of a menu and a table
    public Component createStockTab() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        panel.setLayout(gridBagLayout);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1.0d;
        gc.weighty = 0d;

        panel.add(drawStockMenuPane(), gc);


        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        panel.add(drawStockMainPane(), gc);

        return panel;
    }

    private JButton createNewAddButton() {
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newAddButtonClicked();
            }
        });

        return addButton;
    }

    private JButton createConfirmButton() {
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitaddButtonClicked();
            }
        });
        confirmButton.setEnabled(false);

        return confirmButton;
    }

    // warehouse menu
    private Component drawStockMenuPane() {
        JPanel panel = new JPanel();
        //panel.setLayout(new FlowLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;

        addIdField = new JTextField(Integer.toString(model.getWarehouseTableModel().getRowCount()+1),3);
        addNameField = new JTextField("",10);
        addPriceField = new JTextField("",5);
        addQuantityField = new JTextField("",4);
        addButton = createNewAddButton();
        submitAdd = createConfirmButton();

        panel.add(addButton, gc);


        panel.add(new JLabel("Id: "), gc);
        panel.add(addIdField, gc);
        panel.add(new JLabel("Name:"), gc);
        panel.add(addNameField, gc);
        panel.add(new JLabel("Price:"), gc);
        panel.add(addPriceField, gc);
        panel.add(new JLabel("Amount:"), gc);
        panel.add(addQuantityField, gc);

        panel.add(submitAdd, gc);

        addIdField.setEditable(false);
        addNameField.setEditable(false);
        addPriceField.setEditable(false);
        addQuantityField.setEditable(false);

        addIdField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fillDialogFields();

			}

        });

        addIdField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				fillDialogFields();

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

        //GridBagConstraints gc = new GridBagConstraints();
        //GridBagLayout gb = new GridBagLayout();

        //panel.setLayout(gb);

        //gc.anchor = GridBagConstraints.NORTHWEST;
        //gc.weightx = 0;


        //gc.gridwidth = GridBagConstraints.RELATIVE;
        //gc.weightx = 1.0;

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }


    // table of the warehouse stock
    private Component drawStockMainPane() {
        JPanel panel = new JPanel();

        StockTableModel warehouseTableModel = model.getWarehouseTableModel();
        JTable table = new JTable(warehouseTableModel);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        panel.setLayout(gb);
        panel.add(scrollPane, gc);

        panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
        return panel;
    }

    public void reset(){
    	addIdField.setText(Integer.toString(model.getWarehouseTableModel().getRowCount()+1));
        addNameField.setText("");
        addPriceField.setText("");
        addQuantityField.setText("");
        submitAdd.setEnabled(false);
        addButton.setEnabled(true);
        addNameField.setEditable(false);
        addPriceField.setEditable(false);
        addQuantityField.setEditable(false);
    }



    public void fillDialogFields(){
    	try{
    		StockItem item = model.getWarehouseTableModel().getItemById(Integer.parseInt(addIdField.getText()));
    		addNameField.setText(item.getName());
        	addNameField.setEditable(false);
        	addPriceField.setText(String.valueOf(item.getPrice()));
            addPriceField.setEditable(false);
    	}
        catch(Exception e){
        	addNameField.setText("");
            addPriceField.setText("");
            addNameField.setEditable(true);
            addPriceField.setEditable(true);
    	}
    }


    protected void newAddButtonClicked() {
        log.info("New item input process started");
        startNewAdd();
    }
    private void startNewAdd() {
    	addButton.setEnabled(false);
    	submitAdd.setEnabled(true);
    	addIdField.setEditable(true);
    	addNameField.setEditable(true);
        addPriceField.setEditable(true);
        addQuantityField.setEditable(true);
    }

    protected void submitaddButtonClicked() {
        HibernateDataService service = new HibernateDataService();
        log.info("Confirm button clicked");
        try {
        	stockItem = new StockItem(id, name, price, quantity);
        	int emptyFields = 0;
            JTextField [] fields = {addIdField, addNameField, addPriceField, addQuantityField};
            for (JTextField field : fields) {
                if (field.getText().trim().isEmpty()) {
                    emptyFields += 1;
                }
            }
            if(emptyFields > 0){
            	log.info("Wrong input");
            	reset();
            }

            if(emptyFields == 0){
            	log.info("Adding process started");
            	name = addNameField.getText().trim();

            	//check if fields contain correct data (quantity and id should only consist of numbers 0-9 and price input is restricted to 0-2 decimal places)
            	if(fields[0].getText().matches("^\\d+$") && fields[3].getText().matches("^\\d+$") && fields[2].getText().matches(("[0-9]*\\.?[0-9]{0,2}"))){
                    setUserEnteredItemInfo();
                    model.getWarehouseTableModel().addItem(stockItem);
    				log.info("Item added to warehouse!");

    				service.addStockItem(model.getWarehouseTableModel().getItemById(stockItem.getId()));
    				log.info("Item saved to database");
    				reset();
    				}

            	else{
            		log.info("Wrong input.");
            		reset();
            	}
            }
        } catch (StockTableModel.UnsuitableItem e) {
            log.error(e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Item with the same name already exists in the warehouse. \n" +
                            "You cannot enter item with this name.");
        }
    }

    private void setUserEnteredItemInfo() {
        id = Long.parseLong(addIdField.getText().trim());
        quantity = Integer.parseInt(addQuantityField.getText().trim());
        price = Double.parseDouble(addPriceField.getText().trim());

        stockItem.setId(id);
        stockItem.setName(name);
        stockItem.setPrice(price);
        stockItem.setQuantity(quantity);
    }
}
