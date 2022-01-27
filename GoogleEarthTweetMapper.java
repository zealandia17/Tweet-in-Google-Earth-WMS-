package eot_zealandia_tanya_cesar;

import java.io.IOException;
import org.geotools.ows.ServiceException;
import java.sql.SQLException;
import java.util.Scanner;

public class GoogleEarthTweetMapper 
	{
	public static void main(String[] args) throws IOException, org.geotools.ows.ServiceException, ServiceException, SQLException
		{	
			
			Scanner sc = new Scanner(System.in);//Scanner class object 
			WMSCon wms_object = new WMSCon();// WMSconnection class object
			TwitterViz tweet_object = new TwitterViz();// Twitter class object
			
			/*For WMSCon class*/
			System.out.println("Enter the WMS getCapabilities url");
			String wms_url = sc.nextLine();
			//Example: "http://maps.heigit.org/osm-wms/service?service=WMS&request=GetCapabilities&version=1.1.0";
			System.out.println(" Enter the path where you want to save the WMS image");
			String wms_img = sc.nextLine();
			//Example: "D:\\Copernicus Master Science\\P_Basic Software Development\\Unit 5\\wms_image.png";
			System.out.println("Enter the path where you want to save the WMS image kml");
			String kml_image = sc.nextLine();
			//Example "D:\\Copernicus Master Science\\P_Basic Software Development\\Unit 5\\wms_kml.kml";
			System.out.println("Enter the Google Earth application path");
			String GoogleEarth = sc.nextLine();
			//Example "C:\\Program Files\\Google\\Google Earth Pro\\client\\googleearth.exe";
			wms_object.wms_function(wms_url, wms_img, kml_image, GoogleEarth);//call the function
			
			/*For TwitterViz class*/
			System.out.println("Enter the path to the twitter database that you want to visualize");
			String database_url = sc.nextLine();
			//Example: "D:\\Copernicus Master Science\\P_Basic Software Development\\Unit 5\\twitter\\twitter_database.db";
			System.out.println(" Enter the path where you want to save the tweet kml");
			String kml_twitter = sc.nextLine();
			//Example: "D:\\Copernicus Master Science\\P_Basic Software Development\\tweet_map.kml";
			tweet_object.tweet_function(database_url, GoogleEarth, kml_twitter);//call the function
			
			//close the scanner
			sc.close();
		}

}
