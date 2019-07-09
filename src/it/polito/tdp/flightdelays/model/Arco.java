package it.polito.tdp.flightdelays.model;

public class Arco {
	
	private String id1;
	private String id2;
	private Integer ritardo;
	public Arco(String id1, String id2, Integer ritardo) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.ritardo = ritardo;
	}
	public String getId1() {
		return id1;
	}
	public String getId2() {
		return id2;
	}
	public Integer getRitardo() {
		return ritardo;
	}
	
	
}
