package com.main;

public class Tapsiriq {

	private int idSutun;
	private String adSutun;
	private String tarixSutun;
	private int gunSutun;
	private int qalangunSutun;
	private String kateqoriyaSutun;
	private String statusSutun;

	public int getIdSutun() {
		return idSutun;
	}

	public void setIdSutun(int idSutun) {
		this.idSutun = idSutun;
	}

	public String getAdSutun() {
		return adSutun;
	}

	public void setAdSutun(String adSutun) {
		this.adSutun = adSutun;
	}

	public String getTarixSutun() {
		return tarixSutun;
	}

	public void setTarixSutun(String tarixSutun) {
		this.tarixSutun = tarixSutun;
	}

	public int getGunSutun() {
		return gunSutun;
	}

	public void setGunSutun(int gunSutun) {
		this.gunSutun = gunSutun;
	}

	public int getQalangunSutun() {
		return qalangunSutun;
	}

	public void setQalangunSutun(int qalangunSutun) {
		this.qalangunSutun = qalangunSutun;
	}

	public String getKateqoriyaSutun() {
		return kateqoriyaSutun;
	}

	public void setKateqoriyaSutun(String kateqoriyaSutun) {
		this.kateqoriyaSutun = kateqoriyaSutun;
	}

	public String getStatusSutun() {
		return statusSutun;
	}

	public void setStatusSutun(String statusSutun) {
		this.statusSutun = statusSutun;
	}

	public Tapsiriq(int idSutun, String adSutun, String tarixSutun, int gunSutun, int qalangunSutun,
			String kateqoriyaSutun, String statusSutun) {
		super();
		this.idSutun = idSutun;
		this.adSutun = adSutun;
		this.tarixSutun = tarixSutun;
		this.gunSutun = gunSutun;
		this.qalangunSutun = qalangunSutun;
		this.kateqoriyaSutun = kateqoriyaSutun;
		this.statusSutun = statusSutun;
	}

}
