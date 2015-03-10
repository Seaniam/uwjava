package com.scg.persistent;

import com.scg.domain.*;
import com.scg.util.Address;
import com.scg.util.DateRange;
import com.scg.util.Name;
import com.scg.util.StateCode;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Responsible for providing a programmatic interface to store and access objects in the database.
 *
 * @author Sean Carberry
 * @version 7
 * @since 3/3/15
 */
public final class DbServer {

    private String dbUrl;
    private String username;
    private String password;
    private String driverClassName = "org.apache.derby.jdbc.ClientDriver";

    /**
     * Constructor.
     *
     * @param dbUrl The database URL.
     * @param username the database user name.
     * @param password the database password.
     */
    public DbServer(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * Add a client to the database.
     *
     * @param client the client to add.
     * @throws SQLException if any database operations fail.
     * @throws ClassNotFoundException if unable to load the JDBC driver.
     */
    public void addClient(ClientAccount client) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try{

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

//            stmt = conn.createStatement();

//            TODO: try with prepared statements
//            PreparedStatement ps = conn.prepareStaetments(SQLSTRING);
            String clientQuery = "INSERT INTO clients (name, street, city, state, postal_code, contact_last_name, contact_first_name, contact_middle_name) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(clientQuery);

            stmt.setString(1, client.getName().toString());
            stmt.setString(2, client.getAddress().getStreetNumber());
            stmt.setString(3, client.getAddress().getCity());
            stmt.setString(4, client.getAddress().getState().toString());
            stmt.setString(5, client.getAddress().getPostalCode());
            stmt.setString(6, client.getContact().getLastName());
            stmt.setString(7, client.getContact().getFirstName());
            stmt.setString(8, client.getContact().getMiddleName());

            stmt.executeUpdate();

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * Get all of the clients in the database.
     *
     * @return a list of all of the clients.
     * @throws SQLException if any datbase operations fail.
     */
    public List<ClientAccount> getClients() throws SQLException {

        List<ClientAccount> clients = new ArrayList<ClientAccount>();

        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;

        try{

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            stmt = conn.createStatement();

            String query = "SELECT name, street, city, state, postal_code, contact_last_name, contact_first_name, contact_middle_name " +
                           "FROM clients";

            rs = stmt.executeQuery(query.toString());

            while (rs.next()) {
                String accountName = rs.getString("name");
                Name contact = new Name(rs.getString("contact_last_name"), rs.getString("contact_first_name"), rs.getString("contact_middle_name"));
                StateCode sc = StateCode.valueOf(rs.getString("state"));
                Address address = new Address(rs.getString("street"), rs.getString("city"), sc, rs.getString("postal_code"));
                ClientAccount client = new ClientAccount(accountName, contact, address);
                clients.add(client);
            }

            rs.close();


        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return clients;
    }

    /**
     * Add a consultant to the database.
     *
     * @param consultant the consultant to add.
     * @throws SQLException if any database operations fail.
     */
    public void addConsultant(Consultant consultant) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try{

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            String consultantQuery = "INSERT INTO consultants (last_name, first_name, middle_name) " +
                                     "VALUES (?, ?, ?)";

            stmt = conn.prepareStatement(consultantQuery);

            stmt.setString(1, consultant.getName().getLastName());
            stmt.setString(2, consultant.getName().getFirstName());
            stmt.setString(3, consultant.getName().getMiddleName());

            stmt.executeUpdate();

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * Get all of the consultant in the database.
     *
     * @return a list of all of the consultants.
     * @throws SQLException if any datbase operations fail.
     */
    public List<Consultant> getConsultants() throws SQLException {

        List<Consultant> consultants = new ArrayList<Consultant>();

        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;

        try{

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            stmt = conn.createStatement();

            /* Select all consultants */
            String query = "SELECT last_name, first_name, middle_name " +
                           "FROM consultants";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Name consultantName = new Name(rs.getString("last_name"), rs.getString("first_name"), rs.getString("middle_name"));

                Consultant consultant = new Consultant(consultantName);
                consultants.add(consultant);
            }

            rs.close();

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return consultants;
    }

    /**
     * Add a timecard to the database.
     *
     * @param timeCard the timecard to add.
     * @throws SQLException if any database operations fail.
     */
    public void addTimeCard(TimeCard timeCard) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            int consultantId = getConsultantIdByName(timeCard.getConsultant());

            /* Insert time card */
            Date tcStartDate = new Date(timeCard.getWeekStartingDay().getTime());

            String timeCardQuery = "INSERT INTO timecards (consultant_id, start_date) " +
                                   "VALUES (?, ?)";

            stmt = conn.prepareStatement(timeCardQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, consultantId);
            stmt.setDate(2, tcStartDate);

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            int timecardId = 0;

            while (rs.next()) {
                timecardId = rs.getInt(1);
            }

            if (timecardId == 0) {
                throw new SQLDataException("Can't insert hours without a timecard ID.");
            }


            // Add consulting hours to billable and non-billable tables
            List<ConsultantTime> consultingHours = timeCard.getConsultingHours();

            for (ConsultantTime row : consultingHours) {
                if (row.isBillable()) {
                    addBillableHours(row, timecardId);
                } else {
                    addNonBillableHours(row, timecardId);
                }
            }

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * Adds billable hours based on a timecard ID.
     * @param time the consultant time line item from the invoice.
     * @param timeCardId the respective timecard ID that this time corresponds to.
     * @throws SQLException
     */
    public void addBillableHours(ConsultantTime time, int timeCardId) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            /* Insert billable hours */
            Date billableDate = new Date(time.getDate().getTime());

            String billableQuery = "INSERT INTO billable_hours (client_id, timecard_id, date, skill, hours) " +
                                   "VALUES ((SELECT DISTINCT id FROM clients WHERE name = ?), ?, ?, ?, ?)";

            stmt = conn.prepareStatement(billableQuery);

            stmt.setString(1, time.getAccount().getName());
            stmt.setInt(2, timeCardId);
            stmt.setDate(3, billableDate);
            stmt.setString(4, time.getSkillType().toString());
            stmt.setInt(5, time.getHours());

            stmt.executeUpdate();

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * Adds non-billable hours based on a timecard ID.
     * @param time the consultant time line item from the invoice.
     * @param timecardId the respective timecard ID that this time corresponds to.
     * @throws SQLException
     */
    public void addNonBillableHours(ConsultantTime time, int timecardId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            /* Insert non-billable hours */
            Date billableDate = new Date(time.getDate().getTime());

            String nonBillableQuery = "INSERT INTO non_billable_hours (account_name, timecard_id, date, hours) " +
                                      "VALUES (?, ?, ?, ?)";

            stmt = conn.prepareStatement(nonBillableQuery);

            stmt.setString(1, time.getAccount().toString());
            stmt.setInt(2, timecardId);
            stmt.setDate(3, billableDate);
            stmt.setInt(4, time.getHours());

            stmt.executeUpdate();

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

    }

    /**
     * Retrieves the consultant ID based on their name string.
     * @param consultant the consultant to get the ID of.
     * @return
     */
    public int getConsultantIdByName(Consultant consultant) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            // Obtain the consultant id, for the consultant (using the name fields)
            Name consultantName = consultant.getName();
            String consultantQuery = "SELECT id FROM consultants " +
                                     "WHERE last_name = ? AND first_name = ? AND middle_name = ?";

            stmt = conn.prepareStatement(consultantQuery);

            stmt.setString(1, consultantName.getLastName());
            stmt.setString(2, consultantName.getFirstName());
            stmt.setString(3, consultantName.getMiddleName());

            ResultSet consultantResults = stmt.executeQuery();

            int consultantId = -1;
            while (consultantResults.next()) {
                consultantId = consultantResults.getInt("id");
            }

            if (consultantId == -1) {
                throw new SQLDataException("Can't insert a timecard without a consultant ID.");
            }

            consultantResults.close();

            return consultantId;

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return -1;
    }

    /**
     * Get clients monthly invoice.
     *
     * @param client the client to obtain the invoice line items for.
     * @param month the month of the invoice.
     * @param year the year of the invoice.
     * @return the clients invoice for the month.
     * @throws SQLException if any database operations fail.
     */
    public Invoice getInvoice(ClientAccount client, int month, int year) throws SQLException {

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Invoice invoice = null;

        try{

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);
            invoice = new Invoice(client, month, year);

            DateRange dr = new DateRange(month, year);
            java.sql.Date startDate = new java.sql.Date(dr.getStartDate().getTime());;
            java.sql.Date endDate = new java.sql.Date(dr.getEndDate().getTime());;

            /* Select invoice items */
            String tcQuery = "SELECT b.date, c.last_name, c.first_name, c.middle_name, b.skill, s.rate, b.hours " +
                             "FROM billable_hours b, consultants c, skills s, timecards t " +
                             "WHERE b.client_id = (SELECT DISTINCT id FROM clients WHERE name = ?) " +
                             "AND b.skill = s.name AND b.timecard_id = t.id AND c.id = t.consultant_id " +
                             "AND b.date >= ? " +
                             "AND b.date <= ?";

            stmt = conn.prepareStatement(tcQuery);

            stmt.setString(1, client.getName());
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);

            rs = stmt.executeQuery();

            while (rs.next()) {
                // consultant
                Name consultantName = new Name(rs.getString("last_name"), rs.getString("first_name"), rs.getString("middle_name"));
                Consultant consultant = new Consultant(consultantName);

                // date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("date"));
                java.util.Date liDate = calendar.getTime();

                // skill
                Skill liSkill = Skill.valueOf(rs.getString("skill"));

                // hours
                int liHours = rs.getInt("hours");

                InvoiceLineItem li = new InvoiceLineItem(liDate, consultant, liSkill, liHours);
                invoice.addLineItem(li);
            }

            rs.close();

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
            } try {
                if(conn!=null) {
                    conn.close();
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return invoice;
    }


}
