package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public interface Grafo<V extends Vertice, A extends Aresta<V, V>> {
	public final int NAO_DIRECIONADO = 0;
	public final int DIRECIONADO = 1;
	public final int PONDERADO = 2;
	public final int DIRECIONADO_PONDERADO = 3;

	public Iterator<V> getVerticesAdjacentes(V u);

	public Iterator<V> getVertices();

	public Iterator<A> getArestas();

	public V getVertice(String idVertice);

	public void setIntToVert(HashMap<Integer, Vertice> intToVert);

	public HashMap<Integer, Vertice> getIntToVert();

	public HashMap<Vertice, Integer> getVertToInt();

	public void setVertToInt(HashMap<Vertice, Integer> vertToInt);

	public Integer[][] getMatrizAdj();

	public void setMatrizAdj(Integer[][] matrizAdj);

	public int getTamanhoMatrizAdj();

	public int getIntervalOfLabels();

	public void setIntervalOfLabels(int intervalOfLabels);
	
	public ArrayList<Integer> getLabels();
	
	public void setLabels(ArrayList<Integer> labels);
	
	public int getQtdComponentesConexas();
	
	public void setQtdComponentesConexas(int qtdComponentesConexas);

	/**
	 * Calcula quantos componentes conexos há no grafo.
	 * 
	 * @return quantidade de componentes conexos do grafo.
	 */
	public int connectedComponent();

	/**
	 * Verifica os veriticas adjacentes verificando [key][i] e [i][key]
	 */
	public Iterator<Vertice> getVerticesAdjacentes2(Vertice u);

	/**
	 * Obtem os valores de id contido no hashMap do vertice1 e vertice2 e mapeia
	 * na matriz do grafo.
	 * 
	 * @param vertice1
	 *            já esta no grafo
	 * @param vertice2
	 *            já está no grafo
	 */
	public void mapOnMatrix(V vertice1, V vertice2, int peso);

	/**
	 * Adiciona o vértice <code>verticeAdicionado</code> ajdacente ao vértice
	 * <code>verticeNoGrafo</code> que já está no grafo. Necessariamente, o
	 * vértice <code>verticeNoGrafo</code> precisa estar no grafo
	 * 
	 * @param verticeNoGrafo
	 *            Vértice que já está no grafo
	 * @param verticeAdicionado
	 *            Vértice sendo adicionado no grafo
	 */
	public void adicionaVertice(V verticeNoGrafo, V verticeAdicionado,
			Integer peso);

	/**
	 * Adiciona um vértice <code>verticeAdicionado</code> ao grafo. O vértice
	 * ficará sem nenhum outro vértice adjacente.
	 * 
	 * @param verticeAdicionado
	 *            Vértice sendo adicionado ao grafo
	 */
	public void adicionaVertice(V verticeAdicionado);

	/**
	 * Adiciona uma aresta ao grafo. Se algum dos vértices da aresta adicionada
	 * já estiverem no grafo eles são sobrepostos
	 * 
	 * @param arestaAdicionada
	 *            Aresta adicionada ao grafo
	 */
	public void adicionaAresta(A arestaAdicionada, Integer peso);
}
