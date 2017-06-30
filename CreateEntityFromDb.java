package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * 数据库表转换成javaBean对象小工具(已用了很长时间), 1 bean属性按原始数据库字段经过去掉下划线,并大写处理首字母等等. 2 生成的bean带了数据库的字段说明. 3 各位自己可以修改此工具用到项目中去. 4 生产的文件在项目的根名录下
 */
public class CreateEntityFromDb {
    private String tableName = "";

    private String[] fieldNames; // 字段名

    private String[] colNames; // 列名

    private String[] colTypes; // 列数据类型

    private int[] colSizes; // 列名大小

    private int[] colScale; // 列名小数精度

    private boolean importUtil = false;

    private boolean importSql = false;

    private boolean importMath = false;

    // 数据库连接
    //informix
//    private static final String URL = "jdbc:informix-sqli://10.1.9.62:8005/ad_new:INFORMIXSERVER=adminsoc;DB_LOCALE=zh_cn.gb18030-2000;CLIENT_LOCALE=zh_cn.gb18030-2000;NEWCODESET=gb18030,gb18030-2000,5488";
//    private static final String NAME = "informix";
//    private static final String PASS = "informix";
//    private static final String DRIVERStr = "com.informix.jdbc.IfxDriver";
//    private static final String CONNECTTYPE = ":";
    //mysql
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/shs";
    private static final String NAME = "root";
    private static final String PASS = "wangyu1993";
    private static final String DRIVERStr = "com.mysql.jdbc.Driver";
    private static final String CONNECTTYPE = ".";
    // private static final String DRIVER ="oracle.jdbc.driver.OracleDriver";

    /**
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        CreateEntityFromDb t = new CreateEntityFromDb();
        t.tableToEntity("shs"+CONNECTTYPE, "admin", -1, "com.wondersgroup.gzsq.common.bo", true);
    }

    /**
     * 根据数据库表结构生成bo对象
     * 
     * @param tName 表名，系统会统一转换为大写
     * @param uuidFieldIndex uuid列所在的下标，没有主键或不是uuid的话，传N-1
     * @param packageName package名，不需要生成的话，传NULL
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void tableToEntity(String schemaName, String tName, int uuidFieldIndex, String packageName, boolean extendBaseBO)
            throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        tableName = tName.toLowerCase();
        // 数据连Connection获取
        Class.forName(DRIVERStr).newInstance();
        Connection conn = DriverManager.getConnection(URL, NAME, PASS);
        String strsql = "SELECT * FROM " + (schemaName != null ? (schemaName) : "") + tableName;// +" WHERE ROWNUM=1" 读一行记录;
        try {
            System.out.println(strsql);
            PreparedStatement pstmt = conn.prepareStatement(strsql);
            pstmt.executeQuery();
            ResultSetMetaData rsmd = pstmt.getMetaData();
            int size = rsmd.getColumnCount(); // 共有多少列
            fieldNames = new String[size];
            colNames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            colScale = new int[size];
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                rsmd.getCatalogName(i + 1);
                fieldNames[i] = rsmd.getColumnName(i + 1);
                colNames[i] = rsmd.getColumnName(i + 1).toLowerCase();
                colTypes[i] = rsmd.getColumnTypeName(i + 1).toLowerCase();
                colScale[i] = rsmd.getScale(i + 1);
                System.out.println(rsmd.getCatalogName(i + 1));
                if ("datetime".equals(colTypes[i]) || "date".equals(colTypes[i])) {
                    importUtil = true;
                }
                if ("image".equals(colTypes[i]) || "text".equals(colTypes[i])) {
                    importSql = true;
                }
                if (colScale[i] > 0) {
                    importMath = true;
                }
                colSizes[i] = rsmd.getPrecision(i + 1);
                
            }
            String content = parse(colNames, colTypes, colSizes, packageName, uuidFieldIndex, extendBaseBO);
            try {
                FileWriter fw = new FileWriter(initCap(fieldToColumn(tableName)) + ".java");
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析处理(生成实体类主体代码)
     * 
     * @param colNames
     * @param colTypes
     * @param colSizes
     * @param packageName package名，不需要生成的话，传NULL
     * @param uuidFieldIndex uuid列所在的下标，没有主键或不是uuid的话，传值-1
     * @return
     */
    private String parse(String[] colNames, String[] colTypes, int[] colSizes, String packageName, int uuidFieldIndex, boolean extendBaseBO) {
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(packageName)) {
            sb.append("package " + packageName + ";\r\n");
        }

