package functions;

public class FunctionNode {
    private FunctionPoint value;
    private FunctionNode prev;
    private FunctionNode next;

    public FunctionNode() {
        value = null;
        prev = this;
        next = this;
    }

    public FunctionNode(FunctionPoint value, FunctionNode prev, FunctionNode next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    public FunctionNode(FunctionNode prev, FunctionNode next) {
        this.value = null;
        this.prev = prev;
        this.next = next;
    }

    public FunctionPoint getValue() {
        return value;
    }

    public void setValue(FunctionPoint fp) {
        value = fp;
    }

    public FunctionNode getPrev() {
        return prev;
    }

    public void setPrev(FunctionNode fn) {
        prev = fn;
    }

    public FunctionNode getNext() {
        return next;
    }

    public void setNext(FunctionNode fn) {
        next = fn;
    }
}