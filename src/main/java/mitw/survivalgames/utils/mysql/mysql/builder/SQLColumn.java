package mitw.survivalgames.utils.mysql.mysql.builder;

import java.util.Arrays;

import mitw.survivalgames.utils.tlib.Strings;

/**
 * @Author sky
 * @Since 2018-05-14 19:09
 */
public class SQLColumn {

	public static final SQLColumn PRIMARY_KEY_ID = new SQLColumn(SQLColumnType.INT, "id", SQLColumnOption.NOTNULL, SQLColumnOption.PRIMARY_KEY, SQLColumnOption.AUTO_INCREMENT);

	private final SQLColumnType columnType;
	private int m;
	private int d;

	private final String columnName;
	private Object defaultValue;

	private SQLColumnOption[] columnOptions;

	/**
	 * ??‡æœ¬ ç±»å?‹å¸¸?”¨??„é? å™¨
	 * new SQLColumn(SQLColumnType.TEXT, "username");
	 */
	public SQLColumn(final SQLColumnType columnType, final String columnName) {
		this(columnType, 0, 0, columnName, null);
	}

	/**
	 * CHAR ç±»å?‹å¸¸?”¨??„é? å™¨
	 *
	 * @param columnType
	 * @param columnName
	 */
	public SQLColumn(final SQLColumnType columnType, final int m, final String columnName) {
		this(columnType, m, 0, columnName, null);
	}

	/**
	 * ä¸»é”® ç±»å?‹å¸¸?”¨??„é? å™¨
	 * new SQLColumn(SQLColumnType.TEXT, "username", SQLColumnOption.PRIMARY_KEY, SQLColumnOption.AUTO_INCREMENT);
	 */
	public SQLColumn(final SQLColumnType columnType, final String columnName, final SQLColumnOption... columnOptions) {
		this(columnType, 0, 0, columnName, null, columnOptions);
	}

	/**
	 * ?•°?® ç±»å?‹å¸¸?”¨??„é? å™¨
	 * new SQLColumn(SQLColumnType.TEXT, "player_group", "PLAYER");
	 */
	public SQLColumn(final SQLColumnType columnType, final String columnName, final Object defaultValue) {
		this(columnType, 0, 0, columnName, defaultValue);
	}

	/**
	 * å®Œæ•´??„é? å™¨
	 *
	 * @param columnType    ç±»å??
	 * @param m             m??
	 * @param d             d??
	 * @param columnName    ??ç§°
	 * @param defaultValue  é»˜è®¤??
	 * @param columnOptions å±žæ?§å??
	 */
	public SQLColumn(final SQLColumnType columnType, final int m, final int d, final String columnName, final Object defaultValue, final SQLColumnOption... columnOptions) {
		this.columnType = columnType;
		this.m = m;
		this.d = d;
		this.columnName = columnName;
		this.defaultValue = defaultValue;
		this.columnOptions = columnOptions;
	}

	public SQLColumn m(final int m) {
		this.m = m;
		return this;
	}

	public SQLColumn d(final int d) {
		this.d = d;
		return this;
	}

	public SQLColumn defaultValue(final Object defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	public SQLColumn columnOptions(final SQLColumnOption... columnOptions) {
		this.columnOptions = columnOptions;
		return this;
	}

	public String convertToCommand() {
		if (this.m == 0 && this.d == 0)
			return Strings.replaceWithOrder("`{0}` {1}{2}", columnName, columnType.name().toLowerCase(), convertToOptions());
		else if (this.d == 0)
			return Strings.replaceWithOrder("`{0}` {1}({2}){3}", columnName, columnType.name().toLowerCase(), m, convertToOptions());
		else
			return Strings.replaceWithOrder("`{0}` {1}({2},{3}){4}", columnName, columnType.name().toLowerCase(), m, d, convertToOptions());
	}

	private String convertToOptions() {
		final StringBuilder builder = new StringBuilder();
		for (final SQLColumnOption options : columnOptions) {
			switch (options) {
			case NOTNULL:
				builder.append(" NOT NULL");
				break;
			case PRIMARY_KEY:
				builder.append(" PRIMARY KEY");
				break;
			case AUTO_INCREMENT:
				builder.append(" AUTO_INCREMENT");
				break;
			case UNIQUE_KEY:
				builder.append(" UNIQUE KEY");
				break;
			default:
			}
		}
		if (defaultValue != null) {
			if (defaultValue instanceof String) {
				builder.append(" DEFAULT '").append(defaultValue).append("'");
			} else {
				builder.append(" DEFAULT ").append(defaultValue);
			}
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return "columnType=" + "SQLColumn{" + columnType + ", m=" + m + ", d=" + d + ", columnName='" + columnName + '\'' + ", defaultValue=" + defaultValue + ", columnOptions=" + Arrays.toString(columnOptions) + '}';
	}
}