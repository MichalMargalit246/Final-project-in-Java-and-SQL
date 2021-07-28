package project;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xml{
// insert insert = new insert(null);
	static functions fun = new functions();
	


    public static void exportVehiclesToXML() {
            try (PreparedStatement stmt = sql.getConnection().prepareStatement(sql.SELECT_SONGS);
                    ResultSet rs = stmt.executeQuery()) {
                // create document object.
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder().newDocument();
                
                // push root element into document object.
                Element rootElement = doc.createElement("Songs");
                rootElement.setAttribute("exportDate", LocalDateTime.now().toString());
                doc.appendChild(rootElement);
                while (rs.next()) {     // run on all  vehicle records..
                    // create vehicles element.
                    Element song = doc.createElement("Song");
                    
                    // assign key to vehicles.
                    Attr attr = doc.createAttribute("songId");
                    attr.setValue(rs.getString(1));
                    song.setAttributeNode(attr);
                    
                    // push elements to  vehicle.
                    for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
                        Element element = doc.createElement(
                                rs.getMetaData().getColumnName(i)); // push element to doc.
                        rs.getObject(i); // for wasNull() check..
                        element.appendChild(doc.createTextNode(
                                rs.wasNull() ? "" : rs.getString(i)));  // set element value.
                        song.appendChild(element);  // push element to  vehicle.
                    }
                    
                    // push vehicle to document's root element.
                    rootElement.appendChild(song);
                }
                
                // write the content into xml file
                DOMSource source = new DOMSource(doc);
                File file = new File("projectXML/songs.xml");
                file.getParentFile().mkdir(); // create xml folder if doesn't exist.
                StreamResult result = new StreamResult(file);
                TransformerFactory factory = TransformerFactory.newInstance();
                
                // IF CAUSES ISSUES, COMMENT THIS LINE.
                factory.setAttribute("indent-number", 2);
                //
                
                Transformer transformer = factory.newTransformer();
                
                // IF CAUSES ISSUES, COMMENT THESE 2 LINES.
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
               
                transformer.transform(source, result);
                
                JOptionPane.showMessageDialog(null,"vehicles data exported successfully!");
            } catch (SQLException | NullPointerException | ParserConfigurationException
                    | TransformerException e) {
                e.printStackTrace();
                
            }
    }
    
    /**
     * imports vehicles from xml to db.
     * @param path xml filepath.
     */
    public static void importVehiclesFromXML(String path) {
    	//boolean f2 = true, f3 = true;
    	try {
			Document doc = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(new File(path));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("Song");
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					String str = el.getAttribute("songId");
					int code = Integer.parseInt(str);
					String str2 = el.getElementsByTagName("songName").item(0).getTextContent();
					String str3 = el.getElementsByTagName("lyrics").item(0).getTextContent();
					String str4 = el.getElementsByTagName("music").item(0).getTextContent();
					String str5 = el.getElementsByTagName("performer").item(0).getTextContent();
					String str6 = el.getElementsByTagName("filePath").item(0).getTextContent();
					String str7 = el.getElementsByTagName("sumWords").item(0).getTextContent();
					int sumWords = Integer.parseInt(str7);
					String str8 = el.getElementsByTagName("sumLines").item(0).getTextContent();
					int sumLines = Integer.parseInt(str8);
					String str9 = el.getElementsByTagName("sumVerse").item(0).getTextContent();
					int sumVerse = Integer.parseInt(str9);
					String str10 = el.getElementsByTagName("sumChar").item(0).getTextContent();
					int sumChar = Integer.parseInt(str10);
					boolean flag = checkSong(str2);
					//System.out.println("im4444444444444");
					if(flag == false)
					{
						int[] mySongData = new int[4];
						mySongData = fun.readFile(str6, str2);
						insert.createNewPerson(str2, str3, str4, str5, str6, sumWords, sumLines, sumVerse, sumChar);
					}
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			//System.out.println("im333333333333");
			e.printStackTrace();
		}
    }
    public static boolean checkSong(String title) {
    	try {  		 
			PreparedStatement stmt = sql.getConnection().prepareStatement(sql.CHECK_SONGNAME);
			stmt.setString(1,title);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
		}catch (SQLException e) {
			 e.printStackTrace();
		}
		return false;

    }

}
