package view;

import java.io.File;
import java.util.List;

import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tratamentoimagem.TratarImagem;
import wavefront.Orientacao;
import wavefront.Ponto;
import wavefront.WaveFront;

public class Main {
	public static void verificarAndCriarPasta(String pasta) {
		File file = new File(pasta);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static void main(String[] args) {
		verificarAndCriarPasta("C:\\Temp");
		verificarAndCriarPasta("C:\\Temp\\img");
		verificarAndCriarPasta("C:\\Temp\\output");

		String caminho = JOptionPane.showInputDialog("Digite o caminho da imagem:", "C:\\Temp\\img\\Teste.gif");
		if (!new File(caminho).exists()) {
			JOptionPane.showMessageDialog(null, "Imagem não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// tratamento da imagem
		TratarImagem tratarImagem = new TratarImagem(caminho);
		int[][] celulas = tratarImagem.getCelulas();

		// definir origem
		Ponto robo = tratarImagem.acharRobo();

		JFrame frame = new JFrame("Imagem Fornecida | Imagem Tratada | Imagem em Quadrantes | Caminho do Robô");
		DisplaySynchronizedImages display = new DisplaySynchronizedImages(new PlanarImage[] {
				tratarImagem.getImagemInicial(), tratarImagem.getImagemFinal(), tratarImagem.getImagemProcessada() });

		frame.add(display);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// define destino
		String caminhoEscolhido = JOptionPane.showInputDialog("Escolha objetivo [x y]. (inicia em zero) exemplo: 6 6", "6 6");
		String splited[] = caminhoEscolhido.split(" ");
		Ponto objetivo = new Ponto(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));

		//  wavefront
		WaveFront waveFront = new WaveFront(celulas, new Ponto(robo.getX(), robo.getY()), objetivo);
		System.out.println("WAVEFRONT:");
		System.out.println(imprimirCenario(celulas));

		// capturar direções
		List<Orientacao> navegacao = waveFront.getCaminho();
		for (Orientacao d : navegacao) {
			System.out.print(d.toString() + " ");
		}
		
		display.add(tratarImagem.getImagemCaminho(robo, objetivo, navegacao));
		frame.pack();
	}

	public static String imprimirCenario(int[][] cenario) {
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < cenario.length; i++) {
			for (int j = 0; j < cenario[i].length; j++) {
				if (cenario[i][j] == Integer.MAX_VALUE)
					str.append("[RR]");
				else if (cenario[i][j] == WaveFront.OBSTACULO)
					str.append("[XX]");
				else {
					String s = String.valueOf(cenario[i][j]);
					if (s.length() < 2)
						str.append("[" + String.format("%1s", "0") + s + "]");
					else
						str.append("[" + s + "]");
				}
			}

			str.append("\n");
		}

		return str.toString();
	}
}
