package model;

public class Sensor {
	private int id;
	private String boiaMac;
	private int idTipo;
	private String tipo;
	
	public Sensor(int id, String boiaMac, int tipo){
		this.boiaMac = boiaMac;
		this.idTipo = tipo;
		this.id = id;
	}
	
	public void setTipo(String t){
		this.tipo = t;
	}
	
	public String getSTipo(){
		return tipo;
	}

	public int getId() {
		return id;
	}

	public String getIdBoia() {
		return boiaMac;
	}

	public int getTipo() {
		return idTipo;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + boiaMac.hashCode();
		result = prime * result + idTipo;
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
		Sensor other = (Sensor) obj;
		if (id != other.id)
			return false;
		if (boiaMac != other.boiaMac)
			return false;
		if (idTipo != other.idTipo)
			return false;
		return true;
	}

}
