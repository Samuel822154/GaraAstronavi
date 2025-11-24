import java.util.Random;
public class Astronave implements Runnable {
    private final String nome;
    private final int velocitaBase; // Metri/secondo
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

    // Il cuore del thread di gara
    @Override
    public void run() {
        System.out.println(nome + " si prepara per la partenza (Velocità: " + velocitaBase + "m/s).");

        while (distanzaPercorsa < percorso.getLunghezzaTotale() && giudice.isGaraInCorso()) {
            try {
                // Simula 1 secondo di movimento
                Thread.sleep(1000);

                avanza();

                // Notifica l'avanzamento al Giudice per controllo vittoria e output
                giudice.notificaAvanzamento(this);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nome + " è stata interrotta.");
                break;
            }
        }
    }

    // Metodo per calcolare l'avanzamento, includendo una piccola variabilità
    private void avanza() {
        // Aggiunge una piccola variazione per rendere la gara dinamica (es. +/- 5%)
        int scarto = (int) (velocitaBase * 0.05);
        int avanzamento = velocitaBase + random.nextInt(scarto * 2 + 1) - scarto;

        distanzaPercorsa += avanzamento;

        // Assicura che la distanza non superi di troppo il traguardo
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
