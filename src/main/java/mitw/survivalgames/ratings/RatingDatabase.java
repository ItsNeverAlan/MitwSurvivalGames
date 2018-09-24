package mitw.survivalgames.ratings;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.Getter;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.utils.mysql.Database;
import mitw.survivalgames.utils.mysql.mysql.builder.SQLColumn;
import mitw.survivalgames.utils.mysql.mysql.builder.SQLColumnOption;
import mitw.survivalgames.utils.mysql.mysql.builder.SQLColumnType;
import mitw.survivalgames.utils.mysql.mysql.builder.SQLTable;

public class RatingDatabase {

	@Getter
	private final Database database;

	private final SQLTable sqlTable;

	private final String TABLE_NAME = "survivalgames-playerdata";

	public RatingDatabase(final SurvivalGames plugin) {

		database = new Database(SurvivalGames.getFileManager().getCsettings().getString("database"));

		this.sqlTable = new SQLTable(TABLE_NAME)
				.addColumn(new SQLColumn(SQLColumnType.VARCHAR, "uuid", SQLColumnOption.UNIQUE_KEY, SQLColumnOption.PRIMARY_KEY))
				.addColumn(new SQLColumn(SQLColumnType.INT, "wins", 0))
				.addColumn(new SQLColumn(SQLColumnType.INT, "kills", 0))
				.addColumn(new SQLColumn(SQLColumnType.INT, "deaths", 0))
				.addColumn(new SQLColumn(SQLColumnType.INT, "rating", 0));

		sqlTable.executeQuery(sqlTable.createQuery()).run();
		Bukkit.getConsoleSender().sendMessage("¡±aSurvivalGames database connected");
	}

	public PlayerCache createCache(final Player player) {

		final PlayerCache playerCache = new PlayerCache();

		if (hasData(player)) {
			sqlTable.executeSelect("uuid = " + player.getUniqueId())
			.dataSource(database.getDataSource())
			.resultNext(r -> {
				playerCache.setWins(r.getInt("wins"));
				playerCache.setKills(r.getInt("kills"));
				playerCache.setDeaths(r.getInt("deaths"));
				playerCache.setRating(r.getInt("rating"));
				return null;
			}).run();
		} else {
			sqlTable.executeInsert("?, ?, ?, ?, ?")
			.dataSource(database.getDataSource())
			.statement(s -> {
				s.setString(1, player.getUniqueId().toString());
				s.setInt(2, 0);
				s.setInt(3, 0);
				s.setInt(4, 0);
				s.setInt(5, 1000);
			}).run();
		}

		return playerCache;

	}

	public void savePlayerCache(final PlayerCache playerCache) {
		sqlTable.executeUpdate("UPDATE `" + TABLE_NAME + "` SET `wins` = ?, `kills` = ?, `deaths` = ?, `rating` = ? WHERE `uuid` = ?;")
		.dataSource(database.getDataSource())
		.statement(s -> {
			s.setInt(1, playerCache.getWins());
			s.setInt(2, playerCache.getKills());
			s.setInt(3, playerCache.getDeaths());
			s.setInt(4, playerCache.getRating());
			s.setString(5, playerCache.getUuid());
		}).run();
	}

	public boolean hasData(final Player player) {
		return sqlTable.executeSelect("uuid = ?")
				.dataSource(database.getDataSource())
				.statement(s -> s.setString(1, player.getUniqueId().toString()))
				.resultNext(r -> true)
				.run(false, false);
	}

}
