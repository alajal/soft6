package ee.ut.math.tvt.salessystem.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * A simple CommandLineInterface (CLI) (limited functionality).
 */
public class ConsoleUI {
    private static final Logger log = Logger.getLogger(ConsoleUI.class);
    private final SalesDomainController salesDomainController;
    private List<SoldItem> cart;
    private List<StockItem> warehouse;

    public ConsoleUI(SalesDomainController domainController) {
        this.salesDomainController = domainController;
        cart = new ArrayList<>();
        warehouse = new ArrayList<>();
    }

    /**
     * Run the sales system CLI.
     */
    public void run() throws IOException {
        try {
            populateWarehouse();

            System.out.println("===========================");
            System.out.println("=       Sales System      =");
            System.out.println("===========================");
            printUsage();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));

            while (true) {
                System.out.print("> ");
                String command = in.readLine();
                processCommand(command.trim().toLowerCase());
                System.out.println("Done. ");
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private void populateWarehouse() {
        warehouse = salesDomainController.loadWarehouseState();
    }

    private void showStock(List<StockItem> stock) {
        System.out.println("-------------------------");
        for (StockItem stockItem : stock) {
            System.out.println(stockItem.getId() + " "
                    + stockItem.getName() + " "
                    + stockItem.getPrice() + "Euro ("
                    + stockItem.getQuantity() + " items)");
        }
        if (stock.size() == 0) {
            System.out.println("\tNothing");
        }
        System.out.println("-------------------------");
    }
    
    private void showCart() {
        System.out.println("-------------------------");
        for (SoldItem soldItem : cart) {
            System.out.println(soldItem.getId() + " "
                    + soldItem.getName() + " "
                    + soldItem.getPrice() + "Euro ("
                    + soldItem.getQuantity() + " items)");
        }
        if (cart.size() == 0) {
            System.out.println("\tNothing");
        }
        System.out.println("-------------------------");
    }

    private void printUsage() {
        System.out.println("-------------------------");
        System.out.println("Usage:");
        System.out.println("h\t\tShow this help");
        System.out.println("w\t\tShow warehouse contents");
        System.out.println("c\t\tShow cart contents");
        System.out.println("a IDX NR \tAdd NR of stock item with index IDX to the cart");
        System.out.println("p\t\tPurchase the shopping cart");
        System.out.println("r\t\tReset the shopping cart");
        System.out.println("q\t\tEnd DB session and exit console application");
        System.out.println("-------------------------");
    }

    private StockItem getStockItemById(int id) {
        for (StockItem item : warehouse) {
            if (item.getId() == id)
                return item;
        }
        throw new NoSuchElementException();
    }

    private void processCommand(String command) {
        String[] commands = command.split(" ");

        if (commands[0].equals("h"))
            printUsage();
        else if (commands[0].equals("q")){
        	try {
				salesDomainController.endSession();
			} catch (HibernateException e) {
				System.out.println("Database error on closing!");
			} finally {
				System.exit(0);
			}
        }
        else if (commands[0].equals("w"))
            showStock(warehouse);
        else if (commands[0].equals("c"))
            showCart();
        else if (commands[0].equals("p"))
            try {
                salesDomainController.submitCurrentPurchase(cart);
                cart.clear();
            } catch (VerificationFailedException e) {
                log.error(e.getMessage());
            }
        else if (commands[0].equals("r"))
            try {
                salesDomainController.cancelCurrentPurchase();
                cart.clear();
            } catch (VerificationFailedException e) {
                log.error(e.getMessage());
            }
        else if (commands[0].equals("a") && commands.length == 3) {
            int idx = Integer.parseInt(commands[1]);
            int amount = Integer.parseInt(commands[2]);
            SoldItem item = new SoldItem(getStockItemById(idx), Math.min(amount, getStockItemById(idx).getQuantity()));
            cart.add(item);
        }
    }

}
