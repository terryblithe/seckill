package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.rmi.server.ExportException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring/spring-service.xml",
    "classpath:spring/spring-dao.xml"})
public class SeckillServiceTest {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private SeckillService seckillService;

  @Test
  public void getSeckillList() {
    List<Seckill> list = seckillService.getSeckillList();
    logger.info("list = {}", list);
  }

  @Test
  public void getById() {
    long id = 1000L;
    Seckill seckill = seckillService.getById(id);
    logger.info("seckill = {}", seckill);
  }

  @Test
  public void exportSeckillUrl() {
    long id = 1000L;
    Exposer exposer = seckillService.exportSeckillUrl(id);
    logger.info("exposer = {}", exposer);
    //exposer = Exposer{exposerd=true, md5='6d8da0e62f4a116c4d4efce533737d53', seckillId=1000, now=0, start=0, end=0}
  }

  @Test
  public void executeSeckill() {
    long id = 1000;
    long phone = 13567891232L;
    String md5 = "6d8da0e62f4a116c4d4efce533737d53";
    try {
      SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
      logger.info("result = {}", execution);
    } catch (RepeatKillException e) {
      logger.error(e.getMessage());
    } catch (SeckillException e) {
      logger.error(e.getMessage());
    }
  }

  //测试代码完整逻辑,注意可重复执行
  @Test
  public void testSeckillLogic() {
    long id = 1001L;
    Exposer exposer = seckillService.exportSeckillUrl(id);
    if(exposer.isExposerd()) {
      long phone = 13567891232L;
      String md5 = exposer.getMd5();
      try {
        SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
        logger.info("result = {}", execution);
      } catch (RepeatKillException e) {
        logger.error(e.getMessage());
      } catch (SeckillException e) {
        logger.error(e.getMessage());
      }
    } else {
      //秒杀未开启
      logger.warn("exposer = {}", exposer);
    }
  }

  @Test
  public void executionByProcedureTest() {
    long seckillId = 1000;
    long phone = 13678911234L;
    Exposer exposer = seckillService.exportSeckillUrl(seckillId);
    if(exposer.isExposerd()) {
      String md5 = exposer.getMd5();
      SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
      logger.info(execution.getStateInfo());
    }
  }
}