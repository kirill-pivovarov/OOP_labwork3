package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction{
    private FunctionNode head;

    private int pointsCount;

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        try {
            if (leftX >= rightX)
                throw new IllegalArgumentException("The left boundary of the definition area should be smaller than the right boundary.");
            if (pointsCount < 2)
                throw new IllegalArgumentException("There must be at least two points.");

            this.pointsCount = pointsCount;

            head = new FunctionNode();
            FunctionNode iter = head;

            double step = (rightX - leftX) / (pointsCount);

            for (int i = 0; i < pointsCount + 1; i++) {
                iter.setNext(new FunctionNode(new FunctionPoint(leftX + step * i, 0), iter, null));
                iter = iter.getNext();
            }

            iter.setNext(head);
            head.setPrev(iter);

            } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        try {
            if (leftX >= rightX)
                throw new IllegalArgumentException("The left boundary of the definition area should be smaller than the right boundary.");
            if (values.length < 2)
                throw new IllegalArgumentException("There must be at least two points.");

            pointsCount = values.length;

            head = new FunctionNode();
            FunctionNode iter = head;

            double step = (rightX - leftX) / (values.length - 1);

            for (int i = 0; i < values.length; i++) {
                iter.setNext(new FunctionNode(new FunctionPoint(leftX + step * i, values[i]), iter, null));
                iter = iter.getNext();
            }

            iter.setNext(head);
            head.setPrev(iter);

        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }

    public String toString() {
        String result = "";

        FunctionNode iter = head;
        while (iter.getNext() != head) {
            result += iter.getNext().getValue().toString() + '\n';
            iter = iter.getNext();
        }

        return result;
    }

    public FunctionNode getNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException {
        try {
            if (index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(
                        String.format("The index value must be between 0 and %d.", pointsCount - 1));

            FunctionNode iter = head;
            if (index > (pointsCount / 2 - 1)) {
                for (int i = 0; i < pointsCount - index; i++) {
                    iter = iter.getPrev();
                }
            } else {
                for (int i = 0; i < index + 1; i++) {
                    iter = iter.getNext();
                }
            }
            return iter;
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new FunctionNode();
        }
    }

    public FunctionNode addNodeToTail() {
        FunctionNode iter = head.getPrev();

        iter.setNext(new FunctionNode(iter, head));
        iter.getPrev().setNext(iter.getNext());
        head.setPrev(iter.getNext());

        pointsCount++;
        return iter.getNext();
    }

    public FunctionNode addNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(
                        String.format("The index value must be between 0 and %d.", pointsCount - 1));

            FunctionNode iter = head;
            if (index > (pointsCount / 2 - 1)) {
                for (int i = 0; i < pointsCount - index; i++) {
                    iter = iter.getPrev();
                }
            } else {
                for (int i = 0; i < index + 1; i++) {
                    iter = iter.getNext();
                }
            }

            FunctionNode newNode = new FunctionNode(iter, iter.getNext());
            iter.setNext(newNode);
            newNode.getNext().setPrev(newNode);

            pointsCount++;

            return iter.getNext();
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new FunctionNode();
        }
    }

    public FunctionNode deleteNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException {
        try {
            if (index < 0 || index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(
                        String.format("The index value must be between 0 and %d.", pointsCount - 1));

            FunctionNode iter = head;
            if (index > (pointsCount / 2 - 1)) {
                for (int i = 0; i < pointsCount - index; i++) {
                    iter = iter.getPrev();
                }
            } else {
                for (int i = 0; i < index + 1; i++) {
                    iter = iter.getNext();
                }
            }

            FunctionNode deleted = iter;

            iter.getPrev().setNext(iter.getNext());
            iter.getNext().setPrev(iter.getPrev());

            pointsCount--;

            return deleted;
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new FunctionNode();
        }
    }

    public double getLeftDomainBorder() {
        return head.getNext().getValue().getX();
    }

    public double getRightDomainBorder() {
        return head.getPrev().getValue().getX();
    }

    private FunctionNode search(double x) {
        if (Double.compare(getLeftDomainBorder(), x) <= 0 && Double.compare(x, getRightDomainBorder()) <= 0) {
            FunctionNode iter = head.getNext();
            while (!(iter.getValue().getX() <= x && x <= iter.getNext().getValue().getX())) {
                iter = iter.getNext();
            }
            return iter;
        } else {
            return head;
        }
    }

    public double getFunctionValue(double x) {
        if (head.getNext().getValue().getX() <= x && x <= head.getPrev().getValue().getX()) {
            FunctionNode i = search(x);
            /* Если данное значение x принадлежит одной из точек табулирования функции, то можно сразу вернуть значение y. */
            if (Double.compare(x, i.getValue().getX()) == 0)
                return i.getValue().getY();
            else if (Double.compare(x, i.getNext().getValue().getX()) == 0)
                return i.getNext().getValue().getY();
            else {
                double dx = i.getNext().getValue().getX() - i.getValue().getX(); // изменения значения x на интервале, которому принадлежит данное x
                double dy = i.getNext().getValue().getY() - i.getValue().getY(); // изменнеия значения y

                double k = dy / dx; // коэффициент k линейной функции, заданной на данном интервале
                double b = i.getValue().getY() - k * i.getValue().getX(); // свободный член b линейной функции, заданной на данном интервале

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
            if (index >= pointsCount)
                throw new FunctionPointIndexOutOfBoundsException(
                        String.format("The index value must be between 0 and %d.", pointsCount - 1));

            return getNodeByIndex(index).getValue();
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

            FunctionNode nodeByIndex = getNodeByIndex(index);

            if (nodeByIndex.getValue().getX() < point.getX() && point.getX() < nodeByIndex.getNext().getValue().getX()) {
                nodeByIndex.getValue().setX(point.getX());
                nodeByIndex.getValue().setY(point.getY());
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
            return getNodeByIndex(index).getValue().getX();
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
            return getNodeByIndex(index).getValue().getY();
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

            FunctionNode nodeByIndex = getNodeByIndex(index);
            if (x < nodeByIndex.getValue().getX() || x > nodeByIndex.getValue().getX())
                throw new InappropriateFunctionPointException(
                        String.format("The value of x must be included in the interval: [%.2d, %.2d].",
                                nodeByIndex.getValue().getX(), nodeByIndex.getNext().getValue().getX()));

            if (index < pointsCount - 1) {
                if (nodeByIndex.getValue().getX() < x && x < nodeByIndex.getNext().getValue().getX()) {
                    nodeByIndex.getValue().setX(x);
                }
            } else if (Double.compare(x, nodeByIndex.getValue().getX()) == 0) {
                nodeByIndex.getValue().setX(x);
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
            getNodeByIndex(index).getValue().setY(y);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        try {
            FunctionNode i = search(point.getX());
            if (Double.compare(point.getX(), i.getNext().getValue().getX()) == 0)
                throw new InappropriateFunctionPointException("A point with such an abscissa already exists.");

            FunctionNode iter = head.getNext();
            int index = 0;
            while (iter != i) {
                iter = iter.getNext();
                index++;
            }

            FunctionNode newNode = addNodeByIndex(index);
            newNode.setValue(point);
        } catch (Exception e) {
            System.out.println(e.getClass());
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

            deleteNodeByIndex(index);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }
}
