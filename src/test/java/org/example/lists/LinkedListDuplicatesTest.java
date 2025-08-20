package org.example.lists;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedListDuplicatesTest {

  private static final Logger LOG = LoggerFactory.getLogger(LinkedListDuplicatesTest.class);

  @Test
  void deleteDuplicates_whenInputIsNull_returnNull() {
    assertThat(LinkedListDuplicates.deleteDuplicates(null)).isNull();
  }

  @Test
  void deleteDuplicates() {
    execute(createList(List.of(1)), "1");
    execute(createList(List.of(1, 1)), "1");
    execute(createList(List.of(1, 1, 1)), "1");
    execute(createList(List.of(1, 2, 3)), "1 -> 2 -> 3");
    execute(createList(List.of(1, 1, 2, 3, 3)), "1 -> 2 -> 3");
  }

  private static ListNode createList(List<Integer> values) {
    if (values == null || values.isEmpty()) {
      return null;
    }

    ListNode head = new ListNode(values.getFirst());
    ListNode current = head;

    for (int i = 1; i < values.size(); i++) {
      current.next = new ListNode(values.get(i));
      current = current.next;
    }

    return head;
  }

  private static void execute(ListNode head, String expected) {
    LOG.info("Before: {}", listToString(head));
    assertThat(listToString(LinkedListDuplicates.deleteDuplicates(head))).isEqualTo(expected);
    LOG.info("After: {}", listToString(head));
  }

  private static String listToString(ListNode head) {
    StringBuilder sb = new StringBuilder();
    ListNode current = head;
    while (current != null) {
      sb.append(current.val);
      if (current.next != null) sb.append(" -> ");
      current = current.next;
    }
    return sb.toString();
  }
}
