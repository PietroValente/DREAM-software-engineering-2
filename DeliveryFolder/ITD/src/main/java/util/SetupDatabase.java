package util;

import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.ranking.ManageRankingBean;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

//class that manages the creation of the database when it does not exist
public class SetupDatabase {

	@SuppressWarnings("unused")
	public static void setup() {
		Connection c = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			System.out.println("DATABASE ALREADY EXIST");
			return;
		} catch (Exception e) {
			try {
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", VARIABLES.databaseUser,
						VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				statement.executeUpdate("CREATE DATABASE DREAM;");
				c.close();
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream", VARIABLES.databaseUser,
						VARIABLES.databasePassword);
				statement = c.createStatement();
				
				statement.executeUpdate("CREATE TABLE person("
						+ "	id BIGINT PRIMARY KEY," 
						+ "	firstname VARCHAR(40) NOT NULL,"
						+ "	lastname VARCHAR(40) NOT NULL," 
						+ "	category VARCHAR(40) NOT NULL,"
						+ "	password VARCHAR(40) NOT NULL," 
						+ "	username VARCHAR(40),"
						+ "	province VARCHAR(40)," 
						+ "CHECK (id>=1000000000 AND id<= 9999999999)" + ");");
				
				statement.executeUpdate("CREATE TABLE code("
						+ "code VARCHAR(10) NOT NULL,"
						+ "type VARCHAR(20) NOT NULL,"
						+ "PRIMARY KEY (code)" + ");");
				
				statement.executeUpdate("CREATE TABLE discussion("
						+ "id SERIAL PRIMARY KEY,"
						+ "author BIGINT NOT NULL,"
						+ "subject VARCHAR(200) NOT NULL,"
						+ "description VARCHAR(500) NOT NULL,"
						+ "timestamp timestamp NOT NULL DEFAULT NOW(),"
						+ "FOREIGN KEY (author) REFERENCES person(id));");
				
				statement.executeUpdate("CREATE TABLE comment("
						+ "id SERIAL PRIMARY KEY,"
						+ "author BIGINT NOT NULL,"
						+ "discussion INT NOT NULL,"
						+ "text VARCHAR(500) NOT NULL,"
						+ "timestamp timestamp NOT NULL DEFAULT NOW(),"
						+ "FOREIGN KEY (discussion) REFERENCES discussion(id),"
						+ "FOREIGN KEY (author) REFERENCES person(id));");
				
				statement.executeUpdate("CREATE TABLE farm(" 
						+ "	id BIGINT PRIMARY KEY,"
						+ "	address VARCHAR(40) NOT NULL," 
						+ "	phone VARCHAR(15) NOT NULL," 
						+ " owner BIGINT NOT NULL,"
						+ " agronomist BIGINT NOT NULL," 
						+ "	water INT DEFAULT 0,"
						+ "FOREIGN KEY (owner) REFERENCES person(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE,"
						+ "FOREIGN KEY (agronomist) REFERENCES person(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE,"
						+ "CHECK (water>=0)" + ");");
				
				statement.executeUpdate("CREATE TABLE product(" 
						+ "id SERIAL PRIMARY KEY," 
						+ "name VARCHAR(40) NOT NULL,"
						+ "growing_time SMALLINT NOT NULL," 
						+ "CHECK (id>0 and growing_time>0)" + ");");
				
				statement.executeUpdate("CREATE TABLE land(" 
						+ "id BIGSERIAL," 
						+ "date DATE,"
						+ "dimension INT NOT NULL," 
						+ "farm BIGINT NOT NULL,"
						+ "empty BOOLEAN DEFAULT TRUE,"
						+ "humidity INT DEFAULT NULL," 
						+ "host INT DEFAULT NULL," 
						+ "PRIMARY KEY (id,date),"
						+ "FOREIGN KEY (farm) REFERENCES farm(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE,"
						+ "FOREIGN KEY (host) REFERENCES product(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE,"
						+ "CHECK (id>0 and dimension>0 and humidity>0 and humidity<100)" + ");");
				
				statement.executeUpdate("CREATE TABLE crop(" 
						+ "id BIGSERIAL NOT NULL," 
						+ "date DATE NOT NULL,"
						+ "product INT NOT NULL," 
						+ "quantity INT NOT NULL," 
						+ "farm BIGINT NOT NULL,"
						+ "score INT NOT NULL,"
						+ "PRIMARY KEY (id),"
						+ "FOREIGN KEY (product) REFERENCES product(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE,"
						+ "FOREIGN KEY (farm) REFERENCES farm(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE,"
						+ "CHECK (quantity>0)" + ");");
				
				statement.executeUpdate("CREATE TABLE sessionID(" 
						+ "id BIGINT PRIMARY KEY," 
						+ "farmer BIGINT NOT NULL,"
						+ "timestamp timestamp NOT NULL DEFAULT NOW()" + ");");
				
				statement.executeUpdate("CREATE TABLE ranking(" 
						+ "farm BIGINT NOT NULL," 
						+ "place INT NOT NULL,"
						+ "score INT NOT NULL," 
						+ "PRIMARY KEY (farm),"
						+ "FOREIGN KEY (farm) REFERENCES farm(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE" + ");");
				
				statement.executeUpdate("CREATE TABLE help_request(" 
						+ "id BIGINT NOT NULL,"
						+ "farm BIGINT NOT NULL," 
						+ "agronomist BIGINT NOT NULL,"
						+ "date DATE NOT NULL,"
						+ "subject VARCHAR(200) NOT NULL,"
						+ "description VARCHAR(500) NOT NULL,"
						+ "PRIMARY KEY (id),"
						+ "FOREIGN KEY (farm) REFERENCES farm(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE,"
						+ "FOREIGN KEY (agronomist) REFERENCES person(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE" + ");");
			
				statement.executeUpdate("CREATE TABLE report("
						+ "id BIGSERIAL NOT NULL,"
						+ "farm BIGINT NOT NULL," 
						+ "agronomist BIGINT NOT NULL,"
						+ "date DATE NOT NULL,"
						+ "advice VARCHAR(150) NOT NULL,"
						+ "description VARCHAR(150) NOT NULL,"
						+ "PRIMARY KEY (id),"
						+ "FOREIGN KEY (farm) REFERENCES farm(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE,"
						+ "FOREIGN KEY (agronomist) REFERENCES person(id)" 
						+ "ON DELETE NO ACTION ON UPDATE CASCADE" + ");");
			
				statement.executeUpdate("CREATE FUNCTION sessionID_delete_old_rows() RETURNS trigger"
						+ "    LANGUAGE plpgsql" + "    AS $$" + "BEGIN"
						+ "  DELETE FROM sessionID WHERE timestamp < NOW() - INTERVAL '60 minute';" + "  RETURN NEW;"
						+ "END;" + "$$;");
				
				statement.executeUpdate("CREATE TRIGGER sessionID_delete_old_rows_trigger"
						+ "    AFTER INSERT ON sessionID" + "    EXECUTE PROCEDURE sessionID_delete_old_rows();");
				
				try {
					// INSERT CODES
					BufferedReader reader = new BufferedReader(new FileReader(VARIABLES.path + "codes.txt"));
					String line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("\\s+");
						statement.executeUpdate("INSERT into code values('" + splited[0] + "','"+ splited[1] +"');");
						line = reader.readLine();
					}
					reader.close();
					
					// INSERT FARMERS
					reader = new BufferedReader(new FileReader(VARIABLES.path + "farmer.txt"));
					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("\\s+");
						statement.executeUpdate(
								"INSERT into person values(" + Long.parseLong(splited[0]) + ",'" + splited[1] + "','"
										+ splited[2] + "','Farmer','" + splited[3] + "','" + splited[4] + "');");
						line = reader.readLine();
					}
					reader.close();
					
