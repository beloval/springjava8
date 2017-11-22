
public interface Mapper<V,M> {
    M map (M test, V value);
    default String fromChapter() {
        return "Two";
    }
}
