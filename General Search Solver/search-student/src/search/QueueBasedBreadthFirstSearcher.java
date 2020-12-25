package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Queue. This results in a
 * breadth-first search.
 * 
 */
public class QueueBasedBreadthFirstSearcher<T> extends Searcher<T> {

	public QueueBasedBreadthFirstSearcher(SearchProblem<T> searchProblem) {
		super(searchProblem);
	}

	@Override
	public List<T> findSolution() {
        		// TODO
		if (solution != null) {
			return solution;
		}
		
		List<T> states=new ArrayList<T>();
		List<T> predecessors=new ArrayList<T>();
		List<T> path=new ArrayList<T>();
		List<T> queue=new ArrayList<T>();
		T start=searchProblem.getInitialState();
		
		visited.add(start);
		states.add(start);
		predecessors.add(start);
		queue.add(start);
		T cur=null;
		while(!queue.isEmpty()){
			T state=queue.remove(0);
			if(searchProblem.isGoal(state)) {
				cur=state;
				break;
			}
//			List<T> successors=searchProblem.getSuccessors(state);
//			List<T> neighbors=new ArrayList<T>();
//			for(T n : successors) {
//				if(!visited.contains(n)) {
//					neighbors.add(n);
//				}
//			}
//			if(neighbors.size()!=0) {
//				for(T neighbor: neighbors) {
//					visited.add(neighbor);
//					if(!states.contains(neighbor)) {
//						states.add(neighbor);
//						predecessors.add(neighbor);
//					}
//					predecessors.set(queue.indexOf(neighbor), state);
			
			for (T neighbor : searchProblem.getSuccessors(state)) {
				if (!visited.contains(neighbor)) {
					
					visited.add(neighbor);
					// if this neighbor hasn't been seen before
					if (!states.contains(neighbor)) {
						// add it to the list of states
						states.add(neighbor);
						// and set its predecessor to itself
						predecessors.add(neighbor);
					}

					// now set the neighbor's predecessor correctly
					predecessors.set(states.indexOf(neighbor), state);
					queue.add(neighbor);
				}
			}
		}
		
		if(cur!=null) {
			path.add(cur);
			while(!cur.equals(start)) {
				T predecessor=predecessors.get(states.indexOf(cur));
				path.add(predecessor);
				cur=predecessor;
			}
			Collections.reverse(path);
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
