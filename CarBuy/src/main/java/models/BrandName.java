package models;

import app.ConnectionPool;

import java.sql.*;

public class BrandName {
    private String brandName;

    public BrandName(int id) {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps=c.prepareStatement("select * from brandname where idBrandName=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next())
            {
                this.setBrandName(rs.getString(2));
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

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }
}
