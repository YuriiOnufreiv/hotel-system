package ua.onufreiv.hotel.persistence.dao.mysql;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IPasswordDao;
import ua.onufreiv.hotel.persistence.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlPasswordDao implements IPasswordDao {
    private final static Logger logger = Logger.getLogger(MySqlPasswordDao.class);

    private static MySqlPasswordDao instance;

    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM PASSWORD WHERE idPassword = ?";
    private static final String QUERY_INSERT = "INSERT INTO PASSWORD (pwdhash) VALUES (?)";

    private MySqlPasswordDao() {
    }

    public static synchronized MySqlPasswordDao getInstance() {
        if (instance == null) {
            instance = new MySqlPasswordDao();
        }
        return instance;
    }

    @Override
    public int insert(PasswordHash passwordHash) {
        Connection connection = ConnectionManager.getConnection();
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            id = jdbcQuery.insert(connection, QUERY_INSERT,
                    passwordHash.getPwdHash());
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public PasswordHash find(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                PasswordHash passwordHash = DtoMapper.ResultSet.toPasswordHash(rs);
                return passwordHash;
            }
        } catch (SQLException e) {
            logger.error("Failed to find password hash by id", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return null;
    }

    @Override
    public List<PasswordHash> findAll() {
        return null;
    }

    @Override
    public boolean update(PasswordHash passwordHash) {
        return false;
    }
}
