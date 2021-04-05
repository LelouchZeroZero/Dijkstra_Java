
public class Vertice implements Comparable<Vertice>{
	private Vertice anterior;
	private boolean aberto = false,origem=false,destino=false;
	boolean visitado;
	int posicao_vetor;
	
	public int getPosicao_vetor() {
		return posicao_vetor;
	}

	public void setPosicao_vetor(int posicao_vetor) {
		this.posicao_vetor = posicao_vetor;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	int custo_caminho_acumulado;
	
	public Vertice()
	{
		setAnterior(null);
		setAberto(true);
		setOrigem(false);
		setDestino(false);
		setCusto_caminho_acumulado(0);
	}
	
	public boolean isOrigem() {
		return origem;
	}
	public void setOrigem(boolean origem) {
		this.origem = origem;
	}
	public boolean isDestino() {
		return destino;
	}
	public void setDestino(boolean destino) {
		this.destino = destino;
	}
	public boolean isAberto() {
		return aberto;
	}
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public Vertice getAnterior() {
		return anterior;
	}
	public void setAnterior(Vertice anterior) {
		this.anterior = anterior;
	}
	
	public void setCusto_caminho_acumulado(int custo)
	{
		this.custo_caminho_acumulado = custo;
	}
	
	public int getCusto_caminho_acumulado()
	{
		return custo_caminho_acumulado;
	}

	@Override
	public int compareTo(Vertice v0) {
		return (int) this.custo_caminho_acumulado - v0.custo_caminho_acumulado;
	}
		
	
}
