package com.self.test.DataStructs;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class is an abstraction of Binary Search Tree, and it provides the following
 * utility methods to create a tree, extract elements from the tree, and search
 * within this tree.
 *
 * Methods are provided as either Static (requires the "root" node as input), or as
 * instance methods of the Tree class
 *
 * TBD: Adding and removing elements while keeping the tree height balanced
 */
public class BSTHelper {


    /**
     * Defining a data structure to represent a single node in a binary tree
     * TBD: This belongs to a separate file (and can be reused for representing a binary tree)
     */
    static class Node {
        int data;
        Node left;
        Node right;

        public Node(int val) {
            data = val;
            left = null;
            right = null;
        }

        /**
         * Method to create left child of a node
         * @param val - value of the left child
         */
        public void insertLeftChild(int val) {
            left = new Node(val);
        }

        /**
         * Method to create a right child of a node
         * @param val - value of the right child
         */
        public void insertRightChild(int val) {
            right = new Node(val);
        }

        /**
         * Method for creating left and right sub-objects of an existing
         * Node object
         * @param leftVal - value of left child
         * @param rightVal - value of right child
         */
        public void insertLeftRightChild(int leftVal, int rightVal) {
            left = new Node(leftVal);
            right = new Node(rightVal);
        }

        /**
         * Overloaded constructor for creating a complete Node object with its left and right
         * sub-objects
         * @param nodeVal - value of the node object
         * @param leftVal - value of left child
         * @param rightVal - value of right child
         */
        public Node(int nodeVal, int leftVal, int rightVal) {
            data = nodeVal;
            left = new Node(leftVal);
            right = new Node(rightVal);
        }
    }

    /**
     * Tree data structure
     * Dependencies:
     * a) Node: This is to represent any node of the given tree
     *
     * Supported features:
     * Following features are supported by this data structure, normally as static methods
     * and also as abstracted instance methods:
     * 1) Create Tree from a node object
     * 2) Create minimal Binary Search Tree from an array
     * 3) Flattens BST to a sorted array
     * 4) Compute height of a tree
     * 5) Check if this is a binary search tree (multiple ways)
     * 6) Return in-order successor of tree
     * 7) Return array list of level-order traversals
     * 8) Check if this is a "minimal" Binary Search Tree (difference in heights of subtrees from any node
     *    is less than or equal to 1)
     */
    static class Tree{
        Node root;
        int height;

        public Tree(int val){
            root = new Node(val);
            height = 1;
        }

        public Tree(Node node) {
            root = node;
            height = computeHeight();
        }

        public Tree(Integer[] arr){
            if (arr != null) {
                ArrayList<Integer> arrList = new ArrayList<Integer>(Arrays.asList(arr));
                arrList.sort(Comparator.naturalOrder()); // sort ascending
                root = createMinimalBST((Integer[])arrList.toArray(new Integer[arr.length]), 0, arrList.size() - 1);
                height = computeHeight();
            }
        }

        public static Node createMinimalBST(Integer [] arr, int minIndex, int maxIndex) {
            if (minIndex > maxIndex) {
                return null;
            }

            // Get current index to create a node
            System.out.println("[createMinimalBST] Min Index: " + minIndex + ", Max Index: " + maxIndex);
            int currIndex = (maxIndex + minIndex) / 2;
            Node currNode = new Node(arr[currIndex]);
            currNode.left = createMinimalBST(arr, minIndex, currIndex - 1);
            currNode.right = createMinimalBST(arr, currIndex + 1, maxIndex);
            return currNode;
        }

        // Inorder traversal of tree and flatten it to an array
        /**
         *
         * @param root
         * @param arr
         * @param index
         * @return
         */
        public static int convertToArray(Node root, int[] arr, int index){
            if (root == null) {
                return index;
            }

            index = convertToArray(root.left, arr, index);
            System.out.println("[ConvertToArray] Index = " + index + ", current data = " + root.data);
            arr[index] = root.data; // TODO: size check!
            index = index + 1;
            index = convertToArray(root.right, arr, index);

            return index;
        }

