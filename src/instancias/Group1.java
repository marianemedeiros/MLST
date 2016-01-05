package instancias;

import java.io.File;
import java.io.IOException;

import leDir.Diretorios;

public class Group1 {
	public static final String root = "/home/mariane/Downloads/5_semestre/Grafos/MLSP/resources/instancias/";
	
	public static void main(String[] args) throws IOException {
		
		File grupo1= new File(root + "GROUP 1");
		
		Diretorios dir1 = new Diretorios(grupo1);
		//dir1.readArquivo('H');
		dir1.readArquivo(new File(root + "GROUP 1/HDGraph40_40.txt"));
		//dir1.extractInfo('H');
		
		
	}

}
