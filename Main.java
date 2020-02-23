package HashCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

	static int totalBooks = 0;
	static int libraryCount = 0;
	static int numDays = 0;
	
	public static void main(String[] args) {
//		String path = "a_example.txt";
//		String path = "b_read_on.txt";
//		String path = "c_incunabula.txt";
//		String path = "d_tough_choices.txt";
		String path = "e_so_many_books.txt";
//		String path = "f_libraries_of_the_world.txt";
		List<Library> libraries = new ArrayList<>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			int lineCnt = 0;
			int cnt = 0;
			String line = null;
			while((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				if (lineCnt == 0) {
					totalBooks = Integer.parseInt(parts[0]);
					libraryCount = Integer.parseInt(parts[1]);
					numDays = Integer.parseInt(parts[2]);
					lineCnt++;
				} else if (lineCnt == 1) {
					for (int i = 0; i < totalBooks; i++) {
						map.put(i, Integer.parseInt(parts[i]));
					}
					lineCnt++;
				} else {
					if (cnt < libraryCount) {
						Library lib = new Library(cnt, Integer.parseInt(parts[1]), Integer.parseInt(parts[0]), Integer.parseInt(parts[2]));
						String innerLine = br.readLine();
						String[] innerParts = innerLine.split(" ");
						Set<Book> booksInLib = new HashSet<>();
						for (int i = 0; i < innerParts.length; i++) {
							int bookId = Integer.parseInt(innerParts[i]);
							Book b = new Book(bookId);
							int score = map.get(bookId);
							b.setScore(score);
							booksInLib.add(b);
						}
						lib.setBooks(booksInLib);
						libraries.add(lib);
						cnt++;
					}
				}
			}
		maximizeScore(libraries, map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void maximizeScore(List<Library> libraries, Map<Integer, Integer> map) {
		Collections.sort(libraries, new Comparator<Library>() {
			@Override
			public int compare(Library o1, Library o2) {
				if (o1.getLibraryId() == o2.getLibraryId()) {
					return 0;
				}
				return o1.getDaysForSignup() > o2.getDaysForSignup() ? 1 : -1;
			}
		});
		int daysLeft = numDays;
		int i = 0;
		Map<Integer, Integer> numBooksUsed = new HashMap<>();
		Map<Integer, List<Book>> booksUsed = new HashMap<>();
		int librariesUsed = 0;
		List<Integer> libraryIds = new ArrayList<>();
		List<Integer> result3 = new LinkedList<>();
		while (daysLeft > 0 && i < libraries.size()) {
			Library lib = libraries.get(i);
			libraryIds.add(lib.getLibraryId());
			daysLeft -= lib.getDaysForSignup();
			List<Book> books = new ArrayList<>(lib.getBooks());
			Collections.sort(books, new Comparator<Book>() {

				@Override
				public int compare(Book o1, Book o2) {
					return o1.getScore() > o2.getScore() ? 1 : -1;
				}
			});
			int perDay = lib.getBooksShippedPerDay();
			int numBooks = books.size();
			int factor = numBooks / perDay;
			if (daysLeft > 0) {
				i++;
				librariesUsed++;
				if (factor <= daysLeft) {
					numBooksUsed.put(lib.getLibraryId(), numBooks);
					booksUsed.put(lib.getLibraryId(), books);
					result3.add(lib.getLibraryId());
				} else {
					int diff = factor - daysLeft;
					int numBooksNotScanned = diff * perDay;
					List<Book> usedBooks = books.subList(0, books.size() - numBooksNotScanned);
					numBooksUsed.put(lib.getLibraryId(), usedBooks.size());
					booksUsed.put(lib.getLibraryId(), usedBooks);
					result3.add(lib.getLibraryId());
				}
			}
		}
		writeToFile(numBooksUsed, booksUsed, librariesUsed, result3);
	}

	private static void writeToFile(Map<Integer, Integer> numBooksUsed, Map<Integer, List<Book>> booksUsed, int librariesUsed, List<Integer> result3) {
		BufferedWriter bw = null;
		try {
//			bw = new BufferedWriter(new FileWriter("a_example_out.txt"));
//			bw = new BufferedWriter(new FileWriter("b_read_on_out.txt"));
//			bw = new BufferedWriter(new FileWriter("c_incunabula_out.txt"));
//			bw = new BufferedWriter(new FileWriter("d_tough_choices_out.txt"));
			bw = new BufferedWriter(new FileWriter("e_so_many_books_out.txt"));
//			bw = new BufferedWriter(new FileWriter("f_libraries_of_the_world_out.txt"));
			bw.write(Integer.toString(librariesUsed));
			for (Integer lib: result3) {
				bw.newLine();
				bw.write(lib + " " + numBooksUsed.get(lib));
				bw.newLine();
				List<Book> books = booksUsed.get(lib);
				List<String> vals = books.stream().map(Book::getId).map(s -> String.valueOf(s)).collect(Collectors.toList());
				bw.write(String.join(" ",vals));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
