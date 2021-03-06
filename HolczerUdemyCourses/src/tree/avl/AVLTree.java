package tree.avl;

public class AVLTree<T extends Comparable<T>> implements Tree<T> {
    private Node<T> root;

    @Override
    public void insert(T data) {
        root = insert(root, data);
    }

    @Override
    public void traverse() {
        if (isEmpty()) {
            throw new IllegalStateException("The tree is empty, insert nodes first.");
        }

        inOrderTraversal(root);
    }

    private Node<T> insert(Node<T> node, T data) {
        if (node == null) {
            return new Node<>(data);
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(insert(node.getLeft(), data));
        } else {
            node.setRight(insert(node.getRight(), data));
        }

        // update height
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);

        // handle imbalance if necessary
        node = settleViolation(node, data);

        return node;
    }

    public Node<T> delete(T data) {
        if (isEmpty()) {
            throw new IllegalStateException("The tree is empty, cannot delete");
        }

        return delete(root, data);
    }

    private Node<T> delete(Node<T> node, T data) {
        if (node == null) {
            return node;
        }

        // same cases as BST delete
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(delete(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(delete(node.getRight(), data));
        } else {
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }

            if (node.getRight() == null) {
                Node<T> left = node.getLeft();
                node = null;
                return left;
            } else if (node.getLeft() == null) {
                Node<T> right = node.getRight();
                node = null;
                return right;
            }

            Node<T> predecessor = getPredecessor(node.getLeft());
            node.setData(predecessor.getData());
            node.setLeft(delete(node.getLeft(), predecessor.getData()));
        }

        // update height
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
        // handle imbalance if necessary
        return settleViolation(node);
    }

    private Node<T> getPredecessor(Node<T> node) {
        if (node.getRight() == null) {
            return node;
        } else {
            return getPredecessor(node.getRight());
        }
    }

    private Node<T> settleViolation(Node<T> node, T data) {
        int balance = getBalance(node);
        // Case 1: left-left
        if (balance > 1 && data.compareTo(node.getLeft().getData()) < 0) {
            return rotateRight(node);
        }

        // Case 2: right-right
        if (balance < -1 && data.compareTo(node.getRight().getData()) > 0) {
            return rotateLeft(node);
        }

        // Case 3: left-right
        if (balance > 1 && data.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        // Case 4: right-left
        if (balance < -1 && data.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    private Node<T> settleViolation(Node<T> node) {
        int balance = getBalance(node);

        // Case 1: the tree itself is left heavy, but have to decide whether left-left or left-right
        if (balance > 1) {
            // Subcase 1: if left-right then left rotation on parent + right rotation on grandparent
            if (getBalance(node.getLeft()) < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            // Subcase 2: right rotation on grandparent (or if left-left then only a single rotation)
            return rotateRight(node);
        }

        // Case 2: the tree itself is right heavy, but have to decide whether right-right or right-left
        if (balance < -1) {
            // Subcase 1: if right-left then right rotation on parent + left rotation on grandparent
            if (getBalance(node.getRight()) > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            // Subcase 2: left rotation on grandparent (or if right-right then only a single rotation)
            return rotateLeft(node);
        }

        return node;
    }

    private int getBalance(Node<T> node) {
        if (node == null) {
            return 0;
        }

        return height(node.getLeft()) - height(node.getRight());
    }

    private int height(Node<T> node) {
        if (node == null) {
            return -1;
        }

        return node.getHeight();
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> tempLeft = node.getLeft();
        Node<T> tempRightOfLeft = tempLeft.getRight();

        tempLeft.setRight(node);
        node.setLeft(tempRightOfLeft);

        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
        tempLeft.setHeight(Math.max(height(tempLeft.getLeft()), height(tempLeft.getRight())) + 1);

        return tempLeft;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> tempRight = node.getRight();
        Node<T> tempLeftOfRight = tempRight.getLeft();

        tempRight.setLeft(node);
        node.setRight(tempLeftOfRight);

        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
        tempRight.setHeight(Math.max(height(tempRight.getLeft()), height(tempRight.getRight())) + 1);

        return tempRight;
    }

    private boolean isEmpty() {
        return root == null;
    }

    private void inOrderTraversal(Node<T> node) {
        if (node == null) {
            return;
        }

        inOrderTraversal(node.getLeft());
        System.out.println(node);
        inOrderTraversal(node.getRight());
    }

}
