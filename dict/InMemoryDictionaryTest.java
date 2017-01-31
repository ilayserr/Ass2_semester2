package dict;

/*
Assignment number : 2.1
File Name : InMemoryDictionaryTest.java
Name : Ilay Serr
Email : ilay92@gmail.com
*/
import java.io.File;
import java.io.IOException;

public class InMemoryDictionaryTest extends PersistentDictionaryTest {
	@Override
	PersistentDictionary getDictionary(File file) throws IOException {
		return new InMemoryDictionary(dictFile);
	}
}
