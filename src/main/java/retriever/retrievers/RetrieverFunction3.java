package retriever.retrievers;


import retriever.IValueRetriever;

public class RetrieverFunction3 implements IValueRetriever{

    @Override
    public String getValue(String product, String contract, String merchant, Integer constantValue) {
        return product+merchant;
    }
}
