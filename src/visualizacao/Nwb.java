package visualizacao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Nwb {
	private File nwb;
    private FileWriter writer;
    private Integer[][] grafo;
    
    public Nwb(Integer[][] grafo) throws IOException{
    	this.grafo = grafo;
    	this.nwb = new File("/home/mariane/Documentos/grafo.nwb");
    	this.writer = new FileWriter(nwb);
    }
	
	public void Gerar() throws IOException{
		this.writer.write("*Nodes\nid*int label*string\n");
		nodes();
		
		this.writer.write("*UndirectedEdges\nsource*int target*int label*string\n");
		edges();
		
		this.writer.close();
	}

	private void edges() throws IOException {
	int linha = 0;
	
		for (int i = 0; i < grafo.length; i++) {
			for (int j = linha++; j < grafo.length; j++) {
				if(grafo[i][j] != 20){
					
					this.writer.write((i+1) + " " + (j+1) + " " + "\"" +this.grafo[i][j].toString() + "\"" + "\n");
				}
			}
		}
	}

	private void nodes() throws IOException {
		for (Integer i = 0; i < this.grafo.length; i++) {
			Integer a = i + 1;
			this.writer.write((a) + " " + "\"" +a.toString() + "\"" + "\n");
		}
	}

}
