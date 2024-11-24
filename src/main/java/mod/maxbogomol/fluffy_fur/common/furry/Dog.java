package mod.maxbogomol.fluffy_fur.common.furry;

public class Dog extends Furry {
    public String name;

    public Dog(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "dog";
    }
}
