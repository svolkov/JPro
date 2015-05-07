package day13.shop;

//import day7.classroomBar7.Student;

public class Book extends Goods {
	String author;
	Jenre jenre;

	public Book(){
		}
	public Book(String title, String author, Jenre jenre, int price) {

		this.name = title;
		this.author = author;
		this.jenre = jenre;
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Jenre getJenre() {
		return jenre;
	}
	public void setJenre(Jenre jenre) {
		this.jenre = jenre;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Book){
			Book b = (Book) obj;
			if(name != null && name.equals(b.name) && author != null && 
					author.equals(b.author) && jenre != null && jenre.equals(b.jenre)){
				return true;
			}
		}
		return false; 
	}
	
}
