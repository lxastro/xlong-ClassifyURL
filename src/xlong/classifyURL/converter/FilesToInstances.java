package xlong.classifyURL.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Attribute;

public class FilesToInstances {
	public static Instances createInstances(String instancesName, String positiveFile, String negativeFile) throws IOException{
		// 1. set up attributes
		ArrayList<Attribute> atts = new ArrayList<Attribute>();		
		// URL string
		ArrayList<String> attString = null;
		atts.add(new Attribute("url", attString));

	    // positive or negative
		ArrayList<String>attVals = new ArrayList<String>(2); 
		attVals.add("positive"); 
		attVals.add("negative"); 
		Attribute attClasses = new Attribute("class?", attVals);
	    atts.add(attClasses);
		
	    // 2. create Instances object
	    Instances data = new Instances(instancesName, atts, 0);
	    double[] vals;
	    BufferedReader posReader = new BufferedReader(new FileReader(positiveFile)); 
	    BufferedReader negReader = new BufferedReader(new FileReader(negativeFile));
	    String str;
	    posReader.readLine(); posReader.readLine();
	    while ((str=posReader.readLine())!=null){
		    vals = new double[data.numAttributes()];
		    vals[0] = data.attribute(0).addStringValue(str);
		    vals[1] = attVals.indexOf("pos");	
		    
		    data.add(new DenseInstance(1.0, vals));
	    }
	    negReader.readLine(); negReader.readLine();
	    while ((str=negReader.readLine())!=null){
		    vals = new double[data.numAttributes()];
		    vals[0] = data.attribute(0).addStringValue(str);
		    vals[1] = attVals.indexOf("neg");	    	
		    data.add(new DenseInstance(1.0, vals));
	    }
	    posReader.close();
	    negReader.close();
	    
	    return data;
	}
	
	public static Instances createInstances(String instancesName, String file) throws IOException{
		return createInstances(instancesName,new String[] {file});
		
	}

	public static Instances createInstances(String instancesName, String[] files) throws IOException{
		// 1. set up attributes
		ArrayList<Attribute> atts = new ArrayList<Attribute>();		
		// URL string
		ArrayList<String> attString = null;
		atts.add(new Attribute("url", attString));

	    // positive or negative
		ArrayList<String>attVals = new ArrayList<String>(files.length); 
	    for (String file:files){
	    	attVals.add(new File(file).getName());
	    }
		Attribute attClasses = new Attribute("class?", attVals);
	    atts.add(attClasses);
		
	    // 2. create Instances object
	    Instances data = new Instances(instancesName, atts, 0);
	    double[] vals;
	    
	    //int cnt = 0;
	    for (String file:files){
	    	//cnt ++;
	    	//System.out.println(cnt);
	    	BufferedReader reader = new BufferedReader(new FileReader(file)); 
		    String str;
		    reader.readLine(); reader.readLine();
		    while ((str=reader.readLine())!=null){
			    vals = new double[data.numAttributes()];
			    vals[0] = data.attribute(0).addStringValue(str);
			    vals[1] = attVals.indexOf(new File(file).getName());	
			    data.add(new DenseInstance(1.0, vals));
		    }
		    reader.close();
	    }
	    
	    return data;
	}	
	
	public static void main(String[] args) throws IOException{
		Instances data = createInstances("PoloLeagure",
				new String[] {"results/URLsHaveSameType/2",
				"results/URLsHaveSameType/6"});	
		System.out.print(data);
	}
}
