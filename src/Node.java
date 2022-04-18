public class Node {
    private Integer element;
    private Node next;
    private Node previous;

    public Node() {
        this(null);
    }

    public Node(Integer element) {
        this.element = element;
        next = null;
        previous = null;
    }

    public Integer getElement() {
        return element;
    }

    public void setElement(Integer element) {
        this.element = element;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }
}
