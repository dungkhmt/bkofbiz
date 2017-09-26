package org.bkofbiz.slp.tspd.model;

import java.util.Arrays;

public class TSPDSolution {
	Tour[] tours;
	private double truckspeed; //Speed of truck
	private double dronespeed; //Speed of drone
	private int truckcost;//cost per unit of trunk
	private int dronecost; //cost per unit of drone
	private double delta;
	private double endurance;

	public TSPDSolution(Tour[] tours, double truckSpeed, double droneSpeed,
			int truckCost, int droneCost, double delta, double endurance) {
		super();
		this.tours = tours;
		this.truckspeed = truckSpeed;
		this.dronespeed = droneSpeed;
		this.truckcost = truckCost;
		this.dronecost = droneCost;
		this.delta = delta;
		this.endurance = endurance;
	}

	public Tour[] getTours() {
		return tours;
	}

	public void setTours(Tour[] tours) {
		this.tours = tours;
	}

	public double getTruckSpeed() {
		return truckspeed;
	}

	public void setTruckSpeed(double truckSpeed) {
		this.truckspeed = truckSpeed;
	}

	public double getDroneSpeed() {
		return dronespeed;
	}

	public void setDroneSpeed(double droneSpeed) {
		this.dronespeed = droneSpeed;
	}

	public int getTruckCost() {
		return truckcost;
	}

	public void setTruckCost(int truckCost) {
		this.truckcost = truckCost;
	}

	public int getDroneCost() {
		return dronecost;
	}

	public void setDroneCost(int droneCost) {
		this.dronecost = droneCost;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public double getEndurance() {
		return endurance;
	}

	public void setEndurance(double endurance) {
		this.endurance = endurance;
	}

	@Override
	public String toString() {
		return "TSPDSolution [tours=" + Arrays.toString(tours)
				+ ", truckSpeed=" + truckspeed + ", droneSpeed=" + dronespeed
				+ ", truckCost=" + truckcost + ", droneCost=" + dronecost
				+ ", delta=" + delta + ", endurance=" + endurance + "]";
	}
	
	
}
