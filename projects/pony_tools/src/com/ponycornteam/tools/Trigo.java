package com.ponycornteam.tools;

public class Trigo {

	// On obtiendra l'angle du point x1/y1
	public static double angleCalc(double x1, double y1, double x2, double y2) {
		if (y2 != y1) {
			double a = Math.toDegrees(Math.atan((x2 - x1) / (y2 - y1))
					+ Math.PI / 2)+180;
			if(y1<y2)
				return a;
			else
				return a-180;
		}
		if (x1 > x2)
			return 180;
		else
			return 0;
	}
	
	//Permet d'avoir la destination d'un point si on applique un angle avec ça vitesse
	public static Coord calcDest(Coord start, double speed){
		return new Coord(Math.cos(start.angle/180*Math.PI)*speed, Math.cos(start.angle/180*Math.PI)*speed, start.angle);
	}
}
