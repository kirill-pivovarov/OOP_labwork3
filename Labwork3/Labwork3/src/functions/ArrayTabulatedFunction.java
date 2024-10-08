package functions;

public class ArrayTabulatedFunction implements TabulatedFunction {
    private FunctionPoint[] points;

    private int pointsCount;

    private int arrayCapacity;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        try {
            if (leftX >= rightX)
                throw new IllegalArgumentException("The left boundary of the definition area should be smaller than the right boundary.");
            if (pointsCount < 2)
                throw new IllegalArgumentException("There must be at least two points.");

            this.pointsCount = pointsCount;
            this.arrayCapacity = pointsCount;

            double step = (rightX - leftX) / (pointsCount);

            points = new FunctionPoint[pointsCount + 1];
            for (int i = 0; i < pointsCount; i++) {
                points[i] = new FunctionPoint(leftX + step * i, 0);
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }

    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        try {
            if (leftX >= rightX)
                throw new IllegalArgumentException("The left boundary of the definition area should be smaller than the right boundary.");
            if (values.length < 2)
                throw new IllegalArgumentException("There must be at least two points.");

            this.pointsCount = values.length;
            this.arrayCapacity = values.length;

            double step = (rightX - leftX) / (values.length);

            points = new FunctionPoint[values.length];
            for (int i = 0; i < values.length; i++) {
                points[i] = new FunctionPoint(leftX + step * i, values[i]);
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }

    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < pointsCount; i++) {
            result += points[i].toString() + "\n";
        }
        return result;
    }

    private int search(double x) // Бинарный поиск интервала, в который входит данное значене x
    {
        if (Double.compare(getLeftDomainBorder(), x) <= 0 && Double.compare(x, getRightDomainBorder()) <= 0) {
            int begin = 0;
            int end = pointsCount - 1;
            int i = end / 2;
            while (!(points[i].getX() <= x && x <= points[i + 1].getX()) && i != 0) {
                if (x < points[i].getX()) {
                    end = i + (end - i) / 2;
                } else {
                    begin = begin + (end - begin) / 2;
                }

                i = begin + (i - begin) / 2;
            }
            return i;
        } else {
            return -1;
        }
    }

    public double getFunctionValue(double x) {
        if (points[0].getX() <= x && x <= points[pointsCount - 1].getX()) {
            int i = search(x);
            /* Если данное значение x принадлежит одной из точек табулирования функции, то можно сразу вернуть значение y. */
            if (Double.compare(x, points[i].getX()) == 0)
                return points[i].getY();
            else if (Double.compare(x, points[i + 1].getX()) == 0)
                return points[i + 1].getY();
            else {
                double dx = points[i + 1].getX() - points[i].getX(); // изменения значения x на интервале, которому принадлежит данное x
                double dy = points[i + 1].getY() - points[i].getY(); // изменнеия значения y

                double k = dy / dx; // коэффициент k линейной функции, заданной на данном интервале
                double b = points[i].getY() - k * points[i].getX(); // свободный член b линейной функции, заданной на данном интервале

                return k * x + b;
            }
        } else {
            return Double.NaN;
        }
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(String.format("The index value must be between 0 and %d", pointsCount - 1));
            return points[index];
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new FunctionPoint();
        }
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(String.format("The index value must be between 0 and %d", pointsCount - 1));

            if (points[index].getX() < point.getX() && point.getX() < points[index + 1].getX()) {
                points[index].setX(point.getX());
                points[index].setY(point.getY());
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(String.format("The index value must be between 0 and %d", pointsCount - 1));
            return points[index].getX();
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(String.format("The index value must be between 0 and %d", pointsCount - 1));
            return points[index].getY();
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(String.format("The index value must be between 0 and %d", pointsCount - 1));

            if (x < points[index].getX() || x > points[index].getX())
                throw new InappropriateFunctionPointException(
                        String.format("The value of x must be included in the interval: [%.2d, %.2d].",
                                points[index].getX(), points[index + 1].getX()));

            if (index < pointsCount - 1) {
                if (points[index].getX() < x && x < points[index + 1].getX()) {
                    points[index].setX(x);
                }
            } else if (Double.compare(x, points[index].getX()) == 0) {
                points[index].setX(x);
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(
                        String.format("The index value must be between 0 and %d", pointsCount - 1));
            points[index].setY(y);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(
                        String.format("The index value must be between 0 and %d", pointsCount - 1));

            if (pointsCount < 3)
                throw new IllegalStateException("The tabulated function must have at least 2 points.");

            for (int i = index; i < pointsCount - 1; i++) {
                points[i] = points[i + 1];
            }
            points[pointsCount - 1] = null;
            --pointsCount;
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        try {
            if (pointsCount < arrayCapacity) {
                int index = search(point.getX()) + 1;

                if (Double.compare(point.getX(), points[index - 1].getX()) == 0)
                    throw new InappropriateFunctionPointException("A point with such an abscissa already exists.");

                FunctionPoint cur = points[index];
                FunctionPoint next;
                points[index] = new FunctionPoint(point);

                for (int i = index; i < pointsCount; i++) {
                    next = points[i + 1];
                    points[i + 1] = cur;
                    cur = next;
                }

                pointsCount++;
            } else {
                int index = search(point.getX()) + 1;

                if (Double.compare(point.getX(), points[index - 1].getX()) == 0)
                    throw new InappropriateFunctionPointException("A point with such an abscissa already exists.");

                FunctionPoint[] temp = new FunctionPoint[arrayCapacity << 1];
                System.arraycopy(points, 0, temp, 0, index);

                temp[index] = new FunctionPoint(point);
                System.arraycopy(points, index, temp, index + 1, pointsCount - index);

                pointsCount++;
                arrayCapacity <<= 1;

                points = temp;
            }
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }
}
