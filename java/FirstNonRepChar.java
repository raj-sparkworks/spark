import java.util.*;

public class FirstNonRepChar {

    public static void main(String[] args) {
        FirstNonRepChar first = new FirstNonRepChar();
        System.out.println(first.getRepChar("ab green apple"));

    }

    public char getRepChar(String s)
    {
        Set<Character> set = new HashSet<>();
        for(Character ch : s.toCharArray())
        {
            if(set.contains(ch))
                    return ch;
            //else
                set.add(ch);
            //set.contains(ch) ? return ch : set.add(ch);
        }
        return Character.MIN_VALUE;
    }

    public char getNonRepChar(String s)
    {
        Map<Character,Integer>  map = new HashMap<>();
int count;
        for(char c : s.toCharArray())
        {
            /*if(map.containsKey(c))
            {
                map.put(c,map.get(c)+1);
            }
            else
                map.put(c,1);*/
            count= map.containsKey(c) ? map.get(c)+1 : 1;
            map.put(c,count);
        }

        for(char c : s.toCharArray())
        {
           if(map.get(c) == 1)
               return c;
        }

    return 0;

    }
}
