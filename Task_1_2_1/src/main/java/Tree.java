import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * My tree data structure implementation.
 *
 * @param <T> Type of data stored in each node of the tree.
 */
public class Tree<T> {
    private T nodeValue;
    private  Tree<T> nodeParent;
    private final ArrayList<Tree<T>> nodeChild;
    private int modCount = 0;

    /**
     * Constructs a new tree node with given value.
     *
     * @param nodeValue Value, stored in new tree node.
     */
    public Tree(T nodeValue) {
        this.nodeValue = nodeValue;
        this.nodeParent = null;
        this.nodeChild = new ArrayList<>();
    }

    public void updateParents(Tree<T> current) {
        if (current.nodeValue != null && current.nodeParent != null) {
            current.nodeParent.modCount++;
            updateParents(current.nodeParent);
        }
    }

    /**
     * Adds new child node with given value to the current node.
     *
     * @param nodeValue Value of child node which will be added.
     * @return New child node.
     */
    public Tree<T> addChild(T nodeValue) {
        this.modCount++;
        updateParents(this);
        Tree<T> newNode = new Tree<>(nodeValue);
        newNode.nodeParent = this;
        this.nodeChild.add(newNode);
        return newNode;
    }

    /**
     * Adds an existing tree as a child to the current node.
     *
     * @param subTree Existing tree which will be added as a child node.
     */
    public void addChild(Tree<T> subTree) {
        this.modCount++;
        updateParents(this);
        subTree.nodeParent = this;
        this.nodeChild.add(subTree);
    }

    /**
     * Removes the first child node with the specified value from the tree.
     *
     * @param nodeValue The value of the child node which will be removed.
     */
    public void removeChild(T nodeValue) {
        for (int i = 0; i < nodeChild.size(); i++) {
            Tree<T> child = nodeChild.get(i);
            if (child.nodeValue != null && child.nodeValue.equals(nodeValue)) {
                nodeChild.remove(i);
                return;
            }
        }
    }

    /**
     * Removes the current node from the tree.
     */
    public void remove() {
        this.modCount++;
        updateParents(this);
        this.nodeChild.clear();
        if (this.nodeParent != null) {
            this.nodeParent.nodeChild.remove(this);
        }
        this.nodeValue = null;
    }

    /**
     * Compares this tree with another object for equality.
     *
     * @param comparable The object to compare with this tree.
     * @return {@code true} if given trees are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object comparable) {
        if (!(comparable instanceof Tree)) {
            return false;
        }
        if (this.nodeValue != ((Tree<?>) comparable).nodeValue) {
            return false;
        }
        if (this.nodeChild.isEmpty() && ((Tree<T>) comparable).nodeChild.isEmpty()) {
            return true;
        }
        if (this.nodeChild.size() != ((Tree<T>) comparable).nodeChild.size()) {
            return false;
        }
        Iterator<T> iterFirstTree = this.iteratorDFS();
        Iterator<T> iterSecondTree = ((Tree<T>) comparable).iteratorDFS();
        while (iterFirstTree.hasNext() && iterSecondTree.hasNext()) {
            if (iterFirstTree.next() != iterSecondTree.next()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Iterator for DF traversal.
     *
     * @return A DF iterator for the tree.
     */
    public Iterator<T> iteratorDFS() {
        return new IteratorDFS<T>(this);
    }

    /**
     * Iterator for DF traversal of the tree.
     */
    class IteratorDFS<T> implements Iterator<T> {
        private final Stack<Tree<T>> stack;
        private int currentModCounter;

        IteratorDFS(Tree<T> root) {
            this.stack = new Stack<Tree<T>>();
            if (root.nodeValue != null) {
                this.stack.push(root);
                this.currentModCounter = root.modCount;
            }
        }

        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if (currentModCounter != modCount) {
                throw new ConcurrentModificationException();
            } else {
                return (!this.stack.isEmpty());
            }
        }

        @Override
        public T next() throws NoSuchElementException {
            int i;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> cur = this.stack.pop();
            for (i = 0; i < cur.nodeChild.size(); i++) {
                if (cur.nodeChild.get(i).nodeValue != null) {
                    this.stack.push(cur.nodeChild.get(i));
                }
            }

            return cur.nodeValue;
        }
    }

    /**
     * Iterator for BF traversal.
     *
     * @return A BF iterator for the tree.
     */
    public Iterator<T> iteratorBFS() {
        return new IteratorBFS<T>(this);
    }

    class IteratorBFS<T> implements Iterator<T> {
        private final Queue<Tree<T>> queue;
        private int currentModCounter;

        IteratorBFS(Tree<T> root) {
            this.queue = new ArrayDeque<>();
            if (root.nodeValue != null) {
                this.queue.add(root);
                this.currentModCounter = root.modCount;
            }
        }

        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if (currentModCounter != modCount) {
                throw new ConcurrentModificationException();
            } else {
                return !this.queue.isEmpty();
            }

        }

        @Override
        public T next() throws NoSuchElementException {
            Tree<T> crnt;
            int i;

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            crnt = this.queue.poll();
            for (i = 0; i < crnt.nodeChild.size(); i++) {
                if (crnt.nodeChild.get(i).nodeValue != null) {
                    this.queue.add(crnt.nodeChild.get(i));
                }
            }

            return crnt.nodeValue;
        }
    }
}
