package vns;

import java.util.ArrayList;
import java.util.Random;

import grafo.*;

public class VNS1 {
	
	private Grafo<Vertice, Aresta<Vertice,Vertice>> grafoOriginal; 
	
	private Integer[] C;
	private Grafo<Vertice, Aresta<Vertice,Vertice>> HC;

	public VNS1(Grafo<Vertice, Aresta<Vertice,Vertice>> g){
		this.grafoOriginal = g;
		this.grafoOriginal.setQtdComponentesConexas(this.grafoOriginal.connectedComponent());
		
		this.setC(new Integer[g.getIntervalOfLabels()]);
		this.setHC(new GrafoMatrizAdj(this.grafoOriginal.getTamanhoMatrizAdj()));
	}
	
	public void vns(long inicio){
		long time = 0;
		
		Integer[] CL = new Integer[this.getC().length];
		Grafo<Vertice, Aresta<Vertice,Vertice>> HL;
		
		this.C = generateRandomSolution(this.getC());
		
		int c = 0;
		
		do{
			float k = 1;
			float a1 = (float) getUsedLabels(this.getC());
			float a2 = (float) ((float)getUsedLabels(this.getC()));
			float kmax = a1 + (a2/3);
			
			while(k < kmax){
				CL = shakingPhase(k);
				
				CL = busaLocal(CL);
				
				this.setHC(updateSolution(C));
				int ccC = this.getHC().connectedComponent();
				int ccCL = updateSolution(CL).connectedComponent();
				if(ccCL < ccC){
				//if(getUsedLabels(CL) < getUsedLabels(getC())){
					this.setC(CL);
					k = 1;
					a1 = (float) getUsedLabels(this.getC());
					a2 = (float) ((float)getUsedLabels(this.getC()));
					kmax = a1 + (a2/3);
				}else{
					k++;
				}
			}
			c = updateSolutionC(C).connectedComponent();
			
			time = (System.currentTimeMillis() - inicio);
			if(time > 180000){
				System.err.println("NF");
				break;
			}
		}while(c != this.grafoOriginal.getQtdComponentesConexas() || time > 180000);
		
		
		//
		//termination conditions
	}



	private Grafo<Vertice, Aresta<Vertice, Vertice>> updateSolutionC(Integer[] conjunto) {
		Grafo<Vertice, Aresta<Vertice,Vertice>> temp = new GrafoMatrizAdj(this.grafoOriginal.getTamanhoMatrizAdj());
		
		for (Integer element : this.grafoOriginal.getIntToVert().keySet()) {
			Vertice n = new Vertice(element.toString());
			temp.getIntToVert().put(element, n);
			temp.getVertToInt().put(n, element);
		}
		
		for (int k = 0; k < conjunto.length; k++) {
			if(conjunto[k] != null && conjunto[k] == 1){
				temp.getLabels().add(k);
				
				for (Integer i = 0; i < this.grafoOriginal.getTamanhoMatrizAdj(); i++) {
					for (Integer j = 0; j < this.grafoOriginal.getTamanhoMatrizAdj(); j++) {
						if(this.grafoOriginal.getMatrizAdj()[i][j] == k){
							temp.getMatrizAdj()[i][j] = this.grafoOriginal.getMatrizAdj()[i][j]; 
						}
					}
				}
			}
		}
		return temp;
	}



	private Integer[] busaLocal(Integer[] cl) {
		Grafo<Vertice, Aresta<Vertice,Vertice>> hl = updateSolution(cl);
		int cc = hl.connectedComponent();
		
		while(cc > 1){
			ArrayList<Integer> S = formaS(cl, cc);
			
			if(!S.isEmpty()){
				Random r = new Random();
				Integer rnd = r.nextInt(S.size());
				cl[S.get(rnd)] = 1;
				hl = updateSolution(cl);
				cc = hl.connectedComponent();
			}else{
				//System.err.println("Conjunto S vazio");
				break;
			}
			
		}
		
		for (int i = 0; i < cl.length; i++) {
			if(cl[i] != null && cl[i] == 1){
				cl[i] = null;
				hl = excluirLabel(hl,i);
				hl.setQtdComponentesConexas(hl.connectedComponent());
				if(hl.getQtdComponentesConexas() > 1){
					cl[i] = 1;
					hl = updateSolution(cl);
					hl.setQtdComponentesConexas(hl.connectedComponent());
				}
			}
		}
		return cl;
	}

	private ArrayList<Integer> formaS(Integer[] conjunto, int compC) {
		ArrayList<Integer> u = new ArrayList<Integer>();
		for (Integer e : this.grafoOriginal.getLabels()) {
			if(conjunto[e] == null && !u.contains(e)){
				u.add(e);
			}
		}
		
		if(!u.isEmpty()){
			for (Integer e : u) {
				conjunto[e] = 1;
				Grafo<Vertice, Aresta<Vertice,Vertice>> g = updateSolution(conjunto);
				conjunto[e] = null;
				if(g.connectedComponent() > compC){
					u.remove(e);
					break;
				}
			}
		}
		
		return u;
	}

