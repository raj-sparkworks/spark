import java.util.Stack;

public class Stack_Revr_String {
    public static void main(String[] args) {
        String name = "Raj";
        //char[] name_arr = name.toCharArray();
        Stack<Character> stack = new Stack<>();
        for(char c : name.toCharArray())
            stack.push(c);

        StringBuffer buffer = new StringBuffer();
        while (!stack.empty())
        {
            buffer.append(stack.pop());
        }

        System.out.println("Value "+buffer.toString());
        //System.out.println("Value : "+stack.toString());
        //for(int i=0;i<stack.size();i++)
          //  System.out.println(stack.peek());

    }
}
