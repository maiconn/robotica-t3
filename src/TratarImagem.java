import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TratarImagem {

	public static void verificarAndCriarPasta(String pasta) {
		File file = new File(pasta);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static PlanarImage binarizar(PlanarImage image) {
		// Abre imagem e cria raster
		BufferedImage input = image.getAsBufferedImage();
		Raster raster = input.getRaster();
		// Cria rasterizador de saï¿½da
		BufferedImage outImg = new BufferedImage(raster.getWidth(), raster.getHeight(), input.getType());
		WritableRaster wr = outImg.getRaster();

		// Cria arrays com a quantidade de bytes por pixel
		int[] pixel = new int[raster.getNumBands()];
		int[] pixelOut = new int[pixel.length];

		// Loopa os pixels de entrada
		for (int ix = 0; ix < raster.getWidth(); ++ix) {
			for (int iy = 0; iy < raster.getHeight(); iy++) {
				// pega valor do pixel e joga no array

				raster.getPixel(ix, iy, pixel);

				for (int i = 0; i < pixel.length; ++i) {

					pixelOut[i] = pixel[i] < 127 ? 0 : 255;

				}
				wr.setPixel(ix, iy, pixelOut);
			}
		}
		// Monta nome do arquivo
		String fileName = "c:/Temp/output/binarizada.png";
		try {
			ImageIO.write(outImg, "PNG", new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JAI.create("fileload", fileName);
	}

	public static PlanarImage dilatar(PlanarImage image) {
		float[] estrutura = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0,
				0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1,
				1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		KernelJAI kernel = new KernelJAI(10, 10, estrutura);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		pb.add(kernel);

		PlanarImage dilatada = JAI.create("dilate", pb);
		return dilatada;
	}

	public static PlanarImage erosar(PlanarImage image) {
		float[] estrutura = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0,
				0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1,
				1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		KernelJAI kernel = new KernelJAI(10, 10, estrutura);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		pb.add(kernel);

		PlanarImage dilatada = JAI.create("erode", pb);
		return dilatada;
	}

	public static PlanarImage getImagemProcessada(PlanarImage imagem) {
		BufferedImage input = imagem.getAsBufferedImage();
		Raster raster = input.getRaster();

		int[] pixel = new int[raster.getNumBands()];
		BufferedImage outImg = new BufferedImage(raster.getWidth(), raster.getHeight(), 13);
		WritableRaster wr = outImg.getRaster();
		for (int ix = 8; ix < raster.getWidth() - 50; ix += 47) { // 47
			for (int iy = 8; iy < raster.getHeight() - 50; iy += 45) { // 45
				for (int x = 0; x < 48; x++) {
					for (int y = 0; y < 46; y++) {
						raster.getPixel((x + ix), (y + iy), pixel);
						wr.setPixel((x + ix), (y + iy), pixel);
						wr.setPixel(ix, (y + iy), new int[] { 150, 0, 0 });
					}
					wr.setPixel((x + ix), iy, new int[] { 150, 0, 0 });
				}
			}
		}

		String fileName = "c:/Temp/output/processada.png";
		try {
			ImageIO.write(outImg, "PNG", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return JAI.create("fileload", fileName);
	}

	public static int[][] processar(PlanarImage imagem) {
		Raster raster = imagem.getAsBufferedImage().getRaster();
		// Cria arrays com a quantidade de bytes por pixel
		int[] pixel = new int[raster.getNumBands()];

		int valores[][] = new int[7][7];
		int[][] celulas = new int[7][7];

		int xx = 0;
		int yy = 0;

		for (int ix = 8; ix < raster.getWidth() - 50; ix += 47) { // 47
			for (int iy = 8; iy < raster.getHeight() - 50; iy += 45) { // 45

				// varre todos os pixels do quadrante
				int qtdPretos = 0;
				for (int x = 0; x < 48; x++) {
					for (int y = 0; y < 46; y++) {
						// System.out.println("X:" + (x + ix) + "|Y" + (y +
						// iy));
						raster.getPixel((x + ix), (y + iy), pixel);

						if (pixel[0] < 127 || pixel[1] < 127 || pixel[2] < 127) {
							qtdPretos++;
						}
					}
				}

				if (qtdPretos >= 100) {
					celulas[xx][yy] = -1;
				} else {
					celulas[xx][yy] = 0;
				}

				valores[xx][yy] = qtdPretos;
				xx++;
			}
			xx = 0;
			yy++;
		}

		for (int row = 0; row < celulas.length; row++) {
	        for (int col = 0; col < celulas[row].length; col++) {
	            System.out.printf("%4d |", celulas[row][col]);
	        }
	        System.out.println();
	    }
		
		System.out.println();
		
		for (int row = 0; row < valores.length; row++) {
	        for (int col = 0; col < valores[row].length; col++) {
	            System.out.printf("%4d |", valores[row][col]);
	        }
	        System.out.println();
	    }
		
		return celulas;
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

		PlanarImage image = JAI.create("fileload", caminho);
		PlanarImage binariazada = binarizar(image);
		PlanarImage dilatada = dilatar(binariazada);
		PlanarImage erodida = erosar(dilatada);
		
		int[][] valores = processar(erodida);
		
		new MyWaveFront(valores, 3, 5, 0, 0).WaveFront();
		
		JFrame frame = new JFrame("Imagem Fornecida | Imagem Tratada | Imagem em Quadrantes");
		frame.add(new DisplaySynchronizedImages(image, erodida, getImagemProcessada(erodida)));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
