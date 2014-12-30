package com.assignment.ID3.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private List<Node> children;
	private ArrayList<ArrayList<Record>> records;
	private int offset;
	private int nodeId;
	
	public Node(int offset, int nodeId) {
		children = new ArrayList<Node>();
		records = null;
		this.offset = offset;
		this.nodeId = nodeId;
	}
	
	public int getNodeId(){
		return nodeId;
	}
	
	public String getName(){
		return offset + "";
	}
	
	public int getOffset(){
		return offset;
	}
	
	public void addChild(Node n){
		children.add(n);
	}
	
	public List<Node> getChildren(){
		return children;
	}
	
	public ArrayList<ArrayList<Record>> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<ArrayList<Record>> records) {
		this.records = records;
	}
}
