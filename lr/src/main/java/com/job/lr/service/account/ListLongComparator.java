package com.job.lr.service.account;

import java.util.Comparator;

public class ListLongComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
	     Long l1= (Long)o1;
	     Long l2= (Long)o2;	  
         return l1.compareTo(l2);	
	}

}
