package instancias;

import java.io.IOException;
import java.util.ArrayList;

import file.FileUtil;
import matrix2graph.Matrix2Graph;
import resultadoscomputacionais.AnaliseExperimental;

public class Teste{

	public static void main(String[] args) throws IOException {
		
		/*HDGraph20_20*/
		FileUtil HDGraph20_20 = new FileUtil(Group1.root + "/GROUP 1/teste");
		HDGraph20_20.openFileReader();
		
		
		ArrayList<Integer[][]> l = HDGraph20_20.transform2matrix();

		Matrix2Graph HDGraph20 = new Matrix2Graph(l,HDGraph20_20.getIntervalOfWeight());
		HDGraph20.geraGraph();
		
		AnaliseExperimental a1 = new AnaliseExperimental();
		a1.executeVns(HDGraph20.getListOfGraph());
		/*end*/
		
		
	}

}
