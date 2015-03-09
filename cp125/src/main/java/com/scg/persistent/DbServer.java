package com.scg.persistent;

import com.scg.domain.*;
import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Responsible for providing a programmatic interface to store and access objects in the database.
 *
 * @author Sean Carberry
 * @version 7
 * @since 3/3/15
 * TODO: Maybe there is a more efficient way of doing these connections (abstract to a method?)
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
        Statement stmt = null;

        try{

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            stmt = conn.createStatement();

            StringBuilder clientQuery = new StringBuilder();
            clientQuery.append("INSERT INTO clients (name, street, city, state, postal_code, contact_last_name, contact_first_name, contact_middle_name) ");
            clientQuery.append("VALUES (");
            clientQuery.append("'" + client.getName().toString() + "', ");
            clientQuery.append("'" + client.getAddress().getStreetNumber() + "', ");
            clientQuery.append("'" + client.getAddress().getCity() + "', ");
            clientQuery.append("'" + client.getAddress().getState() + "', ");
            clientQuery.append("'" + client.getAddress().getPostalCode() + "', ");
            clientQuery.append("'" + client.getContact().getLastName() + "', ");
            clientQuery.append("'" + client.getContact().getFirstName() + "', ");
            clientQuery.append("'" + client.getContact().getMiddleName() + "'");
            clientQuery.append(")");

            stmt.executeUpdate(clientQuery.toString());

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

            StringBuilder query = new StringBuilder();

            query.append("SELECT name, street, city, state, postal_code, contact_last_name, contact_first_name, contact_middle_name ");
            query.append("FROM clients");

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
        Statement stmt = null;

        try{

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            stmt = conn.createStatement();

            StringBuilder consultantQuery = new StringBuilder();

            consultantQuery.append("INSERT INTO consultants (last_name, first_name, middle_name) ");
            consultantQuery.append("VALUES (");
            consultantQuery.append("'" + consultant.getName().getLastName() + "', ");
            consultantQuery.append("'" + consultant.getName().getFirstName() + "', ");
            consultantQuery.append("'" + consultant.getName().getMiddleName() + "'");
            consultantQuery.append(")");

            stmt.executeUpdate(consultantQuery.toString());

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

            StringBuilder query = new StringBuilder();

            /* Select all consultants */
            query.append("SELECT last_name, first_name, middle_name ");
            query.append("FROM consultants");

            rs = stmt.executeQuery(query.toString());

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
        Statement stmt = null;

        try {

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            int consultantId = getConsultantIdByName(timeCard.getConsultant());

            /* Insert time card */
            String tcStartDate = new SimpleDateFormat("MM/dd/yyyy").format(timeCard.getWeekStartingDay()).toString();
            stmt = conn.createStatement();
            StringBuilder timeCardQuery = new StringBuilder();

            timeCardQuery.append("INSERT INTO timecards (consultant_id, start_date) ");
            timeCardQuery.append("VALUES (");
            timeCardQuery.append(consultantId + ", ");
            timeCardQuery.append("'" + tcStartDate +"'");
            timeCardQuery.append(")");

            stmt.executeUpdate(timeCardQuery.toString(), Statement.RETURN_GENERATED_KEYS);

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
        Statement stmt = null;

        try {

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            /* Insert billable hours */
            String formattedBillableDate = new SimpleDateFormat("MM/dd/yyyy").format(time.getDate()).toString();
            stmt = conn.createStatement();
            StringBuilder billable = new StringBuilder();

            // VALUES ((SELECT DISTINCT id FROM clients WHERE name = 'Acme Industries'), 3, '2005/03/12', 'Software Engineer', 8);
            billable.append("INSERT INTO billable_hours (client_id, timecard_id, date, skill, hours) ");
            billable.append("VALUES (");
            billable.append("(SELECT DISTINCT id FROM clients WHERE name = '" + time.getAccount().getName() + "'), ");
            billable.append(timeCardId + ", ");
            billable.append("'" + formattedBillableDate + "', ");
            billable.append("'" + time.getSkillType() + "', ");
            billable.append(time.getHours());
            billable.append(")");

            stmt.executeUpdate(billable.toString());

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
        ResultSet rs = null;
        Statement stmt = null;

        try {

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            /* Insert non-billable hours */
            stmt = conn.createStatement();
            StringBuilder nonBillable = new StringBuilder();
            String formattedBillableDate = new SimpleDateFormat("MM/dd/yyyy").format(time.getDate()).toString();

//            VALUES ('VACATION', 1, '2005/03/13', 8);
            nonBillable.append("INSERT INTO non_billable_hours (account_name, timecard_id, date, hours) ");
            nonBillable.append("VALUES (");
            nonBillable.append("'" + time.getAccount() + "', ");
            nonBillable.append(timecardId + ", ");
            nonBillable.append("'" + formattedBillableDate + "', ");
            nonBillable.append(time.getHours());
            nonBillable.append(")");

            stmt.executeUpdate(nonBillable.toString());

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
        ResultSet rs = null;
        Statement stmt = null;

        try {

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);

            // Obtain the consultant id, for the consultant (using the name fields)
            stmt = conn.createStatement();
            ResultSet consultantResults = stmt.executeQuery("SELECT id FROM consultants WHERE " +
                                                            "last_name = '" + consultant.getName().getLastName() + "' AND " +
                                                            "first_name = '" + consultant.getName().getFirstName() + "' AND " +
                                                            "middle_name = '" + consultant.getName().getMiddleName() + "'");

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
        Statement stmt = null;
        Invoice invoice = null;

        try{

            Class.forName(driverClassName);
            conn = DriverManager.getConnection(dbUrl, username, password);
            invoice = new Invoice(client, month, year);

            stmt = conn.createStatement();

            StringBuilder query = new StringBuilder();

            /* Select invoice items */

            /* BILLABLE */
            query.append("SELECT b.date, c.last_name, c.first_name, c.middle_name, b.skill, s.rate, b.hours ");
            query.append("FROM billable_hours b, consultants c, skills s, timecards t ");
            query.append("WHERE b.client_id = (SELECT DISTINCT id ");
            query.append("FROM clients ");
            query.append("WHERE name = '" + client.getName() + "') ");
            query.append("AND b.skill = s.name ");
            query.append("AND b.timecard_id = t.id ");
            query.append("AND c.id = t.consultant_id ");
//            TODO: work out how to set date params in query
//            query.append("AND b.date >= '" + year + "-" + mo + "-01'");
//            query.append("AND b.date <= '" + year + "-" + mo + "-31'");

            rs = stmt.executeQuery(query.toString());

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

            /* NON-BILLABLE */
//            TODO: Figure out how to get the non-billable line-items for the invoice. The query below is flawed.
//            query = new StringBuilder();
//
//            /* Select invoice items */
//            query.append("SELECT b.date, b.account_name, s.rate, b.hours ");
//            query.append("FROM non_billable_hours b, skills s, timecards t ");
//            query.append("WHERE b.account_name = s.name ");
//            query.append("AND b.timecard_id = t.id");
////            query.append("AND b.date >= '" + year + "-" + mo + "-01'");
////            query.append("AND b.date <= '" + year + "-" + mo + "-31'");
//
//            rs = stmt.executeQuery(query.toString());
//
//            while (rs.next()) {
//                // consultant
//                Name consultantName = new Name(rs.getString("last_name"), rs.getString("first_name"), rs.getString("middle_name"));
//                Consultant consultant = new Consultant(consultantName);
//
//                // date
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(rs.getDate("date"));
//                calendar = new GregorianCalendar();
//                java.util.Date liDate = calendar.getTime();
//
//                // skill
//                Skill liSkill = Skill.valueOf(rs.getString("skill"));
//
//                // hours
//                int liHours = rs.getInt("hours");
//
//                InvoiceLineItem li = new InvoiceLineItem(liDate, consultant, liSkill, liHours);
//                invoice.addLineItem(li);
//            }
//
//            rs.close();

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
