package com.holub.database;

import java.io.*;
import java.util.*;


public class XMLExporter implements Table.Exporter
{	private final Writer out;
	private 	  int	 width;
	private String tableName;
	private String[]	columnHeads;
	
	public XMLExporter( Writer out )
	{	this.out = out;
	}

	public void storeMetadata( String tableName,
							   int width,
							   int height,
							   Iterator columnNames ) throws IOException

	{	
		columnHeads = new String[width];
		this.tableName = tableName;
		out.write("<"+this.tableName+">\n");
		int columnIndex = 0;
		while( columnNames.hasNext() )
			columnHeads[columnIndex++] = columnNames.next().toString();
	}
	public void storeRow( Iterator data ) throws IOException
	{	int i = 0;
		out.write("<row>\n");
		while( data.hasNext() )
		{	Object datum = data.next();
			// Null columns are represented by an empty field
			// (two commas in a row). There's nothing to write
			// if the column data is null.
			if( datum != null )	{
				out.write( "<"+this.columnHeads[i]+">"+datum.toString()+"</"+this.columnHeads[i]+">\n");
				i++;
			}
		}
		out.write("</row>\n");
	}

	public void startTable() throws IOException {
	}
	public void endTable()   throws IOException {
		out.write("</"+this.tableName+">\n");
	}
	
	public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("C:\\dp2020\\XMLExporter.xml");
        XMLExporter XMLBuilder = new XMLExporter(writer);
        
        Table people = TableFactory.create( "people",
				   new String[]{ "First", "Last"		} );
		people.insert( new String[]{ "Allen",	"Holub" 	} );
		people.insert( new String[]{ "Ichabod",	"Crane" 	} );
		people.insert( new String[]{ "Rip",		"VanWinkle" } );
		people.insert( new String[]{ "Goldie",	"Locks" 	} );
		
		people.export( XMLBuilder );
		writer.close();
        
    }

}
