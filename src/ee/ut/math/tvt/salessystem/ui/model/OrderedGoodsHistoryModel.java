package ee.ut.math.tvt.salessystem.ui.model;


import ee.ut.math.tvt.salessystem.domain.data.SoldItem;


public class OrderedGoodsHistoryModel extends SalesSystemTableModel<SoldItem>{


    public OrderedGoodsHistoryModel() {
        super(new String[]{"Id", "Name", "Price", "Quantity", "Sum"});

    }

    public void addItemtoTable(SoldItem item){

        rows.add(item);
        fireTableDataChanged();
    }


    @Override
    //JTable kutsub välja
    //JTableis ei ole andmeid, lisan need tablemodelisse
    //protected List<T> rows; sees on andmed
    protected Object getColumnValue(SoldItem item, int columnIndex) {
        switch (columnIndex){
            case 0:
                return item.getId();
            case 1:
                return itemName(item);
            case 2:
                return itemPrice(item);
            case 3:
                return itemQuantity(item);
            case 4:
                return itemSum(item);
        }
        throw new IllegalArgumentException("Column index out of range");
    }

    private Double itemSum(SoldItem item) {
        return item.getSum();
    }

    private Integer itemQuantity(SoldItem item) {
        return item.getQuantity();
    }

    private Double itemPrice(SoldItem item) {
        return item.getPrice();
    }

    private String itemName(SoldItem item) {
        return item.getName();
    }


}

