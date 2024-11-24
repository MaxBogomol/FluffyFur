package mod.maxbogomol.fluffy_fur.common.furry;

public class Fox extends Furry {
    public String name;

    public Fox(String name) {
        super(name);
    }

    @Override
    public String getFurryName() {
        return "fox";
    }

    public String meow() {
        return "Meow!";
    }

    public String woof() {
        return "Woof!";
    }

    public String awoo() {
        return "Awoo!";
    }
}
