import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LRRR
 */
public class Decode {

    	public static int n;
	public static FileInputStream inFile;
	public static FileOutputStream outFile;
	public static BitInputStream in;
	public static BitOutputStream out;
	public static PQ pq = new PQHeap(256);
	private static String[] codeList = new String[256];
        
        public static void main(String[] args) throws IOException {
            scanFile();
            huffman();
        }

    	public static void scanFile() throws IOException
	{
		int q;
		int z;
		int[] bitsArray = new int[256];
		inFile = new FileInputStream("C:\\Users\\LRRR\\Documents\\NetBeansProjects\\Algoritmer2017\\src\\test\\output.txt");
		outFile = new FileOutputStream("C:\\Users\\LRRR\\Documents\\NetBeansProjects\\Algoritmer2017\\src\\test\\restored.txt");
		in = new BitInputStream(inFile);
		out = new BitOutputStream(outFile);
		n = 0;

		int content;
		while ((content = inFile.read()) != -1)
		{
			bitsArray[content] = bitsArray[content] + 1;
		}

//		Inserts elements from byteArray
//		for(int i = 0; i < 256; i++)
//		{
//			z = i;
//			q = bitsArray[i];
//			pq.insert(new Element(q,z));
//			n++;
//		}
	}

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
                	public static boolean IsLeafNode(Element x) 
	{
		return !(x.data instanceof Element[]);
	}

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
