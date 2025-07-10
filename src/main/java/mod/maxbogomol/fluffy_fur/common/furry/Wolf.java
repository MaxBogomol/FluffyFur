package mod.maxbogomol.fluffy_fur.common.furry;

public class Wolf extends Furry {

    public Wolf(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "wolf";
    }

    @Override
    public String sound() {
        return awoo();
    }

    public String awoo() {
        return "Awoo!";
    }
}
