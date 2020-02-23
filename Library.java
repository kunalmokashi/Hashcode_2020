package HashCode;

import java.util.HashSet;
import java.util.Set;

public class Library {

	final int libraryId;
	Set<Book> books = new HashSet<>();
	final int daysForSignup;
	final int numOfBooks;
	final int booksShippedPerDay;
	
	Library(int libId, int daysForSignup, int numOfBooks, int booksShippedPerDay) {
		this.libraryId = libId;
		this.daysForSignup = daysForSignup;
		this.numOfBooks = numOfBooks;
		this.booksShippedPerDay = booksShippedPerDay;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public int getLibraryId() {
		return libraryId;
	}

	public int getDaysForSignup() {
		return daysForSignup;
	}

	public int getNumOfBooks() {
		return numOfBooks;
	}

	public int getBooksShippedPerDay() {
		return booksShippedPerDay;
	}

	@Override
	public String toString() {
		return "Library [libraryId=" + libraryId + ", books=" + books + ", daysForSignup=" + daysForSignup
				+ ", numOfBooks=" + numOfBooks + ", booksShippedPerDay=" + booksShippedPerDay + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result + booksShippedPerDay;
		result = prime * result + daysForSignup;
		result = prime * result + libraryId;
		result = prime * result + numOfBooks;
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
		Library other = (Library) obj;
		if (libraryId != other.libraryId)
			return false;
		return true;
	}

	/*@Override
	public int compareTo(Library o) {
		return this.daysForSignup > o.daysForSignup ? 1 : -1;
	}*/
	
}
