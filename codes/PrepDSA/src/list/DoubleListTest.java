package list;

public class DoubleListTest {
    public static void main(String[] args) {
        DNode node = DoubleList.getADList();
        DoubleList.printDList(node);
        DoubleList.printDListRev(DoubleList.getEnd(node));
    }
}
