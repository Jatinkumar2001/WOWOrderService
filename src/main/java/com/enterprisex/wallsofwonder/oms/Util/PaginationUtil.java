package com.enterprisex.wallsofwonder.oms.Util;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for handling pagination.
 * <p/>
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">Github API</api>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 * </p>
 */
public class PaginationUtil {

    public static final int DEFAULT_OFFSET = 0;

    public static final int MIN_OFFSET = 0;

    public static final int DEFAULT_LIMIT = 20;

    public static final int MAX_LIMIT = 100;

    public static Pageable generatePageRequest(Integer offset, Integer limit) {
        if (offset == null || offset < MIN_OFFSET) {
            offset = DEFAULT_OFFSET;
        }
        if (limit == null || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        return  PageRequest.of(offset - 1, limit);
    }

    public static Map<String, Integer> generatePaginationData(Page<?> page, Integer offset, Integer limit)
            throws URISyntaxException {

        if (offset == null || offset < MIN_OFFSET) {
            offset = DEFAULT_OFFSET;
        }
        if (limit == null || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        Map<String, Integer> NextPage= new HashMap<>();
        if (offset < page.getTotalPages()) {
            if (offset==page.getTotalPages()-1){
               return null;
            }else {
                NextPage.put("page", offset + 1);
                NextPage.put("per_page", limit);
            }

        }
        else {
//            NextPage.put("page",offset - 1);
//            NextPage.put("per_page",limit);
            return null;

        }

        return NextPage;
    }
}

