package mod.maxbogomol.fluffy_fur.common.furry;

public class Fish extends Furry {

    public Fish(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "fish";
    }

    @Override
    public String sound() {
        return underWaterNoises();
    }

    public String underWaterNoises() {
        return "";
    }
}