        sb.append("\r\n");
        sb.append("import java.io.Serializable;\r\n");
        sb.append("import javax.persistence.Column;\r\n");
        sb.append("import javax.persistence.Entity;\r\n");
        sb.append("import javax.persistence.Table;\r\n");
        if (importUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (importSql) {
            sb.append("import java.sql.*;\r\n");
        }
        if (importMath) {
            sb.append("import java.math.*;\r\n");
        }
        if (uuidFieldIndex != -1) {
            sb.append("import javax.persistence.GeneratedValue;\r\n");
            sb.append("import javax.persistence.Id;\r\n");
            sb.append("import org.hibernate.annotations.GenericGenerator;\r\n");
        } else {
            sb.append("import javax.persistence.Id;\r\n");
        }
        if (extendBaseBO) {
            sb.append("import javax.persistence.Transient;\r\n");
            sb.append("import com.wondersgroup.esf.base.bo.BaseBO;\r\n");
        }
        sb.append("\r\n");

        // 增加表注释及处理列名
        processColnames(null);

        sb.append("@Entity\r\n");
        sb.append("@Table(name = \"" + tableName.toUpperCase() + "\")\r\n");
        sb.append("public class " + initCap(fieldToColumn(tableName)));
        if (extendBaseBO) {
            sb.append(" extends BaseBO");
        }
        sb.append(" implements Serializable {\r\n");

        // 生成属性
        processAllAttrs(sb);

        // 生成结构
        processConstructors(sb);

        // 生成方法和注解
        processAllMethod(sb, uuidFieldIndex);

        // 如果extends BaseBO，则需要增加getPrimaryKey方法
        if (extendBaseBO) {
            sb.append("\r\n");
            sb.append("\t@Override\r\n");
            sb.append("\t@Transient\r\n");
            sb.append("\tpublic Object getPrimaryKey() {\r\n");
            sb.append("\t\treturn " + colNames[uuidFieldIndex != -1 ? uuidFieldIndex : 0] + ";\r\n");
            sb.append("\t}\r\n");
        }

        sb.append("}\r\n");
        System.out.println(sb.toString());
        return sb.toString();

    }

    /**
     * 处理列名,把空格下划线'_'去掉,同时把下划线后的首字母大写 要是整个列在3个字符及以内,则去掉'_'后,不把"_"后首字母大写. 同时把数据库列名,列类型写到注释中以便查看,
     * 
     * @param sb
     */
    private void processColnames(StringBuffer sb) {
        if (sb == null) {
            sb = new StringBuffer("");
        }
        sb.append("\r\n/** " + tableName + "\r\n");
        String colsiz = "";
        String colsca = "";
        for (int i = 0; i < colNames.length; i++) {
            colsiz = colSizes[i] <= 0 ? "" : (colScale[i] <= 0 ? "(" + colSizes[i] + ")" : "(" + colSizes[i] + "," + colScale[i] + ")");
            sb.append("\t" + colNames[i].toUpperCase() + "	" + colTypes[i].toUpperCase() + colsiz + "\r\n");
            /*
             * char[] ch = colNames[i].toCharArray(); char c ='a'; if(ch.length>3){ for(int j=0;j <ch.length; j++){ c = ch[j]; if(c == '_'){ if (ch[j+1]>= 'a' && ch[j+1] <= 'z') {
             * ch[j+1]=(char) (ch[j+1]-32); } } } } String str = new String(ch);
             */
            colNames[i] = fieldToColumn(colNames[i]);
        }
        sb.append("*/\r\n");
    }

