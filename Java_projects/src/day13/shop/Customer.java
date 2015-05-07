package day13.shop;

public class Customer {
	private String login;
    private String email;

    public Customer() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

	//public Customer(String login) {
	//	this.login = login;
	//}

	public String getLogin() {
		return login;
	}

}
