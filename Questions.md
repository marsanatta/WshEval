### Question3
#### Features
1. Configure max size of the cache to safeguard memory usage
    - LRU/LRU eviction
2. Support expiration
3. Support configure cache loader as the entry loader when cache miss
4. Support refresh entry
6. Thread-safe

```java
Cache<Key, MyData> cache = CacheBuilder.newBuilder() // use builder to set up default config
       .maximumSize(1000)
       .expireAfterWrite(10, TimeUnit.MINUTES) // alternatively we can have expireAfterAccess
       .refreshAfterWrite(1,TimeUnit.MINUTES)
       .loader(new CacheLoader<Key, MyData>() {
            @Override
            public MyData load(Key key) throws Exception {
                    return readFromDataSource(key);
            }
       })
       .build();

cache.set(key, val); // set with default expiration
cache.setWithExpire(key, val, 10, TimeUnit.MINUTES); // set with customized expiration
cache.get(key); // get
cache.getWithLoad(key, new CacheLoader<Key, MyData>() { // get. If cache-miss using loader to read data and insert it to cache
    @Override
    public MyData load(Key key) throws Exception {
                return readFromDataSource(key);
    }
});
cache.invalidate(key);  // invalidate specific entry
cache.invalidateAll(); // invalidate all entries
cache.refresh(key); // refresh a specific key to reset its refresh period
cache.clean(); // explicitly remove all expired entries
```
#### How to implement expiration?
1. Way1: lazy expiration
    - When get the key, check if the entry is expired, if expired, delete the entry and return cache miss
    - Cons:
        - The memory could have expired entries to waste memory
        - The size of the cache then it's not precise. Impacting max size and LRU/FRU function
2. Way2: periodical scan
    - Have a scheduled thread with ScheduledExecutorService to call `cache.clean()` which scan entries to
      delete expired one
    - Cons
        - Have to scan all the entries. O(N)
    - Note
        - Need to combine with lazy expiration still, or might return entry which should expired already
2. Way3: probabilistic expiration
    - Sample a few entries not whole, if certain percentage of entries is expired. continue to do the next sampling and delete.
      Until below the threshold percentage
    - Cons:
        - Better memory utilization compare to Way1, but still worse than Way2
        - Same size problem as Way1
    - Note
        - Need to combine with lazy expiration still, or might return entry which should expired already
3. Way4: set up scheduler Thread for each entry set with expiration to do clean up for individual entry
    - Cons:
        - Might not be scalable. Requires threads proportional to the entries set. which might consume up the instance resource

#### How to implement auto refresh?
When an entry is requested from the cache and it has reached the specified refresh duration since
its last write or refresh, the following steps occur:
1. The cached value is returned to the caller first
2. A refresh task is scheduled asynchronously to load the new value for the entry.
3. The next time the entry is requested, if the refresh task for that entry has completed, the new value is returned.
4. If the refresh task is still in progress, the old value is returned while the refresh task completes.
5. If refresh fails, it retries, the cache will continue to return the old value until a successful refresh occurs. or when all attempts fail, set up the refresh period again and wait for next time

#### How to reduce the database call from the loader?
1. Have a ConcurrentHashMap<Key, CacheValue>;
2. The CacheValue contains the Value, a Future handled by a loader, and other metadata (e.g. startTime)
3. When getWithLoad you can check, whether there's any loader loading the data right now, and prevent duplicate load.

#### How to implement LRU?

Double Linked List + Hash Map

```java
// Just for elaborating LRU algo, not real cache code
private class LinkNode {
    public LinkNode prev;
    public LinkNode next;
    public int key;
    public int val;

    public LinkNode(int key, int val) {
        this.key = key;
        this.val = val;
    }

    private LinkNode head = new LinkNode(0, 0);
    private LinkNode tail = new LinkNode(0, 0);
    private ConcurrentMap<Integer, LinkNode> nodes = new ConcurrentHashMap<>();
    private int size;

    public LRUCache(int capacity) {
        this.size = capacity;
        head.next = tail;
        tail.prev = head;
    }

    private LinkNode removeNode(LinkNode n) {
        LinkNode prev = n.prev;
        LinkNode next = n.next;
        prev.next = next;
        next.prev = prev;
        return n;
    }

    private void addHead(LinkNode n) {
        LinkNode next = head.next;
        head.next = n;
        n.prev = head;
        n.next = next;
        next.prev = n;
    }

    private void updateNode(LinkNode n) {
        removeNode(n);
        addHead(n);
    }

    public int get(int key) {
        if (!nodes.containsKey(key)) {
            return -1;
        }
        LinkNode n = nodes.get(key);
        updateNode(n);
        return n.val;
    }

    public void put(int key, int val) {
        if (this.size == 0)
            return;
        if (nodes.containsKey(key)) {
            LinkNode n = nodes.get(key);
            updateNode(n);
            n.val = val;
            return;
        }
        LinkNode newNode = new LinkNode(key, val);
        if (nodes.size() + 1 > this.size) {
            //evit
            LinkNode n = removeNode(tail.prev);
            nodes.remove(n.key);
        }
        nodes.put(key, newNode);
        addHead(newNode);
    }
}
```

#### How to implement LFU?

Frequency Bucket

```java
// Just for elaborating LFU algo, not real cache code
class LFUCache {
    public class ListNode {
        public int val;
        public int key;
        public int freq = 1;
        public ListNode (int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    private int minFreq = 1;
    ConcurrentHashMap<Integer, LinkedHashSet<Integer>> freqs = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, ListNode> nodes = new ConcurrentHashMap<>();
    private int cap;
    public LFUCache(int cap) {
        this.cap = cap;
    }
    
    private void addNodeToFreq(ListNode n) {
        freqs.putIfAbsent(n.freq, Collections.synchronizedSet(new LinkedHashSet<>())); // linked hash set is not thread safe
        freqs.get(n.freq).add(n.key);
    }
    
    private void updateNode(ListNode n) {
        LinkedHashSet<Integer> bucket = freqs.get(n.freq);
        bucket.remove(n.key);
        if (n.freq == minFreq && bucket.isEmpty())
            minFreq++;
        n.freq++;
        addNodeToFreq(n);
    }
    
    public int get(int key) {
        if (!nodes.containsKey(key))
            return -1;
        ListNode n = nodes.get(key);
        updateNode(n);
        return n.val;
    }
    
    public void put(int key, int val) {
        if (cap <= 0)
            return;
        if (nodes.containsKey(key)) {
            ListNode n = nodes.get(key);
            updateNode(n);
            n.val = val;
            return;
        }
        if (nodes.size() == cap) {
            LinkedHashSet<Integer> minBucket = freqs.get(minFreq);
            //evict
            int delKey = minBucket.iterator().next();
            minBucket.remove(delKey);
            nodes.remove(delKey);
        }
        ListNode n = new ListNode(key, val);
        addNodeToFreq(n);
        nodes.put(key, n);
        minFreq = 1;
    }
}
```

### Question4

![](https://i.imgur.com/NAKpkS3.png)

![](https://i.imgur.com/DAfrO0R.png)
