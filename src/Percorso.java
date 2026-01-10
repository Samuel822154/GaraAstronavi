public class Percorso {
    private int lunghezzaTotale;
    private String nomePercorso;

    public Percorso(int lunghezza, String nome) {
        this.lunghezzaTotale = lunghezza;
        this.nomePercorso = nome;
        System.out.println("Percorso creato: " + nome + " (" + lunghezza + " metri)");
    }

    public int getLunghezzaTotale() {
        return lunghezzaTotale;
    }

    public String getNomePercorso() {
        return nomePercorso;
    }
}