/*  ==============================================================
**      Author: Alec Maughan 19513869
**     Purpose: Full implementation of Binary Search Tree
**        Date: 27/8/19
**  ==============================================================
*/
import java.util.*;
import java.io.*;

public class DSABinarySearchTree implements Serializable
{
    private class DSATreeNode implements Serializable
    {
        /** ================== DSATreeNode ================== **/

        /** Private Classfields **/
        private int m_key;
        private Object m_value;
        private DSATreeNode m_leftChild, m_rightChild;

        /** Constructor **/
        /*  --------------------------------------------------------------
        ** Constructor: Alternate
        **      Import: inKey (int), inValue (Object)
        **      Export: Memory adress of new DSATreeNode
        **   Assertion: Sets left and right child to null and sets key
        **                  and value to given values
        */
        private DSATreeNode( int inKey, Object inValue )
        {
            m_key = inKey;
            m_value = inValue;
            m_leftChild = null;
            m_rightChild = null;
        }

        /** Accessors **/
        /*  --------------------------------------------------------------
        **   Submodule: getKey
        **      Import: none
        **      Export: m_key (int)
        **   Assertion: Returns the key for the node
        */
        private int getKey()
        {
            return m_key;
        }

        /*  --------------------------------------------------------------
        **   Submodule: getValue
        **      Import: none
        **      Export: m_value (Object)
        **   Assertion: Returns the value of the node
        */
        private Object getValue()
        {
            return m_value;
        }

        /*  --------------------------------------------------------------
        **   Submodule: getLeft
        **      Import: none
        **      Export: m_leftChild (DSATreeNode)
        **   Assertion: Returns reference to the left child node
        */
        private DSATreeNode getLeft()
        {
            return m_leftChild;
        }

        /*  --------------------------------------------------------------
        **   Submodule: getRight
        **      Import: none
        **      Export: m_rightChild (DSATreeNode)
        **   Assertion: Returns reference to the right child node
        */
        private DSATreeNode getRight()
        {
            return m_rightChild;
        }

        /** Mutators **/
        /*  --------------------------------------------------------------
        **   Submodule: setLeft
        **      Import: inLeft (DSATreeNode)
        **      Export: none
        **   Assertion: Sets the left node of the current node
        */
        private void setLeft( DSATreeNode inLeft )
        {
            m_leftChild = inLeft;
        }

        /*  --------------------------------------------------------------
        **   Submodule: setRight
        **      Import: inRight
        **      Export: none
        **   Assertion: Sets the right node of the current node
        */
        private void setRight( DSATreeNode inRight )
        {
            m_rightChild = inRight;
        }
    }


    /** ================== DSABinarySearchTree ================== **/

    /** Private Classfield **/
    private DSATreeNode m_root;

    /** Constructor **/
    /*  --------------------------------------------------------------
    ** Constructor: Default
    **      Import: none
    **      Export: Memory address of new DSABinarySearchTree object
    **   Assertion: Sets root node reference to null
    */
    public DSABinarySearchTree()
    {
        m_root = null;
    }

    /** Accessors **/
    /*  --------------------------------------------------------------
    **   Submodule: find
    **      Import: key (int)
    **      Export: (Object)
    **   Assertion: Wrapper submodule for findRec() recursive algorithm
    */
    public Object find( int key )
    {
        return findRec( key, m_root );
    }

    /*  --------------------------------------------------------------
    **   Submodule: display
    **      Import: none
    **      Export: none
    **   Assertion: Uses inorderRec to populate a queue of elements in tree
    **                  in infix order, then prints all elements using queue
    **                  iterator
    */
    public void display()
    {
        DSAQueue inorderQueue = new DSAQueue();

        inorderRec( m_root, inorderQueue );

        for( Object o : inorderQueue )
        {
            System.out.print( o + " " );
        }
        System.out.println();
    }

    /*  --------------------------------------------------------------
    **   Submodule: height
    **      Import: none
    **      Export: (int)
    **   Assertion: Wrapper submodule for heightRec() recursive algorithm,
    **                  finds the height of the tree
    */
    public int height()
    {
        return heightRec( m_root );
    }

    /*  --------------------------------------------------------------
    **   Submodule: min
    **      Import: none
    **      Export: (int)
    **   Assertion: Wrapper submodule for minRec() recursive algorithm,
    **                  finds the minimum key in the tree
    */
    public int min()
    {
        return minRec( m_root );
    }

    /*  --------------------------------------------------------------
    **   Submodule: max
    **      Import: none
    **      Export: (int)
    **   Assertion: Wrapper submodule for maxRec() recursive algorithm,
    **                  finds the maximum key in the tree
    */
    public int max()
    {
        return maxRec( m_root );
    }

    /*  --------------------------------------------------------------
    **   Submodule: balance
    **      Import: none
    **      Export: balance (double)
    **   Assertion: 
    */
    public double balance()
    {
        int leftHt, rightHt, rootHt, diffHt;
        double balance;

        if( m_root == null )
        {
            balance = 1;
        }
        else
        {
            leftHt = heightRec( m_root.getLeft() );
            rightHt = heightRec( m_root.getRight() );
            rootHt = height();
            diffHt = Math.abs( leftHt - rightHt );
            balance = 1 - ( (double)diffHt ) / ( (double)rootHt );
        }

        return balance;
    }

