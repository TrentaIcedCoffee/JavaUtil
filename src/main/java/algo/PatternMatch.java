package algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PatternMatch {

  /**
   * RobinKarp rolling hash.
   * @param s the string to be searched on
   * @param p the pattern string
   * @return index of first match or -1 if not found
   */
  public static int rabinKarp(String s, String p) {
    if (p.length() > s.length()) {
      return -1;
    } else if (p.length() == 0) {
      return 0;
    }
    // 0 < p.length() <= s.length()
    RollingHash pHash = new RollingHash(101, (int) Math.pow(2, 16));
    for (char c: p.toCharArray()) {
      pHash.add(c);
    }
    RollingHash sHash = new RollingHash(101, (int) Math.pow(2, 16));
    for (int endIdx = 0; endIdx < s.length(); endIdx += 1) {
      sHash.add(s.charAt(endIdx));
      if (sHash.size() > pHash.size()) {
        sHash.removeFirst();
      }
      if (sHash.contextEqual(pHash)) {
        return endIdx - pHash.size() + 1;
      }
    }

    return -1;
  }

  /**
   * Robin Karp rolling hash find all occurrences.
   * @param s the string to be searched on
   * @param p the pattern string
   * @return list of all occurrences
   */
  public static List<Integer> rabinKarpAll(String s, String p) {
    if (p.length() > s.length()) {
      return Collections.emptyList();
    } else if (p.length() == 0) {
      return IntStream.range(0, s.length() + 1).boxed().collect(Collectors.toList());
    }
    // 0 < p.length() <= s.length()
    List<Integer> res = new ArrayList<>();

    RollingHash pHash = new RollingHash(101, (int) Math.pow(2, 16));
    for (char c: p.toCharArray()) {
      pHash.add(c);
    }

    RollingHash sHash = new RollingHash(101, (int) Math.pow(2, 16));
    for (int endIdx = 0; endIdx < s.length(); endIdx += 1) {
      sHash.add(s.charAt(endIdx));
      if (sHash.size() > pHash.size()) {
        sHash.removeFirst();
      }
      if (sHash.contextEqual(pHash)) {
        res.add(endIdx - pHash.size() + 1);
      }
    }

    return res;
  }

  /**
   * Knuth Morris Pratt.
   * @param s the string to be searched on
   * @param p the pattern string
   * @return index of first match or -1 if not found
   */
  public static int kmp(String s, String p) {
    if (p.length() > s.length()) {
      return -1;
    } else if (p.length() == 0) {
      return 0;
    }
    // 0 < p.length() <= s.length()
    int[] shiftTable = new int[p.length()];
    // shiftTable[idx] max len of prefix that is also a suffix
    for (int i = 1; i < p.length(); i += 1) {
      int prev = shiftTable[i - 1];
      // find prefix s.t. prefix's next char == p.charAt(i)
      while (0 < prev && p.charAt(prev) != p.charAt(i)) {
        prev = shiftTable[prev - 1];
      }
      // prev: prev length
      if (prev == 0) {
        shiftTable[i] = p.charAt(i) == p.charAt(0) ? 1 : 0;
      } else {
        shiftTable[i] = prev + 1;
      }
    }

    for (int i = 0, j = 0; i < s.length(); i += 1) {
      while (0 < j && s.charAt(i) != p.charAt(j)) {
        j = shiftTable[j - 1];
      }
      j += s.charAt(i) == p.charAt(j) ? 1 : 0;
      if (j == p.length()) {
        return i - p.length() + 1;
      }
    }

    return -1;
  }

  /**
   * Knuth Morris Pratt find all occurrences.
   * @param s the string to be searched on
   * @param p the pattern string
   * @return list of all occurrences
   */
  public static List<Integer> kmpAll(String s, String p) {
    if (p.length() > s.length()) {
      return Collections.emptyList();
    } else if (p.length() == 0) {
      return IntStream.range(0, s.length() + 1).boxed().collect(Collectors.toList());
    }
    // 0 < p.length() <= s.length()
    List<Integer> res = new ArrayList<>();

    int[] shiftTable = new int[p.length()];
    for (int i = 1; i < p.length(); i += 1) {
      int prev = shiftTable[i - 1];
      while (0 < prev && p.charAt(prev) != p.charAt(i)) {
        prev = shiftTable[prev - 1];
      }
      if (prev == 0) {
        shiftTable[i] = p.charAt(i) == p.charAt(0) ? 1 : 0;
      } else {
        shiftTable[i] = prev + 1;
      }
    }

    for (int i = 0, j = 0; i < s.length(); i += 1) {
      while (0 < j && s.charAt(i) != p.charAt(j)) {
        j = shiftTable[j - 1];
      }
      j += s.charAt(i) == p.charAt(j) ? 1 : 0;
      if (j == p.length()) {
        res.add(i - p.length() + 1);
        j = shiftTable[j - 1];
      }
    }

    return res;
  }

  /**
   * Boyer Moore.
   * @param s the string to be searched on
   * @param p the pattern string
   * @return index of first match or -1 if not found
   */
  public static int boyerMoore(String s, String p) {
    if (p.length() > s.length()) {
      return -1;
    } else if (p.length() == 0) {
      return 0;
    }
    // 0 < p.length() <= s.length()
    Map<Character, Integer> shiftTable = new HashMap<>();
    for (int i = 0; i < p.length(); i += 1) {
      shiftTable.put(p.charAt(i), Math.max(1, p.length() - i - 1));
    }

    int end = p.length() - 1;
    while (end < s.length()) {
      int i = end;
      int j = p.length() - 1;
      while (0 <= j && s.charAt(i) == p.charAt(j)) {
        i -= 1;
        j -= 1;
      }
      if (j == -1) {
        return i + 1;
      }
      end += shiftTable.getOrDefault(p.charAt(j), p.length());
    }

    return -1;
  }

  /**
   * Boyer Moore find all occurrences.
   * @param s the string to be searched on
   * @param p the pattern string
   * @return list of all occurrences
   */
  public static List<Integer> boyerMooreAll(String s, String p) {
    if (p.length() > s.length()) {
      return Collections.emptyList();
    } else if (p.length() == 0) {
      return IntStream.range(0, s.length() + 1).boxed().collect(Collectors.toList());
    }
    // 0 < p.length() <= s.length()
    List<Integer> res = new ArrayList<>();
    Map<Character, Integer> shiftTable = new HashMap<>();
    for (int i = 0; i < p.length(); i += 1) {
      shiftTable.put(p.charAt(i), Math.max(1, p.length() - i - 1));
    }

    int end = p.length() - 1;
    while (end < s.length()) {
      int i = end;
      int j = p.length() - 1;
      while (0 <= j && s.charAt(i) == p.charAt(j)) {
        i -= 1;
        j -= 1;
      }
      if (j == -1) {
        res.add(i + 1);
        end += 1;
        continue;
      }
      end += shiftTable.getOrDefault(p.charAt(j), p.length());
    }

    return res;
  }

}

