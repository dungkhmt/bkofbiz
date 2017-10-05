package org.bkofbiz.slp.tspd.model;

import java.util.ArrayList;

public class Tour {
	private TruckTour td;
	private ArrayList<DroneDelivery> dd;
	private double totalCost;
	private double totalTime;
	
	public TruckTour getTD() {
		return td;
	}
	public void setTD(TruckTour tD) {
		td = tD;
	}
	public ArrayList<DroneDelivery> getDD() {
		return dd;
	}
	public void setDD(ArrayList<DroneDelivery> dD) {
		dd = dD;
	}
	public Tour(TruckTour tD, ArrayList<DroneDelivery> dD) {
		super();
		td = tD;
		dd = dD;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
	public Tour(TruckTour tD, ArrayList<DroneDelivery> dD, double totalCost,
			double totalTime) {
		super();
		td = tD;
		dd = dD;
		this.totalCost = totalCost;
		this.totalTime = totalTime;
	}
	@Override
	public String toString() {
		return "Tour [TD=" + td + ", DD=" + dd + ", totalCost=" + totalCost
				+ ", totalTime=" + totalTime + "]";
	}
	
	
}
