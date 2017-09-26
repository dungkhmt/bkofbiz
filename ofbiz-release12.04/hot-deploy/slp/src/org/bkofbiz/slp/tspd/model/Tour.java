package org.bkofbiz.slp.tspd.model;

import java.util.ArrayList;

public class Tour {
	private TruckTour td;
	private ArrayList<DroneDelivery> dd;
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
	@Override
	public String toString() {
		return "Tour [ TD=" + td.toString() + ", DD=" + dd.toString() + "]";
	}
	
}
