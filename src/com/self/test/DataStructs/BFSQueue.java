package com.self.test.DataStructs;

import java.util.concurrent.LinkedBlockingQueue;
import com.self.test.DataStructs.BSTHelper.Node;

public class BFSQueue extends LinkedBlockingQueue{
    enum NODE_VISITED {
        eNotVisited,
        eVisiting,
        eVisited
    }

    public class NodeElement {
        BSTHelper.Node node;
        NODE_VISITED visitStatus;
        int level; // optional

        public NodeElement(Node n) {
            node = n;
            visitStatus = NODE_VISITED.eNotVisited;
        }
    }

    public LinkedBlockingQueue<NodeElement> queue;

    public boolean add(Node n) {
        if (n == null){
            return false;
        }

        NodeElement nodeElement = new NodeElement(n);
        return super.add(nodeElement);
    }

    public boolean addToVisit(Node n) {
        if (n == null)
            return false;

        NodeElement nodeElement = new NodeElement(n);
        nodeElement.visitStatus = NODE_VISITED.eVisiting;
        return super.add(nodeElement);
    }

    public boolean addToVisit(Node n, int level) {
        if (level >= 0) {
            if (n == null)
                return false;

            NodeElement nodeElement = new NodeElement(n);
            nodeElement.visitStatus = NODE_VISITED.eVisiting;
            nodeElement.level = level;
            return super.add(nodeElement);
        }
        else
            return false;
    }
}
