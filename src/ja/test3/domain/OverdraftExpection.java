package ja.test3.domain;

public class OverdraftExpection extends RuntimeException {

    public OverdraftExpection(String outOfLimit) {
        System.err.println(outOfLimit);
    }
}
