import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

public class TratarImagem {


	public void binarizar(String caminhoImagem){
		PlanarImage image = JAI.create("fileload", caminhoImagem);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image);
		pb.add(127.0);
		PlanarImage binarizada = JAI.create("binarize", pb);
		
		JFrame frame = new JFrame("imagem binarizada");
		frame.add(new DisplayTwoSynchronizedImages(image, binarizada));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {

	}

}
