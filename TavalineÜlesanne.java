public class TavalineÜlesanne extends Ülesanne {
    public TavalineÜlesanne(String kirjeldus, int ajakulu) {
        super(kirjeldus, ajakulu);
    }

    @Override
    public String toString() {
        return super.toString() + " pole kiire";
    }
}