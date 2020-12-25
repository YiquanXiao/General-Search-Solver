package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Stack. This results in a
 * depth-first search.
 * 
 */
public class StackBasedDepthFirstSearcher<T> extends Searcher<T> {
	
	public StackBasedDepthFirstSearcher(SearchProblem<T> searchProblem) {
		super(searchProblem);
	}

	@Override
	public List<T> findSolution() {
        		// TODO
		if (solution != null) {
			return solution;
		}
		Stack<T> path=new Stack<T>();
		T start=searchProblem.getInitialState();
		visited.add(start);
		path.add(start);
		while(!path.isEmpty()) {
			List<T> successors=searchProblem.getSuccessors(path.peek());
			List<T> neighbors=new ArrayList<T>();
			for(T n : successors) {
				if(!visited.contains(n)) {
					neighbors.add(n);
				}
			}
			if(neighbors.size()==0) {
				path.pop();
			}
			else {
				T neighbor=neighbors.get(0);
				if(searchProblem.isGoal(neighbor)) {
					visited.add(neighbor);
					path.push(neighbor);
					break;
				}
				visited.add(neighbor);
				path.push(neighbor);
			}
		}
		
		
		if (path.size() > 0) {
			if (!isValidSolution(path)) {
				throw new RuntimeException(
						"searcher should never find an invalid solution!");
			}
		}
		
		return path;
	}
	
}
