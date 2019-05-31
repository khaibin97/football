import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.shape.Line;

/**
 * This class contains some utility helper methods
 *
 * @author awestlund
 */
public class WordProcessor {

	static List<String> listOfLines = new ArrayList<String>();

//	public static void main(String args[]) {
//		try {
//			getWordStream("word_list.txt");
//			for (int i = 0; i < listOfLines.size(); i++) {
//				System.out.println(listOfLines.get(i));
//			}
//			System.out.println(isAdjacent("MAT", "MATS"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * Gets a Stream of words from the filepath.
	 *
	 * The Stream should only contain trimmed, non-empty and UPPERCASE words.
	 *
	 * @see <a href=
	 *      "http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8
	 *      stream blog</a>
	 *
	 * @param filepath
	 *            file path to the dictionary file
	 * @return Stream<String> stream of words read from the filepath
	 * @throws IOException
	 *             exception resulting from accessing the filepath
	 */
	public static Stream<String> getWordStream(String filepath) throws IOException {
		/**
		 * @see <a href=
		 *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
		 * @see <a href=
		 *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
		 * @see <a href=
		 *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
		 * @see <a href=
		 *      "https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
		 *
		 *      class Files has a method lines() which accepts an interface Path object
		 *      and produces a Stream<String> object via which one can read all the
		 *      lines from a file as a Stream.
		 *
		 *      class Paths has a method get() which accepts one or more strings
		 *      (filepath), joins them if required and produces a interface Path object
		 *
		 *      Combining these two methods: Files.lines(Paths.get(<string filepath>))
		 *      produces a Stream of lines read from the filepath
		 *
		 *      Once this Stream of lines is available, you can use the powerful
		 *      operations available for Stream objects to combine multiple
		 *      pre-processing operations of each line in a single statement.
		 *
		 *      Few of these features: 1. map( ) [changes a line to the result of the
		 *      applied function. Mathematically, line = operation(line)] - trim all the
		 *      lines - convert all the lines to UpperCase - example takes each of the
		 *      lines one by one and apply the function toString on them as
		 *      line.toString() and returns the Stream: streamOfLines =
		 *      streamOfLines.map(String::toString)
		 *
		 *      2. filter( ) [keeps only lines which satisfy the provided condition] -
		 *      can be used to only keep non-empty lines and drop empty lines - example
		 *      below removes all the lines from the Stream which do not equal the
		 *      string "apple" and returns the Stream: streamOfLines =
		 *      streamOfLines.filter(x -> x != "apple");
		 *
		 *      3. collect( ) [collects all the lines into a java.util.List object] -
		 *      can be used in the function which will invoke this method to convert
		 *      Stream<String> of lines to List<String> of lines - example below
		 *      collects all the elements of the Stream into a List and returns the
		 *      List: List<String> listOfLines =
		 *      streamOfLines.collect(Collectors::toList);
		 *
		 *      Note: since map and filter return the updated Stream objects, they can
		 *      chained together as: streamOfLines.map(...).filter(a -> ...).map(...)
		 *      and so on
		 */
		Stream<String> streamOfLines = Files.lines(Paths.get(filepath));
		// should trim, make all upper case, and filter out empty lines
		listOfLines = streamOfLines.map(String::toString).map(String::trim).map(String::toUpperCase)
				.filter(x -> !x.isEmpty()).collect(Collectors.toList());

		return streamOfLines;
	}

	/**
	 * Adjacency between word1 and word2 is defined by: if the difference between
	 * word1 and word2 is of 1 char replacement 1 char addition 1 char deletion then
	 * word1 and word2 are adjacent else word1 and word2 are not adjacent
	 *
	 * Note: if word1 is equal to word2, they are not adjacent
	 *
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return true if word1 and word2 are adjacent else false
	 */
	public static boolean isAdjacent(String word1, String word2) {

		if (word1 == null | word2 == null) {
			return false;
		}
		word1 = word1.trim();
		word2 = word2.trim();
		if (word1 == "" | word2 == "") {
			return false;
		}
		if (word1.equals(word2) == true) {
			return true;
		}
		int len1 = word1.length();
		int len2 = word2.length();
		int compLen = len1 - len2;
		// if the word lengths are greater then 1 apart they aren't adjacent
		if (compLen < -1 | compLen > 1) {
			return false;
		}
		// any singular addition of a letter anywhere in the word
		// if they are the same lengths then look for a letter swap
		else if (compLen == 0) { // swap
			for (int i = 0; i < word1.length(); i++) {
				char char1 = word1.charAt(i);
				char char2 = word2.charAt(i);
				if (char1 != char2) {
					String newWord = swap(i, char2, word1);
					if (newWord.equals(word2)) {
						return true;
					} else {
						return false;
					}
				}
			}
			// is the same word
			return true;
		} else if (compLen == 1) { // deletion
			for (int i = 0; i < word1.length(); i++) {
				String newWord = delete(i, word1);
				if (word2.contentEquals(newWord)) {
					return true;
				}
			}
			return false;
		} else if (compLen == -1) { // add one char
			for (int i = 0; i < word1.length(); i++) {
				char char1 = word1.charAt(i);
				char char2 = word2.charAt(i);
				if (char1 != char2) {
					String newWord = addChar(i, char2, word1);
					if (newWord.equals(word2)) {
						return true;
					} else {
						return false;
					}
				}
			}
			char last = word2.charAt(word2.length() - 1);
			String newWord = addChar(word1.length(), last, word1);
			if (newWord.equals(word2)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * swap swaps the char at the specified index in the given string with a new
	 * char to replace it with
	 * 
	 * @param index of the char being swapped in the string
	 * @param charSwap the char we want in the string
	 * @param word the word1 that we want to change
	 * @return the new word (word1 with swapped out chars)
	 */
	private static String swap(int index, char charSwap, String word) {
		char wordChars[] = word.toCharArray();
		wordChars[index] = charSwap;
		String newWord = new String(wordChars);
		return newWord;
	}

	/**
	 * Delete deletes the one char and the index passed in in to string given
	 * 
	 * @param index
	 *            of the char being removed
	 * @param word
	 *            word1 or the string being changed
	 * @return the new word (word1 - char)
	 */
	private static String delete(int index, String word) {
		char wordChars[] = word.toCharArray();
		// go though and move all chars behind the index up one index space
		for (int i = index; i < word.length() - 1; i++) {
			wordChars[i] = wordChars[i + 1];
		}
		// wordChars[word.length()-1] = 0;
		String newWord = new String(wordChars);
		newWord = newWord.substring(0, word.length() - 1);
		return newWord;
	}

	/**
	 * addChar adds 1 char at a specific index in a word and moves all of the
	 * letters behind the specific index down one index in the string
	 * 
	 * @param index
	 *            the index where the char needs to be inserted
	 * @param newChar
	 *            the letter being added to word1
	 * @param word
	 *            word1 (the word getting a new char)
	 * @return word + new char inserted into it
	 */
	private static String addChar(int index, char newChar, String word) {
		int len = word.length();
		char wordChars2[] = new char[len + 1];
		char wordChars[] = word.toCharArray();
		for (int j = 0; j < wordChars.length; j++) {
			wordChars2[j] = wordChars[j];
		}
		if (len == index) {
			wordChars2[index] = newChar;
			String newWord = new String(wordChars2);
			return newWord;
		}
		// go though and move all chars behind the index up one index space
		for (int i = index; i < word.length(); i++) {
			wordChars2[i + 1] = wordChars[i];
		}
		wordChars2[index] = newChar;
		String newWord = new String(wordChars2);
		return newWord;
	}
}
