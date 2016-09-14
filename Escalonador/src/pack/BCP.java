package pack; 


public class BCP implements Comparable<BCP>{
	private Registrador PC,X,Y;
	private String nome;
	private String[] comandos; //considere como se fosse o programa na memória
	
	private Estado estado;
	private int nEspera; //numero de quanta que deve esperar para sair de bloqueado
	
	public BCP(String nome, String[] comandos, Estado estado){
		this.nome = nome;
		PC = new Registrador();
		X = new Registrador();
		Y = new Registrador();
		this.comandos = comandos;
		this.estado = estado;
	}
	
	public String[] getComandos(){
		return comandos;
	}
	
	public Registrador getPC(){
		return PC;
	}
	
	public Registrador getX(){
		return X;
	}
	
	public Registrador getY(){
		return Y;
	}

	public Estado getEstado(){
		return estado;
	}
	
	public void setEstado(Estado estado){
		this.estado = estado;
	}
	
	public void setEspera(int quanta){
		this.nEspera = quanta;
	}
	
	public void decrementaEspera(){
		this.nEspera--;
	}
	
	public int getEspera(){
		return nEspera;
	}

	@Override
	public int compareTo(BCP o) {
		return this.nome.compareTo(o.nome);
	}
	
}
