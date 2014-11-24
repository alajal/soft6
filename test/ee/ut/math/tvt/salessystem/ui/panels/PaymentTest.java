package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.salessystem.FakeDataService;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTabModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ee.ut.math.tvt.salessystem.ui.panels.PaymentFrame.round;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaymentTest {
	SalesSystemModel salesSystemModel;
	SalesDomainControllerImpl salesDomainController;
	FakeDataService fakeDataService;


	@Before
	public void setUp(){
		fakeDataService = new FakeDataService();
		salesDomainController = new SalesDomainControllerImpl(fakeDataService);
		salesSystemModel = new SalesSystemModel(salesDomainController);
	}

	@Test
	public void testRoundMethod()  {
		double real = round(3.67866456, 2);
		double expected = 3.68;
		assertEquals(expected, real, 0.001);
	}

	@Test
	public void testCreatePayment() throws VerificationFailedException {
		StockItem stockItem = new StockItem(5L,"Pannkoogid", "pannkoogid moosiga", 2,5);
		SoldItem soldItem = new SoldItem(stockItem, 2);
		List<SoldItem> soldItems = new ArrayList<>();
		soldItems.add(soldItem);
		HistoryTabModel historyTabModel = salesSystemModel.getHistoryTabModel();

		salesDomainController.createPayment(soldItems, salesSystemModel);

		int expectedStockQuantity = 3;
		Order expectedOrder = new Order(soldItems,new Date());

		int expectedOrderSum = 4;
		int orderedSum = (int) historyTabModel.getOrderedSum(expectedOrder);

		assertEquals(expectedStockQuantity, stockItem.getQuantity());
		assertEquals(expectedOrderSum, orderedSum);

	}

}