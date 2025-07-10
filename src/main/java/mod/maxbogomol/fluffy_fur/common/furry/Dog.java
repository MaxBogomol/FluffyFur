package mod.maxbogomol.fluffy_fur.common.furry;

public class Dog extends Furry {

    public Dog(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "dog";
    }

    @Override
    public String sound() {
        return woof();
    }

    public String woof() {
        return "Woof!";
    }
}
