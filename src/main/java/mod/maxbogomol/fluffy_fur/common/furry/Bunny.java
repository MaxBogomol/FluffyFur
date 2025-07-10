package mod.maxbogomol.fluffy_fur.common.furry;

public class Bunny extends Furry {

    public Bunny(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "bunny";
    }

    @Override
    public String sound() {
        return squeak();
    }

    public String squeak() {
        return "Squeak!";
    }
}
