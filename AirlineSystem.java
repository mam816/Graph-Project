import java.util.Set;
import java.util.ArrayList;
import java.io.*;
import java.util.*;


final public class AirlineSystem implements AirlineInterface {

 private ArrayList<String>cityNames = new ArrayList<String>();
 // private Set<Route> routes = new HashSet<Route>();
 private Digraph G;
 private final static int INFINITY = Integer.MAX_VALUE;
 

  public boolean loadRoutes(String fileName) {
 
 try
    { 
      File myFile = new File(fileName);
      Scanner myScan = new Scanner(myFile);
      while(myScan.hasNextLine())
           {
             int vertices = Integer.parseInt(myScan.next());
             G = new Digraph(vertices);
             cityNames = new ArrayList<String>(vertices);
             for(int x = 0; x < vertices; x++)
                {   
                  String line = myScan.nextLine();
               
                  if(line.equals(""))
                    {                    
                      line = myScan.nextLine();
                    }
                  cityNames.add(line);
                 
                } 
           while(myScan.hasNextLine())
                {
                  String start = cityNames.get(myScan.nextInt() - 1);
                  String finish = cityNames.get(myScan.nextInt() - 1);
                  int distance = myScan.nextInt();
                  double price = myScan.nextDouble();
                  Route from = new Route(start, finish, distance, price);
                  Route to = new Route(finish, start, distance, price);
                  G.addEdge(from);
                  G.addEdge(to);
                 // Route r = new Route(myScan.next(), myScan.next(), myScan.nextInt(), myScan.nextDouble());              
                }
           }
      myScan.close();
     }
  catch(FileNotFoundException e)
     {
       //System.out.println("This file can not be found. Please try again.");
       return false;
     }  
        
  return true;
  }

  public Set<String> retrieveCityNames() {
  
  Set<String> retrieve = new HashSet<String>();
  for(int i = 0; i < cityNames.size(); i++)
     { 
       String item = cityNames.get(i);
       retrieve.add(item);
     }
      
    return retrieve;
   
  }

  public Set<Route> retrieveDirectRoutesFrom(String city)
    throws CityNotFoundException {
//try catch   
    if(!cityNames.contains(city))
      {
        throw new CityNotFoundException("");
      }
    Set<Route> retrieve = new HashSet<Route>();
    for(LinkedList<Route> route : G.adj)
       {
        for(Route r : route)
           {
            if(r.source.equals(city))
              {
               retrieve.add(r);
              }
           }
        }       
    return retrieve;
}

  public Set<ArrayList<String>> fewestStopsItinerary(String source,
    String destination) throws CityNotFoundException {
    
    Set<ArrayList<String>> result = new HashSet<ArrayList<String>>();
      ArrayList<String> list = new ArrayList<String>();
   if(!cityNames.contains(source))
     {
       throw new CityNotFoundException("");
     }
    if(!cityNames.contains(destination))
      {
        throw new CityNotFoundException("");
      }
      
    int first = cityNames.indexOf(source);
    int sec = cityNames.indexOf(destination);
    G.bfs(first);
    Stack<Integer> stack = new Stack();
    for(int x = sec; x != first; x = G.edgeTo[x])
        {
            stack.push(x); 
        }
        
        stack.push(first);

        while(!stack.empty())
        {         
           list.add(cityNames.get(stack.pop()));
           result.add(list);            
        }

    return result;
  }

  public Set<ArrayList<Route>> shortestDistanceItinerary(String source,
    String destination) throws CityNotFoundException {
    
    Set<ArrayList<Route>> result = new HashSet<ArrayList<Route>>();
    if(!cityNames.contains(source))
     {
       throw new CityNotFoundException("");
     }
    if(!cityNames.contains(destination))
      {
        throw new CityNotFoundException("");
      }

    int first = cityNames.indexOf(source);
    int sec = cityNames.indexOf(destination);
    G.dijkstras(first, sec);   
    Stack<Integer> stack = new Stack<>();
    
    for (int x = sec; x != first; x = G.edgeTo[x])
          {
              stack.push(x);
          }
          
          int prevVertex = first;
          while(!stack.empty())
               {
                int v = stack.pop();
                //Route newRoute  = new Route(source, destination, prevVertex, v);
                prevVertex = v;
               }
   
    return result;
  }

  public Set<ArrayList<Route>> shortestDistanceItinerary(String source,
    String transit, String destination) throws CityNotFoundException {
    
    Set<ArrayList<Route>> result = new HashSet<ArrayList<Route>>();
    
    return result;
   // return null;
  } 

  public boolean addCity(String city){
  if(cityNames.size() == 0)
    {
      cityNames.add(city);
      return true;
    }
  
  for(int i = 0; i < cityNames.size();i++)
      {
        if(cityNames.get(i).equals(city))
          {
            return false;
          }
      }
  cityNames.add(city);
  return true;
     //{
    // ArrayList<String> temp = new ArrayList<String>();
     //temp.add(city);
     //cityNames = new String[temp.size()];
     //cityNames = temp.toArray(cityNames);
     //return true;
    //}
  
  //for(int i = 0; i < cityNames.length; i++)
    // {
     //if(city.equals(cityNames[i]))
       //{
        // return false;
      // }
    // }
  // List<String> temp = new ArrayList<String>();
   //for(String a : cityNames)
     // {
       // temp.add(a);
     // }
   //temp.add(city);
  // cityNames = temp.toArray(cityNames);  
  // return true;
  }

