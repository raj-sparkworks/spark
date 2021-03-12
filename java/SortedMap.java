import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SortedMap {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(111,454);
        map.put(2,565);
        map.put(999,333);

        for(Integer a : map.keySet())
        {
            System.out.println("Key : "+a);
        }

        Map<Integer,Integer> tMap = new TreeMap<>(map);
        for(Integer a : tMap.keySet())
        {
            System.out.println("Key : "+a);
        }

    }
}
