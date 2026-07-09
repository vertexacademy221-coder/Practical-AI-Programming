package search.maze;

/**
 * 2D Maze search
 * Copyright Yossep BINYOUM 2025-2026, All rights reserved.
*/

public class BreadthFirstSearchEngine extends AbstractSearchEngine
{
	public BreadthFirstSearchEngine(int width, int height)
	{
		super(width, height);
		doSearchOn2DGrid();
	}

	private void doSearchOn2DGrid()
	{
		int width = maze.getWidth();
		int height = maze.getHeight();
		
		boolean alreadyVisitedFlag[][] = new boolean[width][height];

		// float distanceToNode[][] = new float[width][height];
		Location predecessor[][] = new Location[width][height];
		LocationQueue queue = new LocationQueue();

		for (int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				alreadyVisitedFlag[i][j] = false;
				predecessor[i][j] = null;
			}
		}

		alreadyVisitedFlag[startLoc.x][startLoc.y] = true;
		queue.addToBackOfQueue(startLoc);
		boolean success = false;

		outer:
			while(queue.isEmpty() == false){

				Location head = queue.peekAtFrontOfQueue();
				if(head == null) break;
				
				Location[] connected = getPossibleMoves(head);
				for(int i=0; i<4; i++){

					if(connected[i] == null) break;

					int w = connected[i].x;
					int h = connected[i].y;

					if(alreadyVisitedFlag[w][h] == false){

						alreadyVisitedFlag[w][h] = true;
						predecessor[w][h] = head;
						queue.addToBackOfQueue(connected[i]);

						if(equals(connected[i], goalLoc)){
							success = true;
							break outer;
						}
					}
				}
				queue.removeFromFrontQueue();		// ignore return value
			}
			// Now calculate the shortest path from the predecessor array
			maxDepth = 0;
			
			if(success){
				searchPath[maxDepth++] = goalLoc;
				for (int i=0; i<100; i++){
					searchPath[maxDepth] = predecessor[searchPath[maxDepth - 1].x][searchPath[maxDepth - 1].y];
					maxDepth++;

					// Back to starting node
					if(equals(searchPath[maxDepth - 1], startLoc))  break;
				}
			}
	}
	protected class LocationQueue
	{
		public LocationQueue(int num)
		{
			queue = new Location[num];
			head = tail = 0;
			len = num;
		}
		public LocationQueue()
		{
			this(400);
		}
		public void addToBackOfQueue(Location n)
		{
			queue[tail] = n;
			if(tail >= (len - 1)){
				tail = 0;
			}
			else{
				tail++;
			}
		}
		public Location removeFromFrontQueue()
		{
			Location ret = queue[head];
			if (head >= (len - 1))
			{
				head = 0;
			}
			else{
				head++;
			}
			return ret;
		}
		public boolean isEmpty()
		{
			return head == (tail + 1);
		}
		public Location peekAtFrontOfQueue()
		{
			return queue[head];
		}
		private Location[] queue;
		private int tail, head, len;
	}
}