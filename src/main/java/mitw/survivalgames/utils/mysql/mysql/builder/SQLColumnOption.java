package mitw.survivalgames.utils.mysql.mysql.builder;

/**
 * @Author sky
 * @Since 2018-05-14 21:43
 */
public enum SQLColumnOption {

    /**
     * 不能为空
     */
    NOTNULL,

    /**
     * ?���?
     */
    UNIQUE_KEY,

    /**
     * 主键
     */
    PRIMARY_KEY,

    /**
     * ?��??
     */
    AUTO_INCREMENT

}
