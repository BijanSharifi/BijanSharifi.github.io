package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	/* STUDENTS:  You decide what data structure(s) to use to
	 * implement this class.
	 * 
	 * You may use any data structures you like, and any Java 
	 * collections that we learned about this semester.  Remember 
	 * that you are implementing a weighted, directed graph.
	 */
	
	//Private instance variable using a map with a key and with the value another 
	//map carrying the verticy weight
	private Map<V, Map<V, Integer>> vertices;
	
	
	
	
	
	
	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing. 
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	

	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	
	
	//Constructor which initializes vertices instance variable to a hashMap
	//ObserverList is stored with a hashSet
	public WeightedGraph() {
		vertices=new HashMap<V, Map<V, Integer>>();
		observerList=new HashSet<GraphAlgorithmObserver<V>>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	//Student Comments
	/*
	 * If graph already contains the vertex, throw an Illegal Argument Exception. Otherwise
	 * add the vertex and the value is a HashMap which will store the cost of that vertex
	 */
	public void addVertex(V vertex) {
		if(vertices.containsKey(vertex)) {
			throw new IllegalArgumentException();
		}else {
			vertices.put(vertex, new HashMap<V, Integer>());
		}
	}
	
	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	//Student Comments
	/*
	 * Checks if a vertex is in the graph. Runs in O(1) because vertices is a hashMap. 
	 * If it contains the key, returns true and if it doesn't returns false.
	 */
	
	 
	public boolean containsVertex(V vertex) {
		if(vertices.containsKey(vertex)) {
			return true;
		}else {
			return false;
		}
	}

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	
	//Student Comments
	/*
	 * If the vertex from or to are not not in the graph, or the weight is less than zero, this 
	 * method throws an Illegal Argument Exception. Otherwise, get the from vert and put the 
	 * to vert along with the weight in the hashMap value.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if((vertices.containsKey(from))==false || (vertices.containsKey(to)==false || weight<0)){
			throw new IllegalArgumentException();
		}else {
			vertices.get(from).put(to, weight);
		}
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	//Student Comments
	/*
	 * If the vertices does not have the from or to vert, this throws illegal argument exception.
	 * Also, if the from does not contain to this returns null. That basically means if there is no
	 * vert from from and to. Otherwise, return the value between the two.
	 */
	public Integer getWeight(V from, V to) {
		if((vertices.containsKey(from))==false||(vertices.containsKey(to))==false) {
			throw new IllegalArgumentException();
		}else if((vertices.get(from).containsKey(to))==false) {
			return null;
		}else {
			return vertices.get(from).get(to);
		}
	}

	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	
	//Student Comments
	/*
	 * Use a hashset for visited set and use linkedlist for the queue. The GraphAlgorithmObserver
	 * traverses the observerList which notifys the observers that BFS has started. Then, the starting vert
	 * is added to the queue. While the queue is not empty, vert curr is the element removed from the queue.
	 * If curr is not in the visitedSet and curr does not equal, we add curr to the visitedSet. We then
	 * traverse currs adjacencies by using the keySet method. If the adjacency is not in the visitedSet, 
	 * we add it to the queue. Then the observers notify the visit of curr. If curr does equal end, 
	 * we add curr to the visitedSet and notify the observers that searchIsOver and after that we return and 
	 * the method is over.
	 */
	public void DoBFS(V start, V end) {
		
		
		
		Set<V>visitedSet=new HashSet<>();
		Queue<V>queue=new LinkedList<>();
		for(GraphAlgorithmObserver<V>observer: observerList) {
			observer.notifyBFSHasBegun();
		}
		queue.add(start);
		while(!queue.isEmpty()) {
			V curr=queue.poll();
			if(!visitedSet.contains(curr)) {
				if(!curr.equals(end)) {
					visitedSet.add(curr);
					for(V adjacency: vertices.get(curr).keySet()) {
						if(!visitedSet.contains(adjacency)) {
							queue.add(adjacency);
						}
						
					}
					for(GraphAlgorithmObserver<V>observer: observerList) {
						observer.notifyVisit(curr);
					}
					
				}else {
					visitedSet.add(curr);
					for(GraphAlgorithmObserver<V>observer: observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}
			}
			
		}
		
		
	}
	
	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	
	//Student Comments
	/*
	 * Again, a hashSet is used for visitedSet. Instead of a queue, we use a stack to use for the 
	 * next things to traverse. This is what is used for DFT. First, we push start onto the stack.
	 * Then we notify the observers that DFS has started. While stack is not empty, we set our
	 * curr vertex to whatever was just popped off the stack. If visitedSet does not contain curr,
	 * we notify a visit of curr. Then curr is added to the visitedSet. Now we store the 
	 * adjacencies of curr in a map and then we iterate through these adjacencies. If the adjacent
	 * is not in the visitedSet, we push the adjacent onto the stack. If curr equals the end, 
	 * we notify that search is over and return and the method is over and search is complete.
	 */
	public void DoDFS(V start, V end) {
		Set<V>visitedSet=new HashSet<>();
		Stack<V>stack=new Stack<>();
		stack.push(start);
		for(GraphAlgorithmObserver<V>observer: observerList) {
			observer.notifyDFSHasBegun();
		}
		while(stack.size()!=0) {
			V curr=stack.pop();
			
			if(!(visitedSet.contains(curr))) {
				for(GraphAlgorithmObserver<V>observer: observerList) {
					observer.notifyVisit(curr);
				}
				visitedSet.add(curr);
				Map<V, Integer>currAdj=vertices.get(curr);
				for(V adjacent: currAdj.keySet()) {
					if(!(visitedSet.contains(adjacent))) {
						stack.push(adjacent);
					}
				}
			}
			if(end.equals(curr)) {
				for(GraphAlgorithmObserver<V>observer: observerList) {
					observer.notifySearchIsOver();
				}
				return;
			}
			
		}
	}
	
	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	
	//Student Comments
	/*
	 * Use 2 maps and a set for this method. We have a map which holds the cost of each vertex. 
	 * We also have another map which stores the previous vertex from a certain vertex. We also have
	 * a visitedSet. First notify the observers that Dijkstras has begun. To start this method, 
	 * just add start and zero to cost. We then put an infinity for the shortest distance for everything
	 * other than the start. We then do something similar for the prevVert map, which we assign null
	 * for everythings prevVert. While the sizes of the vertices and the visitedSet are not the same size,
	 * we set the currSmallest to infinity, and the smallest vert to null. We then iterate through the cost
	 * KeySet. If the visitdSet does not contain curr, and the cost of curr is less than currSmallest,
	 * currSmalest is set to currs cost and smallestVert is set to curr. Does this for all the costs.
	 * After this is done, smallestVert gets added to the visitedSet and the observers notify
	 * Dijkstras Vertex is completed, passing in the smallest vert and the appropriate cost.
	 * The next step deals with the adjacencies of the smallestVert. Iterate through these adjacencies
	 * and if curr is not in the visitedSet, and if the weight of the smallestVert and curr
	 * plus the cost of smallestVert is less than the cost of curr, we put
	 * in the cost map curr and the weight of smallestVert to curr, plus the cost of the smallestVert.
	 * We then update the prevVert map and put curr and smallest vert. We then have finished with the cost
	 * of the path, but now we need to find an appropriate path. We want to start at the end, and trace back-
	 * wards. The variable pathFinder represents the current vertex we are at. We start at the end. We use 
	 * an ArrayList to keep track of the path. While pathFinder is not null, add pathFinder to the list and 
	 * then pathFinder is updated by getting the prevVert of the previous vertex in the path. Finally, we
	 * notify the observers that Dijsktra is over and method is over.
	 */
	public void DoDijsktra(V start, V end) {
		Map<V, Integer>cost=new HashMap<>();
		Map<V, V>prevVert=new HashMap<>();
		Set<V>visitedSet=new HashSet<>();
		
		
		for(GraphAlgorithmObserver<V>observer: observerList) {
			observer.notifyDijkstraHasBegun();
		}
		cost.put(start, 0);
		for(V curr: vertices.keySet()) {
			if(curr.equals(start)==false) {
				cost.put(curr, (int)Double.POSITIVE_INFINITY);
			}
			
			
		}
		for(V curr: vertices.keySet()) {
			prevVert.put(curr, null);
		}
		while(vertices.size()!=visitedSet.size()) {
			int currSmallest=(int)Double.POSITIVE_INFINITY;
			V smallestVert=null;
			for(V curr: cost.keySet()) {
				if(visitedSet.contains(curr)==false) {
					if(cost.get(curr)<currSmallest) {
						currSmallest=cost.get(curr);
						smallestVert=curr;
						
					}
					
				}
			}
			visitedSet.add(smallestVert);
			for(GraphAlgorithmObserver<V>observer: observerList) {
				observer.notifyDijkstraVertexFinished(smallestVert, cost.get(smallestVert));
			}
			for(V curr: vertices.get(smallestVert).keySet()) {
				if(visitedSet.contains(curr)==false) {
					if(getWeight(smallestVert, curr)+cost.get(smallestVert)<cost.get(curr)) {
						cost.put(curr, getWeight(smallestVert, curr) + cost.get(smallestVert));
						prevVert.put(curr, smallestVert);
					}
				}
				
			}
		}
		V pathFinder=end;
		List<V>path=new ArrayList<>();
		while(pathFinder!=null) {
			path.add(0, pathFinder);
			pathFinder=prevVert.get(path.get(0));
			
		}
		for(GraphAlgorithmObserver<V>observer: observerList) {
			observer.notifyDijkstraIsOver(path);
		}
	
		
		
		
		
	}
	
}
