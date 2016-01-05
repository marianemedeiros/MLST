package grafo;

public class Vertice {
	private String id;
	private int compConexa = -1;

	public Vertice() {
		this.id = String.valueOf(getClass().hashCode());
	}

	public Vertice(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCompConexa() {
		return compConexa;
	}

	public void setCompConexa(int compConexa) {
		this.compConexa = compConexa;
	}

}
