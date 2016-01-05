package matrix2graph;

import grafo.GrafoMatrizAdj;
import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

import java.util.ArrayList;
import java.util.HashMap;


public class Matrix2Graph {
	private ArrayList<Integer[][]> listOfMatrix;
	private int intervalOfWeight;
	private ArrayList<Grafo<Vertice, Aresta<Vertice, Vertice>>> listOfGraph;

	public Matrix2Graph(ArrayList<Integer[][]> listOfMatrix,
			int intervalOfWeight) {
		this.listOfMatrix = listOfMatrix;
		this.setIntervalOfWeight(intervalOfWeight);
		this.setListOfGraph(new ArrayList<Grafo<Vertice, Aresta<Vertice, Vertice>>>());
	}

	/**
	 * Seta nos HashMaps do grafo passado por parêmetro os vértices da matriz
	 * (os vértices são identificados pelas posições da matriz, ou seja, se a
	 * matriz possuir dimensão 5, o grafo terá 5 vértices).
	 * 
	 * @param grafo
	 *            , onde será setado o HashMap
	 * @return o grafo com o HashMap setado nos valores desejados.
	 */
	private Grafo<Vertice, Aresta<Vertice, Vertice>> setOnHashMap(
			Grafo<Vertice, Aresta<Vertice, Vertice>> grafo) {
		HashMap<Integer, Vertice> intToVert = new HashMap<Integer, Vertice>();
		HashMap<Vertice, Integer> vertToInt = new HashMap<Vertice, Integer>();

		for (Integer i = 0; i < grafo.getTamanhoMatrizAdj(); i++) {
			Vertice newVertice = new Vertice();
			newVertice.setId(i.toString());

			intToVert.put(i, newVertice);
			vertToInt.put(newVertice, i);
		}

		grafo.setIntToVert(intToVert);
		grafo.setVertToInt(vertToInt);
		return grafo;
	}

	/**
	 * Monta o grafo de acordo com a matrix fornecida por parâmetro.
	 * 
	 * @param matrix
	 *            , que representa o grafo.
	 */
	private void montaGraph(Integer[][] matrix) {
		Grafo<Vertice, Aresta<Vertice, Vertice>> grafo = new GrafoMatrizAdj(matrix.length);
		grafo.setIntervalOfLabels(this.intervalOfWeight);
		grafo = setOnHashMap(grafo);

		for (Integer l = 0; l < grafo.getTamanhoMatrizAdj(); l++) {
			for (Integer c = 0; c < grafo.getTamanhoMatrizAdj(); c++) {
				if (matrix[l][c] != null && matrix[l][c] != this.getIntervalOfWeight()) {
					grafo.mapOnMatrix(grafo.getIntToVert().get(l), grafo.getIntToVert().get(c), matrix[l][c]);
					Integer x = new Integer(matrix[l][c]);
					grafo.getLabels().add(x);
				}else{
					grafo.getMatrizAdj()[l][c] = grafo.getIntervalOfLabels();
				}
			}
		}
		
		this.getListOfGraph().add(grafo);
	}

	public void geraGraph() {
		for (Integer[][] element : this.listOfMatrix) {
			montaGraph(element);
		}
	}

	ArrayList<Integer[][]> getListOfMatrix() {
		return listOfMatrix;
	}

	void setListOfMatrix(ArrayList<Integer[][]> listOfMatrix) {
		this.listOfMatrix = listOfMatrix;
	}

	public ArrayList<Grafo<Vertice, Aresta<Vertice, Vertice>>> getListOfGraph() {
		return listOfGraph;
	}

	public void setListOfGraph(
			ArrayList<Grafo<Vertice, Aresta<Vertice, Vertice>>> listOfGraph) {
		this.listOfGraph = listOfGraph;
	}

	public int getIntervalOfWeight() {
		return intervalOfWeight;
	}

	public void setIntervalOfWeight(int intervalOfWeight) {
		this.intervalOfWeight = intervalOfWeight;
	}

}
