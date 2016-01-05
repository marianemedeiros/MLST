package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

public class GrafoMatrizAdj implements Grafo<Vertice, Aresta<Vertice, Vertice>> {

	/*
	 * Inserir Vertice: mapear no HashMap. Inserir Aresta: mapear na Matriz.
	 */
	private int TamanhoMatrizAdj;
	private int TamanhoAtual = 0;

	private Integer[][] matrizAdj;
	private int intervalOfLabels; // intervalo válido que os pesos das arestas podem assumir.
	private ArrayList<Integer> labels;
	
	private int qtdComponentesConexas;
	
	private HashMap<Integer, Vertice> IntToVert = new HashMap<Integer, Vertice>();
	private HashMap<Vertice, Integer> VertToInt = new HashMap<Vertice, Integer>();
	
	
	public GrafoMatrizAdj(int dimensao) {
		this.setLabels(new ArrayList<Integer>());
		this.setTamanhoMatrizAdj(dimensao);
		this.setMatrizAdj(new Integer[dimensao][dimensao]);
	}

	/*
	 * Se não esta mapeado no hashMap, mapeia, se já esta lança excessão Só
	 * mapeia no HashMap IntToVert se a chave(TamanhoAtual) não estiver na
	 * estrutura, caso já esteja mapeando algum valor lança excessão.
	 */

	/**
	 * Função responsável por mapear um novo vertice ao HashMap.
	 * 
	 * @param vertice
	 *            vertice a ser adicionado ao HashMap
	 */
	public void mapeiaVertice(Vertice vertice) {
		if (!getIntToVert().containsKey(this.TamanhoAtual)
				&& !getVertToInt().containsValue(vertice)) {
			this.getIntToVert().put(TamanhoAtual, vertice);
			this.getVertToInt().put(vertice, TamanhoAtual);
			// System.out.println("key: " + this.VertToInt.get(vertice) +
			// "value: " + this.IntToVert.get(TamanhoAtual).getId());
			this.TamanhoAtual++;
		} else {
			throw new RuntimeException("A key " + this.TamanhoAtual
					+ "já está mapeada no HashMap.");
		}
	}

	public Iterator<Vertice> getVerticesAdjacentes(Vertice u) {
		if (u != null && this.getVertToInt().containsKey(u)) {
			ArrayList<Vertice> adjs = new ArrayList<Vertice>();
			int key = this.getVertToInt().get(u);
			/*
			 * Percorro o matriz na linha [key], variando a coluna, se
			 * matriz[key][i] == 1 adiciona em uma lista que contem os vertices
			 * adjacentes.
			 */
			for (int i = 0; i < this.TamanhoMatrizAdj; i++) {
				try {
					if (this.getMatrizAdj()[key][i] != null
							&& this.getMatrizAdj()[key][i] != this.TamanhoMatrizAdj) {
						adjs.add(this.getIntToVert().get(i));
					}
				} catch (NullPointerException e) {
					System.out.println("err: " + e);
				}
			}
			return adjs.iterator();
		} else {
			return null;
		}
	}

	public Iterator<Vertice> getVerticesAdjacentes2(Vertice u) {
		Integer v = Integer.parseInt(u.getId());
		if (u != null && this.getIntToVert().containsKey(v)) {
			ArrayList<Vertice> adjs = new ArrayList<Vertice>();
			int key = this.getVertToInt().get(u);
			/*
			 * Percorro o matriz na linha [key], variando a coluna, se
			 * matriz[key][i] == 1 adiciona em uma lista que contem os vertices
			 * adjacentes.
			 */
			for (int i = 0; i < this.TamanhoMatrizAdj; i++) {
				try {
					if (this.getMatrizAdj()[key][i] != null && this.getMatrizAdj()[key][i] != this.TamanhoMatrizAdj) {
						adjs.add(this.getIntToVert().get(i));
					}
					if (this.getMatrizAdj()[i][key] != null && this.getMatrizAdj()[i][key] != this.TamanhoMatrizAdj) {
						adjs.add(this.getIntToVert().get(i));
					}
				} catch (NullPointerException e) {
					System.out.println("err: " + e);
				}
			}
			return adjs.iterator();
		} else {
			return null;
		}
	}

	public Iterator<Vertice> getVertices() {
		return this.getVertToInt().keySet().iterator();
	}

	public Iterator<Aresta<Vertice, Vertice>> getArestas() {
		ArrayList<Aresta<Vertice, Vertice>> a = new ArrayList<Aresta<Vertice, Vertice>>();

		for (Entry<Integer, Vertice> v : this.getIntToVert().entrySet()) {
			Iterator<Vertice> vertAdjs = this.getVerticesAdjacentes(v
					.getValue());
			while (vertAdjs.hasNext()) {
				Vertice ver = vertAdjs.next();
				System.out.println("-- " + ver.getId());
				a.add(new Aresta<Vertice, Vertice>(v.getValue(), ver));
			}
		}
		return a.iterator();
	}

	public Vertice getVertice(String idVertice) {
		for (Entry<Vertice, Integer> v : this.getVertToInt().entrySet()) {
			if (v.getKey().getId().equals(idVertice)) {
				return v.getKey();
			}
		}
		return null;
	}

