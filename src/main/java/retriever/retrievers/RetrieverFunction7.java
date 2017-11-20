package retriever.retrievers;


import retriever.IValueRetriever;

public class RetrieverFunction7 implements IValueRetriever{

    @Override
    public String getValue(String product, String contract, String merchant, Integer constantValue) {
        return "this is the test: "+product+merchant;
    }
}
