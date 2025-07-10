package mod.maxbogomol.fluffy_fur.common.furry;

public class Horse extends Furry {

    public Horse(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "horse";
    }

    @Override
    public String sound() {
        return hungry();
    }

    public String hungry() {
        return "Hungry!";
    }

    public String howHungry() {
        return "How Hungry?";
    }
}