	public void adicionaVertice(Vertice verticeNoGrafo,
			Vertice verticeAdicionado, Integer peso) {
		/* verifica se o verticeNoGrafo esta mapeado na Matriz */
		Vertice v = getVertice(verticeNoGrafo.getId());
		if (v == null) {
			throw new RuntimeException("O vertice" + verticeNoGrafo
					+ "deve estar necessarimente no Grafo.");
		} else {
			if (getVertice(verticeAdicionado.getId()) == (null)) {
				if (this.IntToVert == null && this.VertToInt == null) {
					mapeiaVertice(verticeAdicionado);
				}
			}
			/* Insere na MatrizAdj */
			this.getMatrizAdj()[this.getVertToInt().get(verticeNoGrafo)][this.getVertToInt().get(verticeAdicionado)] = peso;
			this.getMatrizAdj()[this.getVertToInt().get(verticeAdicionado)][this.getVertToInt().get(verticeNoGrafo)] = peso;
		}
	}

	public void adicionaVertice(Vertice verticeAdicionado) {
		/*
		 * Mapeia no HashMap, mas não coloca na matriz pois nao tem vertice
		 * adjacente.
		 */
		Vertice v = getVertice(verticeAdicionado.getId());
		if (v == null) {
			if (getVertice(verticeAdicionado.getId()) == (null)) {
				if (this.IntToVert == null && this.VertToInt == null) {
					mapeiaVertice(verticeAdicionado);
				}
			}
		} else {
			System.out.println("Este vértice já foi inserido no grafo");
			verticeAdicionado = v;
		}
	}

	public void adicionaAresta(Aresta<Vertice, Vertice> arestaAdicionada,
			Integer peso) {
		/*
		 * Uma aresta é composta por dois vértices (V1 e V2) 1 - V1 e V2 não
		 * estão na hash. 2 - V1 e V2 estão na hash. 3 - V1 não esta na hash. 4
		 * - V2 não esta na hash.
		 */
		Vertice v1 = arestaAdicionada.getVertice1();
		Vertice v2 = arestaAdicionada.getVertice2();
		System.out.println("v1 ->" + v1.getId() + "v2 -> " + v2.getId());

		/*
		 * Caso 1. Adiciona os vertices no hash, e adiciona uma aresta entre
		 * eles(marca 1 na matrizAdj).
		 */
		if (this.getVertice(v1.getId()) == null
				&& this.getVertice(v2.getId()) == null) {
			/*
			 * Adiciona V1 ao hash, depois com V1 já no hash, adiciona v2
			 * adjacente a v1.
			 */
			this.adicionaVertice(v1);
			this.adicionaVertice(v1, v2, peso);
		}
		/* Caso 2. Apenas adiciona uma aresta entre os vertices. */
		else if (this.getVertice(v1.getId()) != null && this.getVertice(v2.getId()) != null) {
			// System.out.println("if2");
			this.getMatrizAdj()[this.getVertToInt().get(v1)][this.getVertToInt().get(v2)] = peso;
		}
		/* Case 3 e 4. */
		else if (this.getVertice(v1.getId()) == null
				&& this.getVertice(v2.getId()) != null) {
			// System.out.println("if34");
			this.adicionaVertice(v2, v1, peso);
		} else if (this.getVertice(v1.getId()) != null
				&& this.getVertice(v2.getId()) == null) {
			System.out.println("if4");
			this.adicionaVertice(v1, v2, peso);
		}

	}

	public int connectedComponent() {
		int id = 0;

		for (Vertice vertice : this.IntToVert.values()) {
			if (vertice.getCompConexa() == -1) {
				dfs2Cc(vertice, id++);
			}
		}

		return id;
	}

	/**
	 * Busca em profundidade que verifica quantas compenentes há no grafo.
	 * 
	 * @param vertice
	 * @param id
	 */
	private void dfs2Cc(Vertice vertice, int id) {
		vertice.setCompConexa(id);

		Iterator<Vertice> it = this.getVerticesAdjacentes2(vertice);
		while (it.hasNext()) {
			Vertice aux = it.next();

			if (aux.getCompConexa() == -1) {
				dfs2Cc(aux, id);
			}
		}
	}

	public int getTamanhoMatrizAdj() {
		return TamanhoMatrizAdj;
	}

	private void setTamanhoMatrizAdj(int tamanhoMatrizAdj) {
		TamanhoMatrizAdj = tamanhoMatrizAdj;
	}

	public HashMap<Integer, Vertice> getIntToVert() {
		return IntToVert;
	}

	public void setIntToVert(HashMap<Integer, Vertice> intToVert) {
		IntToVert = intToVert;
	}

	public HashMap<Vertice, Integer> getVertToInt() {
		return VertToInt;
	}

	public void setVertToInt(HashMap<Vertice, Integer> vertToInt) {
		VertToInt = vertToInt;
	}

	public void mapOnMatrix(Vertice vertice1, Vertice vertice2, int peso) {
		this.getMatrizAdj()[Integer.parseInt(vertice1.getId())][Integer
				.parseInt(vertice2.getId())] = peso;
	}

	public Integer[][] getMatrizAdj() {
		return matrizAdj;
	}

	public void setMatrizAdj(Integer[][] matrizAdj) {
		this.matrizAdj = matrizAdj;
	}

	public int getIntervalOfLabels() {
		return intervalOfLabels;
	}

	public void setIntervalOfLabels(int intervalOfLabels) {
		this.intervalOfLabels = intervalOfLabels;
	}

	public ArrayList<Integer> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<Integer> labels) {
		this.labels = labels;
	}

	public int getQtdComponentesConexas() {
		return qtdComponentesConexas;
	}

	public void setQtdComponentesConexas(int qtdComponentesConexas) {
		this.qtdComponentesConexas = qtdComponentesConexas;
	}



}
