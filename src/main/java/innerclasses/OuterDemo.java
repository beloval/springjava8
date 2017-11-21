package innerclasses;

public class OuterDemo {
    private int privateNumber = 20;


    public void displayFromInner() {
        InnerDemo innerDemo = new InnerDemo();
        innerDemo.printTest();

    }

    private class InnerDemo {
        public void printTest() {
            System.out.println("Message from inner class private");
        }

    }

    public class InnerDemoPublic {
        public void printTest() {
            System.out.println("Message from inner class public");
        }

        public int getPrivateNumber() {
            System.out.println("This is the getnum method of the inner class");
            return privateNumber;
        }
    }
}