	private Grafo<Vertice, Aresta<Vertice, Vertice>> excluirLabel(Grafo<Vertice, Aresta<Vertice, Vertice>> gl, Integer label) {
		Grafo<Vertice, Aresta<Vertice,Vertice>> temp = new GrafoMatrizAdj(this.grafoOriginal.getTamanhoMatrizAdj());
		
		for (Integer element : gl.getIntToVert().keySet()) {
			Vertice n = new Vertice(element.toString());
			temp.getIntToVert().put(element, n);
			temp.getVertToInt().put(n, element);
		}
		
		temp.setMatrizAdj(gl.getMatrizAdj());
		temp.setLabels(gl.getLabels());
		temp.getLabels().remove(label);
		for (int i = 0; i < temp.getTamanhoMatrizAdj(); i++) {
			for (int j = 0; j < temp.getTamanhoMatrizAdj(); j++) {
				if(temp.getMatrizAdj()[i][j] == label){
					temp.getMatrizAdj()[i][j] = null;
				}
			}
		}
		
		return temp;
	}


	private Integer[] shakingPhase(float k) {
		Integer[] cl = this.getC().clone();
		
		
		for (int i = 0; i < k; i++) {
			Random r = new Random();
			Integer rnd = r.nextInt(10);
			if(rnd <= 5){
				//System.err.println("<!>");
				int indice = selectElement(cl);
				if(indice != -1){
					cl[indice] = null;
				}
			}else{
				int indice = selectUnusedLabel(cl);
				if(indice != -1){
					cl[indice] = 1;
			}
			}
		}
		return cl;
	}

	private int selectUnusedLabel(Integer[] cl) {
		ArrayList<Integer> u = new ArrayList<Integer>();
		
		for (Integer e : this.grafoOriginal.getLabels()) {
			if(cl[e] == null){
				u.add(e);
			}
		}
		if(u.isEmpty()){
			return -1;
		}
		Random r = new Random();
		Integer rnd = r.nextInt(u.size());
		
		return u.get(rnd);
	}

	private int selectElement(Integer[] cl) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < cl.length; i++) {
			if(cl[i] != null && cl[i] == 1){
				l.add(i);
			}
		}
		if(l.isEmpty()){
			return -1;
		}
		Random r = new Random();
		Integer rnd = r.nextInt(l.size());
		return l.get(rnd);
	}

	public int getUsedLabels(Integer[] c) {
		int cont = 0;
		for (int i = 0; i < c.length; i++) {
			if(c[i] != null && c[i] == 1){
				cont++;
			}
		}
		return cont;
	}

	private Grafo<Vertice, Aresta<Vertice, Vertice>> updateSolution(Integer[] conjunto) {
		Grafo<Vertice, Aresta<Vertice,Vertice>> newGraph = new GrafoMatrizAdj(this.grafoOriginal.getTamanhoMatrizAdj());
		
		for (Integer element : this.grafoOriginal.getIntToVert().keySet()) {
			Vertice n = new Vertice(element.toString());
			newGraph.getIntToVert().put(element, n);
			newGraph.getVertToInt().put(n, element);
		}
		
		for (int k = 0; k < conjunto.length; k++) {
			if(conjunto[k] != null && conjunto[k] == 1){
				newGraph.getLabels().add(k);
				
				for (Integer i = 0; i < this.grafoOriginal.getTamanhoMatrizAdj(); i++) {
					for (Integer j = 0; j < this.grafoOriginal.getTamanhoMatrizAdj(); j++) {
						if(this.grafoOriginal.getMatrizAdj()[i][j] == k){
							newGraph.getMatrizAdj()[i][j] = this.grafoOriginal.getMatrizAdj()[i][j]; 
						}
					}
				}
			}
		}
		return newGraph;
	}

	private Integer[] generateRandomSolution(Integer[] c) {
		ArrayList<Integer> listaL = semNumRep();
		
		Random r = new Random();
		Integer rnd = r.nextInt(listaL.size());
		
		c[listaL.get(rnd)] = 1;
		listaL.remove(listaL.get(rnd));
		
		rnd = r.nextInt(listaL.size());
		c[listaL.get(rnd)] = 1;
		listaL.remove(listaL.get(rnd));
	
		
		return c;
	}

	private ArrayList<Integer> semNumRep() {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		for (Integer integer : this.grafoOriginal.getLabels()) {
			if(!lista.contains(integer)){
				lista.add(integer);
			}
		}
		return lista;
	}

	public Integer[] getC() {
		return C;
	}

	public void setC(Integer[] c) {
		C = c;
	}

	public Grafo<Vertice, Aresta<Vertice,Vertice>> getHC() {
		return HC;
	}

	public void setHC(Grafo<Vertice, Aresta<Vertice,Vertice>> hC) {
		HC = hC;
	}
}
