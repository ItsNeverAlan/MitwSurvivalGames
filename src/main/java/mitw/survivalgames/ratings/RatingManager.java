package mitw.survivalgames.ratings;

import lombok.Getter;
import mitw.survivalgames.SurvivalGames;

public class RatingManager {

	@Getter
	private static RatingManager instance;

	@Getter
	private final RatingDatabase database;

	public RatingManager() {
		instance = this;
		database = new RatingDatabase(SurvivalGames.getInstance());
	}



}
