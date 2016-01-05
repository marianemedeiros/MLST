package main;

import java.io.File;
import java.io.IOException;

import leDir.Diretorios;

public class Main {

	public static void main(String[] args) throws IOException {
		
		File grupo1= new File("/home/mariane/Documentos/utf-bcc/5_semestre/Grafos/APS.Teoria.dos.Grafos.2014.1/instancias/GROUP 1");
		
		Diretorios dir1 = new Diretorios(grupo1);
		
		dir1.readArquivo('L');

		
	}

}
