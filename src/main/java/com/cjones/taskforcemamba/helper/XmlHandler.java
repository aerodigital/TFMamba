package com.cjones.taskforcemamba.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.a;


public class XmlHandler extends DefaultHandler {
private RssFeedStructure feedStr = new RssFeedStructure();
private List<RssFeedStructure> rssList = new ArrayList<RssFeedStructure>();

private int articlesAdded = 0;

// Number of articles to download
private static final int ARTICLES_LIMIT = 25;

StringBuffer chars = new StringBuffer();

public void startElement(String uri, String localName, String qName, Attributes atts) {
chars = new StringBuffer();

 if (qName.equalsIgnoreCase("media:content"))
	
{
	 if(!atts.getValue("url").toString().equalsIgnoreCase("null")){
	 feedStr.setImgLink(atts.getValue("url").toString());
	 }
	 else{
		 feedStr.setImgLink(""); 
	 }
}

}
public void endElement(String uri, String localName, String qName) throws SAXException {
if (localName.equalsIgnoreCase("title"))
{
	feedStr.setTitle(chars.toString());
}
else if (localName.equalsIgnoreCase("description"))
{

    feedStr.setDescription(chars.toString());

}
else if (localName.equalsIgnoreCase("pubDate"))
{

	feedStr.setPubDate(chars.toString());
}
else if (localName.equalsIgnoreCase("encoded"))
{

	feedStr.setEncodedContent(chars.toString());
}
else if (qName.equalsIgnoreCase("media:content"))
	
{
	
}
else if (localName.equalsIgnoreCase("link"))
{
    feedStr.setImgLink(chars.toString());

}
if (localName.equalsIgnoreCase("item")) {
rssList.add(feedStr);

feedStr = new RssFeedStructure();
articlesAdded++;
if (articlesAdded >= ARTICLES_LIMIT)
{
throw new SAXException();
}
}
}

public void characters(char ch[], int start, int length) {
chars.append(new String(ch, start, length));
}


public List<RssFeedStructure> getLatestArticles(String feedUrl) {
URL url = null;
try {

SAXParserFactory spf = SAXParserFactory.newInstance();
SAXParser sp = spf.newSAXParser();
XMLReader xr = sp.getXMLReader();
url = new URL(feedUrl);
xr.setContentHandler(this);
xr.parse(new InputSource(url.openStream()));
} catch (IOException e) {
} catch (SAXException e) {

} catch (ParserConfigurationException e) {

}

return rssList;
}

}