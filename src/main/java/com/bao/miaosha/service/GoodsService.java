package com.bao.miaosha.service;

import com.bao.miaosha.Vo.GoodsVo;
import com.bao.miaosha.dao.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> getGoodsVoList(){
        List<GoodsVo> goods = goodsDao.getGoodsVoList();
        return goods;
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        int record = goodsDao.reduceStock(goods.getId());
        return record > 0;
    }

}
