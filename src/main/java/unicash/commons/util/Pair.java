package unicash.commons.util;

/**
 * A Generic Pair class for utility purposes.
 * @param <T> type parameter of the first type of {@code Object} to be contained
 * @param <U> type parameter of the second type of {@code Object} to be contained
 */
public class Pair<T,U> {
    private T t;
    private U u;

    /**
     * Creates the pair object.
     * @param t The first object
     * @param u The second object
     */
    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    /**
     * Returns the first object.
     */
    public T getFirst() {
        return t;
    }

    /**
     * Returns the second object
     */
    public U getSecond() {
        return u;
    }

    /**
     * Sets the first object.
     */
    public void setFirst(T t) {
        this.t = t;
    }

    /**
     * Sets the second object.
     */
    public void setSecond() {
        this.u = u;
    }

    @Override
    public String toString() {
        return "[" + t + ", " + u + "]";
    }

}
