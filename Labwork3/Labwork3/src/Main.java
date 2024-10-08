import functions.*;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException {
        TabulatedFunction exp = new ArrayTabulatedFunction(0, 5, new double[]{1, 2.71828, 7.38906, 20.08554, 54.59815, 148.41316});

        for (double x = -1; x < 6; x += 0.5) {
            System.out.println(String.format("x: %.2f, y: %.2f", x, exp.getFunctionValue(x)));
        }

        exp.deletePoint(5);
        exp.setPointY(2, 7.4);

        System.out.println(exp.getPointX(9));

        for (double x = 0; x < 6; x += 0.5) {
            System.out.println(String.format("x: %.2f, y: %.2f", x, exp.getFunctionValue(x)));
        }

        exp = new LinkedListTabulatedFunction(0, 5, new double[]{1, 2.71828, 7.38906, 20.08554, 54.59815, 148.41316});

        for (double x = -1; x < 6; x += 0.5) {
            System.out.println(String.format("x: %.2f, y: %.2f", x, exp.getFunctionValue(x)));
        }

        exp.deletePoint(5);
        exp.setPointY(2, 7.4);

        System.out.println(exp.getPointX(9));

        for (double x = 0; x < 6; x += 0.5) {
            System.out.println(String.format("x: %.2f, y: %.2f", x, exp.getFunctionValue(x)));
        }
    }
}