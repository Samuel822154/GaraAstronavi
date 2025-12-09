import java.util.Arrays;
import java.util.List;

public class GaraAstronave {
    public static void main(String[] args) {
        Percorso marteExpress = new Percorso(200, "Marte Express");
        Giudice giudice = new Giudice(marteExpress);

        Astronave a1 = new Astronave("Alpha-7", 25, marteExpress, giudice);
        Astronave a2 = new Astronave("Beta-9", 30, marteExpress, giudice);
        Astronave a3 = new Astronave("Gamma-5", 28, marteExpress, giudice);

        List<Astronave> partecipanti = Arrays.asList(a1, a2, a3);

        giudice.avviaGara(partecipanti);
    }
}