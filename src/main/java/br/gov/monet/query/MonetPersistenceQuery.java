package br.gov.monet.query;

import br.gov.monet.core.utils.MonetPage;
import br.gov.monet.core.utils.MonetPageable;

public interface MonetPersistenceQuery<T> {
	
	T findById2(Object id);
    MonetPage<T> collectionPage(MonetPageable pageable);

}
