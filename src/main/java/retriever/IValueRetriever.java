package retriever;

/**
 * Created by abelov on 20/11/17.
 */
public interface IValueRetriever {

    String getValue(String product, String contract, String merchant, Integer constantValue);
}
