import java.util.HashMap;
import java.util.Map;

public class Day14 {
	public static void main(String[] args) {
		String[] input1 = { "CH,B", "HH,N", "CB,H", "NH,C", "HB,C", "HC,B", "HN,C", "NN,C", "BH,H", "NC,B", "NB,B",
				"BN,B", "BB,N", "BC,B", "CC,N", "CN,C" };
		String[] input = { "CO,B", "CV,N", "HV,H", "ON,O", "FS,F", "NS,S", "VK,C", "BV,F", "SC,N", "NV,V", "NC,F",
				"NH,B", "BO,K", "FC,H", "NB,H", "HO,F", "SB,N", "KP,V", "OS,C", "OB,P", "SH,N", "BC,H", "CK,H", "SO,N",
				"SP,P", "CF,P", "KV,F", "CS,V", "FF,P", "VS,V", "CP,S", "PH,V", "OP,K", "KH,B", "FB,S", "CN,H", "KS,P",
				"FN,O", "PV,O", "VC,S", "HF,N", "OC,O", "PK,V", "KC,C", "HK,C", "PO,N", "OO,S", "VH,N", "CC,K", "BP,K",
				"HC,K", "FV,K", "KF,V", "VF,C", "HN,S", "VP,B", "HH,O", "FO,O", "PC,N", "KK,C", "PN,P", "NN,C", "FH,N",
				"VV,O", "OK,V", "CB,N", "SN,H", "VO,H", "BB,C", "PB,F", "NF,P", "KO,S", "PP,K", "NO,O", "SF,N", "KN,S",
				"PS,O", "VN,V", "SS,N", "BF,O", "HP,H", "HS,N", "BS,S", "VB,F", "PF,K", "SV,V", "BH,P", "FP,O", "CH,P",
				"OH,K", "OF,F", "HB,V", "FK,V", "BN,V", "SK,F", "OV,C", "NP,S", "NK,S", "BK,C", "KB,F", };
		Map<String, String> map = new HashMap<>();
		for (String s : input) {
			String[] parts = s.split(",");
			map.put(parts[0], parts[1]);
		}
		String template1 = "NNCB";
		String template = "VPPHOPVVSFSVFOCOSBKF";

		Map<String, Long> ans = new HashMap<>();
		String start = null;
		for (int i = 0; i < template.length() - 1; i++) {
			if (i == 0) {
				start = template.substring(i, i + 2);
			}
			ans.computeIfPresent(template.substring(i, i + 2), (k, v) -> v + 1);
			ans.putIfAbsent(template.substring(i, i + 2), 1L);
		}
		for (int i = 0; i < 40; i++) {
			Map<String, Long> temp = new HashMap<>();
			boolean startSet = false;
			for (String key : ans.keySet()) {
				Long val = ans.get(key);
				String one = key.substring(0, 1) + map.get(key);
				String two = map.get(key) + key.substring(1);
				temp.computeIfPresent(one, (k, v) -> v + val);
				temp.putIfAbsent(one, val);
				temp.computeIfPresent(two, (k, v) -> v + val);
				temp.putIfAbsent(two, val);
				if (!startSet && key.equals(start)) {
					start = one;
					startSet = true;
				}
			}
			ans = temp;
		}
		long[] freq = countFreq(ans, start);
		long max = Long.MIN_VALUE;
		int maxI = -1, minI = -1;
		long min = Long.MAX_VALUE;
		for (int i = 0; i < 26; i++) {
			if (max < freq[i]) {
				max = freq[i];
				maxI = i;
			}
			if (freq[i] > 0 && min > freq[i]) {
				min = freq[i];
				minI = i;
			}
		}
		System.out.println(freq[maxI] - freq[minI]);
	}

	static long[] countFreq(Map<String, Long> map, String start) {
		long[] freq = new long[26];
		for (String key : map.keySet()) {
			freq[key.charAt(1) - 'A'] += map.get(key);
		}
		freq[start.charAt(0) - 'A']++;
		return freq;
	}
}
