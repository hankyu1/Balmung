package entityGame;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.LinkedList;

public class GridBox {
	
	LinkedList<LinkedList<Entity>> list = new LinkedList<LinkedList<Entity>>();
	
	protected class Grid extends Rectangle {
		
		protected LinkedList<Entity> content;
		
		public Grid(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			width = w;
			height = h;
			content = new LinkedList<Entity>();
		}
	}
	
	public Grid[] grid;
	
	public GridBox(Dimension size) {
		grid = new Grid[9];
		
		// size of the grid
		int width = size.width/3,
			height = size.height/3;
		
		// crop into 9 region
		for(int i = 0; i < 9; i++) {
			if(i < 3)
				grid[i] = new Grid(i*width, 0, width, height);
			else if(i < 6)
				grid[i] = new Grid((i-3)*width, height, width, height);
			else
				grid[i] = new Grid((i-6)*width, height*2, width, height);
		}
	}
	
	// add entities to 
	public void addToGrid(LinkedList<Entity> list) {
		for(Entity e : list) {
			for(Grid g : grid)
				if(g.intersects(e.getBoundingBox()))
					g.content.add(e);
		}
	}
	
	// clean list
	public void cleanList() {
		for(Grid g : grid)
			g.content.clear();
	}
	
	// return all the grids that has the given entity in it
	public LinkedList<LinkedList<Entity>> getGridList(Entity e) {
		
		// find the list of entities that are in the same grid
		list.clear();
		for(Grid g : grid)
			if(g.content.contains(e) && e.isSolid()) {
				list.add(g.content);
			}
		//System.out.println("BigO: " + Counter);
		return list;
	}
	
	/*
	Rectangle[] BoundingBox;
	
	public GridBox(Dimension size, int Num) {
		//BoundingBox = new Rectangle(size);
		BoundingBox = new Rectangle[Num];
		
		int width = size.width/3,
			height = size.height/3;
		
		// crop into 9 region
		for(int i = 0; i < 9; i++) {
			if(i < 3)
				BoundingBox[i] = new Rectangle(i*width, 0, width, height);
			else if(i < 6)
				BoundingBox[i] = new Rectangle((i-3)*width, height, width, height);
			else
				BoundingBox[i] = new Rectangle((i-6)*width, height*2, width, height);
		}
	}
	*/
	
	
}
