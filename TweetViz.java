package eot_zealandia_tanya_cesar;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TwitterViz 
{
	public void tweet_function(String database_url, String GoogleEarth, String kml_twitter) throws SQLException
	{
		
		try
		   {
											/*1. Twitter Database Access*/

			/*Path to the SQLite Database that holds the twitter data*/
		       
			String twitter_database_url = database_url;		       
		        File f = new File (twitter_database_url);// storing it as file
            
			/*Check if the database is accessible*/  
			
			boolean fileisthere = f.exists();
		        if (fileisthere)
			System.out.println("File found");
		        else
			System.out.println("File not found");
		
			/*add SQLite specific prefix to file url*/
              
			String db_url = "jdbc:sqlite:" + twitter_database_url;
		
			/*Connect to database*/

			Connection conn = DriverManager.getConnection(db_url);//Establish connection to database
			Statement stmt = conn.createStatement();//for creating SQL statement to manipulate,access data.
			ResultSet rset = stmt.executeQuery("SELECT lat,lng,tweet,created_at FROM twitter;" );// Result set for latitude,longitude,tweets and when they were created
		
										
									/*2.Create KML document and Visualize the tweets in GoogleEarth*/
		
			/*Creating KML document for tweets*/
		     
			/*The static First part of KML*/
		       
			String kmlString = ""+"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\r\n" + "<Document>\r\n";
		      
			/*Loop to access the data in the result set*/
			while(rset.next()) 
		            {
				   String tweet = rset.getString("tweet");// the tweet column
				   String createdAt = rset.getString("created_at");// the Date&Time column
			           Double latitude = rset.getDouble("lat");// the latitude column
			           Double longitude = rset.getDouble("lng"); // the longitude column
			           
			           /*Appending tags to the KML document*/

			           kmlString += "<Placemark>" 
			                     + "<name>tweet</name>\r\n" 
					     + "<description>" + tweet + "\n" + createdAt + "</description>\r\n" 
					     + "<Point>\r\n"
			                     + "<coordinates>" + longitude +","+ latitude + "</coordinates>\r\n" 
			                     + "</Point>\r\n"
					     + "<TimeStamp>\r\n" + "<when>" + createdAt.replace(' ', 'T') + "</when>" + "</TimeStamp>\r\n" //For time series visualization of tweets; T needs to be placed between date and time
					     + "<Style id='icon_style'>\r\n"
					     + "<IconStyle>\r\n"								 
					     + "<scale>2</scale>\r\n"
					     + "<Icon>\r\n"
					     + "<href>https://twimap.com/logo.png</href>\r\n"
					     + "</Icon>\r\n"
					     + "<hotSpot x="0.5" y="0" xunits='fraction' yunits='fraction'/>\r\n"
					     + "</IconStyle>\r\n"
					     + "<ListStyle></ListStyle>\r\n"
					     + "</Style>\r\n"
					     + "</Placemark>\r\n";
		            }
		       
			kmlString += "</Document>" + "</kml>"; // close tag
		        System.out.print(kmlString);
		       		
		                                                     
		       /*Visualizing the tweets*/
				  
		       /*write kml string to file*/
				     FileWriter newFile = new FileWriter(kml_twitter);
				     newFile.write(kmlString);
				     newFile.close();
			
		     /*array to store the path of GoogleEarth application and kml file*/	     
				    String[] tweet1 = new String[2];
				    tweet1 [0] = GoogleEarth;
				    tweet1 [1] = kml_twitter;
				   
		      // print a message
				    System.out.println("Executing google earth pro and opening kml file");

			 // execute file 
				    Runtime.getRuntime().exec(tweet1);

		    // print another message
				     System.out.println("tweet.kml should now open.");
				     
		   }//close try
	catch (Exception e) 
		  {
			e.printStackTrace();
		  }//close catch

	}//close function

}//close class
