import java.util.ArrayList;
import java.util.List;

public class Giudice {
    private boolean garaInCorso = false;
    private List<Astronave> classifica = new ArrayList<>();
    private Percorso percorso;
    private GestoreFile gestoreFile;
    private int numeroPartecipanti;
    private int astronaviArrivate = 0;

    public Giudice(Percorso percorso) {
        this.percorso = percorso;
        this.gestoreFile = new GestoreFile();
    }

    public synchronized void avviaGara(List<Astronave> partecipanti) {
        if (garaInCorso) {
            System.out.println("La gara è già in corso!");
            return;
        }
        garaInCorso = true;
        numeroPartecipanti = partecipanti.size();
        System.out.println("\n--- IL GIUDICE HA DATO IL VIA ALLA GARA SUL PERCORSO: " + percorso.getNomePercorso() + " ---");

        for (Astronave a : partecipanti) {
            new Thread(a).start();
        }
    }

    public synchronized void notificaAvanzamento(Astronave a) {
        if (!garaInCorso) {
            return;
        }

        System.out.println("  [Avanzamento] " + a.getNome() + ": Percorsi " + a.getDistanzaPercorsa() + "/" + percorso.getLunghezzaTotale());

        if (a.getDistanzaPercorsa() >= percorso.getLunghezzaTotale()) {
            if (!classifica.contains(a)) {
                classifica.add(a);
                astronaviArrivate++;

                System.out.println("\n*** ASTRONAVE " + a.getNome() + " HA RAGGIUNTO IL TRAGUARDO! (Posizione: " + astronaviArrivate + ") ***");

                if (astronaviArrivate >= numeroPartecipanti) {
                    garaInCorso = false;
                    terminaGara();
                }
            }
        }
    }

    public synchronized void terminaGara() {
        System.out.println("\n--- CLASSIFICA FINALE ---");

        for (int i = 0; i < classifica.size(); i++) {
            System.out.println((i + 1) + ". " + classifica.get(i).getNome() + " (Dist. finale: " + classifica.get(i).getDistanzaPercorsa() + ")");
        }
        System.out.println("---------------------------\n");

        gestoreFile.salvaClassifica(classifica, percorso);
    }

    public boolean isGaraInCorso() {
        return garaInCorso;
    }
}