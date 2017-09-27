package org.bkofbiz.slp.tspd.model;

import java.util.Arrays;

public class TSPDSolution {
	Tour[] tours;
	private double truckSpeed; //Speed of truck
	private double droneSpeed; //Speed of drone
	private int truckCost;//cost per unit of trunk
	private int droneCost; //cost per unit of drone
	private double delta;
	private double endurance;

	public TSPDSolution(Tour[] tours, double truckSpeed, double droneSpeed,
			int truckCost, int droneCost, double delta, double endurance) {
		super();
		this.tours = tours;
		this.truckSpeed = truckSpeed;
		this.droneSpeed = droneSpeed;
		this.truckCost = truckCost;
		this.droneCost = droneCost;
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
		return truckSpeed;
	}

	public void setTruckSpeed(double truckSpeed) {
		this.truckSpeed = truckSpeed;
	}

	public double getDroneSpeed() {
		return droneSpeed;
	}

	public void setDroneSpeed(double droneSpeed) {
		this.droneSpeed = droneSpeed;
	}

	public int getTruckCost() {
		return truckCost;
	}

	public void setTruckCost(int truckCost) {
		this.truckCost = truckCost;
	}

	public int getDroneCost() {
		return droneCost;
	}

	public void setDroneCost(int droneCost) {
		this.droneCost = droneCost;
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
				+ ", truckSpeed=" + truckSpeed + ", droneSpeed=" + droneSpeed
				+ ", truckCost=" + truckCost + ", droneCost=" + droneCost
				+ ", delta=" + delta + ", endurance=" + endurance + "]";
	}
	
	
}
