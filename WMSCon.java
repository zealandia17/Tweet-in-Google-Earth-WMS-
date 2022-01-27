package eot_zealandia_tanya_cesar;
import org.geotools.ows.ServiceException;
import org.geotools.ows.wms.Layer;
import org.geotools.ows.wms.WMS1_1_0.GetMapRequest;
import org.geotools.ows.wms.WMSCapabilities;
import org.geotools.ows.wms.WMSUtils;
import org.geotools.ows.wms.WebMapServer;
import org.geotools.ows.wms.response.GetMapResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;


public class WMSCon 	
	{
	
	public void wms_function(String wms_url, String wms_img, String kml_image, String GoogleEarth)  throws IOException, org.geotools.ows.ServiceException, ServiceException
		{
		   try
			  {			   
			   	                                           /* 1.WMS Connection and storing the map as local file*/

			   	 /*Making connection to the server*/

			         URL url = null;//initialize the object of URL class to save the connection to WMS Server 
				 url = new URL(wms_url);
				 WebMapServer wms = null; //WebMapServer object to communicate with the server and store the capabilities document
				 wms = new WebMapServer(url); 
				 WMSCapabilities capabilities = wms.getCapabilities();//stores the capabilities of WMS
				 GetMapRequest request = (GetMapRequest) wms.createGetMapRequest(); //GetMapRequest object to access the map from the server
			  
			        /*Setting the Parameters for the Map to be obtained from WMS*/

				 request.setFormat("image/png");
				 request.setDimensions("1000", "1000"); 
				 request.setTransparent(true);
				 request.setSRS("EPSG:4326");
				 request.setBBox("-79.0377,38.9328,-72.8794,44.5947");
				 for ( Layer layer : WMSUtils.getNamedLayers(capabilities) ) 
				      {
					   request.addLayer(layer);
				      }// to access the "Named" layers from WMS service
			 
			         /*Saving the WMS image as png in the system*/

				 File path = new File (wms_img);//local file to store the image
				 GetMapResponse response = (GetMapResponse) wms.issueRequest(request);//GetMapResponse object to get the required map
				 BufferedImage image = ImageIO.read(response.getInputStream());// reading the obtained image
				 ImageIO.write(image, "png", path);// writing the image as png on local file
			
				             
				                                        /* 2.Integrating the local WMS image into KML and visualizing it in GoogleEarth*/
			 
			 
			        /*array to store the path of GoogleEarth application and kml file*/
	
                                  String[] kml_wms = new String[2];
				  kml_wms[0] = GoogleEarth;
				  kml_wms[1] = kml_image;
			
			         /*Creating the kml document for the image*/	

				  String kml_wms_String = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" 
					                       + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\r\n" 
							       +"<Folder>\r\n"
					                       + "<name>WMS Ground Overlay</name>\r\n"
							       + "<description> WMS Ground Overlay </description>\r\n"
							       + "<GroundOverlay>\r\n "
							       + "<Icon>\r\n"
							       + "<href>" + path + "</href>\r\n"
							       + "</Icon>\r\n"
							       + "<color>B3FFFFFF</color>"
							       + "<LatLonBox>\r\n"
					                       + "<north>38.9328</north>\r\n"
					                       + "<south>44.5947</south>\r\n"
					                       + "<east>-79.0377</east>\r\n"
					                       + "<west>-72.8794</west>\r\n"
					                       + "</LatLonBox>\r\n"
					                       + "</GroundOverlay>\r\n"
					                       +"</Folder>"
					                       + "</kml>";
				  System.out.print(kml_wms_String);
			
			         /*write kml document to file*/

				  FileWriter newFile = new FileWriter(kml_wms[1]);
				  newFile.write(kml_wms_String);
				  newFile.close();
				     
		                /*print a message*/

				System.out.println("Executing google earth pro and opening kml file");

		               /*execute file*/ 

			       Runtime.getRuntime().exec(kml_wms);

		              /*print another message*/

			       System.out.println("WMS.kml should now open.");
				     
	            }catch(IOException e) 
		    {
		       e.printStackTrace();
					
		     }//close catch
			
     }//close function

}//close class
	
