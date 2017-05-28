
    /*
    By:
            Jeff Gyldenbrand - jegyl16
            Bjarke Holst Kreiberg - bjkre16
    */


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Encode 
{
	public static int n;
	public static FileInputStream inFile;
	public static FileOutputStream outFile;
	public static BitInputStream in;
	public static BitOutputStream out;
	public static PQ pq = new PQHeap(256);
	private static String[] codeList = new String[256]; // List of bit codes.
	public static int[] bitsArray = new int[256];		// Array to store frequencies
	public static String arg0;
	public static String arg1;
	
	
	public static void main(String[] args) throws Exception 
	{	
		// arguments from user
		arg0 = args[0];
		arg1 = args[1];
		scanFile();			
		huffman();
		bitToOutput();
		System.out.println("Encoding Complete!");
	}
	
	public static void scanFile() throws IOException
	{
		int q;
		int z;
		// loading file from path to input stream / output stream
		inFile = new FileInputStream(arg0);
		outFile = new FileOutputStream(arg1);
		in = new BitInputStream(inFile);
		out = new BitOutputStream(outFile);
		n = 0;
		
		//reads byte for byte from input stream and counts each byte that gets stored in bitsArray
		int content;
		while ((content = inFile.read()) != -1)
		{
			bitsArray[content] = bitsArray[content] + 1;
		}
		
		
//		Inserts new elements from bitsArray to Priority queue
		for(int i = 0; i < 256; i++)
		{
			z = i;
			q = bitsArray[i];
			pq.insert(new Element(q,z));
			n++;
		}

	}
	
	
	
	//Creates the huffman tree in Element-arrays.
	//Each array containing left and right element extracted from the priority queue.
	//storing the frequency as freg.
	//Inserts a new element of the freq as key, and the array as the data, to the priority queue.
	//Creates a new array arr, and runs search with extractmin one last time, the empty array and 0(counter).
	public static void huffman()
	{
		for(int i = 0; i < n-1; i++)
		{		
			Element[] elementNode = new Element[2];
			elementNode[0] = pq.extractMin();
			elementNode[1] = pq.extractMin();
			
			int freq = elementNode[0].key + elementNode[1].key;
			
			pq.insert(new Element(freq, elementNode));
		}
		
		int[] arr = new int[256];
		search(pq.extractMin(), arr, 0);
	}
	
	
	
	//Writes the frequency to ints in the output file.
	//Writes each byte as the new bitcode into the file bit by bit.
	public static void bitToOutput() throws IOException
	{		
		inFile = new FileInputStream(arg0);
		outFile = new FileOutputStream(arg1);
		int content;
		String strBit;
		String cont;
		
		for(int i = 0; i < bitsArray.length; i++ )
		{
			int x = bitsArray[i];
			out.writeInt(x);
		}
		
		
		
//		write file into bytes.
		while ((content = inFile.read()) != -1)
		{			
			cont = codeList[content];
			for(int i = 0; i < cont.length(); i++)
			{
				strBit = Character.toString(cont.charAt(i));
				int intBit = Integer.parseInt(strBit);
				out.writeBit(intBit);
			}			
		}
		
		out.writeBit(0);
		out.writeBit(1);
		
		in.close();
		out.close();		
	}

	
	
	//Checks if Element x is an Element
	public static boolean IsLeafNode(Element x) 
	{
		return !(x.data instanceof Element[]);
	}
	
	
	
	//searches through the entire Huffman tree for leaf nodes.
	//each time it goes left in the tree, it stores a 0 to the temporary array(arr)
	//and 1 if it goes right.
	//if a leaf-node is found, it save the bits from the temporary array to the codeList.
    public static void search(Element x, int arr[], int count) 
    {
        if (x == null) 
        {
        	System.out.println("Tree is empty");
            return;
        }
 
        if (IsLeafNode(x)) 
        {
        	StringBuilder strNum = new StringBuilder();
        	String bitValue;
        	for(int i = 0; i < count; i++ )
        	{
    			strNum.append(arr[i]);
        	}

        	bitValue = strNum.toString();        	
        	codeList[(int)x.data] = bitValue;
        	
        	return;
        }
        else 
        {
        	arr[count] = 0;
        	search(((Element[])x.data)[0], arr, count+1);
        	        	
        	arr[count] = 1;
        	search(((Element[])x.data)[1], arr, count+1);
        } 
    }
}


