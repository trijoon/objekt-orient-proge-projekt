import java.util.Comparator;

public abstract class Ülesanne {
    private final String kirjeldus;
    private final int ajakulu;

    public Ülesanne(String kirjeldus, int ajakulu) {
        this.kirjeldus = kirjeldus;
        this.ajakulu = ajakulu;
    }

    public String getKirjeldus() {
        return kirjeldus;
    }

    public int getAjakulu() {
        return ajakulu;
    }

    @Override
    public String toString() {
        return "Ülesanne{" +
                "kirjeldus='" + kirjeldus + '\'' +
                ", ajakulu=" + ajakulu +
                '}';
    }

    public static Comparator<Ülesanne> getAjakuluComparator() {
        return new AjakuluComparator();
    }

    private static class AjakuluComparator implements Comparator<Ülesanne> {
        @Override
        public int compare(Ülesanne u1, Ülesanne u2) {
            return Integer.compare(u1.getAjakulu(), u2.getAjakulu());
        }
    }
}