    /*  --------------------------------------------------------------
    **   Submodule: traversePreorder
    **      Import: none
    **      Export: preorderQueue (DSAQueue)
    **   Assertion: Wrapper submodule for preorderRec() recursive algorithm
    */
    public DSAQueue traversePreorder()
    {
        DSAQueue preorderQueue = new DSAQueue();

        preorderRec( m_root, preorderQueue );

        return preorderQueue;
    }

    /*  --------------------------------------------------------------
    **   Submodule: traverseInorder
    **      Import: none
    **      Export: inorderQueue (DSAQueue)
    **   Assertion: Wrapper submodule for inorderRec() recursive algorithm
    */
    public DSAQueue traverseInorder()
    {
        DSAQueue inorderQueue = new DSAQueue();

        inorderRec( m_root, inorderQueue );

        return inorderQueue;
    }

    /*  --------------------------------------------------------------
    **   Submodule: traversePostorder
    **      Import: none
    **      Export: postorderQueue (DSAQueue)
    **   Assertion: Wrapper submodule for postorderRec() recursive algorithm
    */
    public DSAQueue traversePostorder()
    {
        DSAQueue postorderQueue = new DSAQueue();

        postorderRec( m_root, postorderQueue );

        return postorderQueue;
    }

    /** Mutators **/
    /*  --------------------------------------------------------------
    **   Submodule: insert
    **      Import: key (int), value (Object)
    **      Export: none
    **   Assertion: Wrapper submodule for insertRec() recursive algorithm
    */
    public void insert( int key, Object value )
    {
        insertRec( key, value, m_root );
    }

    /*  --------------------------------------------------------------
    **   Submodule: delete
    **      Import: key (int)
    **      Export: none
    **   Assertion: Wrapper submodule for deleteRec() recursive algorithm
    */
    public void delete( int key )
    {
        deleteRec( key, m_root );
    }

    /** Private Helper Algorithms **/
    /*  --------------------------------------------------------------
    **   Submodule: findRec
    **      Import: key (int), currNode (DSATreeNode)
    **      Export: value (Object)
    **   Assertion: Recursive algorithm to find a key in the tree
    */
    private Object findRec( int key, DSATreeNode currNode )
    {
        Object value = null;

        if( currNode == null )
        {
            // Base case: Key not found
            throw new NoSuchElementException( "Key " + key + " not found" );
        }
        else if( key == currNode.getKey() )
        {
            // Base case: Key found
            value = currNode.getValue();
        }
        else if( key < currNode.getKey() )
        {
            // Recurse left
            value = findRec( key, currNode.getLeft() );
        }
        else
        {
            // Recurse right
            value = findRec( key, currNode.getRight() );
        }

        return value;
    }

    /*  --------------------------------------------------------------
    **   Submodule: insertRec
    **      Import: key (int), data (Object), currNode (DSATreeNode)
    **      Export: updateNode (DSATreeNode)
    **   Assertion: Recursive algorithm to insert a new leaf into the correct
    **                  position in the tree
    */
    private DSATreeNode insertRec( int key, Object data, 
        DSATreeNode currNode )
    {
        DSATreeNode updateNode = currNode;

        if( currNode == null )
        {
            // Base case: Spot found
            updateNode = new DSATreeNode( key, data );
            if( m_root == null )
            {
                m_root = updateNode;
            }
        }
        else if( key == currNode.getKey() )
        {
            // Base case: Key already in the tree
            throw new IllegalArgumentException( 
                "Key already exists in tree" );
        }
        else if( key < currNode.getKey() )
        {
            // Recurse left
            currNode.setLeft( insertRec( key, data, currNode.getLeft() ) );
        }
        else
        {
            // Recurse right
            currNode.setRight( insertRec( key, data, currNode.getRight() ) );
        }

        return updateNode;
    }

    /*  --------------------------------------------------------------
    **   Submodule: deleteRec
    **      Import: key (int), currNode (DSATreeNode)
    **      Export: updateNode (DSATreeNode)
    **   Assertion: Recursive algorithm to delte a given key
    */
    private DSATreeNode deleteRec( int key, DSATreeNode currNode )
    {
        DSATreeNode updateNode = currNode;

        if( currNode == null )
        {
            // Base case: Not in the tree
            throw new NoSuchElementException( "Key " + key + " not found" );
        }
        else if( key == currNode.getKey() )
        {
            // Base case: Key found
            updateNode = deleteNode( key, currNode );
        }
        else if( key < currNode.getKey() )
        {
            // Recurse left
            currNode.setLeft( deleteRec( key, currNode.getLeft() ) );
        }
        else
        {
            // Recurse right
            currNode.setRight( deleteRec( key, currNode.getRight() ) );
        }

        return updateNode;
    }

