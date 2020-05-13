public abstract class DSAQueue
{
    // Protected Classfields

    protected Object[] queue;
    protected int endIdx;

    // Class Constants

    private static final int DEFAULT_CAPACITY = 100;

    // Constructors

    public DSAQueue()
    {
        queue = new Object[ DEFAULT_CAPACITY ];
        endIdx = 0;
    }

    public DSAQueue( int capacity )
    {
        if( capacity <= 0 )
        {
            throw new IllegalArgumentException( 
                "Capacity must be greater than zero" );
        }
        else
        {
            queue = new Object[ capacity ];
            endIdx = 0;
        }
    }

    // Accessors

    public abstract int getCount();

    public abstract boolean isEmpty();

    public abstract boolean isFull();

    // Mutators

    public abstract void enqueue( Object inObject );

    public abstract Object dequeue();

    public abstract Object peek();
}
