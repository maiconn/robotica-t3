
public class WaveFront {

	private Celula[][] celulas;
	private int Xobjetivo;
	private int Yobjetivo;
	private int Xrobo;
	private int Yrobo;

	// origem 0 ???
	// vazio 0
	// objetivo 2
	// obstaculo -1

	// sequencia
	// 0 direita
	// 1 baixo
	// 2 esquerda
	// 3 cima

	public WaveFront(Celula[][] celulas, int xobjetivo, int yobjetivo, int xrobo, int yrobo) {
		this.celulas = celulas;
		this.Xobjetivo = xobjetivo;
		this.Yobjetivo = yobjetivo;
		this.Xrobo = xrobo;
		this.Yrobo = yrobo;
	}

	public void wave() {

		while (!IsCompleta()) {

			// verifica se passou limite matriz
			if ((Xobjetivo + 1) >= celulas.length) {// direita
				// verfica direita objetivo
				for (int i = Xobjetivo; i < celulas.length; i++) {
					if (celulas[i + 1][Yobjetivo].getValor() == 0) {
						celulas[i + 1][Yobjetivo].setValor(celulas[i][Yobjetivo].getValor() + 1);
					}
				}
			} else if ((Yobjetivo + 1) >= celulas.length) {// baixo
				for (int i = Yobjetivo; i < celulas.length; i++) {
					if (celulas[Xobjetivo][i + 1].getValor() == 0) {
						celulas[Xobjetivo][i + 1].setValor(celulas[Xobjetivo][i].getValor() + 1);
					}
				}
			} else if ((Xobjetivo - 1) >= 0) { // esquerda
				for (int i = Xobjetivo; i < celulas.length; i--) {
					if (celulas[i - 1][Yobjetivo].getValor() == 0) {
						celulas[i - 1][Yobjetivo].setValor(celulas[i][Yobjetivo].getValor() + 1);
					}

				}
			} else if ((Yobjetivo - 1) >= 0) {// cima
				for (int i = Yobjetivo; i < celulas.length; i--) {
					if (celulas[Xobjetivo][i - 1].getValor() == 0) {
						celulas[Xobjetivo][i - 1].setValor(celulas[Xobjetivo][i].getValor() + 1);
					}
				}

			}
		}
	}

	public boolean IsCompleta() {
		for (int i = 0; i < celulas.length; i++) {
			for (int j = 0; j < celulas.length; j++) {
				if (celulas[i][j].getValor()==0) {
					return false;
				}
			}
		}
		return true;
	}

}
