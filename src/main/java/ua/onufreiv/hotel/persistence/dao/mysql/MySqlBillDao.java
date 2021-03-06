package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.Bill;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.BillDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.BillMapper;

import java.sql.Connection;
import java.util.List;

/**
 * DAO for {@link Bill} entity and MySql database
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/10/17.
 */
public class MySqlBillDao implements BillDao {
    private static final String TABLE_NAME = "bill";
    private static final String COLUMN_ID_NAME = "idBill";
    private static final String COLUMN_CREATION_DATE_TIME_NAME = "creationDateTime";
    private static final String COLUMN_BOOK_REQUEST_FK_NAME = "bookRequestFK";
    private static final String COLUMN_ROOM_FK_NAME = "roomFK";
    private static final String COLUMN_PRICE_NAME = "price";

    private static MySqlBillDao instance;
    private QueryBuilder<Bill> queryBuilder;

    private MySqlBillDao() {
        queryBuilder = new QueryBuilder<>(TABLE_NAME);
    }

    public static synchronized MySqlBillDao getInstance() {
        if (instance == null) {
            instance = new MySqlBillDao();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insert(Bill bill) {
        Connection connection = ConnectionManager.getConnection();
        int id = queryBuilder.insert()
                .value(COLUMN_CREATION_DATE_TIME_NAME, bill.getCreationDate())
                .value(COLUMN_BOOK_REQUEST_FK_NAME, bill.getBookRequestId())
                .value(COLUMN_ROOM_FK_NAME, bill.getRoomId())
                .value(COLUMN_PRICE_NAME, bill.getTotalPrice())
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
    public Bill find(int id) {
        Connection connection = ConnectionManager.getConnection();
        Bill bill = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
                .executeQueryForObject(connection, new BillMapper());
        ConnectionManager.closeConnection(connection);
        return bill;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bill> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<Bill> bills = queryBuilder.select()
                .selectAll(connection, new BillMapper());
        ConnectionManager.closeConnection(connection);
        return bills;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Bill bill) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.update()
                .set(COLUMN_CREATION_DATE_TIME_NAME, bill.getCreationDate())
                .set(COLUMN_BOOK_REQUEST_FK_NAME, bill.getBookRequestId())
                .set(COLUMN_ROOM_FK_NAME, bill.getRoomId())
                .set(COLUMN_PRICE_NAME, bill.getTotalPrice())
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(bill.getId())
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bill findByBookRequestId(int id) {
        Connection connection = ConnectionManager.getConnection();
        Bill bill = queryBuilder.select()
                .where()
                .column(COLUMN_BOOK_REQUEST_FK_NAME).isEqualTo(id)
                .executeQueryForObject(connection, new BillMapper());
        ConnectionManager.closeConnection(connection);
        return bill;
    }
}
