import java.util.Date;

public class TestCase {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        System.out.println(l);
        System.out.println(new Date(l+1000*60*30));
    }
}
