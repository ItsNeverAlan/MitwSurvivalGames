package mitw.survivalgames.ratings;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import mitw.survivalgames.SurvivalGames;

public class RatingManager {

	@Getter
	private static RatingManager instance;

	@Getter
	private final RatingDatabase database;

	@Getter
	private Map<String, Integer> ratingTop;
	@Getter
	private Map<String, Integer> winsTop;
	@Getter
	private Map<String, Integer> killsTop;
	@Getter
	private Map<String, Double> kdrTop;

	public RatingManager() {
		instance = this;
		database = new RatingDatabase(SurvivalGames.getInstance());
		updateTops();
	}

	public void updateTops() {
		SurvivalGames.getInstance().getServer().getScheduler().runTaskAsynchronously(SurvivalGames.getInstance(), () -> {
			{
				final Map<String, Integer> ratingTop = new HashMap<>();
				database.getSqlTable().executeQuery("SELECT name, rating from " + database.getTABLE_NAME() + "")
				.dataSource(database.getDatabase().getDataSource())
				.result(r -> {
					if (r.isBeforeFirst()) {
						while (r.next()) {
							try {
								final String name = r.getString("name");
								if (name != null && !ratingTop.containsKey(name)) {
									ratingTop.put(name, r.getInt("rating"));
								}
							} catch (final SQLException e) {
								if (e.getMessage() != null && !e.getMessage().contains("empty result")) {
									e.printStackTrace();
								}
							}
						}
					}
					return r;
				})
				.run();
				this.ratingTop = ratingTop.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			}
			{
				final Map<String, Integer> top = new HashMap<>();
				database.getSqlTable().executeQuery("SELECT name, wins from " + database.getTABLE_NAME() + " order by wins desc limit 10")
				.dataSource(database.getDatabase().getDataSource())
				.result(r -> {
					if (r.isBeforeFirst()) {
						while (r.next()) {
							try {
								final String name = r.getString("name");
								if (name != null && !top.containsKey(name)) {
									top.put(name, r.getInt("wins"));
								}
							} catch (final SQLException e) {
								if (e.getMessage() != null && !e.getMessage().contains("empty result")) {
									e.printStackTrace();
								}
							}
						}
					}
					return r;
				})
				.run();
				this.winsTop = top.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			}
			{
				final Map<String, Integer> top = new HashMap<>();
				database.getSqlTable().executeQuery("SELECT name, kills from " + database.getTABLE_NAME() + " order by kills desc limit 10")
				.dataSource(database.getDatabase().getDataSource())
				.result(r -> {
					if (r.isBeforeFirst()) {
						while (r.next()) {
							try {
								final String name = r.getString("name");
								if (name != null && !top.containsKey(name)) {
									top.put(name, r.getInt("kills"));
								}
							} catch (final SQLException e) {
								if (e.getMessage() != null && !e.getMessage().contains("empty result")) {
									e.printStackTrace();
								}
							}
						}
					}
					return r;
				})
				.run();
				this.killsTop = top.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			}
			{
				final Map<String, Double> top = new HashMap<>();
				database.getSqlTable().executeQuery("SELECT name, kills, deaths from " + database.getTABLE_NAME() + "")
				.dataSource(database.getDatabase().getDataSource())
				.result(r -> {
					if (r.isBeforeFirst()) {
						while (r.next()) {
							try {
								final String name = r.getString("name");
								if (name != null && !top.containsKey(name)) {
									final int deaths = r.getInt("deaths"), kills = r.getInt("kills");

									top.put(name, ((double) deaths == 0 ? (double) kills : ((double) kills / deaths)));
								}
							} catch (final SQLException e) {
								if (e.getMessage() != null && !e.getMessage().contains("empty result")) {
									e.printStackTrace();
								}
							}
						}
					}
					return r;
				})
				.run();
				this.kdrTop = top.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
						.limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			}
		});
	}



}
