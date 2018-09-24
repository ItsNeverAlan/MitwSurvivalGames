package mitw.survivalgames.utils.mysql.mysql.builder;

import java.util.Arrays;

import mitw.survivalgames.utils.mysql.mysql.builder.query.RunnableQuery;
import mitw.survivalgames.utils.mysql.mysql.builder.query.RunnableUpdate;
import mitw.survivalgames.utils.mysql.string.ArrayUtils;
import mitw.survivalgames.utils.tlib.Strings;

/**
 * @Author sky
 * @Since 2018-05-14 19:07
 */
public class SQLTable {

	private final String tableName;
	private SQLColumn[] columns;

	public SQLTable(final String tableName) {
		this.tableName = tableName;
	}

	public SQLTable(final String tableName, final SQLColumn... column) {
		this.tableName = tableName;
		this.columns = column;
	}

	public SQLTable addColumn(final SQLColumn sqlColumn) {
		if (columns == null) {
			columns = new SQLColumn[] {sqlColumn};
		} else {
			columns = ArrayUtils.arrayAppend(columns, sqlColumn);
		}
		return this;
	}

	public String createQuery() {
		final StringBuilder builder = new StringBuilder();
		Arrays.stream(columns).forEach(sqlColumn -> builder.append(sqlColumn.convertToCommand()).append(", "));
		return Strings.replaceWithOrder("CREATE TABLE IF NOT EXISTS `{0}` ({1})", tableName, builder.substring(0, builder.length() - 2));
	}

	public String deleteQuery() {
		return Strings.replaceWithOrder("drop table if exists `{0}`" + tableName);
	}

	public String cleanQuery() {
		return Strings.replaceWithOrder("DELETE FROM `{0}`" + tableName);
	}

	public String truncateQuery() {
		return Strings.replaceWithOrder("TRUNCATE TABLE `{0}`", tableName);
	}

	public RunnableUpdate executeInsert(final String values) {
		return executeUpdate("INSERT INTO " + tableName + " VALUES(" + values + ")");
	}

	public RunnableQuery executeSelect(final String where) {
		return executeQuery("SELECT * FROM " + tableName + " WHERE " + where);
	}

	public RunnableUpdate executeUpdate(final String update, final String where) {
		return executeUpdate("UPDATE " + tableName + " SET " + update + " WHERE " + where);
	}

	public RunnableUpdate executeUpdate(final String query) {
		return new RunnableUpdate(query);
	}

	public RunnableQuery executeQuery(final String query) {
		return new RunnableQuery(query);
	}

	// *********************************
	//
	//        Getter and Setter
	//
	// *********************************

	public String getTableName() {
		return tableName;
	}

	public SQLColumn[] getColumns() {
		return columns;
	}
}