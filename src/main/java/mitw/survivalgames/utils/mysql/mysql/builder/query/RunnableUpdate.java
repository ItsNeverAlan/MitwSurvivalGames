package mitw.survivalgames.utils.mysql.mysql.builder.query;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import mitw.survivalgames.utils.mysql.mysql.builder.SQLExecutor;

/**
 * F
 *
 * @Author sky
 * @Since 2018-07-03 21:29
 */
public class RunnableUpdate {

	private DataSource dataSource;
	private TaskStatement statement;
	private Connection connection;
	private boolean autoClose;
	private final String query;

	public RunnableUpdate(final String query) {
		this.query = query;
	}

	public RunnableUpdate dataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
		return this;
	}

	public RunnableUpdate statement(final TaskStatement task) {
		this.statement = task;
		return this;
	}

	public RunnableUpdate connection(final Connection connection) {
		return connection(connection, false);
	}

	public RunnableUpdate connection(final Connection connection, final boolean autoClose) {
		this.connection = connection;
		this.autoClose = autoClose;
		return this;
	}

	public void run() {
		PreparedStatement preparedStatement = null;
		if (dataSource != null) {
			try (Connection connection = dataSource.getConnection()) {
				preparedStatement = connection.prepareStatement(query);
				if (statement != null) {
					statement.execute(preparedStatement);
				}
				preparedStatement.executeUpdate();
			} catch (final Exception e) {
				printException(e);
			} finally {
				SQLExecutor.freeStatement(preparedStatement, null);
			}
		} else if (connection != null) {
			try {
				preparedStatement = connection.prepareStatement(query);
				if (statement != null) {
					statement.execute(preparedStatement);
				}
				preparedStatement.executeUpdate();
			} catch (final Exception e) {
				printException(e);
			} finally {
				SQLExecutor.freeStatement(preparedStatement, null);
				if (autoClose) {
					SQLExecutor.freeConnection(connection);
				}
			}
		}
	}

	private void printException(final Exception e) {
		e.printStackTrace();
	}
}
