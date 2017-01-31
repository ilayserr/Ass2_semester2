package dict;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

/*
Assignment number : 2.1
File Name : InMemoryDictionary.java
Name : Ilay Serr
Email : ilay92@gmail.com
*/

/**
 * Implements a persistent dictionary that can be held entirely in memory.
 * When flushed, it writes the entire dictionary back to a file.
 * 
 * The file format has one keyword per line:
 * <pre>word:def1:def2:def3,...</pre>
 * 
 * Note that an empty definition list is allowed (in which case the entry would have the form: <pre>word:</pre> 
 */
public class InMemoryDictionary extends TreeMap<String,String> implements PersistentDictionary  {
	private static final long serialVersionUID = 1L; // (because we're extending a serializable class)
	private File file;
	
	public InMemoryDictionary(File dictFile) {
		this.file = dictFile;
	}
	
	@Override
	/**
	 * taking all the data from the file and inserting it
	 * to the TreeMap in the way requested in the assignment.
	 */
	public void open() throws IOException {
		if (!this.file.exists()) this.file.createNewFile();
		BufferedReader reader = new BufferedReader(new FileReader(this.file));
		try {
			String line = reader.readLine();
			while (line != null) {
				int index = line.indexOf(":");
				String word = line.substring(0 , index);
				String def = line.substring(index + 1);
				this.put(word, def);
				line = reader.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error" + e);
		} finally {
			reader.close();
		}
		
	}

	@Override
	/**
	 * moving all the data from TreeMap into the file in the 
	 * specified way requested.
	 */
	public void close() throws IOException {
		if (!this.file.exists()) this.file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
		try {
			for(String key: this.keySet()){
				writer.write(key + ":" + this.get(key) + "\n");
			}
		} catch (IOException e) {
			System.out.println("Error" + e);
		} finally {
			writer.close();
		}
	}
}
