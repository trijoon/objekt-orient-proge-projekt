public class KiireÜlesanne extends Ülesanne {
    public KiireÜlesanne(String kirjeldus, int ajakulu) {
        super(kirjeldus, ajakulu);
    }

    @Override
    public String toString() {
        return super.toString() + " kiire";
    }
}