import innerclasses.OuterDemo;
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongToDoubleFunction;

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
        //case to interface pattern with inner class and lambda
        casePatternWithInterface();

        OuterDemo outerDemo = new OuterDemo();
        outerDemo.displayFromInner();
        //access to inner public class
        OuterDemo.InnerDemoPublic innerDemoPublic = outerDemo.new InnerDemoPublic();
        innerDemoPublic.printTest();
       System.out.println(innerDemoPublic.getPrivateNumber());
        //with anonymous inner classes
        OuterDemo outer = new OuterDemo(){
            public void displayFromInner() {
                System.out.print("now overwrite method to display from private inner with anonymous class");
            }
        };
        Mapper<Integer, Integer> square = (Integer value, Integer test) -> value * value;
        System.out.print(square.map(10,10));
        outer.displayFromInner();
  longToDoubleWithUtilFunction();
    }

    private static void longToDoubleWithUtilFunction() {
        LongToDoubleFunction i = value -> value;
       System.out.println(i.applyAsDouble(1000));
        Consumer<String> print = System.out::print;
        print.accept("My string 1");
        Function<Integer, String> toStr = (value) -> (value + "!");
       // List<String> string = map(integers, toStr);

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
