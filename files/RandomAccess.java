package files;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
Assignment number : 2.3
File Name : RandomAccess.java
Name : Ilay Serr
Email : ilay92@gmail.com
*/

public class RandomAccess {	
	/**
	 * Treat the file as an array of (unsigned) 8-bit values and sort them 
	 * in-place using a bubble-sort algorithm.
	 * You may not read the whole file into memory! 
	 * @param file
	 */
	public static void sortBytes(RandomAccessFile file) throws IOException {
		try {
			for (int i = 0; i < file.length(); i++) {
				for (int j = 1; j < file.length() - i; j++) { 
					file.seek(j - 1);
					int l = file.read();
					int r = file.read();
					if (l > r) {
				        file.seek(j - 1);   
			            file.write(r);
			            file.write(l);
					}
				}
      		} 
		} catch (IOException e) {
			System.out.println("Error" + e);
		}	
	}
	
	/**
	 * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
	 * them in-place using a bubble-sort algorithm. 
	 * You may not read the whole file into memory! 
	 * @param file
	 * @throws IOException
	 */
	public static void sortTriBytes(RandomAccessFile file) throws IOException {
		try {
			for (int i = 0; i < file.length(); i += 3) {
				for (int j = 3; j < file.length() - i; j += 3) { 
					file.seek(j - 3);
					
					// get the first 24 bits in the correct order
					int l = file.read();
					l <<= 8;
					l += (byte)file.read();
					l <<= 8;
					l += (byte)file.read();
					
					// // get the second 24 bits in the correct order
					int r = file.read();
					r <<= 8;
					r += (byte)file.read();
					r <<= 8;
					r += (byte)file.read();
					if(l > r) {
				        file.seek(j - 3);   
			            file.write(r >> 16);
			            file.write(r >> 8);
			            file.write(r);
			            file.write(l >> 16);
			            file.write(l >> 8);
			            file.write(l);
					}
				}
      		} 
		} catch (IOException e) {
			System.out.println("Error" + e);
		}	
	}
	
	/**
	 * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
	 * them in-place using a quicksort algorithm. 
	 * You may not read the whole file into memory! 
	 * @param file
	 * @throws IOException
	 */
	
	public static void sortTriBytesQuick(RandomAccessFile file) throws IOException {   
        if (file == null || file.length() == 0) {
        	return;
        }
        quickSort(0, (int)file.length() - 3 , file);
    }
	
    private static void quickSort(int lowerIndex, int higherIndex ,  RandomAccessFile file) {
        int i = lowerIndex;
        int j = higherIndex;
        
        try {
        
	        file.seek((lowerIndex + (higherIndex - lowerIndex) / 2) + 2);
	        int pivot = file.read();
			pivot <<= 8;
			pivot += (byte)file.read();
			pivot <<= 8;
			pivot += (byte)file.read();
	        while (i <= j) {
	        	file.seek(i);
	        	int l = file.read();
				l <<= 8;
				l += (byte)file.read();
				l <<= 8;
				l += (byte)file.read();
	            while (l < pivot) {
	            	i += 3;
	            	file.seek(i);
	            	l = file.read();
					l <<= 8;
					l += (byte)file.read();
					l <<= 8;
					l += (byte)file.read();
	            }
	        	file.seek(j);
				int r = file.read();
				r <<= 8;
				r += (byte)file.read();
				r <<= 8;
				r += (byte)file.read();
	            while (r > pivot) {
	                j -= 3;
	                if (j > i) {
		                file.seek(j);
						r = file.read();
						r <<= 8;
						r += (byte)file.read();
						r <<= 8;
						r += (byte)file.read();
	                }
	            }
	            if (i <= j) {
	            	file.seek(i);
	                int temp = file.read();
					temp <<= 8;
					temp += (byte)file.read();
					temp <<= 8;
					temp += (byte)file.read();
	                file.seek(j);
	                int w = file.read();
					w <<= 8;
					w += (byte)file.read();
					w <<= 8;
					w += (byte)file.read();
	                file.seek(i);
		            file.write(w >>> 16);
		            file.write(w >>> 8);
		            file.write(w);
	                file.seek(j);
		            file.write(temp >>> 16);
		            file.write(temp >>> 8);
		            file.write(temp);
	                i += 3;
	                j -= 3;
	            }
	        }
	        if (lowerIndex < j)
	            quickSort(lowerIndex, j , file);
	        if (i < higherIndex)
	            quickSort(i, higherIndex , file);
        } catch (IOException e) {
        	System.out.println("Error" + e);
        }	
    }
}
	 
