package view;

import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.RenderedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

/**
 * This class represents a JPanel which contains two scrollable instances of
 * DisplayJAI. The scrolling bars of both images are synchronized so scrolling
 * one image will automatically scroll the other.
 */
public class DisplaySynchronizedImages extends JPanel implements AdjustmentListener {
	private static final long serialVersionUID = 1L;

	// private static final long serialVersionUID = -3942207120738859247L;
	// /** The DisplayJAI for the first image. */
	// protected DisplayJAI dj1;
	// /** The DisplayJAI for the second image. */
	// protected DisplayJAI dj2;
	// /** The DisplayJAI for the second image. */
	// protected DisplayJAI dj3;
	// /** The JScrollPane which will contain the first of the images */
	// protected JScrollPane jsp1;
	// /** The JScrollPane which will contain the second of the images */
	// protected JScrollPane jsp2;
	// /** The JScrollPane which will contain the second of the images */
	// protected JScrollPane jsp3;
	// /**
	// * Creates an instance of this class, setting the components' layout,
	// * creating two instances of DisplayJAI for the two images and
	// * creating/registering event handlers for the scroll bars.
	// *
	// * @param im1
	// * the first image (left side)
	// * @param im2
	// * the second image (right side)
	// */
	//// public DisplaySynchronizedImages(RenderedImage im1, RenderedImage im2,
	// RenderedImage im3) {
	//// super();
	//// setLayout(new GridLayout(2, 3));
	//// dj1 = new DisplayJAI(im1); // Instances of DisplayJAI for the
	//// dj2 = new DisplayJAI(im2); // two images
	//// dj3 = new DisplayJAI(im3); // three images
	//// jsp1 = new JScrollPane(dj1); // JScrollPanes for the both
	//// jsp2 = new JScrollPane(dj2); // instances of DisplayJAI
	//// jsp3 = new JScrollPane(dj3); // instances of DisplayJAI
	//// add(jsp1);
	//// add(jsp2);
	//// add(jsp3);
	//// // Retrieve the scroll bars of the images and registers adjustment
	//// // listeners to them.
	//// // Horizontal scroll bar of the first image.
	//// jsp1.getHorizontalScrollBar().addAdjustmentListener(this);
	//// // Vertical scroll bar of the first image.
	//// jsp1.getVerticalScrollBar().addAdjustmentListener(this);
	//// // Horizontal scroll bar of the second image.
	//// jsp2.getHorizontalScrollBar().addAdjustmentListener(this);
	//// // Vertical scroll bar of the second image.
	//// jsp2.getVerticalScrollBar().addAdjustmentListener(this);
	//// // Horizontal scroll bar of the second image.
	//// jsp3.getHorizontalScrollBar().addAdjustmentListener(this);
	//// // Vertical scroll bar of the second image.
	//// jsp3.getVerticalScrollBar().addAdjustmentListener(this);
	//// }
	public DisplaySynchronizedImages(RenderedImage[] images) {
		super();
		setLayout(new GridLayout(2, 2));
		for (RenderedImage image : images) {
			DisplayJAI dj = new DisplayJAI(image); // Instances of DisplayJAI
													// for the
			JScrollPane jsp = new JScrollPane(dj); // JScrollPanes for the both
			add(jsp);
			jsp.getHorizontalScrollBar().addAdjustmentListener(this);
			jsp.getVerticalScrollBar().addAdjustmentListener(this);
		}
	}

	public void add(RenderedImage image) {
		DisplayJAI dj = new DisplayJAI(image);
		JScrollPane jsp = new JScrollPane(dj);
		add(jsp);
		jsp.getHorizontalScrollBar().addAdjustmentListener(this);
		jsp.getVerticalScrollBar().addAdjustmentListener(this);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {

	}

}