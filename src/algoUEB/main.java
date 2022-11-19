package algoUEB;

import java.util.List;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
 Algorithm  test = new Algorithm();
 List<List<Integer>> myList = test.SCC("C:/Users/valen/OneDrive/Desktop/small_graph.csv");// use this to test
 test.printComponents(myList);
   test = new Algorithm();
 List<List<Integer>> biglist = test.SCC("C:/Users/valen/OneDrive/Desktop/big_graph.csv");
 test.printComponents(biglist);
 
	}

}
