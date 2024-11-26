package mod.maxbogomol.fluffy_fur.common.furry;

public class Goat extends Furry {
    public String name;

    public Goat(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "goat";
    }

    @Override
    public String sound() {
        return bleh();
    }

    public String bleh() {
        return "Bleh!";
    }
}
