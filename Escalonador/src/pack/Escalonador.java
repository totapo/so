package pack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Escalonador {
	public static final int MAX_INSTRUCOES = 21;
	public static final int TEMPO_BLOQUEADO = 2;
	
	//para controle das filas
	private List<String> prontos;
	private List<String> bloqueados;
	private String processoAtual;
	
	private Map<String, BCP> tabelaProcessos; //pra manter referencia pra todos os processos independente do estado (guarda até os finalizados)
	
	private int nInstrucoes; //numero total de instrucoes rodadas
	private int nQuanta; //numero de quantuns necessarios para executar todos os processos
	private int nTrocas; //numero toral de trocas de contexto realizadas
	
	private int quantum; //numero de instrucoes
	private int nInstAtual; //numero de instrucoes executadas no quantum atual
	
	private Registrador PC,X,Y; //registradores
	
	public Escalonador(String pasta){ //le a pasta e instancia os objetos necessarios
		tabelaProcessos = new HashMap<String, BCP>();
		
		nInstAtual=0;
		processoAtual="";
	}

	public void run(){ //executa todos os programas
		while(tabelaProcessos.size()>0){
			if(processoAtual==""){
				if(prontos.size()>0){
					processoAtual = prontos.remove(0); //pega o primeiro da fila de prontos
					
					//seta os registradores conforme o bcp do processo
					BCP bcp = tabelaProcessos.get(processoAtual);
					PC.setValor(bcp.getPC().getValor());
					X.setValor(bcp.getX().getValor());
					Y.setValor(bcp.getY().getValor());
				} else {
					//caso em que todos estao bloqueados
				}
			}
			while(nInstAtual <= quantum){
				
			}
		}
	}
	
	public void trocaProcesso(){
		BCP bcp = tabelaProcessos.get(processoAtual);
		
		salvaContexto(bcp);
		
		
	}
	
	public void bloqueia(){ //salva o contexto e bloqueia o processo atual
		BCP bcp = tabelaProcessos.get(processoAtual);
		
		salvaContexto(bcp);
		
		bcp.setEspera(TEMPO_BLOQUEADO);
		bcp.setEstado(Estado.BLOQUEADO);
		
		bloqueados.add(processoAtual);
		processoAtual="";
	}
	
	public void salvaContexto(BCP bcp){ //salva o contexto do processo atual em seu bcp
		bcp.getPC().setValor(PC.getValor());
		bcp.getX().setValor(X.getValor());
		bcp.getY().setValor(Y.getValor());
		nTrocas++;
	}
}
