package com.ponycornteam.tools;

public class Trigo {

	// On obtiendra l'angle du point x1/y1
	public static double angleCalc(double x1, double y1, double x2, double y2) {
		if (x2 != x1) {
			double a = Math.atan((y2 - y1) / (x2 - x1)) * 180 / Math.PI;
			if (y1 > y2)
				return a<0?-a:180-a;
			else
				return a<0?180-a:360-a;
		}
		if(y1>y2)
			return 90.0;
		else if(y2>y1)
			return 270.0;
		return 0;
	}
}
