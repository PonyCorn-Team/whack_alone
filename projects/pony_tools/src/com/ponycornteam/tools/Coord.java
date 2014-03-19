package com.ponycornteam.tools;

public class Coord {
	public double x=0.0, y=0.0, angle=0.0;
	public static enum direction {right,left,top,bot};
	public Coord(double px, double py)
	{
		x = px;
		y = py;
	}
	public Coord(double px, double py, double ang)
	{
		x = px;
		y = py;
		angle = ang;
	}
}