					// INSERT AGRONOMIST
					reader = new BufferedReader(new FileReader(VARIABLES.path + "agronomist.txt"));
					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("\\s+");
						statement.executeUpdate("INSERT into person values(" + Long.parseLong(splited[0]) + ",'"
								+ splited[1] + "','" + splited[2] + "','Agronomist','" + splited[3] + "',NULL,'"
								+ splited[4] + "');");
						line = reader.readLine();
					}
					reader.close();
					
					// INSERT POLICY MAKER
					reader = new BufferedReader(new FileReader(VARIABLES.path + "policy_maker.txt"));
					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("\\s+");
						statement.executeUpdate(
								"INSERT into person values(" + Long.parseLong(splited[0]) + ",'" + splited[1] + "','"
										+ splited[2] + "','Policy maker','" + splited[3] + "',NULL, NULL);");
						line = reader.readLine();
					}
					reader.close();
					
					// INSERT FARM
					reader = new BufferedReader(new FileReader(VARIABLES.path + "farm.txt"));
					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("\\s+");
						statement.executeUpdate("INSERT into farm values(" + Long.parseLong(splited[0]) + ",'"
								+ splited[1] + "','" + splited[2] + "'," + Long.parseLong(splited[3]) + ","
								+ Long.parseLong(splited[4]) + "," + Integer.parseInt(splited[5]) + ");");
						ResultSet rs = statement.executeQuery("SELECT province from person where id="+Long.parseLong(splited[4]));
						rs.next();
						statement.executeUpdate("UPDATE person SET province='"+rs.getString("province")+"' WHERE id="+Long.parseLong(splited[3])+";");
						statement.executeUpdate("INSERT into Ranking values(" + Long.parseLong(splited[0]) + ",'" + 0 + "','" + 0 + "');");
						ManageRankingBean manageRankingBean = new ManageRankingBean();
						manageRankingBean.updateRanking();
						line = reader.readLine();
					}
					reader.close();
					
					// INSERT PRODUCT
					reader = new BufferedReader(new FileReader(VARIABLES.path + "product.txt"));
					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("\\s+");
						statement.executeUpdate("INSERT into product values(" + Integer.parseInt(splited[0]) + ",'"
								+ splited[1] + "'," + Integer.parseInt(splited[2]) + ");");
						line = reader.readLine();
					}
					reader.close();
					
					// INSERT REPORTS
					reader = new BufferedReader(new FileReader(VARIABLES.path + "report.txt"));
					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("//");
						statement.executeUpdate("INSERT into report (farm,agronomist,date,advice,description) values(" + Long.parseLong(splited[0]) + ","+ Long.parseLong(splited[1]) + ",'" + splited[2] + "','" + splited[3] + "','" + splited[4] + "');");
						line = reader.readLine();
					}
					reader.close();
					
					// INSERT DISCUSSIONS
					reader = new BufferedReader(new FileReader(VARIABLES.path + "discussion.txt"));
					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("//");
						statement.executeUpdate("INSERT into discussion (author,subject,description) values(" + Long.parseLong(splited[0]) + ",'"+ splited[1] + "','" + splited[2] + "');");
						line = reader.readLine();
					}
					reader.close();
					
					// INSERT COMMENTS
					reader = new BufferedReader(new FileReader(VARIABLES.path + "comment.txt"));
					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("//");
						statement.executeUpdate("INSERT into comment (author,discussion,text) values(" + Long.parseLong(splited[0]) + ","+ Integer.parseInt(splited[1]) + ",'" + splited[2] + "');");
						line = reader.readLine();
					}
					reader.close();
					
					// START LAND PROCESS, LANDS INSERTION
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

					LocalDate begin_day = LocalDate.of(2022, 1, 1);
					LocalDateTime now = LocalDateTime.now();
					String t = dtf.format(now);
					LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)),
							Integer.parseInt(t.substring(5, 7)), Integer.parseInt(t.substring(8, 10)));
					long daysBetween = ChronoUnit.DAYS.between(begin_day, today);
					reader = new BufferedReader(new FileReader(VARIABLES.path + "land.txt"));

					line = reader.readLine();
					while (line != null) {
						String[] splited = line.split("\\s+");
						int end = (int) Math.floor(Math.random() * (daysBetween - 5 + 1) + 5);
						int begin = (int) Math.floor(Math.random() * (end + 1));
						LocalDate day = begin_day.plusDays(begin);
						LocalDate day_before = begin_day;
						LocalDate last_day = begin_day.plusDays(end);
						
						while (!(day_before.equals(day))) {
							statement.executeUpdate("INSERT into land values(" + Long.parseLong(splited[0]) + ",'"
									+ day_before.toString() + "'," + Integer.parseInt(splited[1]) + ","
									+ Long.parseLong(splited[2]) + ");");
							day_before = day_before.plusDays(1);
						}
						while (!(day.equals(last_day))) {
							int humidity = (int) Math.floor(Math.random() * (70 - 30 + 1) + 30);
							statement.executeUpdate(
									"INSERT into land values(" + Long.parseLong(splited[0]) + ",'" + day.toString()
											+ "'," + Integer.parseInt(splited[1]) + "," + Long.parseLong(splited[2])
											+ ",FALSE," + humidity + "," + Integer.parseInt(splited[3]) + ");");
							day = day.plusDays(1);
							if (day.equals(last_day)) {
								int quantity = (int) Math.floor(Math.random() * (15000 - 7000 + 1) + 7000);
								int score = quantity/Integer.parseInt(splited[1]);
								statement.executeUpdate("INSERT into crop (date,product,quantity,farm,score) values('"
										+ day.minusDays(1).toString() + "'," + Integer.parseInt(splited[3]) + ","
										+ quantity +","
										+Long.parseLong(splited[2])+ ","+ score +");");
								EntityManager manager = Persistence.createEntityManagerFactory("first_unit").createEntityManager();
								Transaction transaction = null;
								try (Session session2 = HibernateUtil.getSessionFactory().openSession()) {
									transaction = session2.beginTransaction();
									Query q = manager.createNativeQuery("SELECT score from ranking where farm =" + Long.parseLong(splited[2]));
									transaction.commit();
									session2.close();
									score = score + Integer.parseInt(q.getSingleResult().toString());
									}
								statement.executeUpdate("UPDATE ranking SET score="+score+" WHERE farm="+Long.parseLong(splited[2]));
							}
						}
						while (!(day.equals(today.plusDays(1)))) {
							statement.executeUpdate("INSERT into land values(" + Long.parseLong(splited[0]) + ",'"
									+ day.toString() + "'," + Integer.parseInt(splited[1]) + ","
									+ Long.parseLong(splited[2]) + ");");
							day = day.plusDays(1);
						}
						line = reader.readLine();
					}
					reader.close();
					ManageRankingBean manageRankingBean = new ManageRankingBean();
					manageRankingBean.updateRanking();

					System.out.println("DATABASE STORED");
					c.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}