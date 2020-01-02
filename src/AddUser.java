import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AddUser extends JFrame implements ActionListener {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; 
	static final String DB_URL = "jdbc:mysql://localhost/Assignmentdb?autoReconnect=true&useSSL=false";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	static ResultSet rs = null;
	static Connection conn = null;
	Statement stmt = null;
	static PreparedStatement statement = null;
	static String sql;
 
    // Components of the UI
    private Container c;
    private JLabel title,name,email,mno,city,userstatus,res;
    private JTextField tname,temail,tmno,tcity;
    private JRadioButton active,inactive;
    private ButtonGroup usergrp;
    private JButton add,update,delete;
    private static JTextArea tout;
    static String data,data1;
    
    //log4j
    static Logger log = Logger.getLogger(AddUser.class);
    
	public static void main(String[] args)throws IOException,SQLException{ 
		
		  AddUser f = new AddUser();   
		
	   }

	public AddUser(){
        setTitle("User Details");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
 
        c = getContentPane();
        c.setLayout(null);
 
        title = new JLabel("Add User");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);
 
        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        c.add(name);
 
        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(200, 100);
        c.add(tname);
       
        email = new JLabel("Email");
        email.setFont(new Font("Arial", Font.PLAIN, 20));
        email.setSize(100, 20);
        email.setLocation(100, 150);
        c.add(email);
 
        temail = new JTextField();
        temail.setFont(new Font("Arial", Font.PLAIN, 15));
        temail.setSize(190, 20);
        temail.setLocation(200, 150);
        c.add(temail);
 
        mno = new JLabel("Mobile");
        mno.setFont(new Font("Arial", Font.PLAIN, 20));
        mno.setSize(100, 20);
        mno.setLocation(100, 200);
        c.add(mno);
 
        tmno = new JTextField();
        tmno.setFont(new Font("Arial", Font.PLAIN, 15));
        tmno.setSize(190, 20);
        tmno.setLocation(200, 200);
        c.add(tmno);
       
        city = new JLabel("City");
        city.setFont(new Font("Arial", Font.PLAIN, 20));
        city.setSize(100, 20);
        city.setLocation(100, 250);
        c.add(city);
 
        tcity = new JTextField();
        tcity.setFont(new Font("Arial", Font.PLAIN, 15));
        tcity.setSize(190, 20);
        tcity.setLocation(200, 250);
        c.add(tcity);
 
        userstatus = new JLabel("User-status");
        userstatus.setFont(new Font("Arial", Font.PLAIN, 20));
        userstatus.setSize(200, 20);
        userstatus.setLocation(100, 300);
        c.add(userstatus);
 
        active = new JRadioButton("Active");
        active.setFont(new Font("Arial", Font.PLAIN, 15));
        active.setSelected(true);
        active.setSize(75, 20);
        active.setLocation(250, 300);
        c.add(active);
 
        inactive = new JRadioButton("InActive");
        inactive.setFont(new Font("Arial", Font.PLAIN, 15));
        inactive.setSelected(false);
        inactive.setSize(100, 20);
        inactive.setLocation(250, 350);
        c.add(inactive);
 
        usergrp = new ButtonGroup();
        usergrp.add(active);
        usergrp.add(inactive);

        add = new JButton("Add");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setSize(90, 20);
        add.setLocation(100, 400);
        add.addActionListener(this);
        c.add(add);
 
        update = new JButton("Update");
        update.setFont(new Font("Arial", Font.PLAIN, 15));
        update.setSize(90, 20);
        update.setLocation(200, 400);
        update.addActionListener(this);
        c.add(update);
       
        delete = new JButton("Delete");
        delete.setFont(new Font("Arial", Font.PLAIN, 15));
        delete.setSize(120, 20);
        delete.setLocation(300, 400);
        delete.addActionListener(this);
        c.add(delete);
 
        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        c.add(tout);
 
        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        c.add(res);
 
        setVisible(true);
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		      Class.forName(JDBC_DRIVER);
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      if (e.getSource() == add) {
	                data
	                    = "Name   : "
	                      + tname.getText() + "\n"
	                      + "Email  : "
	                      + temail.getText() + "\n"
	                      + "Mobile : "
	                      + tmno.getText() + "\n"
	                      + "City   : "
	                      + tcity.getText() + "\n";
	                if (active.isSelected())
	                    data1 = "User-status : Active"
	                            + "\n";
	                else
	                    data1 = "Userstatus : InActive"
	                            + "\n";
	 
	                tout.setText(data + data1);
	                tout.setEditable(false);
	                res.setText("User Added Successfully..");
	                
	                //Adding to DB
	                sql = "INSERT INTO details(id, name, email, mobile,city,user_status) VALUES (?, ?, ?, ?, ?, ?)";
	                statement = conn.prepareStatement(sql);
	                  statement.setString(1, null);
	                  statement.setString(2, tname.getText());
	                  statement.setString(3, temail.getText());
	                  statement.setString(4, tmno.getText());
	                  statement.setString(5, tcity.getText());
	                  if (active.isSelected()) {
	                  statement.setBoolean(6, true);
	                  }else {
	                  statement.setBoolean(6, false);
	                  }
	                  
	                  try {
	                  int rowsInserted = statement.executeUpdate();
	                  if (rowsInserted > 0) {
	                 res.setText("User Added Successfully..");
	                 System.out.println("User Added Successfully..");
	                  }
	                  }catch(SQLIntegrityConstraintViolationException si) {
	                  res.setText("Failed to Add user,Duplicate Found!");
	                  System.out.println("Failed to Add user,Duplicate Found!");
	                  }
	              
	             }else if (e.getSource() == update) {
	            	 JTextField idField = new JTextField(5);
	                 JTextField cityField = new JTextField(20);

	                 JPanel myPanel = new JPanel();
	                 myPanel.add(new JLabel("id:"));
	                 myPanel.add(idField);
	                 myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	                 myPanel.add(new JLabel("city:"));
	                 myPanel.add(cityField);

	                 int result = JOptionPane.showConfirmDialog(null, myPanel,
	                          "Please Enter id and city to update", JOptionPane.OK_CANCEL_OPTION);
	                 if (result == JOptionPane.OK_OPTION) {
	                    System.out.println("id value: " + idField.getText());
	                    System.out.println("city value: " + cityField.getText());
	                 }
	                     sql = "UPDATE userdetails SET city = ? WHERE id=?";
	                     System.out.println("Enter Id to update:");
	           
	                     int id = Integer.parseInt(idField.getText());
	                     String cityName = cityField.getText();
	           
	                try {
	                 statement = conn.prepareStatement(sql);
	                 statement.setString(1, cityName);
	                 statement.setInt(2, id);
	                 int rowsUpdated = statement.executeUpdate();
	                 if (rowsUpdated > 0) {
	                	 search(idField.getText());
	                	 res.setText("An existing user was updated successfully!");
	                     System.out.println("An existing user was updated successfully!");
	                 }
	                 }catch(SQLException se) {
	                	 res.setText("Failed to Update Data!");
	                	 System.out.println("Failed to Update Data!");
	                	 se.printStackTrace();
	                 }
	             }else if (e.getSource() == delete) {
	            	 Object[] options1 = {"proceed"};
	            	 JPanel panel = new JPanel();
	                 panel.add(new JLabel("Enter ID to delete"));
	                 JTextField textField = new JTextField(10);
	                 panel.add(textField);

	                 int result = JOptionPane.showOptionDialog(null, panel, "Delete User",
	                         JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
	                         null, options1, null);
	                 if (result == JOptionPane.YES_OPTION){
	                  System.out.println("Entered id:"+ textField.getText());
	                  JOptionPane.showMessageDialog(null, textField.getText());
	                 }
	             
	              sql = "DELETE FROM details WHERE id=? OR user_status = false";
	              int id = Integer.parseInt(textField.getText());
	               
	               try {
	               statement = conn.prepareStatement(sql);
	               statement.setInt(1, id);
	               
	               int rowsDeleted = statement.executeUpdate();
	               if (rowsDeleted > 0) {
	            	   tout.setText("A User with ID : " + textField.getText() + "is deleted");
	            	   res.setText("A user deleted successfully!");
	                   System.out.println("A user deleted successfully!");
	               }
	               } catch (SQLException se) {
	            	   tout.setText("");
	            	   res.setText("Failed to Delete User");
	            	   se.printStackTrace();
	               }
	          	}
		      log.debug(data + data1);  
		      log.info(data + data1);
		    }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception ex){
			      //Handle errors for Class.forName
			      ex.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(statement!=null)
			            statement.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			 } 
	}

	private static void search(String searchId) {
		sql = "SELECT * FROM details WHERE id = " + searchId;
	      try {
	    	rs = statement.executeQuery(sql);

		      while(rs.next()){
		         //Retrieve by column name
		    	 int id  = rs.getInt("id");
		         String uname = rs.getString("name");
		         String email = rs.getString("email");
		         String mobile = rs.getString("mobile");
		         String ucity = rs.getString("city");
		         Boolean user_status = rs.getBoolean("user_status");
		         
		         	data
	                    = "Name   : "
	                      + uname + "\n"
	                      + "Email  : "
	                      + email + "\n"
	                      + "Mobile : "
	                      + mobile + "\n"
	                      + "City   : "
	                      + ucity + "\n";
	                if (user_status == true)
	                    data1 = "User-status : Active"
	                            + "\n";
	                else
	                    data1 = "Userstatus : InActive"
	                            + "\n";
	 
	                tout.setText(data + data1);
	                tout.setEditable(false);
	                
		         
		         //Display values
	                System.out.println("Details : " + data + data1);
		         log.info(data + data1);
//		         log.info("EMAIL: " + email + ", ");
//		         log.info("MOBILE: " + mobile + ", ");
//		         log.info("CITY: " + ucity + ", ");
//		         log.info("USER_STATUS: " + user_status);
	
	    	  }
	      } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
}