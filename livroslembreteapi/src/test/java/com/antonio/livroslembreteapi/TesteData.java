package com.antonio.livroslembreteapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TesteData {

	public static void main(String[] args) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm --- dd/MM/yyyy", Locale.getDefault());
		Calendar data = Calendar.getInstance();
		
		
		System.out.println(dateFormatter.format(data.getTime()));
		System.out.println(data.get(Calendar.DAY_OF_WEEK));
		System.out.println(data.get(Calendar.DAY_OF_WEEK_IN_MONTH));
	}
}

