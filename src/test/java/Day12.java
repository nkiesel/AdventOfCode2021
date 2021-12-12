import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day12 {
	public static void main(String[] args) {
		String[] input1 = { "start-A", "start-b", "A-c", "A-b", "b-d", "A-end", "b-end" };
		String[] input2 = { "dc-end", "HN-start", "start-kj", "dc-start", "dc-HN", "LN-dc", "HN-end", "kj-sa", "kj-HN",
				"kj-dc" };
		String[] input3 = { "fs-end", "he-DX", "fs-he", "start-DX", "pj-DX", "end-zg", "zg-sl", "zg-pj", "pj-he",
				"RW-he", "fs-DX", "pj-RW", "zg-RW", "start-pj", "he-WI", "zg-he", "pj-fs", "start-RW" };
		String[] input = { "BC-gt", "gt-zf", "end-KH", "end-BC", "so-NL", "so-ly", "start-BC", "NL-zf", "end-LK",
				"LK-so", "ly-KH", "NL-bt", "gt-NL", "start-zf", "so-zf", "ly-BC", "BC-zf", "zf-ly", "ly-NL", "ly-LK",
				"IA-bt", "bt-so", "ui-KH", "gt-start", "KH-so" };
		Map<String, Set<String>> adj = new HashMap<>();
		for (int i = 0; i < input.length; i++) {
			String s = input[i];
			String[] parts = s.split("-");
			if (!adj.containsKey(parts[0])) {
				adj.put(parts[0], new HashSet<String>());
			}
			if (!adj.containsKey(parts[1])) {
				adj.put(parts[1], new HashSet<String>());
			}
			adj.get(parts[0]).add(parts[1]);
			adj.get(parts[1]).add(parts[0]);
		}
		Map<String, Integer> visited = new HashMap<>();
		visited.put("start", 1);
		long ways = df(adj, "start", visited);

		System.out.println(ways);
	}
	static long df(Map<String, Set<String>> adj, String curr, Map<String, Integer> visited) {
		if (curr.equals("end")) {
			return 1L;
		}
		long ways = 0;
		for (String s : adj.get(curr)) {
			if (!visited.containsKey(s) || (!"start".equals(s) && !"end".equals(s) && visited.containsKey(s)
					&& visited.values().stream().filter(v -> v > 1).count() == 0)) {
				
				//for part 2 add the below cond
				//|| (!"start".equals(s) && !"end".equals(s) && visited.containsKey(s) && visited.values().stream().filter(v -> v > 1).count() == 0
				if (Character.isLowerCase(s.charAt(0))) {
					visited.computeIfPresent(s, (k, v) -> v + 1);
					visited.putIfAbsent(s, 1);
				}
				ways += df(adj, s, visited);
				if (Character.isLowerCase(s.charAt(0))) {
					visited.computeIfPresent(s, (k, v) -> v - 1 == 0 ? null : v - 1);
				}
			}
		}
		return ways;
	}
}

//it's a counting problem, used adjacency list + dfs + backtracking
