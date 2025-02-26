package com.uninadelivery.model.dao;

import com.uninadelivery.model.entities.Cliente;
import com.uninadelivery.model.dbconnection.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final Connection conn;

    public ClienteDAO() throws SQLException {
        this.conn = DBConnection.getDBconnection().getConnection();
    }

    public void createCliente(Cliente cliente) {
        String query = "INSERT INTO cliente (email_cliente, n_telefono_cliente, nome, cognome, data_nascita, indirizzo_predefinito) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, cliente.getEmailCliente());
            ps.setString(2, cliente.getnTelefonoCliente());
            ps.setString(3, cliente.getNome());
            ps.setString(4, cliente.getCognome());
            ps.setDate(5, Date.valueOf(cliente.getDataNascita())); // Usa LocalDate -> Date SQL
            ps.setString(6, cliente.getIndirizzoPredefinito());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> getAllClienti() {
        List<Cliente> clienti = new ArrayList<>();
        String query = "SELECT * FROM cliente";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String email = rs.getString("email_cliente");
                String telefono = rs.getString("n_telefono_cliente");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                LocalDate dataNascita = rs.getDate("data_nascita").toLocalDate();
                String indirizzo = rs.getString("indirizzo_predefinito");

                Cliente cliente = new Cliente(email, telefono, nome, cognome, dataNascita, indirizzo);
                clienti.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienti;
    }
}