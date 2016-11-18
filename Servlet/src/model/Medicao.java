package model;

public class Medicao {
	private int idSensor;
	private String boiaMac;
	private double valor; 
	private long horario;
	private int nivel;
	
	public Medicao(int idSensor, String boiaMac, double valor, long horario, int nivel){
		this.idSensor = idSensor;
		this.boiaMac = boiaMac;
		this.valor = valor;
		this.horario = horario;
		this.nivel = nivel;
	}
	
	public int getIdSensor() {
		return idSensor;
	}

	public int getNivel() {
		return nivel;
	}
	
	public long getHorario(){
		return horario;
	}
	
	public String getIdBoia() {
		return boiaMac;
	}

	public double getValor() {
		return valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (horario ^ (horario >>> 32));
		result = prime * result + boiaMac.hashCode();
		result = prime * result + idSensor;
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
		Medicao other = (Medicao) obj;
		if (horario != other.horario)
			return false;
		if (boiaMac != other.boiaMac)
			return false;
		if (idSensor != other.idSensor)
			return false;
		return true;
	}
	
}
