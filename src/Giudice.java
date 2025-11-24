import java.util.ArrayList;
import java.util.List;

public class Giudice {
    private boolean garaInCorso = false;
    private List<Astronave> classifica = new ArrayList<>();
    private Percorso percorso;

    public Giudice(Percorso percorso) {
        this.percorso = percorso;
    }

    public synchronized void avviaGara(List<Astronave> partecipanti) {
        if (garaInCorso) {
            System.out.println("La gara è già in corso!");
            return;
        }
        garaInCorso = true;
        System.out.println("\n--- IL GIUDICE HA DATO IL VIA ALLA GARA SUL PERCORSO: " + percorso.getNomePercorso() + " ---");

        // Avvia i thread di tutte le astronavi
        for (Astronave a : partecipanti) {
            new Thread(a).start();
        }
    }

    public synchronized void notificaAvanzamento(Astronave a) {
        if (!garaInCorso) {
            return; // Ignora notifiche se la gara è finita
        }

        // 1. Notifica l'avanzamento
        System.out.println("  [Avanzamento] " + a.getNome() + ": Percorsi " + a.getDistanzaPercorsa() + "/" + percorso.getLunghezzaTotale());

        // 2. Controllo vittoria
        if (a.getDistanzaPercorsa() >= percorso.getLunghezzaTotale()) {
            garaInCorso = false;
            classifica.add(a); // Aggiunge il vincitore come primo

            System.out.println("\n*** ASTRONAVE " + a.getNome() + " HA RAGGIUNTO IL TRAGUARDO! ***");
            terminaGara();
        }
    }

    public synchronized void terminaGara() {
        if (!garaInCorso) {
            System.out.println("\n--- CLASSIFICA FINALE ---");

            // Ordina la classifica in base alla distanza percorsa (solo per i piazzati)
            // Se la gara è terminata (garaInCorso = false) il vincitore è già in testa

            // Qui si può aggiungere la logica per trovare i piazzamenti
            // Visto che la prima ad arrivare imposta garaInCorso=false,
            // si può semplicemente stampare la lista di chi è arrivato.

            for (int i = 0; i < classifica.size(); i++) {
                System.out.println((i + 1) + ". " + classifica.get(i).getNome() + " (Dist. finale: " + classifica.get(i).getDistanzaPercorsa() + ")");
            }
            System.out.println("---------------------------\n");
        }
    }

    public boolean isGaraInCorso() {
        return garaInCorso;
    }
}
