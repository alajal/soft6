package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

import java.text.SimpleDateFormat;
import java.util.List;

//extendib TableModel klassi läbi SaleSystemTableModel klassi
//The TableModel interface specifies the methods the JTable will use to interrogate a tabular data model
//JTable "kuulab üle"

public class HistoryTabModel extends SalesSystemTableModel<Order> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	//tabelile vajalik info

    public HistoryTabModel() {
        super(new String[]{"Order total sum", "Time of the order", "Date of the order"});
    }

    //lisab tabelisse uusi andmeid
    public void addData(Order order){
        rows.add(order);
        fireTableDataChanged();
    }

    @Override
    protected Object getColumnValue(Order order, int columnIndex) {
        switch (columnIndex){
            case 0:
                return getOrderedSum(order);
            case 1:
                return getTimeOftheOrder(order);
            case 2:
                return getDateOftheOrder(order);
        }
        throw new IllegalArgumentException("Column index out of range.");
    }

    public String getTimeOftheOrder(Order order) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        return timeFormatter.format(order.getDateAndTimeOfTheOrder());
    }

    public String getDateOftheOrder(Order order) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormatter.format(order.getDateAndTimeOfTheOrder());
    }

    public double getOrderedSum(Order order) {
        Double orderSum = 0.0;
        List<SoldItem> orderedItems = order.getOrderedItems();
        for (SoldItem item : orderedItems) {
            Double itemsSum = item.getSum();
            orderSum = orderSum + itemsSum;
        }
        return orderSum;
    }


    public Order getOrder(int row) {
        return rows.get(row);
    }
}
