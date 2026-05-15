package bstmap;

import afu.org.checkerframework.checker.oigj.qual.O;
import edu.princeton.cs.algs4.BST;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size = 0; // 好吧，这个size其实应该设计给BSTNode存储
    private BSTNode<K, V> root = null;

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (root == null) return false;
        else return root.containsKey(key);
    }

    @Override
    public V get(K key) {
        if (root == null) return null;
        else return root.get(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new BSTNode<>(key, value, null, null);
            size++;
        }
        else root.put(key, value);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        V val = get(key);
        // throw new UnsupportedOperationException();
        root = remove(key, root);
        return val;
    }

    @Override
    public V remove(K key, V val) {
        throw new UnsupportedOperationException();
    }

    /**
     * java的特性是只能按值传递，因此不能手动释放资源（也就是手动删除节点），
     * 必须依赖parent调整子节点指针使其悬空才能删除节点
     * 因此要么让每个节点保留其parent的引用，要么就需要把逻辑写进递归里
     * */
    private BSTNode<K, V> remove(K key, BSTNode<K, V> node) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);

        if (cmp < 0) node.left = remove(key, node.left); // 小于当前key，去左子树继续查找删除
        else if (cmp > 0) node.right = remove(key, node.right);

        // 被删除节点就是当前节点
        // 至多有一个子节点
        if (node.left == null) {
            size--;
            return node.right;
        }
        else if (node.right == null) {
            size--;
            return node.left;
        }// 直接用替代代替删除

        // 有两个子节点，找到左子树的最大节点代替并删除该节点
        BSTNode<K, V> pivot = node.left;
        while (pivot.right != null) {
            pivot = pivot.right;
        }

        node.key = pivot.key;
        node.val = pivot.val;

        node.left = removeMax(node.left);

        size--;
        return node;
    }

    // 删除子树中的最大值（包括子树的根）
    private BSTNode<K, V> removeMax(BSTNode<K, V> node) {
        if (node.left == null) return node.right;
        node.left = removeMax(node.left);
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return new keyIterator<>();
    }

    private class BSTNode<K extends Comparable<K>, V> {
        K key;
        V val;
        BSTNode<K, V> left;
        BSTNode<K, V> right;

        BSTNode(K key, V val, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }

        boolean containsKey(K key) {
            int cmp = key.compareTo(this.key);

            if (cmp == 0) return true;

            if (cmp < 0 && left == null) return false;
            if (cmp < 0) return left.containsKey(key);

            // cmp > 0
            if (right == null) return false;
            else return right.containsKey(key);
        }

        V get(K key) {
            int cmp = key.compareTo(this.key);

            if (cmp == 0) return val;

            if (cmp < 0 && left == null) return null;
            if (cmp < 0) return left.get(key);

            // cmp > 0
            if (right == null) return null;
            else return right.get(key);
        }

        void put(K key, V val) {
            int cmp = key.compareTo(this.key);

            if (cmp == 0) this.val = val;

            else if (cmp < 0 && left == null) {
                left = new BSTNode<>(key, val, null, null);
                size++;
            }
            else if (cmp < 0) left.put(key, val);

            else if (right == null) {
                right = new BSTNode<>(key, val, null, null);
                size++;
            }
            else right.put(key, val);
        }
    }

    private class keyIterator<K> implements Iterator<K> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return null;
        }
    }
}
