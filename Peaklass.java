import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class Peaklass {
    public static void main(String[] args) throws IOException, AjakuluErind, ÜlesandeErind {

        while (true) { // küsib nii kaua kuni vajutatakse cancel

            //pildi suuruse ja ikooni tekitamine
            ImageIcon icon = new ImageIcon("pepe.jpg"); // avab pildi
            Image originaalpilt = icon.getImage(); //teeb ikooniks
            Image muudetudPilt = originaalpilt.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);// annad suurused
            ImageIcon õigedSuurusedPilt = new ImageIcon(muudetudPilt); // teeb õigete suurustega ikooni

            // viskab boxi, et saaksid ülesandeid kirjutada
            String ülesanne = (String) JOptionPane.showInputDialog(null, "Mis ülessannet sul vaja teha on?", "To-do list", JOptionPane.QUESTION_MESSAGE,õigedSuurusedPilt,null,"");
            if (Objects.equals(ülesanne, "")) {
                throw new ÜlesandeErind("Ülesande kirjeldus ei tohi olla tühi.");
            }
            if (ülesanne == null) { // kui vajutab cancel
                tehtudÜlesanded(lugedaFailist("ülesanded.txt")); // algul seal sees loeb selle faili ära ja siis tagastab selle listi sinna tehtudülesanded meetodisse
                break;
            } else { // kui vajutab ok ehk kirjutab edasi ülesandeid
                String ajakulu = (String) JOptionPane.showInputDialog(null, "Kui palju sul selleks aega kulub (minutites)?", "To-do list", JOptionPane.QUESTION_MESSAGE,õigedSuurusedPilt,null,"");
                if (Integer.parseInt(ajakulu) <= 0) {
                    throw new AjakuluErind("Ajakulu peab olema positiivne!");
                }
                int kasOnKiire = JOptionPane.showConfirmDialog(null, "Kas sul on sellega kiire?", "To-do list", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, õigedSuurusedPilt);
                String kiire;
                if (kasOnKiire == 0) {
                    kiire = "kiire";
                } else {
                    kiire = "pole kiire";
                }

                //hakkab kirjutama faili ülesanded.txt mille ise tekitab
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ülesanded.txt", true), StandardCharsets.UTF_8))) {
                    bw.write(ülesanne + ";" + ajakulu + ";" + kiire);
                    bw.newLine();
                }
            }
        }
    }

    private static void tehtudÜlesanded(List<String> lugedaFailist) {
        String[] options2 = lugedaFailist.toArray(new String[0]); // ta teeb sellest loetud listist massiivi kuna meil graafiliselt seda vaja massiivina
        List<Ülesanne> ülesanded = new ArrayList<>();

        for (int i = 0; i < options2.length; i++) { // käib kõik massiivi elemendid (ehk read/ülesanded eraldi läbi)
            String rida = options2[i]; // võtab vaadeldava rea
            String[] reaOsad = rida.split(";");// teeb osadeks
            Ülesanne ülesanne;
            if (Objects.equals(reaOsad[2], "1")) { // 1 tähendab, et tal pole sellega kiire
              ülesanne = new TavalineÜlesanne(reaOsad[0], Integer.parseInt(reaOsad[1])); // kutsub siis tava ülesannete klassi
            } else { // 0 tähendab, et on kiire
              ülesanne = new KiireÜlesanne(reaOsad[0], Integer.parseInt(reaOsad[1])); // kui vastas jah siis kutsub kiired
            }
            ülesanded.add(ülesanne);
        }
        // järjestab ülesanded nii, et kiired on enne ja kõik on ajakulu järgi järjestatud
        ülesanded.sort(Comparator.comparing(Ülesanne::getAjakulu).thenComparing(Ülesanne::getKirjeldus, String.CASE_INSENSITIVE_ORDER));

        while (true) {

            // teise pildi sätted
            ImageIcon icon2 = new ImageIcon("pepe2.jpg");
            Image originaalpilt2 = icon2.getImage();
            Image muudetudPilt2 = originaalpilt2.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon õigedSuurusedPilt2 = new ImageIcon(muudetudPilt2);

            // ta näitab kõiki to do-sid ja see options2 on kõikide ridade list
            String tehtud = (String) JOptionPane.showInputDialog(null, "Mis sul tehtud sai?",
                    "To-do list", JOptionPane.QUESTION_MESSAGE, õigedSuurusedPilt2, options2, options2[0]);

            if (tehtud == null){ // kui vajutab cancel siis lõpetab lic töö
                break;
            }

            // see siin selleks et ta removeks selle ühe to do ja siis convertib tagasi massiiviks
            List<String> list = new ArrayList<>(Arrays.asList(options2));
            list.remove(tehtud);
            options2 = list.toArray(new String[0]);

            // ta avab selle ülesanded faili ja kirjutab nüüd alles olevate ülesannetega uuesti selle faili üle(me just removesime ehk ta kirjutab kõik peale selle mis tehtud sai)
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ülesanded.txt"), StandardCharsets.UTF_8))) {
                for (String item : options2) {
                    bw.write(item);
                    bw.newLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (list.isEmpty()) { // kui kõik ülesanded tehtud siis lõpetab töö
                break;
            }
        }

        ImageIcon icon3 = new ImageIcon("pepe3.jpg");
        Image originaalpilt3 = icon3.getImage();
        Image muudetudPilt3 = originaalpilt3.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon õigedSuurusedPilt3 = new ImageIcon(muudetudPilt3);

        ImageIcon icon4 = new ImageIcon("pepe4.jpg");
        Image originaalpilt4 = icon4.getImage();
        Image muudetudPilt4 = originaalpilt4.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon õigedSuurusedPilt4 = new ImageIcon(muudetudPilt4);

        StringBuilder message = new StringBuilder();
        for (String element : options2) {
            message.append(element).append("\n");
        }
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Kõik ülesanded on täidetud :D", "To-do list", JOptionPane.INFORMATION_MESSAGE, õigedSuurusedPilt4);
        } else { JOptionPane.showMessageDialog(null, message.toString(), "To-do list", JOptionPane.INFORMATION_MESSAGE, õigedSuurusedPilt3); }
    }


    private static List<String> lugedaFailist(String failinimi) {
        // rea haaval lihtsalt loeb faili ridu ja salvestab iga rea eraldi listi
        List<String> ülesanded = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(failinimi))) {
            String rida;
            while ((rida = br.readLine()) != null) {
                ülesanded.add(rida);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return ülesanded;
    }
}
