package com.nelioalves.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	public static List<Integer> decodeIntList(String valor){
//		List<Integer> retorno = new ArrayList<>();
//		String[] vet = valor.split(",");
//		for (String string : vet) {
//			retorno.add(Integer.parseInt(string));
//		}
//		return retorno;
//		
		return Arrays.asList(valor.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	public static String decodeParam(String valor) {
		try {
			return URLDecoder.decode(valor, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
