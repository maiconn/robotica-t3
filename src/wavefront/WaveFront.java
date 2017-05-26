package wavefront;

import java.util.ArrayList;
import java.util.List;

public class WaveFront {
	public final static int PADRAO = 99;
	public final static int OBSTACULO = -1;

	private Ponto robo;
	private Ponto objetivo;
	private int[][] cenario;

	public WaveFront(int[][] cenario, Ponto robo, Ponto objetivo) {
		cenario[robo.getX()][robo.getY()] = Integer.MAX_VALUE;
		cenario[objetivo.getX()][objetivo.getY()] = 0;

		this.objetivo = objetivo;
		this.cenario = cenario;
		this.robo = robo;
		executarWaveFront(cenario, objetivo.getX(), objetivo.getY(), 1);
	}

	private void executarWaveFront(int[][] cenario, int x, int y, int contador) {
		if (x < 0 || x >= cenario.length || y < 0 || y >= cenario[0].length)
			return;

		int xE = (x - 1);
		boolean esq = false;
		int xD = (x + 1);
		boolean dir = false;
		int yC = (y - 1);
		boolean cima = false;
		int yB = (y + 1);
		boolean baixo = false;

		int v;

		// preenche campo na esquerda
		if (xE > -1) {
			v = cenario[xE][y];
			if (v == PADRAO || (v != OBSTACULO && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[xE][y] = contador;
				esq = true;
			}
		}

		// preenche campo em cima
		if (yC > -1) {
			v = cenario[x][yC];
			if (v == PADRAO || (v != OBSTACULO && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[x][yC] = contador;
				cima = true;
			}
		}

		// preenche campo na direita
		if (xD < cenario.length) {
			v = cenario[xD][y];
			if (v == PADRAO || (v != OBSTACULO && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[xD][y] = contador;
				dir = true;
			}
		}

		// preenche campo em baixo
		if (yB < cenario[0].length) {
			v = cenario[x][yB];
			if (v == PADRAO || (v != OBSTACULO && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[x][yB] = contador;
				baixo = true;
			}
		}

		if (esq)
			executarWaveFront(cenario, xE, y, (contador + 1));

		if (dir)
			executarWaveFront(cenario, xD, y, (contador + 1));

		if (cima)
			executarWaveFront(cenario, x, yC, (contador + 1));

		if (baixo)
			executarWaveFront(cenario, x, yB, (contador + 1));
	}

	public List<Orientacao> getCaminho() {
		List<Orientacao> direcoes = new ArrayList<Orientacao>();
		while (!robo.pontosIguais(objetivo)) {
			direcoes.add(proximoMovimento(cenario, robo));
			cenario[robo.getX()][robo.getY()] = Integer.MAX_VALUE;
		}
		return direcoes;
	}

	private Orientacao proximoMovimento(int[][] cenario, Ponto robo) {
		int rX = robo.getX();
		int rY = robo.getY();

		int esq = (rY - 1);
		int dir = (rY + 1);
		int cima = (rX - 1);
		int baixo = (rX + 1);

		int contador = 9999;
		Direcao d = null;

		if (esq > -1) {
			int aux = cenario[rX][esq];

			if (aux == 0) {
				robo.setY(esq);
				return new Orientacao(robo.getX(), robo.getY(), Direcao.ESQUERDA);
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.ESQUERDA;
			}
		}

		if (cima > -1) {
			int aux = cenario[cima][rY];

			if (aux == 0) {
				robo.setX(cima);
				return new Orientacao(robo.getX(), robo.getY(), Direcao.CIMA);
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.CIMA;
			}
		}

		if (dir < cenario.length) {
			int aux = cenario[rX][dir];

			if (aux == 0) {
				robo.setY(dir);
				return new Orientacao(robo.getX(), robo.getY(), Direcao.DIREITA);
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.DIREITA;
			}
		}

		if (baixo < cenario.length) {
			int aux = cenario[baixo][rY];

			if (aux == 0) {
				robo.setX(baixo);
				return new Orientacao(robo.getX(), robo.getY(), Direcao.BAIXO);
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.BAIXO;
			}
		}

		switch (d) {
		case ESQUERDA:
			robo.setY(esq);
			break;
		case CIMA:
			robo.setX(cima);
			break;
		case DIREITA:
			robo.setY(dir);
			break;
		case BAIXO:
			robo.setX(baixo);
			break;
		}

		return new Orientacao(robo.getX(), robo.getY(), d);
	}

	public boolean validarContador(int c) {
		return (c != -1 && c != Integer.MAX_VALUE);
	}

}
