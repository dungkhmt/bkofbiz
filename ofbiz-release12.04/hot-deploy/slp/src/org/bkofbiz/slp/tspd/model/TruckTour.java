package org.bkofbiz.slp.tspd.model;

import java.util.ArrayList;

public class TruckTour {
	private ArrayList<Point> truck_tour;

	public ArrayList<Point> getTruck_tour() {
		return truck_tour;
	}

	public void setTrunk_tour(ArrayList<Point> truck_tour) {
		this.truck_tour = truck_tour;
	}

	public TruckTour(ArrayList<Point> truck_tour) {
		super();
		this.truck_tour = truck_tour;
	}

	@Override
	public String toString() {
		return truck_tour.toString();
	}
	
	
}
