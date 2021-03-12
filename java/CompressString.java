import java.util.Iterator;
import java.util.Map;

public class CompressString {
    public static void main(String[] args) {
        String text = "aaabbbbccccc";
        char[] arr = text.toLowerCase().toCharArray();
        int[] alphabets = new int[26];
        int index=0;
        //char c;
        char x;
        StringBuilder compressStr = new StringBuilder();

for(char c : arr ) {
        //for(int i=0;i<text.length();i++) {
            //c=text.indexOf(i);
            index= c - 97;
            //System.out.println(index);
            //if(!alphabets[index] == null)
                alphabets[index] =  alphabets[index]+1 ;
        }

        for(int i =0;i<alphabets.length;i++) {
            x=alphabets[i];
            if(alphabets[index] > 1)
                compressStr.append(c+""+alphabets[index]);
            else
                compressStr.append(c);

        }

        for(int i =0;i<alphabets.length;i++)
        {
            System.out.println(alphabets[i]);
        }

        System.out.println(compressStr.toString());
    }


    public static String compressString(String text) {

        Map<Character, Integer> map = new HashMap<>();

        for (Character c : text.toLowerCase().toCharArray()) {
            if (map.containsKey(c))
                map.replace(c,map.get(c)+1)
                //map.put(c,map.get(c) + 1);
            else
                map.put(c,1);
        }

        Iterator itr = map.entrySet().iterator();
        StringBuilder builder = new StringBuilder();

        while (itr.hasNext()) {
            builder.append(itr. + itr.getValue());
        }

        return builder.toString();
    }
}
