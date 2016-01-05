package file;

import java.io.IOException;
import java.util.ArrayList;

import visualizacao.Nwb;

public class MainTeste {

	public static int cc(Integer[][] grafo){
		int linha = 0 ;
		int coluna = 0;
		
		int isCc = 1;
		int cc = 0;
		
		for(int nodes = 0; nodes < grafo.length; nodes++){
		
			for(int l = linha++; l < grafo.length; l++){
				
			}
			
			
		}
		
		
		return cc;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileUtil file = new FileUtil("/home/mariane/Documentos/utf-bcc/5_semestre/Grafos/APS.Teoria.dos.Grafos.2014.1/instancias/GROUP 1/teste");
		file.openFileReader();
		
		ArrayList<Integer[][]> lista = file.transform2matrix();
		
		for (int i = 0; i < lista.get(0).length; i++) {
			for (int j = 0; j < lista.get(0).length; j++) {
				System.out.print(lista.get(0)[i][j] + " ");
			}
			System.out.println("\n");
		}
		
		System.err.println(cc(lista.get(0)));
	
		Nwb arq = new Nwb(lista.get(0));
		arq.Gerar();
	
	}

}
