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

        RisultatoGara risultato = new RisultatoGara(
                percorso.getNomePercorso(),
                percorso.getLunghezzaTotale()
        );

        for (int i = 0; i < classifica.size(); i++) {
            Astronave a = classifica.get(i);
            risultato.aggiungiAstronave(i + 1, a.getNome(), a.getDistanzaPercorsa());
        }

        String timestamp = LocalDateTime.now().format(FORMATO_DATA);
        String nomeFile = DIRECTORY_CLASSIFICHE + "/classifica_" +
                percorso.getNomePercorso().replaceAll(" ", "_") +
                "_" + timestamp + ".json";

        return salvaJSON(risultato, nomeFile);
    }

    public boolean salvaJSON(RisultatoGara risultato, String nomeFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            String json = convertiInJSON(risultato);
            writer.write(json);
            System.out.println("\n✓ Classifica salvata con successo in: " + nomeFile);
            return true;
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
            return false;
        }
    }

    public RisultatoGara leggiJSON(String nomeFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            StringBuilder json = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                json.append(linea);
            }
            return convertiDaJSON(json.toString());
        } catch (IOException e) {
            System.err.println("Errore durante la lettura: " + e.getMessage());
            return null;
        }
    }

    private String convertiInJSON(RisultatoGara risultato) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"percorso\": \"").append(risultato.getPercorso()).append("\",\n");
        json.append("  \"lunghezzaPercorso\": ").append(risultato.getLunghezzaPercorso()).append(",\n");
        json.append("  \"dataOra\": \"").append(risultato.getDataOra()).append("\",\n");
        json.append("  \"classifica\": [\n");

        List<RisultatoGara.ClassificaEntry> classifica = risultato.getClassifica();
        for (int i = 0; i < classifica.size(); i++) {
            RisultatoGara.ClassificaEntry entry = classifica.get(i);
            json.append("    {\n");
            json.append("      \"posizione\": ").append(entry.getPosizione()).append(",\n");
            json.append("      \"nomeAstronave\": \"").append(entry.getNomeAstronave()).append("\",\n");
            json.append("      \"distanzaFinale\": ").append(entry.getDistanzaFinale()).append("\n");
            json.append("    }");
            if (i < classifica.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]\n");
        json.append("}");
        return json.toString();
    }

    private RisultatoGara convertiDaJSON(String json) {
        RisultatoGara risultato = new RisultatoGara();

        // Parsing semplice (per una versione più robusta usare una libreria come Gson o Jackson)
        risultato.setPercorso(estraiValore(json, "percorso"));
        risultato.setLunghezzaPercorso(Integer.parseInt(estraiValoreNumerico(json, "lunghezzaPercorso")));
        risultato.setDataOra(estraiValore(json, "dataOra"));

        // Parsing classifica (semplificato)
        String classificaStr = json.substring(json.indexOf("\"classifica\":"));
        // Per semplicità, qui dovresti implementare il parsing completo dell'array

        return risultato;
    }

    private String estraiValore(String json, String chiave) {
        String pattern = "\"" + chiave + "\": \"";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private String estraiValoreNumerico(String json, String chiave) {
        String pattern = "\"" + chiave + "\": ";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("\n", start);
        return json.substring(start, end).trim();
    }
}