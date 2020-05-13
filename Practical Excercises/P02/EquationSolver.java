public class EquationSolver
{
    /*  
     *   Submodule: solve()
     *      Import: equation (string)
     *      Export: answer (real)
     *   Assertion: Should call parseInfixToPostfix() then evaluatePostfix()
     */
    public double solve( String equation )
    {
        DSACircularQueue postfixQueue;
        double answer;

        postfixQueue = parseInfixToPostfix( equation );
        answer = evaluatePostfix( postfixQueue );

        return answer;
    }
    
    /*  
     *   Submodule: parseInfixToPostfix()
     *      Import: equation (string)
     *      Export: postfixQueue (DSACircularQueue)
     *   Assertion: Convert infix-form equation to postfixx. Store the postfix
     *                  terms in a queue of Objects; use Doubles for operands
     *                  and Characters for operators, but put them all in the
     *                  queue in postfix order
     */ 
    private DSACircularQueue parseInfixToPostfix( String equation )
    {
        //DSAStack postfixQueue;
        DSACircularQueue postfixQueue;
        String[] infixArray; //Array of equation terms in infix
        //int idx = 0; //Index of infixArray to next read
        String term;
        DSAStack operatorStack;
        String operators = "+-*/";

        infixArray = equation.split(" ");
        //postfixQueue = new DSAStack( infixArray.length );
        operatorStack = new DSAStack( infixArray.length );
        postfixQueue = new DSACircularQueue( infixArray.length );

        for( int i = 0; i < infixArray.length; i++ )
        {
            term = infixArray[i];

            if( term.equals( "(" ) )
            {
                operatorStack.push( term.charAt(0) );
            }
            else if( term.equals( ")" ) )
            {
                while( !operatorStack.top().equals( '(' ) )
                {
                    postfixQueue.enqueue( operatorStack.pop() );
                }
                operatorStack.pop(); //Pop the '(' and discard it
            }
            else if( operators.contains( term ) ) //If term is +,-,* or /
            {
                while( !operatorStack.isEmpty() && 
                    !operatorStack.top().equals( '(' ) && 
                    ( precedenceOf( operatorStack.top().toString().charAt(0) ) 
                    >= precedenceOf( term.charAt(0) ) ) )
                {
                    postfixQueue.enqueue( operatorStack.pop() );
                }
                operatorStack.push( term );
                    //Puts the new operator onto the stack
            }
            else
            {
                //Term is an operand
                postfixQueue.enqueue( Double.valueOf( term ) );
            }
        }

        while( !operatorStack.isEmpty() )
        {
            //Push any remaining operands
            postfixQueue.enqueue( operatorStack.pop() );
        }

        /*
        while( !postfixQueue.isEmpty() )
        {
            //Put postfixQueue into a queue
            postfixQueue.enqueue( postfixQueue.pop() );
        }
        */

        return postfixQueue;
    }

    /*
     *   Submodule: evaluatePostfix()
     *      Import: postfixQueue (DSACircularQueue)
     *      Export: answer (real)
     *   Assertion: Take the postfixQueue and evaluate it. Use instanceof to
     *                  to determine if a term is an operand (Double) or an
     *                  operator (Character)
     */
    private double evaluatePostfix( DSACircularQueue postfixQueue )
    {
        DSAStack operandStack;
        char op;
        double op1, op2;

        operandStack = new DSAStack( postfixQueue.getCount() );
        while( !postfixQueue.isEmpty() )
        {
            if( postfixQueue.peek() instanceof Double )
            {
                //Next item is an operand
                operandStack.push( postfixQueue.dequeue() );
            }
            else
            {
                //Next item is an operator
                op2 = (double)operandStack.pop();
                op1 = (double)operandStack.pop();
                op = postfixQueue.dequeue().toString().charAt(0);
                operandStack.push( executeOperation( op, op1, op2 ) );
            }
        }

        return (double)operandStack.pop();
    }

    /*  
     *   Submodule: precedenceOf()
     *      Import: theOp (char)
     *      Export: precedence (int)
     *   Assertion: Helper function for parseInfixToPostfix(). Returns the
     *                  precedence (as an integer) of theOperator (ie:
     *                  +,- return 1 and *,/ return 2)
     */
    private int precedenceOf( char theOp )
    {
        int precedence;

        if( theOp == '+' || theOp == '-' )
        {
            precedence = 1;
        }
        else if( theOp == '*' || theOp == '/' )
        {
            precedence = 2;
        }
        else
        {
            throw new IllegalArgumentException( 
                "Postfix equation contains operator " + theOp );
        }

        return precedence;
    }

    /*  
     *   Submodule: executeOperation()
     *      Import: op (char), op1 (real), op2 (real)
     *      Export: answer (real)
     *   Assertion: Helper function for evaluatePostfix(). Executes the binary
     *                  operation implied by op, either op1 + op2, op1 - op2,
     *                  op1 * op2 or op1 / op2, and returns the result.
     */
    private double executeOperation( char op, double op1, double op2 )
    {
        double answer;

        switch( op )
        {
            case '+':
            {
                answer = op1 + op2;
                break;
            }
            case '-':
            {
                answer = op1 - op2;
                break;
            }
            case '*':
            {
                answer = op1 * op2;
                break;
            }
            case '/':
            {
                answer = op1 / op2;
                break;
            }
            default:
            {
                throw new IllegalArgumentException(
                    "Postfix equation contains operator " + op );
            }
        }

        return answer;
    }
}
