package submission;

import java.util.Stack;
import java.io.*;

class SCC {

	static Stack<String> DFSOrder = new Stack<>();
	static Stack<String> SCC = new Stack<>();

	/**
	 * DFS in a connected component
	 * 
	 * @param adj adjacency matrix of the graph
	 * @param x the vertices to start with
	 * @param visitedVertices
	 */
	static void dfsHelp(int[][] adj, int x, boolean[] visitedVertices) {

		visitedVertices[x] = true;

		for (int i = 0; i < adj[x].length; i++) {
			if (adj[x][i] == 1 && (!visitedVertices[i])) {
				dfsHelp(adj, i, visitedVertices);
			}
		}

		DFSOrder.push(String.valueOf(x));
		SCC.push(String.valueOf(x));

	}

	/** jump to another vertice, where there is no edge to
	 * and go on with DFS
	 * 
	 * @param adj adjacency matrix of the graph
	 */
	static void dfsPrint(int[][] adj) {

		boolean[] visited = new boolean[adj.length];

		for (int i = 0; i < adj.length; i++) {
			if (!visited[i]) {
				dfsHelp(adj, i, visited);
			}
		}
		SCC.clear();
	}
	
	/** transpose a directed graph
	 * 
	 * @param adj adjacency matrix of the graph
	 */
	static void transposeGraph(int[][] adj) {
		
		boolean[][] transposed = new boolean[adj.length][adj.length];
		
		for (int i = 0; i < adj.length; i++) {
			for (int j = 0; j < adj.length; j++) {
				if (adj[i][j] == 1 && (!transposed[i][j]) && adj[j][i] == 0) {
					adj[i][j] = 0;
					adj[j][i] = 1;
					transposed[j][i] = true;
				}
			}
		}
	}
	
	/** DFS using the computed finishing times as the order of vertices in the main loop
	 * 
	 * @param adj adjacency matrix of the graph
	 */
	static void dfsPrint2(int[][] adj) {

		boolean[] visited = new boolean[adj.length];

		while (!DFSOrder.isEmpty()) {
			String ii = DFSOrder.pop();
			int i = Integer.valueOf(ii);
			if (!visited[i]) {
				dfsHelp(adj, i, visited);
				System.out.println("strongly connected component: " + SCC.toString());
				SCC.clear();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		
		String fileName = "/Users/johannaheiss/Desktop/big_graph.csv";
		String currentLine;
		FileInputStream input;
		try {
			input = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			input = null;
			e.printStackTrace();
		}
		DataInputStream myInput = new DataInputStream(input);
		int l = 0;
		String[][] data = new String[0][];
		
		while ((currentLine = myInput.readLine()) != null) {
			++l;
			String[][] newData = new String[l][2];
			String arr[] = currentLine.split(",");
			newData[l-1] = arr;
		
		System.arraycopy(data,  0,  newData,  0,  l-1);
		data = newData;
		}
		
		int[][] adj = new int[data.length][data[0].length];
		
		for (int i = 0; i < adj.length; i++) {
			for (int j = 0; j < adj[0].length; j++) {
				adj[i][j] = Integer.parseInt(data[i][j]);
			}
		}
		
		dfsPrint(adj);
		//System.out.println("DFS: " + DFSOrder.toString());
		
		transposeGraph(adj);
		dfsPrint2(adj);
		//System.out.println("DFSTransposedGraph: " + DFSOrder.toString());
		

		//examples 
		/*
		int[][] g = new int[4][4];
		g[0][1] = 1;
		g[2][0] = 1;
		g[2][1] = 1;
		g[2][3] = 1;

		transposeGraph(g);
		dfsPrint(g);
		*/
		/*
		int[][] g1 = new int[6][6];
		g1[0][3] = 1;
		g1[0][1] = 1;
		g1[3][1] = 1;
		g1[1][2] = 1;
		g1[4][2] = 1;
		g1[4][5] = 1;
		g1[5][5] = 1;
		g1[2][3] = 1;

		transposeGraph(g1);
		dfsPrint(g1);
		*/
		/*
		int[][] g2 = new int[7][7];
		g2[0][1] = 1;
		g2[1][2] = 1;
		g2[2][0] = 1;
		g2[2][3] = 1;
		g2[3][4] = 1;
		g2[4][3] = 1;
		g2[4][5] = 1;
		g2[6][5] = 1;
		
		dfsPrint(g2);
		//System.out.println("DFS: " + DFSOrder.toString());
		
		transposeGraph(g2);
		dfsPrint2(g2);
		//System.out.println("DFSTransposedGraph: " + DFSOrder.toString());
		*/

	}
}
