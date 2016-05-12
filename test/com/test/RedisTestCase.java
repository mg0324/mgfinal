package com.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mybatis.caches.redis.RedisCache;

import com.demo.dao.DemoDao;
import com.demo.service.DemoService;
import com.demo.vo.Demo;
import com.mgfinal.core.ioc.annotation.UseBean;

/**
 * Test with Ubuntu
 * sudo apt-get install redis-server
 * execute the test
 */
public final class RedisTestCase {

  private static final String DEFAULT_ID = "REDIS";

  private static RedisCache cache;
  private DemoDao demoDao = new DemoDao();
  
  @BeforeClass
  public static void newCache() {
    cache = new RedisCache(DEFAULT_ID);
  }
  
  @Test
  public void test1(){
	  cache.putObject(Demo.class.getClass().getName(), demoDao.one("id", 2109, Demo.class));
	  System.out.println(cache.getObject(Demo.class.getClass().getName()).toString());
  }

  @Test
  public void shouldDemonstrateCopiesAreEqual() {
	 System.out.println(cache.toString());
    for (int i = 0; i < 1000; i++) {
      cache.putObject(i, i);
      assertEquals(i, cache.getObject(i));
    }
  }

  @Test
  public void shouldRemoveItemOnDemand() {
    cache.putObject(0, 0);
    assertNotNull(cache.getObject(0));
    cache.removeObject(0);
    assertNull(cache.getObject(0));
  }

  @Test
  public void shouldFlushAllItemsOnDemand() {
    for (int i = 0; i < 5; i++) {
      cache.putObject(i, i);
    }
    assertNotNull(cache.getObject(0));
    assertNotNull(cache.getObject(4));
    cache.clear();
    assertNull(cache.getObject(0));
    assertNull(cache.getObject(4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotCreateCache() {
    cache = new RedisCache(null);
  }

  @Test
  public void shouldVerifyCacheId() {
    assertEquals("REDIS", cache.getId());
  }

  @Test
  public void shouldVerifyToString() {
    assertEquals("Redis {REDIS}", cache.toString());
  }

}
