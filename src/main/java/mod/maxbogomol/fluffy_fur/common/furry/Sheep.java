package mod.maxbogomol.fluffy_fur.common.furry;

public class Sheep extends Furry {

    public Sheep(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "sheep";
    }

    @Override
    public String sound() {
        return bweh();
    }

    public String bweh() {
        return "Bweh!";
    }
}
