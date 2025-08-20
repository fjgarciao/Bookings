package org.example.zigzag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  TreeNode(int x) {
    val = x;
  }
}

public class LevelOrder {

  public static List<List<Integer>> levelOrder(TreeNode root, boolean zigzag) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    boolean leftToRight = true;

    while(!queue.isEmpty()) {
      int size = queue.size();
      List<Integer> level = new ArrayList<>();

      for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        assert node != null;

        int val = node.val;
        if (leftToRight) {
          level.addLast(val);
        } else {
          level.addFirst(val);
        }

        if (node.left != null) queue.add(node.left);
        if (node.right != null) queue.add(node.right);
      }

      result.add(level);
      if (zigzag) leftToRight = !leftToRight;
    }

    return result;
  }
}
