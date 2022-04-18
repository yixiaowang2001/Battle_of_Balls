public class SortedLinkedList {
    private Node head = new Node();
    private Node tail = new Node();
    private int count = 0;

    public void add(Integer num) {
        Node newNode = new Node(num);
        if (count == 0) {
            head.setNext(newNode);
            tail.setPrevious(newNode);
            newNode.setNext(tail);
            newNode.setPrevious(head);
        } else {
            Node current = head.getNext();
            while (current != tail) {
                if (current.getElement() > num) {
                    break;
                }
                current = current.getNext();
            }
            newNode.setNext(current);
            newNode.setPrevious(current.getPrevious());
            current.getPrevious().setNext(newNode);
            current.setPrevious(newNode);
        }
        count++;
    }

    public static void main(String[] args) {
        SortedLinkedList sl = new SortedLinkedList();
        sl.add(1);
        sl.add(5);
        sl.add(4);
        sl.add(3);

        Node current = sl.head.getNext();
        while (current != sl.tail) {
            System.out.println(current.getElement());
            current = current.getNext();
        }
    }
}
