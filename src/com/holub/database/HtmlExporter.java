package com.holub.database;

import java.io.*;
import java.util.*;


public class HtmlExporter implements Table.Exporter
{	private final Writer out;
	private 	  int	 width;

	public HtmlExporter( Writer out )
	{	this.out = out;
	}

	public void storeMetadata( String tableName,
							   int width,
							   int height,
							   Iterator columnNames ) throws IOException

	{	
		storeColumn( columnNames );
	}
	public void storeColumn( Iterator data ) throws IOException
	{	int i = width;
		while( data.hasNext() )
		{	Object datum = data.next();
			// Null columns are represented by an empty field
			// (two commas in a row). There's nothing to write
			// if the column data is null.
			if( datum != null )	{
				System.out.println(datum.toString());
				out.write( "<th>"+datum.toString()+"</th>" );
			}
		}
		out.write("\n");
	}
	public void storeRow( Iterator data ) throws IOException
	{	int i = width;
		out.write("<tr>");
		while( data.hasNext() )
		{	Object datum = data.next();
			// Null columns are represented by an empty field
			// (two commas in a row). There's nothing to write
			// if the column data is null.
			if( datum != null )	{
				out.write( "<td>"+datum.toString()+"</td>" );
			}
		}
		out.write("</tr>");
		out.write("\n");
	}

	public void startTable() throws IOException {
		out.write("<table border=\"1\">");
	}
	public void endTable()   throws IOException {
		out.write("</table>");
	}
	
	public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("C:\\dp2020\\htmlExporter.html");
        HtmlExporter htmlBuilder = new HtmlExporter(writer);
        
        Table people = TableFactory.create( "people",
				   new String[]{ "First", "Last"		} );
		people.insert( new String[]{ "Allen",	"Holub" 	} );
		people.insert( new String[]{ "Ichabod",	"Crane" 	} );
		people.insert( new String[]{ "Rip",		"VanWinkle" } );
		people.insert( new String[]{ "Goldie",	"Locks" 	} );
		
		people.export( htmlBuilder );
		writer.close();
        
    }

}
