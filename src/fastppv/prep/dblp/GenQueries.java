package fastppv.prep.dblp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import fastppv.data.Graph;
import fastppv.data.Node;
import fastppv.util.io.TextWriter;

public class GenQueries {

	public static final int numQueries = 1000;

	public static void main(String[] args) throws Exception {
		String procPath = args[0] + "/";
	//	String year = args[1]; 
				
		Graph graph = new Graph();
        graph.loadFromFile(procPath + "graph.node.noAttr", procPath + "graph.edge.noAttr", false);
		List<Node> nList = new ArrayList<Node>(graph.getNodes());
		
		Random rnd = new Random(576298109912L);
		Set<Node> qList = new HashSet<Node>();
		while (qList.size() < numQueries) {
			qList.add(nList.get(rnd.nextInt(nList.size())));
		}		
		
		
		TextWriter out = new TextWriter(procPath + "SSquery.noAttr");
		for (Node n : qList)
			out.writeln(n.id + "");
		out.close();
		
	}

}
