
public class Celula {
	private TipoCelula tipoCelula;
	private int valor;

	public Celula(int valor) {
		this.valor = valor;
	}

	public TipoCelula getTipoCelula() {
		return tipoCelula;
	}

	public void setTipoCelula(TipoCelula tipoCelula) {
		this.tipoCelula = tipoCelula;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
