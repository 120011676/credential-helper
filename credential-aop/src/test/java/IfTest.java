public class IfTest {
    public static void main(String[] args) {
        if (true) {
            System.out.println("1");
            throw new RuntimeException("aa");
        } else if (true) {
            System.out.println("2");
        }
        System.out.println("success");
    }
}
