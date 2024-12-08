package br.gov.monet.core.utils;

import java.util.*;

public class MonetPageImpl<T> implements MonetPage<T>  {

	private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;

    public MonetPageImpl(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content == null ? Collections.emptyList() : content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public int getTotalPages() {
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    @Override
    public boolean isFirst() {
        return pageNumber == 0;
    }

    @Override
    public boolean isLast() {
        return pageNumber >= getTotalPages() - 1;
    }

    @Override
    public String toString() {
        return "PageImpl{" +
               "content=" + content +
               ", pageNumber=" + pageNumber +
               ", pageSize=" + pageSize +
               ", totalElements=" + totalElements +
               ", totalPages=" + getTotalPages() +
               ", isFirst=" + isFirst() +
               ", isLast=" + isLast() +
               '}';
    }
	
}
