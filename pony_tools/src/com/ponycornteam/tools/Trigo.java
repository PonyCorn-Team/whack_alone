package com.ponycornteam.tools;

public class Trigo {

	// On obtiendra l'angle du point x1/y1
	public static double angleCalc(double x1, double y1, double x2, double y2) {
		if (x2 != y2)
			return Math.atan((y2 - y1) / (x2 - x1));
		return 0.0;
	}
}
