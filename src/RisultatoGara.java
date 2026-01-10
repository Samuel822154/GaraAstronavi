import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RisultatoGara {
    private String percorso;
    private int lunghezzaPercorso;
    private String dataOra;
    private List<ClassificaEntry> classifica;

    public RisultatoGara() {
        this.classifica = new ArrayList<>();
    }

    public RisultatoGara(String percorso, int lunghezzaPercorso) {
        this.percorso = percorso;
        this.lunghezzaPercorso = lunghezzaPercorso;
        this.dataOra = LocalDateTime.now().toString();
        this.classifica = new ArrayList<>();
    }

    public void aggiungiAstronave(int posizione, String nome, int distanza) {
        classifica.add(new ClassificaEntry(posizione, nome, distanza));
    }

    // Getters e Setters
    public String getPercorso() {
        return percorso;
    }

    public void setPercorso(String percorso) {
        this.percorso = percorso;
    }

    public int getLunghezzaPercorso() {
        return lunghezzaPercorso;
    }

    public void setLunghezzaPercorso(int lunghezzaPercorso) {
        this.lunghezzaPercorso = lunghezzaPercorso;
    }

    public String getDataOra() {
        return dataOra;
    }

    public void setDataOra(String dataOra) {
        this.dataOra = dataOra;
    }

    public List<ClassificaEntry> getClassifica() {
        return classifica;
    }

    public void setClassifica(List<ClassificaEntry> classifica) {
        this.classifica = classifica;
    }

    public static class ClassificaEntry {
        private int posizione;
        private String nomeAstronave;
        private int distanzaFinale;

        public ClassificaEntry() {}

        public ClassificaEntry(int posizione, String nomeAstronave, int distanzaFinale) {
            this.posizione = posizione;
            this.nomeAstronave = nomeAstronave;
            this.distanzaFinale = distanzaFinale;
        }

        public int getPosizione() {
            return posizione;
        }

        public void setPosizione(int posizione) {
            this.posizione = posizione;
        }

        public String getNomeAstronave() {
            return nomeAstronave;
        }

        public void setNomeAstronave(String nomeAstronave) {
            this.nomeAstronave = nomeAstronave;
        }

        public int getDistanzaFinale() {
            return distanzaFinale;
        }

        public void setDistanzaFinale(int distanzaFinale) {
            this.distanzaFinale = distanzaFinale;
        }
    }
}
