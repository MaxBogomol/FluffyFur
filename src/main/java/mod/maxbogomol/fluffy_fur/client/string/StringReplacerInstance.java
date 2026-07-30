package mod.maxbogomol.fluffy_fur.client.string;

public class StringReplacerInstance {
    public String id;

    public StringReplacerInstance(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getReplaceString() {
        return "";
    }

    public boolean canReplaceString(String string) {
        return string.contains(getId());
    }

    public String replaceString(String string) {
        string = string.replace(getId(), getReplaceString());
        return string;
    }
}
