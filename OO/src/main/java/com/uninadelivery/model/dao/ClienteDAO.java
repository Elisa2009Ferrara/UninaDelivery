package com.uninadelivery.model.dao;

import com.uninadelivery.model.dbconnection.DBConnection;
import java.sql.*;
import java.util.logging.Logger;

public class ClienteDAO {
    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());
    private final Connection conn;

    public ClienteDAO() throws SQLException {
        this.conn = DBConnection.getDBconnection().getConnection();
    }
}