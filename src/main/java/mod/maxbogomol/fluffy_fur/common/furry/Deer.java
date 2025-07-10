package mod.maxbogomol.fluffy_fur.common.furry;

public class Deer extends Furry {

    public Deer(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "deer";
    }

    @Override
    public String sound() {
        return bwah();
    }

    public String bwah() {
        return "Bwah!";
    }
}
