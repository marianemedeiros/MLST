package resultadoscomputacionais;

import grafo.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vns.VNS1;


public class AnaliseExperimental {
	
	
	public void executeVns(ArrayList<Grafo<Vertice, Aresta<Vertice,Vertice>>> listaGrafos){
		int cont = 1;
		for (Grafo<Vertice, Aresta<Vertice, Vertice>> grafo : listaGrafos) {
			System.out.println("Resultados do grafo " + cont );
			cont++;
			VNS1 vns = new VNS1(grafo);
			
			long inicio = System.currentTimeMillis();
			
			vns.vns(inicio);
			showSolution(vns.getC());
			
			long fim = System.currentTimeMillis();
			
			
			System.out.println("---Qtd de labels usadas na solução: " + vns.getUsedLabels(vns.getC()));
			
			System.out.println("---Tempo: " + new SimpleDateFormat("ss:SS").format(new Date(fim - inicio)) + " segundos/milisegundos \n");  
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
