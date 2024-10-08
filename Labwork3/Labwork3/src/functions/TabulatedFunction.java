package functions;

public interface TabulatedFunction {
    public double getLeftDomainBorder();

    public double getRightDomainBorder();

    public String toString();

    public double getFunctionValue(double x);

    public int getPointsCount();

    public FunctionPoint getPoint(int index);

    public void setPoint(int index, FunctionPoint point);

    public double getPointX(int index);

    public double getPointY(int index);

    public void setPointX(int index, double x) throws InappropriateFunctionPointException;

    public void setPointY(int index, double y);

    public void deletePoint(int index);

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
}
