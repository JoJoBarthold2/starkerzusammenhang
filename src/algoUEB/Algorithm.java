package algoUEB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Algorithm {

	int[] visited;
	List<Integer> results; // one component graph
	int[][] matrix; // the adjacency matrix we look at
	int n; // the number of vertices;
	int[][] reverseMatrix; // the reversed matrix
	Stack<Integer> finishTime; // the vertices in finishing time order

	int[][] readCSV(String filepath) {

		int lines = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) // count how many lines we have in the doc
		{
			while (reader.readLine() != null) {
				lines++;
			}
		}
		
		catch(IOException e)
		{e.printStackTrace();}

		int[][] records = new int[lines][lines];

		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			int count =0;

			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				for (int i = 0; i < lines; i++) {
					records[count][i] = Integer.parseInt(values[i]);
				}
				count++;
			}
		}
		
		catch(IOException e)
		{e.printStackTrace();}
		return records;
	}

	void dfstimed() // dfs with finishing time 
	{
    for(int i = 0; i < n; i++) {
    	if(visited[i] == 0) {
    		visitTimed(i);
    	}
    }
	}
	
	
	void visitTimed(int u) {
		visited[u] = 1;
		for(int i = 0; i < n; i++) {
			if(matrix[u][i] == 1 && visited[i] == 0) {
				visitTimed(i);
			}
		}
		visited[u] = 2;
		finishTime.push(u);
	}

	int[][] computeReverseMatrix() // returns the reverse matrix
	{

		int[][] ReverseMatrix = new int[n][n]; // is initialised with 0 so we don't have to change anything in this base case
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 1) {
					reverseMatrix[j][i] = 1;
				}
			}
		}
		return reverseMatrix;
	}
	

	List<Integer> makeComponent(int start) {

		results = new ArrayList<Integer>();
		results.add(start);
		visited[start] = 1; // 1 is our grey color, 0 is our white color
		for (int i = 0; i < n; i++) {
			if (reverseMatrix[start][i] == 1 && visited[i] == 0) {
				results.add(i);
				visitComponent(i);
			}
		}
		return results;
	}

	void visitComponent(int v)// recursive function to conduct DFS
	{
		visited[v] = 1;
		for (int i = 0; i < n; i++) {
			if (reverseMatrix[v][i] == 1 && visited[i] == 0) {
				results.add(i);
				visitComponent(i);
			}
		}
		visited[v] = 2;
	}

	List<List<Integer>> SCC(String filepath) // returns a list of integer lists that can be printed out , these represent the
												// strongly connected components
	{
		List<List<Integer>> finalresult = new ArrayList<>();

		matrix = readCSV(filepath);
		n = matrix.length;
		visited = new int[n];
		finishTime = new Stack<Integer>();
		dfstimed();
		reverseMatrix = computeReverseMatrix();
		visited = new int[n];
		while (!finishTime.empty()) {
			int v = finishTime.pop();
			if (visited[v] == 0) {
				makeComponent(v);
				finalresult.add(results);
			}
		}

		return finalresult;
	}
}
