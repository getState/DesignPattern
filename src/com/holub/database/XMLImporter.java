/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database;

import com.holub.tools.ArrayIterator;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;



import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class XMLImporter implements Table.Importer
{	private BufferedReader  in;			// null once end-of-file reached
	private String[]        columnNames;
	private String          tableName;
	StringBuilder xmlStringBuilder = new StringBuilder();
	DocumentBuilder builder;
	ByteArrayInputStream input;
	int rowIndex = 0;
	Document doc;
	NodeList rows;
	public XMLImporter( Reader in ) throws ParserConfigurationException
	{	this.in = in instanceof BufferedReader
						? (BufferedReader)in
                        : new BufferedReader(in)
	                    ;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		
		
	}
	public void startTable()			throws IOException
	{	
		while(in!=null) {
			String line = in.readLine();
			if(line==null) {
				in=null;
			}else {
				this.xmlStringBuilder.append(line);
			}
		}
		this.input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
		try {
			doc = builder.parse(input);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		Element root = doc.getDocumentElement();
		System.out.println("Table name: "+root.getNodeName());  //people
		
		this.tableName = root.getNodeName();
		
		rows = doc.getElementsByTagName("row");
		
		Node nNode = rows.item(0);
		NodeList nnList = nNode.getChildNodes();
		this.columnNames = new String[nnList.getLength()];
		for(int j = 0 ; j <nnList.getLength() ; j++) {
			Node nnNode = nnList.item(j);
			this.columnNames[j] = nnNode.getNodeName();
		}
	}
	public String loadTableName()		throws IOException
	{	return tableName;
	}
	public int loadWidth()			    throws IOException
	{	return columnNames.length;
	}
	public Iterator loadColumnNames()	throws IOException
	{	
		return new ArrayIterator(columnNames);  //{=CSVImporter.ArrayIteratorCall}
	}

	public Iterator loadRow()			throws IOException
	{
		String[] arr;
		
		Iterator row = null;
		if( rowIndex<rows.getLength() )
		{	
			NodeList nList = rows.item(rowIndex++).getChildNodes();
			arr = new String[nList.getLength()];
			for(int i = 0 ; i<nList.getLength() ; i++) {
				Node nNode = nList.item(i);
				arr[i] = nNode.getTextContent();
				System.out.println(nNode.getTextContent());
			}
			row = new ArrayIterator( arr );
		}
		return row;
	}

	public void endTable() throws IOException {}
	public static void main(String[] args) throws IOException, ParserConfigurationException {
        FileReader reader = new FileReader("C:\\dp2020\\XMLExporter.xml");
        XMLImporter xml = new XMLImporter(reader);
        Table people = new ConcreteTable(xml);
		reader.close();
        
    }
}
