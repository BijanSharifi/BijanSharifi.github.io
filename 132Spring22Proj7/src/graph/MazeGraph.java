package graph;
import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/* STUDENTS:  SEE THE PROJECT DESCRIPTION FOR A MUCH
	 * MORE DETAILED EXPLANATION ABOUT HOW TO WRITE
	 * THIS CONSTRUCTOR
	 */
	
	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and B) which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	
	//Studet Comment
	/*
	 * Because MazeGraph is an extension of WeightedGraph, we need to call the super 
	 * constructor. We then add vertexes based on the maze width and height. We then again
	 * go through the width and the heights and create variables of Juncture type that are 
	 * above, below, left, right, and the curr juncture. Curr is just assinged height and width
	 * and the other four junctures are based on the direction and the height or width are adjusted
	 * accordingly. We then add edges based on if there vertex in that direction and if there 
	 * is a wall in that direction. Does this for all the directions.
	 */
	public MazeGraph(Maze maze) {
		super();
		for(int width=0; width<maze.getMazeWidth(); width++) {
			for(int height=0; height<maze.getMazeHeight(); height++) {
				addVertex(new Juncture(width, height));
			}
		}
		for(int width=0; width<maze.getMazeWidth(); width++) {
			for(int height=0; height<maze.getMazeHeight(); height++) {
				Juncture above=new Juncture(width, height-1);
				Juncture below=new Juncture(width, height+1);
				Juncture left=new Juncture(width-1, height);
				Juncture right=new Juncture(width+1, height);
				Juncture curr=new Juncture(width, height);
				if(containsVertex(left)==true && maze.isWallToLeft(curr)==false) {
					addEdge(curr, left, maze.getWeightToLeft(curr));
					
				}
				if(containsVertex(right)==true && maze.isWallToRight(curr)==false) {
					addEdge(curr, right, maze.getWeightToRight(curr));
				}
				if(containsVertex(above)==true && maze.isWallAbove(curr)==false) {
					addEdge(curr, above, maze.getWeightAbove(curr));
				}
				if(containsVertex(below)==true && maze.isWallBelow(curr)==false) {
					addEdge(curr, below, maze.getWeightBelow(curr));
				}
			}
		}
		
		
	}
}
