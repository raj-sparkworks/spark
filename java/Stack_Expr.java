import java.util.Stack;

public class Stack_Expr {
    public static boolean isExpression(String input)
    {
        Stack<Character> stack = new Stack<>();

        for(char c : input.toCharArray())
        {
            if(c == '(')
            {
                stack.push(c);
            }
            if(c == ')')
            {
                if(stack.empty())
                    return false;
                stack.pop();
            }
        }
        return stack.empty();
    }

    public static void main(String[] args) {
        String input = "(2+4>";
        System.out.println(isExpression(input));

    }
}
