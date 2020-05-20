package algo;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PatternMatchTest {
  static String s0, p0, s1, p1, s2, p2, s3, p3;
  static int r0, r1, r2, r3;
  static List<Integer> r0All, r1All, r2All, r3All;

  @BeforeClass
  public static void initTests() {
    s0 = ""; p0 = ""; r0 = 0; r0All = Collections.singletonList(0);
    s1 = "a"; p1 = ""; r1 = 0; r1All = Arrays.asList(0, 1);
    s2 = "abcabcabc"; p2 = "aa"; r2 = -1; r2All = Collections.emptyList();
    s3 = "ababcabcab"; p3 = "abcab"; r3 = 2; r3All = Arrays.asList(2, 5);
  }

  @Test
  public void rabinKarp() {
    assertEquals(r0, PatternMatch.rabinKarp(s0, p0));
    assertEquals(r1, PatternMatch.rabinKarp(s1, p1));
    assertEquals(r2, PatternMatch.rabinKarp(s2, p2));
    assertEquals(r3, PatternMatch.rabinKarp(s3, p3));
  }

  @Test
  public void rabinKarpAll() {
    assertEquals(r0All, PatternMatch.rabinKarpAll(s0, p0));
    assertEquals(r1All, PatternMatch.rabinKarpAll(s1, p1));
    assertEquals(r2All, PatternMatch.rabinKarpAll(s2, p2));
    assertEquals(r3All, PatternMatch.rabinKarpAll(s3, p3));
  }

  @Test
  public void kmp() {
    assertEquals(r0, PatternMatch.kmp(s0, p0));
    assertEquals(r1, PatternMatch.kmp(s1, p1));
    assertEquals(r2, PatternMatch.kmp(s2, p2));
    assertEquals(r3, PatternMatch.kmp(s3, p3));
  }

  @Test
  public void kmpAll() {
    assertEquals(r0All, PatternMatch.kmpAll(s0, p0));
    assertEquals(r1All, PatternMatch.kmpAll(s1, p1));
    assertEquals(r2All, PatternMatch.kmpAll(s2, p2));
    assertEquals(r3All, PatternMatch.kmpAll(s3, p3));
  }

  @Test
  public void boyerMoore() {
    assertEquals(r0, PatternMatch.boyerMoore(s0, p0));
    assertEquals(r1, PatternMatch.boyerMoore(s1, p1));
    assertEquals(r2, PatternMatch.boyerMoore(s2, p2));
    assertEquals(r3, PatternMatch.boyerMoore(s3, p3));
  }

  @Test
  public void boyerMooreAll() {
    assertEquals(r0All, PatternMatch.boyerMooreAll(s0, p0));
    assertEquals(r1All, PatternMatch.boyerMooreAll(s1, p1));
    assertEquals(r2All, PatternMatch.boyerMooreAll(s2, p2));
    assertEquals(r3All, PatternMatch.boyerMooreAll(s3, p3));
  }
}