package leDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import resultadoscomputacionais.AnaliseExperimental;
import vns.VNS1;
import matrix2graph.Matrix2Graph;
import file.FileUtil;
import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

public class Diretorios {
	private File path;
	private ArrayList<File> filesHD = new ArrayList<File>();
	private ArrayList<File> filesMD = new ArrayList<File>();
	private ArrayList<File> filesLD = new ArrayList<File>();
	private ArrayList<File> teste = new ArrayList<File>();
	
	public int soma = 0;

	public Diretorios(File p) throws IOException {
		this.path = p;
		takeFiles();
	}

	private void takeFiles() {
		File[] files = this.path.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if(files[i].getName().contains("HD")){
				this.filesHD.add(files[i]);
			}else if(files[i].getName().contains("LD")){
				this.filesLD.add(files[i]);
			}else if(files[i].getName().contains("MD")){
				this.filesMD.add(files[i]);
			}else if(files[i].getName().contains("teste")){
				this.teste.add(files[i]);
			}
		}
	}
	
	public void readArquivo(Character arq) throws IOException{
		ArrayList<File> file = null;
		if(arq == 'L'){
			file = this.filesLD;
		}else if(arq == 'M'){
			file = this.filesMD;
		}else if(arq == 'H'){
			file = this.filesHD;
		}else if(arq == 't'){
			file = this.teste;
		}
		
		for (File element : file) {
			FileUtil f = new FileUtil(this.path.getAbsolutePath().concat("/").concat(element.getName()));
			f.openFileReader();
			ArrayList<Integer[][]> c = f.transform2matrix();
			
			Matrix2Graph m = new Matrix2Graph(c, f.getIntervalOfWeight());
			m.geraGraph();
			
			System.out.println("arquivo: " + element.getName() + "\n");
			
				AnaliseExperimental a = new AnaliseExperimental();
				
				long inicio = System.currentTimeMillis();
				a.executeVns(m.getListOfGraph());
				long fim = System.currentTimeMillis();
				
				System.err.println("Tempo de execução do arquivo: " + new SimpleDateFormat("ss:SS").format(new Date(fim - inicio)) + "\n");  
		}
	}
	
	
	public void extractInfo(Character arq) throws IOException{
		ArrayList<File> file = null;
		if(arq == 'L'){
			file = this.filesLD;
		}else if(arq == 'M'){
			file = this.filesMD;
		}else if(arq == 'H'){
			file = this.filesHD;
		}else if(arq == 't'){
			file = this.teste;
		}
		
		float s = 0;
		for (File element : file) {
			FileUtil f = new FileUtil(this.path.getAbsolutePath().concat("/").concat(element.getName()));
			f.openFileReader();
			ArrayList<Integer[][]> c = f.transform2matrix();
			
			Matrix2Graph m = new Matrix2Graph(c, f.getIntervalOfWeight());
			m.geraGraph();
			
			System.out.println("\narquivo: " + element.getName());
			System.out.println("\nlabels: " + m.getIntervalOfWeight() + "\n");
			
			
			float sum = 0;
			
			float m1 = 0;
					sum = 0;
					for (int j = 0; j < m.getListOfGraph().size(); j++){
						zeraGrafo(m.getListOfGraph().get(j));
						VNS1 v = new VNS1(m.getListOfGraph().get(j));
						v.vns(0);
						//System.out.println("Quantidade de labels usadas para encontrar a solução: " + v.getUsedLabels(v.getC()));
						System.out.println("sum");
						sum = sum + v.getUsedLabels(v.getC());
					}
						m1 = sum / 10;
						System.err.println("Total de labels usadas nos 10 grafos da iterarão : " + m1);
		}
	}
	
	private void zeraGrafo(Grafo<Vertice, Aresta<Vertice, Vertice>> grafo) {
		for (Vertice element : grafo.getIntToVert().values()) {
			element.setCompConexa(-1);
		}
	}
	
	public void readArquivo(File arquivo) throws IOException{
		FileUtil f = new FileUtil(arquivo.getPath());
		f.openFileReader();
		ArrayList<Integer[][]> c = f.transform2matrix();
		
		Matrix2Graph m = new Matrix2Graph(c, f.getIntervalOfWeight());
		m.geraGraph();

		float sum = 0;
		float m1 = 0;
		
		for (int j = 0; j < m.getListOfGraph().size(); j++){
			zeraGrafo(m.getListOfGraph().get(j));
			VNS1 vns = new VNS1(m.getListOfGraph().get(j));
			
			System.out.println("Iniciando ...");
			
			long inicio = System.currentTimeMillis();
			vns.vns(inicio);
			long fim = System.currentTimeMillis();
			sum = sum + vns.getUsedLabels(vns.getC());
			
			System.out.println("---Tempo: " + new SimpleDateFormat("ss:SS").format(new Date(fim - inicio)) + "\n");  
		}
		m1 = sum / 10;
		System.err.println("Total de labels usadas nos 10 grafos da iterarão : " + m1);
	}

	private void showVertices(Grafo<Vertice, Aresta<Vertice, Vertice>> grafo) {
		for (Integer element : grafo.getIntToVert().keySet()) {
			System.out.println("Vertice: " + element);
		}
		
	}
	
}
