package org.example.zigzag;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelOrderTest {

  @Test
  void levelOrder_whenNull_returnEmtpyList() {
    assertThat(LevelOrder.levelOrder(null, true)).isEqualTo(List.of());
    assertThat(LevelOrder.levelOrder(null, false)).isEqualTo(List.of());
  }

  @Test
  void levelOrder() {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
    root.right.right = new TreeNode(6);

    assertThat(LevelOrder.levelOrder(root, false)).containsExactly(
      List.of(1), List.of(2, 3), List.of(4, 5, 6)
    );

    assertThat(LevelOrder.levelOrder(root, true)).containsExactly(
      List.of(1), List.of(3, 2), List.of(4, 5, 6)
    );
  }
}