        public static int getTreeHeight(Node root, int currDepth) {
            if (root == null) {
                return currDepth - 1; // base case
            }
            int leftDepth = getTreeHeight(root.left, currDepth + 1);
            int rightDepth = getTreeHeight(root.right, currDepth + 1);
            System.out.println("[getTreeHeight] Current Depth: " + currDepth + ", Depth left: "
                    + leftDepth + ", Depth Right: " + rightDepth);
            return (Math.max(leftDepth,rightDepth));
        }

        public int computeHeight() {
            return getTreeHeight(root, 1);
        }

        private Integer checkBSTRecursionDeprecated(Node root){
            if (root == null) {
                return null;
            }
            Integer val;
            Integer valLeft;
            Integer valRight;
            valLeft = checkBSTRecursionDeprecated(root.left);
            val = root.data;
            System.out.println("[checkBSTRecursionDeprecated] Current Data = " + val);
            valRight = checkBSTRecursionDeprecated(root.right);

            System.out.println("[checkBSTRecursionDeprecated] Left = " + valLeft
                    + "Current Data = " + val + ", Right = " + valRight);

            if ((valLeft != null && (valLeft > val || valLeft == Integer.MIN_VALUE))
                    || (valRight != null && (valRight < val || valRight == Integer.MIN_VALUE)))
                return Integer.MIN_VALUE;
            else
                return val;
        }

        // Passing Integer object to store state of last printed data
        public boolean checkBSTRecursion(Node root, Integer lastPrinted) {
            if (root == null) {
                return true;
            }

            // Inorder traversal
            if (!checkBSTRecursion(root.left, lastPrinted))
                return false;

            if (lastPrinted != null && root.data <= lastPrinted) {
                return false;
            }

            lastPrinted = root.data; // store the current value

            if (!checkBSTRecursion(root.right, lastPrinted)) {
                return false;
            }

            return true;
        }

        public boolean checkBSTMinMax(Node root, Integer min, Integer max) {
            if (root == null) {
                return true;
            }

            if (!checkBSTMinMax(root.left, null, root.data) ||
                    ((min != null && root.data <= min)
                    || (max != null && root.data > max)) ||
                    !checkBSTMinMax(root.right, root.data, null)){
                System.out.println("[checkBSTMinMax] BST check failed for node: " + root.data
                        + ", min: " + min + ", max: " + max);
                return false;
            }
            else {
                System.out.println("[checkBSTMinMax] BST check succeeded for node: " + root.data
                        + ", min: " + min + ", max: " + max);
                return true;
            }
        }

        public Node findInorderSuccessor(Node node) {
            Node successor = null;

            do {
                if (node == null) {
                    // handle base case
                    break;
                }

                // Find out if there is a right subtree relative to the input node
                if (node.right != null) {
                    successor = findMinimalNode(node);
                    break;
                }

                // Traverse from root, and find the node "just" greater than the current node
                int val = node.data;
                node = root;
                while (node != null && node.data != val) {
                    if (node.data <= val) {
                        node = node.right;
                    }
                    else {
                        // Successor is the parent of the left subtree
                        successor = node;
                        node = node.left;
                    }
                }

                if (node == null) {
                    successor = null;
                }

            }while (false);

            return successor;
        }

        public void createLevelOrderLists(ArrayList<LinkedList<Node>> nodeList) {
            if (nodeList != null)
                createLevelOrderLists(root, nodeList, 0);
        }

        public static void createLevelOrderLists(Node node, ArrayList<LinkedList<Node>> nodeList, int index){
            if (node == null) {
                return; // Base case
            }

            LinkedList currentList;
            if (index >= nodeList.size()) {
                // Add current list if no list exists at that index
                currentList = new LinkedList<Node>();
                nodeList.add(currentList);
                System.out.println("[createLevelOrderLists] Creating linked list at level " + index);
            }
            else {
                // Retrieve current list from existing array list
                currentList = nodeList.get(index);
           }
            currentList.add(node);
            createLevelOrderLists(node.left, nodeList, index + 1);
            createLevelOrderLists(node.right, nodeList, index + 1);
        }

