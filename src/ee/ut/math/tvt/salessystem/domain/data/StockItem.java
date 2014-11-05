package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name="STOCKITEM")
public class StockItem implements Cloneable, DisplayableItem {

	@Id
    private Long id;
	
	@Column(name="NAME")
    private String name;
	
	@Column(name="PRICE")
    private double price;
	
	@Column(name="DESCRIPTION")
    private String description;
	
	@Column(name="QUANTITY")
    private int quantity;
	
	// L: added solditems field for mapping, like in the example..needed?
//	@OneToMany(mappedBy="stockItem")
//	private Set<SoldItem> soldItems;

	public StockItem() {
	}
    public StockItem(Long barcodeId, String productName, String productDescription, double productPrice) {
        this.id = barcodeId;
        this.name = productName;
        this.description = productDescription;
        this.price = productPrice;
    }

    public StockItem(Long barcodeId, String productName, String productDescription, double productPrice, int quantity) {
        this(barcodeId, productName, productDescription, productPrice);
        this.quantity = quantity;
    }
    
    public StockItem(Long barcodeId, String productName, double productPrice, int quantity){
    	this.id = barcodeId;
    	this.name = productName;
    	this.price = productPrice;
    	this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return name;
    }

    /**
     * Method for querying the value of a certain column when StockItems are shown
     * as table rows in the SalesSystemTableModel. The order of the columns is:
     * id, name, price, quantity.
     */
    public Object getColumn(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return id;
            case 1:
                return name;
            case 2:
                return price;
            case 3:
                return quantity;
            default:
                throw new RuntimeException("invalid column!");
        }
    }

    public Object clone() {
        return new StockItem(getId(), getName(), getDescription(), getPrice(), getQuantity());
    }

}
