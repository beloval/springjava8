package retriever;

import retriever.enums.SupplementalColumns;
import retriever.retrievers.*;

import java.util.HashMap;
import java.util.Map;

public class IValueRetrieverStatic {


    public static Map<SupplementalColumns, IValueRetriever> generateValuesRetrievers() {

        HashMap<SupplementalColumns, IValueRetriever> valueRetrievers = new HashMap<>();

        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD1, new RetrieverFunction1());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD2, new RetrieverFunction2());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD3, new RetrieverFunction3());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD4, new RetrieverFunction4());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD5, new RetrieverFunction5());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD6, new RetrieverFunction6());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD7, new RetrieverFunction7());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD8, new RetrieverFunction8());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD9, new RetrieverFunction9());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD10, new RetrieverFunction10());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD11, new RetrieverFunction11());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD12, new RetrieverFunction12());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD13, new RetrieverFunction13());
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD14, new IValueRetriever() {
            @Override
            public String getValue(String product, String contract, String merchant, Integer constantValue) {
                return constantValue+"";
            }
        });
        valueRetrievers.put(SupplementalColumns.ACCOUNT_FIELD15, (product, contract, merchant, constantValue)->"lambda test"+merchant+":::"+constantValue);

        return valueRetrievers;
    }
}
