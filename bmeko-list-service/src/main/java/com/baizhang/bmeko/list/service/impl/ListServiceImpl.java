package com.baizhang.bmeko.list.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baizhang.bmeko.bean.SkuLsInfo;
import com.baizhang.bmeko.bean.SkuLsParam;
import com.baizhang.bmeko.service.ListService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ListServiceImpl implements ListService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<SkuLsInfo> search(SkuLsParam skuLsParam) {
        List<SkuLsInfo> skuLsInfos = new ArrayList<>();
        //如何查询es中的数据
        Search search = new Search.Builder(getMyDsl(skuLsParam)).addIndex("bmeko").addType("SkuLsInfo").build();
        try {
            SearchResult execute = jestClient.execute(search);
            List<SearchResult.Hit<SkuLsInfo, Void>> hits = execute.getHits(SkuLsInfo.class);
            for (SearchResult.Hit<SkuLsInfo, Void> hit : hits) {
                SkuLsInfo source = hit.source;
                skuLsInfos.add(source);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
         return skuLsInfos;
    }


    public static String getMyDsl(SkuLsParam skuLsParam){

        String keyword = skuLsParam.getKeyword();
        String[] valueId = skuLsParam.getValueId();
        String catalog3Id = skuLsParam.getCatalog3Id();


        //filter term
        //must match

        //创建一个dsl工具对象
        SearchSourceBuilder dsl = new SearchSourceBuilder();
        //创建一个先过滤后搜索的对象query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //query过滤语句

        if(StringUtils.isNotBlank(catalog3Id)){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }

        if(null!=valueId&&valueId.length>0){
            for (String s : valueId) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",s);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        //query搜索
        if(StringUtils.isNotBlank(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }

        //将过滤条件和搜索条件放入dsl查询
        dsl.query(boolQueryBuilder);
        dsl.size(100);
        dsl.from(0);
        System.out.println(dsl.toString());
        return dsl.toString();
    }

}
