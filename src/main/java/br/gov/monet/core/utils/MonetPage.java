package br.gov.monet.core.utils;

import java.util.List;

public interface MonetPage<T> {
	
	List<T> getContent();  
    int getPageNumber();     
    int getPageSize();    
    long getTotalElements(); 
    int getTotalPages();          
    boolean isFirst();              
    boolean isLast();

}
