public class HashTable<K, V> {

    static class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashNode<K, V>[] chainTable;
    private int capacity;

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.capacity = 10;
        this.chainTable = new HashNode[capacity];
    }

    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        int index = hashCode % capacity;
        if (index < 0) {
            index += capacity;
        }
        return index;
    }

    public void insert(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = chainTable[bucketIndex];
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        HashNode<K, V> newNode = new HashNode<>(key, value);
        head = chainTable[bucketIndex];
        if (head == null) {
            chainTable[bucketIndex] = newNode;
        } else {
            while (head.next != null) {
                head = head.next;
            }
            head.next = newNode;
        }
    }

    public String search(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = chainTable[bucketIndex];

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value.toString();
            }
            head = head.next;
        }
        return "Tidak ditemukan";
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = chainTable[bucketIndex];
        HashNode<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                break;
            }
            prev = head;
            head = head.next;
        }

        if (head == null) {
            System.out.println("Kunci '" + key + "' tidak ditemukan untuk dihapus.");
            return;
        }

        if (prev != null) {
            prev.next = head.next;
        } else {
            chainTable[bucketIndex] = head.next;
        }
        System.out.println("Kunci '" + key + "' berhasil dihapus.");
    }

    public void display() {
        for (int i = 0; i < capacity; i++) {
            System.out.print("Indeks " + i + ": ");
            HashNode<K, V> head = chainTable[i];
            if (head == null) {
                System.out.println("-");
            } else {
                while (head != null) {
                    System.out.print("[" + head.key + ": " + head.value + "]");
                    if (head.next != null) {
                        System.out.print(" -> ");
                    }
                    head = head.next;
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        HashTable<Integer, String> ht = new HashTable<>();
        System.out.println("=== TUGAS STRUKTUR DATA: HASH TABLE SEPARATE CHAINING ===");
        
        System.out.println("\n--- Melakukan Operasi Insert ---");
        ht.insert(43, "Data A");
        ht.insert(22, "Data B");
        ht.insert(1, "Data C");
        ht.insert(12, "Data D");
        ht.insert(34, "Data E");
        ht.insert(56, "Data F");
        ht.insert(77, "Data G");
        ht.insert(88, "Data H");
        ht.display();

        System.out.println("\n--- Melakukan Operasi Search ---");
        System.out.println("Cari kunci 12: " + ht.search(12));
        System.out.println("Cari kunci 34: " + ht.search(34));
        System.out.println("Cari kunci 99: " + ht.search(87));

        System.out.println("\n--- Melakukan Operasi Remove ---");
        ht.remove(89);
        ht.remove(12);
        ht.display();

        System.out.println("\n--- Search Setelah Remove ---");
        System.out.println("Cari kunci 34: " + ht.search(34));
    }
}