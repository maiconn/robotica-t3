package wavefront;

public class Orientacao {
	private int x, y;
	private Direcao direcao;

	public Orientacao(int x, int y, Direcao direcao) {
		this.x = x;
		this.y = y;
		this.direcao = direcao;
	}

	public Direcao getDirecao() {
		return direcao;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "Orientacao [x=" + x + ", y=" + y + ", direcao=" + direcao + "]";
	}
}
