package Helper;

public class Utility {

    private static boolean DEBUG;

    public static void error(String var0) {
        System.out.println("== Synth Error ==");
        System.out.println("== " + var0);
        System.out.println();
    }

    public static void debug(String var0) {
        if (DEBUG) {
            String[] var1 = var0.split("\n");
            System.out.println("== Player Debug ==");

            for(int var2 = 0; var2 < var1.length; ++var2) {
                System.out.println("== " + var1[var2]);
            }

            System.out.println();
        }

    }

    public static void debugOn() {
        DEBUG = true;
    }

    public void debugOff() {
        DEBUG = false;
    }
}
