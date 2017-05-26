package wavefront;

public class Ponto {

	private int x;
	private int y;

	public Ponto(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean pontosIguais(Ponto p) {
		return (this.x == p.getX() && this.y == p.getY());
	}
}