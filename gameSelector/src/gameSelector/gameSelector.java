package gameSelector;

import java.util.ArrayList;
import java.util.Random;


public class gameSelector {
	
private ArrayList<String> gamesList;

public gameSelector(){}

public gameSelector(ArrayList<String> list)
{
	gamesList = list;
}

public ArrayList<String> getGamesList() {
	return gamesList;
}

public void setGamesList(ArrayList<String> gamesList) {
	this.gamesList = gamesList;
}

public void addGameToList(String gameTitle)
{
	gamesList.add(gameTitle);
	
}

public String selectGame()
{
	Random rand = new Random();
	int size=  gamesList.size();
	int index = rand.nextInt(size);
	System.out.println(gamesList.get(index));
	System.out.println("Try to stick with your choice unless friends aren't on!");
	return gamesList.get(index);
	
}

public static void main(String[] args)
{
	ArrayList<String> games =  new ArrayList<String>();
	gameSelector g =  new gameSelector(games);
	g.addGameToList("Madden 18");
	g.addGameToList("UFC 3");
	g.addGameToList("Rocket League (make sure friends can get on)");
	g.addGameToList("Star Wars: The Old Republic");
	g.addGameToList("Drawing/skeleton figures!");
	g.addGameToList("Coloring existing drawings");
	g.addGameToList("Civilization III");
	g.addGameToList("Empire Total War: Darthmod (needs install)");
	g.addGameToList("Make Broncos videos!");
	g.addGameToList("Medieval II Total War: Kingdoms (needs install) Custom Campaign or Third Age Total War");
	g.addGameToList("GTA V");
	g.addGameToList("Star Trek Online");
	g.addGameToList("Battlefield 1");
	g.addGameToList("Soul Calibur VI (When it's released!)");
	g.selectGame();
}
}


