package KLD;

import static org.junit.Assert.*;

import java.awt.Image;

import org.junit.Test;

public class AnimationTest {

	@Test
	public void Animtest() {
		Image[] im = new Image[4];
		Image[] im2 = new Image[2];
		int[] d = new int[3];
		int q = 0;
		Animation anie = new Animation(im,q);
		try {
			Animation anie2 = new Animation(im,d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Animation anie2_5 = new Animation(im2,d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Animation anie3 = new Animation(im);		// initial and alternate constructor tests
		
		anie.getNextFrame();
		anie.clone();
		anie.getImageAt(0);
		anie.getImageAt(-1);
		anie.getCurrentImage();
		anie.getImageArray();
		anie.getDelayAt(0);
		anie.getDelayAt(-1);
		anie.getDelayArray();
		anie.getCurrentFrame();
		anie.isLooping();
		anie.isReversed();
		try {
			anie.setImages(im);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			anie.setImages(im2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		anie.setFrame(-1);
		anie.setDelays(-1);
		try {
			anie.setDelays(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		anie.goNext();
		anie.goPrevious();							//method tests
	}

}
