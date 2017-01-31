package files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;

/*
Assignment number : 2.4
File Name : Streams.java
Name : Ilay Serr
Email : ilay92@gmail.com
*/

public class Streams {
	/**
	 * Read from an InputStream until a quote character (") is found, then read
	 * until another quote character is found and return the bytes in between the two quotes. 
	 * If no quote character was found return null, if only one, return the bytes from the quote to the end of the stream.
	 * @param in
	 * @return A list containing the bytes between the first occurrence of a quote character and the second.
	 */
	public static List<Byte> getQuoted(InputStream in) throws IOException {
		List<Byte> result = new ArrayList<Byte>();
		try {
			Byte temp = (byte)in.read();
			while (temp != -1 && temp != '"') {
				temp = (byte)in.read();
			}
			if (temp == -1) return null;
			temp = (byte)in.read();
			while (temp != -1 && temp != '"') {
				result.add(temp);
				temp = (byte)in.read();
			}
		} catch (IOException e) {
			System.out.println("Error" + e);
		} finally {
			in.close();
		}
		return result;
	}
	
	
	/**
	 * Read from the input until a specific string is read, return the string read up to (not including) the endMark.
	 * @param in the Reader to read from
	 * @param endMark the string indicating to stop reading. 
	 * @return The string read up to (not including) the endMark (if the endMark is not found, return up to the end of the stream).
	 */
	public static String readUntil(Reader in, String endMark) throws IOException {
		StringBuilder sb = new StringBuilder();
		int c; 
		
		// a string that checks if the input is the end mark
		String temp = "";
		try {
			c = in.read();
            while (c != -1) {
                temp += (char)c;
                
                // check if the x first letters of temp equal to the x first
                // letters of the end mark
            	if (!(temp.equals(endMark.substring(0, temp.length())))) {
            		sb.append(temp);
            		temp = "";
            	} else if (temp.equals(endMark)) break;
            	c = in.read();
            }
		} catch (IOException e) {
			System.out.println("Error" + e);
		} finally {
			in.close();
		}
		return sb.toString();
	}
	
	/**
	 * Copy bytes from input to output, ignoring all occurrences of badByte.
	 * @param in
	 * @param out
	 * @param badByte
	 */
	public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
		try {
			int temp = in.read();
			while (temp != -1) {
				if ((byte)temp != badByte) {
					out.write((byte)(temp));
				}
				temp = in.read();
			}
		} catch (IOException e) {
			System.out.println("Error" + e);
		} finally {
			in.close();
		}
	}
	
	/**
	 * Read a 48-bit (unsigned) integer from the stream and return it. The number is represented as five bytes, 
	 * with the most-significant byte first. 
	 * If the stream ends before 5 bytes are read, return -1.
	 * @param in
	 * @return the number read from the stream
	 */
	public static long readNumber(InputStream in) throws IOException {
		long result = 0;
		try {
			for (int i = 0; i < 5; i++) {
				result <<= 8;
				byte temp = (byte)in.read();
				result += temp;
				if (temp == -1) return -1;
			}
		} catch (IOException e) {
			System.out.println("Error" + e);
		} finally {
			in.close();
		}
		return result;
	}
}
