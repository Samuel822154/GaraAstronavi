import java.util.Random;

public class Astronave implements Runnable {
    private final String nome;
    private final int velocitaBase;
    private volatile int distanzaPercorsa = 0;
    private final Percorso percorso;
    private final Giudice giudice;
    private final Random random = new Random();

    public Astronave(String nome, int velocitaBase, Percorso percorso, Giudice giudice) {
        this.nome = nome;
        this.velocitaBase = velocitaBase;
        this.percorso = percorso;
        this.giudice = giudice;
    }

    @Override
    public void run() {
        System.out.println(nome + " si prepara per la partenza (Velocità: " + velocitaBase + "m/s).");

        while (distanzaPercorsa < percorso.getLunghezzaTotale() && giudice.isGaraInCorso()) {
            try {
                Thread.sleep(1000);
                avanza();
                giudice.notificaAvanzamento(this);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nome + " è stata interrotta.");
                break;
            }
        }
    }

    private void avanza() {
        int scarto = (int) (velocitaBase * 0.05);
        int avanzamento = velocitaBase + random.nextInt(scarto * 2 + 1) - scarto;
        distanzaPercorsa += avanzamento;

        if (distanzaPercorsa > percorso.getLunghezzaTotale()) {
            distanzaPercorsa = percorso.getLunghezzaTotale();
        }
    }

    public int getDistanzaPercorsa() {
        return distanzaPercorsa;
    }

    public String getNome() {
        return nome;
    }
}