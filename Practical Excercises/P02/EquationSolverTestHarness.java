public class EquationSolverTestHarness
{
    public static void main( String[] args )
    {
        String[] equations = new String[]{ 
            "1 + 1", "1 - 2", "8 * 2", "2 / 1", "( 1 + 5 ) / 3", 
            "2 * 2 * 2 * 2", "( 10.3 * ( 14 + 3.2 ) ) / ( 5 + 2 - 4 * 3 )",
            "( ( 19 - 53 ) / 54 * 8 * 19 ) / 5.8" };
        String[] answers = new String[]{
            "2", "-1", "16", "2", "2", "16", "-35.432", "-16.5006385696" };
        double answer;
        EquationSolver solver = new EquationSolver();

        for( int i = 0; i < equations.length; i++ )
        {
            System.out.print( "Solving " + equations[i] + " " );
            answer = solver.solve( equations[i] );
            System.out.println( "= " + answer + " (Expeced: " + answers[i] 
                + ")" );
        }
    }
}
            
