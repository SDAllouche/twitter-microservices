package com.sdia.elasticindexclient.service;


import com.sdia.elasticmodel.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