        public static void createLevelOrderListsBFS(Node node, ArrayList<LinkedList<Node>> nodeLists){
            BFSQueue bfsQueue = new BFSQueue();

            if (node == null) {
                return;
            }

            // Add the root node
            int currLevel = 0;
            int nodeListsSize = nodeLists.size();
            LinkedList<Node> currentList;
            bfsQueue.addToVisit(node, currLevel);
            BFSQueue.NodeElement nodeElement;
            // Iterate till the BFS queue is empty
            while (!bfsQueue.isEmpty()) {
                nodeElement = (BFSQueue.NodeElement)bfsQueue.remove();
                currLevel = nodeElement.level;
                bfsQueue.addToVisit(nodeElement.node.left, currLevel + 1);
                bfsQueue.addToVisit(nodeElement.node.right, currLevel + 1);

                if (currLevel >= nodeListsSize) {
                    currentList = new LinkedList<>();
                    nodeLists.add(currentList);
                    nodeListsSize++;
                }
                else {
                    currentList = nodeLists.get(currLevel);
                }

                nodeElement.visitStatus = BFSQueue.NODE_VISITED.eVisited;
                System.out.println("[createLevelOrderListsBFS] Visited node: "
                        + nodeElement.node.data + ", level: " + currLevel);
                currentList.add(nodeElement.node);
            }

            // Print the lists
            Iterator it = nodeLists.iterator();
            while (it.hasNext()){
                LinkedList currList = (LinkedList<Node>)it.next();
                Iterator itList = currList.iterator();
                while (itList.hasNext()){
                    System.out.print(((Node)itList.next()).data + " ");
                }
                System.out.println();
            }

        }

        private Node findMinimalNode(Node node) {

            if (node == null) {
                return null;
            }

            do {
                node = node.left;
            }while (node.left != null);

            return node;
        }

        public static boolean checkTreeBalanced(Node node) {
            if (node == null) {
                return true; // base case
            }

            int heightDiff =
                    (Math.abs(getTreeHeight(node.left, 0) - getTreeHeight(node.right, 0)));
            if (heightDiff > 1) {
                return false;
            }
            else {
                return (checkTreeBalanced(node.left) && checkTreeBalanced(node.right));
            }
        }


    }


    // Test code using this class
    public static void main (String[] args) {
        Node node = new Node(10, 5, 15);
        node.left.insertLeftRightChild(4,6);
        node.right.insertLeftRightChild(13,16);
        Tree tree = new Tree(node);

        // Currently hardcode size
        int size = 7;
        int treeArr[] = new int[7];
        //tree.convertToArray(node, treeArr, 0);
        //System.out.println(tree.checkBSTRecursionDeprecated(node));
        Integer lastPrinted = null;
        //System.out.println(tree.checkBSTRecursion(node, lastPrinted));
        //System.out.println(tree.checkBSTMinMax(node, null, null));
//        Node succ = tree.findInorderSuccessor(node.left.right);
//        System.out.println(succ.data);
        Integer[] arr = {1, 3, 4, 5, 8, 9, 11, 12, 15, 16, 17, 18};
        Tree newTree = new Tree(arr);
        //System.out.println(tree.computeHeight());
        ArrayList<LinkedList<Node>> levelOrderLists = new ArrayList<>();
//        Tree.createLevelOrderLists(newTree.root, levelOrderLists, 0);
//        Iterator it = levelOrderLists.iterator();
//        while (it.hasNext()){
//            LinkedList currList = (LinkedList<Node>)it.next();
//            Iterator itList = currList.iterator();
//            while (itList.hasNext()){
//                System.out.print(((Node)itList.next()).data + " ");
//            }
//            System.out.println();
//        }
        Tree.createLevelOrderListsBFS(newTree.root, levelOrderLists);
    }

}
