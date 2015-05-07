package day13.shop;

public class ShopLauncher {

	public static void main(String[] args) throws InterruptedException {
		//SplashScreen splash = SplashScreen.getSplashScreen();

		//Thread.sleep(3000);
		//splash.close();
		
		Date date = new Date();
		Stock stock = new Stock();
		CustomerDB customerDB = new CustomerDB();

	   
		
	    stock.add(new Book("Book #0", "Bonser W.", Jenre.HISTORY, 150));
	    stock.add(new Book("Book #1", "Ivanov D.", Jenre.FANTASY, 20));
	    stock.add(new Book("Book #2", "Gromov N.", Jenre.FICTION, 27));
	    stock.add(new Book("Book #3", "Petrov H.", Jenre.FICTION, 30));
	    stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
	    stock.add(new Book("Book #0", "Bonser W.", Jenre.HISTORY, 150));
	    stock.add(new Book("Book #0", "Bonser W.", Jenre.HISTORY, 150));
	    stock.add(new Book("Book #0", "Bonser W.", Jenre.HISTORY, 150));
	    stock.add(new Book("Book #1", "Ivanov D.", Jenre.FANTASY, 20));
	    stock.add(new Book("Book #2", "Gromov N.", Jenre.FICTION, 27));
	    stock.add(new Book("Book #3", "Petrov H.", Jenre.FICTION, 30));
	    stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
	    stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
	    stock.add(new Book("Book #1", "Ivanov D.", Jenre.FANTASY, 20));
	    stock.add(new Book("Book #2", "Gromov N.", Jenre.FICTION, 27));
	    stock.add(new Book("Book #3", "Petrov H.", Jenre.FICTION, 30));
	    stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
	    stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
		stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
		stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
		stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
		stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
		stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
		stock.add(new Book("Book #4", "Petrov O.", Jenre.FOOD, 60));
	    stock.add(new Magazine("SPORT", "1", Jenre.HOBBY, 15));
	    stock.add(new Magazine("SPORT", "1", Jenre.HOBBY, 15));
	    stock.add(new Magazine("FASHION", "1", Jenre.HOBBY, 12));

        new Server(customerDB).start();
	//	splash.close();
	//	ShopUI ui = new ShopUI(date,stock);
        new ShopGUI(date,stock,customerDB);
	}

}
