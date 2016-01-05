package instancias;

import java.io.File;
import java.io.IOException;

import leDir.Diretorios;

public class Group2with50 {
	
	public static void main(String[] args) throws IOException {
		
		File grupo2= new File(Group1.root + "Group 2 with n=50");
		
		Diretorios dir1 = new Diretorios(grupo2);
		dir1.readArquivo('M');
	}
}
