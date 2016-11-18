package model;

public class Boia {
	private String mac;
	
	public Boia(String mac){
		this.mac = mac;
	}

	public String getMac() {
		return mac;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*mac.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Boia other = (Boia) obj;
		if (mac != other.mac)
			return false;
		return true;
	}
	
}
