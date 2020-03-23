package com.bednarskar.proxycorn.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class Filter {

	private List<String> protocols;
	private List<String> countryCodes;
	private List<String> portNumbers;
	private static Filter filter = null;

	public static Filter getInstance(){
		if(filter == null) {
			filter = new Filter();
		}
		return filter;
	}
	
	private Filter() {
		this.countryCodes = new ArrayList<>();
		this.protocols = new ArrayList<>();
		this.portNumbers = new ArrayList<>();
	}
	
	public void addCountry(String country) {
		if (!this.countryCodes.contains(country)) this.countryCodes.add(country);
	}

	public void removeCountry(String country) {
		this.countryCodes.remove(country);
	}

	public void addPort(String port) {
		if (!this.portNumbers.contains(port)) this.portNumbers.add(port);
	}

	public void removePort(String port) {
		this.portNumbers.remove(port);
	}

}
