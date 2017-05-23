
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

public class Main 
{
	public static void applyJAIEffect(File f)
	{
		PlanarImage image = JAI.create("fileload", f.getAbsolutePath());
		float[] mask = {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f,
						1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f,
						1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f};
		KernelJAI kernel  = new KernelJAI(3, 3, mask);
		PlanarImage bordas = JAI.create("convolve", image, kernel);
		
		JFrame frame = new JFrame("Arquivo: " + f.getName());
		frame.setSize(new Dimension(image.getWidth()+20, 
									image.getHeight()+40));
		
		//adiciona componente do JAI
		frame.add(new DisplayJAI(bordas));
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public static void applyEffect(File f, boolean isBinary) throws Exception{
		
		//Abre imagem e cria raster
		BufferedImage input = ImageIO.read(f);
		Raster raster = input.getRaster();
		//Cria rasterizador de sa�da
		BufferedImage outImg = new BufferedImage(raster.getWidth(), raster.getHeight(), input.getType());
		WritableRaster wr = outImg.getRaster();
		
		//Cria arrays com a quantidade de bytes por pixel
		int[] pixel = new int[raster.getNumBands()];
		int[] pixelOut = new int[pixel.length];
		
		//Loopa os pixels de entrada
		for (int ix = 0; ix < raster.getWidth(); ++ix){
			for (int iy = 0; iy < raster.getHeight(); iy++){
				//pega valor do pixel e joga no array
				
				raster.getPixel(ix,iy, pixel);
				
				for(int i = 0; i < pixel.length; ++i)
				{
					//binariza��o
					if(isBinary) {
						pixelOut[i] = pixel[i] < 127 ? 0 : 255;
					}
					//negativo
					else{ pixelOut[i] = 255 - pixel[i]; }
				}
				
				//escreve pixel no raster de sa�da
				wr.setPixel(ix, iy, pixelOut);
			}
		}
		
		//Monta nome do arquivo
		String fileName = "c:/Temp/output/" + (isBinary ? "binario" : "negativo");
		ImageIO.write(outImg, "PNG", new File(fileName + ".png"));
	}
	
	public static void main(String args[]) throws Exception{
		
		//evitar problema com o JAI quando esta propriedade est� ativa
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
		
		applyEffect(new File("c://Temp/img/Lena.jpg"), false);
		applyEffect(new File("c://Temp/img/Lena.jpg"), true);
		applyJAIEffect(new File("c://Temp/img/Bola.jpg"));
	}

}
