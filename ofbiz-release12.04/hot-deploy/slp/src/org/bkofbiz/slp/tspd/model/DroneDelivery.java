package org.bkofbiz.slp.tspd.model;

public class DroneDelivery {
	private Point lauch_node;
	private Point drone_node;
	private Point rendezvous_node;
	public Point getLauch_node() {
		return lauch_node;
	}
	public void setLauch_node(Point lauch_node) {
		this.lauch_node = lauch_node;
	}
	public Point getDrone_node() {
		return drone_node;
	}
	public void setDrone_node(Point drone_node) {
		this.drone_node = drone_node;
	}
	public Point getRendezvous_node() {
		return rendezvous_node;
	}
	public void setRendezvous_node(Point rendezvous_node) {
		this.rendezvous_node = rendezvous_node;
	}
	public boolean checkDroneDeliveryPoint(Point i){
		if(i.equals(lauch_node) || i.equals(drone_node) || i.equals(rendezvous_node)){
			return true;
		}
		return false;
	}
	public DroneDelivery(Point lauch_node, Point drone_node,
			Point rendezvous_node) {
		super();
		this.lauch_node = lauch_node;
		this.drone_node = drone_node;
		this.rendezvous_node = rendezvous_node;
	}
	@Override
	public String toString() {
//		return "DroneDelivery [lauch_node=" + lauch_node + ", drone_node="
//				+ drone_node + ", rendezvous_node=" + rendezvous_node + "]";
		return "["+lauch_node+", "+drone_node+", "+rendezvous_node+"]";
	}
}
