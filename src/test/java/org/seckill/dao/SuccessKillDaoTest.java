package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDaoTest {

  @Resource
  private SuccessKillDao successKillDao;

  @Test
  public void insertSuccessKilled() {
    long id = 1000L;
    long phone = 15811049556L;
    int insertCount = successKillDao.insertSuccessKilled(id, phone);
    System.out.println("insertCount = " + insertCount);
  }

  @Test
  public void queryByIdWithSeckill() {
    long id= 1000L;
    long phone = 15811049556L;
    SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(id, phone);
    System.out.println(successKilled);
    System.out.println(successKilled.getSeckill());
  }
}