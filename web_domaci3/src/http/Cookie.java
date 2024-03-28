package http;

import java.util.Objects;

public class Cookie {

    private final String name;
    private final String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Cookie that))
            return false;

        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return 1;//TODO This is criminal pls don't forget, this should be some algorithm Objects.hash(name, value)
    }
}
