import java.util.LinkedList;

public class HashMap {
    private class Entry {
        private int key;
        private String value;

        public Entry(int key,String value) {
            this.key=key;
            this.value=value;
        }
    }
    LinkedList<Entry>[] entries = new LinkedList[5];

    public void put(int key,String value)
    {
        int index = hash(key);
        if(entries[index] == null)
        entries[index] = new LinkedList<>();

        //LinkedList<Entry> bucket = entries[index];
        for(Entry entry : entries[index])
        {
            if(key == entry.key)
            {
                entry.value=value;
                return;
            }
        }

        Entry entry = new Entry(key,value);
        entries[index].addLast(entry);

        //System.out.println("Done !!");

    }

    public String get(int key)
    {
        int index = hash(key);
        for(Entry entry : entries[index])
        {
            if(entry.key == key)
                return entry.value;
        }

        return null;
    }

    public void remove(int key)
    {
        int index = hash(key);

        if(entries[index] !=null)
        for(Entry entry : entries[index])
        {
            if(entry.key == key) {
                entries[index].remove(entry);
                return;
            }
        }
    }

    public void printList()
    {
        Entry entry;
        LinkedList<Entry> list;
        for(int i=0;i<entries.length;i++)
        {
            if(entries[i]!=null) {
                System.out.println("Into the loop...");
                list = entries[i];
                System.out.println(list.getLast().toString());
            }

        }
    }

    private int hash(int key) {
        int hash = key % entries.length;
        //System.out.println("Hash : "+hash);
        return hash;
    }
}
