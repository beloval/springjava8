import hepler.SystemUser;
import innerclasses.OuterDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retriever.IValueRetriever;
import retriever.IValueRetrieverStatic;
import retriever.enums.DescriptionType;
import retriever.enums.SupplementalColumns;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

        System.out.print(square.map(10,10));//assign to lambda to variable

        outer.displayFromInner();
      longToDoubleWithUtilFunction();
        firstActionExample();

        System.out.println(greet("Hello").apply("world")); //function return function

        workingWithDirectory();

        workingWithJustObservable();

        workingWithOtherObservableMethods();

        unsubscribingWorks();

    }

    private static void workingWithJustObservable() {
    Observable
            .just(new SystemUser("Aleks", "Belov"))
            .map(u -> u.getFullname() + " " + u.getName())
            .subscribe(System.out::println);

    }


    private static void workingWithDirectory() {
        Path resources = Paths.get("src", "main", "java");
        try (DirectoryStream<Path> dStream
                     = Files.newDirectoryStream(resources)) {
            Observable<Path> dirObservable = Observable.from(dStream);
            dirObservable.subscribe(System.out::println);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

    private static void firstActionExample() {
        List<String> list = Arrays.asList("One", "Two", "Three", "Four",
                "Five"); // (1)
        Observable<String> observable = Observable.from(list); // (2)
        observable.subscribe(new Action1<String>() { // (3)
            @Override
            public void call(String element) {
                System.out.println(element); // Prints the element (4)
            }
        });
    }
    public static Function<String, String> greet(String greeting) {
        return (String name) -> greeting + " " + name + "!";
    }

    private static <T>  Subscription  subscribePrint(Observable<T> observable, String name) {
        return observable.subscribe(
                (v) -> System.out.println(name + " : " + v),
                (e) -> {
                    System.err.println("Error from " + name + ":");
                    System.err.println(e.getMessage());
                },
                () -> System.out.println(name + " ended!")
        );
    }
    private static void workingWithOtherObservableMethods() {
        try {
        subscribePrint(
                Observable.interval(500L, TimeUnit.MILLISECONDS),
                "Interval Observable"
        );
        subscribePrint(
                Observable.timer(20L, 10L, TimeUnit.SECONDS),
                "Timed Interval Observable"
        );
        subscribePrint(
                Observable.timer(1L, TimeUnit.SECONDS),
                "Timer Observable"
        );
        subscribePrint(
                Observable.error(new Exception("Test Error!")),
                "Error Observable"
        );
        subscribePrint(Observable.empty(), "Empty Observable");
        subscribePrint(Observable.never(), "Never Observable");
        subscribePrint(Observable.range(1, 3), "Range Observable");

            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void unsubscribingWorks() throws IOException {
        Path path = Paths.get("src", "main", "resources",
                "test.txt"); // (1)
        List<String> data = Files.readAllLines(path);
        Observable<String> observable =
                fromIterable(data).subscribeOn(Schedulers.computation()); // (2)
        Subscription subscription = subscribePrint(observable, "File");// (3)
        System.out.println("Before unsubscribe!");
        System.out.println("-------------------");
        subscription.unsubscribe(); // (4)
        System.out.println("-------------------");
        System.out.println("After unsubscribe!");
    }

    private static  <T> Observable<T> fromIterable(final Iterable<T> iterable) {
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(Subscriber<? super T> subscriber) {
                    try {
                        Iterator<T> iterator = iterable.iterator();
                        while (iterator.hasNext()) {
                            if (subscriber.isUnsubscribed()) {
                                return;
                            }
                            subscriber.onNext(iterator.next());
                        }
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }
                    catch (Exception e) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(e);
                        }
                    }
                }
            });
        }

}


