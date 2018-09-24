package mitw.survivalgames.utils.mysql.mysql.builder.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import mitw.survivalgames.utils.mysql.mysql.builder.SQLExecutor;

/**
 * @Author sky
 * @Since 2018-07-03 21:29
 */
public class RunnableQuery {

	private DataSource dataSource;
	private TaskStatement statement;
	private TaskResult result;
	private TaskResult resultNext;
	private Connection connection;
	private boolean autoClose;
	private final String query;

	public RunnableQuery(final String query) {
		this.query = query;
	}

	public RunnableQuery dataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
		return this;
	}

	public RunnableQuery statement(final TaskStatement statement) {
		this.statement = statement;
		return this;
	}

	public RunnableQuery result(final TaskResult result) {
		this.result = result;
		return this;
	}

	public RunnableQuery resultNext(final TaskResult result) {
		this.resultNext = result;
		return this;
	}

	public RunnableQuery connection(final Connection connection) {
		return connection(connection, false);
	}

	public RunnableQuery connection(final Connection connection, final boolean autoClose) {
		this.connection = connection;
		this.autoClose = autoClose;
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T run(final Object def, final T translate) {
		final Object object = run(def);
		return object == null ? def == null ? null : (T) def : (T) object;
	}

	public Object run() {
		return run(null);
	}

	public Object run(final Object def) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		if (dataSource != null) {
			try (Connection connection = dataSource.getConnection()) {
				preparedStatement = connection.prepareStatement(query);
				if (statement != null) {
					statement.execute(preparedStatement);
				}
				resultSet = preparedStatement.executeQuery();
				return getResult(resultSet);
			} catch (final Exception e) {
				printException(e);
			} finally {
				SQLExecutor.freeStatement(preparedStatement, resultSet);
			}
		} else if (connection != null) {
			try {
				preparedStatement = connection.prepareStatement(query);
				if (statement != null) {
					statement.execute(preparedStatement);
				}
				resultSet = preparedStatement.executeQuery();
				return getResult(resultSet);
			} catch (final Exception e) {
				printException(e);
			} finally {
				SQLExecutor.freeStatement(preparedStatement, resultSet);
				if (autoClose) {
					SQLExecutor.freeConnection(connection);
				}
			}
		}
		return def;
	}

	private void printException(final Exception e) {
		e.printStackTrace();
	}

	private Object getResult(final ResultSet resultSet) throws SQLException {
		if (resultNext != null && resultSet.next())
			return resultNext.execute(resultSet);
		else if (result != null)
			return result.execute(resultSet);
		else
			return null;
	}
}