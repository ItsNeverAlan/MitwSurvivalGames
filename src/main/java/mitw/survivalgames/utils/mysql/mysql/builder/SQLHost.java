package mitw.survivalgames.utils.mysql.mysql.builder;

import java.util.Objects;

import mitw.survivalgames.utils.tlib.Strings;

/**
 * @Author sky
 * @Since 2018-05-14 19:01
 */
public class SQLHost {

	private final String host;
	private final String user;
	private final String port;
	private final String password;
	private final String database;

	public SQLHost(final String host, final String user, final String port, final String password, final String database) {
		this.host = host;
		this.user = user;
		this.port = port;
		this.password = password;
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public String getUser() {
		return user;
	}

	public String getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

	public String getDatabase() {
		return database;
	}

	public String getConnectionUrl() {
		return Strings.replaceWithOrder("jdbc:mysql://{0}:{1}/{2}?characterEncoding=utf-8&useSSL=false", this.host, this.port, this.database);
	}

	public String getConnectionUrlSimple() {
		return Strings.replaceWithOrder("jdbc:mysql://{0}:{1}/{2}", this.host, this.port, this.database);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SQLHost))
			return false;
		final SQLHost sqlHost = (SQLHost) o;
		return Objects.equals(getHost(), sqlHost.getHost()) &&
				Objects.equals(getUser(), sqlHost.getUser()) &&
				Objects.equals(getPort(), sqlHost.getPort()) &&
				Objects.equals(getPassword(), sqlHost.getPassword()) &&
				Objects.equals(getDatabase(), sqlHost.getDatabase());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getHost(), getUser(), getPort(), getPassword(), getDatabase());
	}

	@Override
	public String toString() {
		return "SQLHost{" +
				"host='" + host + '\'' +
				", user='" + user + '\'' +
				", port='" + port + '\'' +
				", password='" + password + '\'' +
				", database='" + database + '\'' +
				'}';
	}
}
