package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.RoomTypeDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.SqlQuerySelect;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.RoomTypeMapper;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.SimpleStringMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *DAO for {@link RoomType} entity and MySql database
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/4/17.
 */
public class MySqlRoomTypeDao implements RoomTypeDao {
    private static final String TABLE_NAME = "room_type";
    private static final String COLUMN_ID_NAME = "idRoomType";
    private static final String COLUMN_TYPE_NAME = "type";

    private static MySqlRoomTypeDao instance;
    private final QueryBuilder<RoomType> queryBuilder;

    private MySqlRoomTypeDao() {
        queryBuilder = new QueryBuilder<>(TABLE_NAME);
    }

    public static synchronized MySqlRoomTypeDao getInstance() {
        if (instance == null) {
            instance = new MySqlRoomTypeDao();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insert(RoomType roomType) {
        Connection connection = ConnectionManager.getConnection();
        int id = queryBuilder.insert()
                .value(COLUMN_TYPE_NAME, roomType.getType())
                .execute(connection);
        ConnectionManager.closeConnection(connection);
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.delete()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoomType find(int id) {
        Connection connection = ConnectionManager.getConnection();
        RoomType RoomType = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
                .executeQueryForObject(connection, new RoomTypeMapper());
        ConnectionManager.closeConnection(connection);
        return RoomType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoomType> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<RoomType> roomTypes = queryBuilder.select()
                .selectAll(connection, new RoomTypeMapper());
        ConnectionManager.closeConnection(connection);
        return roomTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(RoomType roomType) {
        Connection connection = ConnectionManager.getConnection();
        boolean update = queryBuilder.update()
                .set(COLUMN_TYPE_NAME, roomType.getType())
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(roomType.getId())
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return update;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> findAllRoomTypesString() {
        Connection connection = ConnectionManager.getConnection();
        List<String> types = new SqlQuerySelect<String>(TABLE_NAME)
                .columns(COLUMN_TYPE_NAME)
                .selectAll(connection, new SimpleStringMapper());
        ConnectionManager.closeConnection(connection);
        return types;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, String> findAllAsMap() {
        List<RoomType> roomTypes = findAll();

        if (roomTypes == null) {
            return null;
        }

        Map<Integer, String> idTypeTitleMap = new HashMap<>(roomTypes.size());
        for (RoomType roomType : roomTypes) {
            idTypeTitleMap.put(roomType.getId(), roomType.getType());
        }
        return idTypeTitleMap;
    }
}
