package com.sdia.elasticindexclient.service;


import com.sdia.elasticmodel.index.IndexModel;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<IndexedObjectInformation> save(List<T> documents);
}
