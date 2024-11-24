package mod.maxbogomol.fluffy_fur.common.furry;

public class Cat extends Furry {
    public String name;

    public Cat(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "cat";
    }
}