    /**
     * 生成所有的方法
     * 
     * @param sb
     * @param uuidFieldIndex uuid列所在的下标，没有主键或不是uuid的话，传值-1
     */
    private void processAllMethod(StringBuffer sb, int uuidFieldIndex) {
        sb.append("\t// Property accessors\r\n");
        for (int i = 0; i < colNames.length; i++) {
            if (i == uuidFieldIndex) {
                sb.append("\t@GenericGenerator(name = \"generator\", strategy = \"uuid.hex\")\r\n");
                sb.append("\t@Id\r\n");
                sb.append("\t@GeneratedValue(generator = \"generator\")\r\n");
            }
            if (uuidFieldIndex == -1 && i == 0) {
                sb.append("\t@Id\r\n");
            }
            sb.append("\t@Column(name = \"" + fieldNames[i] + "\")\r\n");
            sb.append("\tpublic " + oracleSqlType2JavaType(colTypes[i], colScale[i], colSizes[i]) + " get" + initCap(colNames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colNames[i] + ";\r\n");
            sb.append("\t}\r\n");

            sb.append("\tpublic void set" + initCap(colNames[i]) + "(" + oracleSqlType2JavaType(colTypes[i], colScale[i], colSizes[i]) + " " + colNames[i]
                    + "){\r\n");
            sb.append("\t\tthis." + colNames[i] + "=" + colNames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");
        }
    }

    /**
     * 解析输出属性
     * 
     * @return
     */
    private void processAllAttrs(StringBuffer sb) {
        sb.append("\t// Fields\r\n");
        sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
        for (int i = 0; i < colNames.length; i++) {
            sb.append("\tprivate " + oracleSqlType2JavaType(colTypes[i], colScale[i], colSizes[i]) + " " + colNames[i] + ";\r\n");
        }
        sb.append("\r\n");
    }

    /**
     * 解析输出结构
     * 
     * @return
     */
    private void processConstructors(StringBuffer sb) {
        sb.append("\t// Constructors\r\n");/** default constructor */
        sb.append("\t/** default constructor */\r\n");
        sb.append("\tpublic " + initCap(fieldToColumn(tableName)) + "() {\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
    }

    /**
     * 把输入字符串的首字母改成大写
     * 
     * @param str
     * @return
     */
    private String initCap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 把数据库的字段转换成属性（表或字段）
     * 
     * @param str
     * @return
     */
    private String fieldToColumn(String str) {
        char[] ch = str.toCharArray();
        char c = 'a';
        if (ch.length > 3) {
            for (int j = 0; j < ch.length; j++) {
                c = ch[j];
                if (c == '_') {
                    if (ch[j + 1] >= 'a' && ch[j + 1] <= 'z') {
                        ch[j + 1] = (char) (ch[j + 1] - 32);
                    }
                }
            }
        }
        return new String(ch).replaceAll("_", "");
    }

    /**
     * Oracle
     * 
     * @param sqlType
     * @param scale
     * @return
     */
    private String oracleSqlType2JavaType(String sqlType, int scale, int size) {
        if (sqlType.equals("integer")) {
            return "Integer";
        } else if (sqlType.equals("long")) {
            return "Long";
        } else if (sqlType.equals("float") || sqlType.equals("float precision") || sqlType.equals("double") || sqlType.equals("double precision")) {
            return "BigDecimal";
        } else if (sqlType.equals("number") || sqlType.equals("decimal") || sqlType.equals("numeric") || sqlType.equals("real")) {
            return scale == 0 ? (size < 10 ? "Integer" : "Long") : "BigDecimal";
        } else if (sqlType.equals("varchar") || sqlType.equals("varchar2") || sqlType.equals("char") || sqlType.equals("nvarchar") || sqlType.equals("nchar")) {
            return "String";
        } else if (sqlType.equals("datetime") || sqlType.equals("date") || sqlType.equals("timestamp")) {
            return "Date";
        }
        return null;
    }

}
