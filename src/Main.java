
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.sun.media.jai.widget.DisplayJAI;

public class Main {
	public static void applyJAIEffect(File f) {
		PlanarImage image = JAI.create("fileload", f.getAbsolutePath());
		float[] mask = { 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f,
				1.0f / 9.0f, 1.0f / 9.0f };
		KernelJAI kernel = new KernelJAI(3, 3, mask);
		PlanarImage bordas = JAI.create("convolve", image, kernel);

		JFrame frame = new JFrame("Arquivo: " + f.getName());
		frame.setSize(new Dimension(image.getWidth() + 20, image.getHeight() + 40));

		// adiciona componente do JAI
		frame.add(new DisplayJAI(bordas));

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void applyEffect(File f, boolean isBinary) throws Exception {

		// Abre imagem e cria raster
		BufferedImage input = ImageIO.read(f);
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
					// binariza��o
					if (isBinary) {
						pixelOut[i] = pixel[i] < 127 ? 0 : 255;
					}
					// negativo
					else {
						pixelOut[i] = 255 - pixel[i];
					}
				}

				// escreve pixel no raster de sa�da
				wr.setPixel(ix, iy, pixelOut);
			}
		}

		// Monta nome do arquivo
		String fileName = "c:/Temp/output/" + (isBinary ? "binario" : "negativo");
		ImageIO.write(outImg, "PNG", new File(fileName + ".png"));
	}

	public static void main(String args[]) throws Exception {

		// evitar problema com o JAI quando esta propriedade est� ativa
		// System.setProperty("com.sun.media.jai.disableMediaLib", "true");

		// applyEffect(new File("c://Temp/img/Lena.jpg"), false);
		// applyEffect(new File("c://Temp/img/Lena.jpg"), true);
		// applyJAIEffect(new File("c://Temp/img/Bola.jpg"));

		Celula c00 = new Celula(0);
		Celula c01 = new Celula(0);
		Celula c02 = new Celula(0);
		Celula c03 = new Celula(0);
		Celula c04 = new Celula(0);
		Celula c05 = new Celula(0);
		Celula c06 = new Celula(0);

		Celula c10 = new Celula(0);
		Celula c11 = new Celula(0);
		Celula c12 = new Celula(0);
		Celula c13 = new Celula(0);
		Celula c14 = new Celula(0);
		Celula c15 = new Celula(0);
		Celula c16 = new Celula(0);

		Celula c20 = new Celula(0);
		Celula c21 = new Celula(0);
		Celula c22 = new Celula(-1);
		Celula c23 = new Celula(0);
		Celula c24 = new Celula(0);
		Celula c25 = new Celula(0);
		Celula c26 = new Celula(0);

		Celula c30 = new Celula(0);
		Celula c31 = new Celula(0);
		Celula c32 = new Celula(-1);
		Celula c33 = new Celula(0);
		Celula c34 = new Celula(0);
		Celula c35 = new Celula(0);
		Celula c36 = new Celula(0);

		Celula c40 = new Celula(-1);
		Celula c41 = new Celula(-1);
		Celula c42 = new Celula(-1);
		Celula c43 = new Celula(0);
		Celula c44 = new Celula(0);
		Celula c45 = new Celula(0);
		Celula c46 = new Celula(0);

		Celula c50 = new Celula(-1);
		Celula c51 = new Celula(-1);
		Celula c52 = new Celula(-1);
		Celula c53 = new Celula(0);
		Celula c54 = new Celula(0);
		Celula c55 = new Celula(2);
		Celula c56 = new Celula(0);

		Celula c60 = new Celula(0);
		Celula c61 = new Celula(0);
		Celula c62 = new Celula(0);
		Celula c63 = new Celula(0);
		Celula c64 = new Celula(0);
		Celula c65 = new Celula(0);
		Celula c66 = new Celula(0);

		Celula celulas[][] = { { c00, c01, c02, c03, c04, c05, c06 },

				{ c10, c11, c12, c13, c14, c15, c16 }, { c20, c21, c22, c23, c24, c25, c26 },
				{ c30, c31, c32, c33, c34, c35, c36 }, { c40, c41, c42, c43, c44, c45, c46 },
				{ c50, c51, c52, c53, c54, c55, c56 }, { c60, c61, c62, c63, c64, c65, c66 } };

		//  0 , 0 , 0 ,0 ,0 ,0 ,0
		//  0 , 0 , 0 ,0 ,0 ,0 ,0
		//  0 , 0 ,-1 ,0 ,0 ,0 ,0
		//  0 , 0 ,-1 ,0 ,0 ,0 ,0
		// -1 ,-1 ,-1 ,0 ,0 ,0 ,0
		// -1 ,-1 ,-1 ,0 ,0 ,2 ,0
		//  0 , 0 , 0 ,0 ,0 ,0 ,0

		WaveFront wave = new WaveFront(celulas, 6, 6, 0, 0);
		wave.wave();
		
	}

}
