package mod.maxbogomol.fluffy_fur.common.furry;

public class Mouse extends Furry {
    public String name;

    public Mouse(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "mouse";
    }

    @Override
    public String sound() {
        return squeak();
    }

    public String squeak() {
        return "Squeak!";
    }
}
