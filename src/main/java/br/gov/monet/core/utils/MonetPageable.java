package br.gov.monet.core.utils;

public class MonetPageable {

	private final int pageNumber; 
    private final int pageSize;
    private final String sortBy;
    private final boolean ascending;

    public MonetPageable(int pageNumber, int pageSize, String sortBy, boolean ascending) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Número da página deve ser maior ou igual a 0.");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Tamanho da página deve ser maior que 0.");
        }
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.ascending = ascending;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public boolean isAscending() {
        return ascending;
    }

    public int getOffset() {
        return pageNumber * pageSize;
    }

    @Override
    public String toString() {
        return "Pageable{" +
               "pageNumber=" + pageNumber +
               ", pageSize=" + pageSize +
               ", sortBy='" + sortBy + '\'' +
               ", ascending=" + ascending +
               '}';
    }
	
}
