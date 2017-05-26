
public class MyWaveFront {
	private int[][] celulas;
	private int xObjetivo;
	private int yObjetivo;
	private int xRobo;
	private int yRobo;

	// origem 0 ???
	// vazio 0
	// objetivo 2
	// obstaculo -1

	// sequencia
	// 0 direita
	// 1 baixo
	// 2 esquerda
	// 3 cima

	public MyWaveFront(int[][] celulas, int xRobo, int yRobo, int xObjetivo, int yObjetivo) {
		this.celulas = celulas;
		this.xObjetivo = xObjetivo;
		this.yObjetivo = yObjetivo;
		this.xRobo = xRobo;
		this.yRobo = yRobo;
	}

	public void WaveFront() {
		
		
		celulas[xRobo][yRobo] = 2;
				
		for (int i = xRobo; i < celulas.length; i++) {
			for (int j = yRobo; j < celulas[i].length; j++) {
				if ((j + 1) < celulas[i].length)
					celulas[i][j + 1] = celulas[i][j] + 1;

				if ((i + 1) < celulas.length)
					celulas[i + 1][j] = celulas[i][j] + 1;

				if ((j - 1) < celulas[i].length)
					celulas[i][j - 1] = celulas[i][j] + 1;

				if ((i - 1) < celulas.length)
					celulas[i - 1][j] = celulas[i][j] + 1;
				
				
				System.out.println();
				for (int row = 0; row < celulas.length; row++) {
					for (int col = 0; col < celulas[row].length; col++) {
						System.out.printf("%4d |", celulas[row][col]);
					}
					System.out.println();
				}	
				System.out.println();
			}			
		}		
		
		for (int row = 0; row < celulas.length; row++) {
			for (int col = 0; col < celulas[row].length; col++) {
				System.out.printf("%4d |", celulas[row][col]);
			}
			System.out.println();
		}

	}

	public boolean completa() {
		for (int i = 0; i < celulas.length; i++) {
			for (int j = 0; j < celulas.length; j++) {
				if (celulas[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
}
