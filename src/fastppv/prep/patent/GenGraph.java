package fastppv.prep.patent;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import fastppv.data.Graph;
import fastppv.util.io.TextReader;
import fastppv.util.io.TextWriter;


public class GenGraph {
	
	
	
	public static void main(String[] args) throws Exception {
		
		String rawPath = args[0] + "/";
		String procPath = args[1] + "/";
	//	double frac = Double.parseDouble(args[2]); 
		String fileName = args[2];
		
		Set<Integer> nodes = new HashSet<Integer>();
		
		TextReader in = new TextReader(rawPath + fileName);
		String line;
		while ( (line = in.readln()) != null) {
			if (line.startsWith("#"))
				continue;
			String[] split = line.split("\t");
			int from = Integer.parseInt(split[0]);
			int to = Integer.parseInt(split[1]);
			nodes.add(from);
			nodes.add(to);
		}
		in.close();
		
		String nodeTmp = procPath + fileName + "-nodes.tmp";
		TextWriter out = new TextWriter(nodeTmp);
		for (int n : nodes)
			out.writeln(n);
		out.close();
		
		String edgeTmp = procPath + fileName + "-edges.tmp";
		out = new TextWriter(edgeTmp);
		in = new TextReader(rawPath + fileName );
		while ( (line = in.readln()) != null) {
			if (!line.startsWith("#"))
				out.writeln(line);
		}
		in.close();
		out.close();
		
		Graph g = new Graph();
		g.loadFromFile(nodeTmp, edgeTmp, false);
		//g.preprocess();
		g.saveToFile(procPath + fileName + "-nodes", procPath + fileName + "-edges");
		
		( new File(nodeTmp) ).delete();
		( new File(edgeTmp) ).delete();
		/*
		String rawPath = args[0] + "/";
		String procPath = args[1] + "/";
		double frac = Double.parseDouble(args[2]); 
		
		Set<Integer> nodes = new HashSet<Integer>();
		
		TextReader in = new TextReader(rawPath + "livejournal.txt");
		String line;
		while ( (line = in.readln()) != null) {
			if (line.startsWith("#"))
				continue;
			String[] split = line.split("\t");
			int from = Integer.parseInt(split[0]);
			int to = Integer.parseInt(split[1]);
			nodes.add(from);
			nodes.add(to);
		}
		in.close();
		
		Random rnd = new Random(7822219909881L);
		List<Integer> nList = new ArrayList<Integer>(nodes);
		//Collections.sort(nList);
		//nList = nList.subList(0, (int)(nList.size() * frac));
		Set<Integer> subNodes = new HashSet<Integer>();
		if (frac <= 0.5) {
			while (subNodes.size() < nodes.size() * frac) {
				subNodes.add(nList.get(rnd.nextInt(nList.size())));
			}
		}
		else {
			subNodes.addAll(nList);
			while (subNodes.size() > nodes.size() * frac) {
				subNodes.remove(nList.get(rnd.nextInt(nList.size())));
			}
		}
		
		TextWriter out = new TextWriter(procPath + "lj-" + frac + "-nodes");
		for (int n : subNodes)
			out.writeln(n);
		out.close();
		
		out = new TextWriter(procPath + "lj-" + frac + "-edges");
		in = new TextReader(rawPath + "livejournal.txt");
		while ( (line = in.readln()) != null) {
			if (line.startsWith("#"))
				continue;
			String[] split = line.split("\t");
			int from = Integer.parseInt(split[0]);
			int to = Integer.parseInt(split[1]);
			if (subNodes.contains(from) && subNodes.contains(to))
				out.writeln(from + "\t" + to);
		}
		in.close();
		out.close();
		*/
	}
}
