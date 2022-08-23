package me.x1xx.bees.utility.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private final List<Node<T>> children;
    private T data;
    private Node<T> parent;
    private int depth;

    public Node(T data) {
        this.children = new ArrayList<>();
        this.parent = null;
        this.data = data;
        this.depth = 0;
    }

    public Node(T data, Node<T> parent) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.depth = parent.getDepth()+1;
        parent.addChild(this);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setParent(Node<T> parent) {
        this.depth = parent.getDepth() + 1;
        parent.addChild(this);
        this.parent = parent;
    }

    public void addChild(T data) {
        Node<T> node = new Node<>(data);
        this.children.add(node);
    }

    public void addChild(Node<T> node) {
        this.children.add(node);
    }

    public boolean isRootNode() {
        return this.parent == null;
    }

    public boolean isLeafNode() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }

    @Override
    public String toString() {
        String out = "";
        out += "Node: " + this.getData().toString() + " | Depth: " + this.depth + " | Parent: " + (this.getParent() == null ? "None" : this.getParent().getData().toString()) + " | Children: " + (this.getChildren().size() == 0 ? "None" : "");
        for (Node<T> child : this.getChildren()) {
            out += "\n\t" + child.getData().toString() + " | Parent: " + (child.getParent() == null ? "None" : child.getParent().getData());
        }
        return out;
    }
}