    /*  --------------------------------------------------------------
    **   Submodule: deleteNode
    **      Import: key (int), delNode (DSATreeNode)
    **      Export: updateNode (DSATreeNode)
    **   Assertion: Deletes a given node and maintains BST functionality
    */
    private DSATreeNode deleteNode( int key, DSATreeNode delNode )
    {
        DSATreeNode updateNode = null;

        if( ( delNode.getLeft() == null ) && ( delNode.getRight() == null ) )
        {
            // Node to delete has no children
            updateNode = null;
        }
        else if( ( delNode.getLeft() != null ) && 
            ( delNode.getRight() == null ) )
        {
            // Node has one child on the left
            updateNode = delNode.getLeft();
        }
        else if( ( delNode.getLeft() == null ) && 
            ( delNode.getRight() != null ) )
        {
            // Node has one child on the right
            updateNode = delNode.getRight();
        }
        else
        {
            // Node has two children
            updateNode = promoteSuccessor( delNode.getRight() );
            if( updateNode != delNode.getRight() )
            {
                updateNode.setRight( delNode.getRight() );
                    // Update right
            }
            updateNode.setLeft( delNode.getLeft() );
                // Update left
        }

        return updateNode;
    }

    /*  --------------------------------------------------------------
    **   Submodule: promoteSuccessor
    **      Import: currNode (DSATreeNode)
    **      Export: successor (DSATreeNode)
    **   Assertion: Helper submodule for deleteNode, promotes the left-most
    **                  node from the given node.
    */
    private DSATreeNode promoteSuccessor( DSATreeNode currNode )
    {
        DSATreeNode successor = currNode;

        if( currNode.getLeft() != null )
        {
            successor = promoteSuccessor( currNode.getLeft() );
            if( successor == currNode.getLeft() )
            {
                currNode.setLeft( successor.getRight() );
            }
        }

        return successor;
    }

    /*  --------------------------------------------------------------
    **   Submodule: heightRec
    **      Import: currNode (DSATreeNode)
    **      Export: currHt (int)
    **   Assertion: Recursive algorithm to find the height of the tree
    */
    private int heightRec( DSATreeNode currNode )
    {
        int currHt, iLeftHt, iRightHt;

        if( currNode == null )
        {
            // Base case: No more nodes on this branch
            currHt = -1;
        }
        else
        {
            iLeftHt = heightRec( currNode.getLeft() );
            iRightHt = heightRec( currNode.getRight() );
                // Calculate left & right height from here

            currHt = Math.max( iLeftHt, iRightHt ) + 1;
                // Current height is changed depending on of left or right
                // branch is highest
        }

        return currHt;
    }

    /*  --------------------------------------------------------------
    **   Submodule: inorderRec
    **      Import: root (DSATreeNode), queue (DSAQueue)
    **      Export: none
    **   Assertion: Recursive algorithm to store inorder traversal of tree
    */ 
    private void inorderRec( DSATreeNode root, DSAQueue queue )
    {
        if( root != null )
        {
            inorderRec( root.getLeft(), queue );
                // Recurse left
            queue.enqueue( root.getValue() );
                // Store root value
            inorderRec( root.getRight(), queue );
                // Recurse right
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: postorderRec
    **      Import: root (DSATreeNode), queue (DSAQueue)
    **      Export: none
    **   Assertion: Recursive algorithm to store postorder traversal of tree
    */ 
    private void postorderRec( DSATreeNode root, DSAQueue queue )
    {
        if( root != null )
        {
            postorderRec( root.getLeft(), queue );
                // Recurse left
            postorderRec( root.getRight(), queue );
                // Recurse right
            queue.enqueue( root.getValue() );
                // Store root value
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: preorderRec
    **      Import: root (DSATreeNode), queue (DSAQueue)
    **      Export: none
    **   Assertion: Recursive algorithm to store preorder traversal of tree
    */ 
    private void preorderRec( DSATreeNode root, DSAQueue queue )
    {
        if( root != null )
        {
            queue.enqueue( root.getValue() );
                // Store root value
            postorderRec( root.getLeft(), queue );
                // Recurse left
            postorderRec( root.getRight(), queue );
                // Recurse right
        }
    }

    /*  --------------------------------------------------------------
    **   Submodule: minRec
    **      Import: currNode (DSATreeNode)
    **      Export: minKey (int)
    **   Assertion: Recursive algorithm to find the minimum key in tree
    */ 
    private int minRec( DSATreeNode currNode )
    {
        int minKey;

        if( currNode.getLeft() != null )
        {
            minKey = minRec( currNode.getLeft() );
                // Recurse left
        }
        else
        {
            minKey = currNode.getKey();
                // Base case
        }

        return minKey;
    }

    /*  --------------------------------------------------------------
    **   Submodule: maxRec
    **      Import: currNode (DSATreeNode)
    **      Export: maxKey (int)
    **   Assertion: Recursive algorithm to find the maximum key in tree
    */ 
     private int maxRec( DSATreeNode currNode )
    {
        int maxKey;

        if( currNode.getRight() != null )
        {
            maxKey = maxRec( currNode.getRight() );
                // Recurse right
        }
        else
        {
            maxKey = currNode.getKey();
                // Base case
        }

        return maxKey;
    }
}
