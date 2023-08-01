import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimumSnippet {
	

	//Private instance variables
	private int start; //Marks the start of the snippet
	private int end; //Marks the end of the snippet
	private Iterable<String>document; //The document
	private List<String> terms; //The terms being searched for
	private List<String>minimumSnippet; //Holder for the minimum snippet
	private ArrayList<String>docArray; //The document transformed into an arrayList
	private int front; //Front of the subList
	private int back; //Back of the subList
	


	
	
	
	
	
	
	
	
	
	
	

	//Constructor which takes in the document and the terms and constructs a minimumSnippet
	//Sets the parameter document equal to the current object document and parameter terms equal to
	//the terms current object
	//Also sets the minimumSnippet holder as a new ArrayList of strings
	//Set docArray equal to the document passed into the documentToArrayListHelper method 
	//Uses the setMinimumSnippetHelper and pass in the docArray
	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		if(terms==null || terms.size()==0) {
			throw new IllegalArgumentException("No terms entered");
		}
		this.document=document;
		this.terms=terms;
		minimumSnippet=new ArrayList<String>();
		docArray=documentToArrayListHelper(this.document);
		
		if(foundAllTerms()==true) {
			setMinimumSnippetHelper(docArray);
			start=front;
			end=back-1; 
			
			
			
			
		
			
		
			
			
		}
		
		
	
		
		
		
		
	
		
	}
	/* Helper method which takes in the document parameter and transfers the contents to 
	 * an ArrayList using a for-each loop
	 */
	private ArrayList<String> documentToArrayListHelper(Iterable<String> document){
		ArrayList<String>newDoc=new ArrayList<>();
		for(String a: document) {
			newDoc.add(a);
		}
		return newDoc;
	}
	
	
	/* Helper method which does the majority of work for getting the minimumSnippet. It takes
	 * in the parameter of a List which contains strings. Start it off with a for loop (I realize now
	 * it would have made more sense to use a while loop, but it still works), and if the subList
	 * contains all the terms, the loop continues. The front and back variables get incremented and 
	 * decremented. If it does not contain all the terms we first check if a corner case is happening.
	 * The corner case is that if the partial snippet does not contain all the terms, but shifted in each way 
	 * back one and forward one. If both of them contain all the terms, we make new variables tracking the
	 * front and back for each of our two potential snippets. We make a minimum snippet for both of these
	 * potential cases and store them each in minSnipHold1 and minSnipHold2. We set the minimumSnippet to whichever
	 * ones size is smallest. If this is not the case, we proceed and check if the sublist of the back index
	 * plus one and the front index. If true, we make that our minimumSnippet with the back+1 index. 
	 * If this is not the minimumSnippet, we test if it the other way.
	 */
	private void setMinimumSnippetHelper(List<String>docArray) {
		front=-1; 
		back=docArray.size()+1;
		List<String>minSnipHold1=new ArrayList<String>();
		List<String>minSnipHold2=new ArrayList<String>();
		
		
		
		
		for(int index=0; index<docArray.size(); index++) {
			if(docArray.subList(++front, --back).containsAll(terms)==true) {
				continue;
			}else if(docArray.subList(front, back+1).containsAll(terms)==true
					&& docArray.subList(front-1, back).containsAll(terms)==true) {
				int front1=front;
				int front2=front-1;
				int back1=back+1;
				int back2=back;
				for(int inner=0; inner<docArray.size(); inner++) {
					if(docArray.subList(++front1, back1).containsAll(terms)==true) {
						continue;
						
						
					}else {
						minSnipHold1=docArray.subList(--front1, back1);
						break;
					}
					
				}
				for(int inner=0; inner<docArray.size(); inner++) {
					if(docArray.subList(front2, --back2).containsAll(terms)) {
						continue;
					}else {
						minSnipHold2=docArray.subList(front2, ++back2);
					}
				}
				if(minSnipHold1.size()<=minSnipHold2.size()) {
					front=front1;
					back=back1;
					minimumSnippet=minSnipHold1;
					
					return;
				}else {
					front=front2;
					back=back2;
					minimumSnippet=minSnipHold2;
					return;
				}
				
				
				
				
				
				
				
		}else if(docArray.subList(front, back+1).containsAll(terms)==true) {
				for(int inner=0; inner<docArray.size(); inner++) {
					if(docArray.subList(++front, back+1).containsAll(terms)==true) {
						continue;
					}else {
						minimumSnippet=docArray.subList(--front, ++back);
						return;
					}
				}
			}else if(docArray.subList(front-1, back).containsAll(terms)==true) {
				for(int inner=0; inner<docArray.size(); inner++) {
					if(docArray.subList(front-1, --back).containsAll(terms)==true) {
						continue;
					}else {
						minimumSnippet=docArray.subList(--front, ++back);
						return;
					}
				}
			}
		}
		
		
		
		
	
	}
	
	
	
	
	
	
	//If the docArray contains all the terms, this return true and else it returns false
	public boolean foundAllTerms() {
		if(docArray.containsAll(this.terms)) {
			return true;
		}else {
			return false;
		}

		

	}
	
	//Returns the start
	public int getStartingPos() {
		return this.start;
	
		

	}
	//Returns the end
	public int getEndingPos() {
		return this.end;
		

	}
	//Returns the length 
	public int getLength() {
		return this.minimumSnippet.size();
		
	
		

	}
	
	/*Method which returns an int which checks if a certain index of the terms is 
	 * equal to the docArray of the index of the loop, and then we set the position
	 * int equal to the loop index and return the position
	 */
	public int getPos(int index) {
		int position=0;
		for(int loop=start; loop<=end; loop++) {
			if(terms.get(index).equals(docArray.get(loop))) {
				position=loop;
			}
		}
		return position;
		
		
		
		

	}

}
