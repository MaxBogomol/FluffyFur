package mod.maxbogomol.fluffy_fur.common.furry;

public abstract class Furry {
    public String name;

    public Furry(String name) {
        this.name = name;
    }

    public void rename(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFurryName() {
        return "furry";
    }

    public String pat() {
        return "You pat the " + name + " " + getFurryName() + "!";
    }

    public String sound() {
        return "Noise!";
    }
}
