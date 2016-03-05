package KLD.obj;

import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.Point;

import org.junit.Test;

import KLD.Animation;

public class GameObjectTest {

	@Test
	public void Objtest() {
		double a = 0;
		double b = 0;
		Image[] im = new Image[4];
		Animation Ani = new Animation(im);
		Point p = new Point();
		GameObject gamer = new GameObject();
		GameObject gamer2 = new GameObject(a,b);
		//GameObject gamer3 = new GameObject(im[1]);
		//GameObject gamer4 = new GameObject(Ani);
		
		gamer.x();
		gamer.x(a);
		gamer.xa(a);
		gamer.xs(a);
		gamer.xm(a);
		gamer.xd(a);
		gamer.xr(a);
		gamer.x(p);
		gamer.xw();
		gamer.y();
		gamer.y(a);
		gamer.ya(a);
		gamer.ys(a);
		gamer.ym(a);
		gamer.yd(a);
		gamer.yr(a);
		gamer.y(p);
		gamer.yh();
		gamer.setXY(0, 0);
		gamer.setWH(a, b);
		gamer.width(a);
		gamer.height(a);
		
	}

}
