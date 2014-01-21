package core.utils;

import core.models.WarehouseItem;
import java.util.List;
import java.util.ArrayList;

public class WarehouseItemBinaryTree
{
    private Node rootNode = null;

    /** Inserts a new new node into the binary search tree
     *
     * @param inData the new Data to be inserted
     * @return returns -1 or 1 either if the insert failed or insert succeeded. The insert will only fail if there is an attempt to enter a duplicate key
     */
    public int insert(WarehouseItem inData)
    {
        Node current = rootNode;
        Node nodeToInsert = new Node(inData);
        int compareValue;
        boolean finished = false;
        int status = -1;
        //The tree is empty
        if (rootNode == null)
        {
            rootNode = nodeToInsert;
            status = 1;
        }

        else
        {

            while (!finished)
            {
                compareValue = current.data.getKey().compareTo(inData.getKey());
                if (compareValue > 0)
                {
                    if (current.leftChild == null)
                    {
                        current.leftChild = nodeToInsert;
                        finished = true;
                        status = 1;
						
                    }
                    else
                        current = current.leftChild;

                }
                else if (compareValue < 0)
                {
                    if (current.rightChild == null)
                    {
                        current.rightChild = nodeToInsert;
                        finished = true;
                        status = 1;
                    }
                    else
                        current = current.rightChild;

                }
                else if (compareValue == 0)
                {
                    finished = true;
                    status = -1;
                }
            }
        }
        return status;
    }

    /** Finds an item in the binary search tree
     *
     * @param searchKey The ID to search for
     * @return returns the Item or null if it cannot be found
     */
    public WarehouseItem find(String searchKey)
    {
        Node foundNode = findNode(searchKey);
		return foundNode == null ? null : foundNode.data;
    }
    private Node findNode(String searchKey)
    {
		Node item = rootNode;
		int compareValue;
		while( item != null && (compareValue = item.data.getKey().compareTo(searchKey)) != 0 )
		{
			if(compareValue > 0)
			{
				item = item.leftChild;
			}	
			else if(compareValue < 0)
			{
				item = item.rightChild;
			}
		}    
		return item;
			
    }

    /** Finds the number of items in the binary tree
     *
     * @return the number of elements in the list
     */
    public int size()
    {
        return size(rootNode);
    }

    /** Where the heavy lifting goes for finding the size
     *
     * @param node the next node to being to count
     * @return the size of the tree where the paramater is the root
     */
    private int size(Node node)
    {
        //The tree is empty
        if (node == null)
            return 0;

        //The node has no children
        else if (node.leftChild == null && node.rightChild == null)
            return 1;

        //The node has one child, the left one
        else if (node.leftChild != null && node.rightChild == null)
            return 1 + size(node.leftChild);

        //The node has one child, the right one
        else if (node.rightChild != null && node.leftChild == null)
            return 1 + size(node.rightChild);

        //The node has two children
        else
            return 1 + size(node.leftChild) + size(node.rightChild);
    }

    /** Sorts the tree using an inorder traversal
     *
     * @return an array in sorted order
     */
	public WarehouseItem[] sort()
	{
		List<WarehouseItem> list = new ArrayList<WarehouseItem>();
		sortRecurse(rootNode, list);
		return list.toArray(new WarehouseItem[list.size()]);
	}

	private void sortRecurse(Node node, List<WarehouseItem> list)
	{
        //We hit the base case
		if(node == null)
			return;
        //traverse the left side
		sortRecurse(node.leftChild, list);
        //Add current data to list
		list.add(node.data);
        //traverse the right side
		sortRecurse(node.rightChild, list);
	}
	
    private class Node
    {
        Node leftChild = null;
        Node rightChild = null;
        WarehouseItem data = null;

        public Node(WarehouseItem inData)
        {
            data = inData;
        }

    }
}
