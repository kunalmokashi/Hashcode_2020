package HashCode;

public class Book {

	final int id;
	int score;
	
	Book(int id) {
		this.id = id;
	}
	

	public int getScore() {
		return this.score;
	}


	public int getId() {
		return id;
	}


	public void setScore(int score) {
		this.score = score;
	}


	@Override
	public String toString() {
		return "Book [id=" + id + ", score=" + score + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + score;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id != other.id)
			return false;
		if (score != other.score)
			return false;
		return true;
	}


	/*@Override
	public int compareTo(Book o) {
		return this.score < o.score ? 1 : -1;
	}*/
}
