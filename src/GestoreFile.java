import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GestoreFile {
    private static final String DIRECTORY_CLASSIFICHE = "classifiche";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public GestoreFile() {
        File directory = new File(DIRECTORY_CLASSIFICHE);
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Directory '" + DIRECTORY_CLASSIFICHE + "' creata.");
        }
    }

    public boolean salvaClassifica(List<Astronave> classifica, Percorso percorso) {
        if (classifica == null || classifica.isEmpty()) {
            System.err.println("Errore: Classifica vuota, impossibile salvare.");
            return false;
        }

        String timestamp = LocalDateTime.now().format(FORMATO_DATA);
        String nomeFile = DIRECTORY_CLASSIFICHE + "/classifica_" +
                percorso.getNomePercorso().replaceAll(" ", "_") +
                "_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            writer.write("==============================================\n");
            writer.write("    CLASSIFICA GARA ASTRONAVI\n");
            writer.write("==============================================\n");
            writer.write("Percorso: " + percorso.getNomePercorso() + "\n");
            writer.write("Lunghezza: " + percorso.getLunghezzaTotale() + " metri\n");
            writer.write("Data e ora: " + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            writer.write("==============================================\n\n");

            writer.write("POSIZIONE | ASTRONAVE      | DISTANZA FINALE\n");
            writer.write("----------------------------------------------\n");

            for (int i = 0; i < classifica.size(); i++) {
                Astronave a = classifica.get(i);
                String posizione = String.format("%-9d", (i + 1));
                String nome = String.format("%-14s", a.getNome());
                String distanza = String.format("%d/%d m",
                        a.getDistanzaPercorsa(),
                        percorso.getLunghezzaTotale());

                writer.write(posizione + " | " + nome + " | " + distanza + "\n");
            }

            writer.write("----------------------------------------------\n");
            writer.write("\nVincitore: " + classifica.get(0).getNome() + "\n");

            System.out.println("\n✓ Classifica salvata con successo in: " + nomeFile);
            return true;

        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio della classifica: " + e.getMessage());
            return false;
        }
    }

    public boolean salvaLogDettagliato(List<String> eventi, Percorso percorso) {
        String timestamp = LocalDateTime.now().format(FORMATO_DATA);
        String nomeFile = DIRECTORY_CLASSIFICHE + "/log_" +
                percorso.getNomePercorso().replaceAll(" ", "_") +
                "_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            writer.write("LOG DETTAGLIATO GARA\n");
            writer.write("Percorso: " + percorso.getNomePercorso() + "\n");
            writer.write("==============================================\n\n");

            for (String evento : eventi) {
                writer.write(evento + "\n");
            }

            System.out.println("✓ Log dettagliato salvato in: " + nomeFile);
            return true;

        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del log: " + e.getMessage());
            return false;
        }
    }
}