  public boolean addRoute(String source, String destination, int distance,
    double price) throws CityNotFoundException {
    
    int start = cityNames.indexOf(source);
    int finish = cityNames.indexOf(destination);
    
      if(!cityNames.contains(source))
     {
       throw new CityNotFoundException("");
     }
    if(!cityNames.contains(destination))
      {
        throw new CityNotFoundException("");
      }
  
      Route x = new Route(source, destination, distance, price); 
      for(LinkedList<Route> route: G.adj)
         {
           for(Route z: route)
              {
               if(x.source.equals(z.source) && x.destination.equals(z.destination))
                 {
                  return false;
                 }
            }
         }
        Route from = new Route(source, destination, distance, price);
        Route to = new Route(destination, source, distance, price);
        G.addEdge(from);
        G.addEdge(to); 
        return true; 
                       
//add try and catch  
       }

  public boolean updateRoute(String source, String destination, int distance,
    double price) throws CityNotFoundException {
  
// add try catch  
  // Route route = new Route(source, destination, distance, price);
  
  int start = cityNames.indexOf(source);
  int finish = cityNames.indexOf(destination);
  
  if(!cityNames.contains(source))
     {
       throw new CityNotFoundException("");
     }
    if(!cityNames.contains(destination))
      {
        throw new CityNotFoundException("");
      }
   if(start >= 0 && finish >=0)
     {
       for(Route m : G.adj(start))
          {
             if(m.destination.equals(destination))
               {               
                m.distance = distance;
                m.price = price; 
               //return true;
               }
          }
                  
     
        for(Route t: G.adj(finish))
           {
              if(t.destination.equals(source))
                {
                  t.distance = distance;
                  t.price = price;
                }
           }
         
        return true;
     }   
   else
      {
          return false;                  
      }
 }
 
  private class Digraph {
    private final int v;
    private int e;
    private LinkedList<Route>[] adj;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    private double[] priceTo;


    /**
    * Create an empty digraph with v vertices.
    */
    public Digraph(int v) {
      if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
      this.v = v;
      this.e = 0;
      @SuppressWarnings("unchecked")
      LinkedList<Route>[] temp =
      (LinkedList<Route>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
        adj[i] = new LinkedList<Route>();
    }

    /**
    * Add the edge e to this digraph.
    */
    public void addEdge(Route edge) {
      int from = cityNames.indexOf(edge.source);
      adj[from].add(edge);
      e++;
    }


    /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (WeightedDirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<Route> adj(int v) {
      return adj[v];
    }
   

    public void bfs(int source) {
      marked = new boolean[this.v];
      distTo = new int[this.e];
      edgeTo = new int[this.v];
      priceTo = new double[this.e];

      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      q.add(source);

      while (!q.isEmpty()) {
        int v = q.remove();
        for (Route w : adj(v)) {
        int going = cityNames.indexOf(w.destination);
          if (!marked[going]) {
            edgeTo[going] = v;
            distTo[going] = distTo[v] + 1;
            marked[going] = true;
            q.add(going);
          }
        }
      }
    }

    public void dijkstras(int source, int destination) {
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];
      priceTo = new double[this.v];


      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      priceTo[source] = 0;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (Route w : adj(current)) {
          int going = cityNames.indexOf(w.destination);
          if (distTo[current]+w.distance < distTo[going]) {
	      //TODO:update edgeTo and distTo
            edgeTo[going] = current;
            distTo[going] = distTo[current]+w.distance;
            priceTo[going] = priceTo[current] + w.price;
            // maybe need price also
	      
          }
        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        int min = INFINITY;
        current = -1;

        for(int i=0; i<distTo.length; i++){
          if(marked[i])
            continue;
          if(distTo[i] < min){
            min = distTo[i];
            current = i;
          }
        }

	//TODO: Update marked[] and nMarked. Check for disconnected graph.
        if(current >= 0)
        {
            marked[current] = true;
            nMarked++;
        }
        else 
        {
          break;
        }
      }
    }
   }
  
   
  private class WeightedDirectedEdge {
  private final String v;
  private final String w;
  private int weight;
  
  public WeightedDirectedEdge(String v, String w, int weight) {
      this.v = v;
      this.w = w;
      this.weight = weight;
    }

    public String from(){
      return v;
    }

    public String to(){
      return w;
    }

    public int weight(){
      return weight;
    }
  }  
  
  // private class DirectedEdge {
   // private final int v;
   // private final int w;
    /**
    * Create a directed edge from v to w with given weight.
    */
    //public DirectedEdge(int v, int w) {
      //this.v = v;
      //this.w = w;
   // }

    //public int from(){
    // return v;
   // }

   // public int to(){
     // return w;
   // }
  
  
 }
