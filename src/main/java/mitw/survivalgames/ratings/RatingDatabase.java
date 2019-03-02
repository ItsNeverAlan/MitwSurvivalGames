package mitw.survivalgames.ratings;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.skymc.taboolib.mysql.builder.SQLColumn;
import me.skymc.taboolib.mysql.builder.SQLColumnType;
import me.skymc.taboolib.mysql.builder.SQLTable;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.PlayerManager;
import net.development.mitw.Mitw;
import net.development.mitw.database.Database;
import net.development.mitw.utils.FastUUID;
import net.development.mitw.uuid.UUIDCache;

public class RatingDatabase {

	@Getter
	private final Database database;

	@Getter
	private final SQLTable sqlTable;

	@Getter
	private final String TABLE_NAME = "sg_playerdata";

	public RatingDatabase(final SurvivalGames plugin) {

		database = new Database(Mitw.getInstance(), SurvivalGames.getFileManager().getCsettings().getString("database"));

		this.sqlTable = new SQLTable(TABLE_NAME)
				.addColumn(new SQLColumn(SQLColumnType.TEXT, "uuid"))
				.addColumn(new SQLColumn(SQLColumnType.TEXT, "name"))
				.addColumn(new SQLColumn(SQLColumnType.INT, "wins", 0))
				.addColumn(new SQLColumn(SQLColumnType.INT, "kills", 0))
				.addColumn(new SQLColumn(SQLColumnType.INT, "deaths", 0))
				.addColumn(new SQLColumn(SQLColumnType.INT, "rating", 1000));

		sqlTable.executeUpdate(sqlTable.createQuery()).dataSource(database.getDataSource()).run();
		Bukkit.getConsoleSender().sendMessage("¡±aSurvivalGames database connected");
	}

	public PlayerCache createCache(final Player player) {

		if (PlayerManager.hasCache(player.getUniqueId()))
			return PlayerManager.getCache(player.getUniqueId());

		final PlayerCache playerCache = new PlayerCache();
		playerCache.setUuid(player.getUniqueId());

		if (hasData(player)) {
			sqlTable.executeSelect("uuid = ?")
			.dataSource(database.getDataSource())
			.statement(s -> s.setString(1, FastUUID.toString(player.getUniqueId())))
			.resultNext(r -> {
				playerCache.setWins(r.getInt("wins"));
				playerCache.setKills(r.getInt("kills"));
				playerCache.setDeaths(r.getInt("deaths"));
				playerCache.setRating(r.getInt("rating"));
				return null;
			}).run();
		} else {
			sqlTable.executeInsert("?, ?, ?, ?, ?, ?")
			.dataSource(database.getDataSource())
			.statement(s -> {
				s.setString(1, FastUUID.toString(player.getUniqueId()));
				s.setString(2, player.getName());
				s.setInt(3, 0);
				s.setInt(4, 0);
				s.setInt(5, 0);
				s.setInt(6, 1000);
			}).run();
			playerCache.setRating(1000);
		}

		playerCache.setRatingTop(getRatingTop(player.getName()));

		return playerCache;

	}

	private String getRatingTop(final String name) {
		int i = 1;
		for (final String name2 : RatingManager.getInstance().getRatingTop().keySet()) {
			if (name.equals(name2))
				return i + "";
			i++;
		}
		return "unkwown";
	}

	public void savePlayerCache(final PlayerCache playerCache) {
		sqlTable.executeUpdate("UPDATE `" + TABLE_NAME + "` SET `name` = ?, `wins` = ?, `kills` = ?, `deaths` = ?, `rating` = ? WHERE `uuid` = ?;")
		.dataSource(database.getDataSource())
		.statement(s -> {
			s.setString(1,UUIDCache.getName(playerCache.getUuid()));
			s.setInt(2, playerCache.getWins());
			s.setInt(3, playerCache.getKills());
			s.setInt(4, playerCache.getDeaths());
			s.setInt(5, playerCache.getRating());
			s.setString(6, FastUUID.toString(playerCache.getUuid()));
		}).run();
	}

	public boolean hasData(final Player player) {
		return sqlTable.executeSelect("uuid = ?")
				.dataSource(database.getDataSource())
				.statement(s -> s.setString(1, FastUUID.toString(player.getUniqueId())))
				.resultNext(r -> true)
				.run(false, false);
	}

}
