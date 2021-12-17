import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

public class Day17 {
	public static void main(String[] args) {
		int[] targetX1 = new int[] { 20, 30 };
		int[] targetY1 = new int[] { -10, -5 };
		int[] targetX = new int[] { 211, 232 };
		int[] targetY = new int[] { -124, -69 };
		Map<Integer, Set<Integer>> yTimes = new HashMap<>();
		int maxY = 0;
		for (int vel = targetY[0]; vel <= targetY[0] * (-1); vel++) {
			int y = 0;
			int currVel = vel;
			int time = 0;
			int maxLocalY = 0;
			while (y >= targetY[0]) {
				y += currVel;
				currVel--;
				time++;
				if (currVel == 0)
					maxLocalY = y;
				if (y >= targetY[0] && y <= targetY[1]) {
					maxY = Math.max(maxY, maxLocalY);
					if (!yTimes.containsKey(time)) {
						yTimes.put(time, new HashSet<Integer>());
					}
					yTimes.get(time).add(vel);
				}
			}
		}
		System.out.println(maxY);
		OptionalInt optionalMaxYTime = yTimes.keySet().stream().mapToInt(Integer::intValue).max();
		int maxYTime = optionalMaxYTime.getAsInt();
		Set<String> velocities = new HashSet<>();
		Map<Integer, Set<Integer>> xTimes = new HashMap<>();
		for (int vel = 0; vel <= targetX[1]; vel++) {
			int dist = 0;
			int velX = vel;
			int time = 0;
			while (dist <= targetX[1] && time <= maxYTime) {
				dist += velX;
				if (velX > 0) {
					velX--;
				}
				time++;
				if (dist >= targetX[0] && dist <= targetX[1]) {
					if (yTimes.containsKey(time)) {
						for (int b : yTimes.get(time)) {
							velocities.add(vel + "," + b);
						}
					}
				}
			}
		}
		System.out.println(velocities.size());
	}
}
