package day13.shop;

public class Magazine extends Goods {
	protected String issueNumber;
	Jenre jenre;

	public Magazine() {
	}

	public Magazine(String title, String issueNumber, Jenre jenre, int price) {
		this.name = title;
		this.issueNumber = issueNumber;
		this.jenre = jenre;
		this.price = price;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public Jenre getJenre() {
		return jenre;
	}

	public void setJenre(Jenre jenre) {
		this.jenre = jenre;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Magazine) {
			Magazine m = (Magazine) obj;
			if (name != null && name.equals(m.name) && issueNumber != null
					&& issueNumber.equals(m.issueNumber) && jenre != null
					&& jenre.equals(m.jenre)) {
				return true;
			}
		}
		return false;
	}

}
