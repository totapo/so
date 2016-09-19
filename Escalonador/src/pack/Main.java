package pack;

public class Main {

	public static void main(String[] args) {
		Thread t = new Thread(new Escalonador("processos"));
		t.start();

	}

}
