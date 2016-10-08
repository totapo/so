package pack; 


public class BCP { //classe que representa um Bloco de Controle de Processo
	private int PC,X,Y; //para guradar os valores dos registradores de uso geral
	private String nome; //nome do programa
	private String[] comandos; //é uma referência ao programa na memória
	
	private Estado estado; //indica o estado atual do programa
	private int nEspera; //numero de quanta que deve esperar para sair de bloqueado
	
	public BCP(String nome, String[] comandos, Estado estado){
		this.nome = nome;
		this.comandos = comandos;
		this.estado = estado;
	}
	
	public String[] getComandos(){
		return comandos;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public int getPC(){
		return PC;
	}
	
	public int getX(){
		return X;
	}
	
	public int getY(){
		return Y;
	}
	
	public void setPC( int v){
		PC = v;
	}
	
	public void setX(int v){
		X=v;
	}
	
	public void setY(int v){
		Y=v;
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
	
}
