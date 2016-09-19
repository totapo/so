package pack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Escalonador implements Runnable {
	public static final int MAX_INSTRUCOES = 21;
	public static final int TEMPO_BLOQUEADO = 2;

	// para controle das filas
	private List<String> prontos;
	private List<String> bloqueados;
	private String processoAtual;

	private Map<String, BCP> tabelaProcessos; // pra manter referencia pra todos
												// os processos independente do
												// estado (guarda atÃ© os
												// finalizados)

	private int nInstrucoes; // numero total de instrucoes rodadas
	private int nQuanta; // numero de quanta necessarios para executar todos os
							// processos
	private int nTrocas; // numero toral de trocas de contexto realizadas
	private int nProcessos;// numero total de processos

	private String log = "";

	private int quantum; // numero de instrucoes
	private int nInstAtual; // numero de instrucoes executadas no quantum atual

	private Registrador PC, X, Y; // registradores

	public Escalonador(String pasta) { // le a pasta e instancia os objetos
										// necessarios
		// instancia recursos
		tabelaProcessos = new TreeMap<String, BCP>();
		prontos = new LinkedList<String>();
		bloqueados = new LinkedList<String>();
		PC = new Registrador();
		X = new Registrador();
		Y = new Registrador();

		lerArquivos(pasta);

		nInstAtual = 0;
		processoAtual = null;
	}

	private void lerArquivos(String p) {
		File pasta = new File(p);
		BufferedReader fr;
		String name, linha;
		String[] comandos;
		BCP b;
		if (pasta.isDirectory()) {
			File[] files = pasta.listFiles();
			Arrays.sort(files);
			for (File f : files) {
				try {
					fr = new BufferedReader(new FileReader(f));

					if (f.getName().equals("quantum.txt")) {
						quantum = Integer.parseInt(fr.readLine().trim());
					} else {
						nProcessos++;
						adicionaLog("Carregando " + f.getName());// Registra no
																	// log
						name = fr.readLine().trim();
						comandos = new String[21];
						for (int i = 0; i < 21
								&& (linha = fr.readLine()) != null; i++) {
							comandos[i] = linha.trim();
						}
						b = new BCP(name, comandos, Estado.PRONTO);
						tabelaProcessos.put(name, b);
						prontos.add(name);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void run() { // executa todos os programas
		BCP bcp;
		String comando;
		String[] auxiliar;
		boolean flag = false;// indica que o processo foi encerrado ou bloqueado
		while (tabelaProcessos.size() > 0) { // roda um quantum inteiro por
												// loop, exceto caso todos
												// estejam bloqueados
			// if(processoAtual==null){
			while (prontos.size() == 0) {
				decrementaTempoBloqueados(); // TODO PERGUNTAR SE DEVE CONTAR
												// QUANTUM AQUI, CASO EM Q TODOS
												// TAO BLOQUEADOS
			}
			processoAtual = prontos.remove(0); // pega o primeiro da fila de
												// prontos

			adicionaLog("Executando "
					+ tabelaProcessos.get(processoAtual).getNome());// Grava no
																	// log
			// seta os registradores conforme o bcp do processo
			bcp = tabelaProcessos.get(processoAtual);
			PC.setValor(bcp.getPC().getValor());
			X.setValor(bcp.getX().getValor());
			Y.setValor(bcp.getY().getValor());

			bcp.setEstado(Estado.EXECUTANDO);
			// }
			while (nInstAtual < quantum) {
				nInstAtual++; // aumenta o numero de instrucoes executadas no
								// quantum
				nInstrucoes++; // aumenta o numero de instrucoes executadas
				comando = bcp.getComandos()[PC.getValor()]; // pega o comando a
															// ser executado
				PC.setValor(PC.getValor() + 1); // incrementa PC

				switch (comando) {
				case "COM": // nÃ£o faz nada
					break;
				case "E/S": // bloqueia o processo, escreve no log
					bloqueia(bcp);
					flag = true;
					break;
				case "SAIDA": // encerra o processo
					encerraProcesso(bcp); // grava as informaÃ§Ãµes no log
					flag = true;
					break;
				default:
					auxiliar = comando.split("=");
					int valor = Integer.parseInt(auxiliar[1]);
					if (auxiliar[0].charAt(0) == 'X')
						X.setValor(valor);
					else
						Y.setValor(valor);
				}
				if (flag)
					break;
			}

			if (!flag) {
				trocaProcesso(bcp);
			}

			// aumenta o numero de quanta e reseta o numero de instrucoes
			// executadas
			nQuanta++;
			nInstAtual = 0;
			decrementaTempoBloqueados();
			flag = false;
		}
		gravaLog();
	}

	private void encerraProcesso(BCP bcp) {
		adicionaLog("Processo " + tabelaProcessos.get(processoAtual).getNome()
				+ " interrompido Após " + nInstAtual + " instruções");// Grava
																		// no
																		// log
		adicionaLog(tabelaProcessos.get(processoAtual).getNome()
				+ " terminado. X = " + X.getValor() + " Y = " + Y.getValor());// Grava
																				// no
																				// log
		tabelaProcessos.remove(processoAtual);
		processoAtual = null;

	}

	private void decrementaTempoBloqueados() {
		BCP b;
		List<String> remover = new ArrayList<String>();
		for (String g : bloqueados) {
			b = this.tabelaProcessos.get(g);
			b.decrementaEspera();
			if (b.getEspera() == 0) {
				remover.add(g);

				b.setEstado(Estado.PRONTO);
				prontos.add(g);
			}
		}
		bloqueados.removeAll(remover);
	}

	private void trocaProcesso(BCP bcp) { // salva o contexto e passa o processo
											// atual para o fim da fila de
											// prontos
		salvaContexto(bcp);

		bcp.setEstado(Estado.PRONTO);

		prontos.add(processoAtual);
		adicionaLog("Processo " + tabelaProcessos.get(processoAtual).getNome()
				+ " interrompido Após " + nInstAtual + " instruções");// Grava
																		// no
																		// log
		processoAtual = null;
	}

	private void bloqueia(BCP bcp) { // salva o contexto e bloqueia o processo
										// atual
		salvaContexto(bcp);

		bcp.setEspera(TEMPO_BLOQUEADO);
		bcp.setEstado(Estado.BLOQUEADO);

		bloqueados.add(processoAtual);
		adicionaLog("E/S iniciada em "
				+ tabelaProcessos.get(processoAtual).getNome());// Grava no log
		adicionaLog("Processo " + tabelaProcessos.get(processoAtual).getNome()
				+ " interrompido Após " + nInstAtual + " instruções");// Grava
																		// no
																		// log
		processoAtual = null;
	}

	private void salvaContexto(BCP bcp) { // salva o contexto do processo atual
											// em seu bcp
		bcp.getPC().setValor(PC.getValor());
		bcp.getX().setValor(X.getValor());
		bcp.getY().setValor(Y.getValor());
		nTrocas++;
	}

	// Preenche a String que guarda o log
	private void adicionaLog(String s) {
		log += s + "\n";
	}

	private void gravaLog() {
		try {
			double mediaTrocas, mediaInstQuantum;
			mediaTrocas = (double) nTrocas / (double) nProcessos;
			mediaInstQuantum = (double) nInstrucoes / (double) nQuanta;
			adicionaLog("Media de trocas: "+mediaTrocas);
			adicionaLog("Media de Instrucoes: "+mediaInstQuantum);
			adicionaLog("Quantum: "+quantum);
			String nomeArq;
			nomeArq = (quantum < 10) ? "log0" + quantum : "log" + quantum;
			File arqSaida = new File(nomeArq);
			BufferedWriter bw = new BufferedWriter(new FileWriter(arqSaida));
			bw.write(log);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
