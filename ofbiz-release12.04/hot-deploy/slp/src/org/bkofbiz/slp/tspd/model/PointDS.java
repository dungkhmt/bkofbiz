package org.bkofbiz.slp.tspd.model;

import java.io.Serializable;

public class PointDS implements Serializable{
	private String P_Id;
	private String P_Lat;
	private String P_Lng;
	private String DS_Id;
	public String getP_Id() {
		return P_Id;
	}
	public void setP_Id(String p_Id) {
		P_Id = p_Id;
	}
	public String getP_Lat() {
		return P_Lat;
	}
	public void setP_Lat(String p_Lat) {
		P_Lat = p_Lat;
	}
	public String getP_Lng() {
		return P_Lng;
	}
	public void setP_Lng(String p_Lng) {
		P_Lng = p_Lng;
	}
	public String getDS_Id() {
		return DS_Id;
	}
	public void setDS_Id(String dS_Id) {
		DS_Id = dS_Id;
	}
	public PointDS(String p_Id, String p_Lat, String p_Lng, String dS_Id) {
		super();
		P_Id = p_Id;
		P_Lat = p_Lat;
		P_Lng = p_Lng;
		DS_Id = dS_Id;
	}
	

}
