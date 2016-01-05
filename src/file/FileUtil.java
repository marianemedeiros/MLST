package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtil {

	private String url;
	private BufferedReader fileAtual;
	private int intervalOfWeight;

	public FileUtil(String url) {
		setUrl(url);
	}

	/**
	 * Abre um arquivo para escrita.
	 * 
	 * @return retorna um Buffer de escrita.
	 */
	public BufferedWriter openFileWriter() {
		try {
			BufferedWriter w = new BufferedWriter(
					new FileWriter(this.url, true));
			return w;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Não abriu");
			return null;
		}

	}

	/**
	 * Abre um arquivo para leitura.
	 * 
	 * @return um buffer de leitura.
	 * @throws FileNotFoundException
	 */
	public BufferedReader openFileReader() throws FileNotFoundException {
		try {
			BufferedReader r = new BufferedReader(new FileReader(this.url));
			this.setFileAtual(r);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Não abriu");
			return null;
		}
	}

	public void writeToFile(String novo) throws IOException {
		BufferedWriter write = openFileWriter();
		write.write(novo);
		write.close();
	}

	/**
	 * Tranforma cada linha lida do arquivo, em uma linha válida de uma matriz.
	 * 
	 * 
	 * @return lista de matrizes contidas no arquivo.
	 * @throws IOException
	 */
	public ArrayList<Integer[][]> transform2matrix() throws IOException {
		String firstLine = this.fileAtual.readLine();
		
		Integer dimensao = Integer.parseInt(firstLine.split(" ")[0]);
		dimensao = dimensao - 1;
		this.intervalOfWeight = Integer.parseInt(firstLine.split(" ")[1]);
		
		Integer[][] matrizAtual = new Integer[dimensao][dimensao];
		ArrayList<Integer[][]> listOfMatriz = new ArrayList<Integer[][]>();

		String line = this.fileAtual.readLine();
		while (line != null) {
			if (line.equals("")) {
				listOfMatriz.add(matrizAtual);
				Integer[][] matrizNova = new Integer[dimensao][dimensao];
				matrizAtual = matrizNova;
			}
			matrizAtual = MatrixInvertida(matrizAtual, line);
			//matrizAtual = setOnMatriz(matrizAtual, line);
			line = this.fileAtual.readLine();
		}
		return listOfMatriz;
	}



	private Integer[][] MatrixInvertida(Integer[][] matrizAtual, String line) {
		String[] el = line.split(" ");
		ArrayList<Integer> elementsLine = new ArrayList<Integer>();
		

		for (int i = 0; i < el.length; i++) {
			if (!el[i].equals("")) {
				elementsLine.add(Integer.parseInt(el[i]));
			}
		}
		int k = elementsLine.size()-1;

		for (int l = 0; l < matrizAtual.length; l++) {
			/*
			 * se o primeiro elemento da linha x não for null, significa que
			 * esta linha já possui elementos, então tem que ir para a próxima
			 * linha.
			 */
			if (matrizAtual[l][matrizAtual.length-1] == null) {
				int i = matrizAtual.length - elementsLine.size();
				for (int c = i; c < matrizAtual.length; c++) {
						if(k < 0){ c = matrizAtual.length;}
						matrizAtual[l][c] = elementsLine.get(k);
						
						k--;
				}
				l = matrizAtual.length;
			}
		}
		return matrizAtual;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private void setFileAtual(BufferedReader fileAtual) {
		this.fileAtual = fileAtual;
	}

	public int getIntervalOfWeight() {
		return intervalOfWeight;
	}

	public void setIntervalOfWeight(int intervalOfWeight) {
		this.intervalOfWeight = intervalOfWeight;
	}

}
