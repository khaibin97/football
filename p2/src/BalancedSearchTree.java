// starter class for a BalancedSearchTree
// you may implement AVL, Red-Black, 2-3 Tree, or 2-3-4 Tree
// be sure to include in class header which tree you have implemented
public class BalancedSearchTree<T extends Comparable<T>> implements SearchTreeADT<T> {

    // inner node class used to store key items and links to other nodes
    protected class Treenode<K extends Comparable<K>> {
        public Treenode(K item) {
            this(item,null,null);
        }
        public Treenode(K item, Treenode<K> left, Treenode<K> right) {
            key = item;
            this.left = left;
            this.right = right;
        }
        public Treenode(K item, Treenode<K> parent) {
            this(item,null,null,parent);
        }
        public Treenode(K item, Treenode<K> left, Treenode<K> right,Treenode<K> parent) {
            key = item;
            this.left = left;
            this.right = right;
            this.parent=parent;
        }
        
        K key;
        Treenode<K> left;
        Treenode<K> right;
        Treenode<K> parent;
    }

    protected Treenode<T> root;

    @Override
    public String inAscendingOrder() {
        //TODO : must return comma separated list of keys in ascending order
        
        return inOrder(root) ;
    }
    private String inOrder(Treenode<T> node) {
        String result ="";
        if (!(node==null)) {
            
            inOrder(node.left);
            result=""+ node.key +",";
            inOrder(node.right);
        }
        return result;
    }

    @Override
    //hello
    public boolean isEmpty() {
        //TODO return empty if there are no keys in structure
        if (root==null) {
            return true;    
            }
        return false;
    }

    @Override
    public int height() {
        if (isEmpty()) {
            return 0;
        }
        
        //TODO return the height of this tree
        return heightHelper(root); 
    }
    

    @Override
    public boolean lookup(T item) {
        //TODO must return true if item is in tree, otherwise false
        if (isEmpty()) {
            return false;
        } else {
             
            return lookupHelper (item, root);
        }
        
    }

    @Override
    public void insert(T item) throws IllegalArgumentException, DuplicateKeyException {
        //TODO if item is null throw IllegalArgumentException, 
        // otherwise insert into balanced search tree
        if (item==null) {
            throw new IllegalArgumentException("null value not accepted");
        }
        if(lookup(item)==true) {
        throw new DuplicateKeyException();
        }
        else insertHelper(root,item);
        
        balancing(root);
    }

    @Override
    public void delete(T item) {
        //TODO if item is null or not found in tree, return without error
        // else remove this item key from the tree and rebalance

        // NOTE: if you are unable to get delete implemented
        // it will be at most 5% of the score for this program assignment
    }


    // HINT: define this helper method that can find the smallest key 
    // in a sub-tree with "node" as its root
    // PRE-CONDITION: node is not null
    private T leftMost(Treenode<T> node) {
        // TODO return the key value of the left most node in this subtree
        // or return node's key if node does not have a left child
        if ( node.left == null) {
            return node.key;
        } else {
            return leftMost(node.left);
        }
        
    }
    
    private int heightHelper(Treenode<T> node) {
        int lMax;
        int rMax;
        int hMax;
        if (node == null) {
            return 0;
        }
        else {
            lMax=heightHelper(node.left);
            rMax=heightHelper(node.right);
            hMax= (lMax>=rMax)?lMax+1:rMax+1;
            return hMax;
        }
        
    }
    private boolean lookupHelper(T item, Treenode<T> node) {
        if (node.key.equals(item)) {
            return true;
        } else if (item.compareTo(node.key)<0) {
            if (node.left.key == null) {
                return false;
            } else {
                return lookupHelper(item, node.left);
                
            }
        } else {                                                                          //might need fix
            if (node.right.key == null) {
                return false;
            } else {
                return lookupHelper(item, node.right);
            }
        }
        
    }
       private void insertHelper(Treenode<T> node, T item){
            if (node==null) node = new Treenode<T> (item);
            else if (node.key.compareTo(item)<0) {
                insertHelper(node.left,item);
            }
            else if (node.key.compareTo(item)>0) {
                insertHelper(node.right,item);
            }
            if (root == null) root = node;
        }
    private void balancing(Treenode<T> node) {
              int balance = getBalance(node);
              
              if (balance >1 && (node.key.compareTo(node.left.key)<0)) {
                  rightRotate(node);
              }
    }
       
    private Treenode<T> leftRotate(Treenode<T> curr) { //making right child the parent of current
         Treenode<T> rightNode = curr.right;
         Treenode<T> rLeftSubtree = rightNode.left;
         
         rightNode.left = curr; //rotates left
         curr.right = rLeftSubtree; //left subtree of right node becomes right subtree of "current"
         
         return rightNode; //current node
            
    }
    
    private Treenode<T> rightRotate(Treenode<T> curr) { //making left child the parent of current
        Treenode<T> leftNode = curr.left;
        Treenode<T> lRightSubtree = leftNode.right;
        
        leftNode.right = curr; //rotates right
        curr.left = lRightSubtree; //right subtree of left node becomes left subtree of "current"
        
        return leftNode; //current node
        
        
    }

    private int getBalance(Treenode<T> n) {
        if (n==null) {
            return 0;
        }
        return heightHelper(n.left) - heightHelper(n.right);
    }
    



}