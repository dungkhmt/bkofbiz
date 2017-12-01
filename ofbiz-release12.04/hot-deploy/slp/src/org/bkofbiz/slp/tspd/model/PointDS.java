package org.bkofbiz.slp.tspd.model;

import java.io.Serializable;

public class PointDS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String P_Id;
	private String P_Lat;
	private String P_Lng;
	private String DS_Id;
	private String P_AllowDrone;
	
	public String getP_AllowDrone() {
		return P_AllowDrone;
	}
	public void setP_AllowDrone(String p_AllowDrone) {
		P_AllowDrone = p_AllowDrone;
	}
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
	public PointDS(String p_Id, String p_Lat, String p_Lng, String dS_Id,
			String p_AllowDrone) {
		super();
		P_Id = p_Id;
		P_Lat = p_Lat;
		P_Lng = p_Lng;
		DS_Id = dS_Id;
		P_AllowDrone = p_AllowDrone;
	}
	
	

}
