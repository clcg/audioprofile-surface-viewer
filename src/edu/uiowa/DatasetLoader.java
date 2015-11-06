package edu.uiowa;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import edu.uiowa.mappers.Poly22Mapper;
import edu.uiowa.mappers.Poly23Mapper;
import edu.uiowa.mappers.Poly33Mapper;

public class DatasetLoader {
	
	static String [] ADNSHLlist = {"DFNA2A.surf",
			"DFNA2notAnotB.surf",
			"DFNA3A.surf",
			"DFNA5.surf",
			"DFNA6_14_38.surf",
			"DFNA8_12.surf",
			"DFNA9.surf",
			"DFNA10.surf",
			"DFNA11.surf",
			"DFNA13.surf",
			"DFNA15.surf",
			"DFNA17.surf",
			"DFNA20_26.surf",
			"DFNA21.surf",
			"DFNA22.surf",
			"DFNA24.surf",
			"DFNA25.surf",
			"DFNA27.surf",
			"DFNA28.surf",
			"DFNA31.surf",
			"DFNA36A.surf"};
	
	static Vector<Surface> loadDataset()
	{
		
		Vector<Surface> surfaces = new Vector<Surface>();
		
		InputStream url = DatasetLoader.class.getResourceAsStream("/ADNSHL/DFNA10.surf");
		
		for(int i = 0; i < ADNSHLlist.length; i++)
		{
			try {
				surfaces.add( parseSurface( "/ADNSHL/POLY22/"+ADNSHLlist[i] ) );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return surfaces;
		
	}
	
	static Surface parseSurface(String path) {
		Surface s = new Surface();
		
		Scanner br = null;
		try {
			InputStream is = DatasetLoader.class.getResourceAsStream(path);
			//BufferedInputStream bis = new BufferedInputStream(is);
			
			
			br = new Scanner(is);
			br.useDelimiter("\n");
			
			s.title = br.next();
			
			s.type = br.next();
			
			s.minAge = Float.parseFloat(br.next());
			s.maxAge = Float.parseFloat(br.next());
			
			s.formula = br.next();
			s.coefNames = br.next();
			
			String coef = br.next();
			System.out.println(s.type);
			
			if(s.type.equals("poly33"))
				s.mapper = new Poly33Mapper( coef );
			else if(s.type.equals("poly23"))
				s.mapper = new Poly23Mapper( coef );
			else if(s.type.equals("poly22"))
				s.mapper = new Poly22Mapper( coef );
			
			System.out.println(s.mapper);
			br.close();

			
		} catch (Exception e) {
			e.printStackTrace();
			s = null;
		}
		
		
		
		return s;
	}
	
}
