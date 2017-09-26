package org.bkofbiz.slp.tspd.model;

import java.io.Serializable;

public class Direction implements Serializable {
	private String D_Id;
	private String D_StartPointId;
	private String D_EndPointId;
	private String D_Distance;
	private String D_Time;
	private String DS_Id;
	private String D_Path;
	public String getD_Id() {
		return D_Id;
	}
	public void setD_Id(String d_Id) {
		D_Id = d_Id;
	}
	public String getD_StartPointId() {
		return D_StartPointId;
	}
	public void setD_StartPointId(String d_StartPointId) {
		D_StartPointId = d_StartPointId;
	}
	public String getD_EndPointId() {
		return D_EndPointId;
	}
	public void setD_EndPointId(String d_EndPointId) {
		D_EndPointId = d_EndPointId;
	}
	public String getD_Distance() {
		return D_Distance;
	}
	public void setD_Distance(String d_Distance) {
		D_Distance = d_Distance;
	}
	public String getD_Time() {
		return D_Time;
	}
	public void setD_Time(String d_Time) {
		D_Time = d_Time;
	}
	public String getDS_Id() {
		return DS_Id;
	}
	public void setDS_Id(String dS_Id) {
		DS_Id = dS_Id;
	}
	public String getD_Path() {
		return D_Path;
	}
	public void setD_Path(String d_Path) {
		D_Path = d_Path;
	}
	public Direction(String d_Id, String d_StartPointId, String d_EndPointId,
			String d_Distance, String d_Time, String dS_Id, String d_Path) {
		super();
		D_Id = d_Id;
		D_StartPointId = d_StartPointId;
		D_EndPointId = d_EndPointId;
		D_Distance = d_Distance;
		D_Time = d_Time;
		DS_Id = dS_Id;
		D_Path = d_Path;
	}
	

}
