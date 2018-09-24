package mitw.survivalgames.utils.mysql;

import javax.sql.DataSource;

import lombok.Getter;
import mitw.survivalgames.utils.mysql.mysql.builder.SQLHost;
import mitw.survivalgames.utils.mysql.mysql.builder.hikari.HikariHandler;
import net.development.mitw.config.MySQL;

public class Database {

	@Getter
	private final SQLHost sqlHost;
	@Getter
	private final DataSource dataSource;

	public Database(final String database) {
		sqlHost = new SQLHost(MySQL.HOSTNAME, MySQL.USER, MySQL.PORT, MySQL.PASSWORD, database);
		dataSource = HikariHandler.createDataSource(sqlHost);

	}

}
