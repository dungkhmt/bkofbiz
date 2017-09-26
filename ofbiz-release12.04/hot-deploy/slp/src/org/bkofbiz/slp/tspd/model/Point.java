package org.bkofbiz.slp.tspd.model;

public class Point {
	private String id;
	private double lat;
	private double lng;
	public String getID() {
		return id;
	}
	public void setID(String iD) {
		id = iD;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public Point(String iD, double lat, double lng) {
		super();
		id = iD;
		this.lat = lat;
		this.lng = lng;
	}
	public boolean equals(Point other){
		return other.getID() == this.id;
	}
	@Override
	public String toString() {
		//return "Point [ID=" + ID + ", lat=" + lat + ", lng=" + lng + "]";
		return ""+id;
	}
	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
