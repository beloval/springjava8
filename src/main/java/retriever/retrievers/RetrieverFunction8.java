package retriever.retrievers;


import retriever.IValueRetriever;

public class RetrieverFunction8 implements IValueRetriever{

    @Override
    public String getValue(String product, String contract, String merchant, Integer constantValue) {
        return product+merchant;
    }
}
