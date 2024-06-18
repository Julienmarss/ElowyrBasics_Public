package fr.elowyr.basics.limiters;

import org.bukkit.Material;

public class Limiter {

	private Material material;
	private int limitPerChunk;
	
	public Limiter(Material material, int limitPerChunk) {
		this.material = material;
		this.limitPerChunk = limitPerChunk;
	}

	public Material getMaterial() {
		return material;
	}

	public int getLimitPerChunk() {
		return limitPerChunk;
	}
}
