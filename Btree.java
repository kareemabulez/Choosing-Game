import com.sun.tools.hat.internal.model.Root;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Btree<E> {

    public static Node RootNode = null, recentNode = null;
    public static String LeftNode = "Do you have a life ?", RightNode = "Holland College";
    static Scanner scan = new Scanner(System.in);
    static String userInput = "";

    public static void main(String[]args){
        Btree b = new Btree();
        System.out.println("Play game");
        System.out.println("Load game");
        System.out.println("Save game");
        System.out.println("Quit game");

        System.out.print("Your choice: ");
        userInput = scan.nextLine();

        while(!userInput.toLowerCase().equals("quit game")){
            if(userInput.toLowerCase().equals("play game")){ b.gameMenu();}
            else if(userInput.toLowerCase().equals("save game")) {b.saveAgame();}
            else if(userInput.toLowerCase().equals("load game")) {b.loadAgame();}
            else if(userInput.toLowerCase().equals("quit game")) {return;}

            System.out.println("Play game");
            System.out.println("Load game");
            System.out.println("Save game");
            System.out.println("Quit game");

            System.out.print("Your choice: ");
            userInput = scan.nextLine();
        }
    }

    public Btree(){
        RootNode = new Node("Are you in UPEI ?", null, null);
        recentNode = new Node(LeftNode, RootNode, null, null);
        RootNode.setLeft(recentNode);

        recentNode = new Node(RightNode, RootNode, null, null);
        RootNode.setRight(recentNode);
    }

    public static void fileCreator(PrintWriter writer, Node RootNode) {
        if(RootNode == null)
            return;

        fileCreator(writer,RootNode.getLeft());
        fileCreator(writer,RootNode.getRight());
        writer.println(RootNode.getParent());
    }

    public static Node treeCreator(Scanner sc, Node RootNode) {

        String temp = " ";
        if(sc.hasNextLine()) {temp = sc.nextLine();}
        else {
            System.out.println("Loaded Successfully");
            return RootNode;
        }

        if (temp.charAt(temp.length()-1) == '?') {
            System.out.println("Root:"+temp);

            recentNode = new Node(temp,RootNode, null, null);
            recentNode.setLeft(treeCreator(sc,recentNode));
            recentNode.setRight(treeCreator(sc,recentNode));
            return RootNode;
        } else {
            System.out.println("NODE: "+temp);
            recentNode = new Node(temp, RootNode, null, null);
            recentNode.setLeft(treeCreator(sc, recentNode));
            recentNode.setRight(treeCreator(sc, recentNode));
            return recentNode;
        }

    }

    protected static class Node<E> {
        private E element;          // an element stored at this node
        private Node<E> parent;     // a reference to the parent node (if any)
        private Node<E> left;       // a reference to the left child (if any)
        private Node<E> right;      // a reference to the right child (if any)

        public Node(E e, Node<E> root, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            parent = root;
            left = leftChild;
            right = rightChild;
        }

        public Node(E e, Node left, Node right){
            element = e;
            this.left = left;
            this.right = right;
        }

        public void setParent(Node<E> parentNode) { parent = parentNode; }
        public Node<E> getParent() { return parent; }

        public void setLeft(Node<E> newLeft) { left = newLeft; }
        public Node<E> getLeft(){ return left; }

        public void setRight(Node<E> newRight) { right = newRight; }
        public Node<E> getRight(){ return right; }

        public E getElement() {
            return element;
        }
    }

    public void gameMenu(){
        String chooser;

        System.out.println("Are you in UPEI ?");
        chooser = scan.nextLine();

        playAgame(chooser, RootNode, "");

        while(!chooser.toLowerCase().equals("Quit game")){
            System.out.println("Do you want to play another round?");
            chooser = scan.nextLine();

            if(chooser.toLowerCase().equals("yes")){
                System.out.println("Are you in UPEI ?");
                chooser = scan.nextLine();
                playAgame(chooser, RootNode, "");
            }
            else {return;}
        }
    }

    public void playAgame(String chooser, Node RootNode, String questOrAns) {
        if (chooser.equals("return"))
            return;

        if (RootNode.getLeft() != null && questOrAns.toLowerCase().equals("yes")) {
            System.out.println(RootNode.getLeft().getElement());
            System.out.println("What do want to do?" + "\n" + "Play Game" + "\n" +
                    "Load Game" + "\n" + "Save Game");
        }

        if (RootNode.getRight() != null && chooser.toLowerCase().equals("no")) {
            System.out.println(RootNode.getRight().getElement());
            chooser = scan.nextLine();
            playAgame(chooser, RootNode.getRight(), questOrAns);
        }

        if (RootNode.getLeft() == null && chooser.toLowerCase().equals("yes")) {
            System.out.println("I am stuck now please tell me what is it ?");
            questOrAns = scan.nextLine();
            Node newNodeLeft = new Node(questOrAns, RootNode, null, null);
            System.out.println("Please type a yes/no question, that is yes for a " + chooser + " but no for a " + RootNode.getElement() + ":");
            questOrAns = scan.nextLine();
            Node newNode2 = new Node(questOrAns, RootNode, newNodeLeft, RootNode.getLeft());
            RootNode.setLeft(newNode2);
            playAgame("return", null, "");
        }

        if (RootNode.getRight() == null && chooser.toLowerCase().equals("no")) {
            System.out.println("I am stuck now please tell me what is it ?");
            questOrAns = scan.nextLine();
            Node newNodeLeft = new Node(questOrAns, RootNode, null, null);

            System.out.println("Please type a yes/no question, that is yes for a " + questOrAns + " but no for a "+ RootNode.getElement() + ":");
            questOrAns = scan.nextLine();

            Node newNode2 = new Node(questOrAns, RootNode, newNodeLeft, RootNode.getRight());
            RootNode.setRight(newNode2);
            playAgame("return", null, "");

        } else if (RootNode.getRight() == null && RootNode.getLeft() == null) {
            System.out.println("1 : I am stuck now please tell me what is it ?");
            questOrAns = scan.nextLine();
            Node newNodeLeft = new Node(questOrAns, RootNode, null, null);
            System.out.println("Please type a yes/no question, that is yes for a " + RootNode + " but no for "+ RootNode.getElement() +":");
            questOrAns = scan.nextLine();
            Node newNode2 = new Node(chooser, RootNode, newNodeLeft, RootNode.getRight());
            RootNode.setLeft(newNode2);
            return;
        }
    }

    public void saveAgame() {
        System.out.print("Enter the game file name:");
        String fileName = scan.nextLine();

        try {
            PrintWriter writer = new PrintWriter(fileName);
            fileCreator(writer, RootNode);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving..");
        }
    }

    public void loadAgame(){
        System.out.print("Enter the file name to load it: ");
        String loadedFile = scan.nextLine();
        try
        {
            File file = new File(loadedFile);
            Scanner fileScan = new Scanner(file);
            treeCreator(fileScan, null);
            new Btree();

        }
        catch (FileNotFoundException e)
        {
            System.out.println("File couldn't be found");
        }
    }


}
