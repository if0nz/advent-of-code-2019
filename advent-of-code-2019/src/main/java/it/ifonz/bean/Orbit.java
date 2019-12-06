package it.ifonz.bean;

public class Orbit {
	public String center;
	public String orbiting;

	public Orbit(String[] o) {
		this.center = o[0];
		this.orbiting = o[1];
	}
}