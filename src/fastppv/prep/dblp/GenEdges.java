package fastppv.prep.dblp;

import java.util.HashSet;

import fastppv.util.io.TextReader;
import fastppv.util.io.TextWriter;


public class GenEdges {
	
	public static void main(String[] args) throws Exception {
		String rawPath = args[0] + "/";
		String procPath = args[1] + "/";
		String year = args[2]; 
		
		String line;
		TextReader in = new TextReader(procPath + "dblp-" + year + "-nodes");
		HashSet<Integer> idSet = new HashSet<Integer>();
		while ( (line = in.readln()) != null)
			idSet.add(Integer.parseInt(line));
		in.close();
		
		in = new TextReader(rawPath + "paperEvent.txt");
		TextWriter out = new TextWriter(procPath + "dblp-" + year + "-edges");
		while ( (line = in.readln()) != null ) {
			String[] split = line.split("\t");
			int pid = Integer.parseInt(split[0]);
			if (!idSet.contains(pid))
				continue;
			
			if (!split[1].isEmpty()) {
				int vid = Integer.parseInt(split[1]) + GenNodes.VENUE_ID_OFFSET;
				if (idSet.contains(vid)) {
					out.writeln(pid + "\t" + vid);
					out.writeln(vid + "\t" + pid);
				}
			}
			
			for (int i = 3; i < split.length; i++) {
				int aid = Integer.parseInt(split[i]) + GenNodes.AUTHOR_ID_OFFSET;
				if (idSet.contains(aid)) {
					out.writeln(pid + "\t" + aid);
					out.writeln(aid + "\t" + pid);
				}
			}
		}
		in.close();
		
		/*
		in = new TextReader(rawPath + "citations.txt");
		while ( (line = in.readln()) != null ) {
			String[] split = line.split("\t");
			int n = Integer.parseInt(split[0]);
			if (!idSet.contains(n))
				continue;
			
			for (int i = 1; i < split.length; i++) {
				int m = Integer.parseInt(split[i]);
				if (idSet.contains(m))
					out.writeln(n + "\t" + m);
			}
		}
		*/
		
//		in = new TextReader(rawPath + "paper_term.txt");
//		while ( (line = in.readln()) != null ) {
//			String[] split = line.split("\t");
//			int pid = Integer.parseInt(split[0]);
//			if (!idSet.contains(pid))
//				continue;
//			for (int i = 1; i < split.length; i++) {
//				int tid = Integer.parseInt(split[i]) + GenNodes.TERM_ID_OFFSET;
//				if (idSet.contains(tid)) {
//					out.writeln(pid + "\t" + tid);
//					out.writeln(tid + "\t" + pid);
//				}
//			}
//		}
//		in.close();
		
		out.close();
	}
}
