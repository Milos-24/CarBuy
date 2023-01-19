package models;

import app.ConnectionPool;

import java.sql.*;

public class Model {
    private String modelName;
    private BrandName brandName;

    public Model(int id) {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps=c.prepareStatement("select * from model where idModel=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            System.out.println(id);
            while (rs.next())
            {
                this.setModelName(rs.getString(3));
                this.setBrandName(new BrandName(rs.getInt(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public BrandName getBrandName() {
        return brandName;
    }

    public void setBrandName(BrandName brandName) {
        this.brandName = brandName;
    }
}
