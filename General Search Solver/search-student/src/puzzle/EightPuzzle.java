package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import graphs.UnweightedGraphInterface;
import mazes.Cell;
import search.SearchProblem;
import search.Solver;

/**
 * A class to represent an instance of the eight-puzzle.
 * 
 * The spaces in an 8-puzzle are indexed as follows:
 * 
 * 0 | 1 | 2
 * --+---+---
 * 3 | 4 | 5
 * --+---+---
 * 6 | 7 | 8
 * 
 * The puzzle contains the eight numbers 1-8, and an empty space.
 * If we represent the empty space as 0, then the puzzle is solved
 * when the values in the puzzle are as follows:
 * 
 * 1 | 2 | 3
 * --+---+---
 * 4 | 5 | 6
 * --+---+---
 * 7 | 8 | 0
 * 
 * That is, when the space at index 0 contains value 1, the space 
 * at index 1 contains value 2, and so on.
 * 
 * From any given state, you can swap the empty space with a space 
 * adjacent to it (that is, above, below, left, or right of it,
 * without wrapping around).
 * 
 * For example, if the empty space is at index 2, you may swap
 * it with the value at index 1 or 5, but not any other index.
 * 
 * Only half of all possible puzzle states are solvable! See:
 * https://en.wikipedia.org/wiki/15_puzzle
 * for details.
 * 

 * @author liberato
 *
 */
public class EightPuzzle implements SearchProblem<List<Integer>> {
	
	private final List<Integer> start;
	
	/**
	 * Creates a new instance of the 8 puzzle with the given starting values.
	 * 
	 * The values are indexed as described above, and should contain exactly the
	 * nine integers from 0 to 8.
	 * 
	 * @param startingValues
	 *            the starting values, 0 -- 8
	 * @throws IllegalArgumentException
	 *             if startingValues is invalid
	 */
	public EightPuzzle(List<Integer> startingValues) {
		if(startingValues.size()!=9) {
			throw new IllegalArgumentException();
		}
		for(int i=0;i<9;i++) {
			if(!startingValues.contains(i)) {
				throw new IllegalArgumentException();
			}
		}
		start=startingValues;
	}

	@Override
	public List<Integer> getInitialState() {
		// TODO
		return start;
	}

	@Override
	public List<List<Integer>> getSuccessors(List<Integer> currentState) {
		// TODO
		List<List<Integer>> successors=new ArrayList<List<Integer>>();
		List<Integer> leftS=left(currentState.indexOf(0),currentState);
		List<Integer> rightS=right(currentState.indexOf(0),currentState);
		List<Integer> upS=up(currentState.indexOf(0),currentState);
		List<Integer> downS=down(currentState.indexOf(0),currentState);
		if(leftS!=null) {
			successors.add(leftS);
		}
		if(rightS!=null) {
			successors.add(rightS);
		}
		if(upS!=null) {
			successors.add(upS);
		}
		if(downS!=null) {
			successors.add(downS);
		}
		return successors;
	}
	
	public List<Integer> left(int index, List<Integer> cur){
		List<Integer> l=new ArrayList<Integer>();
		for(int i=0;i<9;i++) {
			l.add(cur.get(i));
		}
		if((index==0)||(index==3)||(index==6)) {
			return null;
		}
		else {
			l.set(index, l.get(index-1));
			l.set(index-1, (Integer)0);
		}
		return l;
	}
	
	public List<Integer> right(int index, List<Integer> cur){
		List<Integer> r=new ArrayList<Integer>();
		for(int i=0;i<9;i++) {
			r.add(cur.get(i));
		}
		if((index==2)||(index==5)||(index==8)) {
			return null;
		}
		else {
			r.set(index, r.get(index+1));
			r.set(index+1, (Integer)0);
		}
		return r;
	}
	
	public List<Integer> up(int index, List<Integer> cur){
		List<Integer> u=new ArrayList<Integer>();
		for(int i=0;i<9;i++) {
			u.add(cur.get(i));
		}
		if((index==0)||(index==1)||(index==2)) {
			return null;
		}
		else {
			u.set(index, u.get(index-3));
			u.set(index-3, (Integer)0);
		}
		return u;
	}
	
	public List<Integer> down(int index, List<Integer> cur){
		List<Integer> d=new ArrayList<Integer>();
		for(int i=0;i<9;i++) {
			d.add(cur.get(i));
		}
		if((index==6)||(index==7)||(index==8)) {
			return null;
		}
		else {
			d.set(index, d.get(index+3));
			d.set(index+3, (Integer)0);
		}
		return d;
	}

	@Override
	public boolean isGoal(List<Integer> state) {
		// TODO
		for(int i=0;i<8;i++) {
			if(!state.get(i).equals(i+1)) {
				return false;
			}
		}
		if(!state.get(8).equals(0)) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		EightPuzzle e = new EightPuzzle(Arrays.asList(new Integer[] { 1, 2, 3,
				4, 0, 6, 7, 5, 8 }));

		List<List<Integer>> r = new Solver<List<Integer>>(e).solveWithBFS();
		for (List<Integer> l : r) {
			System.out.println(l);
		}
	}
}
