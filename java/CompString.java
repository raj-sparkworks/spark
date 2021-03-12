public class CompString {
    public static String compressString(String text) {

        int[] alphabets = new int[26];

        for(int i=0;i<text.length()-1;i++) {
            alphabets[text.charAt(i) - 'a'] = alphabets[text.charAt(i) - 'a']+1;
        }

        for(int i=0;i<text.length();i++) {
            System.out.print(alphabets[i]);
            System.out.println((char) (i + 'a'));
        }
        System.out.println("==================");

        StringBuilder output = new StringBuilder();

        for(int i=0;i<alphabets.length-1;i++) {
            if(alphabets[i] > 1) {
                output.append((char)i)
                        .append(i);
            }
        }

        return output.toString();

    }

    public static void main(String[] args) {

        System.out.println(compressString("aaacceiii"));
    }
}
