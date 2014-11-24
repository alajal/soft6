package ee.ut.math.tvt.salessystem.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;


public class HibernateDataService {
	private Session session;

	public HibernateDataService() {
		session = HibernateUtil.currentSession();
	}

	public HibernateDataService(Session session) {
		this.session = session;
	}

	// paring "from entity_name" tagastabki koik read, list() teeb selle veel listiks
	public List<StockItem> getStockItems() {
		@SuppressWarnings("unchecked")
		List<StockItem> result = session.createQuery("from StockItem").list();
		return result;
	}
	
	public List<SoldItem> getSoldItems() {
		@SuppressWarnings("unchecked")
		List<SoldItem> result = session.createQuery("from SoldItem").list();
		return result;
	}
	
    @SuppressWarnings("unchecked")
    public List<Order> getOrders() {
        List<Order> orders = session.createQuery("FROM Order").list();
        return orders;
    }
	
	public void addStockItem(StockItem stockItem) {
		Transaction trans = null;
		try {
			trans = session.beginTransaction();
			if (session.get(StockItem.class, stockItem.getId()) != null) {
				StockItem stockItemFromTable = (StockItem)session.get(StockItem.class, stockItem.getId());
				stockItemFromTable.setQuantity(stockItem.getQuantity());
				session.update(stockItemFromTable);
			} else {
				session.save(stockItem);
			}
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null) trans.rollback();
			e.printStackTrace();
		}
	}
	
	public void addOrder(Order order) {
		Transaction trans = null;
		try {
			trans = session.beginTransaction();
			session.save(order);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null) trans.rollback();
			e.printStackTrace();
		}
	}
	
	public void addSoldItem(SoldItem soldItem) {
		Transaction trans = null;
		try {
			trans = session.beginTransaction();
			session.save(soldItem);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null) trans.rollback();
			e.printStackTrace();
		}
	}
	
	public void updateStockItemQuantity(StockItem stockItem, int itemQuantity) {
		Transaction trans = null;
		try {
			trans = session.beginTransaction();
			StockItem stockItemFromTable = (StockItem)session.get(StockItem.class, stockItem.getId());
			stockItemFromTable.setQuantity(itemQuantity);
			session.update(stockItemFromTable);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null) trans.rollback();
			e.printStackTrace();
		}
	}

}
