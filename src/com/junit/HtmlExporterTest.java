package com.junit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.holub.database.HtmlExporter;
import com.holub.database.JTableExporter;
import com.holub.database.Table;
import com.holub.database.TableFactory;
import java.io.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
class HtmlExporterTest {

	@Test
	void testStoreMetadata() {
		fail("Not yet implemented");
	}

	@Test
	void testStoreRow() {
		fail("Not yet implemented");
	}

	@Test
	void testStartTable() {
		fail("Not yet implemented");
	}

	@Test
	void testEndTable() {
		fail("Not yet implemented");
	}
	
	@Test
	void main() throws IOException {
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
