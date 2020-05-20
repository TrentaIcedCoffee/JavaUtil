package algo;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * RollingHash.
 */
class RollingHash {
  /**
   * Context, data class for rolling hash, contains hash and hashQueue.
   */
  private static class Context {

    public int hash;
    public Deque<Integer> hashQueue;

    public Context() {
      hash = 0;
      hashQueue = new ArrayDeque<>();
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if (!(obj instanceof Context)) {
        return false;
      }
      Context that = (Context) obj;
      return this.hash == that.hash && hashQueueEquals(that.hashQueue);
    }

    private boolean hashQueueEquals(Deque<Integer> thatQueue) {
      if (thatQueue != null && hashQueue.size() == thatQueue.size()) {
        Iterator<Integer> iteThis = hashQueue.iterator();
        Iterator<Integer> iteThat = thatQueue.iterator();
        for (int i = 0; i < hashQueue.size(); i += 1) {
          if (!iteThis.next().equals(iteThat.next())) {
            return false;
          }
        }
        return true;
      }
      return false;
    }

    public int size() {
      return hashQueue.size();
    }
  }

  private final int mod;
  private final BigInteger modBig;
  private final int charset;
  private final BigInteger charsetBig;
  private final Context context;

  public RollingHash(int mod, int charset) {
    this.mod = mod;
    this.modBig = BigInteger.valueOf(mod);
    this.charset = charset;
    this.charsetBig = BigInteger.valueOf(charset);
    this.context = new Context();
  }

  public void add(int toAdd) {
    context.hashQueue.addLast(toAdd);
    context.hash = (context.hash * charset + toAdd) % mod;
  }

  public void removeFirst() {
    // (hash - first * charset ^ (size - 1)) % mod
    if (context.hashQueue.isEmpty()) {
      throw new NoSuchElementException("hash Queue is empty");
    }
    int toRemove = charsetBig.modPow(BigInteger.valueOf(size() - 1), modBig)
        .multiply(BigInteger.valueOf(context.hashQueue.removeFirst())).mod(modBig).intValue();
    context.hash = (context.hash + mod - toRemove) % mod;
  }

  public boolean contextEqual(RollingHash that) {
    return this.context.equals(that.context);
  }

  public int size() {
    return context.size();
  }
}