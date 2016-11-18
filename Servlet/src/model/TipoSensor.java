package model;

public class TipoSensor {
	private int id;
	private String descricao;
	
	public TipoSensor(int id, String desc){
		this.id = id;
		this.descricao = desc;
	}

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		TipoSensor other = (TipoSensor) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
