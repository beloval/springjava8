import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retriever.IValueRetriever;
import retriever.IValueRetrieverStatic;
import retriever.enums.DescriptionType;
import retriever.enums.SupplementalColumns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class Example {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);

        List<Integer> numbers = Arrays.asList(2, 3, 5, 6, 7, 8, 20);
        List<Integer> mappedLambda = map(numbers, 100, (test, value) -> {
            Integer k = test * test;
            return k * value;
        });
        mappedLambda.forEach(System.out::println);

        List<Integer> mapped = map(numbers, 100, new Mapper<Integer, Integer>() {

            @Override
            public Integer map(Integer test, Integer value) {
                return value * 10;
            }
        });
        for (Integer intValue : mapped) {
            System.out.println(intValue); //refactor it with java 8 lambdas
        }
        casePatternWithInterface();
    }

    public static <V, M> List<M> map(List<V> list, M num, Mapper<V, M> mapper) {
        List<M> mapped = new ArrayList<M>(list.size());
        for (V v : list) {
            mapped.add(mapper.map(num, v));
        }
        return mapped;
    }
    public static void casePatternWithInterface(){
        Map<SupplementalColumns, IValueRetriever> valueRetrievers = IValueRetrieverStatic.generateValuesRetrievers();
        int number=0;
        for (SupplementalColumns column : SupplementalColumns.values()) {
            String data;
            IValueRetriever retriever = valueRetrievers.get(column);
            if (retriever != null) {
                data = retriever.getValue("product", "contract", "merchant", number);
            number++;
                System.out.println(data);
            }
        }
        }

    @RequestMapping("/")
    String home() {

        return DescriptionType.LONG_DESCRIPTION.getTextLineType();
    }


}
