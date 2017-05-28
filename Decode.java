
    /*
    By:
            Jeff Gyldenbrand - jegyl16
            Bjarke Holst Kreiberg - bjkre16
    */


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.IntStream;

public class Decode
{
	public static int[] bitsArray = new int[256];	// Array to store frequencies
	public static int n;
	public static FileInputStream inFile;
	public static FileOutputStream outFile;
	public static BitInputStream in;
	public static BitOutputStream out;
	public static PQ pq = new PQHeap(256);
	public static Element ex;
	public static int counter = 0;
	public static String arg0;
	public static String arg1;
	
	public static void main(String[] args) throws Exception 
	{	
		arg0 = args[0];
		arg1 = args[1];
		scanFile();	
		in.close();
		out.close();

		System.out.println("Decoding Complete!");
	}
	
	//Reads the first 256 ints and stores them to bitsArray, that is our frequency
	public static void scanFile() throws IOException
	{
		int q;
		int z;
		
		inFile = new FileInputStream(arg0);
		outFile = new FileOutputStream(arg1);
		in = new BitInputStream(inFile);
		out = new BitOutputStream(outFile);
		n = 0;
		int count = 0;

		while (count < 256)
		{
			int x = in.readInt();
			bitsArray[count] = x;
			count++;
		}
		
//		Inserts elements from byteArray to priority queue
		for(int i = 0; i < 256; i++)
		{
			z = i;
			q = bitsArray[i];
			pq.insert(new Element(q,z));
			n++;
		}
		
		huffman();
		
	}

	
	//Creates the huffman tree in Element-arrays.
	//Each array containing left and right element extracted from the priority queue.
	//storing the frequency as freg.
	//Inserts a new element of the freq as key, and the array as the data, to the priority queue.
	//Creates a new array arr, and runs search with extractmin one last time, the empty array and 0(counter).
	public static void huffman() throws IOException
	{
		for(int i = 0; i < n-1; i++)
		{		
			Element[] elementNode = new Element[2];
			elementNode[0] = pq.extractMin();
			elementNode[1] = pq.extractMin();
			
			int freq = elementNode[0].key + elementNode[1].key;
			
			pq.insert(new Element(freq, elementNode));
		}
		ex = pq.extractMin();
		search(ex);		
	}
	
	//Checks if Element x is an Element
	public static boolean IsLeafNode(Element x) 
	{
		return !(x.data instanceof Element[]);
	}
	
	
	
	//Searches down the Huffman tree bit by bit, if 0 go left, if 1 go right.
	//if leafnode, write the byte to output stream and set current element to root element(x)
	//as long as counter is less than the sum of frequencies and bit value is not -1
	//Each times it goes left or right current element gets updated to leaf element.
    public static void search(Element x) throws IOException 
    {
    	String str;
    	int bit = 0;
    	int result;
    	Element current = x;
    	int sum = IntStream.of(bitsArray).sum();
    	while ( bit != -1 && counter < sum)
    	{
	    	bit = in.readBit();
	    	
	        if (IsLeafNode(current)) 
	        {
	        	counter++;
	        	str = current.data.toString();	        	
	        	result = Integer.parseInt(str);
	        	outFile.write(result);
	        	current = x;	
	        	
	        }
	        if(bit == 0)
	        {
	        	
	        	current = ((Element[])current.data)[0];
	        	
	        }
	        if(bit == 1)
	        {
	        	
	        	current = ((Element[])current.data)[1];
	        	
	        }
    	}
      
    }
	
}
