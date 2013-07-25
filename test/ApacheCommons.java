package test;

import java.util.Date;

import org.apache.commons.collections.IterableMap;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class ApacheCommons {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		map();
		int tt = NumberUtils.INTEGER_MINUS_ONE;

		String dt = DateFormatUtils.format(new Date(), "MMM d, yyyy");
		System.out.println(dt);
	}

	@SuppressWarnings("unchecked")
	public static void map() {

		IterableMap map = new HashedMap();
		map.put("1", "aa");
		map.put("2", "a2");
		MapIterator it = map.mapIterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = it.getValue();

			it.setValue("aaa");
		}
	}

}
