package datastructures;

public class LinkedList<NodeType> {
    ListNode root = null; // Root Element

    private class ListNode {
        NodeType val;
        ListNode next = null;

        ListNode(NodeType obj) {
            val = obj;
        }
    }

    public int getSize() {
        if (root == null) {
            return 0;
        } else {
            ListNode current_node = root;
            int size = 1;

            while (current_node.next != null) {
                current_node = current_node.next;
                size++;
            }

            return size;
        }
    }

    private ListNode getNode(int pos) {
        if(pos >= getSize()) {
            throw new IndexOutOfBoundsException();
        } else {
            ListNode current_node = root;

            for(int i = 1; i <= pos; i++) {
                current_node = current_node.next;
            }

            return current_node;
        }
    }

    public NodeType get(int pos) {
        return getNode(pos).val;
    }

    public void add(NodeType obj) {
        if(root == null) {
            root = new ListNode(obj);
        } else {
            getNode(getSize() - 1).next = new ListNode(obj);
        }
    }

    public void insert(int pos, NodeType obj) {
        if (pos == 0) {
            if (root == null) {
                root = new ListNode(obj);
            } else {
                ListNode old_root = root;
                root = new ListNode(obj);
                root.next = old_root;
            }
        } else {
            ListNode prev_node = getNode(pos - 1);
            ListNode next_node = prev_node.next;

            prev_node.next = new ListNode(obj);
            prev_node.next.next = next_node;
        }
    }

    public void delete(int pos) {
        if (pos == 0) {
            if (root != null) {
                root = root.next;
            } else {
                throw new IndexOutOfBoundsException();
            }
        } else {
            ListNode current_node = getNode(pos);
            ListNode prev_node = getNode(pos - 1);

            prev_node.next = current_node.next;
        }
    }

    public boolean contains(NodeType item){
        ListNode current_node = root;

        while (current_node != null) {
            if (current_node.val.equals(item)) {
                return true;
            }

            current_node = current_node.next;
        }

        return false;
    }

    public void deleteNode(NodeType item) {
        ListNode current_node = root;

        int pos = 0;
        while (current_node != null) {
            if (current_node.val.equals(item)) {
                delete(pos);
            }

            current_node = current_node.next;
            pos++;
        }
    }

    @SuppressWarnings("unchecked")
    public NodeType[] toArray() {
        NodeType[] arr = (NodeType[]) new Object[getSize()];

        ListNode current_node = root;
        for(int i = 0; i < getSize(); i++) {
            arr[i] = current_node.val;
            current_node = current_node.next;
        }

        return arr;
    }

    public static void main(String[] args) {
        LinkedList<String> favoriteFruits = new LinkedList<>();

        favoriteFruits.add("Oranges");
        favoriteFruits.add("Bananas");
        favoriteFruits.add("Apples");
        favoriteFruits.insert(2, "Watermelon");
        System.out.println(favoriteFruits.get(2));
        favoriteFruits.delete(1);
        System.out.println(favoriteFruits.get(2));
    }
}
