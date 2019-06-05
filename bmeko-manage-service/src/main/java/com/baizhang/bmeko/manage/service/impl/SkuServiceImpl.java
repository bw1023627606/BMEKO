package com.baizhang.bmeko.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baizhang.bmeko.bean.SkuAttrValue;
import com.baizhang.bmeko.bean.SkuImage;
import com.baizhang.bmeko.bean.SkuInfo;
import com.baizhang.bmeko.bean.SkuSaleAttrValue;
import com.baizhang.bmeko.manage.mapper.SkuAttrValueMapper;
import com.baizhang.bmeko.manage.mapper.SkuImageMapper;
import com.baizhang.bmeko.manage.mapper.SkuInfoMapper;
import com.baizhang.bmeko.manage.mapper.SkuSaleAttrValueMapper;
import com.baizhang.bmeko.service.SkuService;
import com.baizhang.bmeko.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    SkuImageMapper skuImageMapper;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public List<SkuInfo> getSkuListBySpu(String spuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(spuId);
        return skuInfoMapper.select(skuInfo);
    }

    @Override
    public void saveSku(SkuInfo skuInfo) {
        skuInfoMapper.insertSelective(skuInfo);
        String skuId = skuInfo.getId();
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(skuId);
            skuAttrValueMapper.insert(skuAttrValue);
        }

        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);
        }

        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuId);
            skuImageMapper.insert(skuImage);
        }


    }

    @Override
    public SkuInfo getSkuById(String skuId) {
        Jedis jedis = null;
        try{
            jedis = redisUtil.getJedis();
        }catch (Exception e){
            return null;
        }
        SkuInfo skuInfo = null;

        //查询redis缓存
        String key = "sku:"+skuId+":info";
        String val = jedis.get(key);

        if("empty".equals(val)){
            System.out.println(Thread.currentThread()+"发现数据库中暂时没有该商品，直接返回空对象");
            return skuInfo;
        }


        if(StringUtils.isBlank(val)){
            System.out.println(Thread.currentThread()+"发现缓存中没有该数据，申请分布式锁");
            //申请缓存锁
//            String OK = jedis.set("sku:" + skuId + ":lock", "1", "nxxx", "expx", 3000);
            String OK= jedis.set("sku:"+skuId+":lock","1","nx","px",5000);

            if("OK".equals(OK)){//拿到缓存锁
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread()+"获得分布式锁，开始访问数据库");
                //查询Db,前提是没有查到数据
                skuInfo = getSkuByIdFromDb(skuId);
                if(skuInfo!=null){
                    System.out.println(Thread.currentThread()+"通过分布式锁，查到缓存，同步缓存，归还锁");
                    //同步缓存
                    jedis.set(key,JSON.toJSONString(skuInfo));
                }else{
                    System.out.println(Thread.currentThread()+"通过分布式锁，没有查到缓存，通知同伴10秒钟之内不要访问该sku");
                    //通知同伴
                    jedis.setex("sku:"+skuId+":info",10,"empty");
                }
                //归还缓存锁
                System.out.println(Thread.currentThread()+"归还分布式锁");
                jedis.del("sku:" + skuId + "info");
            }else{//没有拿到缓存锁
                System.out.println(Thread.currentThread()+"没有获得分布式锁，等待3秒，自旋");
                //自旋
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getSkuById(skuId);
            }



        }else{
            //正常转换缓存数据
            skuInfo = JSON.parseObject(val,SkuInfo.class);
        }

        return skuInfo;
    }

    @Override
    public List<SkuInfo> getSkuListBycaralog3Id(String catalog3Id) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setCatalog3Id(catalog3Id);
        List<SkuInfo> skuInfos = skuInfoMapper.select(skuInfo);

        for (SkuInfo info : skuInfos) {
            String id = info.getId();
            SkuAttrValue skuAttrValue = new SkuAttrValue();
            skuAttrValue.setSkuId(id);
            List<SkuAttrValue> skuAttrValues = skuAttrValueMapper.select(skuAttrValue);
            info.setSkuAttrValueList(skuAttrValues);
        }

        return skuInfos;
    }

    private SkuInfo getSkuByIdFromDb(String skuId) {

        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        SkuInfo skuInfo1 = skuInfoMapper.selectOne(skuInfo);

        if(skuInfo1!=null){
            SkuImage skuImage = new SkuImage();
            skuImage.setSkuId(skuId);
            List<SkuImage> skuImages = skuImageMapper.select(skuImage);
            skuInfo1.setSkuImageList(skuImages);

            SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
            skuSaleAttrValue.setSkuId(skuId);
            List<SkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueMapper.select(skuSaleAttrValue);
            skuInfo1.setSkuSaleAttrValueList(skuSaleAttrValues);
        }

        return skuInfo1;
    }


}
