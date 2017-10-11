package br.com.getset.calendarchurch.util;

import java.util.List;

public class Util {
	public static <T> int getIndexList(List<?> list, Object obj, Class<T> clazz){
		int index = 0;
		for (Object object : list) {
			if(clazz.cast(object).equals(clazz.cast(obj)))
				return index;
		}
		return -1;
	}
}
