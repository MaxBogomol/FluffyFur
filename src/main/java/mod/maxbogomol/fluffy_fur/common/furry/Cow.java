package mod.maxbogomol.fluffy_fur.common.furry;

public class Cow extends Furry {

    public Cow(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "cow";
    }

    @Override
    public String sound() {
        return moo();
    }

    public String moo() {
        return "Moo!";
    }
}
