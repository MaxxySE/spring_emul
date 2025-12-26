import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        BigDecimal max = new BigDecimal(300);

        double min = 0.0d;

        double random = min + (max.doubleValue() - min) * ThreadLocalRandom.current().nextDouble();

        BigDecimal randomD = new BigDecimal(random);

        System.out.println(randomD.setScale(2, RoundingMode.HALF_DOWN));

    }

}
