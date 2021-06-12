package com.leadspace.addressresolver.pojo;

/**
 * 
 * @author sncohen
 *
 */
public class Resolve {
	private String normalized;
	private String status;
	private int hereUsage;
	
	public int getHereUsage() {
		return hereUsage;
	}
	public void setHereUsage(int hereUsage) {
		this.hereUsage = hereUsage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNormalized() {
		return normalized;
	}
	public void setNormalized(String normalized) {
		this.normalized = normalized;
	}
}
