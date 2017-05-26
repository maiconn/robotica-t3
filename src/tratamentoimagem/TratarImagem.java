package tratamentoimagem;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import com.sun.media.jai.iterator.RandomIterCSMInt;

import wavefront.Orientacao;
import wavefront.Ponto;
import wavefront.WaveFront;

public class TratarImagem {
	private PlanarImage imagemInicial;
	private PlanarImage imagemFinal;
	private PlanarImage imagemProcessada;
	private String caminho;
	private int[][] celulas;

	public TratarImagem(String caminho) {
		this.caminho = caminho;
		celulas = processarImagem(caminho);
	}

	private int[][] processarImagem(String caminho) {
		imagemInicial = JAI.create("fileload", caminho);
		PlanarImage binariazada = binarizar(imagemInicial);
		PlanarImage dilatada = dilatar(binariazada);
		imagemFinal = erosar(dilatada);
		imagemProcessada = imagemProcessada();
		return processar(imagemFinal);
	}

	public Ponto acharRobo() {
		// Abre imagem e cria raster
		BufferedImage input = JAI.create("fileload", caminho).getAsBufferedImage();
		Raster raster = input.getRaster();
		
		// Cria arrays com a quantidade de bytes por pixel
		int[] pixel = new int[raster.getNumBands()];

		int xx = 0;
		int yy = 0;
		
		Ponto ponto = null;
		int valores[][] = new int[7][7];
		// Loopa os pixels de entrada
		for (int ix = 8; ix < raster.getWidth() - 50; ix += 47) { // 47
			for (int iy = 8; iy < raster.getHeight() - 50; iy += 45) { // 45

				// varre todos os pixels do quadrante
				int qtdBrancos = 0;
				for (int x = 0; x < 48; x++) {
					for (int y = 0; y < 46; y++) {
						raster.getPixel((x + ix), (y + iy), pixel);

						if (pixel[0] >= 251) {
							qtdBrancos++;
						}
					}
				}

				if (qtdBrancos >= 98) {
					ponto = new Ponto(xx, yy);
				} 

				valores[xx][yy] = qtdBrancos;
				xx++;
			}
			xx = 0;
			yy++;
		}

		raster.getPixel(286, 184, pixel);
		
		System.out.println(pixel[0]);
		System.out.println("QTD BRANCOS:");
		for (int row = 0; row < valores.length; row++) {
			for (int col = 0; col < valores[row].length; col++) {
				System.out.printf("%4d |", valores[row][col]);
			}
			System.out.println();
		}
		System.out.println();

		return ponto;
	}

	private PlanarImage binarizar(PlanarImage image) {
		// Abre imagem e cria raster
		BufferedImage input = image.getAsBufferedImage();
		Raster raster = input.getRaster();
		// Cria rasterizador de sa�da
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

	private PlanarImage dilatar(PlanarImage image) {
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

	private PlanarImage erosar(PlanarImage image) {
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

	private static int[][] processar(PlanarImage imagem) {
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
					celulas[xx][yy] = WaveFront.OBSTACULO;
				} else {
					celulas[xx][yy] = WaveFront.PADRAO;
				}

				valores[xx][yy] = qtdPretos;
				xx++;
			}
			xx = 0;
			yy++;
		}

		System.out.println();
		System.out.println("QTD PRETOS:");
		for (int row = 0; row < valores.length; row++) {
			for (int col = 0; col < valores[row].length; col++) {
				System.out.printf("%4d |", valores[row][col]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Depois de processado:");
		for (int row = 0; row < celulas.length; row++) {
			for (int col = 0; col < celulas[row].length; col++) {
				System.out.printf("%4d |", celulas[row][col]);
			}
			System.out.println();
		}
		System.out.println();

		return celulas;
	}

	public PlanarImage imagemProcessada() {
		BufferedImage input = imagemFinal.getAsBufferedImage();
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

	public PlanarImage getImagemCaminho(Ponto robo, Ponto objetivo, List<Orientacao> caminho) {
		BufferedImage input = imagemFinal.getAsBufferedImage();
		Raster raster = input.getRaster();

		int[] pixel = new int[raster.getNumBands()];
		BufferedImage outImg = new BufferedImage(raster.getWidth(), raster.getHeight(), 13);
		WritableRaster wr = outImg.getRaster();

		for (int ix = 8; ix < raster.getWidth() - 50; ix += 47) { // 47
			for (int iy = 8; iy < raster.getHeight() - 50; iy += 45) { // 45

				for (int x = 0; x < 47; x++) {
					for (int y = 0; y < 45; y++) {
						raster.getPixel((x + ix), (y + iy), pixel);
						wr.setPixel((x + ix), (y + iy), pixel);
						wr.setPixel(ix, (y + iy), new int[] { 150, 0, 0 });
					}
					wr.setPixel((x + ix), iy, new int[] { 150, 0, 0 });
				}
			}
		}

		// pinta robo
		int xPixel = 8 + (47 * robo.getY());
		int yPixel = 8 + (45 * robo.getX());
		for (int x = xPixel + 1; x < (xPixel + 47); x++) {
			for (int y = yPixel + 1; y < (yPixel + 45); y++) {
				wr.setPixel(x, y, new int[] { 10, 150, 150 });
			}
		}

		// pinta objetivo
		xPixel = 8 + (47 * objetivo.getY());
		yPixel = 8 + (45 * objetivo.getX());
		for (int x = xPixel + 1; x < (xPixel + 47); x++) {
			for (int y = yPixel + 1; y < (yPixel + 45); y++) {
				wr.setPixel(x, y, new int[] { 20, 20, 20 });
			}
		}

		// pinta caminho
		for (int i = 0; i < caminho.size() - 1; i++) {
			Orientacao orient = caminho.get(i);
			xPixel = 8 + (47 * orient.getY());
			yPixel = 8 + (45 * orient.getX());
			for (int x = xPixel + 1; x < (xPixel + 47); x++) {
				for (int y = yPixel + 1; y < (yPixel + 45); y++) {
					wr.setPixel(x, y, new int[] { 200, 5, 20 });
				}
			}
		}

		// pinta barreitas
		for (int i = 0; i < celulas.length; i++) {
			for (int j = 0; j < celulas[i].length; j++) {
				if (celulas[i][j] == WaveFront.OBSTACULO) {
					xPixel = 8 + (47 * j);
					yPixel = 8 + (45 * i);
					for (int x = xPixel + 1; x < (xPixel + 47); x++) {
						for (int y = yPixel + 1; y < (yPixel + 45); y++) {
							wr.setPixel(x, y, new int[] { 0, 0, 0 });
						}
					}
				}
			}
		}

		String fileName = "c:/Temp/output/caminho.png";
		try {
			ImageIO.write(outImg, "PNG", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return JAI.create("fileload", fileName);
	}

	public int[][] getCelulas() {
		return celulas;
	}

	public PlanarImage getImagemFinal() {
		return imagemFinal;
	}

	public PlanarImage getImagemInicial() {
		return imagemInicial;
	}

	public PlanarImage getImagemProcessada() {
		return imagemProcessada;
	}
}
