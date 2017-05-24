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

public class TratarImagem {

	public static PlanarImage binarizar(PlanarImage image) {
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
		String fileName = "c:/Temp/output/teste.png";
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

	public static void processar(Raster raster) {
		// Cria arrays com a quantidade de bytes por pixel
		int[] pixel = new int[raster.getNumBands()];

		int valores[][] = new int[8][8];
		
		int xx = 0;
		int yy = 0;
		for (int ix = 8; ix < raster.getWidth() - 50; ix += 47) { // 47
			for (int iy = 8; iy < raster.getHeight() - 50; iy += 45) { // 45
				// System.out.println("ix: "+ix+"|iy"+iy);
				// varre todos os pixels do quadrante
				int qtdPretos = 0;
				for (int x = 0; x < 48; x++) {
					for (int y = 0; y < 46; y++) {
						//System.out.println("X:" + (x + ix) + "|Y" + (y + iy));
						raster.getPixel((x + ix), (y + iy), pixel);

						if (pixel[0] < 127 || pixel[1] < 127 || pixel[2] < 127) {
							qtdPretos++;
						}
					}
				}
				if(qtdPretos > 200){
					valores[xx++][yy++] = 1;
				} else {
					valores[xx++][yy++] = 0;
				}
			}
		}
		
		for (int i = 0; i < valores.length; i++) {
			System.out.print(valores[i][0]);
			System.out.print(" | ");
			System.out.print(valores[i][1]);
			System.out.print(" | ");
			System.out.print(valores[i][2]);
			System.out.print(" | ");
			System.out.print(valores[i][3]);
			System.out.print(" | ");
			System.out.print(valores[i][4]);
			System.out.print(" | ");
			System.out.print(valores[i][5]);
			System.out.print(" | ");
			System.out.print(valores[i][6]);
			System.out.print(" | ");
			System.out.println(valores[i][6]);
		}
	}

	public static void main(String[] args) {
		String caminho = ("C:\\Temp\\img\\Teste.gif");

		PlanarImage image = JAI.create("fileload", caminho);

		PlanarImage binariazada = binarizar(image);

		PlanarImage dilatada = dilatar(binariazada);

		PlanarImage erodida = erosar(dilatada);

		System.out.println(erodida.getWidth());
		System.out.println(erodida.getHeight());

		processar(erodida.getAsBufferedImage().getRaster());

		JFrame frame = new JFrame("imagem");
		frame.add(new DisplayTwoSynchronizedImages(image, erodida));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
