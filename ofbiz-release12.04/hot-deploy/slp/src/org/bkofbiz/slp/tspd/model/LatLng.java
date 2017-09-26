package org.bkofbiz.slp.tspd.model;

import java.io.Serializable;

public class LatLng implements Serializable{
	private double lat;
	private double lng;
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
	public LatLng(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	@Override
	public String toString() {
		return "LatLng [lat=" + lat + ", lng=" + lng + "]";
	}
	

}
