import java.util.ArrayList;

public class RedBTree {
	// Program to implement Red-Black Tree.
	static class TNode {
		Bldg data; // data value.
		TNode left; // points to left child
		TNode right; // points to right child.
		TNode parent; // points to parent node.
		char color; // color of the node.
		
		//All the variables of TNode are initialized
		public TNode(Bldg data, char color) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.parent = null;
			this.color = color;
		}
	}
	

	static TNode root; // root of the tree.

	//Method to create node of RBT 
	public TNode makeTNode(Bldg data) {
		TNode treeNode = new TNode(data, 'R');
		treeNode.left = new TNode(null, 'B');
		treeNode.right = new TNode(null, 'B');
		return treeNode;
	}

	//Method to add node to RBT
	public boolean add(Bldg data) {
		TNode treeNode = makeTNode(data);
		if (root == null) {
			root = treeNode;
			root.color = 'B';
			return true;
		}

		TNode temp = root;
		while (temp != null) {
			if (temp.data.getBldgID() == data.getBldgID()) {
				return false;
			}
			if (temp.data.getBldgID() > data.getBldgID()) {
				if (temp.left.data == null) {
					temp.left = treeNode;
					treeNode.parent = temp;
					balance(treeNode); // balance the tree.
					return true;
				}
				temp = temp.left;
				continue;
			}
			if (temp.data.getBldgID() < data.getBldgID()) {
				if (temp.right.data == null) {
					temp.right = treeNode;
					treeNode.parent = temp;
					balance(treeNode); // balance the tree.
					return true;
				}
				temp = temp.right;
			}
		}
		return true;
	}

	//After deleting the node this method balances the RBT
	private static void balance(TNode treeNode, String color) {
		// if node is Red-Black.
		if (treeNode.parent == null || color.equals("BR") || color.equals("RB")) {
			treeNode.color = 'B';
			return;
		}

		TNode parent = treeNode.parent;

		// get sibling of the given node.
		TNode sibling = null;
		if (parent.left == treeNode) {
			sibling = parent.right;
		} else {
			sibling = parent.left;
		}

		TNode sibleft = sibling.left; // sibling's left node.
		TNode sibright = sibling.right; // siblings right node.

		if(sibleft==null && sibright==null) {
			return;
		}
		
		// sibling sibleft and sibright all are balck.
		if (sibling.color == 'B' && sibleft.color == 'B' && sibright.color == 'B') {
			sibling.color = 'R';
			treeNode.color = 'B';
			String c = Character.toString(parent.color) + Character.toString('B');
			balance(parent, c);
			return;
		}

		// sibling is red.
		if (sibling.color == 'R') {
			if (parent.right == sibling) {
				leftRotate(sibling);
			} else {
				rightRotate(sibling);
			}
			balance(treeNode, color);
			return;
		}

		// sibling is red but one of its children are red.
		if(sibleft==null) {
			return;
		}
		if (parent.left == sibling) {
			if (sibleft.color == 'R') {
				rightRotate(sibling);
				sibleft.color = 'B';
			} else {
				leftRotate(sibright);
				rightRotate(sibright);
				sibright.left.color = 'B';
			}
			return;
		} else {
			if (sibright.color == 'R') {
				leftRotate(sibling);
				sibright.color = 'B';
			} else {
				rightRotate(sibleft);
				leftRotate(sibleft);
				sibleft.right.color = 'B';
			}
			return;
		}
	}

	//This method removes one node from RBT
	public void remove(Bldg data) {
		if (root == null) {
			return;
		}

		// search for the given element in the tree.
		TNode temp = root;
		while (temp.data != null) {
			if (temp.data == data) {
				break;
			}
			if (temp.data.getBldgID() >= data.getBldgID()) {
				temp = temp.left;
				continue;
			}
			if (temp.data.getBldgID() < data.getBldgID()) {
				temp = temp.right;
				continue;
			}
		}

		// if not found. then return.
		if (temp.data == null) {
			return;
		}

		// find the next greater number than the given data.
		TNode next = findNext(temp);

		// swap the data values of given node and next greater number.
		Bldg t = temp.data;
		temp.data = next.data;
		next.data = t;

		// remove the next node.
		TNode parent = next.parent;
		if (parent == null) {
			if (next.left.data == null) {
				root = null;
			} else {
				root = next.left;
				next.parent = null;
				root.color = 'B';
			}
			return;
		}

		if (parent.right == next) {
			parent.right = next.left;
		} else {
			parent.left = next.left;
		}
		next.left.parent = parent;
		String color = Character.toString(next.left.color) + Character.toString(next.color);
		balance(next.left, color); // balance the tree.
	}
	
	// This is used to find the next greater element in the RBT Node
	private static TNode findNext(TNode treeNode) {
		TNode next = treeNode.right;
		if (next.data == null) {
			return treeNode;
		}
		while (next.left.data != null) {
			next = next.left;
		}
		return next;
	}

	// After inserting the node this method balances the RBT
	public static void balance(TNode treeNode) {
		// if given node is root node.
		if (treeNode.parent == null) {
			root = treeNode;
			root.color = 'B';
			return;
		}

		// if node's parent color is black.
		if (treeNode.parent.color == 'B') {
			return;
		}

		// get the node's parent's sibling node.
		TNode sibling = null;
		if (treeNode.parent.parent.left == treeNode.parent) {
			sibling = treeNode.parent.parent.right;
		} else {
			sibling = treeNode.parent.parent.left;
		}

		// if sibling color is red.
		if (sibling.color == 'R') {
			treeNode.parent.color = 'B';
			sibling.color = 'B';
			treeNode.parent.parent.color = 'R';
			balance(treeNode.parent.parent);
			return;
		}

		// if sibling color is black.
		else {
			if (treeNode.parent.left == treeNode && treeNode.parent.parent.left == treeNode.parent) {
				rightRotate(treeNode.parent);
				balance(treeNode.parent);
				return;
			}
			if (treeNode.parent.right == treeNode && treeNode.parent.parent.right == treeNode.parent) {
				leftRotate(treeNode.parent);
				balance(treeNode.parent);
				return;
			}
			if (treeNode.parent.right == treeNode && treeNode.parent.parent.left == treeNode.parent) {
				leftRotate(treeNode);
				rightRotate(treeNode);
				balance(treeNode);
				return;
			}
			if (treeNode.parent.left == treeNode && treeNode.parent.parent.right == treeNode.parent) {
				rightRotate(treeNode);
				leftRotate(treeNode);
				balance(treeNode);
				return;
			}
		}
	}

	// This method does left rotation
	private static void leftRotate(TNode treeNode) {
		TNode parent = treeNode.parent;
		TNode left = treeNode.left;
		treeNode.left = parent;
		parent.right = left;
		if (left != null) {
			left.parent = parent;
		}
		char c = parent.color;
		parent.color = treeNode.color;
		treeNode.color = c;
		TNode gp = parent.parent;
		parent.parent = treeNode;
		treeNode.parent = gp;

		if (gp == null) {
			root = treeNode;
			return;
		} else {
			if (gp.left == parent) {
				gp.left = treeNode;
			} else {
				gp.right = treeNode;
			}
		}
	}

	// This method does right rotation
	private static void rightRotate(TNode treeNode) {
		TNode parent = treeNode.parent;
		TNode right = treeNode.right;
		treeNode.right = parent;
		parent.left = right;
		if (right != null) {
			right.parent = parent;
		}
		char c = parent.color;
		parent.color = treeNode.color;
		treeNode.color = c;
		TNode gp = parent.parent;
		parent.parent = treeNode;
		treeNode.parent = gp;

		if (gp == null) {
			root = treeNode;
			return;
		} else {
			if (gp.left == parent) {
				gp.left = treeNode;
			} else {
				gp.right = treeNode;
			}
		}
	}

	// This method does pre-order transversal in a tree
	private static void preOrder(TNode treeNode) {
		if (treeNode.data == null) {
			return;
		}
		System.out.print(treeNode.data + "-" + treeNode.color + " ");
		preOrder(treeNode.left);
		preOrder(treeNode.right);
	}

	// Method To print the contents of the RBT
	public static void display() {
		if (root == null) {
			System.out.println("Empty Tree");
			return;
		}

		System.out.print("Tree's PreOrder Traversal : ");
		preOrder(root);
		System.out.println();
	}

	public static Bldg search(TNode treeNode, int buildingNumber) {
		if(treeNode == null || treeNode.data == null) {
			return null;
		}
		if(treeNode.data.getBldgID()==buildingNumber) {
			return treeNode.data;
		}else if(treeNode.data.getBldgID()<buildingNumber) {
			return search(treeNode.right, buildingNumber);
		}else if(treeNode.data.getBldgID()>buildingNumber) {
			return search(treeNode.left, buildingNumber);
		}
		return null;
	}
	
	//Method that searches and gets buildings from from startBldgID and endBldgID range
	public static ArrayList<Bldg> searchRange(ArrayList<Bldg> list, TNode treeNode, int startBldgID, int endBldgID){
		if(treeNode==null || treeNode.data == null) {
			return list;
		}
		if(isBetween(treeNode.data.getBldgID(), startBldgID, endBldgID)) {
			searchRange(list, treeNode.left, startBldgID, endBldgID);
			list.add(treeNode.data);
			searchRange(list, treeNode.right, startBldgID, endBldgID);
		}else if(treeNode.data.getBldgID() >= startBldgID) {
			searchRange(list, treeNode.left, startBldgID, endBldgID);
		}else if(treeNode.data.getBldgID() <= startBldgID) {
			searchRange(list, treeNode.right, startBldgID, endBldgID);
		}
		return list;
	}
	
	//This method returns true if the provided buildingNum is in between startBldgID and endBldgID
	private static boolean isBetween(int buildingNum, int startBldgID, int endBldgID) {
		if(buildingNum >= startBldgID && buildingNum<=endBldgID)
			return true;
		return false;
	}
}


