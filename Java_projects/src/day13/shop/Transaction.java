package day13.shop;

public class Transaction {
    private int indexInGoodsList;
	private String currentDate;
	private String clientLogin;
	private String elementName;
	private int price;
	private int quantity;

	public Transaction(){
	}
	public Transaction(int indexInGoodsList,String currentData, String clientLogin,
			String elementName, int price, int quantity) {
		super();
        this.indexInGoodsList = indexInGoodsList;
		this.currentDate = currentData;
		this.clientLogin = clientLogin;
		this.elementName = elementName;
		this.price = price;
		this.quantity = quantity;
	}

    public int getIndexInGoodsList() {
        return indexInGoodsList;
    }

    public void setIndexInGoodsList(int indexInGoodsList) {
        this.indexInGoodsList = indexInGoodsList;
    }

    public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentData) {
		this.currentDate = currentData;
	}

	public String getClientLogin() {
		return clientLogin;
	}

	public void setClientLogin(String clientLogin) {
		this.clientLogin = clientLogin;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		
		return "Date: " + this.getCurrentDate()+" "+this.clientLogin+" "+this.elementName+" "+
		         " Price: "+this.getPrice()+"USD"+" Goods: "+this.getQuantity();
	}
	
	
}
