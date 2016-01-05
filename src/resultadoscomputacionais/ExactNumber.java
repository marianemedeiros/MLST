package resultadoscomputacionais;

import grafo.Aresta;
import grafo.Grafo;
import grafo.GrafoMatrizAdj;
import grafo.Vertice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import vns.VNS1;



public class ExactNumber {

	private int max;
	private int min;
	private int moda;
	private float media;
	
	public void executeVns(ArrayList<Grafo<Vertice, Aresta<Vertice,Vertice>>> listaGrafos){
		int cont = 1;
		int qtd_labels = 0 ;
		
		for (Grafo<Vertice, Aresta<Vertice, Vertice>> grafo : listaGrafos) {
				System.out.println("\nResultados da matriz " + cont + "\n");
				cont++;
				
				float media = 0;
				for (int i = 0; i < 10; i++) {
					VNS1 vns = new VNS1(grafo);
						vns.vns(0);
						//showSolution(vns.getC());
						qtd_labels = qtd_labels + vns.getUsedLabels(vns.getC());
						//System.err.println("s " + s);
										
						zeraGrafo(grafo);
				}
				this.media = (float) (qtd_labels / 10.0);
				System.err.println("Em Media " + this.media + " labels foi dada no conjunto solução.");
				
			}
	}



	private void zeraGrafo(Grafo<Vertice, Aresta<Vertice, Vertice>> grafo) {
		for (Vertice element : grafo.getIntToVert().values()) {
			element.setCompConexa(-1);
		}
	}



	private void showSolution(Integer[] c) {
		for (int i = 0; i < c.length; i++) {
			if(c[i] != null && c[i] == 1){
				System.out.println(" i = " + i + " valor: "  + c[i]);
			}
		}
	}

}
